/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htw.ai.kbe.songsrx.restserver;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ClientExceptionMapper implements ExceptionMapper<Throwable>
{
    @Override
    public Response toResponse(Throwable ex) 
    {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .build();
    }
}