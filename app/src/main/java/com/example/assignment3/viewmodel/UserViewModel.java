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

public class UserViewModel extends AndroidViewModel {
    private UserRepository uRepository;
    private LiveData<List<User>> allUsers;

    public UserViewModel(Application application) {
        super(application);
        uRepository = new UserRepository(application);
        allUsers = uRepository.getAllUsers();
    }

    public User findByEmail(final String email) {
        return uRepository.findByEmail(email);
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
