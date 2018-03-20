package com.example.meg2tron.digitalentrypass;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.meg2tron.digitalentrypass.Interface.ItemClickListener;
import com.example.meg2tron.digitalentrypass.ViewHolder.CityViewHolder;
import com.example.meg2tron.digitalentrypass.ViewHolder.MonuActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.funtory.slideshowimageview.SlideshowImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;

import utils.Cities;


public class second extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView userpic;
    TextView usrname, usremail;
    Button btn;

    FirebaseDatabase database;
    DatabaseReference cities, user, monu;

    RecyclerView recycler_city;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.i("app", "started");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        //Init Firebase

        database = FirebaseDatabase.getInstance();
        cities = database.getReference("cities");
        user = database.getReference("users");


        FloatingActionButton cart = (FloatingActionButton) findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(v, "ABCDE", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        /*btn =(Button)findViewById(R.id.na);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(second.this,Login.class));
            }
        });*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView = navigationView.getHeaderView(0);

        userpic = navHeaderView.findViewById(R.id.usrimg);
        usrname = navHeaderView.findViewById(R.id.usrnme);
        usremail = navHeaderView.findViewById(R.id.usrmail);
        //Chll dekhda ruk dekh ki pnga ae
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user.child(firebaseUser.getEmail().replace('.', ','));
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map map = (Map) dataSnapshot.getValue();
                Log.i("map", map.toString());
                if (dataSnapshot.getValue() != null) {

                    if (firebaseUser != null) {
                        Glide.with(second.this)
                                .load(firebaseUser.getPhotoUrl())
                                .into(userpic);
                        usrname.setText(firebaseUser.getDisplayName());
                        usremail.setText(firebaseUser.getEmail());
                    }

                }

                recycler_city = (RecyclerView) findViewById(R.id.recycler_cities);//Kya tha yeh baar call ho rahi h
                recycler_city.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(second.this);
                recycler_city.setLayoutManager(layoutManager);

                loadcities();

            }

            private void loadcities() {
                FirebaseRecyclerAdapter<Cities, CityViewHolder> adapter = new FirebaseRecyclerAdapter<Cities, CityViewHolder>(Cities.class, R.layout.city_item, CityViewHolder.class, cities) {
                    @Override
                    protected void populateViewHolder(CityViewHolder viewHolder, Cities model, int position) {
                        viewHolder.txtcityname.setText(model.getName());
                        Picasso.with(getBaseContext()).load(model.getImage())
                                .into(viewHolder.cityImage);

                        final Cities clickItem = model;
                        viewHolder.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onClick(View v, int pos, boolean isLongClick) {
                                Toast.makeText(second.this, clickItem.getName(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(second.this, MonuActivity.class);
                                intent.putExtra("name", clickItem.getName());
                                startActivity(intent);
                            }
                        });

                    }
                };
                Log.i("adap", "Adap Set");
                recycler_city.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("err", "Fuck");
                //startActivity(new Intent(second.this, Login.class));
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {

            FirebaseAuth.getInstance().signOut();

            startActivity(new Intent(second.this, Login.class));

        } else if (id == R.id.helpline) {
            Intent phoneIntent = new Intent(Intent.ACTION_CALL);
            phoneIntent.setData(Uri.parse("tel:1098"));

            if (ActivityCompat.checkSelfPermission(second.this,android.Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                return true;
            };
            startActivity(phoneIntent);
            Toast.makeText(this,"Calling",Toast.LENGTH_LONG).show();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
