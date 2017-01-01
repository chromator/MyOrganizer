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

import static avisha.com.myorganizer.db.Database.TASK;
import static avisha.com.myorganizer.db.Database.TASK_DATE;
import static avisha.com.myorganizer.db.Database.TASK_EMAIL;
import static avisha.com.myorganizer.db.Database.TASK_ID;
import static avisha.com.myorganizer.db.Database.TASK_IMPORTANT;
import static avisha.com.myorganizer.db.Database.TASK_PHONE;
import static avisha.com.myorganizer.db.Database.TASK_URGENT;

/**
 * Created by skulw on 11/19/16.
 */
public class DatabaseHandler {
    private static final String TAG = DatabaseHandler.class.getSimpleName();
    private static DatabaseHandler mDatabaseHandler;
    private final Context mContext;
    private Database mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    DatabaseHandler(Context context) {
        mContext = context;
    }

    public static DatabaseHandler databaseHandler(Context context) {
        if(mDatabaseHandler == null) {
            mDatabaseHandler = new DatabaseHandler(context);
        }

        return mDatabaseHandler;
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
            contentValues.put(TASK_IMPORTANT, task.isImportant());
            contentValues.put(Database.TASK_URGENT, task.isUrgent());
            contentValues.put(Database.TASK_DATE, task.getDate());
            contentValues.put(Database.TASK_PHONE, task.getPhone());
            contentValues.put(Database.TASK_EMAIL, task.getEmail());

            row = mDatabase.insert(TASK, null, contentValues);

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
                moTask.setId(cursor.getInt(cursor.getColumnIndex(Database.TASK_ID)));
                moTask.setName(cursor.getString(cursor.getColumnIndex(Database.TASK_NAME)));
                moTask.setImportant(cursor.getInt(cursor.getColumnIndex(TASK_IMPORTANT))== 1);
                moTask.setUrgent(cursor.getInt(cursor.getColumnIndex(Database.TASK_URGENT)) == 1);
                moTask.setDate(cursor.getLong(cursor.getColumnIndex(Database.TASK_DATE)));
                moTask.setPhone(cursor.getString(cursor.getColumnIndex(Database.TASK_PHONE)));
                moTask.setEmail(cursor.getString(cursor.getColumnIndex(Database.TASK_EMAIL)));
                taskList.add(moTask);
            } while (cursor.moveToNext());

        }

        return taskList;
    }

    public void updateTask(MOTask moTask) {
        int row = -1;

        if(mDatabaseHelper == null) {
            createDatabase();
        }

        try {


            mDatabase.beginTransaction();

            ContentValues contentValues = new ContentValues();
            contentValues.put(Database.TASK_NAME, moTask.getName());
            contentValues.put(TASK_IMPORTANT, moTask.isImportant());
            contentValues.put(TASK_URGENT, moTask.isUrgent());
            contentValues.put(TASK_DATE, moTask.getDate());
            contentValues.put(TASK_PHONE, moTask.getPhone());
            contentValues.put(TASK_EMAIL, moTask.getEmail());

            row = mDatabase.update(TASK, contentValues, TASK_ID + "= ?", new String[]{"" + moTask.getId()});

            mDatabase.setTransactionSuccessful();
            mDatabase.endTransaction();
        }catch (SQLiteException ex) {
            Log.e(TAG, "Failed to update ");
        }
    }

    public int deleteTask(MOTask moTask) {
        int row = -1;

        if(mDatabaseHelper == null) {
            createDatabase();
        }

        try {
            mDatabase.beginTransaction();

            row = mDatabase.delete(TASK, TASK_ID + "= ?", new String[]{"" + moTask.getId()});

            mDatabase.setTransactionSuccessful();
            mDatabase.endTransaction();
        }catch (SQLiteException ex) {
            Log.e(TAG, "Failed to update ");
        }
        return row;
    }
}
