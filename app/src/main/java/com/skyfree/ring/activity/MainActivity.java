package com.skyfree.ring.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.skyfree.ring.adapter.MyAdapter;
import com.skyfree.ring.R;
import com.skyfree.ring.adapter.PageAdapter;
import com.skyfree.ring.model.Song;
import com.skyfree.ring.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTapLayout;
    private ViewPager mPager;
    private Realm mRealm;

    private ArrayList<String> mListName;
    private ArrayList<Integer> mListResourceId;
    private ArrayList<String> mListPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        mRealm = Realm.getDefaultInstance();
        initView();
        addPermission();
        addEvent();
    }

    private void initView() {
        mTapLayout = (TabLayout) findViewById(R.id.tab_layout);
        mPager = (ViewPager) findViewById(R.id.view_pager);
    }

    private void addEvent() {
        FragmentManager manager = getSupportFragmentManager();
        PageAdapter adapter = new PageAdapter(manager, this);
        mPager.setAdapter(adapter);
        mTapLayout.setTabTextColors(Color.BLACK, Color.WHITE);
        mTapLayout.setupWithViewPager(mPager);
        listRaw();
        saveAs(mListResourceId, mListName);
        addDataToRealm();
    }

    private void addPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                addEvent();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_SETTINGS}, Utils.PERMISSION_ID);
            }
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            addEvent();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(this)) {
                // Do stuff here
            } else {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }

    public boolean saveAs(ArrayList<Integer> resSoundId, ArrayList<String> fileName) {
        mListPath = new ArrayList<>();
        byte[] buffer = null;
        for(int i = 0; i<resSoundId.size(); i++){
            InputStream fIn = getBaseContext().getResources().openRawResource(resSoundId.get(i));
            int size = 0;
            try {
                size = fIn.available();
                buffer = new byte[size];
                fIn.read(buffer);
                fIn.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                return false;
            }
            String path = "/mnt/sdcard/yourapp/temp/";
            boolean exists = (new File(path)).exists();
            if (!exists) {
                new File(path).mkdirs();
            }
            mListPath.add(path + fileName.get(i));
            FileOutputStream save;
            try {
                save = new FileOutputStream(path + fileName.get(i));
                save.write(buffer);
                save.flush();
                save.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                return false;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                return false;
            }
        }
        return true;
    }

    public void listRaw(){
        mListName = new ArrayList<>();
        mListResourceId = new ArrayList<>();
        Field[] fields=R.raw.class.getFields();
        for(int count=0; count < fields.length; count++){
            mListName.add(fields[count].getName());
            try {
                mListResourceId.add(fields[count].getInt(fields[count]));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void addDataToRealm(){
        for(int i = 0; i<mListName.size(); i++){
            Utils.insertSong(mRealm, new Song(mListResourceId.get(i), mListName.get(i), mListPath.get(i)));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
