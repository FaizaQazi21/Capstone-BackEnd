package com.capstone.data;


import com.capstone.models.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();
    User findById (int id);
    User add (User user);
    boolean update(User user);

    boolean deleteById(int id);
}
