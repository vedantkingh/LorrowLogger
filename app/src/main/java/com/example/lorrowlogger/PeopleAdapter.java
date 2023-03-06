package com.example.lorrowlogger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PeopleAdapter extends ArrayAdapter<String> {
    private ArrayList<String> objects;
    public PeopleAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        this.objects=objects;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return objects.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(getContext()).inflate(R.layout.people_list_layout,parent,false);
        TextView name=convertView.findViewById(R.id.textView);
        name.setText(getItem(position));
        return convertView;
    }
}
