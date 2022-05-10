package com.example.assignment3.repository;

import com.example.assignment3.dao.UserDao;
import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.assignment3.database.UserDatabase;
import com.example.assignment3.entity.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

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

        public void insert(final User user){
            UserDatabase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    userDao.insert(user);
                }
            });
        }

        public void deleteAll(){
            UserDatabase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    userDao.deleteAll();
                }
            });
        }
        public void delete(final User user){
            UserDatabase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    userDao.delete(user);
                }
            });
        }

    public void updateUser(final User user){
        UserDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.updateUser(user);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<User> findByIDFuture(final int userId) {
        return CompletableFuture.supplyAsync(new Supplier<User>() {
            @Override
            public User get() {
                return userDao.findByID(userId);
            }
        }, UserDatabase.databaseWriteExecutor);
    }
}

