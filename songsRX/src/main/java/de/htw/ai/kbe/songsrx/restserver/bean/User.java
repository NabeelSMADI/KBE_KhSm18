package de.htw.ai.kbe.songsrx.restserver.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class User {

    private Integer id;
    private String userId;
    private String lastName;
    private String firstName;


    public User() {
    }

    public User(Integer id, String userId, String lastName, String firstName) {
        this.id = id;
        this.userId = userId;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", userId=" + userId + ", lastName=" + lastName + ", firstName=" + firstName + '}';
    }
    
}
