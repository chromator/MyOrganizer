package avisha.com.myorganizer.view;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import avisha.com.myorganizer.R;
import avisha.com.myorganizer.model.MOTask;
import avisha.com.myorganizer.presenter.EditTaskPresenter;
import avisha.com.myorganizer.util.Util;

public class EditTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PERMISSION_WRITE_EXTERNAL = 1;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEditTaskPresenter = new EditTaskPresenter();
        mEditTaskPresenter.attach(this);

        mMoTask = (MOTask) getIntent().getSerializableExtra("task");
        loadViews();
    }

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
                    mEditTaskPresenter.updateTask(mMoTask);
                }
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    private void loadViews() {
        mCalendar = Calendar.getInstance();
        taskNameView = (EditText) findViewById(R.id.task_name_edt);
        taskImportantView = (Switch) findViewById(R.id.important_swt);
        taskUrgentView = (Switch) findViewById(R.id.urgent_swt);
        taskDateView = (EditText) findViewById(R.id.date_edt);
        taskDateView.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                    DatePickerFragment newFragment = new DatePickerFragment();
                    newFragment.setDate(mMoTask.getDate());
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                    newFragment.setOnDateSetListener(dateSetListener);
                }
            }
        );
        taskTimeView = (EditText) findViewById(R.id.time_edt);
        taskTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment newFragment = new TimePickerFragment();
                newFragment.setDate(mMoTask.getDate());
                newFragment.show(getSupportFragmentManager(), "timePicker");
                newFragment.setOnTimeSetListener(timeSetListener);
            }
        });
        taskPhoneView = (EditText) findViewById(R.id.phone_edt);
        taskEmailView = (EditText) findViewById(R.id.email_edt);

        taskNameView.setText(mMoTask.getName());
        taskImportantView.setChecked(mMoTask.isImportant());
        taskUrgentView.setChecked(mMoTask.isUrgent());
        taskDateView.setText(Util.milisToTextDate(mMoTask.getDate()));
        taskTimeView.setText(Util.milisToTextTime(mMoTask.getDate()));
        taskPhoneView.setText(mMoTask.getPhone());
        taskEmailView.setText(mMoTask.getEmail());


        taskUrgentView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                taskDateView.setEnabled(!b);
                taskTimeView.setEnabled(!b);
                if(b) {
                    taskDateView.setText(Util.toTextDate(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DATE)));
                    taskTimeView.setText(Util.toTextTime(mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE)));
                } else {
                    taskDateView.setText(Util.milisToTextDate(mMoTask.getDate()));
                    taskTimeView.setText(Util.milisToTextTime(mMoTask.getDate()));
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    private boolean validateAllFields() {
        boolean isValid = false;
        String taskName = null;
        String taskDate = null;
        String taskTime = null;
        String taskPhone = null;
        String taskEmail = null;

        if (mMoTask == null) {
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
