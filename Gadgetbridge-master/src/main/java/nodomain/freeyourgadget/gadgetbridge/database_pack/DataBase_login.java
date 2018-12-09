package nodomain.freeyourgadget.gadgetbridge.database_pack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class DataBase_login {
    public static void login(JSONObject userJson, Context context){


        try {

            ContentValues cv;
            cv = new ContentValues();
            cv.put("H_name",userJson.getString("h_name"));
            cv.put("H_phone_num", userJson.getString("h_Mobile"));
            cv.put("H_post", userJson.getString("h_access_level"));
            Toast.makeText(context, userJson.getString("h_name")+"/"+userJson.getString("h_Mobile")
                    +"/"+userJson.getString("h_access_level"), Toast.LENGTH_SHORT).show();
            DataBase.getInstance(context).getDb().insert("Holding_member", null, cv);
            ContentValues cv2;
            cv2 = new ContentValues();
            cv2.put("C_name",userJson.getString("c_name"));
            cv2.put("C_code",userJson.getString("c_code"));
            cv2.put("C_Ages",userJson.getString("c_ages"));
            cv2.put("C_sex",userJson.getString("c_sex"));
            cv2.put("C_date",userJson.getString("c_date"));
            cv2.put("C_event_place",userJson.getString("c_evenet_place"));
            cv2.put("C_inflation",userJson.getString("c_inflation"));
            cv2.put("C_inflation_period",userJson.getString("c_inflation_period"));
            cv2.put("C_duration",userJson.getString("c_duration"));
            cv2.put("C_first_score",userJson.getString("c_first_score"));

            DataBase.getInstance(context).getDb().insert("Competition", null, cv2);
            Toast.makeText(context, userJson.getString("c_name")+"/"+userJson.getString("c_code"), Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show();


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "error_on_saving", Toast.LENGTH_SHORT).show();
        }


    }
    public static boolean islogin( Context context){

        Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from Holding_member", null);
        cr.moveToFirst();
        if(cr.getCount()==0){
            return false;
        }
        else return true;
    }
    public static void logout(Context context){

        DataBase.getInstance(context).getDb().execSQL("delete from " + "Holding_member");
        DataBase.getInstance(context).getDb().execSQL("delete from " + "Competition");
        DataBase.getInstance(context).getDb().execSQL("delete from " + "gamer_table");
        DataBase.getInstance(context).getDb().execSQL("delete from " + "Question");

    }
}
