package com.example.danny.trendy_reads.activities.Activities.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.net.Uri;
import android.widget.Toast;

import com.example.danny.trendy_reads.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {


    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

    //LAYOUT

    private CircleImageView mprofile_image;
    private TextView mname_settings;
    private TextView msettings_status;
    private Button mButton, mImageButton;


    ////////////////////////////////////////////
   // FirebaseAuth mAuth;
   // FirebaseUser currentUser;

    private static final int GALLERY_PICK = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mImageButton = findViewById(R.id.settings_image_button);
        mButton = findViewById(R.id.settings_status_button);
        mprofile_image = findViewById(R.id.profile_image);
        mname_settings = findViewById(R.id.name_settings);
        msettings_status = findViewById(R.id.settings_status);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        String name = mCurrentUser.getDisplayName();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(name);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Checar que sí se esté realizando el proceso
                //Usar esto cada que se quiera revisar que algo se está realizando de manera correcta.
                //Toast.makeText(SettingsActivity.this, dataSnapshot.toString(), Toast.LENGTH_LONG).show();


                String name = dataSnapshot.child("Nombre").getValue().toString();
                String image = dataSnapshot.child("imagen").getValue().toString();
                String status = dataSnapshot.child("descripción").getValue().toString();


                mname_settings.setText(name);
                msettings_status.setText(status);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        {

        }


        //////////////////////////////////////////////////////////////////////////////
        // mAuth = FirebaseAuth.getInstance();
        //  currentUser=mAuth.getCurrentUser();

        //////////////////////////////////////////////////////////////////////
        // UpdatePhoto();


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String status_value = msettings_status.getText().toString();

                Intent status = new Intent(SettingsActivity.this, StatusActivity.class);
                status.putExtra("status_value", status_value);
                startActivity(status);
            }
        });

        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery_intent = new Intent();
                gallery_intent.setType("image/*");
                gallery_intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery_intent, "Elige una imagen."), GALLERY_PICK);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK)  {
        Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setAspectRatio(1, 1)
                    .start(this);
            //Toast.makeText(SettingsActivity.this, imageUri , Toast.LENGTH_LONG).show();

     /*  if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK)  {
       ////////////////////////////
       //if (requestCode == GALLERY_PICK && resultCode == RESULT_OK)  {
            /////////////////////////////77
            //String imageUri = data.getDataString();
            //////////////////////////////
            //Toast.makeText(SettingsActivity.this, imageUri, Toast.LENGTH_LONG).show();
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            Uri resultUri = result.getUri();


            } /*else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }*/
    }}}
    /////////////////////////////////////////////////////////////////////////
    /*public void UpdatePhoto(){
        TextView profileUsername = findViewById(R.id.settings_display_name);
        TextView profileStatus = findViewById(R.id.settings_status);
        ImageView UserPhoto = findViewById(R.id.profile_image);

        profileUsername.setText(currentUser.getDisplayName());
        profileStatus.setText("Hi! I'm using Trendy Reads");

        Glide.with(this).load(currentUser.getPhotoUrl()).into(UserPhoto);

    }*/
//}
