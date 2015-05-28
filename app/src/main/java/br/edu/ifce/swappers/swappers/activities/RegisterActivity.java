package br.edu.ifce.swappers.swappers.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.fragments.dialogs.UserPhotoDialogFragment;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.*;

public class RegisterActivity extends AppCompatActivity implements UserPhotoDialogFragment.UserPhotoDialogListener{

    private Toolbar toolbar;
    private Button  registerButton;
    private CircleImageView userPhotoCircleImageView;

    private static final short CAMERA_INTENT_CODE  = 1015;
    private static final short GALLERY_INTENT_CODE = 1016;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.toolbar                  = (Toolbar) findViewById(R.id.toolbar);
        this.registerButton           = (Button)  findViewById(R.id.registerButton);
        this.userPhotoCircleImageView = (CircleImageView) findViewById(R.id.userPhotoCircleImageView);

        this.setUpActivityToolbar();
        this.registerButton.setOnClickListener(this.makeRegisterButtonClickListener());
        this.userPhotoCircleImageView.setOnClickListener(this.makeUserPhotoCircleButtonClickListener());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap userPhotoBitmap;

        if (requestCode == CAMERA_INTENT_CODE && resultCode == RESULT_OK){

            userPhotoBitmap = this.retrieveImageFromCameraResult(data);
            this.userPhotoCircleImageView.setImageBitmap(userPhotoBitmap);
        }
        else if (requestCode == GALLERY_INTENT_CODE && resultCode == RESULT_OK) {

            userPhotoBitmap = this.retrieveImageFromGalleryResult(data);
            this.userPhotoCircleImageView.setImageBitmap(userPhotoBitmap);
        }
    }

    private void setUpActivityToolbar(){
        if (this.toolbar != null){
            setSupportActionBar(toolbar);
        }
    }

    /*
    *
    * Listener builder methods
    *
    * */

    private OnClickListener makeUserPhotoCircleButtonClickListener() {
        OnClickListener clickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                UserPhotoDialogFragment dialogFragment = new UserPhotoDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "User Photo Dialog Fragment");
            }
        };

        return clickListener;
    }

    private OnClickListener makeRegisterButtonClickListener(){

        OnClickListener clickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isRegisterFormValid()){
                    RegisterActivity.this.startMainActivity();
                }
                else{
                    RegisterActivity.this.markWrongFields();
                }
            }
        };

        return clickListener;
    }

    /*
    *
    * Validation methods for the form fields
    *
    * */

    private boolean isRegisterFormValid(){
        //TODO Write a code here to validate the register form.

        return true;
    }

    private void markWrongFields(){
        //TODO mark the fields with wrong information.
    }

    private void startMainActivity(){
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        this.startActivity(mainActivityIntent);
    }


    /*
    *
    * Delegate methods from UserPhotoDialogFragment
    *
    * */

    @Override
    public void onGalleryClick(DialogFragment dialogFragment) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_INTENT_CODE);

    }

    @Override
    public void onCameraClick(DialogFragment dialogFragment) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(cameraIntent.resolveActivity( getPackageManager() ) != null) {
            startActivityForResult(cameraIntent, CAMERA_INTENT_CODE);
        }
    }

    private Bitmap retrieveImageFromCameraResult(Intent data){
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");

        return imageBitmap;
    }

    private Bitmap retrieveImageFromGalleryResult(Intent data){
        Uri selectedImageUri = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);

        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        Bitmap image = BitmapFactory.decodeFile(picturePath);
        return image;
    }
}
