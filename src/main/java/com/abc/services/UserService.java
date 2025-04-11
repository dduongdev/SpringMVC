package com.abc.services;

import java.util.List;

import com.abc.entities.ExtendedUser;
import com.abc.entities.User;

public interface UserService {
    User getUserByUserName(String userName);
    boolean registerUser(User user);
    List<User> searchOnUsername(String username);
    boolean isEmailExists(String email);
    ExtendedUser getExtendedUserByUsername(String username);
    void update(ExtendedUser user);
}