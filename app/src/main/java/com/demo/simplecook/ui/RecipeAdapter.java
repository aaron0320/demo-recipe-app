package com.demo.simplecook.ui;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.demo.simplecook.R;
import com.demo.simplecook.databinding.ItemRecipeBinding;
import com.demo.simplecook.model.Recipe;
import com.demo.simplecook.ui.listener.OnRecipeClickListener;

import java.util.List;
import java.util.Objects;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import timber.log.Timber;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private OnRecipeClickListener mOnRecipeClickListener;

    private List<Recipe> mRecipes;

    public RecipeAdapter(OnRecipeClickListener onRecipeClickListener) {
        this.mOnRecipeClickListener = onRecipeClickListener;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRecipeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_recipe, parent, false);
        binding.setCallback(mOnRecipeClickListener);
        return new RecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final RecipeViewHolder holder, int position) {
        holder.mBinding.setRecipe(mRecipes.get(position));
        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mRecipes == null ? 0 : mRecipes.size();
    }

    public void setRecipes(List<Recipe> recipes) {
        if (mRecipes == null) {
            mRecipes = recipes;
            if (mRecipes != null) {
                notifyItemRangeInserted(0, mRecipes.size());
            } else {
                notifyDataSetChanged();
            }
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mRecipes.size();
                }

                @Override
                public int getNewListSize() {
                    return recipes.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return Objects.equals(mRecipes.get(oldItemPosition).getWebUrl(),
                            recipes.get(newItemPosition).getWebUrl());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return Objects.equals(mRecipes.get(oldItemPosition).getWebUrl(),recipes.get(newItemPosition).getWebUrl()) &&
                            Objects.equals(mRecipes.get(oldItemPosition).getLabel(), recipes.get(newItemPosition).getLabel()) &&
                            Objects.equals(mRecipes.get(oldItemPosition).getImageUrl(), recipes.get(newItemPosition).getImageUrl()) &&
                            Objects.equals(mRecipes.get(oldItemPosition).getSource(), recipes.get(newItemPosition).getSource());
                }
            });
            mRecipes = recipes;
            result.dispatchUpdatesTo(this);
        }
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        private ItemRecipeBinding mBinding;

        public RecipeViewHolder(ItemRecipeBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
    }
}
