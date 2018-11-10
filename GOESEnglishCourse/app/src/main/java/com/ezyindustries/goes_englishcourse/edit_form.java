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
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class edit_form extends AppCompatActivity {

    private final int GALLERY_ACTIVITY_CODE=200;
    private final int RESULT_CROP = 400;

    private ImageView confirm, cancel,camera;
    private EditText name, nickname, descirption, mobilephone, email, website;
    private FirebaseAuth Auth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    StorageReference Sref;
    private DatabaseReference databaseReference;
    private String lesson, tes, latihan, Gender;
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

        Auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("user");

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
                    update();
                }

            }
        });


    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                getCroppedBitmap(selectedImage);
                camera.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(edit_form.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(edit_form.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(edit_form.this, "Update Failed Logcat   " + databaseError.getDetails(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void update(){
        final ProgressDialog dialog = new ProgressDialog(edit_form.this);
        dialog.setMessage("Hold on we loading up data for you....");
        dialog.show();
        ValueEventListener valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User usr = new User(
                        name.getText().toString(),
                        nickname.getText().toString(),
                        email.getText().toString(),
                        mobilephone.getText().toString(),
                        lesson,
                        tes,
                        latihan,
                        Gender,
                        descirption.getText().toString(),
                        website.getText().toString()

                );
                dialog.hide();
                databaseReference.child(Objects.requireNonNull(Auth.getCurrentUser()).getUid()).setValue(usr).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Update Succes",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), profile.class));
                        }

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(edit_form.this, "Insert Data Failed", Toast.LENGTH_LONG).show();
            }
        });

    }
}


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == GALLERY_ACTIVITY_CODE) {
//            if(resultCode == Activity.RESULT_OK){
//                picturePath = data.getStringExtra("picturePath");
//                performCrop(picturePath);
//            }
//        }
//
//        if (requestCode == RESULT_CROP ) {
//            if(resultCode == Activity.RESULT_OK){
//                Bundle extras = data.getExtras();
//                Bitmap selectedBitmap = extras.getParcelable("data");
//                // Set The Bitmap Data To ImageView
//                image_capture1.setImageBitmap(selectedBitmap);
//                image_capture1.setScaleType(ImageView.ScaleType.FIT_XY);
//            }
//        }
//    }
//
//    private void performCrop(String picUri) {
//        try {
//            //Start Crop Activity
//
//            Intent cropIntent = new Intent("com.android.camera.action.CROP");
//            // indicate image type and Uri
//            File f = new File(picUri);
//            Uri contentUri = Uri.fromFile(f);
//
//            cropIntent.setDataAndType(contentUri, "image/*");
//            // set crop properties
//            cropIntent.putExtra("crop", "true");
//            // indicate aspect of desired crop
//            cropIntent.putExtra("aspectX", 1);
//            cropIntent.putExtra("aspectY", 1);
//            // indicate output X and Y
//            cropIntent.putExtra("outputX", 280);
//            cropIntent.putExtra("outputY", 280);
//
//            // retrieve data on return
//            cropIntent.putExtra("return-data", true);
//            // start the activity - we handle returning in onActivityResult
//            startActivityForResult(cropIntent, RESULT_CROP);
//        }
//        // respond to users whose devices do not support the crop action
//        catch (ActivityNotFoundException anfe) {
//            // display an error message
//            String errorMessage = "your device doesn't support the crop action!";
//            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
//            toast.show();
//        }
//    }
//}