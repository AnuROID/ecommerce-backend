package com.example.ecommerce.service;

import com.example.ecommerce.exception.UserException;
import com.example.ecommerce.model.User;

public interface UserService {
    public User findUserById(long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;
}
