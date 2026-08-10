package com.example.student_management_api.model;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Student {

    private int id;

    @NotBlank(message = "Name cannot be blank")
    private String name;
    @Min(value = 18, message = "Age must be at least 18")
    private int age;
    @Email(message = "Email cannot be blank")
    private String email;

    //Jackson needs a way to create the object when you're using @RequestBody. this will help
    public Student() {
    }

    public Student(int id,String name,int age,String email)
    {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
