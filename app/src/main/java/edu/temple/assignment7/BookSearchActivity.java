//This activity is just a search box that the user types in

package edu.temple.assignment7;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BookSearchActivity extends AppCompatActivity {

    private TextView mTextView;
    //To buttons to search and cancel
    //Then the edit text box where the user types
    Button search;
    Button cancel;
    TextView editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search);

        search = findViewById(R.id.search);
        editText = findViewById(R.id.editText);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If use clicks search it will return the value in the
                //text box and close application
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result", editText.getText().toString());

                setResult(RESULT_OK, resultIntent);
                finish();


            }
        });

        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If use clicks cancel it just ends the app
                Intent resultIntent = new Intent();
                setResult(RESULT_CANCELED, resultIntent);
                finish();


            }
        });
    }

}