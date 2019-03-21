package kaczmarek.learnapp1.ui.posts.views;

import com.arellomobile.mvp.MvpView;

public interface NavigationViewActivityView extends MvpView {

     void updateListName();
     void updateRecyclerView();
     void showSwipeRefreshLayout(Boolean refreshing);
}
