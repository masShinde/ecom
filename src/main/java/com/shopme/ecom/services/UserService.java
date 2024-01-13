package com.shopme.ecom.services;

import com.shopme.ecom.admin.user.RoleRepository;
import com.shopme.ecom.admin.user.UserRepository;
import com.shopme.ecom.dto.userDtos.CreateUserRequest;
import com.shopme.ecom.dto.userDtos.IUserRequest;
import com.shopme.ecom.dto.userDtos.UpdateUserRequest;
import com.shopme.ecom.entities.Role;
import com.shopme.ecom.entities.User;
import com.shopme.ecom.exceptions.CommonExceptions.InvalidImageException;
import com.shopme.ecom.exceptions.userExceptions.UserAlreadyExistsException;
import com.shopme.ecom.exceptions.userExceptions.UserCreateException;
import com.shopme.ecom.exceptions.userExceptions.UserInternalException;
import com.shopme.ecom.exceptions.userExceptions.UserNotFoundException;
import com.shopme.ecom.utils.FileUploadUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public User updateUser(UpdateUserRequest user){
        User existingUser = findUserById(user.getId());
        if(existingUser == null)
            throw new UserNotFoundException("User Not Found!");
        if(!existingUser.getEmail().equals(user.getEmail())  && !isEmailUnique(user.getEmail()))
            throw new UserAlreadyExistsException("User with provided email already exists.");
        existingUser.setEmail(user.getEmail());
        existingUser.setPhotos(user.getPhotos());
        existingUser.setEnabled(user.isEnabled());
        existingUser.setRoles(getAssociatedRoles(user));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        if(user.getPassword() != null)
            existingUser.setPassword(user.getPassword());
        try {
            return userRepository.save(existingUser);
        }catch (Exception ex){
            throw new UserCreateException("Error While updating the user! Please try again.");
        }
    }

    public void updateProfileImage(int id, MultipartFile multipartFile){
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String uploadDir = "user-photos/" + id ;
        if(!multipartFile.isEmpty()){
            try {
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
                User user = findUserById(id);
                user.setPhotos(uploadDir+"/"+fileName);
                userRepository.save(user);
            }catch (Exception ex){
                throw new UserInternalException("Error while updating profile photo! Please try again.");
            }
        }else{
            throw new InvalidImageException("Please provide valid image!");
        }
    }


    public User saveUser(CreateUserRequest user){
        if(!isEmailUnique(user.getEmail()))
            throw new UserAlreadyExistsException("User with given email already exists!");
        User newUser = new User();
        newUser.setLastName(user.getLastName());
        newUser.setFirstName(user.getFirstName());
        newUser.setPassword(user.getPassword());
        newUser.setEnabled(user.isEnabled());
        newUser.setEmail(user.getEmail());
        newUser.setRoles(getAssociatedRoles(user));
        encodePassword(newUser);
        try {
            User savedUser = userRepository.save(newUser);
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

    private Set<Role> getAssociatedRoles(IUserRequest user){
        Set<Role> associatedRoles = new HashSet<>();
        for(Integer i: user.getRoles())
            associatedRoles.add(new Role(i));
        return associatedRoles;
    }

}
