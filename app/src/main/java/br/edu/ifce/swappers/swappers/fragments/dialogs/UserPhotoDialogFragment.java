package br.edu.ifce.swappers.swappers.fragments.dialogs;


/**
 * Last modified by Joamila on 14/11/2015
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import at.markushi.ui.CircleButton;
import br.edu.ifce.swappers.swappers.R;

public class UserPhotoDialogFragment extends DialogFragment {

    private String title = new String();
    private String description = new String();

    public interface UserPhotoDialogListener {
        void onGalleryClick(DialogFragment dialogFragment);
        void onCameraClick(DialogFragment dialogFragment);
    }

    public UserPhotoDialogFragment() {
    }

    public UserPhotoDialogFragment(String title, String description) {
        this.title = title;
        this.description = description;
    }

    UserPhotoDialogListener userPhotoDialogListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        FragmentActivity currentActivity    = getActivity();
        AlertDialog.Builder dialogBuilder   = new AlertDialog.Builder(currentActivity);
        LayoutInflater dialogLayoutInflater = currentActivity.getLayoutInflater();
        View dialogView                     = dialogLayoutInflater.inflate(R.layout.fragment_user_photo_dialog, null);

        TextView dialogTitleTextView = (TextView) dialogView.findViewById(R.id.textView2);
        dialogTitleTextView.setText(this.title);
        TextView dialogDescriptionTextView = (TextView) dialogView.findViewById(R.id.textSelectPicture);
        dialogDescriptionTextView.setText(this.description);

        CircleButton galleryCircleButton    = (CircleButton) dialogView.findViewById(R.id.galleryCircleButton);
        CircleButton cameraCircleButton     = (CircleButton) dialogView.findViewById(R.id.cameraCircleButton);

        dialogBuilder.setView(dialogView);

        galleryCircleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPhotoDialogListener.onGalleryClick(UserPhotoDialogFragment.this);
                dismiss();
            }
        });

        cameraCircleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPhotoDialogListener.onCameraClick(UserPhotoDialogFragment.this);
                dismiss();
            }
        });

        return dialogBuilder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            this.userPhotoDialogListener = (UserPhotoDialogListener) activity;
        }
        catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
        }
    }


}
