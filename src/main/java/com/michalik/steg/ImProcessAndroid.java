package com.michalik.steg;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by michalik on 17.04.16.
 */
public class ImProcessAndroid {
    //set of methods to process image
    public static int[][] bmpToArray(Bitmap bmp, int channel){
        int[][] out = new int[bmp.getWidth()][bmp.getHeight()];
        switch(channel){
            case 1:
                for(int i=0; i<bmp.getWidth(); i++){
                    for(int j=0; j<bmp.getHeight(); j++){
                        int pixel = bmp.getPixel(i, j);
                        out[i][j]= Color.red(pixel);
                    }
                }
                return out;
            case 2:
                for(int i=0; i<bmp.getWidth(); i++){
                    for(int j=0; j<bmp.getHeight(); j++){
                        int pixel = bmp.getPixel(i, j);
                        out[i][j]= Color.green(pixel);
                    }
                }
                return out;
            case 3:
                for(int i=0; i<bmp.getWidth(); i++){
                    for(int j=0; j<bmp.getHeight(); j++){
                        int pixel = bmp.getPixel(i, j);
                        out[i][j]= Color.blue(pixel);
                    }
                }
                return out;
            default:
                for(int i=0; i<bmp.getWidth(); i++){
                    for(int j=0; j<bmp.getHeight(); j++){
                        int pixel = bmp.getPixel(i, j);
                        out[i][j]= Color.red(pixel);
                    }
                }
                return out;
        }
    }
    public static Bitmap arrayToBMP(int[][] imR, int[][] imG, int[][] imB){
        Bitmap bmp = Bitmap.createBitmap(imR.length, imR[1].length, Bitmap.Config.ARGB_8888);

        for(int i=0; i<bmp.getWidth(); i++){
            for(int j=0; j<bmp.getHeight(); j++){
                bmp.setPixel(i,j,Color.rgb(imR[i][j], imG[i][j], imB[i][j]));
            }
        }
        return bmp;
    }
    public static Bitmap grayScale(Bitmap bmp){

        double red = 0.33;
        double green = 0.59;
        double blue = 0.11;
        for (int i = 0; i < bmp.getWidth(); i++) {
            for (int j = 0; j < bmp.getHeight(); j++) {
                int p = bmp.getPixel(i, j);
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);

                r = (int) red *r;
                g = (int) green * g;
                b = (int) blue * b;
                bmp.setPixel(i, j, Color.argb(Color.alpha(p), r, r, r));
            }
        }
        return bmp;
    }
    public static void saveBitmapFile(Bitmap bitmap){

    }
}
