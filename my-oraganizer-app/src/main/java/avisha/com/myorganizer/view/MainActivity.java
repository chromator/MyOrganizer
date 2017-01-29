package avisha.com.myorganizer.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import avisha.com.myorganizer.R;
import avisha.com.myorganizer.model.MOTask;
import avisha.com.myorganizer.presenter.TaskListPresenter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private OnVersionNameSelectionChangeListener mListener;
    private TaskListPresenter mTaskListPresenter;
    private RecyclerAdapter mAdapter;
    private List<MOTask> mTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.today);
        setSupportActionBar(toolbar);
        mTaskListPresenter = new TaskListPresenter();
        mTaskListPresenter.attach(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.version_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

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

    @Override
    protected void onStart() {
        super.onStart();
        LoadTasksAsyncTask asyncTask = new LoadTasksAsyncTask(this);
        asyncTask.execute();
    }

    private List<MOTask> getDummyTaskList() {
        ArrayList dummyTaskList = new ArrayList<MOTask>();
        MOTask moTask1 = new MOTask();
        moTask1.setName("adasds");
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
            } else if (!task.isImportant() && !task.isUrgent() && q4Count == -1) {
                q4Count = i;
            }
        }

        sections.add(new RecyclerSectionedList.Section(q1Count, resources.getString(R.string.necessity)));

        if (q2Count > -1) {
            sections.add(new RecyclerSectionedList.Section(q2Count, resources.getString(R.string.extr_prod)));
        }

        if (q3Count > -1) {
            sections.add(new RecyclerSectionedList.Section(q3Count, resources.getString(R.string.distraction)));
        }

        if (q4Count > -1) {
            sections.add(new RecyclerSectionedList.Section(q4Count, resources.getString(R.string.waste)));
        }
        return sections;
    }

    public void removeItem(MOTask moTask) {
        int index = -1;
        if(moTask != null) {
            index = mAdapter.getItemIndex(moTask);
            if(index > -1) {
                mTaskList.remove(index);
                List<RecyclerSectionedList.Section> sectionList = prepareSectionList(mTaskList);
                RecyclerSectionedList.Section[] sectionArray = new RecyclerSectionedList.Section[sectionList.size()];
                RecyclerSectionedList sectionedAdapter = new
                        RecyclerSectionedList(this, R.layout.section_layout, R.id.section_title, mAdapter);
                sectionedAdapter.setSections(sectionList.toArray(sectionArray));
                mRecyclerView.setAdapter(sectionedAdapter);
            }
        }
    }


    /**
     * Created by skulw on 11/20/16.
     */
    public class LoadTasksAsyncTask extends AsyncTask<Void, Void, List<MOTask>> {

        private final WeakReference<MainActivity> mContext;

        public LoadTasksAsyncTask(MainActivity context) {
            mContext = new WeakReference<MainActivity>(context);
        }

        @Override
        protected List<MOTask> doInBackground(Void... voids) {
            return mTaskListPresenter.getTodaysTaskList();
        }

        @Override
        protected void onPostExecute(final List<MOTask> moTaskList) {
            super.onPostExecute(moTaskList);
            mContext.get().loadTaskList(moTaskList);
        }

    }

    private void loadTaskList(final List<MOTask> moTaskList) {
        mTaskList = moTaskList;
        mAdapter = new RecyclerAdapter(this, mTaskList);
        mAdapter.setListener(new OnVersionNameSelectionChangeListener() {
            @Override
            public void OnSelectionChanged(int versionNameIndex) {
                Intent intent = new Intent(MainActivity.this, ViewTaskDetails.class);
                intent.putExtra("task", moTaskList.get(versionNameIndex));
                startActivity(intent);
            }

            @Override
            public void onLongClick(final int index) {
                AlertDialog dialog = null;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete Task")
                        .setMessage("Do you want to delete this task?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mTaskListPresenter.deleteTask(mTaskList.get(index));
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                dialog = builder.show();
            }
        });

        List<RecyclerSectionedList.Section> sectionList = prepareSectionList(mTaskList);
        RecyclerSectionedList.Section[] sectionArray = new RecyclerSectionedList.Section[sectionList.size()];
        RecyclerSectionedList sectionedAdapter = new
                RecyclerSectionedList(this, R.layout.section_layout, R.id.section_title, mAdapter);
        sectionedAdapter.setSections(sectionList.toArray(sectionArray));
        mRecyclerView.setAdapter(sectionedAdapter);
    }
}
