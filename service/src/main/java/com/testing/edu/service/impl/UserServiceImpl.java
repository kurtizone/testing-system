package com.testing.edu.service.impl;


import com.testing.edu.entity.User;
import com.testing.edu.repository.UserRepository;
import com.testing.edu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean isUsernameExist(String username) {
        return userRepository.findByUsername(username) == null;
    }


}
