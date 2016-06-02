package com.michalik.steg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
/*
@TODO
podpięcie nowych metod
startowanie z galerii "otwórz jako..."
refaktoryzacja kodu
 */
public class StartMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
    }
    public void selectHideActivity(View view){
        Intent intent = new Intent(this, HidingActivity.class);
        startActivity(intent);
    }
    public void selectAboutActivity(View view){
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }
    public void selectShowActivity(View view){
        Intent intent = new Intent(this, DecodeAndShow.class);
        startActivity(intent);
    }
}

