package kaczmarek.learnapp1.ui.posts.views;

import com.arellomobile.mvp.MvpView;

public interface BackdropActivityView extends MvpView {
    void updateRecyclerView();

    void showSwipeRefreshLayout(boolean refreshing);

    void updateListName();
}
