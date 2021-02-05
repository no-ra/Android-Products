package com.example.nora.firstproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by NORA on 12/21/2015.
 */
public class Utils {

    public static void compressAndSaveBmp(Uri outputFileUri, Bitmap pictureBitmap) throws IOException {
        OutputStream fOut;
        File file = new File(outputFileUri.getPath()); // the File to save to
        fOut = new FileOutputStream(file);
        pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fOut); // saving the Bitmap to a file compressed as a JPEG with 80% compression rate
        fOut.flush();
        fOut.close(); // do not forget to close the stream
    }

    public static File createImageFile(Context self) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPG_" + timeStamp + "_";
        File storageDir = self.getExternalFilesDir(null);
        File tmpImage = File.createTempFile(
                imageFileName, /* prefix */
                ".jpeg", /* suffix */
                storageDir /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        //mCurrentPhotoPath = tmpImage.getAbsolutePath();
        return tmpImage;
    }
}
