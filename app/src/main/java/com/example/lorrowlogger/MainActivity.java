package com.example.lorrowlogger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lorrowlogger.data.DataBaseHandler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    DataBaseHandler mydb;
    ArrayList<String> arr=new ArrayList<>();
    AlertDialog.Builder builder;
    AlertDialog dialog;
    EditText name;
    Button save,cancel;
    public static final String EXTRA_NAMEID="com.example.nameid";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        listView=findViewById(R.id.listView);
        PeopleAdapter pa=new PeopleAdapter(this,R.layout.people_list_layout,arr);
        listView.setAdapter(pa);
        mydb=new DataBaseHandler(this);
        displayarr();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(), MainActivity2.class);
                intent.putExtra(EXTRA_NAMEID,arr.get(i));
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int whichitem=i;
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this record?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mydb.deletedata(arr.get(whichitem));
                                arr.remove(whichitem);
                                pa.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_add:
                Toast.makeText(this, "Creating new account", Toast.LENGTH_SHORT).show();
                createNewAccount();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void createNewAccount(){
        builder=new AlertDialog.Builder(this);
        final View accountInput=getLayoutInflater().inflate(R.layout.popup,null);
        name=(EditText) accountInput.findViewById(R.id.editTextTextPersonName);
        save=(Button) accountInput.findViewById(R.id.button);
        cancel=(Button) accountInput.findViewById(R.id.button2);
        builder.setView(accountInput);
        dialog=builder.create();
        dialog.show();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameforarr=name.getText().toString();
                mydb.insertData(nameforarr);
                mydb.CreateNameAccount(nameforarr);
                arr.add(nameforarr);
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void displayarr(){
        Cursor res=mydb.getData();
        while(res.moveToNext()){
            arr.add(res.getString(1));
        }
    }
}