package avisha.com.myorganizer.presenter;

import android.content.Context;

import java.util.List;

import avisha.com.myorganizer.db.DatabaseHandler;
import avisha.com.myorganizer.model.MOTask;

/**
 * Created by skulw on 11/20/16.
 */
public class TaskListPresenter {
    public List<MOTask> getTodaysTaskList(Context context) {
        DatabaseHandler databaseHandler = new DatabaseHandler(context);
        return databaseHandler.getTodaysTaskList();
    }
}
