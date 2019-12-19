package com.company;
import java.io.Serializable;

public class User implements Serializable {
    private String name = "";
    private String login = "";
    private String password = "";
    public boolean enter(String login, String password){
        return this.login.compareTo(login) == 0 && this.password.compareTo(password) == 0;
    }
    public void setUser(String name, String login, String password){
        this.name = name;
        this.login = login;
        this.password = password;
    }
    public void changePassword(String password){
        this.password = password;
    }
    public String getName(){
        return name;
    }
    public String getLogin(){
        return login;
    }
    public String getPassword(){
        return password;
    }
}
