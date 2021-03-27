package edu.temple.assignment7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookListFragmentInterface {
    BookList myList;
    boolean container2present;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container2present = findViewById(R.id.containerLandscape) != null;

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

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, BookListFragment.newInstance( myList ))
                .commit();


    }

    @Override
    public void itemClicked(int position, BookList myList) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,BookDetailsFragment.newInstance(myList.get(position)))
                .addToBackStack(null)
                .commit();
    }
}