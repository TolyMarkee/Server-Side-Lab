package com.wujinkun.helloserver.entity;

public class User {
    private String name;
    private Long id;
    private Integer age;

    // 必须有【无参构造】（JSON反序列化用）
    public User() {}

    // 全参构造（可选，但建议加）
    public User(String name, Long id, Integer age) {
        this.name = name;
        this.id = id;
        this.age = age;
    }

    // 所有属性的getter/setter（缺一不可）
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
}