package avisha.com.myorganizer.presenter;

import android.content.Context;

import avisha.com.myorganizer.db.DatabaseHandler;
import avisha.com.myorganizer.model.MOTask;
import avisha.com.myorganizer.view.EditTaskActivity;

/**
 * Created by skulw on 11/24/16.
 */
public class EditTaskPresenter {
    private EditTaskActivity mView;

    public void attach(EditTaskActivity editTaskActivity) {
        mView = editTaskActivity;
    }

    public void updateTask(MOTask moTask) {
        DatabaseHandler databaseHandler = DatabaseHandler.databaseHandler((Context) mView);
        databaseHandler.updateTask(moTask);
        mView.finish();
    }
}
