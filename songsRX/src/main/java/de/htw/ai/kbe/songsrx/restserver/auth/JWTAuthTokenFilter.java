/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htw.ai.kbe.songsrx.restserver.auth;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * @author Antonio Goncalves http://www.antoniogoncalves.org --
 */
@Provider
@Auth
@Priority(Priorities.AUTHENTICATION)
public class JWTAuthTokenFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        System.out.println("#### authorizationHeader : " + authorizationHeader);

        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith(JWTAuth.Bearer)) {
            System.out.println("#### invalid authorizationHeader : " + authorizationHeader);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        } else {
            // Extract the token from the HTTP Authorization header
            String token = authorizationHeader.substring(JWTAuth.Bearer.length()).trim();
            try {
                if (!JWTAuth.isAuth(token)) {
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                }
            } catch (Exception e) {
                System.out.println("#### invalid token : " + token);
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        }
    }
}
