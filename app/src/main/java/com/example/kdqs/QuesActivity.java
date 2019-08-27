package com.example.kdqs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class QuesActivity extends AppCompatActivity {

    Button buttonGQ;
    Button buttonGA;
    TextView textViewGQ;
    EditText editTextGQ;
    TextView textViewGA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques);

        buttonGQ = findViewById(R.id.button6);
        textViewGQ = findViewById(R.id.textView5);
        editTextGQ = findViewById(R.id.editText);
        buttonGA = findViewById(R.id.button7);
        textViewGA = findViewById(R.id.textView4);

        textViewGQ.setText("");
        editTextGQ.setText("");
        textViewGA.setText("");

        textViewGA.setVisibility(TextView.INVISIBLE);

        buttonGQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random r = new Random();
                int ranNum = r.nextInt(99 - 0) + 1;
                int ranop = r.nextInt(5 ) ;
                String question = "";
                String operation = "";

                int result=0;
                String fresult="";

                if (ranop==0) {
                      question= ranNum+","+(ranNum+ranNum)+","+(ranNum+ranNum+ranNum)+","+(ranNum+ranNum+ranNum+ranNum)+",";
                      result=ranNum+ranNum+ranNum+ranNum+ranNum;
                    operation = "Addtion";
                }
                if (ranop==1) {
                     question= ranNum+","+(ranNum+ranNum)+","+(ranNum+ranNum+ranNum)+","+(ranNum+ranNum+ranNum+ranNum)+",";
                    result=ranNum+ranNum+ranNum+ranNum+ranNum;
                    operation = "Addtion";
                }
                if (ranop==2) {
                     question= ranNum+","+(ranNum+ranNum)+","+(ranNum+ranNum+ranNum)+","+(ranNum+ranNum+ranNum+ranNum)+",";
                    result=ranNum+ranNum+ranNum+ranNum+ranNum;
                    operation = "Addtion";
                }
                if (ranop==3) {
                     question= ranNum+","+(ranNum+ranNum)+","+(ranNum+ranNum+ranNum)+","+(ranNum+ranNum+ranNum+ranNum)+",";
                    result=ranNum+ranNum+ranNum+ranNum+ranNum;
                    operation = "Addtion";
                }
                if (ranop==4) {
                     question= ranNum+","+(ranNum+ranNum)+","+(ranNum+ranNum+ranNum)+","+(ranNum+ranNum+ranNum+ranNum)+",";
                    result=ranNum+ranNum+ranNum+ranNum+ranNum;
                    operation = "Addtion";
                }

                textViewGQ.setText(question);
                fresult=Integer.toString(result);
                textViewGA.setText(fresult + "  You need to "+operation);
                textViewGA.setVisibility(TextView.INVISIBLE);
            }
        });



        buttonGA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewGA.setVisibility(TextView.VISIBLE);

            }
        });
    }
}
