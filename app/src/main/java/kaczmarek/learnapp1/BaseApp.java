package kaczmarek.learnapp1;

import android.app.Application;

import kaczmarek.learnapp1.di.components.AppComponent;
import kaczmarek.learnapp1.di.components.DaggerAppComponent;
import kaczmarek.learnapp1.di.modules.ContextModule;
import kaczmarek.learnapp1.di.services.NetworkService;

public class BaseApp extends Application {
    public static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .networkService(new NetworkService("https://jsonplaceholder.typicode.com/"))
                .build();
    }

    public static AppComponent getComponent(){
        return appComponent;
    }
}
