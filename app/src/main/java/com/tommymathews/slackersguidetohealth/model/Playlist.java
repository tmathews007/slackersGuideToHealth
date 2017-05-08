package com.tommymathews.slackersguidetohealth.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by gregs on 4/29/2017.
 */

public class Playlist implements Comparable<Playlist>{
    private Fitness[] playlist = {null, null, null, null, null, null, null, null, null, null};
    private File thumbNail;
    private String name = "";
    private String id;
    private int likes;

    public Playlist (String n, File image, List<Fitness> lst) {
        name = n;
        likes = 0;
        id = UUID.randomUUID().toString();
        thumbNail = image;

        int i = 0;
        while (i < lst.size() && i < playlist.length) {
            playlist[i] = lst.get(i);
            ++i;
        }
    }

    public Playlist (String n, File image, Fitness[] lst) {
        name = n;
        likes = 0;
        id = UUID.randomUUID().toString();
        thumbNail = image;
        int i = 0;
        while (i < lst.length && i < playlist.length) {
            playlist[i] = lst[i];
            ++i;
        }
    }

    public void setThumbNail(File i) {
        thumbNail = i;
    }

    public void setLikes(int i) {
        likes = i;
    }

    public void setId(String i) {
        id = i;
    }

    public void setPlaylistDB(Fitness[] temp) {
        int i = 0;
        while (i < temp.length) {
            playlist[i] = temp[i];
        }

        while (i < playlist.length) {
            playlist[i] = null;
        }
    }

    public void setPlaylist(List<Fitness> lst) {

        int i = 0;
        while (i < lst.size() && i < playlist.length) {
            playlist[i] = lst.get(i);
            ++i;
        }
    }

    public void addFitnessToPlayList(Fitness in) {
        int i = 0;
        while (i < playlist.length) {
            if (playlist[i] == null) {
                playlist[i] = in;
                break;
            }
            ++i;
        }
    }

    public void removeFitnessFromPlayList(Fitness in) {
        int i = 0;
        while (i < playlist.length) {
            if (playlist[i] == in) {
                playlist[i] = null;
                break;
            }
            ++i;
        }

        while (i < playlist.length) {
            playlist[i] = playlist[i + 1];
            ++i;
        }

        playlist[i] = null;
    }

    public void setPlayListName(String n){
        name = n;
    }

    public void addLike() {
        ++likes;
    }

    public void subtractLike() {
        --likes;
    }

    public Bitmap getThumbNailBitmap() {
        return BitmapFactory.decodeFile(thumbNail.getPath());
    }

    public File getThumbNail() {
        return thumbNail;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLikes() {
        return likes;
    }

    public Fitness[] getPlaylistDB() {
        return playlist;
    }

    public List<Fitness> getList() {
        ArrayList<Fitness> temp = new ArrayList<>();
        int i = 0;
        while (i < playlist.length) {
            temp.add(playlist[i]);
            ++i;
        }
        return temp;
    }

    @Override
    public int compareTo(@NonNull Playlist o) {
        if (likes == o.getLikes()) {
            return 0;
        } else if (likes > o.getLikes()){
            return 1;
        } else {
            return -1;
        }
    }
}
