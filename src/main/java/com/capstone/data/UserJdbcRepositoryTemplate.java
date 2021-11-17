package com.capstone.data;

import com.capstone.data.mapper.UserMapper;
import com.capstone.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserJdbcRepositoryTemplate implements UserRepository{
    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepositoryTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        final String sql = "select * from user;";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    @Override
    public User findById(int id) {
        final String sql = "select * from user where user_id = ?;";
        return jdbcTemplate.query(sql, new UserMapper(), id).stream().findAny().orElse(null);
    }

    @Override
    public User add(User user) {
        final String sql = "insert into user (name, role_id, email, password, salary) values(?,?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getRole_id());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setDouble(5, user.getSalary());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        user.setId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public boolean update(User user) {
        final String sql = "update user set name = ? , role_id = ?, email = ?, password = ? , salary =? where user_id = ?;";

        return jdbcTemplate.update(sql, user.getName(), user.getRole_id(), user.getEmail(), user.getPassword(),
                user.getSalary(), user.getId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        return jdbcTemplate.update(
                "delete from user where user_id = ?", id) > 0;
    }
}
