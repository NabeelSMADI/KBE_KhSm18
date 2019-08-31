/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htw.ai.kbe.songsrx.restserver.services;

import de.htw.ai.kbe.songsrx.restserver.auth.Auth;
import de.htw.ai.kbe.songsrx.restserver.auth.JWTAuthTokenFilter;
import de.htw.ai.kbe.songsrx.restserver.bean.Song;
import de.htw.ai.kbe.songsrx.restserver.bean.SongList;
import de.htw.ai.kbe.songsrx.restserver.bean.User;
import de.htw.ai.kbe.songsrx.restserver.storage.SongListDAO;
import de.htw.ai.kbe.songsrx.restserver.storage.SongsDAO;
import de.htw.ai.kbe.songsrx.restserver.storage.UsersDAO;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Smady91
 */
@Path("/songLists")
public class SongListsWebService {

    private SongListDAO songListDAO;

    private SongsDAO songsDAO;

    private UsersDAO usersDAO;

    @Inject
    public SongListsWebService(SongListDAO dao, SongsDAO songsDAO, UsersDAO usersDAO) {
        this.songListDAO = dao;
        this.songsDAO = songsDAO;
        this.usersDAO = usersDAO;
    }

    @GET
    @Auth
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllSongListsByUserId(@HeaderParam(HttpHeaders.AUTHORIZATION) String auth, @QueryParam("userId") String ownerId) {
        if (ownerId == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("User Id is Missing").build();
        }
        User SongListsOwner = usersDAO.getUser(ownerId);
        if (SongListsOwner == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No User found with id " + ownerId).build();
        }
        int userId = Integer.parseInt(JWTAuthTokenFilter.getId(auth));
        List<SongList> songLists = songListDAO.getAllSongListsByUserId(SongListsOwner, SongListsOwner.getId().equals(userId));
        GenericEntity<List<SongList>> songListsWrapper = new GenericEntity<List<SongList>>(songLists) {
        };
        if (songLists != null) {
            System.out.println("getAllSongListsByUserId: Returning All SongLists");
            return Response.ok(songListsWrapper).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Auth
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSongListById(@HeaderParam(HttpHeaders.AUTHORIZATION) String auth, @PathParam("id") Integer id) {
        String userId = JWTAuthTokenFilter.getUserId(auth);
        User user = usersDAO.getUser(userId);
        SongList songList = songListDAO.getSongListById(id);

        if (songList != null) {
            if (!songList.isIsPublic()) {
                if (songList.getOwner().getId() != user.getId()) {
                    return Response.status(Response.Status.UNAUTHORIZED).entity("The SongList with id " + id + " is Private").build();
                }
            }
            System.out.println("getSongListById: Returning SongList for id " + id);
            return Response.ok(songList).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No SongList found with id " + id).build();
        }
    }

    @POST
    @Auth
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addSongList(@HeaderParam(HttpHeaders.AUTHORIZATION) String auth, SongList songList) {
        String userId = JWTAuthTokenFilter.getUserId(auth);
        User user = usersDAO.getUser(userId);
        if (songList != null) {
            List<Integer> checkDuplicate = new ArrayList<>();
            for (Song song : songList.getSongs()) {
                if (song.getId() == null) {
                    return Response.status(Response.Status.BAD_REQUEST).entity("Song Id is Missing").build();
                }
                if (checkDuplicate.contains(song.getId())) {
                    return Response.status(Response.Status.BAD_REQUEST).entity("Song with Id " + song.getId() + " is Duplicated").build();
                }
                Song songDB = songsDAO.getSong(song.getId());
                if (songDB == null) {
                    return Response.status(Response.Status.BAD_REQUEST).entity("Song with Id " + song.getId() + " Not Found").build();
                }
                checkDuplicate.add(song.getId());
            }
            System.out.println("addSongList: add new SongList");
            songList.setOwner(user);
            SongList newSongList = songListDAO.addSongList(songList);
            if (newSongList != null) {
                return Response.status(Response.Status.CREATED)
                        .header("location", "/songsRX/rest/songLists/" + newSongList.getId()).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Wrong SongList format").build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Auth
    public Response delSongList(@HeaderParam(HttpHeaders.AUTHORIZATION) String auth, @PathParam("id") Integer id) {
        String userId = JWTAuthTokenFilter.getUserId(auth);
        User user = usersDAO.getUser(userId);
        SongList songList = songListDAO.getSongListById(id);
        if (songList != null) {
            if (songList.getOwner().getId() != user.getId()) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("The SongList with id " + id + " belongs to another user").build();
            }
            songListDAO.delSongList(id);
            System.out.println("delSongList: Delete SongList with id " + id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No SongList found with id " + id).build();
        }
    }

}
