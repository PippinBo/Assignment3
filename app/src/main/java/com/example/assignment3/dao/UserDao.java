package com.example.assignment3.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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


    @Query("DELETE FROM movement WHERE userId = :userId AND time = :time AND movement = :movement")
    void deleteMovement(int userId, String time, long movement);

    @Query("SELECT address FROM user WHERE role = :role")
    LiveData<List<String>> getAddressByRole(String role);

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

    // Get movement by user id
    @Query("SELECT * FROM movement WHERE userId = :userID")
    LiveData<List<Movement>> getMovementByID(int userID);

    // Edit
    @Query("UPDATE Movement SET movement = :distance WHERE userId = :id  AND time = :date")
    void editByRecord(int id, String date, long distance);

    // Delete
    @Query("DELETE FROM Movement WHERE userId = :id AND time = :date AND movement = :distance")
    void  deleteByRecord(int id,String date, long distance);

    @Query("UPDATE Movement SET movement = :newDistance WHERE userId = :id  AND time =:date AND movement = :distance")
    void editDistanceByRecord(int id, String date, long distance, long newDistance);

    @Query("SELECT * FROM movement WHERE userId = :id AND time = :date LIMIT 1")
    Movement checkDailyEntry(int id, String date);

}
