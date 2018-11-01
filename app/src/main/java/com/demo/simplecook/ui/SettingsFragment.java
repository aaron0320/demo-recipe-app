package com.demo.simplecook.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.demo.simplecook.R;
import com.demo.simplecook.databinding.FragmentSavedBinding;
import com.demo.simplecook.databinding.FragmentSettingsBinding;
import com.demo.simplecook.ui.listener.OnRecipeClickListener;
import com.demo.simplecook.viewmodel.SavedViewModel;
import com.demo.simplecook.viewmodel.SettingsViewModel;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import static com.demo.simplecook.ui.RecipeDetailsActivity.INTENT_KEY_RECIPE;

public class SettingsFragment extends Fragment {
    public static final String TAG = SettingsFragment.class.getName();

    private FragmentSettingsBinding mBinding;
    private SettingsViewModel mViewModel;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);
        mBinding.setLifecycleOwner(this);
        mBinding.setOnDropAllLocalRecipesCallback(mOnDropAllLocalRecipesClickListener);
        mBinding.setOnClearDiskCacheCallback(mOnClearDiskCacheClickListener);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
    }

    private View.OnClickListener mOnDropAllLocalRecipesClickListener = view -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            mViewModel.dropAllLocalRecipes().observe(this, success -> {
                if (success) {
                    Toast.makeText(getActivity(),
                            getString(R.string.drop_local_recipes_success),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(),
                            getString(R.string.drop_local_recipes_failed),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    private View.OnClickListener mOnClearDiskCacheClickListener = view -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            mViewModel.clearDiskCache().observe(this, success -> {
                if (success) {
                    Toast.makeText(getActivity(),
                            getString(R.string.clear_disk_cache_success),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(),
                            getString(R.string.clear_disk_cache_failed),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

}
