package com.example.assignment3.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.assignment3.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user ORDER BY uid ASC")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM user WHERE email = :email")
    LiveData<User> findByEmail(String email);



    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Update
    void updateUser(User user);

    @Query("DELETE FROM user")
    void deleteAll();
}
