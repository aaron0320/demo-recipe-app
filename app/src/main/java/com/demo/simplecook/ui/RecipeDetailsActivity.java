package com.demo.simplecook.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.demo.simplecook.R;
import com.demo.simplecook.databinding.ActivityRecipeDetailsBindingImpl;
import com.demo.simplecook.model.Recipe;
import com.demo.simplecook.ui.listener.OnRecipeClickListener;
import com.demo.simplecook.viewmodel.RecipeDetailsViewModel;

public class RecipeDetailsActivity extends AppCompatActivity {
    public static final String INTENT_KEY_RECIPE = "INTENT_KEY_RECIPE";

    private ActivityRecipeDetailsBindingImpl mBinding;
    private RecipeDetailsViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Recipe recipe = getIntent().getParcelableExtra(INTENT_KEY_RECIPE);
        if (recipe == null) {
            // Recipe should never be null
            finish();
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_details);
        mBinding.setLifecycleOwner(this);
        mBinding.setRecipe(recipe);
        mBinding.setSaveRecipeClickCallback(mOnSaveRecipeClickListener);
        mBinding.setDeleteRecipeClickCallback(mOnDeleteRecipeClickListener);

        mViewModel = ViewModelProviders.of(this).get(RecipeDetailsViewModel.class);
        subscribeUI();
    }

    private void subscribeUI() {
        mViewModel.getLocalRecipe(mBinding.getRecipe().getWebUrl())
                .observe(this, recipe -> {
                    if (recipe != null) {
                        mBinding.saveButton.setVisibility(View.GONE);
                        mBinding.deleteButton.setVisibility(View.VISIBLE);
                    } else {
                        mBinding.saveButton.setVisibility(View.VISIBLE);
                        mBinding.deleteButton.setVisibility(View.GONE);
                    }
                });
    }

    private OnRecipeClickListener mOnSaveRecipeClickListener = (recipe) -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            mViewModel.saveLocalRecipe(recipe).observe(RecipeDetailsActivity.this, (success) -> {
                if (success) {
                    Toast.makeText(RecipeDetailsActivity.this,
                            getString(R.string.save_recipe_success),
                            Toast.LENGTH_SHORT).show();
                    mBinding.saveButton.setVisibility(View.GONE);
                    mBinding.deleteButton.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(RecipeDetailsActivity.this,
                            getString(R.string.save_recipe_failed),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    private OnRecipeClickListener mOnDeleteRecipeClickListener = (recipe) -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            mViewModel.deleteLocalRecipe(recipe).observe(RecipeDetailsActivity.this, (success) -> {
                if (success) {
                    Toast.makeText(RecipeDetailsActivity.this,
                            getString(R.string.delete_recipe_success),
                            Toast.LENGTH_SHORT).show();
                    mBinding.saveButton.setVisibility(View.VISIBLE);
                    mBinding.deleteButton.setVisibility(View.GONE);
                } else {
                    Toast.makeText(RecipeDetailsActivity.this,
                            getString(R.string.delete_recipe_failed),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
}
