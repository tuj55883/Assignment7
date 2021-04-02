package edu.temple.assignment7;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookListFragmentInterface {
    private static final String KEY = "a";
    private static final String KEY2 = "b";

    String bookKey = "book";
    static BookList myList;
    boolean container2present;
    BookDetailsFragment bookDetailsFragment;
    static int place;
    Button searchMain;
    RequestQueue requestQueue;


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

        searchMain = findViewById(R.id.searchMain);
        searchMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent launchIntent = new Intent(MainActivity.this, BookSearchActivity.class);
                startActivityForResult(launchIntent, 1);


            }
        });
        container2present = findViewById(R.id.containerLandscape) != null;



        /*getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, BookListFragment.newInstance(myList))
                .commit();*/

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
        place = position;
        if (!container2present) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, BookDetailsFragment.newInstance(myList.get(position)))
                    .addToBackStack(null)
                    .commit();
        } else {
            bookDetailsFragment.changeBook(myList.get(position));
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(KEY,place);
        outState.putParcelable(KEY2,myList);
        super.onSaveInstanceState(outState);

    }



    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
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
            myList = savedInstanceState.getParcelable(KEY2);
            Log.d("test", String.valueOf(place));
            if (!container2present&&myList != null) {

                if(place>=0) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, BookDetailsFragment.newInstance(myList.get(place)))
                            .addToBackStack(null)
                            .commit();
                } else {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, BookListFragment.newInstance(myList))
                            .commit();
                }


            }else {
                if (myList != null) {
                    bookDetailsFragment.changeBook(myList.get(place));
                }
            }
                if (container2present&&myList != null) {

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, BookListFragment.newInstance(myList))
                            .commit();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.containerLandscape, bookDetailsFragment)
                            .commit();
                    if(place>=0) {
                        bookDetailsFragment.changeBook(myList.get(place));
                    }

                }

            }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        requestQueue = Volley.newRequestQueue(this);

        if(requestCode==1){
            if(resultCode==RESULT_OK){

                String url = "https://kamorris.com/lab/cis3515/search.php?term=";
                String result = data.getStringExtra("result");
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,url+result,null,new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            myList = new BookList();
                            place = -1;
                            for(int i = 0 ; i<response.length(); i++) {

                                JSONObject book = response.getJSONObject(i);
                                String title = book.getString("title");
                                String author = book.getString("author");
                                int id = book.getInt("id");
                                String coverURL = book.getString("cover_url");
                                Book newBook = new Book(title,author,id,coverURL);
                                myList.add(newBook);
                                container2present = findViewById(R.id.containerLandscape) != null;
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.container, BookListFragment.newInstance(myList))
                                        .commit();


                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }


                });
                requestQueue.add(jsonArrayRequest);




            }
        }
    }
}
//TODO: when you type in a word that is not found the data set is not updated
//TODO: Save when closing, i think you can copy code from onrestoreinstancestate and put it in resume