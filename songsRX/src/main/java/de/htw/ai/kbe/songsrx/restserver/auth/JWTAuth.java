/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htw.ai.kbe.songsrx.restserver.auth;

import de.htw.ai.kbe.songsrx.restserver.bean.User;
import de.htw.ai.kbe.songsrx.restserver.storage.UsersManager;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

/**
 *
 * @author Smady91
 */
public class JWTAuth {

    private final static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public final static String Bearer = "songRXAuth:";

    public static String generateAuthToken(User user) {
        // We need a signing key, so we'll create one just for this example. Usually
        // the key would be read from your application configuration instead.
        String jws = Jwts.builder()
                .setId(user.getId().toString())
                .setSubject(user.getUserId())
                .signWith(key).compact();
        return Bearer + jws;
    }

    public static boolean isAuth(String compactJws) {
        try {
            String id = Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws).getBody().getId();
            String userId = Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws).getBody().getSubject();
            User user = UsersManager.getInstance().getUser(userId);
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
            String token = compactJws.substring(JWTAuth.Bearer.length()).trim();
            String id = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getId();
            return id;
        } catch (JwtException e) {
            return null;
        }
    }

    public static String getUserId(String compactJws) {
        try {
            String token = compactJws.substring(JWTAuth.Bearer.length()).trim();
            String userId = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
            return userId;
        } catch (JwtException e) {
            return null;
        }
    }

}
