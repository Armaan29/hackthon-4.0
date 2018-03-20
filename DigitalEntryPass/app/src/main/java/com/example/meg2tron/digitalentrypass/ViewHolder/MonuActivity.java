package com.example.meg2tron.digitalentrypass.ViewHolder;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meg2tron.digitalentrypass.R;
import com.example.meg2tron.digitalentrypass.cartActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MonuActivity extends AppCompatActivity{
    DatabaseReference monu,monudis,monuprice;
    ListView listView;
    TextView desctv;
    ImageView monuimg;
    Button cartbtn;
    String monuname1 = null;
    public int position;
    ArrayList<String> monuList;

    Integer frgnstuno=0,indstuno,frgnadlno,indadlno;

    int frgnstutotal,indstutotal,frgnadltotal,indadltotal,total;

    EditText frgnstu,idnstu,frgnadl,idnadl;
    DatePicker simpleDatePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monu);




        monu = FirebaseDatabase.getInstance().getReference("monuments").child(getIntent().getStringExtra("name"));
        monu.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                Map map = (Map) dataSnapshot.getValue();
                Set monus = map.keySet();
                listView = findViewById(R.id.monulist);
                monuList = new ArrayList<String>();
                Iterator iterator = monus.iterator();
                while(iterator.hasNext()){/////KI krna? usto aage jitho chdya
                    String item = iterator.next().toString();
                    monuList.add(item);
                    Log.i("monu", item);
                }



                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MonuActivity.this, android.R.layout.simple_list_item_1, monuList);
                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        View mview = getLayoutInflater().inflate(R.layout.monu_desc, null);
                        TextView monuName = mview.findViewById(R.id.name);
                        monuName.setText(monuList.get(position));
                        monuname1 = monuList.get(position);

                        frgnstu = mview.findViewById(R.id.frgnstu);
                        idnstu = mview.findViewById(R.id.indstu);
                        frgnadl = mview.findViewById(R.id.frgnadl);
                        idnadl = mview.findViewById(R.id.indadl);

                        cartbtn = mview.findViewById(R.id.cart);



                        desctv = mview.findViewById(R.id.desc);
                        monuimg = mview.findViewById(R.id.slides);
                        monudis  = FirebaseDatabase.getInstance().getReference("monuments").child(getIntent().getStringExtra("name"));
                        DatabaseReference monudis1 = monudis.child(monuList.get(position)).child("desc");
                        final DatabaseReference monudis2 = monudis.child(monuList.get(position)).child("image");


                        simpleDatePicker = mview.findViewById(R.id.simpleDatePicker); // initiate a date picker

                        Log.i("Image", String.valueOf(monudis2));
                        Log.i("Desc",monuList.get(position));
                        Log.i("Desc", monudis2.toString());
                        monudis1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String description = (String) dataSnapshot.getValue();
                                desctv.setText(description);
                                Log.i("Desc",description);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        monudis2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Picasso.with(MonuActivity.this).load((String) dataSnapshot.getValue()).into(monuimg);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });



                        new AlertDialog.Builder(MonuActivity.this).setView(mview).show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void addcart(View v) {

        frgnstuno=Integer.parseInt(frgnstu.getText().toString());
        indstuno=Integer.parseInt(idnstu.getText().toString());
        frgnadlno=Integer.parseInt(frgnadl.getText().toString());
        indadlno = Integer.parseInt(idnadl.getText().toString());

        Log.i("frgnst",frgnstuno.toString());

//        Bundle bundle=new Bundle();
//        bundle.putString("Cityname",monuList.get(0));
//        bundle.putString("Monumentname","abc");
//        bundle.putInt("frgnstu",frgnstuno);
//        bundle.putInt("indstu", indstuno);

        monuprice  = FirebaseDatabase.getInstance().getReference("monuments").child(getIntent().getStringExtra("name"));
        DatabaseReference monuprice1 = monuprice.child(monuList.get(position)).child("prices");
        DatabaseReference monuprice2=monuprice1.child("person");





        int day = simpleDatePicker.getDayOfMonth();
        int month =  (simpleDatePicker.getMonth() + 1);
        int year = simpleDatePicker.getYear();

        String date = day+" / "+month+" / "+year;
        // display the values by using a toast
        Toast.makeText(getApplicationContext(), day + "\n" + month + "\n" + year, Toast.LENGTH_LONG).show();

        if (frgnstuno!=0)
        {

            DatabaseReference monuprice3=monuprice2.child("foreign");

            Log.i("price",monuprice3.toString());

            monuprice3.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                   String temp = (String) dataSnapshot.getValue();
                   int temp1 = Integer.parseInt(temp);
                    Log.i("temp", "onDataChange: "+frgnstuno*(temp1/2));
                    frgnstutotal = frgnstuno*(temp1/2);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        if (indstuno !=0)
        {
            DatabaseReference monuprice3=monuprice2.child("indian");

            Log.i("price",monuprice3.toString());

            monuprice3.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String temp = (String) dataSnapshot.getValue();
                    int temp1 = Integer.parseInt(temp);
                   // Log.i("temp", "onDataChange: "+frgnstuno*(temp1/2));
                    indstutotal = indstuno*(temp1/2);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        if (indadlno !=0)
        {
            DatabaseReference monuprice3=monuprice2.child("indian");

            Log.i("price",monuprice3.toString());

            monuprice3.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String temp = (String) dataSnapshot.getValue();
                    int temp1 = Integer.parseInt(temp);
                   // Log.i("temp", "onDataChange: "+frgnstuno*(temp1/2));
                    indadltotal = indadlno*(temp1/2);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        if (frgnadlno !=0)
        {
            DatabaseReference monuprice3=monuprice2.child("foreign");

            Log.i("price",monuprice3.toString());

            monuprice3.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String temp = (String) dataSnapshot.getValue();
                    int temp1 = Integer.parseInt(temp);


                    frgnadltotal = frgnadlno*temp1;
                     Log.i("temp11", "onDataChange: "+frgnadltotal);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
            total = frgnstutotal+indstutotal+frgnadltotal+indadltotal;
        Log.i("frgnstutotal", String.valueOf(total));
        Intent intent = new Intent(this, cartActivity.class);

        ArrayList<String> datalist = new ArrayList<>();

        datalist.add(monuname1);
        datalist.add(String.valueOf(frgnstuno));
        datalist.add(String.valueOf(frgnstutotal));
        datalist.add(String.valueOf(indstuno));
        datalist.add(String.valueOf(indstutotal));
        datalist.add(String.valueOf(indadlno));
        datalist.add(String.valueOf(indadltotal));
        datalist.add(String.valueOf(frgnadlno));
        datalist.add(String.valueOf(frgnadltotal));
        datalist.add(String.valueOf(total));
        datalist.add(date);

        Iterator<String> iterator = datalist.iterator();
        while (iterator.hasNext()){
            Log.i("datalistttttt",iterator.next());
        }


        intent = intent.putExtra("datalist",datalist);

        startActivity(intent);


    }
}
