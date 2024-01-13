package com.shopme.ecom.controllers;

import com.shopme.ecom.dto.userDtos.CreateUserRequest;
import com.shopme.ecom.dto.userDtos.UpdateUserRequest;
import com.shopme.ecom.entities.SuccessResponse;
import com.shopme.ecom.entities.User;
import com.shopme.ecom.services.UserService;
import com.shopme.ecom.utils.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ResponseHandler responseHandler;

    @GetMapping("/users")
    public ResponseEntity<SuccessResponse> getAllUsers(){
        List<User> users = userService.listAllUsers();
        HashMap<String, List<User>> res = new HashMap<>();
        res.put("users", users);
        return responseHandler.handleResponse(HttpStatus.OK, res, "Users Fetched Successfully!");
    }

    @GetMapping("/users/pages")
    public ResponseEntity<SuccessResponse> getUsersByPage(@RequestParam int pageNum, @RequestParam int pageSize, @RequestParam String sortField, @RequestParam String sortDir , @RequestParam String keyword){
        Page<User> page = userService.listByPage(pageNum, pageSize, sortField, sortDir, keyword);
        HashMap<String, Page<User>> res = new HashMap<>();
        res.put("users", page);
        return responseHandler.handleResponse(HttpStatus.OK, res, "Users Fetched Successfully!");
    }

    @PostMapping("/users")
    public ResponseEntity<SuccessResponse> createNewUser(@RequestBody CreateUserRequest user){
        User savedUser = userService.saveUser(user);
        HashMap<String, User> res = new HashMap<>();
        res.put("user", savedUser);
        return responseHandler.handleResponse(HttpStatus.CREATED, res, "Users Created Successfully!");
    }

    @PostMapping("/users/profile")
    public ResponseEntity<SuccessResponse> uploadUserImage(@RequestParam int id, @RequestParam(name = "file") MultipartFile multipartFile) throws IOException {
        userService.updateProfileImage(id, multipartFile);
        return responseHandler.handleResponse(HttpStatus.OK, null, "Profile Image Updated Successfully!");
    }

    @PutMapping("/users")
    public ResponseEntity<SuccessResponse> updateUser( @RequestBody UpdateUserRequest user){
        User updatedUser = userService.updateUser(user);
        HashMap<String, User> res = new HashMap<>();
        res.put("user", updatedUser);
        return responseHandler.handleResponse(HttpStatus.OK, res, "User updated successfully.");
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<SuccessResponse> getUserById(@PathVariable int id){
        User updatedUser = userService.findUserById(id);
        HashMap<String, User> res = new HashMap<>();
        res.put("user", updatedUser);
        return responseHandler.handleResponse(HttpStatus.OK, res, "User Fetched successfully.");

    }

    @DeleteMapping("/users")
    public ResponseEntity<SuccessResponse> deleteUserById(@RequestParam int id){
        userService.deleteUserById(id);
        return responseHandler.handleResponse(HttpStatus.OK, null, "User Deleted successfully.");
    }

    @PutMapping("/users/enable")
    public ResponseEntity<SuccessResponse> setUserEnableStatus(@RequestParam int id, @RequestBody HashMap<String, Boolean> reqBody){
        userService.updateEnableStatus(id, (Boolean) reqBody.getOrDefault("isEnable", false));
        return responseHandler.handleResponse(HttpStatus.OK, null, "User updated successfully.");

    }

    @GetMapping("/users/csv")
    public void exportUsersCSV(HttpServletResponse response) throws IOException {
        List<User> users = userService.listAllUsers();
        UserCsvExporter exporter = new UserCsvExporter();
        exporter.export(users, response);
    }

    @GetMapping("/users/excel")
    public void exportUsersExcel(HttpServletResponse response) throws IOException {
        List<User> users = userService.listAllUsers();
        UserExcelExporter exporter = new UserExcelExporter();
        exporter.export(users, response);
    }

    @GetMapping("/users/pdf")
    public void exportUsersPDF(HttpServletResponse response) throws IOException {
        List<User> users = userService.listAllUsers();
        UserPDFExporter exporter = new UserPDFExporter();
        exporter.export(users, response);
    }

}
