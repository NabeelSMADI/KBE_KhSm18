/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htw.ai.kbe.songsrx.restserver.services;

import de.htw.ai.kbe.songsrx.restserver.auth.JWTAuthTokenFilter;
import de.htw.ai.kbe.songsrx.restserver.bean.Song;
import de.htw.ai.kbe.songsrx.restserver.bean.SongList;
import de.htw.ai.kbe.songsrx.restserver.storage.DBSongListDAO;
import de.htw.ai.kbe.songsrx.restserver.storage.DBSongsDAO;
import de.htw.ai.kbe.songsrx.restserver.storage.DBUsersDAO;
import de.htw.ai.kbe.songsrx.restserver.storage.SongListDAO;
import de.htw.ai.kbe.songsrx.restserver.storage.SongsDAO;
import de.htw.ai.kbe.songsrx.restserver.storage.UsersDAO;
import java.util.List;
import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

public class SongListsWebServiceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(SongsWebService.class)
                .packages(true, "de.htw.ai.kbe.songsrx.restserver.services")
                .packages(true, "de.htw.ai.kbe.songsrx.restserver.auth")
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(Persistence
                                .createEntityManagerFactory("songsRX-Test-PU"))
                                .to(EntityManagerFactory.class);
                        bind(DBSongsDAO.class)
                                .to(SongsDAO.class)
                                .in(Singleton.class);
                        bind(DBSongListDAO.class)
                                .to(SongListDAO.class)
                                .in(Singleton.class);
                        bind(DBUsersDAO.class)
                                .to(UsersDAO.class)
                                .in(Singleton.class);
                    }
                });
    }

    @Test
    public void getAllSongListsByUserIdShouldReturn200() {
        Response response = target("/auth").queryParam("userId", "mmuster").request().get();
        String authToken = response.readEntity(String.class);
        Response response2 = target("/songLists").queryParam("userId", "mmuster").request(MediaType.APPLICATION_JSON).header("Authorization", authToken).get();
        List<SongList> list = response2.readEntity(new GenericType<List<SongList>>() {
        });
        Assert.assertEquals(200, response2.getStatus());
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void getAllSongListsByUserIdwithAnotherUserShouldReturnPublic() {
        Response response = target("/auth").queryParam("userId", "mmuster").request().get();
        String authToken = response.readEntity(String.class);
        Response response2 = target("/songLists").queryParam("userId", "eschuler").request(MediaType.APPLICATION_JSON).header("Authorization", authToken).get();
        List<SongList> list = response2.readEntity(new GenericType<List<SongList>>() {
        });
        Assert.assertEquals(200, response2.getStatus());
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void getSongListByIdSameUserPrivatShouldReturn200() {
        Response response = target("/auth").queryParam("userId", "mmuster").request().get();
        String authToken = response.readEntity(String.class);
        Response response2 = target("/songLists/2").request(MediaType.APPLICATION_JSON).header("Authorization", authToken).get();
        Assert.assertEquals(200, response2.getStatus());
    }

    @Test
    public void getSongListByIdwithAnotherUserPublicShouldReturn200() {
        Response response = target("/auth").queryParam("userId", "mmuster").request().get();
        String authToken = response.readEntity(String.class);
        Response response2 = target("/songLists/3").request(MediaType.APPLICATION_JSON).header("Authorization", authToken).get();
        Assert.assertEquals(200, response2.getStatus());
    }

    @Test
    public void getSongListByIdwithAnotherUserPrivatShouldReturn401() {
        Response response = target("/auth").queryParam("userId", "mmuster").request().get();
        String authToken = response.readEntity(String.class);
        Response response2 = target("/songLists/4").request(MediaType.APPLICATION_JSON).header("Authorization", authToken).get();
        Assert.assertEquals(401, response2.getStatus());
    }

    @Test
    public void AddSongListShouldReturn201() {
        Response response = target("/auth").queryParam("userId", "mmuster").request().get();
        String authToken = response.readEntity(String.class);
        Response response2 = target("/songLists").request().header("Authorization", authToken).post(Entity.json("{\"isPublic\": true,\n"
                + "    \"songs\": [\n"
                + "        { \"id\": 1 },\n"
                + "        { \"id\": 10 },\n"
                + "        {\"id\": 5 } ]}"));
        Assert.assertEquals(201, response2.getStatus());
    }

    @Test
    public void AddSongListWithWrongSongIdShouldReturn400() {
        Response response = target("/auth").queryParam("userId", "mmuster").request().get();
        String authToken = response.readEntity(String.class);
        Response response2 = target("/songLists").request().header("Authorization", authToken).post(Entity.json("{\"isPublic\": true,\n"
                + "    \"songs\": [\n"
                + "        { \"id\": 999 },\n"
                + "        { \"id\": 10 },\n"
                + "        {\"id\": 5 } ]}"));
        Assert.assertEquals(400, response2.getStatus());
    }

    @Test
    public void AddSongListWithDuplicatedSongIdShouldReturn400() {
        Response response = target("/auth").queryParam("userId", "mmuster").request().get();
        String authToken = response.readEntity(String.class);
        Response response2 = target("/songLists").request().header("Authorization", authToken).post(Entity.json("{\"isPublic\": true,\n"
                + "    \"songs\": [\n"
                + "        { \"id\": 5 },\n"
                + "        { \"id\": 10 },\n"
                + "        {\"id\": 5 } ]}"));
        Assert.assertEquals(400, response2.getStatus());
    }

    @Test
    public void delSongListShouldReturn204() {
        Response response = target("/auth").queryParam("userId", "mmuster").request().get();
        String authToken = response.readEntity(String.class);
        Response response2 = target("/songLists/1").request(MediaType.APPLICATION_JSON).header("Authorization", authToken).delete();
        Assert.assertEquals(204, response2.getStatus());
    }

    @Test
    public void delSongListWithWrongIdShouldReturn404() {
        Response response = target("/auth").queryParam("userId", "mmuster").request().get();
        String authToken = response.readEntity(String.class);
        Response response2 = target("/songLists/99").request(MediaType.APPLICATION_JSON).header("Authorization", authToken).delete();
        Assert.assertEquals(404, response2.getStatus());
    }

    @Test
    public void delSongListSameUserPrivatShouldReturn204() {
        Response response = target("/auth").queryParam("userId", "mmuster").request().get();
        String authToken = response.readEntity(String.class);
        Response response2 = target("/songLists/2").request(MediaType.APPLICATION_JSON).header("Authorization", authToken).delete();
        Assert.assertEquals(204, response2.getStatus());
    }

    
    @Test
    public void delSongListAnotherUserShouldReturn401() {
        Response response = target("/auth").queryParam("userId", "mmuster").request().get();
        String authToken = response.readEntity(String.class);
        Response response2 = target("/songLists/4").request(MediaType.APPLICATION_JSON).header("Authorization", authToken).delete();
        Assert.assertEquals(401, response2.getStatus());
    }

}
