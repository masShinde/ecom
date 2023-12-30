package com.shopme.ecom.Controllers;

import com.shopme.ecom.entities.Category;
import com.shopme.ecom.entities.CommonResponse;
import com.shopme.ecom.enums.SortDirections;
import com.shopme.ecom.services.CategoryService;
import com.shopme.ecom.utils.CommonUtilities;
import com.shopme.ecom.enums.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CommonUtilities commonUtilities;

    @GetMapping("/categories")
    public ResponseEntity<CommonResponse> getAllCategories(@RequestParam Optional<Integer> pageNum, @RequestParam Optional<Integer> pageSize, @RequestParam Optional<String> sortBy, @RequestParam Optional<SortDirections> sortDir){
        List<Category> categories = categoryService.listAllCategories(pageNum.orElse(1), pageSize.orElse(5), sortBy.orElse("id"), sortDir.orElse(SortDirections.ASC));
        HashMap<String, List<Category>> res = new HashMap<>();
        res.put("categories", categories);
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.OK.value(), "Categories Fetched Successfully.", ResponseType.Sucess, res );
        return new ResponseEntity<>(cr,  commonUtilities.getCommonHeaders(), HttpStatus.OK);
    }

    @GetMapping("/categories/search")
    public ResponseEntity<CommonResponse> searchCategory(@RequestParam Optional<Integer> pageSize, @RequestParam Optional<Integer> pageNum, @RequestParam String keyword){
        List<Category> categories = categoryService.searchCategory(pageSize.orElse(5), pageNum.orElse(1), keyword);
        HashMap<String, List<Category>> res = new HashMap<>();
        res.put("categories", categories);
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.OK.value(), "Categories Fetched Successfully.", ResponseType.Sucess, res );
        return new ResponseEntity<>(cr,  commonUtilities.getCommonHeaders(), HttpStatus.OK);

    }

    @PostMapping("/categories")
    public ResponseEntity<CommonResponse> createCategory(@RequestBody Category category) {
        if(category.getImage() == null)
            category.setImage("");
        Category savedCategory = categoryService.createCategory(category);
        HashMap<String, Category> res = new HashMap<>();
        res.put("category", savedCategory);
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.CREATED.value(), "Categories Fetched Successfully.", ResponseType.Sucess, res );
        return new ResponseEntity<>(cr,  commonUtilities.getCommonHeaders(), HttpStatus.CREATED);
    }

    @PostMapping("/categories/image")
    public ResponseEntity<CommonResponse> uploadUserImage(@RequestParam int id, @RequestParam(name = "file") MultipartFile multipartFile) throws IOException {
        categoryService.saveCategoryImage(id, multipartFile);
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.OK.value(), "Category image updated Successfully.", ResponseType.Sucess);
        return new ResponseEntity<>(cr, commonUtilities.getCommonHeaders(), HttpStatus.OK);
    }

    @PutMapping("/categories")
    public ResponseEntity<CommonResponse> updateCategory(@RequestBody Category category){
        Category updatedCategory = categoryService.updateCategory(category);
        HashMap<String, Category> res = new HashMap<>();
        res.put("category", updatedCategory);
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.OK.value(), "Categories Fetched Successfully.", ResponseType.Sucess, res );
        return new ResponseEntity<>(cr,  commonUtilities.getCommonHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/categories")
    public ResponseEntity<CommonResponse> deleteCategory(@RequestParam Integer id){
        categoryService.deleteById(id);
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.OK.value(), "Category Deleted Successfully.", ResponseType.Sucess );
        return new ResponseEntity<>(cr,  commonUtilities.getCommonHeaders(), HttpStatus.OK);
    }



}


