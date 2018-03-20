package com.example.meg2tron.digitalentrypass;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class gateway extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gateway);

        Toast.makeText(this, "Payment Done!", Toast.LENGTH_SHORT).show();
    }
}
