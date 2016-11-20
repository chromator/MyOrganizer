package avisha.com.myorganizer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public long saveNewTaskInfo(MOTask task) {
        long row = -1;
        if (mDatabaseHelper == null) {
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

    public List<MOTask> getTodaysTaskList() {
        List<MOTask> taskList = new ArrayList<>();
        String query = "SELECT * FROM TASK ORDER BY TASK_IMPPORTANT DESC, TASK_URGENT DESC";

        if (mDatabaseHelper == null) {
            createDatabase();
        }

        Cursor cursor = mDatabase.rawQuery(query, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                MOTask moTask = new MOTask();
                moTask.setName(cursor.getString(cursor.getColumnIndex(Database.TASK_NAME)));
                moTask.setImportant(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(Database.TASK_IMPORTANT))));
                moTask.setUrgent(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(Database.TASK_URGENT))));
                moTask.setDate(cursor.getLong(cursor.getColumnIndex(Database.TASK_DATE)));
                moTask.setPhone(cursor.getString(cursor.getColumnIndex(Database.TASK_PHONE)));
                moTask.setEmail(cursor.getString(cursor.getColumnIndex(Database.TASK_EMAIL)));
                taskList.add(moTask);
            } while (cursor.moveToNext());

        }

        return taskList;
    }
}
