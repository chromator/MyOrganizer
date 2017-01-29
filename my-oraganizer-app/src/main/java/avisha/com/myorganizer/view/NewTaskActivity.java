package avisha.com.myorganizer.view;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;

import java.util.Calendar;

import avisha.com.myorganizer.R;
import avisha.com.myorganizer.model.MOTask;
import avisha.com.myorganizer.presenter.NewTaskPresenter;
import avisha.com.myorganizer.util.Util;

public class NewTaskActivity extends AppCompatActivity {

    private static final int PERMISSION_WRITE_EXTERNAL = 1;
    private NewTaskPresenter mPresenter;
    private MOTask mMoTask;
    private TextInputEditText taskNameView;
    private Switch taskImportantView;
    private Switch taskUrgentView;
    private EditText taskDateView;
    private EditText taskTimeView;
    private EditText taskPhoneView;
    private EditText taskEmailView;
    private Calendar mCalendar;
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            mCalendar.set(Calendar.YEAR, i);
            mCalendar.set(Calendar.MONTH, i1);
            mCalendar.set(Calendar.DATE, i2);
            String textDate = Util.toTextDate(i, i1, i2);
            taskDateView.setText(textDate);
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            mCalendar.set(Calendar.HOUR_OF_DAY, i);
            mCalendar.set(Calendar.MINUTE, i1);
            String textTime = Util.toTextTime(i, i1);
            taskTimeView.setText(textTime);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_save:
                if (validateAllFields()) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSION_WRITE_EXTERNAL);
                }
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getViewId();
        mPresenter = new NewTaskPresenter();
        mPresenter.attach(this);
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

        mMoTask.setDate(mCalendar.getTimeInMillis());
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

        mCalendar = Calendar.getInstance();
        taskDateView.setText(Util.toTextDate(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DATE)));
        taskDateView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                DatePickerFragment newFragment = new DatePickerFragment();
                                                newFragment.show(getSupportFragmentManager(), "datePicker");
                                                newFragment.setOnDateSetListener(dateSetListener);
                                            }
                                        }

        );

        taskTimeView.setText(Util.toTextTime(mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE)));
        taskTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
                newFragment.setOnTimeSetListener(timeSetListener);
            }
        });
    }

    public void closeActivity() {
        finish();
    }

}
