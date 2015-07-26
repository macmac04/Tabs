package com.app.macky.droidgency;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;


public class UploadActivity extends Fragment {
    private Button mButton;
    private ImageView mImage;
    private Uri mUri;
    private EditText mMessageView;
    private ImageButton mTweetButton;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String TAG = "UploadActivity";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_upload, container, false);

        mButton = (Button) view.findViewById(R.id.camera_button);
        mImage = (ImageView) view.findViewById(R.id.my_image);
        // If we have an image saved to disk, set it to our ImageView after converting to bitmap and resizing it
        if (Utils.doesSavedImageExist(this.getActivity())) {
            mImage.setImageBitmap(Utils.getResizedBitmapFromFile(Utils.getSavedImage(this.getActivity())));
        }
        mUri = Utils.getOutputMediaFileUri(getActivity());


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the intent
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                // Add the output URI as an extra argument in the intent
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);

                // Start the image capture Intent
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        });


        // This is where the user will enter their tweet message
        mMessageView = (EditText) view.findViewById(R.id.message);

        /**
         * Clicking the Tweet button triggers a chain of events.
         * We get an instance of TwitterService and ask it to post a
         * tweet with our message and picture. If we're not logged in,
         * TweetService checks performs
         * the necessary OAuth steps and redirects us to a web browser where
         * we can log into Twitter. The browser redirects us back to the app
         * where we can try to tweet again now that we are logged in.
         */
        mTweetButton = (ImageButton) view.findViewById(R.id.tweet_button);
        mTweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progress = new ProgressDialog(getActivity());
                progress.setTitle("Loading");
                progress.setMessage("Wait while loading...");
                progress.show();
// To dismiss the dialo
                Log.d(TAG, "Clicked on Tweet button");

                /**
                 * Get the saved image. If there isn't one,
                 * save a file with the current bitmap image
                 */
                File image = null;
                if (Utils.doesSavedImageExist(UploadActivity.this.getActivity())) {
                    image = Utils.getSavedImage(UploadActivity.this.getActivity());
                } else {
                    image = Utils.saveToFile(
                            UploadActivity.this.getActivity(),
                            ((BitmapDrawable) mImage.getDrawable()).getBitmap()
                    );
                }

                String message = mMessageView.getText().toString();

                // Tweet!
                TwitterService.getInstance(UploadActivity.this.getActivity()).tweet(UploadActivity.this.getActivity(), message, image);
                progress.dismiss();
            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == this.getActivity().RESULT_OK) {
                /**
                 * Image captured and saved to the URI specified in the Intent
                 * so we read the image from disk and set it to our ImageView mImage.
                 */
                Bitmap photo = Utils.getResizedBitmapFromUri(mUri);
                mImage.setImageBitmap(photo);
                Toast.makeText(this.getActivity(), "Worked", Toast.LENGTH_SHORT);
            } else if (resultCode == this.getActivity().RESULT_CANCELED) {
                // User cancelled the image capture, do nothing
            } else {
                // Image capture failed, do nothing
            }
        }
    }
}
