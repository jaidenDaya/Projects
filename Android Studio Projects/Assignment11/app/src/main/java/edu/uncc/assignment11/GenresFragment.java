package edu.uncc.assignment11;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import edu.uncc.assignment11.databinding.FragmentGenresBinding;

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

    ArrayList<String> mGenres = Data.getAllGenres();
    GenresAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Genres");

        adapter = new GenresAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);
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

    class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.GenreViewHolder> {

        @NonNull
        @Override
        public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.simple_row_item, parent, false);
            return new GenreViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
            String genre = mGenres.get(position);
            holder.setupUI(genre);
        }

        @Override
        public int getItemCount() {
            return mGenres.size();
        }

        class GenreViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            String mGenre;
            public GenreViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.textView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoBooksForGenre(mGenre);
                    }
                });
            }

            public void setupUI(String genre){
                textView.setText(genre);
                mGenre = genre;
            }
        }
    }
}