package com.example.dk_ragnar.apidefacebook;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    ShareDialog shareDialog;
    ShareButton shareButton;
    ShareLinkContent linkContent;

    TextView txtNombre, txtEmail, txtUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombre = findViewById(R.id.txtnombre);
        txtEmail = findViewById(R.id.txtemail);
        txtUid = findViewById(R.id.txtuid);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            String nombre = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            String uid = user.getUid();

            txtNombre.setText(nombre);
            txtEmail.setText(email);
            txtUid.setText(uid);
        }else{
            aLogearsePerro();
        }

        //Esto es solo facebook
        //if(AccessToken.getCurrentAccessToken()==null){
       //     aLogearsePerro();
        //}

        shareButton = (ShareButton)findViewById(R.id.fb_share_button);

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            Log.d("UDB","Esta dentro del If");
            linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("Prueba de Desarrollador")
                    .setContentDescription("Compartiendo esto para ver si tu aplicacion permite el uso de la API de Facebook")
                    .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                    .build();

            shareButton.setShareContent(linkContent);
        }
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareDialog.show(linkContent);
            }
        });

    }

   public void aLogearsePerro(){
       Intent intent = new Intent(this, LoginActivity.class);
       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
       startActivity(intent);
   }

    public void logout(View view) {
       FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        aLogearsePerro();
    }


}
