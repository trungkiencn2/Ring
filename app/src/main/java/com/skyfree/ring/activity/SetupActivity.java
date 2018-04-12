package com.skyfree.ring.activity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.skyfree.ring.R;
import com.skyfree.ring.model.Song;
import com.skyfree.ring.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class SetupActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvNameSong;
    private Button mBtnSetRingtone, mBtnSetSMS, mBtnSetContact, mBtnAlarm, mBtnDownload, mBtnBack;

    private Realm mRealm;
    private ArrayList<Song> mListSong;
    private int mIndexSong;
    private int mIndexRingtone, mIndexNotify, mIndexAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        Realm.init(this);
        mRealm = Realm.getDefaultInstance();
        mIndexSong = getIntent().getIntExtra(Utils.INDEX_SONG, 0);
        initView();
        getSong();
    }

    private void initView() {
        mTvNameSong = (TextView) findViewById(R.id.tv_name_song_setup_activity);
        mBtnSetRingtone = (Button) findViewById(R.id.btn_set_ringtone_setup_activity);
        mBtnSetSMS = (Button) findViewById(R.id.btn_set_sms_setup_activity);
        mBtnSetContact = (Button) findViewById(R.id.btn_set_all_setup_activity);
        mBtnAlarm = (Button) findViewById(R.id.btn_set_alarm_setup_activity);
        mBtnDownload = (Button) findViewById(R.id.btn_download_setup_activity);
        mBtnBack = (Button) findViewById(R.id.btn_back_setup_activity);

        mBtnSetRingtone.setOnClickListener(this);
        mBtnSetSMS.setOnClickListener(this);
        mBtnSetContact.setOnClickListener(this);
        mBtnAlarm.setOnClickListener(this);
        mBtnDownload.setOnClickListener(this);
        mBtnBack.setOnClickListener(this);
    }

    private void getSong(){
        mListSong = new ArrayList<>();
        mListSong = Utils.getAllSong(mRealm);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_set_ringtone_setup_activity:
                setDefaultRingtone(mIndexSong);
                Toast.makeText(this, getString(R.string.you_set_ringtone), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_set_sms_setup_activity:
                setDefaultNotify(mIndexSong);
                Toast.makeText(this, getString(R.string.you_set_notify), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_set_all_setup_activity:
                setDefaultAll(mIndexSong);
                Toast.makeText(this, getString(R.string.you_set_all), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_set_alarm_setup_activity:
                setDefaultAlarm(mIndexSong);
                Toast.makeText(this, getString(R.string.you_set_alarm), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_download_setup_activity:
                break;
            case R.id.btn_back_setup_activity:
                finish();
                break;
        }
    }

    private void setDefaultAll(int index) {
        File k = new File("/mnt/sdcard/yourapp/temp/" + mListSong.get(index).getName());

        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DATA, k.getAbsolutePath());
        values.put(MediaStore.MediaColumns.TITLE, mListSong.get(index).getName());
        values.put(MediaStore.MediaColumns.SIZE, 215454);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
        values.put(MediaStore.Audio.Media.ARTIST, "Madonna");
        values.put(MediaStore.Audio.Media.DURATION, 2346);
        values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
        values.put(MediaStore.Audio.Media.IS_ALARM, true);
        values.put(MediaStore.Audio.Media.IS_MUSIC, true);

        Uri uri = MediaStore.Audio.Media.getContentUriForPath(k.getAbsolutePath());
        getContentResolver().delete(
                uri,
                MediaStore.MediaColumns.DATA + "=\""
                        + k.getAbsolutePath() + "\"", null);
        Uri newUri = this.getContentResolver().insert(uri, values);

        RingtoneManager.setActualDefaultRingtoneUri(
                this,
                RingtoneManager.TYPE_ALL,
                newUri
        );
    }

    private void setDefaultAlarm(int index) {
        File k = new File("/mnt/sdcard/yourapp/temp/" + mListSong.get(index).getName());

        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DATA, k.getAbsolutePath());
        values.put(MediaStore.MediaColumns.TITLE, mListSong.get(index).getName());
        values.put(MediaStore.MediaColumns.SIZE, 215454);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
        values.put(MediaStore.Audio.Media.ARTIST, "Madonna");
        values.put(MediaStore.Audio.Media.DURATION, 2346);
        values.put(MediaStore.Audio.Media.IS_RINGTONE, false);
        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
        values.put(MediaStore.Audio.Media.IS_ALARM, true);
        values.put(MediaStore.Audio.Media.IS_MUSIC, false);

        Uri uri = MediaStore.Audio.Media.getContentUriForPath(k.getAbsolutePath());
        getContentResolver().delete(
                uri,
                MediaStore.MediaColumns.DATA + "=\""
                        + k.getAbsolutePath() + "\"", null);
        Uri newUri = this.getContentResolver().insert(uri, values);

        RingtoneManager.setActualDefaultRingtoneUri(
                this,
                RingtoneManager.TYPE_ALARM,
                newUri
        );
    }

    private void setDefaultNotify(int index) {
        File k = new File("/mnt/sdcard/yourapp/temp/" + mListSong.get(index).getName());

        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DATA, k.getAbsolutePath());
        values.put(MediaStore.MediaColumns.TITLE, mListSong.get(index).getName());
        values.put(MediaStore.MediaColumns.SIZE, 215454);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
        values.put(MediaStore.Audio.Media.ARTIST, "Madonna");
        values.put(MediaStore.Audio.Media.DURATION, 2345);
        values.put(MediaStore.Audio.Media.IS_RINGTONE, false);
        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
        values.put(MediaStore.Audio.Media.IS_ALARM, false);
        values.put(MediaStore.Audio.Media.IS_MUSIC, false);

        Uri uri = MediaStore.Audio.Media.getContentUriForPath(k.getAbsolutePath());
        getContentResolver().delete(
                uri,
                MediaStore.MediaColumns.DATA + "=\""
                        + k.getAbsolutePath() + "\"", null);
        Uri newUri = this.getContentResolver().insert(uri, values);

        RingtoneManager.setActualDefaultRingtoneUri(
                this,
                RingtoneManager.TYPE_NOTIFICATION,
                newUri
        );
    }

    private void setDefaultRingtone(int index) {

        File k = new File("/mnt/sdcard/yourapp/temp/" + mListSong.get(index).getName());

        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DATA, k.getAbsolutePath());
        values.put(MediaStore.MediaColumns.TITLE, mListSong.get(index).getName());
        values.put(MediaStore.MediaColumns.SIZE, 215454);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
        values.put(MediaStore.Audio.Media.ARTIST, "Madonna");
        values.put(MediaStore.Audio.Media.DURATION, 2345);
        values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
        values.put(MediaStore.Audio.Media.IS_ALARM, false);
        values.put(MediaStore.Audio.Media.IS_MUSIC, false);

        Uri uri = MediaStore.Audio.Media.getContentUriForPath(k.getAbsolutePath());
        getContentResolver().delete(
                uri,
                MediaStore.MediaColumns.DATA + "=\""
                        + k.getAbsolutePath() + "\"", null);
        Uri newUri = this.getContentResolver().insert(uri, values);

        RingtoneManager.setActualDefaultRingtoneUri(
                this,
                RingtoneManager.TYPE_RINGTONE,
                newUri
        );

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}

