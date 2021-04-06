package com.example.pizzaneck;

import android.graphics.Color;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Graph extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph);
        setToolbar();

        BarChart barChart = findViewById(R.id.graph_total);

        ArrayList<BarEntry> visitors = new ArrayList<>();
        visitors.add(new BarEntry(17,8));
        visitors.add(new BarEntry(18,3));

        visitors.add(new BarEntry(20,4));
        visitors.add(new BarEntry(21,7));

        visitors.add(new BarEntry(23,9));
        visitors.add(new BarEntry(24,3));

        visitors.add(new BarEntry(26,3));
        visitors.add(new BarEntry(27,5));

        visitors.add(new BarEntry(29,12));
        visitors.add(new BarEntry(30,6));

        visitors.add(new BarEntry(32,8));
        visitors.add(new BarEntry(33,1));

        visitors.add(new BarEntry(35,5));
        visitors.add(new BarEntry(36,8));


        ArrayList date = new ArrayList();
        date.add("mar,02");
        date.add("");

        date.add("mar,03");
        date.add("");

        date.add("mar,04");
        date.add("");

        date.add("mar,05");
        date.add("");

        date.add("mar,06");
        date.add("");

        date.add("mar,07");
        date.add("");

        date.add("mar,08");
        date.add("");


        BarDataSet barDataSet = new BarDataSet(visitors, "");
        barChart.animateY(5000);
        barDataSet.setColors(ColorTemplate.rgb("#091772"),ColorTemplate.rgb("#AF2525"));
        barDataSet.setValueTextColor(ColorTemplate.rgb("#104D9E"));
        barDataSet.setDrawValues(false);
        barDataSet.setValueTextSize(0f);
        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setDrawGridBackground(false);
        barChart.setData(barData);
        barChart.getAxisRight().setDrawGridLines(true);
        barChart.getAxisLeft().setLabelCount(6);



    }

    /* 툴바 및 툴바기능 설정 함수.
     * onCreate에서 호출
     * 클래스 내 DrawerLayout drawerLayout; NavigationView navView; Toolbar toolbar; 선언 필요
     */
    protected void setToolbar(){
        //툴바 설정
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);    //기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true);      //뒤로가기 버튼 생성. 이 버튼을 메뉴바 버튼으로 사용
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu); //뒤로가기 버튼 아이콘 -> 메뉴 아이콘 변경


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
                    //액티비티 스택제거 -> 메인에서는 뒤로가기 누르면 이전 액티비티로 가지 않고 종료됨.
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else if(id == R.id.realtime){
                    Intent intent = new Intent(getApplicationContext(), Realtime.class);
                    startActivity(intent);
                }
                else if(id == R.id.gallery){
                    Intent intent = new Intent(getApplicationContext(), Gallery.class);
                    startActivity(intent);

                }
                else if(id == R.id.stretching){
                    Intent intent = new Intent(getApplicationContext(), Stretching.class);
                    startActivity(intent);
                }
                else if(id == R.id.graph){
                    Intent intent = new Intent(getApplicationContext(), Gallery.class);
                    startActivity(intent);
                }

                menuItem.setChecked(false);
                return true;
            }
        });
    }
    //툴바 우측에 버튼 생성 (설정버튼)
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_option_btn, menu);
        return true;
    }

    //툴바에 버튼 클릭 시
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            //네비게이션드로어
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            //설정버튼
            case R.id.setting:
                Intent intent = new Intent(getApplicationContext(), Setting.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    //네비게이션 열려있을때 뒤로가기로 버튼 닫기
    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}