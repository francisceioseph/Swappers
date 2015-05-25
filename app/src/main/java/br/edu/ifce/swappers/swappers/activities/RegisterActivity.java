package br.edu.ifce.swappers.swappers.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import at.markushi.ui.CircleButton;
import br.edu.ifce.swappers.swappers.R;

public class RegisterActivity extends AppCompatActivity{

    Toolbar toolbar;
    Button  registerButton;
    CircleButton userPhotoCircleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.toolbar                = (Toolbar) findViewById(R.id.toolbar);
        this.registerButton         = (Button)  findViewById(R.id.registerButton);
        this.userPhotoCircleButton  = (CircleButton) findViewById(R.id.userPhotoCircleButton);


        this.setUpActivityToolbar();
        this.registerButton.setOnClickListener(this.makeRegisterButtonClickListener());
        this.userPhotoCircleButton.setOnClickListener(this.makeUserPhotoCircleButtonClickListener());

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

    /*
     * Helper Methods for internal Activity use
     */

    private void setUpActivityToolbar(){
        if (this.toolbar != null){
            setSupportActionBar(toolbar);
        }
    }

    private View.OnClickListener makeUserPhotoCircleButtonClickListener() {
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Open a Dialog to know if the user wants to open a gallery or a camera

            }
        };

        return clickListener;
    }

    private View.OnClickListener makeRegisterButtonClickListener(){

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateRegisterForm() == true){
                    RegisterActivity.this.startMainActivity();
                }
                else{
                    RegisterActivity.this.markWrongFields();
                }
            }
        };

        return clickListener;
    }

    private boolean validateRegisterForm(){
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

    private void openPhotoGallery(){
        //TODO Open a Gallery
    }

    private void openCamera(){
        //TODO Open a Camera
    }
}
