package kaczmarek.learnapp1.ui.posts.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import kaczmarek.learnapp1.R;
import kaczmarek.learnapp1.ui.posts.adapters.RVAdapter;
import kaczmarek.learnapp1.ui.posts.models.Users;
import kaczmarek.learnapp1.ui.posts.presenters.NavigationViewActivityPresenter;
import kaczmarek.learnapp1.ui.posts.views.NavigationViewActivityView;

public class NavigationViewActivity extends MvpAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NavigationViewActivityView {
    @InjectPresenter
    NavigationViewActivityPresenter mPresenter;
    NavigationView navigationView;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    int userId = 1;
    MenuItem selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_view);
        recyclerView = findViewById(R.id.recyclerviewPosts);
        initNavigationView();
        initSwipeRefreshLayout();
        mPresenter.getNamesAllUsers();
    }
    private void initSwipeRefreshLayout(){
        swipeRefreshLayout = findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> mPresenter.getAllPostsById(userId));
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
    }
    private void initNavigationView(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        TextView userName = header.findViewById(R.id.userNameTextView);
        userName.setText(R.string.name_user1);
        TextView email = header.findViewById(R.id.emailTextView);
        email.setText(R.string.email_user1);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if(item.getGroupId() == R.id.users){
            if(selected!=null){
                selected.setChecked(false);
            }
            selected = item;
            selected.setChecked(true);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        userId = item.getItemId();
        if(item.getItemId() == R.id.openBackdrop){
            Intent intent = new Intent(NavigationViewActivity.this, BackdropActivity.class);
            startActivity(intent);
            finish();
        } else {
            mPresenter.getAllPostsById(item.getItemId());
        }
        return true;
    }


    @Override
    public void updateListName() {
        Menu menu = navigationView.getMenu();
        if(mPresenter.getList() !=null){
            for (Users name: mPresenter.getList()) {
                menu.add(R.id.users,name.getId(),1,name.getName());
            }

            if(mPresenter.getList().size()!=0){
                selected = menu.findItem(1);
                menu.getItem(1).setChecked(true);
                mPresenter.getAllPostsById(1);
            }
        }
    }

    @Override
    public void updateRecyclerView() {
        RVAdapter adapter = new RVAdapter(mPresenter.getListPosts(),recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showSwipeRefreshLayout(Boolean refreshing) {
        swipeRefreshLayout.setRefreshing(refreshing);
    }
}
