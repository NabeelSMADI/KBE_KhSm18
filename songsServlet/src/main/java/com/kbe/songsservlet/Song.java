package com.kbe.songsservlet;

//import javax.xml.bind.annotation.XmlRootElement;
//@XmlRootElement(name = "song")
public class Song {

    private Integer id;
    private String title;
    private String artist;
    private String album;
    private Integer released;

    public Song() {
    }

    public Song(Integer id, String title, String artist, String album, Integer released) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.released = released;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public Integer getReleased() {
        return released;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Song [id=" + id + ", title=" + title + ", artist=" + artist + ", album=" + album + ", released="
                + released + "]";
    }

}
