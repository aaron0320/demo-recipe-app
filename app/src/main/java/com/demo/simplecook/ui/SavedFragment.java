package com.demo.simplecook.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.simplecook.R;
import com.demo.simplecook.databinding.FragmentSavedBinding;

public class SavedFragment extends Fragment {
    public static final String TAG = SavedFragment.class.getName();
    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;

    private FragmentSavedBinding mBinding;

    public SavedFragment() {
    }

    @SuppressWarnings("unused")
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
        mBinding.recyclerView.setAdapter(new SavedRecipeAdapter());

        return mBinding.getRoot();
    }

}
