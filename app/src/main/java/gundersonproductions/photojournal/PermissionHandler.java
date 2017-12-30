package gundersonproductions.photojournal;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * Created by bkrol on 12/29/17.
 */

public class PermissionHandler {
    public static final int REQUEST_PERMISSION_WRITE = 1;
    public static final int REQUEST_PERMISSION_READ = 2;
    public static final int REQUEST_PERMISSION_CAMERA = 3;
    public static final int NUMBER_OF_PERMISSIONS = 3;

    // Checks if external storage is available for read and write
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    // Initiate request for permissions.
    public static boolean checkPermissions(Activity parent) {

        if(Camera.getNumberOfCameras()==0){
            Toast.makeText(parent, "This app only works on devices with a camera available",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isExternalStorageWritable()) {
            Toast.makeText(parent, "This app only works on devices with usable external storage",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(hasWriteExternalStoragePermissions(parent) && hasReadExternalStoragePermissions(parent) && hasCameraPermissions(parent)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check WRITE_EXTERNAL_STORAGE_PERMISSION
     * @return
     */
    private static boolean hasWriteExternalStoragePermissions(Activity parent){
        int permissionCheck = ContextCompat.checkSelfPermission(parent,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(parent,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_WRITE);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check REQUEST_PERMISSION_READ
     * @return
     */
    private static boolean hasReadExternalStoragePermissions(Activity parent){
        int permissionCheck = ContextCompat.checkSelfPermission(parent,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(parent,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_READ);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check REQUEST_PERMISSION_CAMERA
     * @return
     */
    private static boolean hasCameraPermissions(Activity parent){
        int permissionCheck = ContextCompat.checkSelfPermission(parent,
                Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(parent,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_PERMISSION_CAMERA);
            return false;
        } else {
            return true;
        }
    }



}
