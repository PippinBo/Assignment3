package com.example.assignment3.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.assignment3.entity.Movement;
import com.example.assignment3.entity.User;
import com.example.assignment3.entity.relationship.UserWithMovements;
import com.example.assignment3.repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository uRepository;
    private LiveData<List<User>> allUsers;

    public UserViewModel(Application application) {
        super(application);
        uRepository = new UserRepository(application);
        allUsers = uRepository.getAllUsers();
    }

    public LiveData<User> findByEmail(final String email) {
        return uRepository.findByEmail(email);
    }

    public LiveData<List<UserWithMovements>> getMovementByEmail(final String email) { return uRepository.getMovementByEmail(email); }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

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

    public LiveData<List<Movement>> getMovementById(int id){return uRepository.getMovementByID(id);}
    public void deleteByRecord(int id, String date, long distance){uRepository.deleteByRecord(id,date,distance);}
    public void editByRecord(int id, String date, long distance){ uRepository.editByRecord(id,date,distance);}
    public void editDistanceByRecord(int id, String date, long distance, long newDistance){ uRepository.editDistanceByRecord(id, date, distance, newDistance);}
}
