package com.demo.simplecook;

import android.app.Application;
import android.util.Log;

import com.bumptech.glide.annotation.GlideOption;
import com.demo.simplecook.api.EdamamApiService;
import com.demo.simplecook.api.EdamamRetrofitClient;
import com.demo.simplecook.repository.LocalRecipeDataSource;
import com.demo.simplecook.repository.RecipeRepository;
import com.demo.simplecook.repository.RemoteRecipeDataSource;

import timber.log.Timber;

public class SimpleCookApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    public static RecipeRepository getRecipeRepository() {
        return RecipeRepository.getInstance(
                LocalRecipeDataSource.getInstance(),
                RemoteRecipeDataSource.getInstance(getEdamamApiService())
        );
    }

    public static EdamamApiService getEdamamApiService() {
        return EdamamRetrofitClient.getRetrofitInstance().create(EdamamApiService.class);
    }

    /**
     * A tree which logs important information for crash reporting.
     */
    private static final class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            // FIXME - Integrate with external crash reporting service, e.g. Crashlytics
//            FakeCrashLibrary.log(priority, tag, message);
//
//            if (t != null) {
//                if (priority == Log.ERROR) {
//                    FakeCrashLibrary.logError(t);
//                } else if (priority == Log.WARN) {
//                    FakeCrashLibrary.logWarning(t);
//                }
//            }
        }
    }
}