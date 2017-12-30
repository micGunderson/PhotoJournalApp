package gundersonproductions.photojournal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import gundersonproductions.photojournal.persistence.AppDatabase;
import gundersonproductions.photojournal.persistence.PhotoInfo;

public class MainActivity extends AppCompatActivity implements PhotoInfoFragment.OnFragmentInteractionListener{

    private static final int REQUEST_TAKE_PHOTO = 1;
    private String mCurrentPhotoPath;
    private int permissionsGranted=0;

    private AppDatabase database;

    public static final String CURRENT_PHOTO_PATH = "gundersonproductions.photojournal.CURRENT_PHOTO_PATH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = AppDatabase.getDatabase(getApplicationContext());

        if (permissionsGranted<PermissionHandler.NUMBER_OF_PERMISSIONS) {
            PermissionHandler.checkPermissions(this);
            return;
        }
    }

    /**
     * Used when the Take Picture button is selected.
     * @param view
     */
    public void onTakePicture(View view){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                //TODO: Needs error handling
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "gundersonproductions.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
          /*  Date date = Calendar.getInstance().getTime();
            SimpleDateFormat postFormater = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a");
            String dateString = postFormater.format(date);*/
            /*Intent intent = new Intent(this, SavePhotoInfo.class);
            intent.putExtra(CURRENT_PHOTO_PATH, mCurrentPhotoPath);
            startActivity(intent);*/

            ConstraintLayout cl = (ConstraintLayout) findViewById(R.id.initial_layout);
            cl.setVisibility(View.GONE);
            Fragment fragment = PhotoInfoFragment.newInstance(mCurrentPhotoPath);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment).commitAllowingStateLoss();

            //database.photoInfoDAO().addPhotoInfo(photoInfo);
        }
    }

    public AppDatabase getDatabase(){
        return this.database;
    }

    /**
     * Displays the PhotoInfo for all photos in a table... hopefully.
     * @param view
     */
    public void onViewPhotoTable(View view){
        Intent intent = new Intent(this, DisplayPhotoTable.class);
        startActivity(intent);
    }

    /**
     * Used when the View on Map button is selected.
     * @param view
     */
    public void onViewOnMap(View view){

    }

    /**
     *
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    // Handle permissions result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionHandler.REQUEST_PERMISSION_WRITE:
            case PermissionHandler.REQUEST_PERMISSION_CAMERA:
            case PermissionHandler.REQUEST_PERMISSION_READ:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionsGranted = permissionsGranted+1;
                    Toast.makeText(this, "Permission granted",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "You must grant permission!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onDestroy(){
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //
    }
}
