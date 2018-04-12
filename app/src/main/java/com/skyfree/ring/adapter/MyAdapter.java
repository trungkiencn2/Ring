package com.skyfree.ring.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.skyfree.ring.R;
import com.skyfree.ring.activity.SetupActivity;
import com.skyfree.ring.fragment.FragmentRingTone;
import com.skyfree.ring.model.Song;
import com.skyfree.ring.utils.Utils;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by KienBeu on 4/11/2018.
 */

public class MyAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Song> mListSong;
    private MediaPlayer mPlayer;
    private Realm mRealm;
    private ArrayList<Song> mRealmList;

    public MyAdapter(Context mContext, ArrayList<Song> mListSong) {
        this.mContext = mContext;
        this.mListSong = mListSong;
        Realm.init(mContext);
        mRealm = Realm.getDefaultInstance();
        mRealmList = new ArrayList<>();
        getListSong();

    }

    private void getListSong(){
        mRealmList = Utils.getAllSong(mRealm);
    }

    @Override
    public int getCount() {
        return mListSong.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mRow = inflater.inflate(R.layout.item_music, null);
        TextView mTvNameOfSong = (TextView) mRow.findViewById(R.id.tv_name_song_item_music);
        Song song = mListSong.get(position);
        mTvNameOfSong.setText(song.getName());
        mRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPlayer != null){
                    mPlayer.stop();
                    mPlayer.release();
                    mPlayer = MediaPlayer.create(mContext, mRealmList.get(position).getId());
                    mPlayer.start();
                }else {
                    mPlayer = MediaPlayer.create(mContext, mRealmList.get(position).getId());
                    mPlayer.start();
                }
                Intent intent = new Intent(mContext, SetupActivity.class);
                intent.putExtra(Utils.INDEX_SONG, position);
                mContext.startActivity(intent);
            }
        });

        return mRow;
    }




}
