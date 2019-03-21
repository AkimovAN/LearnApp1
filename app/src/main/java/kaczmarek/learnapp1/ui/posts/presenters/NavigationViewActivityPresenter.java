package kaczmarek.learnapp1.ui.posts.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kaczmarek.learnapp1.BaseApp;
import kaczmarek.learnapp1.di.services.NetworkService;
import kaczmarek.learnapp1.ui.posts.models.Posts;
import kaczmarek.learnapp1.ui.posts.models.Users;
import kaczmarek.learnapp1.ui.posts.views.NavigationViewActivityView;
import retrofit2.Retrofit;


@InjectViewState
public class NavigationViewActivityPresenter extends MvpPresenter<NavigationViewActivityView> {


  private NetworkService.Api api;
    List<Users> nameUsers = new ArrayList<>();
    List<Posts> posts = new ArrayList<>();
    public NavigationViewActivityPresenter() {
        BaseApp.getComponent().inject(this);
        this.api = BaseApp.getComponent().getApi();
    }
    public List<Users> getList(){
        return nameUsers;
    }
    public void getNamesAllUsers(){
        nameUsers.clear();
        api.getNameAllUsers().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user ->{
                    nameUsers.addAll(user);
                    getViewState().updateListName();

                });
    }

    public void getAllPostsById(int id) {
       posts.clear();
       api.getAllPostsById(String.valueOf(id)).subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .doOnSubscribe(disposable -> getViewState().showSwipeRefreshLayout(true))
               .doFinally(()->getViewState().showSwipeRefreshLayout(false))
               .subscribe(post ->{
                   posts.addAll(post);
                   getViewState().updateRecyclerView();
               });
    }

    public List<Posts> getListPosts() {
        return posts;
    }
}
