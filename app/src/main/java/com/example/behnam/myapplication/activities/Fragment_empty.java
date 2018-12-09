
package com.example.behnam.myapplication.activities;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.activities.Fragment_home.Fragment_home;
import com.example.behnam.myapplication.connect_to_server.URLs;
import com.example.behnam.myapplication.database_pack.Utils;
import com.example.behnam.myapplication.objects.AudioItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Fragment_empty extends Fragment {

    // POJO to hold data about audio items


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.exercise_fragment_mental_voice, container, false);


        return rootView;
    }


}