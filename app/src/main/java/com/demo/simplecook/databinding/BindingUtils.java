package com.demo.simplecook.databinding;

import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import timber.log.Timber;

public class BindingUtils {

    @BindingAdapter("android:progress")
    public static void setFloat(ProgressBar view, float value) {
        view.setProgress((int) value);
    }

    @InverseBindingAdapter(attribute = "android:progress")
    public static float getFloat(ProgressBar view) {
        return  view.getProgress();
    }

    @BindingAdapter("app:glide_src")
    public static void setUri(ImageView view, String uri) {
        Glide.with(view)
                .load(uri)
                .into(view);
    }

    @BindingAdapter("app:text_list")
    public static void setStringList(TextView view, List<String> stringList) {
        if (stringList != null && stringList.size() > 0) {
            view.setText("•" + TextUtils.join("\n•", stringList));
        }
    }
}