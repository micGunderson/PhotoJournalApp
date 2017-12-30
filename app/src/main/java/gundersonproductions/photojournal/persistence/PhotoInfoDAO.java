package gundersonproductions.photojournal.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by bkrol on 12/29/17.
 */

@Dao
public interface PhotoInfoDAO {

    @Insert(onConflict = OnConflictStrategy.FAIL)
    void addPhotoInfo(PhotoInfo photoInfo);

    @Query("select * from photoinfo")
    public List<PhotoInfo> getAllPhotos();

    @Query("select * from photoinfo where id = :photoId")
    public List<PhotoInfo> getPhoto(long photoId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updatePhotoInfo(PhotoInfo photoInfo);

    @Query("delete from photoinfo where id = :photoId")
    void removePhoto(long photoId);

}
