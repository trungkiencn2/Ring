package com.skyfree.ring.fragment;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.skyfree.ring.R;
import com.skyfree.ring.adapter.MyAdapter;
import com.skyfree.ring.model.Song;
import com.skyfree.ring.utils.Utils;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by KienBeu on 4/2/2018.
 */

public class FragmentRingTone extends Fragment{

    private ListView mLvMusic;
    private View mView;
    private static Context mContext;

    private ArrayList<Song> mListSong;
    private MyAdapter mAdapter;
    private MediaPlayer mPlayer;

    private Realm mRealm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_ring_tone, container, false);
        mContext = getContext();
        Realm.init(mContext);
        mRealm = Realm.getDefaultInstance();
        initView();
        addEvent();
        return mView;
    }

    private void initView() {
        mLvMusic = (ListView) mView.findViewById(R.id.lv_music);
    }

    private void addEvent(){
        mListSong = new ArrayList<>();
        mListSong = Utils.getAllSong(mRealm);
        mAdapter = new MyAdapter(mContext, mListSong);
        mLvMusic.setAdapter(mAdapter);
    }

}
