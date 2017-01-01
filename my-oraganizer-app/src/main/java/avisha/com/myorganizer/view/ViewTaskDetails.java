package avisha.com.myorganizer.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import avisha.com.myorganizer.R;
import avisha.com.myorganizer.model.MOTask;

public class ViewTaskDetails extends AppCompatActivity {

    private MOTask moTask;
    private TextView taskNameView;
    private TextView importantView;
    private TextView urgentView;
    private TextView dateTimeView;
    private TextView phoneView;
    private TextView emailView;
    private Button editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task_details);

        moTask = (MOTask) getIntent().getSerializableExtra("task");
        loadViews();
    }

    private void loadViews() {
        taskNameView = (TextView) findViewById(R.id.task_name_txt);
        importantView = (TextView)findViewById(R.id.important_txt);
        urgentView = (TextView) findViewById(R.id.urgent_txt);
        dateTimeView = (TextView) findViewById(R.id.date_time_txt);
        phoneView = (TextView) findViewById(R.id.phone_txt);
        emailView = (TextView) findViewById(R.id.email_txt);
        editButton = (Button) findViewById(R.id.edit_btn);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewTaskDetails.this, EditTaskActivity.class);
                intent.putExtra("task", moTask);
                startActivity(intent);
            }
        });

        taskNameView.setText(moTask.getName());
        importantView.setText("This is Important but not urgent");
        urgentView.setText("Remove this");
        dateTimeView.setText(""+moTask.getDate());
        phoneView.setText(moTask.getPhone());
        emailView.setText(moTask.getEmail());
    }
}
