package avisha.com.myorganizer.view;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import avisha.com.myorganizer.R;
import avisha.com.myorganizer.model.MOTask;
import avisha.com.myorganizer.presenter.TaskListPresenter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRecyclerView;
    private OnVersionNameSelectionChangeListener mListener;
    private TaskListPresenter mTaskListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.today);
        setSupportActionBar(toolbar);
        mTaskListPresenter = new TaskListPresenter();

        mRecyclerView = (RecyclerView) findViewById(R.id.version_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        List<MOTask> dataModalList = mTaskListPresenter.getTodaysTaskList(this);

        RecyclerAdapter adapter = new RecyclerAdapter(this, dataModalList);
        adapter.setListener(mListener);
        List<RecyclerSectionedList.Section> sectionList = prepareSectionList(dataModalList);
        RecyclerSectionedList.Section[] sectionArray = new RecyclerSectionedList.Section[sectionList.size()];
        RecyclerSectionedList sectionedAdapter = new
                RecyclerSectionedList(this, R.layout.section_layout, R.id.section_title, adapter);
        sectionedAdapter.setSections(sectionList.toArray(sectionArray));
        mRecyclerView.setAdapter(sectionedAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewTaskActivity.class);
                startActivity(intent);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private List<MOTask> getDummyTaskList() {
        ArrayList dummyTaskList = new ArrayList<MOTask>();
        MOTask moTask1 = new MOTask();
        moTask1.setName("Join Guitar class");
        moTask1.setImportant(true);
        moTask1.setUrgent(false);
        moTask1.setPhone("345344323");
        moTask1.setEmail("dfdfs@fdfd.com");

        MOTask moTask2 = new MOTask();
        moTask2.setName("Work on release documents");
        moTask2.setImportant(true);
        moTask2.setUrgent(true);
        moTask2.setPhone("345344323");
        moTask2.setEmail("dfdfs@fdfd.com");

        MOTask moTask3 = new MOTask();
        moTask3.setName("Prepare report for boss");
        moTask3.setImportant(false);
        moTask3.setUrgent(true);
        moTask3.setPhone("345344323");
        moTask3.setEmail("dfdfs@fdfd.com");

        MOTask moTask4 = new MOTask();
        moTask4.setName("Watch favorite TV show");
        moTask4.setImportant(false);
        moTask4.setUrgent(false);
        moTask4.setPhone("345344323");
        moTask4.setEmail("dfdfs@fdfd.com");

        dummyTaskList.add(moTask1);
        dummyTaskList.add(moTask2);
        dummyTaskList.add(moTask3);
        dummyTaskList.add(moTask4);
        return dummyTaskList;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private List<RecyclerSectionedList.Section> prepareSectionList(List<MOTask> taskList) {
        if (taskList == null) {
            return null;
        }
        int q1Count = 0;
        int q2Count = -1;
        int q3Count = -1;
        int q4Count = -1;
        int size = 0;

        List<RecyclerSectionedList.Section> sections =
                new ArrayList<RecyclerSectionedList.Section>();
        Resources resources = getResources();


        size = taskList.size();

        for (int i = 0; i < size; i++) {
            MOTask task = taskList.get(i);
            if (task.isImportant() && !task.isUrgent() && q2Count == -1) {
                q2Count = i;
            } else if (!task.isImportant() && task.isUrgent() && q3Count == -1) {
                q3Count = i;
            } else if (!task.isImportant() && !task.isUrgent() && q3Count == -1) {
                q4Count = i;
            }
        }

        sections.add(new RecyclerSectionedList.Section(q1Count, resources.getString(R.string.must_todo)));

        if (q2Count > -1) {
            sections.add(new RecyclerSectionedList.Section(q2Count, resources.getString(R.string.q2_activities)));
        }

        if (q3Count > -1) {
            sections.add(new RecyclerSectionedList.Section(q3Count, resources.getString(R.string.delegate_quick)));
        }

        if (q4Count > -1) {
            sections.add(new RecyclerSectionedList.Section(q4Count, resources.getString(R.string.postpone)));
        }
        return sections;
    }
}
