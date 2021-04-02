package edu.temple.assignment7;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class BookDetailsFragment extends Fragment {

    private static final String ARG_BOOK = "param1";
    TextView textView1;
    TextView textView2;
    ImageView imageView;




    private Book book;


    public BookDetailsFragment() {

    }


    public static BookDetailsFragment newInstance(Book book) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_BOOK, book);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            book = getArguments().getParcelable(ARG_BOOK);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_book_details, container, false);
        textView1 = layout.findViewById(R.id.textView);
        textView2 = layout.findViewById(R.id.textView2);
        imageView = layout.findViewById(R.id.imageView);

        if (book!= null){
            changeBook(book);
        }

        return layout;
    }

    public void changeBook(Book book){
        textView1.setText(book.getTitle());
        textView2.setText("Author: "+book.getAuthor());
        Picasso.get().load(Uri.parse(book.getCoverURL())).into(imageView);
    }
}