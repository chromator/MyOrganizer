package avisha.com.myorganizer.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Date;

import avisha.com.myorganizer.R;
import avisha.com.myorganizer.model.MOTask;
import avisha.com.myorganizer.presenter.EditTaskPresenter;

public class EditTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView taskNameView;
    private Switch taskImportantView;
    private Switch taskUrgentView;
    private EditText taskDateView;
    private EditText taskTimeView;
    private EditText taskPhoneView;
    private EditText taskEmailView;
    private Button saveButton;
    private MOTask mMoTask;
    private EditTaskPresenter mEditTaskPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        mEditTaskPresenter = new EditTaskPresenter();
        mEditTaskPresenter.attach(this);

        mMoTask = (MOTask) getIntent().getSerializableExtra("task");
        loadViews();
    }

    private void loadViews() {
        taskNameView = (EditText) findViewById(R.id.task_name_edt);
        taskImportantView = (Switch) findViewById(R.id.important_swt);
        taskUrgentView = (Switch) findViewById(R.id.urgent_swt);
        taskDateView = (EditText) findViewById(R.id.date_edt);
        taskTimeView = (EditText) findViewById(R.id.time_edt);
        taskPhoneView = (EditText) findViewById(R.id.phone_edt);
        taskEmailView = (EditText) findViewById(R.id.email_edt);

        saveButton = (Button) findViewById(R.id.save_btn);
        saveButton.setOnClickListener(this);

        taskNameView.setText(mMoTask.getName());
        taskImportantView.setChecked(mMoTask.isImportant());
        taskUrgentView.setChecked(mMoTask.isUrgent());
        taskDateView.setText(mMoTask.getDate().toString());
        taskTimeView.setText(mMoTask.getDate().toString());
        taskPhoneView.setText(mMoTask.getPhone());
        taskEmailView.setText(mMoTask.getEmail());
    }

    @Override
    public void onClick(View view) {
        if(validateAllFields()) {
            mEditTaskPresenter.updateTask(mMoTask);
        }
    }

    private boolean validateAllFields() {
        boolean isValid = false;
        String taskName = null;
        String taskDate = null;
        String taskTime = null;
        String taskPhone = null;
        String taskEmail = null;

        if(mMoTask == null) {
            mMoTask = new MOTask();
        }
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
}
