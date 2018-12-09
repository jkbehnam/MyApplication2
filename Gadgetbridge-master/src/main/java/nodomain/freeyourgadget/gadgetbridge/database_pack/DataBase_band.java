package nodomain.freeyourgadget.gadgetbridge.database_pack;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 1/25/2018.
 */

 public class DataBase_band extends SQLiteOpenHelper {

    public DataBase_band(Context context) {

        super(context, "/Storage/emulated/0/Android/data/nodomain.behnam.tapesh/files/GadgetBridge", null, new DataBase_band(context).getWritableDatabase().getVersion());
        SQLiteDatabase.openOrCreateDatabase("/Storage/emulated/0/Android/data/nodomain.behnam.tapesh/files/GadgetBridge",null);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
