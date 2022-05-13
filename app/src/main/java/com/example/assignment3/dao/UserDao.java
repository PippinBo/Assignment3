package com.example.assignment3.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.assignment3.entity.User;
import com.example.assignment3.entity.Movement;
import com.example.assignment3.entity.relationship.UserWithMovements;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user ORDER BY uid ASC")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM user WHERE email = :email")
    LiveData<User> findByEmail(String email);

    @Transaction
    @Query("SELECT * FROM user WHERE email = :email")
    LiveData<List<UserWithMovements>> getMovementByEmail(String email);

    @Insert
    void insertUser(User user);

    @Insert
    void insertMovement(Movement movement);

    @Delete
    void deleteUser(User user);

    @Delete
    void deleteMovement(Movement movement);

    @Update
    void updateUser(User user);

    @Update
    void updateMovement(Movement movement);

    @Query("DELETE FROM user")
    void deleteAllUser();

    @Query("DELETE FROM movement")
    void deleteAllMovement();
}
