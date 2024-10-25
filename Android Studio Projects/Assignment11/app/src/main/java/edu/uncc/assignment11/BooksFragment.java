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

import edu.uncc.assignment11.databinding.FragmentBooksBinding;
import edu.uncc.assignment11.databinding.SingleBookItemBinding;

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
    private int currentIndex = 0;

    public BooksFragment() {
        // Required empty public constructor
    }

    FragmentBooksBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBooksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    BooksAdapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Books");
        mBooks.clear();
        mBooks.addAll(Data.getBooksByGenre(mGenre));

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.closeBooks();
            }
        });

        adapter = new BooksAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);

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

    class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {
        @NonNull
        @Override
        public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.single_book_item, parent, false);
            return new BookViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
            Book book = mBooks.get(position);
            holder.setupUI(book);
        }

        @Override
        public int getItemCount() {
            return mBooks.size();
        }

        class BookViewHolder extends RecyclerView.ViewHolder {
            TextView bookTitle, bookAuthor, bookGenre, bookYear;
            Book mBook;

            public BookViewHolder(@NonNull View itemView) {
                super(itemView);
                bookTitle = itemView.findViewById(R.id.book_title);
                bookAuthor = itemView.findViewById(R.id.book_author);
                bookGenre = itemView.findViewById(R.id.book_genre);
                bookYear = itemView.findViewById(R.id.book_year);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoBookDetails(mBook);
                    }
                });
            }

            public void setupUI(Book book) {
                mBook = book;
                bookTitle.setText(mBook.getTitle());
                bookAuthor.setText(mBook.getAuthor());
                bookGenre.setText(mBook.getGenre());
                bookYear.setText(String.valueOf(mBook.getYear()));

            }
        }
    }


    interface BooksListener{
        void closeBooks();
        void gotoBookDetails(Book book);
    }
}