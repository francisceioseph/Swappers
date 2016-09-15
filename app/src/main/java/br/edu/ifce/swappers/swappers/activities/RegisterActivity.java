package br.edu.ifce.swappers.swappers.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.fragments.dialogs.UserPhotoDialogFragment;
import br.edu.ifce.swappers.swappers.model.User;
import br.edu.ifce.swappers.swappers.miscellaneous.utils.AndroidUtils;
import br.edu.ifce.swappers.swappers.miscellaneous.utils.ImageUtil;
import br.edu.ifce.swappers.swappers.miscellaneous.tasks.RegisterTask;
import br.edu.ifce.swappers.swappers.miscellaneous.SwappersToast;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.TaskInterface;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.OnClickListener;

public class RegisterActivity extends AppCompatActivity implements UserPhotoDialogFragment.UserPhotoDialogListener,TaskInterface{

    private static final short CAMERA_INTENT_CODE  = 1015;
    private static final short GALLERY_INTENT_CODE = 1016;

    private Toolbar toolbar;
    private CircleImageView userPhotoCircleImageView;
    public Bitmap userPhotoBitmap;

    private CallbackManager callbackManager;
    private User user = new User();
    private boolean flagFb = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.initToolbar();

        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this.makeRegisterButtonClickListener());
        LoginButton registerFacebookButton = (LoginButton) findViewById(R.id.login_button);
        registerFacebookButton.setOnClickListener(this.makeRegisterFbButtonClickListener());

        this.userPhotoCircleImageView = (CircleImageView) findViewById(R.id.user_photo_circle_image_view);
        this.userPhotoCircleImageView.setOnClickListener(this.makeUserPhotoCircleButtonClickListener());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(flagFb){
            super.onActivityResult(requestCode, resultCode, data);
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

        if (requestCode == CAMERA_INTENT_CODE && resultCode == RESULT_OK){
            userPhotoBitmap = ImageUtil.retrieveImageFromCameraResult(data);
            this.userPhotoCircleImageView.setImageBitmap(userPhotoBitmap);
        }
        else if (requestCode == GALLERY_INTENT_CODE && resultCode == RESULT_OK) {
            userPhotoBitmap = ImageUtil.retrieveImageFromGalleryResult(data, this);
            this.userPhotoCircleImageView.setImageBitmap(userPhotoBitmap);
        }
    }

    private void initToolbar(){
        if (this.toolbar != null){
            this.setSupportActionBar(this.toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    /*
    *
    * Listener builder methods
    *
    * */
    private OnClickListener makeUserPhotoCircleButtonClickListener() {

        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                UserPhotoDialogFragment dialogFragment = new UserPhotoDialogFragment(getString(R.string.userPhotoDialogTitle),
                        getString(R.string.new_profile_picture_message));
                dialogFragment.show(getSupportFragmentManager(), "User Photo Dialog Fragment");
            }
        };
    }

    private OnClickListener makeRegisterButtonClickListener(){
        return new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(AndroidUtils.isNetworkAvailable(getApplicationContext())) {
                    RegisterActivity.this.saveRegisterInformation();
                }
                else {
                    AndroidUtils.makeDialog(getApplicationContext(),
                            getString(R.string.dialog_error_title),
                            getString(R.string.internet_connection_error_message)).show();
                }
            }
        };
    }

    private OnClickListener makeRegisterFbButtonClickListener() {
        return new OnClickListener() {
            @Override
            public void onClick(View view) {
                flagFb = true;

                if(AndroidUtils.isNetworkAvailable(getApplicationContext())) {
                    RegisterActivity.this.saveRegisterFromFb();
                }
                else {
                    AndroidUtils.makeDialog(getApplicationContext(),
                            getString(R.string.dialog_error_title),
                            getString(R.string.internet_connection_error_message)).show();
                }

            }
        };
    }


    /*
    *
    * Validation methods for the form fields
    *
    * */
    @Override
    public void startNextActivity() {
        final Intent mainActivityIntent = new Intent(this, MainActivity.class);
        mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        AlertDialog alert = AndroidUtils.makeDialog(this,
                getString(R.string.dialog_success_title),
                getString(R.string.user_registration_success_message));

        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                startActivity(mainActivityIntent);

                onBackPressed();
            }
        });

        alert.show();
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

    /*
    *
    * Local and Webservice database communication methods
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
        String userPasswordConfirmation = userPasswordConfirmationEditText.getText().toString();

        if (validationRegistryUser(userName, userEmail, userPassword, userPasswordConfirmation)){
            callAsyncTask(userName, userEmail, userPassword);
        }
    }

    private void saveRegisterFromFb(){
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email","public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Profile profile = Profile.getCurrentProfile();
                user.setName(profile.getName());
                final AccessToken pass = loginResult.getAccessToken();

                GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback(){
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            try {
                                user.setEmail(object.getString("email"));
                                if(validationRegistryFb(user.getName(), user.getEmail(), pass)){
                                    callAsyncTask(user.getName(), user.getEmail(), pass.toString());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                );
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.error_waiver_login, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getApplicationContext(), R.string.error_while_processing_donation_message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callAsyncTask(String name, String email, String usePassword){

        String passwordCodec = new String(Hex.encodeHex(DigestUtils.sha256(usePassword.getBytes())));

        RegisterTask registerTask = new RegisterTask(this,this);
        User user = new User(name, email, passwordCodec);

        if(userPhotoBitmap!=null) {
            user.setPhoto2(ImageUtil.BitMapToString(userPhotoBitmap));
        }

        registerTask.execute(user);
    }

    private boolean validationRegistryUser(String name, String email, String usePassword, String passwordConfirmation){
        if(validationNameUser(name)){
            if (validateEmailWithMasks(email)){
                if (validatePassword(usePassword,passwordConfirmation)){
                    return true;
                }
                else{return false;}
            }
            else{return false;}
        }
        else{return false;}
    }

    private boolean validationRegistryFb(String name, String email, AccessToken token){
        if(validationNameUser(name)){
            if(validateEmailWithMasks(email)){
                token.getToken();
                return true;
            }
        }

        return false;
    }

    private boolean validateEmailWithMasks(String email){
        Pattern pattern = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
        Matcher matcher = pattern.matcher(email);

        if(matcher.find()){
            return true;
        }
        else{
            SwappersToast.makeText(getApplicationContext(), getString(R.string.email_validation_error_message), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private boolean validatePassword(String pwd , String pwdConfirmation) {
        if (pwd.length() > 5 && pwdConfirmation.length() > 5) {
            if (pwd.equals(pwdConfirmation)) {
                return true;
            }
            else {
                SwappersToast.makeText(getApplicationContext(), getString(R.string.mismatching_password_error_message), Toast.LENGTH_LONG).show();
                return false;
            }
        }
        else{
            SwappersToast.makeText(getApplicationContext(), getString(R.string.blank_password_error_message), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private boolean validationNameUser(String name){
        if (name.length()>2){
            return true;
        }
        else{
            SwappersToast.makeText(getApplicationContext(), getString(R.string.less_characters_name_error_message), Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
