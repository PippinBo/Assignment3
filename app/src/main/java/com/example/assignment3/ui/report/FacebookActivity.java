package com.example.assignment3.ui.report;

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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.assignment3.R;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;

public class FacebookActivity extends AppCompatActivity {

    LoginButton loginButton;
    ShareButton sbLink;
    ShareButton sbPhoto;
    CallbackManager callbackManager;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_facebook);

        loginButton = findViewById(R.id.loginButton);
        imageView = findViewById(R.id.fb_photo);
        sbLink = findViewById(R.id.sb_link);
        sbPhoto = findViewById(R.id.sb_photo);
        callbackManager = CallbackManager.Factory.create();
        imageView.setImageResource(R.drawable.unnamed);

    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
        imageView.setImageResource(R.drawable.unnamed);

        ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://www.facebook.com/profile.php?id=100080892737577"))
                .setShareHashtag(new ShareHashtag.Builder()
                        .setHashtag("#My fitness report").build())
                .build();

        sbLink.setShareContent(shareLinkContent);

        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        SharePhoto sharePhoto = new SharePhoto.Builder()
                .setBitmap(bitmap)
                .build();

        SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder()
                .addPhoto(sharePhoto)
                .build();

        sbPhoto.setShareContent(sharePhotoContent);
    }
}