package com.shopme.ecom.Controllers;

import com.shopme.ecom.entities.CommonResponse;
import com.shopme.ecom.entities.User;
import com.shopme.ecom.services.UserService;
import com.shopme.ecom.utils.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
    private CommonUtilities commonUtilities;

    @GetMapping("/users")
    public ResponseEntity<CommonResponse> getAllUsers(){
        List<User> users = userService.listAllUsers();
        HashMap<String, List<User>> res = new HashMap<>();
        res.put("users", users);
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.OK.value(), "Users fetched Successfully", ResponseType.Sucess, res);
        return new ResponseEntity<>(cr, commonUtilities.getCommonHeaders(), HttpStatus.OK);
    }

    @GetMapping("/users/pages")
    public ResponseEntity<CommonResponse> getUsersByPage(@RequestParam int pageNum, @RequestParam int pageSize, @RequestParam String sortField, @RequestParam String sortDir , @RequestParam String keyword){
        Page<User> page = userService.listByPage(pageNum, pageSize, sortField, sortDir, keyword);
        HashMap<String, Page<User>> res = new HashMap<>();
        res.put("users", page);
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.OK.value(), "Users fetched Successfully", ResponseType.Sucess, res);
        return new ResponseEntity<>(cr, commonUtilities.getCommonHeaders(), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<CommonResponse> createNewUser(@RequestBody User user){
        User savedUser = userService.saveUser(user);
        HashMap<String, User> res = new HashMap<>();
        res.put("user", savedUser);
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.CREATED.value(), "User Registered Successfully", ResponseType.Sucess, res);
        return new ResponseEntity<>(cr, commonUtilities.getCommonHeaders(), HttpStatus.CREATED);
    }

    @PostMapping("/users/profile")
    public ResponseEntity<CommonResponse> uploadUserImage(@RequestParam int id, @RequestParam(name = "file") MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String uploadDir = "user-photos/" + id ;
        if(!multipartFile.isEmpty()){
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            User user = userService.findUserById(id);
            user.setPhotos(uploadDir+"/"+fileName);
            updateUser(user);
        }
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.OK.value(), "Profile Image Updated Successfully.", ResponseType.Sucess);
        return new ResponseEntity<>(cr, commonUtilities.getCommonHeaders(), HttpStatus.OK);
    }

    @PutMapping("/users")
    public ResponseEntity<CommonResponse> updateUser( @RequestBody User user){
        User updatedUser = userService.updateUser(user);
        HashMap<String, User> res = new HashMap<>();
        res.put("user", updatedUser);
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.OK.value(), "User updated successfully.", ResponseType.Sucess, res );
        return new ResponseEntity<>(cr,  commonUtilities.getCommonHeaders(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<CommonResponse> getUserById(@PathVariable int id){
        User updatedUser = userService.findUserById(id);
        HashMap<String, User> res = new HashMap<>();
        res.put("user", updatedUser);
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.OK.value(), "User fetched successfully.", ResponseType.Sucess, res );
        return new ResponseEntity<>(cr,  commonUtilities.getCommonHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/users")
    public ResponseEntity<CommonResponse> deleteUserById(@RequestParam int id){
        userService.deleteUserById(id);
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.OK.value(), "User Deleted Successfully.", ResponseType.Sucess );
        return new ResponseEntity<>(cr,  commonUtilities.getCommonHeaders(), HttpStatus.OK);
    }

    @PutMapping("/users/enable")
    public ResponseEntity<CommonResponse> setUserEnableStatus(@RequestParam int id, @RequestBody HashMap<String, Boolean> reqBody){
        userService.updateEnableStatus(id, (Boolean) reqBody.getOrDefault("isEnable", false));
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.OK.value(), "User Updated Successfully.", ResponseType.Sucess );
        return new ResponseEntity<>(cr,  commonUtilities.getCommonHeaders(), HttpStatus.OK);
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
