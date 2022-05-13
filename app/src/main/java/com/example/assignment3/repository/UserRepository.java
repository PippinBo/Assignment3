package com.example.assignment3.repository;

import com.example.assignment3.dao.UserDao;
import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.assignment3.database.UserDatabase;
import com.example.assignment3.entity.Movement;
import com.example.assignment3.entity.User;
import com.example.assignment3.entity.relationship.UserWithMovements;

import java.util.List;

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;

    public UserRepository(Application application) {
        UserDatabase db = UserDatabase.getInstance(application);
        userDao = db.userDao();
        allUsers = userDao.getAll();
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public void insertUser(final User user) {
        UserDatabase.databaseWriteExecutor.execute(() -> userDao.insertUser(user));
    }

    public void insertMovement(final Movement movement) {
        UserDatabase.databaseWriteExecutor.execute(() -> userDao.insertMovement(movement));
    }

    public void deleteAllUser() {
        UserDatabase.databaseWriteExecutor.execute(() -> userDao.deleteAllUser());
    }

    public void deleteAllMovement() {
        UserDatabase.databaseWriteExecutor.execute(() -> userDao.deleteAllMovement());
    }

    public void deleteUser(final User user) {
        UserDatabase.databaseWriteExecutor.execute(() -> userDao.deleteUser(user));
    }

    public void deleteMovement(final Movement movement) {
        UserDatabase.databaseWriteExecutor.execute(() -> userDao.deleteMovement(movement));
    }

    public void updateUser(final User user) {
        UserDatabase.databaseWriteExecutor.execute(() -> userDao.updateUser(user));
    }

    public void updateMovement(final Movement movement) {
        UserDatabase.databaseWriteExecutor.execute(() -> userDao.updateMovement(movement));
    }

    public LiveData<User> findByEmail(final String email) {
        return userDao.findByEmail(email);
    }

    public LiveData<List<UserWithMovements>> getMovementByEmail(final String email) { return userDao.getMovementByEmail(email); }

    public void deleteMovement(final int userId, final String time, final long movement) {
        UserDatabase.databaseWriteExecutor.execute(() -> userDao.deleteMovement(userId, time, movement));
    }
}

