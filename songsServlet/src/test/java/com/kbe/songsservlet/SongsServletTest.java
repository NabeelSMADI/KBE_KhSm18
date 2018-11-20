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
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.AfterClass;
import org.junit.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author smadi
 */
public class SongsServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    private SongsServlet myServlet;
    static String path = "SongsTest.json";

    @Before
    public void setUp() throws Exception {
        Song s1 = new Song(1, "test1", "test1", "test1", 2018);
        Song s2 = new Song(2, "test2", "test2", "test2", 2018);
        List<Song> songs = new ArrayList();
        songs.add(s1);
        songs.add(s2);
        File testFile = new File(path);
        if (testFile.exists()) {
            testFile.delete();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(path))) {
            objectMapper.writeValue(os, songs);
        }

        myServlet = new SongsServlet();
        SongsManager songsManager = new SongsManager(path);
        myServlet.setSongsManager(songsManager);
        MockitoAnnotations.initMocks(this);
    }

    @AfterClass
    public static void tearDownClass() {
        File testFile = new File(path);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testDoGetAllSongs01() throws IOException, ServletException {
        when(request.getParameter("all")).thenReturn("");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        myServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();

        String SongsFileContent = new String(Files.readAllBytes(Paths.get(path)), "UTF-8");
        assertEquals(result, SongsFileContent);
    }

    @Test
    public void testDoGetAllSongs02() throws IOException, ServletException {
        when(request.getParameter("all")).thenReturn(null);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        myServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();

        assertEquals(result, "");
    }

    @Test
    public void testDoGetGetSong01() throws IOException, ServletException {
        when(request.getParameter("songid")).thenReturn("1");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        myServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();

        String expected = "{\"id\":1,\"title\":\"test1\",\"artist\":\"test1\",\"album\":\"test1\",\"released\":2018}";
        assertEquals(result, expected);
    }

    @Test
    public void testDoGetGetSong02() throws IOException, ServletException {
        when(request.getParameter("songid")).thenReturn("test");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        myServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        String expected = "";
        assertEquals(result, expected);
    }

    @Test
    public void testDoGetGetSong03() throws IOException, ServletException {
        when(request.getParameter("songid")).thenReturn("500");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        myServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        String expected = "Song Not Found";

        assertEquals(result, expected);
    }

    @Test
    public void testDoPost01() throws IOException, ServletException {
        String json = "{\"title\":\"test1\",\"artist\":\"test1\",\"album\":\"test1\",\"released\":2018}";

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(request.getContentType()).thenReturn("application/json");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");
        when(request.getProtocol()).thenReturn("HTTP/1.1");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        myServlet.doPost(request, response);
        pw.flush();
        assertEquals(myServlet.getSongsManager().getOurSongs().size(), 3);
        assertNotNull(myServlet.getSongsManager().getOurSongs().get(3));
        assertEquals(myServlet.getSongsManager().getOurSongs().get(3).getTitle(), "test1");
    }

    @Test
    public void testDoPost02() throws IOException, ServletException {
        String json = "{\"title\":\"test2\",\"artist\":null,\"album\":null,\"released\":null}";

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(request.getContentType()).thenReturn("application/json");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");
        when(request.getProtocol()).thenReturn("HTTP/1.1");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        myServlet.doPost(request, response);
        pw.flush();
        assertEquals(myServlet.getSongsManager().getOurSongs().size(), 3);
        assertNotNull(myServlet.getSongsManager().getOurSongs().get(3));
        assertEquals(myServlet.getSongsManager().getOurSongs().get(3).getTitle(), "test2");
    }

    @Test
    public void testDoPost03() throws IOException, ServletException {
        String json = "{\"album\":\"test1\"}";

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(request.getContentType()).thenReturn("application/json");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");
        when(request.getProtocol()).thenReturn("HTTP/1.1");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        myServlet.doPost(request, response);
        pw.flush();
        assertEquals(myServlet.getSongsManager().getOurSongs().size(), 2);
        assertNull(myServlet.getSongsManager().getOurSongs().get(3));
    }

    @Test
    public void testDoPost04() throws IOException, ServletException {
        String json = "{\"title\":null,\"artist\":null,\"album\":null,\"released\":null}";

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(request.getContentType()).thenReturn("application/json");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");
        when(request.getProtocol()).thenReturn("HTTP/1.1");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        myServlet.doPost(request, response);
        pw.flush();
        assertEquals(myServlet.getSongsManager().getOurSongs().size(), 2);
        assertNull(myServlet.getSongsManager().getOurSongs().get(3));
    }

    @Test
    public void testDoPostThreadSafety01() throws IOException, ServletException {
        String json = "{\"title\":\"TestThreadSafety\",\"artist\":\"test1\",\"album\":\"test1\",\"released\":2018}";
        int loops = 100;
        for (int i = 3; i <= loops; i++) {
            when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
            when(request.getContentType()).thenReturn("application/json");
            when(request.getCharacterEncoding()).thenReturn("UTF-8");
            when(request.getProtocol()).thenReturn("HTTP/1.1");

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            when(response.getWriter()).thenReturn(pw);

            myServlet.doPost(request, response);
            pw.flush();
            assertNotNull(myServlet.getSongsManager().getOurSongs().get(i));
            assertEquals(myServlet.getSongsManager().getOurSongs().get(i).getTitle(), "TestThreadSafety");
            assertEquals(myServlet.getSongsManager().getOurSongs().size(), i);
        }

        myServlet.destroy();
        SongsManager songsManager = new SongsManager(path);
        assertEquals(loops, songsManager.getOurSongs().size());
    }

}
