package com.michalik.steg;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by michalik on 14.05.16.
 */
public class DecodeTextBinary {
    private Bitmap bitmap;
    private String decoded;
    final String EOM = "-|-EOM|-"; //end of message
    final String SOM = "-|-SOM|-"; //start of message
    public DecodeTextBinary(Bitmap bitmap){
        this.decoded = "";
        this.bitmap=bitmap;

    }
    void decodeBitmap(int canal){
        String tmpBin;
        String tmpString="";
        int px;
        for(int i=0; i<bitmap.getWidth(); i++){
            for(int j=0; j<bitmap.getHeight()-8;){
                tmpBin="";
                for(int k=0; k<8; k++){
                    switch(canal){
                        case 1:
                            px = Color.red(bitmap.getPixel(i, j+k));
                            break;
                        case 2:
                            px = Color.green(bitmap.getPixel(i, j + k));
                            break;
                        case 3:
                            px = Color.blue(bitmap.getPixel(i, j + k));
                            break;
                        default:
                            px = Color.red(bitmap.getPixel(i, j + k));
                            break;
                    }
                    if(px%2==0){
                        tmpBin+="0";
                    }
                    else {
                        tmpBin+="1";
                    }
                }
                tmpString+=(char)Integer.parseInt(tmpBin, 2);
                if(tmpString.contains(SOM)){
                    decoded=tmpString;
                }

                if(decoded.contains(EOM)){
                    return;
                }
                j+=8;
            }
        }
    }
    public String getDecoded(){
        decoded=decoded.replace(SOM, "");
        decoded=decoded.replace(EOM, "");
        return decoded;
    }

}
