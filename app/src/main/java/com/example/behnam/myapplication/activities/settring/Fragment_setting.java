
package com.example.behnam.myapplication.activities.settring;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.example.behnam.myapplication.activities.Fragment_home.Fragment_AddDailyData;
import com.example.behnam.myapplication.activities.Fragment_home.Fragment_signs;
import com.example.behnam.myapplication.activities.Fragment_mental.Fragment_mental;
import com.example.behnam.myapplication.activities.MainActivity;
import com.example.behnam.myapplication.activities.about_page_text;
import com.example.behnam.myapplication.connect_to_server.URLs;
import com.example.behnam.myapplication.database_pack.DataBase_login;
import com.example.behnam.myapplication.database_pack.Utils;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

import mehdi.sakout.aboutpage.AboutPage;

import static android.content.Context.LOCATION_SERVICE;


public class Fragment_setting extends Fragment  {
    TextView tv_steps, last_loc, tv_about_us, tv_exit,tv_quide;
    JSONObject data = null;
    FragmentActivity c;
    LocationManager locationManager;
    ProgressDialog progressDialog;
    AlertDialog ad;
Boolean fileExists;
    File file;
    protected static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.exercise_fragment_setting, container, false);

        setRetainInstance(true);
        c = getActivity();
        TextView tv_band = (TextView) rootView.findViewById(R.id.tv_setting_band);
        TextView tv_location = (TextView) rootView.findViewById(R.id.tv_choose_city);
        last_loc = (TextView) rootView.findViewById(R.id.txt_last_loc);
        tv_band.setOnClickListener(cv_click1);
        tv_location.setOnClickListener(cv_click1);
        tv_about_us = (TextView) rootView.findViewById(R.id.about_page_txt);
        tv_about_us.setOnClickListener(cv_click1);
        tv_exit = (TextView) rootView.findViewById(R.id.tv_exit);
        tv_exit.setOnClickListener(cv_click1);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        TextView tvToolbarTitle = (TextView) rootView.findViewById(R.id.tvToolbarAllPage);
        tvToolbarTitle.setText("تنظیمات");
tv_quide=(TextView)rootView.findViewById(R.id.guide_page_txt);
        tv_quide.setOnClickListener(cv_click1);
         file = new File(Utils.getRootDirPath(this.getContext()) + "/" +"tapesh_guide.pdf");
        fileExists=file.exists();
        if (fileExists) {
            tv_quide.setText("نمایش فایل راهنما");
        } else {
            tv_quide.setText("دریافت فایل راهنما");
        }
        showcountry();

        return rootView;
    }

    private void showcountry() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c.getApplicationContext());
        String loc = String.valueOf(prefs.getString("last_loc", "(IR Mashhad" +
                ")"));
        last_loc.setText(loc);

    }

    private View.OnClickListener cv_click1 = new View.OnClickListener() {

        @Override
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.tv_setting_band:
                    Intent i = new Intent(c, nodomain.freeyourgadget.gadgetbridge.activities.ControlCenterv2.class);
                    startActivity(i);
                    break;
                case R.id.tv_choose_city:
                    getCityByLocation();
                    break;
                case R.id.about_page_txt:
                    Intent x = new Intent(getActivity(), about_page_text.class);
                    startActivity(x);
                    break;
                case R.id.tv_exit:
                    String message;
                    String btn_cancle = "انصراف";
                    String btn_confirm = "خروج";
                    message = "آیا مطمئن هستید؟";
                    new SweetAlertDialog(c, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                            .setTitleText("خروج از سیستم")
                            .setContentText(message)
                            .setConfirmText(btn_confirm)
                            .setCancelText(btn_cancle)
                            .setCustomImage(R.drawable.logout)
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();

                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    DataBase_login.logout(c);
                                    Intent i = c.getBaseContext().getPackageManager()
                                            .getLaunchIntentForPackage( c.getBaseContext().getPackageName() );
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                }
                            })
                            .show();
                    break;
                case R.id.guide_page_txt:
                    if (fileExists) {
                        if(Build.VERSION.SDK_INT>=24){
                            try{
                                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                                m.invoke(null);
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                        Uri path = Uri.fromFile(file);
                        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                        pdfIntent.setDataAndType(path, "application/pdf");
                        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        try{
                            startActivity(pdfIntent);
                        }catch(ActivityNotFoundException e){
                            Toast.makeText(MainActivity.mainContext, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(c, "دانلود", Toast.LENGTH_SHORT).show();
                        download(MainActivity.mainContext,tv_quide,URLs.ROOT_URL+"/help.pdf","tapesh_guide.pdf");

                    }
                    break;


            }

        }
    };


    void getCityByLocation() {
        locationManager = (LocationManager) c.getSystemService(LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(c,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Explanation not needed, since user requests this themmself

            } else {
                ActivityCompat.requestPermissions(c,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_ACCESS_FINE_LOCATION);

            }

        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Dialog_loaction_loading aa;
            aa = new Dialog_loaction_loading();
            ad = aa.qrcode_reader(c);
            ad.show();
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, loc_lis());
            }
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, loc_lis());
            }
        } else {
            showLocationSettingsDialog();
        }
    }

    private void showLocationSettingsDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(c);
        alertDialog.setTitle("تنظیم موقعیت مکانی");
        alertDialog.setMessage("مکان نمای " +
                "گوشی فعال نیست. آیا میخواید در تنظیمات آن را فعال کنید؟");
        alertDialog.setPositiveButton("تنظیمات", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCityByLocation();
                }
                return;
            }
        }
    }

    public LocationListener loc_lis() {

        LocationListener a = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //  progressDialog.hide();
                //dialoig
                ad.dismiss();

                try {
                    locationManager.removeUpdates(this);
                } catch (SecurityException e) {
                    Log.e("LocationManager", "Error while trying to stop listening for location updates. This is probably a permissions issue", e);
                }
                Log.i("LOCATION (" + location.getProvider().toUpperCase() + ")", location.getLatitude() + ", " + location.getLongitude());
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(c.getApplicationContext());
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                prefsEditor.putFloat("latitude", (float) latitude);
                prefsEditor.putFloat("longitude", (float) longitude);
                prefsEditor.commit();
                getJSON("lat=" + (float) latitude + "&" + "lon=" + (float) longitude);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        return a;
    }

    public void getJSON(final String city) {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected Void doInBackground(Void... params) {
                try {

                    // URL url = new URL("https://samples.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=b6907d289e10d714a6e88b30761fae22");

                    URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=&" + city + "&units=metric&APPID=304dd13d07451ddabcf4cc80cb08824e");


                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    StringBuffer json = new StringBuffer(1024);
                    String tmp = "";

                    while ((tmp = reader.readLine()) != null)
                        json.append(tmp).append("\n");
                    reader.close();

                    data = new JSONObject(json.toString());

                    if (data.getInt("cod") != 200) {
                        System.out.println("Cancelled");
                        return null;
                    }


                } catch (Exception e) {

                    System.out.println("Exception " + e.getMessage());
                    return null;
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void Void) {
                if (data != null) {
                    Log.d("my weather received", data.toString());
                    try {
                        String city = data.getString("name");
                        String country = data.getJSONObject("sys").getString("country");
                        // String temp = data.getString("description");
                        //  String temp =String.valueOf( data.getInt("temp")) ;


                        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(c.getApplicationContext());
                        SharedPreferences.Editor prefsEditor = mPrefs.edit();
                        prefsEditor.putString("last_loc", "(" + country + " " + city + ")");
                        prefsEditor.commit();
                        showcountry();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }.execute();

    }
    public void download(Context context, TextView tvprog, String url, String name) {

        int downloadId = PRDownloader.download(url, Utils.getRootDirPath(context), name)
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {
                        Toast.makeText(context, "دانلود آغاز شد", Toast.LENGTH_SHORT).show();
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

                        tvprog.setText("نمایش فایل راهنما");
                        fileExists=true;

                    }

                    @Override
                    public void onError(Error error) {
                        Toast.makeText(context, "اشکال در دانلود", Toast.LENGTH_SHORT).show();
                        tvprog.setText("دریافت فایل راهنما");
                    }
                });


    }

}