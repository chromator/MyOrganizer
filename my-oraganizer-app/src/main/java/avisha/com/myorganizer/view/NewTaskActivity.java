package avisha.com.myorganizer.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import java.util.Date;

import avisha.com.myorganizer.R;
import avisha.com.myorganizer.model.MOTask;
import avisha.com.myorganizer.presenter.NewTaskPresenter;

public class NewTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private Button saveButton;
    private NewTaskPresenter mPresenter;
    private MOTask mMoTask;
    private EditText taskNameView;
    private Switch taskImportantView;
    private Switch taskUrgentView;
    private EditText taskDateView;
    private EditText taskTimeView;
    private EditText taskPhoneView;
    private EditText taskEmailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getViewId();
        mPresenter = new NewTaskPresenter();
        mPresenter.attach(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_button: {
                if (validateAllFields()) {
                    mPresenter.saveNewTaskInfo(mMoTask);
                }
            }
            break;
        }
    }

    private boolean validateAllFields() {
        boolean isValid = false;
        String taskName = null;
        String taskDate = null;
        String taskTime = null;
        String taskPhone = null;
        String taskEmail = null;

        mMoTask = new MOTask();
        if (!TextUtils.isEmpty((taskName = taskNameView.getText().toString()))) {
            mMoTask.setName(taskName);
            isValid = true;
        } else {
            taskNameView.setError("Task name can not be empty!");
        }

        mMoTask.setImportant(taskImportantView.isChecked());
        mMoTask.setUrgent(taskUrgentView.isChecked());

        mMoTask.setDate(new Date().getTime());
        mMoTask.setPhone(taskPhoneView.getText().toString());
        mMoTask.setEmail(taskEmailView.getText().toString());

        return isValid;
    }

    private void getViewId() {
        taskNameView = (EditText) findViewById(R.id.taskName);
        taskImportantView = (Switch) findViewById(R.id.important_switch);
        taskUrgentView = (Switch) findViewById(R.id.urgent_switch);
        taskDateView = (EditText) findViewById(R.id.date_edit);
        taskTimeView = (EditText) findViewById(R.id.time_edit);
        taskPhoneView = (EditText) findViewById(R.id.phone_edit);
        taskEmailView = (EditText) findViewById(R.id.email_edit);

        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);
    }

    public void closeActivity() {
        finish();
    }
}
