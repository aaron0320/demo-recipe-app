package com.demo.simplecook.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.demo.simplecook.R;
import com.demo.simplecook.databinding.FragmentExploreBinding;
import com.demo.simplecook.model.Recipe;
import com.demo.simplecook.ui.listener.OnRecipeClickListener;
import com.demo.simplecook.viewmodel.ExploreViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;

import static com.demo.simplecook.ui.RecipeDetailsActivity.INTENT_KEY_RECIPE;

public class ExploreFragment extends Fragment {
    public static final String TAG = ExploreFragment.class.getName();

    private FragmentExploreBinding mBinding;
    private RecipeAdapter mRecipeAdapter;

    private ExploreViewModel mViewModel;

    public ExploreFragment() {
    }

    public static ExploreFragment newInstance() {
        ExploreFragment fragment = new ExploreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_explore, container, false);
        mRecipeAdapter = new RecipeAdapter(getContext(), mOnRecipeClickListener);
        mBinding.recyclerView.setAdapter(mRecipeAdapter);

        ArrayAdapter<CharSequence> foodChoiceAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.food_choice_array, R.layout.spinner_item_simple_cook);
        foodChoiceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.foodChoiceSpinner.setAdapter(foodChoiceAdapter);
        mBinding.foodChoiceSpinner.setSelection(0, false);
        mBinding.foodChoiceSpinner.setOnItemSelectedListener(mOnSpinnerSelectedListener);

        ArrayAdapter<CharSequence> prepTimeChoiceAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.prep_time_choice_array, R.layout.spinner_item_simple_cook);
        foodChoiceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.prepTimeChoiceSpinner.setAdapter(prepTimeChoiceAdapter);
        mBinding.prepTimeChoiceSpinner.setSelection(0, false);
        mBinding.prepTimeChoiceSpinner.setOnItemSelectedListener(mOnSpinnerSelectedListener);

        ArrayAdapter<CharSequence> dietChoiceAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.diet_choice_array, R.layout.spinner_item_simple_cook);
        foodChoiceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.dietChoiceSpinner.setAdapter(dietChoiceAdapter);
        mBinding.dietChoiceSpinner.setSelection(0, false);
        mBinding.dietChoiceSpinner.setOnItemSelectedListener(mOnSpinnerSelectedListener);

        mBinding.errorLayout.retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subscribeUI();
            }
        });

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(ExploreViewModel.class);
        subscribeUI();
    }

    private void subscribeUI() {
        String query = mBinding.foodChoiceSpinner.getSelectedItem().toString();
        String time = mBinding.prepTimeChoiceSpinner.getSelectedItem().toString();
        String diet = mBinding.dietChoiceSpinner.getSelectedItem().toString();

        mBinding.setIsLoading(true);
        mBinding.setIsError(false);

        // FIXME - Remove previous observer here
        mViewModel.getRemoteRecipes(query, time, diet)
                .observe(this, recipeResultWrapper -> {
                    if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                        if (recipeResultWrapper.isSucess() && recipeResultWrapper.getRecipes().size() > 0) {
                            mBinding.setIsLoading(false);
                            mRecipeAdapter.setRecipes(recipeResultWrapper.getRecipes());
                        } else if (recipeResultWrapper.isSucess() && recipeResultWrapper.getRecipes().size() == 0) {
                            mBinding.setIsLoading(false);
                            mBinding.setIsError(true);
                            mBinding.errorLayout.setErrorMsg(getString(R.string.load_recipe_no_result));
                        } else {
                            mBinding.setIsLoading(false);
                            mBinding.setIsError(true);
                            if (recipeResultWrapper.getCode() == 401) {
                                mBinding.errorLayout.setErrorMsg(getString(R.string.load_recipe_error_exceed_limit));
                            } else {
                                mBinding.errorLayout.setErrorMsg(getString(R.string.load_recipe_error_general));
                            }
                        }
                        mBinding.executePendingBindings();
                    }
                });
    }

    private AdapterView.OnItemSelectedListener mOnSpinnerSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                subscribeUI();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) { }
    };

    private OnRecipeClickListener mOnRecipeClickListener = new OnRecipeClickListener() {
        @Override
        public void onRecipeClick(Recipe recipe) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                Intent intent = new Intent(getContext(), RecipeDetailsActivity.class);
                intent.putExtra(INTENT_KEY_RECIPE, recipe);
                startActivity(intent);
            }
        }
    };
}
