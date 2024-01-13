package com.shopme.ecom.controllers;

import com.shopme.ecom.dto.categoryDtos.CreateCategoryRequest;
import com.shopme.ecom.dto.categoryDtos.UpdateCategoryRequest;
import com.shopme.ecom.entities.Category;
import com.shopme.ecom.entities.SuccessResponse;
import com.shopme.ecom.enums.SortDirections;
import com.shopme.ecom.services.CategoryService;
import com.shopme.ecom.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ResponseHandler responseHandler;

    @GetMapping("/categories")
    public ResponseEntity<SuccessResponse> getAllCategories(@RequestParam Optional<Integer> pageNum, @RequestParam Optional<Integer> pageSize, @RequestParam Optional<String> sortBy, @RequestParam Optional<SortDirections> sortDir){
        List<Category> categories = categoryService.listAllCategories(pageNum.orElse(1), pageSize.orElse(5), sortBy.orElse("id"), sortDir.orElse(SortDirections.ASC));
        HashMap<String, List<Category>> res = new HashMap<>();
        res.put("categories", categories);
        return responseHandler.handleResponse(HttpStatus.OK, res, "Categories Fetched Successfully.");

    }

    @GetMapping("/categories/search")
    public ResponseEntity<SuccessResponse> searchCategory(@RequestParam Optional<Integer> pageSize, @RequestParam Optional<Integer> pageNum, @RequestParam String keyword){
        List<Category> categories = categoryService.searchCategory(pageSize.orElse(5), pageNum.orElse(1), keyword);
        HashMap<String, List<Category>> res = new HashMap<>();
        res.put("categories", categories);
        return responseHandler.handleResponse(HttpStatus.OK, res, "Categories Fetched Successfully.");

    }

    @PostMapping("/categories")
    public ResponseEntity<SuccessResponse> createCategory(@RequestBody CreateCategoryRequest category) {
        if(category.getImage() == null)
            category.setImage("");
        Category savedCategory = categoryService.createCategory(category);
        HashMap<String, Category> res = new HashMap<>();
        res.put("category", savedCategory);
        return responseHandler.handleResponse(HttpStatus.CREATED, res, "Categories Created Successfully.");
    }

    @PostMapping("/categories/image")
    public ResponseEntity<SuccessResponse> uploadUserImage(@RequestParam int id, @RequestParam(name = "file") MultipartFile multipartFile) throws IOException {
        categoryService.saveCategoryImage(id, multipartFile);
        return responseHandler.handleResponse(HttpStatus.OK, null, "Category image updated Successfully.");
    }

    @PutMapping("/categories")
    public ResponseEntity<SuccessResponse> updateCategory(@RequestBody UpdateCategoryRequest category){
        Category updatedCategory = categoryService.updateCategory(category);
        HashMap<String, Category> res = new HashMap<>();
        res.put("category", updatedCategory);
        return responseHandler.handleResponse(HttpStatus.OK, res, "Categories Updated Successfully.");
    }

    @DeleteMapping("/categories")
    public ResponseEntity<SuccessResponse> deleteCategory(@RequestParam Integer id){
        categoryService.deleteById(id);
        return responseHandler.handleResponse(HttpStatus.CREATED, null, "Categories Deleted Successfully.");
    }



}


