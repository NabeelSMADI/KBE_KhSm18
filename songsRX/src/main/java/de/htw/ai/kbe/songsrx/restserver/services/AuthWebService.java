/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htw.ai.kbe.songsrx.restserver.services;

import de.htw.ai.kbe.songsrx.restserver.auth.JWTAuthTokenFilter;
import de.htw.ai.kbe.songsrx.restserver.bean.User;
import de.htw.ai.kbe.songsrx.restserver.storage.UsersDAO;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Smady91
 */
@Path("/auth")
public class AuthWebService {
    
    private UsersDAO usersDAO;

    @Inject
    public AuthWebService(UsersDAO dao) {
        this.usersDAO = dao;
    }


    @GET
    @Produces({MediaType.TEXT_PLAIN})
    public Response getUserAuthToken(@QueryParam("userId") String userId) {
        User user = usersDAO.getUser(userId);
        if (user != null) {
            return Response.ok(JWTAuthTokenFilter.generateAuthToken(user)).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).entity("No UserId found with id " + userId).build();
        }
    }

}
