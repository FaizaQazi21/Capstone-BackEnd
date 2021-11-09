package com.capstone.data;


import com.capstone.models.Roles;

import java.util.List;

public interface RoleRepository {

    List<Roles> findAll();
    Roles findById (int id);
    Roles add (Roles roles);
    boolean update(Roles roles);
    boolean deleteById(int id);


}
