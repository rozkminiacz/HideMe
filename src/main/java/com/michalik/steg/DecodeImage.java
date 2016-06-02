package com.michalik.steg;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by michalik on 25.04.16.
 */
public class DecodeImage {
    private Bitmap bitmap;
    private int col;
    private int canal;
    private String text;
    public DecodeImage(Bitmap bitmap, int col, int canal){
        this.bitmap=bitmap;
        this.col=col;
        this.canal=canal;
    }



    public void decodeTextFromBitmap(){

        for(int i=0; i<100; i++){
            int k=30;
            switch (canal){

                case 1:
                    text+=(char)(Color.red(bitmap.getPixel(bitmap.getWidth()-i-1, bitmap.getHeight()-col))+k);
                    //text += (char)(bitmap.getPixel(bitmap.getWidth()-i-1, bitmap.getHeight()-col) >> 16 ) & 0xff;

                    break;
                case 2:
                    text+=(char) (Color.green(bitmap.getPixel(bitmap.getWidth()-i-1, bitmap.getHeight()-col))+k);
                    break;
                case 3:
                    text+=(char) (Color.blue(bitmap.getPixel(bitmap.getWidth()-i-1, bitmap.getHeight()-col))+k);
                    break;
                default:
                    text+=(char) (Color.red(bitmap.getPixel(bitmap.getWidth()-i-1, bitmap.getHeight()-col))+k);
                    break;
            }

        }
    }
    public String getDecodedText(){
        return text;
    }

}
