package com.example.examservice.database;

public class User {

    String first_name;
    String last_name;
    String role;
    String email;
    Date date_registration;
    Date last_login;
    Date last_password_change;
    String group_of_students ;
    int id;


    public User() {

    }

    public User(int id, String first_name, String last_name, String role, String email,
                Date date_registration, Date last_login, Date last_password_change, String group_of_students) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.role = role;
        this.email = email;
        this.date_registration = date_registration;
        this.last_login = last_login;
        this.last_password_change = last_password_change;
        this.group_of_students = group_of_students;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate_registration() {
        return date_registration;
    }

    public void setDate_registration(Date date_registration) {
        this.date_registration = date_registration;
    }

    public Date getLast_login() {
        return last_login;
    }

    public void setLast_login(Date last_login) {
        this.last_login = last_login;
    }

    public Date getLast_password_change() {
        return last_password_change;
    }

    public void setLast_password_change(Date last_password_change) {
        this.last_password_change = last_password_change;
    }

    public String getGroup_of_students() {
        return group_of_students;
    }

    public void setGroup_of_students(String clazz) {
        this.group_of_students = clazz;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", date_registration=" + date_registration +
                ", last_login=" + last_login +
                ", last_password_change=" + last_password_change +
                ", group_of_students='" + group_of_students + '\'' +
                ", id=" + id +
                '}';
    }
}
