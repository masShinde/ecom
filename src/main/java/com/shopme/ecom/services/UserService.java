package com.shopme.ecom.services;

import com.shopme.ecom.admin.user.RoleRepository;
import com.shopme.ecom.admin.user.UserRepository;
import com.shopme.ecom.entities.Role;
import com.shopme.ecom.entities.User;
import com.shopme.ecom.exceptions.userExceptions.UserAlreadyExistsException;
import com.shopme.ecom.exceptions.userExceptions.UserCreateException;
import com.shopme.ecom.exceptions.userExceptions.UserInternalException;
import com.shopme.ecom.exceptions.userExceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> listAllUsers(){
        try {
            return (List<User>) userRepository.findAll();
        }catch (Exception ex){
            throw new UserInternalException("Error while fetching users! Please try again.");
        }
    }

    public Page<User> listByPage(int pageNum, int pageSize, String sortField, String sortDir, String keyword){
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum-1, pageSize, sort);
        return userRepository.findAll(keyword, pageable);
    }

    public List<Role> listRoles(){
        return (List<Role>) roleRepository.findAll();
    }

    public User findUserById(Integer id){
        try{
            return userRepository.findById(id).get();
        }catch(Exception ex){
            throw new UserNotFoundException("User Not Found!");
        }
    }

    public User updateUser(User user){
        User existingUser = findUserById(user.getId());
        if(existingUser == null)
            throw new UserNotFoundException("User Not Found!");
        if(!existingUser.getEmail().equals(user.getEmail())  && !isEmailUnique(user.getEmail()))
            throw new UserAlreadyExistsException("User with provided email already exists.");
        try {
            if(user.getPassword() == null)
                user.setPassword(existingUser.getPassword());
            return userRepository.save(user);
        }catch (Exception ex){
            throw new UserCreateException("Error While updating the user! Please try again.");
        }
    }


    public User saveUser(User user){
        if(!isEmailUnique(user.getEmail()))
            throw new UserAlreadyExistsException("User with given email already exists!");
        encodePassword(user);
        try {
            User savedUser = userRepository.save(user);
            return savedUser;
        }catch (Exception ex){
            throw new UserCreateException("Error while registering user! Please try again.");
        }
    }

    public void deleteUserById(int id){
        if(findUserById(id) == null)
            throw new UserNotFoundException("User does not exist!");
        try {
            userRepository.deleteById(id);
        }catch (Exception ex){
            throw new UserInternalException("Error while deleting User! Please try again.");
        }
    }

    public void updateEnableStatus(int id, boolean isEnabled){
        if(findUserById(id) == null)
            throw new UserNotFoundException("User does not exist!");
        try {
            userRepository.updateEnabledStatus(id, isEnabled);
        }catch (Exception ex){
            throw new UserInternalException("Error while updating the status! Please try again.");
        }
    }

    private void encodePassword(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    private boolean isEmailUnique(String email){
        return userRepository.getUserByEmail(email) == null;
    }

}
