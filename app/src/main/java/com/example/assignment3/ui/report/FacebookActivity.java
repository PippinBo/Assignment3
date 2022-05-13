package com.example.assignment3.ui.report;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment3.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Share;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.GraphRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class FacebookActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private ShareButton sbLink;
    private ShareButton sbPhoto;
    private CallbackManager callbackManager;
    private ImageView imageView;

    //fb dialog
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_facebook);

        loginButton = findViewById(R.id.loginButton);
        imageView = findViewById(R.id.fb_photo);
        sbLink = findViewById(R.id.sb_link);
        sbPhoto = findViewById(R.id.sb_photo);
        callbackManager = CallbackManager.Factory.create();
        imageView.setImageResource(R.drawable.screenshot_1652368992);


        shareDialog = new ShareDialog(this);
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("https://www.facebook.com/profile.php?id=100080892737577"))
                    .build();
            shareDialog.show(shareLinkContent);
        }

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Demo", "Login successful!");
            }

            @Override
            public void onCancel() {
                Log.d("Demo", "Login canceled!");
            }

            @Override
            public void onError(@NonNull FacebookException e) {
                Log.d("Demo", "Login error!");
            }
        });

        sbLink.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Log.d("Demo", "Share successful!");
            }

            @Override
            public void onCancel() {
                Log.d("Demo", "Share canceled!");
            }

            @Override
            public void onError(@NonNull FacebookException e) {
                Log.d("Demo", "Share error!");
            }
        });

        sbPhoto.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Log.d("Demo", "Share successful!");
            }

            @Override
            public void onCancel() {
                Log.d("Demo", "Share canceled!");
            }

            @Override
            public void onError(@NonNull FacebookException e) {
                Log.d("Demo", "Share error!");
            }
        });
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
        imageView.setImageResource(R.drawable.screenshot_1652368992);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(@Nullable JSONObject jsonObject, @Nullable GraphResponse graphResponse) {
                        Log.d("Demo", jsonObject.toString());
                        try {
                            String email = jsonObject.getString("email");
                            Picasso.get().load("http://graph.facebook.com" + email + "/picture?type=large")
                                    .into(imageView);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://www.facebook.com/profile.php?id=100080892737577"))
                .setShareHashtag(new ShareHashtag.Builder()
                        .setHashtag("#My fitness report").build())
                .build();

        sbLink.setShareContent(shareLinkContent);

        SharePhoto sharePhoto = new SharePhoto.Builder()
                .setBitmap(bitmap)
                .build();

        SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder()
                .addPhoto(sharePhoto)
                .build();

        sbPhoto.setShareContent(sharePhotoContent);

    }
}