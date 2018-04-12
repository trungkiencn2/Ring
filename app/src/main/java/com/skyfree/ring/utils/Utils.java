package com.skyfree.ring.utils;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import com.skyfree.ring.model.Song;

import java.io.File;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by KienBeu on 4/11/2018.
 */

public class Utils {
    public static final String POSITION = "POSITION";
    public static final int PERMISSION_ID = 1;
    public static final String INDEX_SONG = "INDEX_SONG";

    public static void insertSong(Realm realm, final Song song){
        if(!checkSongExist(realm, song.getId())){
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insert(song);
                }
            });
        }
    }

    public static boolean checkSongExist(Realm realm, int id) {
        RealmResults<Song> mListSong = realm.where(Song.class).findAll();
        for (int i = 0; i < mListSong.size(); i++) {
            if (id == (mListSong.get(i).getId())) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Song> getAllSong(Realm realm) {

        ArrayList<Song> mListSong = new ArrayList<>();
        RealmResults<Song> mResult = realm.where(Song.class).findAll();
        for(int i = 0; i<mResult.size(); i++){
            mListSong.add(mResult.get(i));
        }

        return mListSong;
    }
}
