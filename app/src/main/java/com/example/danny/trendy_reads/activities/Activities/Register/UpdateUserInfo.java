package com.example.danny.trendy_reads.activities.Activities.Register;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.danny.trendy_reads.R;
import com.example.danny.trendy_reads.activities.Activities.Home;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Random;

public class UpdateUserInfo extends AppCompatActivity {
    ImageView ImgUserPhoto;
    //Para actualizar los datos del usuario.
    static int PReqCode = 1;
    static int REQUESCODE = 1;
    Uri pickedImgUri;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;



    private EditText userName;
    private ProgressBar loadingProgress;
    private Button regBtn;
    private  String stringUri;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdata);
        userName= findViewById(R.id.RegName);
        loadingProgress=findViewById(R.id.progressBar2);
        loadingProgress.setVisibility(View.INVISIBLE);
        mAuth= FirebaseAuth.getInstance();
        regBtn=findViewById(R.id.RegBttn);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regBtn.setVisibility(View.INVISIBLE);
                loadingProgress.setVisibility(View.VISIBLE);

                String name = userName.getText().toString();


                if (name.isEmpty()) {
                    showMessage("Por favor ingrese su nombre");
                    regBtn.setVisibility(View.VISIBLE);
                    loadingProgress.setVisibility(View.INVISIBLE);

                } else {
                    name = name.toLowerCase();
                    CreateUserData(name);
                }
            }
        });

        ImgUserPhoto=findViewById(R.id.regUserPhoto);
        ImgUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=22){
                    checkAndRequestForPermission();
                }
                else{
                    openGallery();
                }
            }
        });
    }

    private void checkAndRequestForPermission() {
        if(ContextCompat.checkSelfPermission(UpdateUserInfo.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(UpdateUserInfo.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(UpdateUserInfo.this, "Por favor acepta para poder otorgar permiso.", Toast.LENGTH_SHORT).show();
            }
            else{
                ActivityCompat.requestPermissions(UpdateUserInfo.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }
        }

        else{
            openGallery();
        }

    }

    private void openGallery() {
        Intent galleryIntent= new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);
    }

    private void CreateUserData(final String name) {
        databaseReference.child("Users").child(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists() || pickedImgUri == null ) {
                    showMessage("Ese usuario ya está registrado.");
                    regBtn.setVisibility(View.VISIBLE);
                    loadingProgress.setVisibility(View.INVISIBLE);

                    if (pickedImgUri == null) {
                        showMessage("Ingrese una imagen");
                        regBtn.setVisibility(View.VISIBLE);
                        loadingProgress.setVisibility(View.INVISIBLE);
                    }
                }

                else{
                    FirebaseUser current_user =FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();
                   // mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    databaseReference.child("Users").child(name).setValue(true);



                    if (name.isEmpty()) {
                        showMessage("No puede dejar el nombre vacío.");
                        regBtn.setVisibility(View.VISIBLE);
                        loadingProgress.setVisibility(View.INVISIBLE);
                    }

                    else{
                       // HashMap< String, String > userMap = new HashMap<>();
                        //userMap.put("Name", name);


                        updateUserInfo(name, pickedImgUri, mAuth.getCurrentUser());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateUserInfo(final String name, final Uri pickedImgUri, final FirebaseUser currentUser) {
        if (pickedImgUri == null) {
            showMessage("Ingrese una imagen");
            regBtn.setVisibility(View.VISIBLE);
            loadingProgress.setVisibility(View.INVISIBLE);
        } else {
            final StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("Imagenes").child(name + ".jpg");
            final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());
            imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful());
                    Uri downloadUrl = urlTask.getResult();

                    final String sdownload_url = String.valueOf(downloadUrl);


                    /////////////////////////////////////////////////////////////////////////////////////
                    imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            //String downlaod_URL = pickedImgUri.getDownloadUrl().toString();
                            //mDatabase.child("Imagenes").setValue(downlaod_URL);
                            //String imagen = mStorage.child("Imagenes/"+name +"/.jpg").getDownloadUrl().getResult().toString();


                            //mStorage.child(("Imagenes/")+(name+"/.jpg")).getDownloadUrl().toString();
                            UserProfileChangeRequest profileupdate = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .setPhotoUri(uri)
                                    .build();

                            FirebaseUser current_user =FirebaseAuth.getInstance().getCurrentUser();
                            String uid = current_user.getUid();

                            mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(name);
                            HashMap<String, String>  userMap = new HashMap<>();
                            userMap.put("UID", uid);
                            userMap.put("Nombre", name);
                            userMap.put("descripción", "Hola, estoy usando Trendy Reads.");
                            userMap.put("imagen",  sdownload_url);
                            mDatabase.setValue(userMap);

                            currentUser.updateProfile(profileupdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful() ) {

                                        showMessage("Registro completo");

                                        updateUI();
                                    }

                                    else{
                                        showMessage("Ocurrió un error");
                                    }
                             /*   HashMap< String, String > userMap = new HashMap<>();
                                userMap.put("name", name);
                                userMap.put("status", "Hola, estoy usando Trendy Reads");
                                userMap.put("image", "default");

                                mDatabase.setValue(userMap);
                            */
                                }
                            });

                        }
                    });
                }
            });

        }
    }



    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    private void updateUI() {
        Intent homeActivity = new Intent(getApplicationContext(), Home.class);
        homeActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeActivity);
        //finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == REQUESCODE && data != null){

            //El usuario seleccionó con éxito una imagen.
            //Se guardará su referencia a una variable URI.

            pickedImgUri = data.getData();
            ImgUserPhoto.setImageURI(pickedImgUri);

        }
    }

    @Override
    public void onBackPressed() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //startActivity(new Intent(this, LoginActivity.class));
        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent LoginActivity = new Intent(getApplicationContext(), com.example.danny.trendy_reads.activities.Activities.LoginActivity.class);
                    finish();
                    startActivity(LoginActivity);
                    showMessage("Bienvenido");
                }
            }

        });
    }

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
}
