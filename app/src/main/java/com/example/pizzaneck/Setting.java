package com.example.pizzaneck;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.view.Menu;
import android.view.MenuInflater;
import android.content.Context;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.view.View;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Setting extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navView;
    Toolbar toolbar;
    LinearLayout layout;

    RadioButton button1;
    RadioButton button2;
    RadioButton button3;
    private RadioGroup radioGroup;
    private SharedPreferences appData;
    private SharedPreferences.Editor editor;

    private void load() {
        String alarmSetting = appData.getString("ALARM_SETTING", "");
        switch (alarmSetting) {
            case "SOUND":
                button1.setChecked(true);
                break;
            case "VIBRATE":
                button2.setChecked(true);
                break;
            case "MUTE":
                button3.setChecked(true);
                break;
        }
        String modeSetting = appData.getString("MODE_SETTING","");
        switch (modeSetting){
            case "LIGHT":
                radioGroup.check(R.id.idRBLight);
                break;
            case "DARK":
                radioGroup.check(R.id.idRBDark);
                break;
            case "DEFAULT":
                radioGroup.check(R.id.idRBDefault);
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        setToolbar();

        Button alarm = (Button) findViewById(R.id.alarm);
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),alarm.class);
                startActivity(intent);
            }
        });

        appData = getSharedPreferences("appData", MODE_PRIVATE);
        editor = appData.edit();


        radioGroup = findViewById(R.id.idRGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){
                    case R.id.idRBLight:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        editor.putString("MODE_SETTING","LIGHT");
                        editor.commit();
                        break;
                    case R.id.idRBDark:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        editor.putString("MODE_SETTING","DARK");
                        editor.commit();
                        break;
                    case R.id.idRBDefault:
                        // ??????????????? 10 ??????
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                            editor.putString("MODE_SETTING","DEFAULT");
                            editor.commit();
                        }
                        // ??????????????? 10 ??????
                        else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                        }
                        break;
                }
            }
        });

        button1 = (RadioButton) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(),uri);
                ringtone.play();
                editor.putString("ALARM_SETTING","SOUND");
                editor.commit();
            }
        });

        button2 = (RadioButton) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                if(Build.VERSION.SDK_INT >=26){
                    vibrator.vibrate(VibrationEffect.createOneShot(1000,10));
                }else{
                    vibrator.vibrate(1000);
                }
                editor.putString("ALARM_SETTING","VIBRATE");
                editor.commit();
            }
        });

        button3 = (RadioButton) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(),uri);
                ringtone.stop();
                editor.putString("ALARM_SETTING","MUTE");
                editor.commit();
            }
        });
        load();
    }

    /* ?????? ??? ???????????? ?????? ??????.
     * onCreate?????? ??????
     * ????????? ??? DrawerLayout drawerLayout; NavigationView navView; Toolbar toolbar; ?????? ??????
     */


    protected void setToolbar(){
        //?????? ??????
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);    //?????? title ?????????
        actionBar.setDisplayHomeAsUpEnabled(true);      //???????????? ?????? ??????. ??? ????????? ????????? ???????????? ??????
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu); //???????????? ?????? ????????? -> ?????? ????????? ??????


        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                int id = menuItem.getItemId();

                if(id == R.id.home){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    //???????????? ???????????? -> ??????????????? ???????????? ????????? ?????? ??????????????? ?????? ?????? ?????????.
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else if(id == R.id.realtime){
                    Intent intent = new Intent(getApplicationContext(), ClassifierActivity.class);
                    startActivity(intent);
                }
                else if(id == R.id.stretching){
                    Intent intent = new Intent(getApplicationContext(), Stretching.class);
                    startActivity(intent);
                }
                else if(id == R.id.graph){
                    Intent intent = new Intent(getApplicationContext(), Graph.class);
                    startActivity(intent);
                }

                menuItem.setChecked(false);
                return true;
            }
        });
    }

    //????????? ?????? ?????? ???
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            //????????????????????????
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //??????????????? ??????????????? ??????????????? ?????? ??????
    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }


}

