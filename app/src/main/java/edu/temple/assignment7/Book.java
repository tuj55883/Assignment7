//Just a basic book class
package edu.temple.assignment7;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private String title;
    private String author;
    private int id;
    private String coverURL;
    private int duration;

    public Book(String title, String author, int id, String coverURL, int duration) {
        this.title = title;
        this.author = author;
        this.id = id;
        this.coverURL = coverURL;
        this.duration = duration;
    }

    public Book() {
    }

    protected Book(Parcel in) {
        title = in.readString();
        author = in.readString();
    }


    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getTitle(){
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getId() {
        return id;
    }

    public String getCoverURL() {
        return coverURL;
    }

    public int getDuration() {
        return duration;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
    }
}
