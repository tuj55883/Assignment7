//This app does a search based on the users input for books
//then it returns the list of books that matches by author or
//title and the user can then click on it to see the cover
package edu.temple.assignment7;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
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

import edu.temple.audiobookplayer.AudiobookService;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookListFragmentInterface, ControlFragment.ControlFragmentInterface  {
    //Makes keys for the booklist and book selected that we will save
    private static final String KEY = "a";
    private static final String KEY2 = "b";
    private static final String KEY3 = "c";
    private static final String KEY4 = "d";
    private static final String KEY5 = "e";

    //Initializes all the values we will use
    String bookKey = "book";
    static BookList myList;
    boolean container2present;
    BookDetailsFragment bookDetailsFragment;
    ControlFragment audioControlFragment;
    static int place = -1;
    static int bookLength;
    static int spot;
    static String name;
    static int max;
    Button searchMain;
    RequestQueue requestQueue;
    static boolean canPlay = false;
    AudiobookService.MediaControlBinder myService;
    boolean isConnected;


    Handler myHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            ControlFragment fragment = (ControlFragment) getSupportFragmentManager().
                    findFragmentById(R.id.audioControlContainer);
            canPlay =false;
            if(myService.isPlaying()&&((AudiobookService.BookProgress)msg.obj).getProgress()>=0&& max>=((AudiobookService.BookProgress)msg.obj).getProgress()){
                canPlay =false;

                fragment.seekBar.setProgress(((AudiobookService.BookProgress) msg.obj).getProgress());
                spot = fragment.seekBar.getProgress();

            }

            canPlay = true;

            return true;
        }
    });


    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myService = (AudiobookService.MediaControlBinder) service;
            myService.setProgressHandler(myHandler);
            isConnected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isConnected = false;
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This is the search button
        //When clicked it launches the bookSearchActivty
        //the activity returns the user search
        //Go to activity result to see the outcome of the search
        searchMain = findViewById(R.id.searchMain);
        Intent serviceIntent = new Intent(MainActivity.this, AudiobookService.class);
        bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE);

        audioControlFragment = new ControlFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.audioControlContainer, audioControlFragment)
                .commit();

        searchMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent launchIntent = new Intent(MainActivity.this, BookSearchActivity.class);
                startActivityForResult(launchIntent, 1);
                if(myService.isPlaying()){
                    myService.stop();
                    ControlFragment fragment = (ControlFragment) getSupportFragmentManager().
                            findFragmentById(R.id.audioControlContainer);
                    fragment.textView.setText("");
                    name = "";
                }


            }
        });
        container2present = findViewById(R.id.containerLandscape) != null;



        //This just makes the book details fragment for landscape mode
        if (container2present) {
            bookDetailsFragment = new BookDetailsFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerLandscape, bookDetailsFragment)
                    .commit();
        }


    }

    @Override
    public void playClicked(){

        if(isConnected&& place!=-1){
            myService.play(myList.get(place).getId());
            ControlFragment fragment = (ControlFragment) getSupportFragmentManager().
                    findFragmentById(R.id.audioControlContainer);
            fragment.textView.setText("Now Playing: "+myList.get(place).getTitle());
            name="Now Playing: "+myList.get(place).getTitle();

        }
    }

    @Override
    public void pauseClicked() {
        if(isConnected && place!=-1){
            myService.pause();

        }
    }

    @Override
    public void stopClicked() {
        if(isConnected && place!=-1){
            myService.stop();
            ControlFragment fragment = (ControlFragment) getSupportFragmentManager().
                    findFragmentById(R.id.audioControlContainer);
            fragment.textView.setText("");
            name = "";
            spot = 0;

        }
    }

    @Override
    public void timeChanged(int progress) {
        if(isConnected && place!=-1&& canPlay) {
            myService.seekTo(progress);

        }
    }


    //This is the selection of the book from the list
    //When clicked it will display the book selected
    @Override
    public void itemClicked(int position, BookList myList) {
        //This sets the place if the app is restarted
        place = position;
        bookLength = myList.get(position).getDuration();
        canPlay = false;
        ControlFragment fragment = (ControlFragment) getSupportFragmentManager().
                findFragmentById(R.id.audioControlContainer);
        fragment.seekBar.setProgress(0);
        spot = 0;
        fragment.seekBar.setMax(myList.get(position).getDuration());
        max = myList.get(position).getDuration();
        fragment.textView.setText("");
        name = "";
        if(myService.isPlaying()) {
            myService.stop();
        }
        canPlay = true;

        //This checks weather to put the display fragment in
        //container one if in portrait or container 2 in landscape
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
    //Saves the book list and placement when rotated
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(KEY,place);
        outState.putParcelable(KEY2,myList);
        outState.putString(KEY3,name);
        outState.putInt(KEY4,spot);
        outState.putInt(KEY5,max);

        super.onSaveInstanceState(outState);

    }


    //This sets the fragments when the app is rotated
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        //All these ifs are to check which fragment to put it into
        if (savedInstanceState != null) {
            place = savedInstanceState.getInt(KEY);
            myList = savedInstanceState.getParcelable(KEY2);
            name = savedInstanceState.getString(KEY3);
            spot = savedInstanceState.getInt(KEY4);
            max = savedInstanceState.getInt(KEY5);
            canPlay = false;
            ControlFragment fragment = (ControlFragment) getSupportFragmentManager().
                    findFragmentById(R.id.audioControlContainer);
            fragment.seekBar.setMax(max);
            fragment.seekBar.setProgress(spot);
            canPlay = true;
            fragment.textView.setText(name);
            if (!container2present&&myList != null) {

                if(place>=0) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, BookDetailsFragment.newInstance(myList.get(place)))
                            .commit();
                } else {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, BookListFragment.newInstance(myList))
                            .commit();
                }


            }else {
                if (myList != null && myList.size() != 0 && place !=-1) {
                    bookDetailsFragment.changeBook(myList.get(place));
                }
            }
                if (container2present&&myList != null && place !=-1) {

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
        //Makes sure result is good
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                //Initializes the url, result and json
                String url = "https://kamorris.com/lab/cis3515/search.php?term=";
                String result = data.getStringExtra("result");
                //combines the url and result into one search url
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,url+result,null,new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            //Makes a brand new book list and sets place to -1 so
                            //it resets the book selection
                            myList = new BookList();
                            place = -1;
                            for(int i = 0 ; i<response.length(); i++) {
                                //goes through each book one by one and adds it to the list

                                JSONObject book = response.getJSONObject(i);
                                String title = book.getString("title");
                                String author = book.getString("author");
                                int id = book.getInt("id");
                                int duration = book.getInt("duration");
                                String coverURL = book.getString("cover_url");

                                Book newBook = new Book(title,author,id,coverURL,duration);
                                myList.add(newBook);

                            }
                            //checks if container 2 is open
                            container2present = findViewById(R.id.containerLandscape) != null;
                            //makes the a brand new book list from the search
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.container, BookListFragment.newInstance(myList))
                                    .commit();
                            //if container 2 is present makes a new one of them
                            if(container2present) {
                                bookDetailsFragment.makeEmpty();

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


