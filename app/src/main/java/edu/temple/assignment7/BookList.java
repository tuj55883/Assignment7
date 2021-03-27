package edu.temple.assignment7;

import java.util.ArrayList;


public class BookList {
    private ArrayList<Book> list = new ArrayList<>();


    public BookList(){
    }

    public void add(Book x){
        list.add(x);
    }

    public void remove(Book x){
        list.remove(x);
    }

    public Book get(int x){
        return list.get(x);
    }

    public void size(){
        list.size();
    }


}
