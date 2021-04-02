package edu.temple.assignment7;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BookListAdapter extends BaseAdapter{

    Context context;
    BookList bookList;
    LinearLayout linearLayout;

    public BookListAdapter(Context context, BookList bookList){
        this.context = context;
        this.bookList = bookList;
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView textView;
        TextView author;

        if(convertView==null){

            linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(linearLayout.VERTICAL);
            textView = new TextView(context);
            textView.setTextSize(22);
            textView.setPadding(15,20,0,10);
            author = new TextView(context);
            author.setTextSize(15);
            author.setPadding(15,10,0,20);
            linearLayout.addView(textView);
            linearLayout.addView(author);
        } else {
            linearLayout = (LinearLayout) convertView;
            textView = (TextView) linearLayout.getChildAt(0);
            author = (TextView) linearLayout.getChildAt(1);

        }




        textView.setText(bookList.get(position).getTitle());
        author.setText(bookList.get(position).getAuthor());
        notifyDataSetChanged();


        return linearLayout;
    }
}
