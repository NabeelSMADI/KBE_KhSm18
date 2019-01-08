/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htw.ai.kbe.songsrx.restserver.storage;

import de.htw.ai.kbe.songsrx.restserver.bean.SongList;
import de.htw.ai.kbe.songsrx.restserver.bean.User;
import java.util.List;

/**
 *
 * @author smadi
 */
public interface SongListDAO {

    public List<SongList> getAllSongLists();

    public List<SongList> getAllSongListsByUserId(User user,boolean isOwner);

    public SongList getSongListById(int id);

    public SongList addSongList(SongList songList);

    public boolean delSongList(int id);

}
