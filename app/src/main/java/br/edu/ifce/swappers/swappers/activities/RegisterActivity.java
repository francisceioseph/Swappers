package br.edu.ifce.swappers.swappers.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.fragments.dialogs.UserPhotoDialogFragment;
import br.edu.ifce.swappers.swappers.webservice.UserService;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.OnClickListener;

public class RegisterActivity extends AppCompatActivity implements UserPhotoDialogFragment.UserPhotoDialogListener{

    private static final short CAMERA_INTENT_CODE  = 1015;
    private static final short GALLERY_INTENT_CODE = 1016;

    private Toolbar toolbar;
    private CircleImageView userPhotoCircleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setUpActivityToolbar();

        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this.makeRegisterButtonClickListener());

        this.userPhotoCircleImageView = (CircleImageView) findViewById(R.id.user_photo_circle_image_view);
        this.userPhotoCircleImageView.setOnClickListener(this.makeUserPhotoCircleButtonClickListener());
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
            this.setSupportActionBar(this.toolbar);
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
                    RegisterActivity.this.saveRegisterInformation();
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

        return (Bitmap) extras.get("data");
    }

    private Bitmap retrieveImageFromGalleryResult(Intent data){
        Uri selectedImageUri;

        String picturePath;
        String[] filePathColumn;

        Cursor cursor;
        int columnIndex;


        selectedImageUri = data.getData();
        filePathColumn = new String[]{ MediaStore.Images.Media.DATA };
        cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);

        cursor.moveToFirst();
        columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        picturePath = cursor.getString(columnIndex);

        cursor.close();

        return BitmapFactory.decodeFile(picturePath);
    }

    /*
    *
    * Local and Webservice database comunication methods
    *
    * */

    private void saveRegisterInformation(){
        EditText userNameEditText     = (EditText) findViewById(R.id.user_name_edit_text);
        EditText userEmailEditText    = (EditText) findViewById(R.id.user_email_edit_text);
        EditText userPasswordEditText = (EditText) findViewById(R.id.user_password_edit_text);
        EditText userPasswordConfirmationEditText = (EditText) findViewById(R.id.user_password_confirmation_edit_text);

        String userName = userNameEditText.getText().toString();
        String userEmail = userEmailEditText.getText().toString();
        String userPassword = userPasswordEditText.getText().toString();

        syncToRemoteDatabase(userName,userEmail,userPassword);
        //TODO Save data information on database
    }

    private void syncToRemoteDatabase(String userName, String userEmail, String userPassword){
        //TODO Send data to webservice remote database
        UserService.authenticateUserWithWS(userName,userEmail,userPassword, getApplicationContext());

    }
}
