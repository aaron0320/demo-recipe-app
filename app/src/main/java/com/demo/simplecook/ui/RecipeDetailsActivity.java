package com.demo.simplecook.ui;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import timber.log.Timber;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.demo.simplecook.R;
import com.demo.simplecook.databinding.ActivityRecipeDetailsBindingImpl;
import com.demo.simplecook.model.Recipe;
import com.demo.simplecook.ui.listener.OnRecipeClickListener;

public class RecipeDetailsActivity extends AppCompatActivity {
    public static final String INTENT_KEY_RECIPE = "INTENT_KEY_RECIPE";

    private ActivityRecipeDetailsBindingImpl mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_details);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        mBinding.setRecipe(getIntent().getParcelableExtra(INTENT_KEY_RECIPE));
        mBinding.setCallback(mOnRecipeClickListener);
    }

    OnRecipeClickListener mOnRecipeClickListener = new OnRecipeClickListener() {
        @Override
        public void onRecipeClick(Recipe recipe) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                Timber.e("Recipe click");
            }
        }
    };
}
