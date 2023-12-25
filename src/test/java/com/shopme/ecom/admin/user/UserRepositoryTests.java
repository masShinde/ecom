package com.shopme.ecom.admin.user;

import com.shopme.ecom.entities.Role;
import com.shopme.ecom.entities.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUser(){
        Role admin = entityManager.find(Role.class, 1);
        User newUser = new User("u1@test.com", "123456", "f1", "l1");
        newUser.addRole(admin);
        User savedUser = repo.save(newUser);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateUserWithTwoRoles(){
        Role roleEditor = new Role(2);
        Role roleAssistant = new Role(4);
        User newUser = new User("u2@test.com", "123123", "f2", "l2");
        newUser.addRole(roleEditor);
        newUser.addRole(roleAssistant);
        User savedUser = repo.save(newUser);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers(){
        Iterable<User> users = repo.findAll();
        users.forEach(user -> System.out.println(user));
    }

    @Test
    public void testGetUserById(){
        User user = repo.findById(1).get();
        assertThat(user).isNotNull();
    }

    @Test
    public void testUpdateUserDetails(){
        User user = repo.findById(1).get();
        user.setEnabled(true);
        user.setEmail("updateEmail@test.com");
        repo.save(user);
    }

    @Test
    public void testUpdateUserRoles(){
        User user = repo.findById(1).get();
        Role roleEditor = new Role(2);
        Role rolesSalesPerson = new Role(1);
        user.getRoles().remove(roleEditor);
        user.addRole(rolesSalesPerson);
        repo.save(user);
    }

    @Test
    public void testDeleteUser(){
        repo.deleteById(2);
    }

    @Test
    public void testGetUserByEmail(){
        User u = repo.getUserByEmail("u2@test.com");
        assertThat(u).isNotNull();

        User u2 = repo.getUserByEmail("u4@test.com");
        assertThat(u2).isNull();
    }

    @Test
    public void testCoundById(){
        int id = 1;
        long count = repo.countById(id);
        assertThat(count).isNotNull().isGreaterThan(0);
    }

    @Test
    public void disableUser(){
        int id = 1;
        repo.updateEnabledStatus(id, false);
        User u = repo.findById(id).get();
        assertThat(u.isEnabled()).isEqualTo(false);
    }

    @Test
    public void enableUser(){
        int id = 1;
        repo.updateEnabledStatus(id, true);
        User u = repo.findById(id).get();
        assertThat(u.isEnabled()).isEqualTo(true);
    }

    @Test
    public void testListFirstPage(){
        int pageNumber = 0;
        int pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = repo.findAll(pageable);
        List<User> users = page.getContent();
        assertThat(users.size()).isEqualTo(pageSize);
    }

}
