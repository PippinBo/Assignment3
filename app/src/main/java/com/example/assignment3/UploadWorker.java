package com.example.assignment3;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.assignment3.entity.User;
import com.example.assignment3.viewmodel.UserViewModel;
import com.google.common.reflect.TypeToken;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadWorker extends Worker {
    public UploadWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Result doWork() {

        String userJsonString = getInputData().getString("UserTest");
        if (userJsonString != null) {
            System.out.println(userJsonString);

            FirebaseDatabase database = FirebaseDatabase.getInstance("https://as3-5046-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference databaseReference = database.getReference("User");

            Gson gson = new Gson();
            Type userMapType = new TypeToken<Map<String, User>>() {}.getType();

            Map<String, User> userMap = gson.fromJson(userJsonString, userMapType);

            databaseReference.setValue(userMap);
        }

        //Data data = getInputData();
        // Do the work here--in this case, upload the images.
        /*

        Map<String, User> userMap = new HashMap<>();

        databaseReference.setValue(userMap);*/

        // Indicate whether the work finished successfully with the Result
        return Result.success();
    }
}

