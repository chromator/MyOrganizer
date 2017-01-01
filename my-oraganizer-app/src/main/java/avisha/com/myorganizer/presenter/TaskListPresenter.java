package avisha.com.myorganizer.presenter;

import android.content.Context;

import java.util.List;

import avisha.com.myorganizer.db.DatabaseHandler;
import avisha.com.myorganizer.model.MOTask;
import avisha.com.myorganizer.view.MainActivity;

/**
 * Created by skulw on 11/20/16.
 */
public class TaskListPresenter {
    private MainActivity mView;

    public List<MOTask> getTodaysTaskList() {
        DatabaseHandler databaseHandler = DatabaseHandler.databaseHandler(mView);
        return databaseHandler.getTodaysTaskList();
    }

    public void deleteTask(MOTask moTask) {
        int rows = 0;
        DatabaseHandler databaseHandler = DatabaseHandler.databaseHandler(mView);
        rows = databaseHandler.deleteTask(moTask);
        if(rows > 0) {
            mView.removeItem(moTask);
        }
    }

    public void attach(MainActivity mainActivity) {
        mView = mainActivity;
    }
}
