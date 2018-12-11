/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htw.ai.kbe.songsrx.restserver.storage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.ai.kbe.songsrx.restserver.bean.Song;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author smadi
 */
public class SongsManager {

    private volatile TreeMap<Integer, Song> storage = new TreeMap<Integer, Song>();

    private SongsManager instance = null;
    private final String filename = "/songs.json";

    private SongsManager() {
        Map<Integer, Song> songs = readSongsFromFile();
        if (songs != null) {
            storage = new TreeMap<>(songs);
        }
    }


    public synchronized static SongsManager getInstance() {
        if (instance == null) {
            instance = new SongsManager();
        }
        return instance;
    }

   private Map<Integer, Song> readSongsFromFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = SongsManager.class.getResourceAsStream(filename)) {
            List<Song> OurSongsList = (List<Song>) objectMapper.readValue(is, new TypeReference<List<Song>>() {
            });
            return OurSongsList.stream().collect(Collectors.toMap(Song::getId, Song -> Song));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SongsManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SongsManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void writeSongsToFile() throws FileNotFoundException, IOException, URISyntaxException {
        ObjectMapper objectMapper = new ObjectMapper();
        URL resourceUrl = getClass().getResource(filename);
        File file = new File(resourceUrl.toURI());
        try (OutputStream os =  new FileOutputStream(file)){;
            objectMapper.writeValue(os, storage.values());
        }
    }

    public synchronized Song addSong(Song song) {
        if (storage.size() > 0) {
            song.setId(storage.lastKey() + 1);
        } else {
            song.setId(1);
        }
        if (song.getTitle() != null) {
            storage.put(song.getId(), song);
            return song;
        }
        return null;
    }

    public List<Song> getAllSongs() {
        if (storage == null) {
            return null;
        }
        return new ArrayList<>(storage.values());
    }

    public Song getSong(int id) {
        if (storage.get(id) == null) {
            return null;
        }
        return storage.get(id);
    }

    public boolean updateSong(Song song) {
        if (storage.get(song.getId()) != null) {
            storage.put(song.getId(), song);
            return true;
        } else {
            return false;
        }
    }

    public boolean delSong(int id) {
        if (storage.get(id) != null) {
            storage.remove(id);
            return true;
        } else {
            return false;
        }
    }
}
