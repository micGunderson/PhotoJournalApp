package gundersonproductions.photojournal.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by bkrol on 12/29/17.
 */

@Database(entities = {PhotoInfo.class
}, version = 16, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{

    private static AppDatabase INSTANCE;

    public abstract PhotoInfoDAO photoInfoDAO();

    public static AppDatabase getDatabase(Context context){
        if(INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "photodatabase")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }

}
