package avisha.com.myorganizer.db;

/**
 * Created by skulw on 11/19/16.
 */

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;


final class Database extends SQLiteOpenHelper {
    private static final String DB_NAME = "q2_table";
    private static final int DB_VERSION = 1;
    public static final String TASK = "TASK";
    public static final String TASK_NAME = "TASK_NAME";
    public static final String TASK_IMPORTANT = "TASK_IMPPORTANT";
    public static final String TASK_URGENT = "TASK_URGENT";
    public static final String TASK_DATE = "TASK_DATE";
    public static final String TASK_PHONE = "TASK_PHONE";
    public static final String TASK_EMAIL = "TASK_EMAIL";
    public static final String TASK_ID = "TASK_ID";
    public static final String QUERY_CREATE_TBL_TASK = "CREATE TABLE " + TASK + "( "
            + TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TASK_NAME + " TEXT NOT NULL, "
            + TASK_IMPORTANT + " TEXT NOT NULL, "
            + TASK_URGENT + " TEXT NOT NULL, "
            + TASK_DATE + " INTEGER, "
            + TASK_PHONE + " TEXT, "
            + TASK_EMAIL + " TEXT"
            + ")";
    private static Database database;
    private static final String TAG = "Database";

    private Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);//Environment.getExternalStorageDirectory().getPath()+"/My/"+
    }

    /**
     * Singleton function to get the DwDatabase instance.
     *
     * @param context must me a application context
     * @return DwDatabase instance.
     */
    public static synchronized Database getInstance(Context context) {
        if (database != null) {
            return database;
        } else {
            database = new Database(context.getApplicationContext());
            return database;
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
     * .SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(QUERY_CREATE_TBL_TASK);
        } catch (SQLException e) {
            Log.e(TAG, "FAILED TO CREATE TABLE TASK");
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
     * .SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

