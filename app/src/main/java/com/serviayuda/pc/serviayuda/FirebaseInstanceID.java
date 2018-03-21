package com.serviayuda.pc.serviayuda;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by PC on 20/03/2018.
 */

public class FirebaseInstanceID extends FirebaseInstanceIdService{

    private static final String TAG = "FirebaseIDService";

    @Override
    public void onTokenRefresh(){
        //Obteniendo el token de registro
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        //Muestra el token en consola
        Log.d(TAG, " Token: " + refreshedToken);

        storeToken(refreshedToken);
    }


    private void storeToken(String token){
        SharedPrefManager.getInstance(getApplicationContext()).saveDeviceToken(token);
    }
}
