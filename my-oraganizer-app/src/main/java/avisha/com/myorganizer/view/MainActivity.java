package avisha.com.myorganizer.view;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import avisha.com.myorganizer.R;
import avisha.com.myorganizer.model.DataModal;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRecyclerView;
    private OnVersionNameSelectionChangeListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.version_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        List<DataModal> dataModalList = new ArrayList<DataModal>();
        dataModalList.add(new DataModal("Label1", "Description1"));
        dataModalList.add(new DataModal("Label2", "Description2"));
        dataModalList.add(new DataModal("Label3", "Description3"));
        dataModalList.add(new DataModal("Label4", "Description4"));
        dataModalList.add(new DataModal("Label5", "Description5"));

        RecyclerAdapter adapter = new RecyclerAdapter(this, dataModalList);
        adapter.setListener(mListener);
        List<RecyclerSectionedList.Section> sectionList = prepareSectionList();
        RecyclerSectionedList.Section[] sectionArray = new RecyclerSectionedList.Section[sectionList.size()];
        RecyclerSectionedList sectionedAdapter = new
                RecyclerSectionedList(this, R.layout.section_layout, R.id.section_title, adapter);
        sectionedAdapter.setSections(sectionList.toArray(sectionArray));
        mRecyclerView.setAdapter(sectionedAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    private List<RecyclerSectionedList.Section> prepareSectionList() {
        List<RecyclerSectionedList.Section> sections =
                new ArrayList<RecyclerSectionedList.Section>();
        Resources resources = getResources();
        sections.add(new RecyclerSectionedList.Section(0, resources.getString(R.string.must_todo)));
        sections.add(new RecyclerSectionedList.Section(2, resources.getString(R.string.q2_activities)));
        sections.add(new RecyclerSectionedList.Section(3, resources.getString(R.string.delegate_quick)));
        sections.add(new RecyclerSectionedList.Section(4, resources.getString(R.string.postpone)));
        return sections;
    }
}
