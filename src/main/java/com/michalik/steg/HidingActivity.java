package com.michalik.steg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class HidingActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private EditText editText;

    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;

    private Bitmap outBitmap;

    private static final int CAMERA_REQUEST = 1888;
    private boolean isSaved;
    public int canal;
    public String text;
    ProgressDialog pd;
    private String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiding);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        editText = (EditText) findViewById(R.id.editText); //string to hide

        isSaved=false;
    }

    public void hideButton(View view) {
        String text = "";
        int canal;


        this.text = editText.getText().toString();

        int id = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(id);
        String can = radioButton.getText().toString();
        switch (can) {
            case "Red":
                canal = 1;
                break;
            case "Green":
                canal = 2;
                break;
            case "Blue":
                canal = 3;
                break;
            default:
                canal = 1;
        }
        this.canal=canal;
        if(bitmap!=null){
            new HideTextASCII().execute();
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), "First select image from gallery or take a picture", Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    public class HideTextASCII extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(HidingActivity.this);
            pd.setMessage("loading");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            CodeTextBinary codeTextBinary = new CodeTextBinary(bitmap, text, canal);


                codeTextBinary.codeBinaryToBitmap();
                outBitmap=codeTextBinary.getOutBitmap();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pd.dismiss();
            Toast toast = Toast.makeText(getApplicationContext(), "Hiding done, now save or code another text.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }



    public void captureImage(View view){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
        //http://stackoverflow.com/questions/10377783/low-picture-image-quality-when-capture-from-camera
    }

    public void selectImage(View view) {
        Intent intent = new Intent();

        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");

        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                //ImageView imageView = (ImageView) findViewById(R.id.imageView);
                //imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendData(View view) {
        shareImage();
    }

    private void shareImage() {
        Intent share = new Intent(Intent.ACTION_SEND);

        share.setType("file/"); //as file, so we don't have compression

        String imagePath = path;

        try{
            if(!isSaved){
                save();
            }
            File imageFileToShare = new File(imagePath);
            Uri uri = Uri.fromFile(imageFileToShare);
            share.putExtra(Intent.EXTRA_STREAM, uri);

            startActivity(Intent.createChooser(share, "Share Image!"));
        }
        catch (NullPointerException e1){
            Toast toast = Toast.makeText(getApplicationContext(), "Sharing failed, first save image!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void save(View view)     {

        String filePath;
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());


        filePath = Environment.getExternalStorageDirectory().getAbsolutePath();



        File file = new File(filePath, "DCIM/"+timeStamp+".png");
        this.path=file.getAbsolutePath();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);

            outBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            if (out != null) {
                out.close();

                MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
                Toast toast = Toast.makeText(getApplicationContext(), "Saved"+timeStamp+".png", Toast.LENGTH_SHORT);
                toast.show();
                isSaved=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(getApplicationContext(), "Saving failed", Toast.LENGTH_SHORT);
            toast.show();
            isSaved=false;
        }
    }
    public void save(){

        String filePath;
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());


        filePath = Environment.getExternalStorageDirectory().getAbsolutePath();



        File file = new File(filePath, "DCIM/"+timeStamp+".png");
        this.path=file.getAbsolutePath();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);

            outBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            if (out != null) {
                out.close();

                MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
                Toast toast = Toast.makeText(getApplicationContext(), "Saved"+timeStamp+".png", Toast.LENGTH_SHORT);
                toast.show();
                isSaved=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(getApplicationContext(), "Saving failed", Toast.LENGTH_SHORT);
            toast.show();
            isSaved=false;
        }
    }
}
