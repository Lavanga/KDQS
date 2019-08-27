package com.example.kdqs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    Button buttonFA;
    Button buttonTQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        buttonFA = findViewById(R.id.buttonFA);
        buttonTQ = findViewById(R.id.buttonTQ);



        buttonFA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainMenu.this, MainActivity.class);
                MainMenu.this.startActivity(myIntent);

            }
        });



        buttonTQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainMenu.this, QuesActivity.class);
                MainMenu.this.startActivity(myIntent);

            }
        });



    }


}
