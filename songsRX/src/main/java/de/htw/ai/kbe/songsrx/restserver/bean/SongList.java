package de.htw.ai.kbe.songsrx.restserver.bean;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "songList")
@Entity
@Table(name = "SongList")
public class SongList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Owner_id")
    private User owner;

    private boolean isPublic;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "SongList_Songs",
            joinColumns = {
                @JoinColumn(name = "songList_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "song_id")}
    )
    private Set<Song> songs;

    public SongList() {
    }

    public SongList(Integer id, User owner, boolean isPublic, Set<Song> songs) {
        this.id = id;
        this.owner = owner;
        this.isPublic = isPublic;
        this.songs = songs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public boolean isIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Set<Song> getSongs() {
        return songs;
    }

    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }

    @Override
    public String toString() {
        return "SongList{" + "id=" + id + ", Owner=" + owner + ", isPublic=" + isPublic + '}';
    }

}
