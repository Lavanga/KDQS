package com.example.kdqs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    static final int GALLERY_REQUEST = 244;

    Bitmap bitmap;
    String currentPhotoPath;
    String imageText;

    Button button;
    Button buttonGall;
    Button buttonTest;

    Button buttonCamera;
    Button buttonKeyboard;
    Button buttonClear;

    TextView textView;
    TextView textView2;

    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        buttonGall = findViewById(R.id.button5);
        buttonTest = findViewById(R.id.buttonTest);
        buttonCamera = findViewById(R.id.button2);
        buttonKeyboard = findViewById(R.id.button3);
        buttonClear = findViewById(R.id.button4);
        editText = (EditText) findViewById(R.id.inputText);
        textView = findViewById(R.id.textView);
        textView.setText("");
        textView2 = findViewById(R.id.textView3);
        textView2.setText("");

        button.setVisibility(View.INVISIBLE);
        buttonGall.setVisibility(View.INVISIBLE);
        buttonTest.setVisibility(View.INVISIBLE);
        editText.setVisibility(View.INVISIBLE);
        buttonClear.setVisibility(View.INVISIBLE);
        //textView.setVisibility(View.INVISIBLE);


        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setVisibility(View.VISIBLE);
                buttonGall.setVisibility(View.VISIBLE);
                buttonTest.setVisibility(View.INVISIBLE);
                buttonClear.setVisibility(View.INVISIBLE);
                editText.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.VISIBLE);
                textView.setText("");


            }
        });

        buttonKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setVisibility(View.INVISIBLE);
                buttonGall.setVisibility(View.INVISIBLE);
                buttonTest.setVisibility(View.VISIBLE);
                buttonClear.setVisibility(View.VISIBLE);
                editText.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                textView.setText("");
            }
        });


// send camera button intent
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();

            }

        });

        buttonGall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dispatchTakeGallPictureIntent();

            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editText.setText("");
                textView.setText("");
            }
        });


        // predict next
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputText = editText.getText().toString();

                if (inputText == "") {
                    Toast.makeText(getApplicationContext(), "Input is Empty", Toast.LENGTH_SHORT).show();
                }
                int countCommas = 0;
                int countNumbers = 0;
                for (int i = 0; i < inputText.length(); i++) {
                    if (inputText.charAt(i) == ',') countCommas++;
                }
                String words[] = inputText.split(",");

                if (countCommas == 3 & words.length == 4) {
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), 0);
                    imageText = editText.getText().toString();
                    Log.e("test", "Image to Text = " + imageText);


                    List<String> myIntList = Arrays.asList(imageText.split("\\s*,\\s*"));


                    boolean allIntergerCheck = true;

                    for (String strObject : myIntList) {
                        if(isInteger(strObject)){
                            allIntergerCheck = true;
                        }else{
                            allIntergerCheck = false;
                            break;
                        }
                    }


                    if (allIntergerCheck) {

                        String Opera = "";
                        boolean add = false;
                        boolean sub = false;
                        boolean mul = false;
                        boolean div = false;
                        boolean cons = false;

                        boolean add1 = false;
                        boolean sub1 = false;
                        boolean mul1 = false;
                        boolean div1 = false;
                        boolean cons1 = false;

                        boolean add2 = false;
                        boolean sub2 = false;
                        boolean mul2 = false;
                        boolean div2 = false;
                        boolean cons2 = false;

                        int n = 0;
                        int cosst = 0;

                        int listSize = myIntList.size();

                        for (String x : myIntList) {
                            Log.e("test", "loop" + x);
                        }

                        if (Integer.valueOf(myIntList.get(n)) + Integer.valueOf(myIntList.get(n + 1)) == Integer.valueOf(myIntList.get(n + 2))) {
                            add1 = true;
                            Opera = "Addtion";
                            Log.e("test", "operation +");
                        } else if (Integer.valueOf(myIntList.get(n)) - Integer.valueOf(myIntList.get(n + 1)) == Integer.valueOf(myIntList.get(n + 2))) {
                            sub1 = true;
                            Opera = "Subtraction";
                            Log.e("test", "operation -");
                        } else if (Integer.valueOf(myIntList.get(n)) * Integer.valueOf(myIntList.get(n + 1)) == Integer.valueOf(myIntList.get(n + 2))) {
                            mul1 = true;
                            Opera = "Multiplication";
                            Log.e("test", "operation *");
                        } else if (Integer.valueOf(myIntList.get(n)) / Integer.valueOf(myIntList.get(n + 1)) == Integer.valueOf(myIntList.get(n + 2))) {
                            div1 = true;
                            Opera = "Division";
                            Log.e("test", "operation /");
                        } else if (Integer.valueOf(myIntList.get(n)) - Integer.valueOf(myIntList.get(n + 1)) == (Integer.valueOf(myIntList.get(n + 1)) - Integer.valueOf(myIntList.get(n + 2)))) {
                            cons1 = true;
                            Opera = "Constant";
                            cosst = Integer.valueOf(myIntList.get(n + 1)) - Integer.valueOf(myIntList.get(n));
                            //Log.e("test", "operation cons value " +cosst );
                            Log.e("test", "operation cons");
                        }

                        n = 1;
                        if (Integer.valueOf(myIntList.get(n)) + Integer.valueOf(myIntList.get(n + 1)) == Integer.valueOf(myIntList.get(n + 2))) {
                            add2 = true;
                            Opera = "Addtion";
                            Log.e("test", "operation +");
                        } else if (Integer.valueOf(myIntList.get(n)) - Integer.valueOf(myIntList.get(n + 1)) == Integer.valueOf(myIntList.get(n + 2))) {
                            sub2 = true;
                            Opera = "Subtraction";
                            Log.e("test", "operation -");
                        } else if (Integer.valueOf(myIntList.get(n)) * Integer.valueOf(myIntList.get(n + 1)) == Integer.valueOf(myIntList.get(n + 2))) {
                            mul2 = true;
                            Opera = "Multiplication";
                            Log.e("test", "operation *");
                        } else if (Integer.valueOf(myIntList.get(n)) / Integer.valueOf(myIntList.get(n + 1)) == Integer.valueOf(myIntList.get(n + 2))) {
                            div2 = true;
                            Opera = "Division";
                            Log.e("test", "operation /");
                        } else if (Integer.valueOf(myIntList.get(n)) - Integer.valueOf(myIntList.get(n + 1)) == (Integer.valueOf(myIntList.get(n + 1)) - Integer.valueOf(myIntList.get(n + 2)))) {
                            cons2 = true;
                            Opera = "Constant";
                            cosst = Integer.valueOf(myIntList.get(n + 1)) - Integer.valueOf(myIntList.get(n));
                            //Log.e("test", "operation cons value " +cosst );
                            Log.e("test", "operation cons");
                        }


                        if (add1 = add2) {
                            add = true;
                        }
                        if (sub1 = sub2) {
                            sub = true;
                        }
                        if (mul1 = mul2) {
                            mul = true;
                        }
                        if (div1 = div2) {
                            div = true;
                        }
                        if (cons1 = cons2) {
                            cons = true;
                        }


                        int nextVlaue = 0;
                        if (add) {
                            nextVlaue = Integer.valueOf(myIntList.get(myIntList.size() - 2)) + Integer.valueOf(myIntList.get(myIntList.size() - 1));
                        }
                        if (sub) {
                            nextVlaue = Integer.valueOf(myIntList.get(myIntList.size() - 2)) - Integer.valueOf(myIntList.get(myIntList.size() - 1));
                        }
                        if (mul) {
                            nextVlaue = Integer.valueOf(myIntList.get(myIntList.size() - 2)) * Integer.valueOf(myIntList.get(myIntList.size() - 1));
                        }
                        if (div) {
                            nextVlaue = Integer.valueOf(myIntList.get(myIntList.size() - 2)) / Integer.valueOf(myIntList.get(myIntList.size() - 1));
                        }
                        if (cons) {
                            nextVlaue = (Integer.valueOf(myIntList.get(myIntList.size() - 1)) + cosst);
                        }

                        Log.e("test", "next value is " + nextVlaue);

                        textView.setText("The next value is : " + nextVlaue);
                        textView2.setText("Operation is : " + Opera);

                    } else {
                        Toast.makeText(getApplicationContext(), "Input format ERROR - Can only contain Integer", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Input format ERROR - Enter 4 numbers separated by . Eg:- 2,4,6,10 ", Toast.LENGTH_SHORT).show();
                }


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

    private void dispatchTakeGallPictureIntent() {
        Intent takePictureIntent = new Intent(Intent.ACTION_PICK);
        takePictureIntent.setType("image/*");
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
                startActivityForResult(takePictureIntent, GALLERY_REQUEST);
            }
        }
    }


    // camera image part
    /// after camera intent
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
                                            imageText = firebaseVisionText.getText();
                                            Log.e("test", "Image to Text = " + imageText);


                                            if (imageText == "") {
                                                Toast.makeText(getApplicationContext(), "Input is Empty", Toast.LENGTH_SHORT).show();
                                            }
                                            int countCommas = 0;
                                            int countNumbers = 0;
                                            for (int i = 0; i < imageText.length(); i++) {
                                                if (imageText.charAt(i) == ',') countCommas++;
                                            }
                                            String words[] = imageText.split(",");

                                            if (countCommas == 3 & words.length == 4) {
                                                //InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                                //imm.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), 0);
                                                //imageText = editText.getText().toString();
                                                Log.e("test", "Image to Text = " + imageText);

                                                List<String> myIntList = Arrays.asList(imageText.split("\\s*,\\s*"));

                                                String Opera = "";


                                                boolean allIntergerCheck = true;

                                                for (String strObject : myIntList) {
                                                    if(isInteger(strObject)){
                                                        allIntergerCheck = true;
                                                    }else{
                                                        allIntergerCheck = false;
                                                        break;
                                                    }
                                                }


                                                if (allIntergerCheck) {

                                                    boolean add = false;
                                                    boolean sub = false;
                                                    boolean mul = false;
                                                    boolean div = false;
                                                    boolean cons = false;

                                                    boolean add1 = false;
                                                    boolean sub1 = false;
                                                    boolean mul1 = false;
                                                    boolean div1 = false;
                                                    boolean cons1 = false;

                                                    boolean add2 = false;
                                                    boolean sub2 = false;
                                                    boolean mul2 = false;
                                                    boolean div2 = false;
                                                    boolean cons2 = false;

                                                    int n = 0;
                                                    int cosst = 0;

                                                    int listSize = myIntList.size();

                                                    for (String x : myIntList) {
                                                        Log.e("test", "loop" + x);
                                                    }

                                                    if (Integer.valueOf(myIntList.get(n)) + Integer.valueOf(myIntList.get(n + 1)) == Integer.valueOf(myIntList.get(n + 2))) {
                                                        add = true;
                                                        Opera = "Addtion";
                                                        Log.e("test", "operation +");
                                                    } else if (Integer.valueOf(myIntList.get(n)) - Integer.valueOf(myIntList.get(n + 1)) == Integer.valueOf(myIntList.get(n + 2))) {
                                                        sub = true;
                                                        Opera = "Subtraction";
                                                        Log.e("test", "operation -");
                                                    } else if (Integer.valueOf(myIntList.get(n)) * Integer.valueOf(myIntList.get(n + 1)) == Integer.valueOf(myIntList.get(n + 2))) {
                                                        mul = true;
                                                        Opera = "Multiplication";
                                                        Log.e("test", "operation *");
                                                    } else if (Integer.valueOf(myIntList.get(n)) / Integer.valueOf(myIntList.get(n + 1)) == Integer.valueOf(myIntList.get(n + 2))) {
                                                        div = true;
                                                        Opera = "Division";
                                                        Log.e("test", "operation /");
                                                    } else if (Integer.valueOf(myIntList.get(n)) - Integer.valueOf(myIntList.get(n + 1)) == (Integer.valueOf(myIntList.get(n + 1)) - Integer.valueOf(myIntList.get(n + 2)))) {
                                                        cons = true;
                                                        Opera = "Constant";
                                                        cosst = Integer.valueOf(myIntList.get(n + 1)) - Integer.valueOf(myIntList.get(n));
                                                        Log.e("test", "operation cons");
                                                    }

                                                    n = 1;
                                                    if (Integer.valueOf(myIntList.get(n)) + Integer.valueOf(myIntList.get(n + 1)) == Integer.valueOf(myIntList.get(n + 2))) {
                                                        add2 = true;
                                                        Opera = "Addtion";
                                                        Log.e("test", "operation +");
                                                    } else if (Integer.valueOf(myIntList.get(n)) - Integer.valueOf(myIntList.get(n + 1)) == Integer.valueOf(myIntList.get(n + 2))) {
                                                        sub2 = true;
                                                        Opera = "Subtraction";
                                                        Log.e("test", "operation -");
                                                    } else if (Integer.valueOf(myIntList.get(n)) * Integer.valueOf(myIntList.get(n + 1)) == Integer.valueOf(myIntList.get(n + 2))) {
                                                        mul2 = true;
                                                        Opera = "Multiplication";
                                                        Log.e("test", "operation *");
                                                    } else if (Integer.valueOf(myIntList.get(n)) / Integer.valueOf(myIntList.get(n + 1)) == Integer.valueOf(myIntList.get(n + 2))) {
                                                        div2 = true;
                                                        Opera = "Division";
                                                        Log.e("test", "operation /");
                                                    } else if (Integer.valueOf(myIntList.get(n)) - Integer.valueOf(myIntList.get(n + 1)) == (Integer.valueOf(myIntList.get(n + 1)) - Integer.valueOf(myIntList.get(n + 2)))) {
                                                        cons2 = true;
                                                        Opera = "Constant";
                                                        cosst = Integer.valueOf(myIntList.get(n + 1)) - Integer.valueOf(myIntList.get(n));
                                                        //Log.e("test", "operation cons value " +cosst );
                                                        Log.e("test", "operation cons");
                                                    }


                                                    if (add1 = add2) {
                                                        add = true;
                                                    }
                                                    if (sub1 = sub2) {
                                                        sub = true;
                                                    }
                                                    if (mul1 = mul2) {
                                                        mul = true;
                                                    }
                                                    if (div1 = div2) {
                                                        div = true;
                                                    }
                                                    if (cons1 = cons2) {
                                                        cons = true;
                                                    }

                                                    int nextVlaue = 0;
                                                    if (add) {
                                                        nextVlaue = Integer.valueOf(myIntList.get(myIntList.size() - 2)) + Integer.valueOf(myIntList.get(myIntList.size() - 1));
                                                    }
                                                    if (sub) {
                                                        nextVlaue = Integer.valueOf(myIntList.get(myIntList.size() - 2)) - Integer.valueOf(myIntList.get(myIntList.size() - 1));
                                                    }
                                                    if (mul) {
                                                        nextVlaue = Integer.valueOf(myIntList.get(myIntList.size() - 2)) * Integer.valueOf(myIntList.get(myIntList.size() - 1));
                                                    }
                                                    if (div) {
                                                        nextVlaue = Integer.valueOf(myIntList.get(myIntList.size() - 2)) / Integer.valueOf(myIntList.get(myIntList.size() - 1));
                                                    }
                                                    if (cons) {
                                                        nextVlaue = (Integer.valueOf(myIntList.get(myIntList.size() - 1)) + cosst);
                                                    }

                                                    Log.e("test", "next value is " + nextVlaue);

                                                    textView.setText("The next value is : " + nextVlaue);
                                                    textView2.setText("Operation is : " + Opera);
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Input format ERROR - Can only contain Integer", Toast.LENGTH_SHORT).show();
                                                }

                                            } else {
                                                Toast.makeText(getApplicationContext(), "Input format ERROR - Enter 4 numbers separated by . Eg:- 2,4,6,10 ", Toast.LENGTH_SHORT).show();
                                            }
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
// gallary code
            if (requestCode == GALLERY_REQUEST) {
                Uri selectedImage = intent.getData();

                //File file = new File(currentPhotoPath);
                //bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), selectedImage);
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
                                            imageText = firebaseVisionText.getText();
                                            Log.e("test", "Image to Text = " + imageText);


                                            if (imageText == "") {
                                                Toast.makeText(getApplicationContext(), "Input is Empty", Toast.LENGTH_SHORT).show();
                                            }
                                            int countCommas = 0;
                                            int countNumbers = 0;
                                            for (int i = 0; i < imageText.length(); i++) {
                                                if (imageText.charAt(i) == ',') countCommas++;
                                            }
                                            String words[] = imageText.split(",");

                                            Log.e("test", "countCommas - " + countCommas);
                                            Log.e("test", "WORD Count - " + words.length);
                                            if (countCommas == 3 & words.length == 4) {
                                                //InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                                //imm.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), 0);
                                                //imageText = editText.getText().toString();
                                                Log.e("test", "Image to Text = " + imageText);

                                                List<String> myIntList = Arrays.asList(imageText.split("\\s*,\\s*"));

                                                boolean allIntergerCheck = true;

                                                for (String strObject : myIntList) {
                                                    if(isInteger(strObject)){
                                                        allIntergerCheck = true;
                                                    }else{
                                                        allIntergerCheck = false;
                                                        break;
                                                    }
                                                }


                                                if (allIntergerCheck) {

                                                    String Opera = "";

                                                    boolean add = false;
                                                    boolean sub = false;
                                                    boolean mul = false;
                                                    boolean div = false;
                                                    boolean cons = false;

                                                    boolean add1 = false;
                                                    boolean sub1 = false;
                                                    boolean mul1 = false;
                                                    boolean div1 = false;
                                                    boolean cons1 = false;

                                                    boolean add2 = false;
                                                    boolean sub2 = false;
                                                    boolean mul2 = false;
                                                    boolean div2 = false;
                                                    boolean cons2 = false;

                                                    int n = 0;
                                                    int cosst = 0;

                                                    int listSize = myIntList.size();

                                                    for (String x : myIntList) {
                                                        Log.e("test", "loop" + x);
                                                    }

                                                    if (Integer.valueOf(myIntList.get(n)) + Integer.valueOf(myIntList.get(n + 1)) == Integer.valueOf(myIntList.get(n + 2))) {
                                                        add = true;
                                                        Opera = "Addtion";
                                                        Log.e("test", "operation +");
                                                    } else if (Integer.valueOf(myIntList.get(n)) - Integer.valueOf(myIntList.get(n + 1)) == Integer.valueOf(myIntList.get(n + 2))) {
                                                        sub = true;
                                                        Opera = "Subtraction";
                                                        Log.e("test", "operation -");
                                                    } else if (Integer.valueOf(myIntList.get(n)) * Integer.valueOf(myIntList.get(n + 1)) == Integer.valueOf(myIntList.get(n + 2))) {
                                                        mul = true;
                                                        Opera = "Multiplication";
                                                        Log.e("test", "operation *");
                                                    } else if (Integer.valueOf(myIntList.get(n)) / Integer.valueOf(myIntList.get(n + 1)) == Integer.valueOf(myIntList.get(n + 2))) {
                                                        div = true;
                                                        Opera = "Division";
                                                        Log.e("test", "operation /");
                                                    } else if (Integer.valueOf(myIntList.get(n)) - Integer.valueOf(myIntList.get(n + 1)) == (Integer.valueOf(myIntList.get(n + 1)) - Integer.valueOf(myIntList.get(n + 2)))) {
                                                        cons = true;
                                                        Opera = "Constant";
                                                        cosst = Integer.valueOf(myIntList.get(n + 1)) - Integer.valueOf(myIntList.get(n));
                                                        Log.e("test", "operation cons");
                                                    }

                                                    n = 1;
                                                    if (Integer.valueOf(myIntList.get(n)) + Integer.valueOf(myIntList.get(n + 1)) == Integer.valueOf(myIntList.get(n + 2))) {
                                                        add2 = true;
                                                        Opera = "Addtion";
                                                        Log.e("test", "operation +");
                                                    } else if (Integer.valueOf(myIntList.get(n)) - Integer.valueOf(myIntList.get(n + 1)) == Integer.valueOf(myIntList.get(n + 2))) {
                                                        sub2 = true;
                                                        Opera = "Subtraction";
                                                        Log.e("test", "operation -");
                                                    } else if (Integer.valueOf(myIntList.get(n)) * Integer.valueOf(myIntList.get(n + 1)) == Integer.valueOf(myIntList.get(n + 2))) {
                                                        mul2 = true;
                                                        Opera = "Multiplication";
                                                        Log.e("test", "operation *");
                                                    } else if (Integer.valueOf(myIntList.get(n)) / Integer.valueOf(myIntList.get(n + 1)) == Integer.valueOf(myIntList.get(n + 2))) {
                                                        div2 = true;
                                                        Opera = "Division";
                                                        Log.e("test", "operation /");
                                                    } else if (Integer.valueOf(myIntList.get(n)) - Integer.valueOf(myIntList.get(n + 1)) == (Integer.valueOf(myIntList.get(n + 1)) - Integer.valueOf(myIntList.get(n + 2)))) {
                                                        cons2 = true;
                                                        Opera = "Constant";
                                                        cosst = Integer.valueOf(myIntList.get(n + 1)) - Integer.valueOf(myIntList.get(n));
                                                        //Log.e("test", "operation cons value " +cosst );
                                                        Log.e("test", "operation cons");
                                                    }


                                                    if (add1 = add2) {
                                                        add = true;
                                                    }
                                                    if (sub1 = sub2) {
                                                        sub = true;
                                                    }
                                                    if (mul1 = mul2) {
                                                        mul = true;
                                                    }
                                                    if (div1 = div2) {
                                                        div = true;
                                                    }
                                                    if (cons1 = cons2) {
                                                        cons = true;
                                                    }

                                                    int nextVlaue = 0;
                                                    if (add) {
                                                        nextVlaue = Integer.valueOf(myIntList.get(myIntList.size() - 2)) + Integer.valueOf(myIntList.get(myIntList.size() - 1));
                                                    }
                                                    if (sub) {
                                                        nextVlaue = Integer.valueOf(myIntList.get(myIntList.size() - 2)) - Integer.valueOf(myIntList.get(myIntList.size() - 1));
                                                    }
                                                    if (mul) {
                                                        nextVlaue = Integer.valueOf(myIntList.get(myIntList.size() - 2)) * Integer.valueOf(myIntList.get(myIntList.size() - 1));
                                                    }
                                                    if (div) {
                                                        nextVlaue = Integer.valueOf(myIntList.get(myIntList.size() - 2)) / Integer.valueOf(myIntList.get(myIntList.size() - 1));
                                                    }
                                                    if (cons) {
                                                        nextVlaue = (Integer.valueOf(myIntList.get(myIntList.size() - 1)) + cosst);
                                                    }

                                                    Log.e("test", "next value is " + nextVlaue);

                                                    textView.setText("The next value is : " + nextVlaue);
                                                    textView2.setText("Operation is : " + Opera);
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Input format ERROR - Can only contain Integer", Toast.LENGTH_SHORT).show();
                                                }

                                            } else {
                                                Toast.makeText(getApplicationContext(), "Input format ERROR - Enter 4 numbers separated by . Eg:- 2,4,6,10 ", Toast.LENGTH_SHORT).show();
                                            }
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

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }


}
