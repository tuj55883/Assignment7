//This handles the displaying of the list of searched up books
package edu.temple.assignment7;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;



public class BookListFragment extends Fragment {


    private static final String ARG_BOOKS = "param1";



    private BookList myBookList;


    public BookListFragment() {

    }

    //This initializes the fragment
    public static BookListFragment newInstance(BookList myBookList) {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_BOOKS, myBookList);
        fragment.setArguments(args);
        return fragment;
    }
    //This passes the list of books when created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myBookList = getArguments().getParcelable(ARG_BOOKS);
        }

    }
    //Puts the list of books in a list view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ListView listView = (ListView) inflater.inflate(R.layout.fragment_book_list, container, false);

        listView.setAdapter(new BookListAdapter(getActivity(),myBookList));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((BookListFragmentInterface) getActivity()).itemClicked(position,myBookList);
            }
        });
        return listView;
    }
    //This implements the item clicked method from the main activity to return the book selected
    //from the list
    interface BookListFragmentInterface{
        public void itemClicked(int position,BookList myBookList) ;

    }
}
