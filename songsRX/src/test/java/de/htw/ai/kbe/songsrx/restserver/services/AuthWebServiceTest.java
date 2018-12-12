/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htw.ai.kbe.songsrx.restserver.services;

import de.htw.ai.kbe.songsrx.restserver.auth.JWTAuth;
import de.htw.ai.kbe.songsrx.restserver.auth.JWTAuthTokenFilter;
import de.htw.ai.kbe.songsrx.restserver.bean.Song;
import de.htw.ai.kbe.songsrx.restserver.storage.SongsManager;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;


public class AuthWebServiceTest extends JerseyTest {

    @Override
    protected Application configure() {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.register(SongsWebService.class);
        resourceConfig.register(AuthWebService.class);
        resourceConfig.register(JWTAuthTokenFilter.class);
        return resourceConfig;
    }

    @Test
    public void getUserAuthTokenShouldReturn200() {
        Response response = target("/auth").queryParam("userId", "mmuster").request().get();
        String content = response.readEntity(String.class);
        Assert.assertEquals(200, response.getStatus());
        String id = JWTAuth.getId(content);
        String userId = JWTAuth.getUserId(content);
        Assert.assertEquals("1", id);
        Assert.assertEquals("mmuster", userId);
    }

    @Test
    public void getWrongUserShouldReturn403() {
        Response response = target("/auth").queryParam("userId", "Wrong").request().get();
        Assert.assertEquals(403, response.getStatus());
    }

    @Test
    public void UpdateSongWithAuthShouldReturn204() {
        Response response = target("/auth").queryParam("userId", "mmuster").request().get();
        String authToken = response.readEntity(String.class);

        Song song = new Song();
        song.setId(10);
        song.setTitle("test1");
        song.setAlbum("test1");
        song.setArtist("test1");
        song.setReleased(1);
        Response response2 = target("/songs/10").request().header("Authorization", authToken).put(Entity.json(song));
        Assert.assertEquals(204, response2.getStatus());
        Assert.assertEquals("test1", SongsManager.getInstance().getSong(10).getTitle());
    }

    @Test
    public void UpdateSongWithoutAuthShouldReturn204() {
        Song song = new Song();
        song.setId(10);
        song.setTitle("test1");
        song.setAlbum("test1");
        song.setArtist("test1");
        song.setReleased(1);
        Response response = target("/songs/10").request().put(Entity.json(song));
        Assert.assertEquals(401, response.getStatus());
    }

    @Test
    public void UpdateSongWithWrongAuthShouldReturn204() {
        Song song = new Song();
        song.setId(10);
        song.setTitle("test1");
        song.setAlbum("test1");
        song.setArtist("test1");
        song.setReleased(1);
        Response response = target("/songs/10").request().header("Authorization", "Wrong").put(Entity.json(song));
        Assert.assertEquals(401, response.getStatus());
    }

}
