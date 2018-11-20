package com.kbe.songsservlet;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 *
 * @author smadi
 */
public class SongsManager {

    TreeMap<Integer, Song> OurSongs = new TreeMap<Integer, Song>();
    private String filename;

    public SongsManager(String filename) {
        try {
            this.filename = filename;
            Map OurSongsMap = readSongsFromFile();
            OurSongs = new TreeMap(OurSongsMap);
        } catch (Exception e) {
        }
    }

    @SuppressWarnings("unchecked")
    private Map<Integer, Song> readSongsFromFile() throws FileNotFoundException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
            List<Song> OurSongsList = (List<Song>) objectMapper.readValue(is, new TypeReference<List<Song>>() {
            });
            return OurSongsList.stream().collect(Collectors.toMap(Song::getId, Song -> Song));
        }
    }

    public void writeSongsToFile() throws FileNotFoundException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(filename))) {
            objectMapper.writeValue(os, OurSongs.values());
        }
    }

    public String SongsToJSON() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(OurSongs.values());
    }

    public synchronized Song addSong(BufferedReader songString) throws FileNotFoundException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        Song newSong = (Song) objectMapper.readValue(songString, new TypeReference<Song>() {
        });
        if (OurSongs.size() > 0) {
            newSong.setId(OurSongs.lastKey() + 1);
        } else {
            newSong.setId(1);
        }
        if (newSong.getTitle() != null) {
            OurSongs.put(newSong.getId(), newSong);

            return newSong;
        }
        return null;
    }

    public String getSongAsJSON(int id) throws JsonProcessingException {
        if (OurSongs.get(id) == null) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(OurSongs.get(id));

    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public Map<Integer, Song> getOurSongs() {
        return OurSongs;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    

}
