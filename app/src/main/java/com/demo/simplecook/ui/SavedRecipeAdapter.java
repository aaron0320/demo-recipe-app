package com.demo.simplecook.ui;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.demo.simplecook.R;
import com.demo.simplecook.databinding.ItemSavedRecipeBinding;
import com.demo.simplecook.model.Recipe;
import com.demo.simplecook.ui.listener.OnRecipeClickListener;

import java.util.List;
import java.util.Objects;

public class SavedRecipeAdapter extends RecyclerView.Adapter<SavedRecipeAdapter.SavedRecipeViewHolder> {
    private OnRecipeClickListener mOnRecipeClickListener;

    private List<Recipe> mSavedRecipes;

    public SavedRecipeAdapter(OnRecipeClickListener onRecipeClickListener) {
        this.mOnRecipeClickListener = onRecipeClickListener;
    }

    @Override
    public SavedRecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemSavedRecipeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_saved_recipe, parent, false);
        binding.setCallback(mOnRecipeClickListener);
        return new SavedRecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final SavedRecipeViewHolder holder, int position) {
        holder.mBinding.setRecipe(mSavedRecipes.get(position));
        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mSavedRecipes == null ? 0 : mSavedRecipes.size();
    }

    public void setSavedRecipes(List<Recipe> savedRecipes) {
        if (mSavedRecipes == null) {
            mSavedRecipes = savedRecipes;
            if (mSavedRecipes != null) {
                notifyItemRangeInserted(0, mSavedRecipes.size());
            } else {
                notifyDataSetChanged();
            }
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mSavedRecipes.size();
                }

                @Override
                public int getNewListSize() {
                    return savedRecipes.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return Objects.equals(mSavedRecipes.get(oldItemPosition).getWebUrl(),
                            savedRecipes.get(newItemPosition).getWebUrl());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return Objects.equals(mSavedRecipes.get(oldItemPosition).getWebUrl(),savedRecipes.get(newItemPosition).getWebUrl()) &&
                            Objects.equals(mSavedRecipes.get(oldItemPosition).getLabel(), savedRecipes.get(newItemPosition).getLabel()) &&
                            Objects.equals(mSavedRecipes.get(oldItemPosition).getImageUrl(), savedRecipes.get(newItemPosition).getImageUrl()) &&
                            Objects.equals(mSavedRecipes.get(oldItemPosition).getSource(), savedRecipes.get(newItemPosition).getSource());
                }
            });
            mSavedRecipes = savedRecipes;
            result.dispatchUpdatesTo(this);
        }
    }

    public class SavedRecipeViewHolder extends RecyclerView.ViewHolder {
        private ItemSavedRecipeBinding mBinding;

        public SavedRecipeViewHolder(ItemSavedRecipeBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
    }
}
