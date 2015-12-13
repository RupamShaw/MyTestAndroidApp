package com.example.dell.myapplication;

import android.net.Uri;

/**
 * Created by Dell on 18/11/2015.
 */
public class Student {
    String _id;
    String _name;
    String _address;
    String _phone;
    String _email;
    Uri _imageUri;
    public Uri get_imageUri() {
        return _imageUri;
    }

    public void set_imageUri(Uri _imageUri) {
        this._imageUri = _imageUri;
    }



    public Student(String id, String name, String address, String phone, String email, Uri imageUri) {
        _id = id;
        _name = name;
        _address = address;
        _phone = phone;
        _email = email;
        _imageUri=imageUri;

    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }

    public String get_phone() {
        return _phone;
    }

    public void set_phone(String _phone) {
        this._phone = _phone;
    }


}
