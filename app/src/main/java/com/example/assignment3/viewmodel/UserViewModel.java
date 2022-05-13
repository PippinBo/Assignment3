package com.example.assignment3.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.assignment3.entity.Movement;
import com.example.assignment3.entity.User;
import com.example.assignment3.entity.relationship.UserWithMovements;
import com.example.assignment3.repository.UserRepository;

import java.util.List;
import java.util.PrimitiveIterator;

public class UserViewModel extends AndroidViewModel {
    private UserRepository uRepository;
    private LiveData<List<User>> allUsers;
    private LiveData<List<String>> allAddress;

    public UserViewModel(Application application) {
        super(application);
        uRepository = new UserRepository(application);
        allUsers = uRepository.getAllUsers();
    }

    public LiveData<User> findByEmail(final String email) {
        return uRepository.findByEmail(email);
    }


    public  LiveData<List<String>> getAllGym(final String role) {
        return uRepository.getAddressByRole(role);
    }

    public LiveData<List<UserWithMovements>> getMovementByEmail(final String email) { return uRepository.getMovementByEmail(email); }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public void deleteMovement(final int userId, final String time, final long movement) {
        uRepository.deleteMovement(userId, time, movement);
    }

    public LiveData<List<String>> getAddressByRole(final String role) { return uRepository.getAddressByRole(role); }

    public void insertUser(User user) {
        uRepository.insertUser(user);
    }

    public void insertMovement(Movement movement) {
        uRepository.insertMovement(movement);
    }

    public void deleteAllUser() {
        uRepository.deleteAllUser();
    }

    public void deleteAllMovement() {
        uRepository.deleteAllMovement();
    }

    public void deleteUser(User user) { uRepository.deleteUser(user); }

    public void deleteMovement(Movement movement) { uRepository.deleteMovement(movement); }

    public void updateUser(User user) {
        uRepository.updateUser(user);
    }

    public void updateMovement(Movement movement) {
        uRepository.updateMovement(movement);
    }

}
