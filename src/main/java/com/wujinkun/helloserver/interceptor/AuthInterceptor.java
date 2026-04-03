package com.wujinkun.helloserver.interceptor;

import com.wujinkun.helloserver.common.ResultCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;

/**
 * 自定义鉴权拦截器（实验要求：接口鉴权拦截）
 * 校验请求头中的Authorization令牌，精细化放行/拦截
 */
public class AuthInterceptor implements HandlerInterceptor {

    /**
     * 请求前置处理：鉴权核心逻辑
     * @return true=放行，false=拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // -------------------------- 步骤1：精细化放行规则（基于HTTP动词+路径） --------------------------
        String method = request.getMethod();
        String uri = request.getRequestURI();

        // 规则1：POST /api/users → 新增用户，放行（无需Token）
        boolean isCreateUser = "POST".equalsIgnoreCase(method) && "/api/users".equals(uri);
        // 规则2：GET /api/users/{id} → 查询用户，放行（无需Token，匹配以/api/users/开头的路径）
        boolean isGetUser = "GET".equalsIgnoreCase(method) && uri.startsWith("/api/users/");
        // 规则3：POST /api/users/login → 登录接口，已在WebConfig中全局放行，此处无需重复判断

        // 满足任一公开规则，直接放行
        if (isCreateUser || isGetUser) {
            return true;
        }

        // -------------------------- 步骤2：敏感操作Token校验（DELETE/PUT等） --------------------------
        // 从请求头获取令牌（前端传参：Header → Authorization: 令牌值）
        String token = request.getHeader("Authorization");

        // 令牌为空/无效 → 拦截，返回401自定义JSON响应
        if (token == null || token.isEmpty()) {
            // 设置响应头：JSON格式+UTF-8编码（解决中文乱码）
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(200); // 保持HTTP状态码200，业务状态码在JSON中
            // 构造错误响应（使用ResultCode枚举，统一格式）
            String errorJson = String.format("{\"code\":%d,\"msg\":\"%s\",\"data\":null}",
                    ResultCode.TOKEN_INVALID.getCode(),
                    "非法操作：敏感动作[" + method + "]需携带登录凭证");
            // 写入响应
            PrintWriter writer = response.getWriter();
            writer.write(errorJson);
            writer.flush();
            writer.close();
            return false; // 拦截请求，不进入Controller
        }

        // 令牌存在 → 放行（实际项目可在此处校验JWT有效性）
        return true;
    }
}