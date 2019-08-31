/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htw.ai.kbe.songsrx.restserver.services;

import de.htw.ai.kbe.songsrx.restserver.auth.Auth;
import de.htw.ai.kbe.songsrx.restserver.bean.Song;
import de.htw.ai.kbe.songsrx.restserver.storage.SongsDAO;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Smady91
 */
@Path("/songs")
public class SongsWebService {

    private SongsDAO songsDAO;

    @Inject
    public SongsWebService(SongsDAO dao) {
        this.songsDAO = dao;
    }

    @GET
    @Auth
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllSongs() {
        List<Song> songs = songsDAO.getAllSongs();
        GenericEntity<List<Song>> songsWrapper = new GenericEntity<List<Song>>(songs) {
        };
        if (songs != null) {
            System.out.println("getAllSongs: Returning All Songs");
            return Response.ok(songsWrapper).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database is Missing").build();
        }
    }

    @GET
    @Auth
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSong(@PathParam("id") Integer id) {
        Song song = songsDAO.getSong(id);
        if (song != null) {
            System.out.println("getSong: Returning song for id " + id);
            return Response.ok(song).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No song found with id " + id).build();
        }
    }

    @PUT
    @Auth
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateSong(@PathParam("id") Integer id, Song song) {
        if (song != null) {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Song ID is Missing").build();
            }
            if (song.getTitle() == null || song.getTitle().equals("")) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Song Title is Missing").build();
            }
            song.setId(id);
            if (songsDAO.updateSong(song)) {
                System.out.println("updateSong: update song with id " + song.getId());
                return Response.status(Response.Status.NO_CONTENT)
                        .header("location", "/songsRX/rest/songs/" + song.getId()).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("No song found with id " + song.getId()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Wrong song format").build();
        }
    }

    @POST
    @Auth
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addSong(Song song) {
        if (song != null) {
            System.out.println("addSong: add new song");
            Song newSong = songsDAO.addSong(song);
            if (newSong != null) {
                return Response.status(Response.Status.CREATED)
                        .header("location", "/songsRX/rest/songs/" + newSong.getId()).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Song Titel is Missing").build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Wrong song format").build();
        }
    }

    @DELETE
    @Path("/{id}")
//    @Auth
    public Response delSong(@PathParam("id") Integer id) {
         return Response.status(Response.Status.FORBIDDEN).entity("User are not allowed to Delete any Song").build();
//        if (songsDAO.delSong(id)) {
//            System.out.println("delSong: Delete song with id " + id);
//            return Response.status(Response.Status.NO_CONTENT).build();
//        } else {
//            return Response.status(Response.Status.NOT_FOUND).entity("No song found with id " + id).build();
//        }
    }

    @PreDestroy
    void preDestroy() {
        songsDAO.preDestroy();
    }
}
