package edu.temple.assignment7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookListFragmentInterface {
    BookList myList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing and populating the books we will use
        BookList myList = new BookList();
        myList.add(new Book("To Kill A Mockingbird", "Harper Lee"));//1
        myList.add(new Book("Great Expectations", "Charles Dickens"));//2
        myList.add(new Book("Lolita", "Vladimir Nabokov"));//3
        myList.add(new Book("Lord of the Flies", "William Golding"));//4
        myList.add(new Book("The Scarlet Letter", "Nathaniel Hawthorne"));//5
        myList.add(new Book("The Catcher in the Rye", "JD Salinger"));//6
        myList.add(new Book("Wuthering Heights", "Emily Bronte"));//7
        myList.add(new Book("Lady Chatterley's Lover", "DH Lawrence"));//8
        myList.add(new Book("The Handmaid's Tale", "Margaret Atwood"));//9
        myList.add(new Book("The Great Gatsby", "F Scott Fitzgerald"));//10

        Fragment myFragment = new Fragment();
        Bundle myBundle = new Bundle();
        myBundle.putInt("id",123);
        myFragment.setArguments(myBundle);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, BookListFragment.newInstance(myList))
                .commit();


    }

    @Override
    public void itemClicked(int position) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,BookDetailsFragment.newInstance(myList.get(position)))
                .addToBackStack(null)
                .commit();
    }
}