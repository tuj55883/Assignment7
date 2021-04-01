package edu.temple.assignment7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookListFragmentInterface {
    private static final String KEY = "a";

    String bookKey = "book";
    BookList myList;
    boolean container2present;
    BookDetailsFragment bookDetailsFragment;
    static int place;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Initializing and populating the books we will use
        /*BookList myList = new BookList();
        myList.add(new Book("To Kill A Mockingbird", "Harper Lee"));//1
        myList.add(new Book("Great Expectations", "Charles Dickens"));//2
        myList.add(new Book("Lolita", "Vladimir Nabokov"));//3
        myList.add(new Book("Lord of the Flies", "William Golding"));//4
        myList.add(new Book("The Scarlet Letter", "Nathaniel Hawthorne"));//5
        myList.add(new Book("The Catcher in the Rye", "JD Salinger"));//6
        myList.add(new Book("Wuthering Heights", "Emily Bronte"));//7
        myList.add(new Book("Lady Chatterley's Lover", "DH Lawrence"));//8
        myList.add(new Book("The Handmaid's Tale", "Margaret Atwood"));//9
        myList.add(new Book("The Great Gatsby", "F Scott Fitzgerald"));//10*/

        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);

        container2present = findViewById(R.id.containerLandscape) != null;



        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, BookListFragment.newInstance(myList))
                .commit();

        if (container2present) {
            bookDetailsFragment = new BookDetailsFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerLandscape, bookDetailsFragment)
                    .commit();
        }


    }

    @Override
    public void itemClicked(int position, BookList myList) {
        if (!container2present) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, BookDetailsFragment.newInstance(myList.get(position)))
                    .addToBackStack(null)
                    .commit();
        } else {
            bookDetailsFragment.changeBook(myList.get(position));
        }
        place = position;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(KEY,place);
        super.onSaveInstanceState(outState);

    }



    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        BookList myList = new BookList();
        /*myList.add(new Book("To Kill A Mockingbird", "Harper Lee"));//1
        myList.add(new Book("Great Expectations", "Charles Dickens"));//2
        myList.add(new Book("Lolita", "Vladimir Nabokov"));//3
        myList.add(new Book("Lord of the Flies", "William Golding"));//4
        myList.add(new Book("The Scarlet Letter", "Nathaniel Hawthorne"));//5
        myList.add(new Book("The Catcher in the Rye", "JD Salinger"));//6
        myList.add(new Book("Wuthering Heights", "Emily Bronte"));//7
        myList.add(new Book("Lady Chatterley's Lover", "DH Lawrence"));//8
        myList.add(new Book("The Handmaid's Tale", "Margaret Atwood"));//9
        myList.add(new Book("The Great Gatsby", "F Scott Fitzgerald"));//10*/


        if (savedInstanceState != null) {
            place = savedInstanceState.getInt(KEY);
            if (!container2present) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, BookDetailsFragment.newInstance(myList.get(place)))
                        .addToBackStack(null)
                        .commit();
            } else {
                bookDetailsFragment.changeBook(myList.get(place));
            }
    }
    }
}