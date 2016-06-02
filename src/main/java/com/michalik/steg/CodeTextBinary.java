package com.michalik.steg;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.io.UnsupportedEncodingException;

/**
 * Created by michalik on 09.05.16.
 */
public class CodeTextBinary {
    private Bitmap bitmap;
    private Bitmap outBitmap;
    private int[][] image;
    private int canal;
    final String EOM = "-|-EOM|-"; //end of message
    final String SOM = "-|-SOM|-"; //start of message
    private String string;
    private String[] binarString;

    public CodeTextBinary(Bitmap bitmap, String string, int canal){
        this.bitmap=bitmap;
        this.string=SOM+string+EOM;
        this.canal=canal;
        this.outBitmap=bitmap.copy(Bitmap.Config.ARGB_8888, true);
        //this.image = new int[bitmap.getWidth()][bitmap.getHeight()];

    }


    void stringToBinaryConversion() throws UnsupportedEncodingException {
        byte[] infoBin= string.getBytes("UTF-8");
        String[] w = new String[infoBin.length];
        int i=0;
        for (byte b : infoBin) {
            w[i] = "0"+Integer.toBinaryString(b);

            if(w[i].length()==7){
                w[i]="0"+w[i];
            }
            System.out.println(w[i]);
            i++;
        }
        binarString=w;
    }

    void codeBinaryToBitmap(){
        try{
            stringToBinaryConversion();
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        int stringIterator = 0;
        for(int i=0; i<binarString.length; i++){
            char[] c = binarString[i].toCharArray();
            for(int j=0; j<8; j++){

            }

        }

        for(int i=0; i<bitmap.getWidth(); i++) {
            for (int j = 0; j < bitmap.getHeight() - 8; j+=8) {
                if (stringIterator > binarString.length - 1) {
                    break;
                }
                char[] c = binarString[stringIterator].toCharArray();

                for (int k = 0; k < 8; k++) {
                    //System.out.print("image["+i+"]["+(j+k)+"]="+image[i][(j+k)]+" --> "+c[k]+" --> ");
                    int px;
                    int colour;
                    switch (canal) {
                        case 1:
                            px = Color.red(outBitmap.getPixel(i, j + k));

                            px = codedOutput(px, c[k]);
                            colour=Color.argb(255, px, Color.green(bitmap.getPixel(i, j + k)), Color.blue(bitmap.getPixel(i, j + k)));
                            break;
                        case 2:
                            px = codedOutput(Color.green(bitmap.getPixel(i, j + k)), c[k]);
                            colour=Color.argb(255, Color.red(bitmap.getPixel(i, j+k)), px, Color.blue(bitmap.getPixel(i, j + k)));
                            break;
                        case 3:
                            px = codedOutput(Color.blue(bitmap.getPixel(i, j + k)), c[k]);
                            colour=Color.argb(255, Color.red(bitmap.getPixel(i, j+k)), Color.green(bitmap.getPixel(i, j + k)), px);
                            break;
                        default:
                            px = codedOutput(Color.red(bitmap.getPixel(i, j + k)), c[k]);
                            colour=Color.argb(255, px, Color.green(bitmap.getPixel(i, j + k)), Color.blue(bitmap.getPixel(i, j + k)));
                            break;
                    }
                    outBitmap.setPixel(i, j+k, colour);
                }
                stringIterator++;
            }
        }

    }

    void codeBinaryToIntImage(){
        try{
            stringToBinaryConversion();
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        int stringIterator = 0;
        for(int i=0; i<binarString.length; i++){
            char[] c = binarString[i].toCharArray();
            for(int j=0; j<8; j++){
                //System.out.print(c[j]);
            }
            //System.out.println("\n");
        }
        for(int i=0; i<image.length; i++){
            for(int j=0; j<image[1].length-8;){
                if(stringIterator>binarString.length-1){
                    break;
                }
                char[] c = binarString[stringIterator].toCharArray();


                for(int k=0; k<8; k++){
                    //System.out.print("image["+i+"]["+(j+k)+"]="+image[i][(j+k)]+" --> "+c[k]+" --> ");
                    image[i][j+k]=codedOutput(image[i][j+k], c[k]);
                    //System.out.println(image[i][j+k]);
                }
                stringIterator++;
                j+=8;

            }
        }
    }

    int codedOutput(int im, char b){
        int px=im;

        if(im%2==0 && b=='0'){
            return px;
        }
        if(im%2==0 && b=='1'){
            return px+1;
        }
        if(im%2!=0 && b=='0'){
            return px-1;
        }
        if(im%2!=0 && b=='1'){
            return px;
        }

        return px;
    }

    public Bitmap getOutBitmap(){
        return outBitmap;
    }


}
