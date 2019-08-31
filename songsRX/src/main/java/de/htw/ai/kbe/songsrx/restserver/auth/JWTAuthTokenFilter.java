/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htw.ai.kbe.songsrx.restserver.auth;

import de.htw.ai.kbe.songsrx.restserver.bean.User;
import de.htw.ai.kbe.songsrx.restserver.storage.UsersDAO;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;
import javax.inject.Inject;

/**
 * @author Antonio Goncalves http://www.antoniogoncalves.org --
 */
@Provider
@Auth
@Priority(Priorities.AUTHENTICATION)
public class JWTAuthTokenFilter implements ContainerRequestFilter {

    private final static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public final static String Bearer = "songRXAuth:";
    
    private UsersDAO usersDAO;

    @Inject
    public JWTAuthTokenFilter(UsersDAO dao) {
        this.usersDAO = dao;
    }


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        System.out.println("#### authorizationHeader : " + authorizationHeader);

        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith(Bearer)) {
            System.out.println("#### invalid authorizationHeader : " + authorizationHeader);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        } else {
            // Extract the token from the HTTP Authorization header
            String token = authorizationHeader.substring(Bearer.length()).trim();
            try {
                if (!isAuth(token)) {
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                }
            } catch (Exception e) {
                System.out.println("#### invalid token : " + token);
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        }
    }

    public static String generateAuthToken(User user) {
        // We need a signing key, so we'll create one just for this example. Usually
        // the key would be read from your application configuration instead.
        String jws = Jwts.builder()
                .setId(user.getId().toString())
                .setSubject(user.getUserId())
                .signWith(key).compact();
        return Bearer + jws;
    }

    public boolean isAuth(String compactJws) {
        try {
            String id = Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws).getBody().getId();
            String userId = Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws).getBody().getSubject();
            User user = usersDAO.getUser(userId);
            if (user != null) {
                if (user.getId().toString().equals(id)) {
                    return true;
                }
            }
            return false;
        } catch (JwtException e) {
            return false;
        }
    }

    public static String getId(String compactJws) {
        try {
            String token = compactJws.substring(Bearer.length()).trim();
            String id = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getId();
            return id;
        } catch (JwtException e) {
            return null;
        }
    }

    public static String getUserId(String compactJws) {
        try {
            String token = compactJws.substring(Bearer.length()).trim();
            String userId = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
            return userId;
        } catch (JwtException e) {
            return null;
        }
    }
}
