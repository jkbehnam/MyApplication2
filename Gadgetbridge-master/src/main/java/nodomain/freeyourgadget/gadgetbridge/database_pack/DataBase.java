package nodomain.freeyourgadget.gadgetbridge.database_pack;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by User on 1/25/2018.
 */

public class DataBase {
     String User_Path;
     Context context;
     private SQLiteDatabase db;
     public static DataBase mydb;
    @SuppressLint("WrongConstant")
    public DataBase(Context Context) {
        context = Context;
        User_Path =  "/data/data/com.example.behnam.myapplication/datahhbases/";

        open_db();
        setDb(context.openOrCreateDatabase(User_Path + "/tapesh_db.db",
                SQLiteDatabase.CREATE_IF_NECESSARY, null));

        mydb=this;
    }

    public  void open_db() {
        File f = new File(User_Path);
        if (!f.exists()) {
            try {
                f.mkdirs();
                f.createNewFile();
                InputStream in = context.getAssets().open("tapesh_db.db");

                OutputStream out = new FileOutputStream(User_Path + "tapesh_db.db");

                int read;
                byte[] buffer = new byte[1024];

                while ((read = in.read(buffer)) != -1) {

                    out.write(buffer, 0, read);

                }

                in.close();
                out.close();


            } catch (Exception exp) {


            }
        } else {

        }
    }

    public static DataBase getInstance(Context context) {
        if(mydb==null){
            return new DataBase(context);
        }else {
        return mydb;}
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }
}
