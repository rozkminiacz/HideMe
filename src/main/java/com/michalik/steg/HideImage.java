package com.michalik.steg;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.io.UnsupportedEncodingException;

/**
 * Created by michalik on 17.04.16.
 */
public class HideImage {
    private String text;
    private Bitmap bitmap;
    private int canal;
    private int col;
    public HideImage(String text, Bitmap bitmap, int canal, int col){
        if(bitmap==null){
            throw new NullPointerException();
        }
        this.bitmap=bitmap;
        this.text=text;
        this.canal=canal;
        this.col=col;
    }


    public void codeTextBitmapDifferent() throws UnsupportedEncodingException, IndexOutOfBoundsException{
        byte[] b = text.getBytes("ASCII");
        Bitmap outbitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        for(int i=0; i<b.length; i++){
            b[i]+=100;
            int k=bitmap.getWidth()-i;
            int j=bitmap.getHeight()-i;
            int color;
            switch (canal){
                case 1:
                    color = Color.argb(Color.alpha(bitmap.getPixel(k, j)), b[i], Color.green(bitmap.getPixel(k, j)), Color.blue(bitmap.getPixel(k, j)));
                    break;
                case 2:
                    color = Color.argb(Color.alpha(bitmap.getPixel(k, j)), Color.red(bitmap.getPixel(k, j)), b[i], Color.blue(bitmap.getPixel(k, j)));
                    break;
                case 3:
                    color = Color.argb(Color.alpha(bitmap.getPixel(k, j)), Color.red(bitmap.getPixel(k, j)), Color.green(bitmap.getPixel(k, j)), b[i]);
                    break;
                default:
                    color=Color.argb(Color.alpha(bitmap.getPixel(k, j)), b[i], b[i], b[i]);
                    break;
            }
            outbitmap.setPixel(k, j, color);
        }
    }

    public void codeTextBitmap() throws UnsupportedEncodingException, IndexOutOfBoundsException{
        byte[] b = text.getBytes("ASCII");
        Bitmap outbitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        for(int i=0; i<b.length; i++){
            int k = bitmap.getWidth()-i-1;
            int j = bitmap.getHeight()-col;
            int color;
            b[i]-=30;
            switch (canal){
                case 1:
                    color = Color.argb(Color.alpha(bitmap.getPixel(k, j)), b[i], Color.green(bitmap.getPixel(k, j)), Color.blue(bitmap.getPixel(k, j)));

                    break;
                case 2:
                    color = Color.argb(Color.alpha(bitmap.getPixel(k, j)), Color.red(bitmap.getPixel(k, j)), b[i], Color.blue(bitmap.getPixel(k, j)));
                    break;
                case 3:
                    color = Color.argb(Color.alpha(bitmap.getPixel(k, j)), Color.red(bitmap.getPixel(k, j)), Color.green(bitmap.getPixel(k, j)), b[i]);
                    break;
                default:
                    color=Color.argb(Color.alpha(bitmap.getPixel(k, j)), b[i], b[i], b[i]);
                    break;
            }
            outbitmap.setPixel(k, j, color);
        }
        this.bitmap=outbitmap;
    }
}
