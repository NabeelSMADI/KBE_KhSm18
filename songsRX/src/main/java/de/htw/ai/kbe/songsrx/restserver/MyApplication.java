package de.htw.ai.kbe.songsrx.restserver;

import de.htw.ai.kbe.songsrx.restserver.auth.JWTAuthTokenFilter;
import de.htw.ai.kbe.songsrx.restserver.config.DependencyBinder;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author Smady91
 */
@ApplicationPath("rest")
public class MyApplication extends ResourceConfig {

    public MyApplication() {
        register(new DependencyBinder());
        packages(true, "de.htw.ai.kbe.songsrx.restserver.services");
        packages(true, "de.htw.ai.kbe.songsrx.restserver.auth");
    }

}
