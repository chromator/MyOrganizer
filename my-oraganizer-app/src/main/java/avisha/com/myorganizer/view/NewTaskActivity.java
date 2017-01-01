package avisha.com.myorganizer.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
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

    private static final int PERMISSION_WRITE_EXTERNAL = 1;
    private Button saveButton;
    private NewTaskPresenter mPresenter;
    private MOTask mMoTask;
    private TextInputEditText taskNameView;
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
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSION_WRITE_EXTERNAL);
                }
            }
            break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_WRITE_EXTERNAL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPresenter.saveNewTaskInfo(mMoTask);
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
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
        taskNameView = (TextInputEditText) findViewById(R.id.taskName);
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
