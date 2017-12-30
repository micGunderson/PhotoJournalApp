package gundersonproductions.photojournal;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import gundersonproductions.photojournal.persistence.AppDatabase;
import gundersonproductions.photojournal.persistence.PhotoInfo;

public class DisplayPhotoTable extends AppCompatActivity {

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_photo_table);

        database = AppDatabase.getDatabase(getApplicationContext());

        List<PhotoInfo> photoList = database.photoInfoDAO().getAllPhotos();
        TableLayout tl = findViewById(R.id.phototable);

        for(PhotoInfo photoInfo : photoList){
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView photoLocation = new TextView(this);
            photoLocation.setText(photoInfo.fileLocation);
            photoLocation.setTextSize(10);
            photoLocation.setPaintFlags(photoLocation.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            row.addView(photoLocation);
            tl.addView(row);
        }
    }
}
