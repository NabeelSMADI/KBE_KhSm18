/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htw.ai.kbe.songsrx.restserver.storage;

import de.htw.ai.kbe.songsrx.restserver.bean.Song;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smadi
 */
public interface SongsDAO {
    
     public Song addSong(Song song);

     public List<Song> getAllSongs() ;

     public Song getSong(int id) ;

    public boolean updateSong(Song song) ;
    
     public boolean delSong(int id) ;
    
     public void preDestroy() ;
    
}
