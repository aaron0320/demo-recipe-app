package com.demo.simplecook.helper;

import android.content.Context;

import com.bumptech.glide.Glide;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ImageHelper {
    public static ImageHelper sInstance;

    private ImageHelper() {}

    public static ImageHelper getInstance() {
        if (sInstance == null) {
            synchronized (ImageHelper.class) {
                if (sInstance == null) {
                    sInstance = new ImageHelper();
                }
            }
        }
        return sInstance;
    }

    public LiveData<Boolean> removeGlideDiskCache(Context appContext) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        Disposable disposable = Observable.fromCallable(() -> {
            Glide.get(appContext)
                    .clearDiskCache();
            return true;
        })
                .subscribeOn(Schedulers.io())
                .subscribe(
                response -> liveData.postValue(response),
                throwable -> liveData.postValue(false)
        );
        return liveData;
    }
}
