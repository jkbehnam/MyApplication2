
package com.example.behnam.myapplication.activities.Fragment_mental;

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
import com.example.behnam.myapplication.activities.MainActivity;
import com.example.behnam.myapplication.connect_to_server.URLs;
import com.example.behnam.myapplication.database_pack.Utils;
import com.example.behnam.myapplication.objects.AudioItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Fragment_mental_voices extends Fragment {
    private AudioItemAdapter audioItemAdapter;
    View rootView;
    private MediaPlayer mediaPlayer;
    // POJO to hold data about audio items


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.exercise_fragment_mental_voice, container, false);

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv);

        // arrange cells in vertical column
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        // add 256 stub audio items
        ArrayList<AudioItem> audioItems = new ArrayList<>();
        {
            AudioItem a = new AudioItem();
            a.setTitle("مقدمه آموزش آرام\u200Cسازی");
            a.setName("05-Relaxation_Training_Moghadameh.mp3");
            a.setPath("/data/data/com.example.behnam.myapplication/audio/");
            a.setUrl(URLs.ROOT_URL+"/05-Relaxation_Training_Moghadameh.mp3");
            audioItems.add(a);


        }
        {
            AudioItem a = new AudioItem();
            a.setTitle("آرام\u200Cسازی عضلانی");
            a.setName("06-Relaxation_Training_Technique.mp3");
            a.setPath("/data/data/com.example.behnam.myapplication/audio/");
            a.setUrl(URLs.ROOT_URL+"/06-Relaxation_Training_Technique.mp3");
            audioItems.add(a);


        }
        {
            AudioItem a = new AudioItem();
            a.setTitle("آرام\u200Cسازی تصویر سازی ذهنی");
            a.setName("final2.mp3");
            a.setPath("/data/data/com.example.behnam.myapplication/audio/");
            a.setUrl(URLs.ROOT_URL+"/final2.mp3");
            audioItems.add(a);


        }
        audioItemAdapter = new AudioItemAdapter(audioItems);
        rv.setAdapter(audioItemAdapter);
        return rootView;
    }

    private class AudioItemAdapter extends RecyclerView.Adapter<AudioItemAdapter.AudioItemsViewHolder> implements Handler.Callback {
        Context context;
        private static final int MSG_UPDATE_SEEK_BAR = 1845;

        private Handler uiUpdateHandler;
        private List<AudioItem> audioItems;
        private int playingPosition;
        private AudioItemsViewHolder playingHolder;

        AudioItemAdapter(List<AudioItem> audioItems) {
            this.audioItems = audioItems;
            this.playingPosition = -1;
            uiUpdateHandler = new Handler(this);
        }

        @Override
        public AudioItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            this.context = parent.getContext();
            return new AudioItemsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voice, parent, false));
        }

        @Override
        public void onBindViewHolder(AudioItemsViewHolder holder, int position) {
            if (position == playingPosition) {
                playingHolder = holder;
                // this view holder corresponds to the currently playing audio cell
                // update its view to show playing progress
                updatePlayingView();
            } else {
                // and this one corresponds to non playing
                updateNonPlayingView(holder);
            }

            File file = new File(Utils.getRootDirPath(context) + "/" + audioItems.get(position).getName());
            if (file.exists()) {
                holder.im_download.setVisibility(View.GONE);
                holder.textViewProgressOne.setVisibility(View.GONE);
                holder.la.setClickable(true);
            } else {
                holder.im_download.setVisibility(View.VISIBLE);
                holder.la.setClickable(false);
            }
//Do something

// Do something else.
            holder.tvIndex.setText(String.format(Locale.US, "%d", position));
            holder.tv_title_audio.setText(audioItems.get(position).getTitle());
            holder.im_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PRDownloader.initialize((context));
                    download(context, holder.textViewProgressOne, holder.im_download, audioItems.get(position).getUrl(), audioItems.get(position).getName(), holder.la);
                }
            });
        }

        @Override
        public int getItemCount() {
            return audioItems.size();
        }

        @Override
        public void onViewRecycled(AudioItemsViewHolder holder) {
            super.onViewRecycled(holder);
            if (playingPosition == holder.getAdapterPosition()) {
                // view holder displaying playing audio cell is being recycled
                // change its state to non-playing
                updateNonPlayingView(playingHolder);
                playingHolder = null;
            }
        }

        /**
         * Changes the view to non playing state
         * - icon is changed to play arrow
         * - seek bar disabled
         * - remove seek bar updater, if needed
         *
         * @param holder ViewHolder whose state is to be chagned to non playing
         */
        private void updateNonPlayingView(AudioItemsViewHolder holder) {
            if (holder == playingHolder) {
                uiUpdateHandler.removeMessages(MSG_UPDATE_SEEK_BAR);
            }
            holder.sbProgress.setEnabled(false);
            holder.sbProgress.setProgress(0);
            holder.la.setFrame(52);
        }

        /**
         * Changes the view to playing state
         * - icon is changed to pause
         * - seek bar enabled
         * - start seek bar updater, if needed
         */
        private void updatePlayingView() {
            playingHolder.sbProgress.setMax(mediaPlayer.getDuration());
            playingHolder.sbProgress.setProgress(mediaPlayer.getCurrentPosition());
            playingHolder.sbProgress.setEnabled(true);
            if (mediaPlayer.isPlaying()) {
                uiUpdateHandler.sendEmptyMessageDelayed(MSG_UPDATE_SEEK_BAR, 100);
                playingHolder.la.setFrame(20);

            } else {
                uiUpdateHandler.removeMessages(MSG_UPDATE_SEEK_BAR);
                playingHolder.la.setFrame(52);


            }
        }

        void stopPlayer() {
            if (null != mediaPlayer) {
                releaseMediaPlayer();
            }
        }

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_SEEK_BAR: {
                    playingHolder.sbProgress.setProgress(mediaPlayer.getCurrentPosition());
                    uiUpdateHandler.sendEmptyMessageDelayed(MSG_UPDATE_SEEK_BAR, 100);
                    return true;
                }
            }
            return false;
        }

        // Interaction listeners e.g. click, seekBarChange etc are handled in the view holder itself. This eliminates
        // need for anonymous allocations.
        class AudioItemsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
            SeekBar sbProgress;
            TextView tvIndex, tv_title_audio, textViewProgressOne;
            ImageView im_download;
            LottieAnimationView la;

            AudioItemsViewHolder(View itemView) {
                super(itemView);
                la = (LottieAnimationView) itemView.findViewById(R.id.ivPlayPause);
                //  la.setScale(5f);
                la.setFrame(52);

                la.setOnClickListener(this);
                sbProgress = (SeekBar) itemView.findViewById(R.id.sbProgress);
                sbProgress.setOnSeekBarChangeListener(this);
                tvIndex = (TextView) itemView.findViewById(R.id.tvIndex);
                tv_title_audio = (TextView) itemView.findViewById(R.id.tv_title_audio);
                textViewProgressOne = (TextView) itemView.findViewById(R.id.textViewProgressOne);
                im_download = (ImageView) itemView.findViewById(R.id.iv_download_audio);

            }

            @Override
            public void onClick(View v) {
                if (getAdapterPosition() == playingPosition) {
                    // toggle between play/pause of audio
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    } else {

                        mediaPlayer.start();
                    }
                } else {
                    // start another audio playback
                    playingPosition = getAdapterPosition();
                    if (mediaPlayer != null) {
                        if (null != playingHolder) {
                            updateNonPlayingView(playingHolder);
                        }
                        mediaPlayer.release();
                    }
                    playingHolder = this;
                    startMediaPlayer(audioItems.get(playingPosition).getName());
                }
                updatePlayingView();
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        }

        private void startMediaPlayer(String audioResId) {
            mediaPlayer = new MediaPlayer();

            //  mediaPlayer = MediaPlayer.create(MainActivity.mainContext, Uri.parse(Environment.getExternalStorageDirectory().getPath()+ "/hichkas.mp3"));
            mediaPlayer = MediaPlayer.create(MainActivity.mainContext, Uri.parse(Utils.getRootDirPath(context) + "/" + audioResId));


            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    releaseMediaPlayer();
                }
            });
            mediaPlayer.start();
        }

        private void releaseMediaPlayer() {
            if (null != playingHolder) {
                updateNonPlayingView(playingHolder);
            }
            mediaPlayer.release();
            mediaPlayer = null;
            playingPosition = -1;
        }

    }

    public void download(Context context, TextView tvprog, ImageView dl, String url, String name, LottieAnimationView la) {

        int downloadId = PRDownloader.download(url, Utils.getRootDirPath(context), name)
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {

                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {

                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {

                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                        tvprog.setText(Utils.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));

                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        Toast.makeText(context, "دانلود تمام شد", Toast.LENGTH_SHORT).show();
                        dl.setVisibility(View.INVISIBLE);
                        tvprog.setVisibility(View.INVISIBLE);
                        la.setClickable(true);
                    }

                    @Override
                    public void onError(Error error) {
                        Toast.makeText(context, "اشکال در دانلود", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
                if (mediaPlayer != null) {
                    mediaPlayer.pause();
                }
            } else {
                // do what you like
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }
    @Override
    public void onResume() {
        super.onResume();

        if (getView() == null) {
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    Fragment fragment = new Fragment_home();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.add(R.id.container, fragment);
                    transaction.commit();
                    return true;
                }
                return false;
            }
        });
    }
}