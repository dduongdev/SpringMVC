package com.abc.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.dao.UserDAO;
import com.abc.entities.ExtendedUser;
import com.abc.entities.User;

@Service 
public class UserServiceImpl implements UserService {

	@Autowired
    private UserDAO userDAO;

    @Override
    public User getUserByUserName(String userName) {
        return userDAO.getUserByUserName(userName);
    }

    @Override
    public boolean registerUser(User user) {
        return userDAO.registerUser(user);
    }

	@Override
	public List<User> searchOnUsername(String keyword) {
		return userDAO.searchOnUsername(keyword);
	}

	@Override
	public boolean isEmailExists(String email) {
		return userDAO.isEmailExists(email);
	}

	@Override
	public ExtendedUser getExtendedUserByUsername(String username) {
		return new ExtendedUser("dduongdev", "123", "kyvalaxz2021@gmail.com", LocalDate.of(2004, 3, 13), 1, "avt.jpg");
	}

	@Override
	public void update(ExtendedUser user) {
		userDAO.update(user);
	}
}