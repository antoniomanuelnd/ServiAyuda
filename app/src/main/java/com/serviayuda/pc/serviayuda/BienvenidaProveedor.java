package com.serviayuda.pc.serviayuda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by PC on 04/03/2018.
 */

public class BienvenidaProveedor  extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bienvenida);
        Intent i = new Intent(BienvenidaProveedor.this, MenuViewPagerProveedor.class);
        startActivity(i);
    }
}
