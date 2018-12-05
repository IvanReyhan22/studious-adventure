package com.ezyindustries.goes_englishcourse;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class edit_form extends AppCompatActivity {

    private final int GALLERY_ACTIVITY_CODE=200;
    private final int RESULT_CROP = 400;

    private ImageView confirm, cancel,camera;
    private EditText name, nickname, descirption, mobilephone, email, website;
    private FirebaseAuth Auth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private StorageReference Sref;
    private DatabaseReference databaseReference;
    private String lesson, tes, latihan, Gender, ImageUrl,isImage;
    private Uri imageUri, DownloadUrl;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_form);
        confirm = (ImageView) findViewById(R.id.confirm);
        cancel = (ImageView) findViewById(R.id.cancel);
        camera = (ImageView)findViewById(R.id.profile_image);

        name = (EditText) findViewById(R.id.name);
        nickname = (EditText) findViewById(R.id.nickname);
        descirption = (EditText) findViewById(R.id.description);
        mobilephone = (EditText) findViewById(R.id.number);
        email = (EditText) findViewById(R.id.email);
        website = (EditText) findViewById(R.id.website);
        loading = (ProgressBar)findViewById(R.id.loading);

        Auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("user");
        firebaseStorage = FirebaseStorage.getInstance();
        Sref = firebaseStorage.getReference("UserPhoto").child(Objects.requireNonNull(Auth.getCurrentUser()).getUid());

        getData();

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nickname.length() >= 7){
                    Toast.makeText(getApplicationContext(), "Nickname must be Less than 7 characters",Toast.LENGTH_LONG).show();
                }else{
                    UploadImage();
                    update();
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK && data.getData() != null) {
            try {
                imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                camera.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(edit_form.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(edit_form.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    private void UploadImage(){
        if(imageUri != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            Sref.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    progressDialog.hide();

                    Sref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            ImageUrl = uri.toString();
                            Toast.makeText(getApplicationContext(),""+ImageUrl, Toast.LENGTH_LONG).show();
                            databaseReference.child(Objects.requireNonNull(Auth.getCurrentUser()).getUid()).child("picUrl").setValue(ImageUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(edit_form.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Failed get Download url", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.hide();
                    Toast.makeText(edit_form.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                            .getTotalByteCount());
                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                }
            });

        }
    }


    private void getData(){
        databaseReference.child(Objects.requireNonNull(Auth.getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    name.setText(Objects.requireNonNull(user).getUsername());
                    nickname.setText(user.getNickname());
                    descirption.setText(user.getDeskripsion());
                    email.setText(user.getEmail());
                    website.setText(user.getWebsite());
                    mobilephone.setText(user.getPhone());
                    lesson = user.getLesson();
                    tes = user.getTes();
                    Gender = user.getGender();
                    latihan = user.getLatihan();
                    isImage = user.getPicUrl();
                    if(!isImage.equals("")){
                        ImageUrl = isImage;
                        Picasso.get().load(isImage).into(camera, new Callback() {
                            @Override
                            public void onSuccess() {
                                loading.setVisibility(View.GONE);
                            }
                            @Override
                            public void onError(Exception e) {
                                Log.e("Image", e.getMessage());}
                        });
                    }else{
                        loading.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(edit_form.this, "Update Failed Logcat   " + databaseError.getDetails(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void update(){
        if (ImageUrl == null){
            ImageUrl = "";
        }
        final ProgressDialog dialog = new ProgressDialog(edit_form.this);
        dialog.setMessage("Hold on we loading up data for you....");
        dialog.show();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user").child(Objects.requireNonNull(Auth.getCurrentUser()).getUid());
        Map<String, Object> updates = new HashMap<String,Object>();
        updates.put("deskripsion",descirption.getText().toString());
        updates.put("email",email.getText().toString());
        updates.put("gender",Gender);
        updates.put("latihan",latihan);
        updates.put("lesson",lesson);
        updates.put("nickname",nickname.getText().toString());
        updates.put("phone",mobilephone.getText().toString());
        updates.put("picUrl",ImageUrl);
        updates.put("tes",tes);
        updates.put("username",name.getText().toString());
        updates.put("website",website.getText().toString());

        ref.updateChildren(updates);

    }
}


