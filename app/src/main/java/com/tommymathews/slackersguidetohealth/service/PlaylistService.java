package com.tommymathews.slackersguidetohealth.service;

import android.content.Context;

import com.tommymathews.slackersguidetohealth.model.Playlist;

import java.util.List;

/**
 * Created by gregs on 5/6/2017.
 */

public interface PlaylistService {
    public void addPlaylist(Playlist p);

    public Playlist getPlaylistById(String id);

    public List<Playlist> getAllPlaylists();
}
