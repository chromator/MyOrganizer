package avisha.com.myorganizer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import avisha.com.myorganizer.model.MOTask;

/**
 * Created by skulw on 11/19/16.
 */
public class DatabaseHandler {
    private static final String TAG = DatabaseHandler.class.getSimpleName();
    private final Context mContext;
    private Database mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    public DatabaseHandler(Context context) {
        mContext = context;
    }

    public void createDatabase() {
        mDatabaseHelper = Database.getInstance(mContext);
        mDatabase = mDatabaseHelper.getReadableDatabase();
    }

    public long saveNewTaskInfo(MOTask task) {
        long row = -1;
        if(mDatabaseHelper == null) {
            createDatabase();
        }

        try {
            mDatabase.beginTransaction();

            ContentValues contentValues = new ContentValues();
            contentValues.put(Database.TASK_NAME, task.getName());
            contentValues.put(Database.TASK_IMPORTANT, Boolean.toString(task.isImportant()));
            contentValues.put(Database.TASK_URGENT, Boolean.toString(task.isUrgent()));
            contentValues.put(Database.TASK_DATE, task.getDate());
            contentValues.put(Database.TASK_PHONE, task.getPhone());
            contentValues.put(Database.TASK_EMAIL, task.getEmail());

            row = mDatabase.insert(Database.TASK, null, contentValues);

            mDatabase.setTransactionSuccessful();

            mDatabase.endTransaction();
        } catch (SQLiteException e) {
            Log.e(TAG, "Failed to save TASK!");
        }
        return row;
    }
}
