package avisha.com.myorganizer.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import avisha.com.myorganizer.R;
import avisha.com.myorganizer.model.MOTask;
import avisha.com.myorganizer.util.Util;

public class ViewTaskDetails extends AppCompatActivity {

    private MOTask moTask;
    private TextView taskNameView;
    private TextView dateTimeView;
    private TextView phoneView;
    private TextView emailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        moTask = (MOTask) getIntent().getSerializableExtra("task");
        loadViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_edit:
                Intent intent = new Intent(ViewTaskDetails.this, EditTaskActivity.class);
                intent.putExtra("task", moTask);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void loadViews() {
        taskNameView = (TextView) findViewById(R.id.task_name_txt);
        dateTimeView = (TextView) findViewById(R.id.date_time_txt);
        phoneView = (TextView) findViewById(R.id.phone_txt);
        emailView = (TextView) findViewById(R.id.email_txt);

        taskNameView.setText(moTask.getName());
        dateTimeView.setText(Util.milisToTextDate(moTask.getDate()) +" "+Util.milisToTextTime(moTask.getDate()));
        phoneView.setText(moTask.getPhone());
        emailView.setText(moTask.getEmail());
    }
}
