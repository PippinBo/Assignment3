package com.example.assignment3.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Version 1.0.3: data entry --- Lichen
@Entity(tableName = "User")
public class User implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    private String email;
    private String password;
    @ColumnInfo(name = "role")
    @NonNull
    private String role;
    @ColumnInfo(name = "name")
    @NonNull
    private String name;
    @ColumnInfo(name = "address")
    @NonNull
    private String address;

    public User(Parcel in) {
        this.email = in.readString();
        this.password = in.readString();
        this.role = in.readString();
        this.name = in.readString();
        this.address = in.readString();
    }

    public User(String email, String password, @NonNull String role, @NonNull String name, @NonNull String address) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
        this.address = address;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(role);
        parcel.writeString(name);
        parcel.writeString(address);
    }

    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NonNull
    public String getRole() {
        return role;
    }

    public void setRole(@NonNull String role) {
        this.role = role;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }
}
