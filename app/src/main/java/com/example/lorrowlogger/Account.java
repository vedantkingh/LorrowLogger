package com.example.lorrowlogger;

import java.util.ArrayList;

public class Account {
    String name;
    int mainBal;
    ArrayList<Integer> entry=new ArrayList<>();

    void addEntryLend(int num){
        entry.add(num);
        mainBal=mainBal+num;
    }

    void addEntryBorrow(int num){
        num=num*-1;
        entry.add(num);
        mainBal=mainBal+num;
    }
}
