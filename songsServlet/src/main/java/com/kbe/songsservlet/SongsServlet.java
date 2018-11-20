/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kbe.songsservlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author smadi
 */
public class SongsServlet extends HttpServlet {

    private SongsManager songsManager;

    @Override
    public void init() {
        if (songsManager == null) {
            songsManager = new SongsManager(getInitParameter("DBPath") + "songs.json");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            if (request.getParameter("all") != null) {
                out.print(songsManager.SongsToJSON());
            } else if (songsManager.isInteger(request.getParameter("songId"))) {
                String song = songsManager.getSongAsJSON(Integer.parseInt(request.getParameter("songId")));
                if (song != null) {
                    out.print(song);
                } else {
                    out.print("Song Not Found");
                }
            }
            out.flush();
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     *
     *  //{id:20, title:" Stop the Feeling", artist:"Justin Timberlake",
     * album:"Trolls", released:2016}
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Song newSong = null;
            synchronized (songsManager) {
                newSong = songsManager.addSong(request.getReader());
            }
            if (newSong != null) {
                response.setStatus(HttpServletResponse.SC_FOUND);
                response.setHeader("Location", "/songsServlet?songId=" + newSong.getId());
            } else {
                try (PrintWriter out = response.getWriter()) {
                    out.print("Song Title is Missing");
                    out.flush();
                }
            }
        } catch (Exception e) {
            try (PrintWriter out = response.getWriter()) {
                out.print("That is not a valid Song JSON Format: " + e.getMessage());
                out.flush();
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            songsManager.writeSongsToFile();
        } catch (IOException ex) {
            Logger.getLogger(SongsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setSongsManager(SongsManager songsManager) {
        this.songsManager = songsManager;
    }

    public SongsManager getSongsManager() {
        return songsManager;
    }

}
