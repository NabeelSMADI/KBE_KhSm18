/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htw.ai.kbe.songsrx.restserver.services;

import de.htw.ai.kbe.songsrx.restserver.bean.Song;
import de.htw.ai.kbe.songsrx.restserver.storage.DBSongsDAO;
import de.htw.ai.kbe.songsrx.restserver.storage.SongsDAO;
import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Smady91
 */
public class SongsWebServiceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(SongsWebService.class).register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(Persistence
                        .createEntityManagerFactory("songsRX-Test-PU"))
                        .to(EntityManagerFactory.class);
                bind(DBSongsDAO.class)
                        .to(SongsDAO.class)
                        .in(Singleton.class);
            }
        });
    }

    @Test
    public void UpdateSongXMLShouldReturn204() {
        Song song = new Song();
        song.setId(10);
        song.setTitle("test1");
        song.setAlbum("test1");
        song.setArtist("test1");
        song.setReleased(1);
        Response response = target("/songs/10").request().put(Entity.xml(song));
        Assert.assertEquals(204, response.getStatus());
//        Assert.assertEquals("test1", songsDAO.getSong(10).getTitle());
    }

    @Test
    public void UpdateSongJSONShouldReturn204() {
        Song song = new Song();
        song.setId(10);
        song.setTitle("test2");
        song.setAlbum("test2");
        song.setArtist("test2");
        song.setReleased(2);
        Response response = target("/songs/10").request().put(Entity.json(song));
        Assert.assertEquals(204, response.getStatus());
//        Assert.assertEquals("test2", songsDAO.getSong(10).getTitle());
    }

    @Test
    public void UpdateSongWithoutIDShouldReturn400() {
        Song song = new Song();
        song.setId(10);
        song.setTitle("test3");
        song.setAlbum("test3");
        song.setArtist("test3");
        song.setReleased(3);
        Response response = target("/songs/a").request().put(Entity.json(song));
        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void UpdateSongWithWrongIDShouldReturn400() {
        Song song = new Song();
        song.setId(999);
        song.setTitle("test4");
        song.setAlbum("test4");
        song.setArtist("test4");
        song.setReleased(4);
        Response response = target("/songs/999").request().put(Entity.json(song));
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void UpdateSongWithEmptyShouldReturn400() {
        Response response = target("/songs/10").request().put(Entity.json(""));
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void UpdateSongWithWrongSongFormat01ShouldReturn400() {
        Response response = target("/songs/10").request().put(Entity.json("{\n"
                + "    \"id\": 10,\n"
                + "    \"title\": \"Can’t Stop the Feeling\",\n"
                + "    \"artist\": \"Justin Timberlake\",\n"
                + "    \"WrongSongFormat\": \"22\",\n"
                + "    \"released\": 2016\n"
                + "}"));
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void UpdateSongWithWrongSongFormat02ShouldReturn400() {
        Response response = target("/songs/10").request().put(Entity.json("{\n"
                + "    \"id\": \"WrongSongFormat\",\n"
                + "    \"title\": \"Can’t Stop the Feeling\",\n"
                + "    \"artist\": \"Justin Timberlake\",\n"
                + "    \"album\": \"22\",\n"
                + "    \"released\": 2016\n"
                + "}"));
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void UpdateSongWithWrongContenttypeShouldReturn400() {
        Response response = target("/songs/10").request().put(Entity.xml("{\n"
                + "    \"id\": 10,\n"
                + "    \"title\": \"Can’t Stop the Feeling\",\n"
                + "    \"artist\": \"Justin Timberlake\",\n"
                + "    \"album\": \"22\",\n"
                + "    \"released\": 2016\n"
                + "}"));
        Assert.assertEquals(400, response.getStatus());
    }

}
