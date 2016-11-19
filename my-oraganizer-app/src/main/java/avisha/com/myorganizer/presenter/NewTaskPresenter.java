package avisha.com.myorganizer.presenter;

import avisha.com.myorganizer.db.DatabaseHandler;
import avisha.com.myorganizer.model.MOTask;
import avisha.com.myorganizer.view.NewTaskActivity;

/**
 * Created by skulw on 11/19/16.
 */
public class NewTaskPresenter {
    private NewTaskActivity mView;
    private DatabaseHandler mDatbaseHandler;

    public void attach(NewTaskActivity newTaskActivity) {
        if(newTaskActivity != null) {
            mView = newTaskActivity;
            mDatbaseHandler = new DatabaseHandler(newTaskActivity);
        }
    }

    public void saveNewTaskInfo(MOTask task) {
        long rows = mDatbaseHandler.saveNewTaskInfo(task);
        if(rows > -1) {
            mView.closeActivity();
        }
    }
}
