package edu.temple.assignment7;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class BookList implements Parcelable {
    private ArrayList<Book> list = new ArrayList<>();


    public BookList(){
    }

    protected BookList(Parcel in) {
    }

    public static final Creator<BookList> CREATOR = new Creator<BookList>() {
        @Override
        public BookList createFromParcel(Parcel in) {
            return new BookList(in);
        }

        @Override
        public BookList[] newArray(int size) {
            return new BookList[size];
        }
    };

    public void add(Book x){
        list.add(x);
    }

    public void remove(Book x){
        list.remove(x);
    }

    public Book get(int x){
        return list.get(x);
    }

    public int size(){
        return list.size();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
