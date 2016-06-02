package com.michalik.steg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/*
dwa wejścia: z poziomu aplikacji oraz z poziomu wyboru zdjęcia

 */
public class DecodeAndShow extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int RESULT_LOAD_IMAGE = 1;
    private Bitmap bitmap;
    private String decodedText;

    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private int col;
    private int canal;

    ProgressDialog pd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decode_and_show);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup2);

    }

    public void applyDecoding(View view){

        //this.col = Integer.parseInt(editText3.getText().toString());

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

            if(bitmap!=null){
                new Decoding().execute();
            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(), "First select image from gallery or take a picture", Toast.LENGTH_SHORT);
                toast.show();
            }


        /*DecodeImage decodeImage = new DecodeImage(bitmap, col, canal);
        decodeImage.decodeTextFromBitmap();
        this.decodedText = decodeImage.getDecodedText();*/


    }

    public class Decoding extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(DecodeAndShow.this);
            pd.setMessage("loading");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DecodeTextBinary decodeTextBinary = new DecodeTextBinary(bitmap);
            decodeTextBinary.decodeBitmap(canal);
            decodedText=decodeTextBinary.getDecoded();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pd.dismiss();
            TextView textView = (TextView) findViewById(R.id.textView7);
            textView.setText(decodedText);
        }
    }

    public void selectImage(View view) {
        Intent intent = new Intent();
// Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            bitmap = BitmapFactory.decodeFile(picturePath);
        }
    }

}
