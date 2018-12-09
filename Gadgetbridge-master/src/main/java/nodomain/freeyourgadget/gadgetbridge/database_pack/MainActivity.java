package nodomain.freeyourgadget.gadgetbridge.database_pack;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nodomain.freeyourgadget.gadgetbridge.R;

public class MainActivity extends AppCompatActivity {

    TextInputEditText videoUrlView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
         videoUrlView=(TextInputEditText)findViewById(R.id.videoUrlView);
        videoUrlView.setText("http://185.105.239.235:1010/blood_pressure_measurement.mp4");
        //videoUrlView.setText(R.string.hardcoded_video_url_2);
        Button btnPlay=(Button)findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPlay();
            }
        });
    }

    void onClickPlay() {
        String videoUrl = videoUrlView.getText().toString().trim();
        if(videoUrl.isEmpty() || !videoUrl.startsWith("http")) {
            Toast.makeText(this, R.string.error_invalid_input, Toast.LENGTH_LONG).show();
            return;
        }
        startActivity(PlayerActivity.getStartingIntent(this,videoUrl));
    }




    public void deleteCache() {
        try {
            File dir = getCacheDir();
            deleteDir(dir);
        } catch (Exception ignored) {}
    }

    public boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else
            return dir != null && dir.isFile() && dir.delete();
    }

    class DeleteTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        @Override
        protected void onPreExecute() {
            progressDialog.setIndeterminate(true);
            progressDialog.setTitle(getString(R.string.deleting_videos));
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... nothing) {
            deleteCache();
            return null;
        }

        @Override
        protected void onPostExecute(Void nothing) {
            progressDialog.dismiss();
        }
    };
}
