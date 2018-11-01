package com.demo.simplecook.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import timber.log.Timber;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.simplecook.R;
import com.demo.simplecook.databinding.FragmentSavedBinding;
import com.demo.simplecook.ui.listener.OnRecipeClickListener;
import com.demo.simplecook.viewmodel.SavedViewModel;

import static com.demo.simplecook.ui.RecipeDetailsActivity.INTENT_KEY_RECIPE;

public class SavedFragment extends Fragment {
    public static final String TAG = SavedFragment.class.getName();
    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;

    private FragmentSavedBinding mBinding;
    private SavedViewModel mViewModel;

    private SavedRecipeAdapter mSavedRecipeAdapter;

    public static SavedFragment newInstance(int columnCount) {
        SavedFragment fragment = new SavedFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_saved, container, false);
        mBinding.setLifecycleOwner(this);

        mBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
        mSavedRecipeAdapter = new SavedRecipeAdapter(mOnRecipeClickListener);
        mBinding.recyclerView.setAdapter(mSavedRecipeAdapter);
        // No retry needed for local recipes
        mBinding.errorLayout.retryButton.setVisibility(View.GONE);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SavedViewModel.Factory factory = new SavedViewModel.Factory(getActivity().getApplication());
        mViewModel = ViewModelProviders.of(this, factory).get(SavedViewModel.class);
        mBinding.setViewModel(mViewModel);
        subscribeUI();
    }

    private void subscribeUI() {
        mViewModel.getLocalRecipes()
                .observe(this, recipes -> {
                    mSavedRecipeAdapter.setSavedRecipes(recipes);
                });
    }

    private OnRecipeClickListener mOnRecipeClickListener = recipe -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            Intent intent = new Intent(getContext(), RecipeDetailsActivity.class);
            intent.putExtra(INTENT_KEY_RECIPE, recipe);
            startActivity(intent);
        }
    };

}
