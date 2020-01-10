package com.example.i_for.writtingboard;

import android.graphics.Bitmap;

import java.text.DecimalFormat;


public class Comparation {
    public String score0;
    public String score3;
    public String level;

    public void outputpixels(Bitmap mBitmap, Bitmap bmp) {
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        int s = 0,g = 0,bingo = 0,more = 0,less=0;
        for(int i=0;i<width;i++){
            for(int j=0;j<width;j++){
                int pixel=mBitmap.getPixel(j,i);
                int Red = (pixel & 0xff0000) >> 16;
                int Green = (pixel & 0xff00) >> 8;
                int Blue = (pixel &0xff);
                int grey1 = (int) (Red*0.299+Green*0.587+Blue*0.114);
                pixel=bmp.getPixel(j,i);
                Red = (pixel & 0xff0000) >> 16;
                Green = (pixel & 0xff00) >> 8;
                Blue = (pixel &0xff);
                int grey2 = (int) (Red*0.299+Green*0.587+Blue*0.114);
                //if (grey1==0) {
                if (grey1>200) {
                    g++;
                    grey1=255;
                    if(grey2==0){
                        bingo++;
                    }
                    else {
                        more++;
                    }
                }
                if (grey2==0) {
                    s++;
                    if(grey1!=255){
                        //if(grey1!=0){
                        less++;
                    }
                }
            }
        }
        DecimalFormat df = new DecimalFormat("0.00");
        score0 = df.format(100*bingo/(double)s);
        score3 = df.format(Math.sqrt(100*(bingo-more/2)/(double)s)*10);
        if ((Math.sqrt(100*(bingo-more/2)/(double)s)*10)>80)
            level="1";
        else if((Math.sqrt(100*(bingo-more/2)/(double)s)*10)>60)
            level="2";
        else level="3";

//        double score1 = 100*(bingo-more)/(double)s;
//        double score2 = 100*(1-more/(double)g-less/(double)s);
    }

//    private void saveImage(Bitmap bitmap) throws FileNotFoundException {
//        if(Environment.getExternalStorageState().equals("mounted")){
//            String sdcard = Environment.getExternalStorageDirectory().getPath().toString()+"/tttest";
//            FileOutputStream fos = new FileOutputStream(sdcard+"lll.jpeg");
//            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
//        }
//    }


/**    private void saveImage(Bitmap bmp) {
 File appDir = new File(Environment.getExternalStorageDirectory(),"BBBBB");
 if(!appDir.exists()){
 appDir.mkdirs();
 }
 String filename = "Test.jpg";
 File file = new File(appDir,filename);
 //        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
 //            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
 //        }
 try{
 FileOutputStream fos = new FileOutputStream(file);
 bmp.compress(Bitmap.CompressFormat.JPEG,100,fos);
 fos.flush();
 fos.close();
 }catch (FileNotFoundException e){
 e.printStackTrace();
 }catch (IOException e){
 e.printStackTrace();
 }
 try {
 MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), filename, null);
 }catch (FileNotFoundException e){
 e.printStackTrace();
 }
 sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("/sdcard/BBBBB/Test.jpg")));
 }
 */

}

