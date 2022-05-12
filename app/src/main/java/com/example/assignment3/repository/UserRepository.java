package com.example.assignment3.repository;

import com.example.assignment3.dao.UserDao;
import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.assignment3.database.UserDatabase;
import com.example.assignment3.entity.User;

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

    public void insert(final User user) {
        UserDatabase.databaseWriteExecutor.execute(() -> userDao.insert(user));
    }

    public void deleteAll() {
        UserDatabase.databaseWriteExecutor.execute(() -> userDao.deleteAll());
    }

    public void delete(final User user) {
        UserDatabase.databaseWriteExecutor.execute(() -> userDao.delete(user));
    }

    public void updateUser(final User user) {
        UserDatabase.databaseWriteExecutor.execute(() -> userDao.updateUser(user));
    }

    public LiveData<User> findByEmail(final String email) {
        return userDao.findByEmail(email);
    }
}

