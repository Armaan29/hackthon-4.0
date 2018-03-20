package com.example.meg2tron.digitalentrypass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

public class cartActivity extends AppCompatActivity {


    TextView monuname,date,frnst,indst,frnadl,indadl,total;
    Button bttn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        monuname = (TextView)findViewById(R.id.monudisname);
        date = (TextView)findViewById(R.id.disdate);
        frnst = (TextView)findViewById(R.id.frnst);
        indst = (TextView)findViewById(R.id.indst);
        frnadl = (TextView)findViewById(R.id.frnadl);
        indadl = (TextView)findViewById(R.id.indadl);
        total=(TextView)findViewById(R.id.total);
        bttn = (Button)findViewById(R.id.payment);

        ArrayList <String> ticket = (ArrayList<String>) getIntent().getSerializableExtra("datalist");

        monuname.setText(ticket.get(0));
        date.setText("Date:"+ticket.get(10));
        frnst.setText("Foreign Students :" + ticket.get(1));
        indst.setText("Indian Students :"+ticket.get(3));
        frnadl.setText("Foreign Adults :"+ticket.get(7));
        indadl.setText("Indian Adults"+ticket.get(5));
       total.setText("Payable Amount :Rs."+ticket.get(9));


        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(cartActivity.this,gateway.class));
            }
        });

       // Log.i("Bundle", String.valueOf(intent));
    }

}
