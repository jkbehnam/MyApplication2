package com.example.behnam.myapplication.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.behnam.myapplication.R;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class about_page_text extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page_text);
        simulateDayNight(/* DAY */ 0);



        View aboutPage = new AboutPage(this)
                .isRTL(true)
                .setDescription("نرم افزار تپش")
                .setImage(R.mipmap.ic_launcher)
                .addItem(new Element().setTitle("Version 0.1"))
                .addGroup("ارتباط با ما")
                .addEmail("beigzadeh4behnam@gmail.com")

                .addWebsite("")
               // .addFacebook("the.medy")
               // .addTwitter("medyo80")
               // .addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
               // .addPlayStore("com.ideashower.readitlater.pro")
                .addInstagram("behnam4b")
               // .addGitHub("medyo")
                .addItem(getCopyRightsElement())
                .create();

        setContentView(aboutPage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        final String copyrights = String.format("تمامی حقوق مادی و معنوی این محصول متعلق به شرکت تپش میباشد", Calendar.getInstance().get(Calendar.YEAR));
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(about_page_text.this, copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }

    void simulateDayNight(int currentSetting) {
        final int DAY = 0;
        final int NIGHT = 1;
        final int FOLLOW_SYSTEM = 3;

        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        if (currentSetting == DAY && currentNightMode != Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        } else if (currentSetting == NIGHT && currentNightMode != Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else if (currentSetting == FOLLOW_SYSTEM) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
