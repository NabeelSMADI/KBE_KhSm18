package de.htw.ai.kbe.songsrx.restserver.bean;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userId;
    private String lastName;
    private String firstName;
    
        
//    @OneToMany(mappedBy="owner",fetch = FetchType.LAZY)
//    private Set<SongList> songLists;

    public User() {
    }

    public User(Integer id, String userId, String lastName, String firstName) {
        this.id = id;
        this.userId = userId;
        this.lastName = lastName;
        this.firstName = firstName;
//        this.songLists = songLists;
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

//    public Set<SongList> getSongLists() {
//        return songLists;
//    }
//
//    public void setSongLists(Set<SongList> songLists) {
//        this.songLists = songLists;
//    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", userId=" + userId + ", lastName=" + lastName + ", firstName=" + firstName + '}';
    }
    

   

}
