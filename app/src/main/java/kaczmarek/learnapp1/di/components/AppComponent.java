package kaczmarek.learnapp1.di.components;

import javax.inject.Singleton;
import dagger.Component;
import kaczmarek.learnapp1.di.modules.ContextModule;
import kaczmarek.learnapp1.di.services.NetworkService;
import kaczmarek.learnapp1.ui.posts.presenters.BackdropActivityPresenter;
import kaczmarek.learnapp1.ui.posts.presenters.NavigationViewActivityPresenter;

@Singleton
@Component(modules = {ContextModule.class, NetworkService.class})
public interface AppComponent {
    NetworkService.Api getApi();
    void inject(NavigationViewActivityPresenter navigationViewActivityPresenter);
    void inject(BackdropActivityPresenter backdropActivityPresenter);
}
