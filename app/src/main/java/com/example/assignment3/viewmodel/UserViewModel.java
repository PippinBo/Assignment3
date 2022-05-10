package com.example.assignment3.viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.assignment3.entity.User;
import com.example.assignment3.repository.UserRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UserViewModel  extends AndroidViewModel {
    private UserRepository uRepository;
    private LiveData<List<User>> allUsers;
    public UserViewModel (Application application) {
        super(application);
        uRepository = new UserRepository(application);
        allUsers = uRepository.getAllUsers();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<User> findByIDFuture(final int userId){
        return uRepository.findByIDFuture(userId);
    }
    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }
    public void insert(User user) {
        uRepository.insert(user);
    }

    public void deleteAll() {
        uRepository.deleteAll();
    }
    public void update(User user) {
        uRepository.updateUser(user);
    }
}
