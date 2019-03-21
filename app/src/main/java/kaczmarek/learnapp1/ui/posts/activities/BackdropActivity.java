package kaczmarek.learnapp1.ui.posts.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;


import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;

import kaczmarek.learnapp1.R;
import kaczmarek.learnapp1.ui.posts.adapters.RVAdapter;
import kaczmarek.learnapp1.ui.posts.models.Users;
import kaczmarek.learnapp1.ui.posts.presenters.BackdropActivityPresenter;
import kaczmarek.learnapp1.ui.posts.views.BackdropActivityView;
import kaczmarek.learnapp1.utils.NavigationIconClickListener;


public class BackdropActivity extends MvpAppCompatActivity implements BackdropActivityView {
    @InjectPresenter
    BackdropActivityPresenter mPresenter;
    NavigationIconClickListener iconClickListener;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout nameLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backdrop);
        toolbar = findViewById(R.id.toolbarBackdrop);
        setSupportActionBar(toolbar);
        nameLayout = findViewById(R.id.namesLayout);
        recyclerView = findViewById(R.id.recyclerViewBackdrop);
        initSwipeRefreshLayout();
        mPresenter.getNamesAllUsers();
        mPresenter.getAllPostsById(1);

    }

    private void initSwipeRefreshLayout(){
        swipeRefreshLayout = findViewById(R.id.swipeLayoutBackdrop);
        swipeRefreshLayout.setOnRefreshListener(() -> mPresenter.getAllPostsById(1));
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        iconClickListener = new NavigationIconClickListener(
                BackdropActivity.this,
                findViewById(R.id.swipeLayoutBackdrop),
                new AccelerateDecelerateInterpolator(),
                BackdropActivity.this.getResources().getDrawable(R.drawable.ic_menu),
                BackdropActivity.this.getResources().getDrawable(R.drawable.ic_close));
        toolbar.setNavigationOnClickListener(iconClickListener);
    }
    @Override
    public void updateRecyclerView() {
        RVAdapter adapter = new RVAdapter(mPresenter.getListPosts(),recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {

        if (iconClickListener.isOpen() == true) {
            getNavigationIconView(toolbar).performClick();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void showSwipeRefreshLayout(boolean refreshing) {
        swipeRefreshLayout.setRefreshing(refreshing);
    }

    @Override
    public void updateListName() {
        Button button = new Button(getApplicationContext());
        button.setText(getString(R.string.open_navigationview_list));
        button.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)
        );
        button.setOnClickListener(v ->{
            Intent intent = new Intent(BackdropActivity.this, NavigationViewActivity.class);
            startActivity(intent);
            finish();
        });
        button.setBackgroundResource(R.color.colorTransparent);
        button.setAllCaps(false);
        button.setTextColor(getResources().getColor(R.color.lightColorText));
        View view = new View(getApplicationContext());
        view.setLayoutParams(
                new LinearLayout.LayoutParams(
                        100, 1)
        );
        view.setBackgroundResource(R.color.lightColorText);
        nameLayout.addView(button);
        nameLayout.addView(view);
        if(mPresenter.getList() != null){
            for (Users name: mPresenter.getList()) {
                button = new Button(getApplicationContext());
                button.setText(name.getName());
                button.setLayoutParams(
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT)
                );
                button.setId(name.getId());
                button.setOnClickListener(v -> {
                    getNavigationIconView(toolbar).performClick();
                    mPresenter.getAllPostsById(name.getId());
                });
                button.setAllCaps(false);
                button.setBackgroundResource(R.color.colorTransparent);
                button.setTextColor(getResources().getColor(R.color.lightColorText));
                nameLayout.addView(button);
            }
        }
    }
    public static View getNavigationIconView(Toolbar toolbar) {
        String previousContentDescription = (String) toolbar.getNavigationContentDescription();
        boolean hadContentDescription = !TextUtils.isEmpty(previousContentDescription);
        String contentDescription = hadContentDescription ?
                previousContentDescription : "navigationIcon";
        toolbar.setNavigationContentDescription(contentDescription);
        ArrayList<View> potentialViews = new ArrayList<>();
        toolbar.findViewsWithText(potentialViews, contentDescription,
                View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        View navIcon = null;
        if (potentialViews.size() > 0) {
            navIcon = potentialViews.get(0);
        }
        if (!hadContentDescription)
            toolbar.setNavigationContentDescription(previousContentDescription);
        return navIcon;
    }
}
