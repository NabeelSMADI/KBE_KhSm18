/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kbe.songsservlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author smadi
 */
public class SongsManagerTest {

    SongsManager songsManager;

    @BeforeClass
    public static void setUpClass() throws IOException {
        Song song = new Song(1, "test", "test", "test", 2018);
        List<Song> songs = new ArrayList();
        songs.add(song);
        String path = "Test.json";
        File testFile = new File(path);
        if (testFile.exists()) {
            testFile.delete();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(path))) {
            objectMapper.writeValue(os, songs);
        }
    }

    @Before
    public void setUp() throws IOException {
        String path = "Test.json";
        songsManager = new SongsManager(path);

    }

    @AfterClass
    public static void tearDownClass() {
        String path = "Test.json";
        File testFile = new File(path);
        if (testFile.exists()) {
            testFile.delete();
        }
        File testOutPut = new File("testOutPut.json");
        if (testOutPut.exists()) {
            testOutPut.delete();
        }
    }

    /**
     * Test of ReadSongsFromFile method, of class SongsManager.
     */
    @Test
    public void testReadSongsFromFile() throws Exception {
        System.out.println("ReadSongsFromFile");
        Assert.assertEquals(1, songsManager.getOurSongs().size());
    }

    /**
     * Test of SongsToJSON method, of class SongsManager.
     */
    @Test
    public void testSongsToJSON() throws Exception {
        System.out.println("SongsToJSON");
        String expResult = "[{\"id\":1,\"title\":\"test\",\"artist\":\"test\",\"album\":\"test\",\"released\":2018}]";
        String result = songsManager.SongsToJSON();
        assertEquals(expResult, result);
    }

    /**
     * Test of addSong method, of class SongsManager.
     */
    @Test
    public void testAddSong() throws Exception {
        System.out.println("addSong");
        String songString = "{\"id\":1,\"title\":\"test\",\"artist\":\"test\",\"album\":\"test\",\"released\":2018}";
        Reader inputString = new StringReader(songString);
        BufferedReader reader = new BufferedReader(inputString);
        Song result = songsManager.addSong(reader);
        assertNotNull(result);
        Assert.assertEquals(2, songsManager.getOurSongs().size());
    }

    /**
     * Test of getSongAsJSON method, of class SongsManager.
     */
    @Test
    public void testGetSongAsJSON() throws Exception {
        System.out.println("getSongAsJSON");
        int id = 1;
        String expResult = "{\"id\":1,\"title\":\"test\",\"artist\":\"test\",\"album\":\"test\",\"released\":2018}";
        String result = songsManager.getSongAsJSON(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of isInteger method, of class SongsManager. with String
     */
    @Test
    public void testIsInteger01() {
        System.out.println("isInteger");
        String s = "s";
        boolean expResult = false;
        boolean result = SongsManager.isInteger(s);
        assertEquals(expResult, result);
    }

    /**
     * Test of isInteger method, of class SongsManager. with Double
     */
    @Test
    public void testIsInteger02() {
        System.out.println("isInteger");
        String s = "3.5";
        boolean expResult = false;
        boolean result = SongsManager.isInteger(s);
        assertEquals(expResult, result);
    }

    /**
     * Test of isInteger method, of class SongsManager. with Int
     */
    @Test
    public void testIsInteger03() {
        System.out.println("isInteger");
        String s = "3";
        boolean expResult = true;
        boolean result = SongsManager.isInteger(s);
        assertEquals(expResult, result);
    }

    /**
     * Test of getOurSongs method, of class SongsManager.
     */
    @Test
    public void testGetOurSongs() {
        System.out.println("getOurSongs");
        Map<Integer, Song> result = songsManager.getOurSongs();
        assertNotNull(result);
    }

    /**
     * Test of writeSongsToFile method, of class SongsManager.
     */
    @Test
    public void testWriteSongsToFile() throws Exception {
        System.out.println("writeSongsToFile");
        File testFile = new File("testOutPut.json");
        songsManager.setFilename("testOutPut.json");
        songsManager.writeSongsToFile();
        assertTrue(testFile.exists());
        String expResult = "[{\"id\":1,\"title\":\"test\",\"artist\":\"test\",\"album\":\"test\",\"released\":2018}]";
        String content = new String(Files.readAllBytes(Paths.get("testOutPut.json")), "UTF-8");
        assertEquals(expResult, content);
    }

}
