package gundersonproductions.photojournal.persistence;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by bkrol on 12/29/17.
 */

@Entity (indices = {@Index(value = "id")})
public class PhotoInfo {

    @PrimaryKey (autoGenerate = true)
    long id;
    public String name;
    public String description;
    @NonNull
    public String date;
    @NonNull
    public String gpsLocation;
    @NonNull
    public String fileLocation;

    public PhotoInfo(){};

    public PhotoInfo(String name, String description, String date, String location, String fileLocation){
        this.name = name;
        this.description = description;
        this.date = date;
        this.gpsLocation = location;
        this.fileLocation = fileLocation;
    }

}
