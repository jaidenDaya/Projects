package edu.uncc.assignment10;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import edu.uncc.assignment10.databinding.FragmentSelectCategoryBinding;


public class SelectCategoryFragment extends Fragment {

    String[] mCategories = {"Housing", "Transportation", "Food", "Health", "Other"};

    public SelectCategoryFragment() {
        // Required empty public constructor
    }

    FragmentSelectCategoryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ArrayAdapter<String> adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Category");
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mCategories);
        binding.listView.setAdapter(adapter);

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String category = mCategories[position];
                mListener.selectCategory(category);
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelSelectCategory();
            }
        });


    }

    SelectCategoryListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SelectCategoryListener) {
            mListener = (SelectCategoryListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectCategoryListener");
        }
    }

    interface SelectCategoryListener {
        void selectCategory(String category);
        void onCancelSelectCategory();
    }

}