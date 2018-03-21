package com.serviayuda.pc.serviayuda;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by PC on 20/03/2018.
 */

public class DisplayToken extends AppCompatActivity implements View.OnClickListener {

    //defining views
    private Button buttonDisplayToken;
    private EditText textViewToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displaytoken);

        //getting views from xml
        textViewToken = findViewById(R.id.textViewToken);
        buttonDisplayToken = findViewById(R.id.buttonDisplayToken);

        //adding listener to view
        buttonDisplayToken.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonDisplayToken) {
            String token = FirebaseInstanceId.getInstance().getToken();
            textViewToken.setText(token);
        }
    }
}