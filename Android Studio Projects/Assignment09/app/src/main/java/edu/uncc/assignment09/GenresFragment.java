package edu.uncc.assignment09;

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

import java.util.ArrayList;

import edu.uncc.assignment09.databinding.FragmentGenresBinding;

// Assignment 9
// Group 7: Nhu Nguyen and Jaiden Daya
public class GenresFragment extends Fragment {
    public GenresFragment() {
        // Required empty public constructor
    }

    FragmentGenresBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGenresBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ArrayAdapter<String> adapter;

    ArrayList<String> mGenres = Data.getAllGenres();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Genres");
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mGenres);
        binding.listView.setAdapter(adapter);

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String genre = mGenres.get(i);
                mListener.gotoBooksForGenre(genre);
            }
        });
    }

    GenresListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof GenresListener) {
            mListener = (GenresListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement GenresListener");
        }
    }

    interface GenresListener {
        void gotoBooksForGenre(String genre);
    }
}