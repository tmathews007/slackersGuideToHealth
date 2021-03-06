package com.tommymathews.slackersguidetohealth.service.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.tommymathews.slackersguidetohealth.DependencyFactory;
import com.tommymathews.slackersguidetohealth.model.Fitness;
import com.tommymathews.slackersguidetohealth.model.Playlist;
import com.tommymathews.slackersguidetohealth.service.PlaylistService;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by gregs on 5/6/2017.
 */

public class SQLitePlaylistService implements PlaylistService {
    private SQLiteDatabase database;
    private Context context;

    public SQLitePlaylistService(Context context) {
        database = new DbHelper(context).getWritableDatabase();
    }

    //adds a playlist to the table in the database
    @Override
    public void addPlaylist(Playlist p) {
        ContentValues contentValues = getContentValues(p );
        Playlist pList = getPlaylistById(p.getId());
        if ( pList == null ) {
            database.insert(DbSchema.PlayListTable.PLAYLIST_NAME, null, contentValues);

        } else {
            database.update(DbSchema.PlayListTable.PLAYLIST_NAME, contentValues, DbSchema.PlayListTable.Columns.ID + "=?", new String[] {p.getId()});

        }
    }

    //returns a playlist via an id number
    @Override
    public Playlist getPlaylistById(String id) {
        if (id == null)
            return null;

        List<Playlist> p = queryPlaylists(DbSchema.PlayListTable.Columns.ID, new String[]{id}, null);

        for (Playlist playlist : p) {
            if (playlist.getId().equals(id))
                return playlist;
        }
        return null;
    }

    //Returns a list of all playlists in the database from greatest to least likes
    @Override
    public List<Playlist> getAllPlaylists() {
        List<Playlist> playlists = queryPlaylists(null, null, null);
        Collections.sort(playlists);
        Collections.reverse(playlists);

        return playlists;
    }

    //returns a list of playlists that satisfy the criteria
    private List<Playlist> queryPlaylists(String whereClause, String[] whereArgs, String orderBy) {
        List<Playlist> playlists = new ArrayList<Playlist>();
        if (whereClause != null)
            whereClause = whereClause + "=?";

        Cursor cursor = database.query(DbSchema.PlayListTable.PLAYLIST_NAME, null, whereClause, whereArgs, null, null, orderBy);
        SQLitePlaylistService.PlaylistCursorWrapper wrapper = new SQLitePlaylistService.PlaylistCursorWrapper(cursor);

        try {
            wrapper.moveToFirst();
            while (!wrapper.isAfterLast()) {
                playlists.add(wrapper.getPlayList());
                wrapper.moveToNext();
            }

        } finally {
            cursor.close();
            wrapper.close();
        }

        return playlists;
    }


    //sets all the content values from a playlist to the table
    private ContentValues getContentValues(Playlist p) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DbSchema.PlayListTable.Columns.ID, p.getId());
        contentValues.put(DbSchema.PlayListTable.Columns.NAME, p.getName());
        contentValues.put(DbSchema.PlayListTable.Columns.THUMBNAIL, p.getThumbNail().getPath());
        contentValues.put(DbSchema.PlayListTable.Columns.LIKES, p.getLikes());
        contentValues.put(DbSchema.PlayListTable.Columns.FITNESS_1, p.getPlaylistDB()[0].getId());
        contentValues.put(DbSchema.PlayListTable.Columns.FITNESS_2, p.getPlaylistDB()[1].getId());
        contentValues.put(DbSchema.PlayListTable.Columns.FITNESS_3, p.getPlaylistDB()[2].getId());
        contentValues.put(DbSchema.PlayListTable.Columns.FITNESS_4, p.getPlaylistDB()[3].getId());
        contentValues.put(DbSchema.PlayListTable.Columns.FITNESS_5, p.getPlaylistDB()[4].getId());
        contentValues.put(DbSchema.PlayListTable.Columns.FITNESS_6, p.getPlaylistDB()[5].getId());
        contentValues.put(DbSchema.PlayListTable.Columns.FITNESS_7, p.getPlaylistDB()[6].getId());
        contentValues.put(DbSchema.PlayListTable.Columns.FITNESS_8, p.getPlaylistDB()[7].getId());
        contentValues.put(DbSchema.PlayListTable.Columns.FITNESS_9, p.getPlaylistDB()[8].getId());
        contentValues.put(DbSchema.PlayListTable.Columns.FITNESS_10, p.getPlaylistDB()[9].getId());

        return contentValues;
    }

    //Cursor wrapper that can get the top values off the table and returns a playlist
    private class PlaylistCursorWrapper extends CursorWrapper {
        Context context;

        public PlaylistCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public Playlist getPlayList() {
            String id = getString(getColumnIndex(DbSchema.PlayListTable.Columns.ID));
            String name = getString(getColumnIndex(DbSchema.PlayListTable.Columns.NAME));
            int likes = getInt(getColumnIndex(DbSchema.PlayListTable.Columns.LIKES));
            File thumbNail = new File(getString(getColumnIndex(DbSchema.PlayListTable.Columns.THUMBNAIL)));
            List<Fitness> playlist = new ArrayList<Fitness>();
            if (getString(getColumnIndex(DbSchema.PlayListTable.Columns.FITNESS_1)) != null ) {
                playlist.add(DependencyFactory.getFitnessService(context).getFitnessById(getString(getColumnIndex(DbSchema.PlayListTable.Columns.FITNESS_1))));
            }
            if (getString(getColumnIndex(DbSchema.PlayListTable.Columns.FITNESS_2)) != null ) {
                playlist.add(DependencyFactory.getFitnessService(context).getFitnessById(getString(getColumnIndex(DbSchema.PlayListTable.Columns.FITNESS_2))));
            }
            if (getString(getColumnIndex(DbSchema.PlayListTable.Columns.FITNESS_3)) != null ) {
                playlist.add(DependencyFactory.getFitnessService(context).getFitnessById(getString(getColumnIndex(DbSchema.PlayListTable.Columns.FITNESS_3))));
            }
            if (getString(getColumnIndex(DbSchema.PlayListTable.Columns.FITNESS_4)) != null ) {
                playlist.add(DependencyFactory.getFitnessService(context).getFitnessById(getString(getColumnIndex(DbSchema.PlayListTable.Columns.FITNESS_4))));
            }
            if (getString(getColumnIndex(DbSchema.PlayListTable.Columns.FITNESS_5)) != null ) {
                playlist.add(DependencyFactory.getFitnessService(context).getFitnessById(getString(getColumnIndex(DbSchema.PlayListTable.Columns.FITNESS_5))));
            }
            if (getString(getColumnIndex(DbSchema.PlayListTable.Columns.FITNESS_6)) != null ) {
                playlist.add(DependencyFactory.getFitnessService(context).getFitnessById(getString(getColumnIndex(DbSchema.PlayListTable.Columns.FITNESS_6))));
            }
            if (getString(getColumnIndex(DbSchema.PlayListTable.Columns.FITNESS_7)) != null ) {
                playlist.add(DependencyFactory.getFitnessService(context).getFitnessById(getString(getColumnIndex(DbSchema.PlayListTable.Columns.FITNESS_7))));
            }
            if (getString(getColumnIndex(DbSchema.PlayListTable.Columns.FITNESS_8)) != null ) {
                playlist.add(DependencyFactory.getFitnessService(context).getFitnessById(getString(getColumnIndex(DbSchema.PlayListTable.Columns.FITNESS_8))));
            }
            if (getString(getColumnIndex(DbSchema.PlayListTable.Columns.FITNESS_9)) != null ) {
                playlist.add(DependencyFactory.getFitnessService(context).getFitnessById(getString(getColumnIndex(DbSchema.PlayListTable.Columns.FITNESS_9))));
            }
            if (getString(getColumnIndex(DbSchema.PlayListTable.Columns.FITNESS_10)) != null ) {
                playlist.add(DependencyFactory.getFitnessService(context).getFitnessById(getString(getColumnIndex(DbSchema.PlayListTable.Columns.FITNESS_10))));
            }

            Playlist retlist = new Playlist(name, thumbNail, playlist);

            retlist.setId(id);
            retlist.setLikes(likes);

            return retlist;
        }
    }
}
