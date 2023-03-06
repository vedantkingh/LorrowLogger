package com.example.lorrowlogger;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.lorrowlogger.data.DataBaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    DataBaseHandler mydb2;
    ListView lv;
    FloatingActionButton fab;
    TextView nameandstat,totalamnt,giveortake;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    EditText amount;
    Button save,cancel,addtransbutton;
    RadioGroup radioGroup;
    ArrayList<Integer> transacs=new ArrayList<Integer>();
    Integer tot=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        fab=findViewById(R.id.fab);
        lv=findViewById(R.id.lv);
        nameandstat=findViewById(R.id.textView7);
        addtransbutton=findViewById(R.id.button3);
        totalamnt=findViewById(R.id.textView2);
        giveortake=findViewById(R.id.textView3);
        mydb2=new DataBaseHandler(this);

        Intent intent=getIntent();
        String nameofperson=intent.getStringExtra(MainActivity.EXTRA_NAMEID);
        nameandstat.setText(nameofperson);

        ArrayAdapter arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,transacs);
        lv.setAdapter(arrayAdapter);
        displaytrans(nameofperson);
        setamount();
        addtransbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewTransaction(nameofperson);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void addNewTransaction(String nameofperson){
        builder=new AlertDialog.Builder(this);
        final View transInput=getLayoutInflater().inflate(R.layout.addtrans,null);
        amount = (EditText) transInput.findViewById(R.id.editTextNumber);
        save=(Button) transInput.findViewById(R.id.button4);
        cancel=(Button) transInput.findViewById(R.id.button5);
        radioGroup=(RadioGroup) transInput.findViewById(R.id.rGroup);
        builder.setView(transInput);
        dialog=builder.create();
        dialog.show();
        save.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onClick(View view) {
                int numfortrans=Integer.parseInt(amount.getText().toString());
                int gtno;
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.givebutton:
                        gtno=numfortrans;
                        break;
                    case R.id.takebutton:
                        gtno=numfortrans*-1;
                        break;
                    default:
                        throw new IllegalStateException("Select an option " + radioGroup.getCheckedRadioButtonId());
                }
                mydb2.insertDataAccount(nameofperson,gtno);
                transacs.add(gtno);
                tot+=gtno;
                if(tot<0){
                    giveortake.setText("to be given to");
                    tot*=-1;
                }else if(tot>0){
                    giveortake.setText("to be taken from");
                }
                totalamnt.setText(tot.toString());
                transacs.notify();
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

    public void displaytrans(String nameofperson){
        Cursor curs=mydb2.getNameAccount(nameofperson);
        while(curs.moveToNext()){
            if(curs.getInt(0)!=0){
                transacs.add(curs.getInt(0));
            };
        }
    }

    public void setamount(){
        for (int i = 0; i < transacs.size(); i++) {
            tot+=transacs.get(i);
        }
        if(tot<0){
            giveortake.setText("to be given to");
            tot*=-1;
        }else if(tot>0){
            giveortake.setText("to be taken from");
        }
        totalamnt.setText(tot.toString());
    }
}