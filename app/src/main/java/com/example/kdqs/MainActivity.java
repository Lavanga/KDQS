package com.example.kdqs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentText;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentTextRecognizer;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 1;

    Bitmap bitmap;
    String currentPhotoPath;
    String imageText;

    Button button;
    Button buttonTest;

    TextView textView;


    EditText editText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        buttonTest = findViewById(R.id.buttonTest);
        editText= (EditText) findViewById(R.id.inputText);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();

            }

        });

        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                                        imageText=editText.getText().toString();
                                        Log.e("test", "Image to Text" + imageText);

                                        List<String> myIntList = Arrays.asList(imageText.split("\\s*,\\s*"));

                                        boolean add=false;
                                        boolean sub=false;
                                        boolean mul=false;
                                        boolean div=false;
                                        int n=0;

                                        int listSize=myIntList.size();

                                        for(String x:myIntList){Log.e("test", "loop" + x);}

                                        if(Integer.valueOf(myIntList.get(n)) + Integer.valueOf(myIntList.get(n+1) ) == Integer.valueOf(myIntList.get(n+2))){
                                            add=true;
                                            Log.e("test", "operation +" );
                                        }else if(Integer.valueOf(myIntList.get(n)) - Integer.valueOf(myIntList.get(n+1) ) == Integer.valueOf(myIntList.get(n+2))){
                                            sub=true;
                                            Log.e("test", "operation -" );
                                        }else if(Integer.valueOf(myIntList.get(n)) * Integer.valueOf(myIntList.get(n+1) ) == Integer.valueOf(myIntList.get(n+2))){
                                            mul=true;
                                            Log.e("test", "operation *" );
                                        }else if(Integer.valueOf(myIntList.get(n)) / Integer.valueOf(myIntList.get(n+1) ) == Integer.valueOf(myIntList.get(n+2))){
                                            div=true;
                                            Log.e("test", "operation /" );
                                        }

                                        int nextVlaue=0;
                                        if(add){nextVlaue=Integer.valueOf(myIntList.get(myIntList.size()-2))+ Integer.valueOf(myIntList.get(myIntList.size()-1));}
                                        if(sub){nextVlaue=Integer.valueOf(myIntList.get(myIntList.size()-2))- Integer.valueOf(myIntList.get(myIntList.size()-1));}
                                        if(mul){nextVlaue=Integer.valueOf(myIntList.get(myIntList.size()-2))* Integer.valueOf(myIntList.get(myIntList.size()-1));}
                                        if(div){nextVlaue=Integer.valueOf(myIntList.get(myIntList.size()-2))/ Integer.valueOf(myIntList.get(myIntList.size()-1));}

                                        Log.e("test", "next value is " + nextVlaue);

                                        textView.setText("The next value is - "+nextVlaue);

            }

        });

    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        Log.e("test", "path for file--" + currentPhotoPath);
        return image;
    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.kdqs", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        try {
            if (requestCode == REQUEST_TAKE_PHOTO) {

                File file = new File(currentPhotoPath);
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                if (bitmap != null) {
                    Log.e("test", "file loaded successfully");
                    FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
                    // uncomment for on-device processing
                    FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();

                    // uncomment for cloud processing
                    //FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance().getCloudTextRecognizer();

                    Task<FirebaseVisionText> result =
                            detector.processImage(image)
                                    .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                                        @Override
                                        public void onSuccess(FirebaseVisionText firebaseVisionText) {
                                            Log.e("test", "image to text  successfully");
                                            imageText=firebaseVisionText.getText();
                                            Log.e("test", "Image to Text" + imageText);

                                            List<String> myIntList = Arrays.asList(imageText.split("\\s*,\\s*"));

                                            boolean add=false;
                                            boolean sub=false;
                                            boolean mul=false;
                                            boolean div=false;
                                            int n=0;

                                            int listSize=myIntList.size();

                                            for(String x:myIntList){Log.e("test", "loop" + x);}

                                            if(Integer.valueOf(myIntList.get(n)) + Integer.valueOf(myIntList.get(n+1) ) == Integer.valueOf(myIntList.get(n+2))){
                                                 add=true;
                                                Log.e("test", "operation +" );
                                            }else if(Integer.valueOf(myIntList.get(n)) - Integer.valueOf(myIntList.get(n+1) ) == Integer.valueOf(myIntList.get(n+2))){
                                                sub=true;
                                                Log.e("test", "operation -" );
                                            }else if(Integer.valueOf(myIntList.get(n)) * Integer.valueOf(myIntList.get(n+1) ) == Integer.valueOf(myIntList.get(n+2))){
                                                mul=true;
                                                Log.e("test", "operation *" );
                                            }else if(Integer.valueOf(myIntList.get(n)) / Integer.valueOf(myIntList.get(n+1) ) == Integer.valueOf(myIntList.get(n+2))){
                                                div=true;
                                                Log.e("test", "operation /" );
                                            }

                                            int nextVlaue=0;
                                            if(add){nextVlaue=Integer.valueOf(myIntList.get(myIntList.size()-2))+ Integer.valueOf(myIntList.get(myIntList.size()-1));}
                                            if(sub){nextVlaue=Integer.valueOf(myIntList.get(myIntList.size()-2))- Integer.valueOf(myIntList.get(myIntList.size()-1));}
                                            if(mul){nextVlaue=Integer.valueOf(myIntList.get(myIntList.size()-2))* Integer.valueOf(myIntList.get(myIntList.size()-1));}
                                            if(div){nextVlaue=Integer.valueOf(myIntList.get(myIntList.size()-2))/ Integer.valueOf(myIntList.get(myIntList.size()-1));}

                                            Log.e("test", "next value is " + nextVlaue);

                                            textView.setText("The next value is - "+nextVlaue);

                                        }
                                    })
                                    .addOnFailureListener(
                                            new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Task failed with an exception
                                                }
                                            });
                }
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }




}
