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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.uncc.assignment09.databinding.FragmentBooksBinding;

// Assignment 9
// Group 7: Nhu Nguyen and Jaiden Daya
public class BooksFragment extends Fragment {
    private static final String ARG_PARAM_GENRE = "ARG_PARAM_GENRE";
    private String mGenre;

    public static BooksFragment newInstance(String genre) {
        BooksFragment fragment = new BooksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_GENRE, genre);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGenre = getArguments().getString(ARG_PARAM_GENRE);
        }
    }

    ArrayList<Book> mBooks = new ArrayList<>();

    public BooksFragment() {
        // Required empty public constructor
    }

    FragmentBooksBinding binding;
    BooksAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBooksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Books");
        mBooks.clear();
        mBooks.addAll(Data.getBooksByGenre(mGenre));
        adapter = new BooksAdapter(getActivity(), mBooks);
        binding.listView.setAdapter(adapter);
        binding.genreSelected.setText(mGenre);

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = mBooks.get(position);
                mListener.gotoBookDetails(book);
            }
        });

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.closeBooks();
            }
        });
    }



    class BooksAdapter extends ArrayAdapter<Book> {

        public BooksAdapter(@NonNull Context context, @NonNull List<Book> objects) {
            super(context, R.layout.single_book_details, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.single_book_details, parent, false);
            }
            TextView book_title = convertView.findViewById(R.id.book_title);
            TextView book_author = convertView.findViewById(R.id.book_author);
            TextView book_genre = convertView.findViewById(R.id.book_genre);
            TextView book_year = convertView.findViewById(R.id.book_year);

            Book book = getItem(position);

            book_title.setText(book.getTitle());
            book_author.setText(book.getAuthor());
            book_genre.setText(book.getGenre());
            book_year.setText(String.valueOf(book.getYear()));

            return convertView;
        }
    }

    BooksListener mListener;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BooksListener) {
            mListener = (BooksListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement BooksListener");
        }
    }

    interface BooksListener{
        void closeBooks();
        void gotoBookDetails(Book book);
    }
}