package com.shopme.ecom.controllers;


import com.shopme.ecom.dto.productDtos.CreateProductRequest;
import com.shopme.ecom.dto.productDtos.UpdateProductRequest;
import com.shopme.ecom.entities.Product;
import com.shopme.ecom.entities.SuccessResponse;
import com.shopme.ecom.enums.SortDirections;
import com.shopme.ecom.services.ProductService;
import com.shopme.ecom.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ResponseHandler responseHandler;

    @GetMapping("/products")
    public ResponseEntity<SuccessResponse> getAllProducts(
            @RequestParam Optional<Integer> pageSize,
            @RequestParam Optional<Integer> pageNum,
            @RequestParam Optional<String> sortBy,
            @RequestParam Optional<SortDirections> sortDir){

        List<Product> productList = productService.getAllProducts( pageSize, pageNum, sortBy, sortDir);
        HashMap<String, List<Product>> res = new HashMap<>();
        res.put("products", productList);
        return responseHandler.handleResponse(HttpStatus.OK, res, "Products Fetched Successfully!");
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<SuccessResponse> getProductById(@PathVariable Integer id){
        Product product = productService.getProductById(id);
        HashMap<String, Product> res = new HashMap<>();
        res.put("product", product);
        return responseHandler.handleResponse(HttpStatus.OK, res, "Product Fetched Successfully!");
    }

    @PostMapping("/products")
    public ResponseEntity<SuccessResponse> createNewProduct(@RequestBody CreateProductRequest product){
        Product savedProduct = productService.createNewProduct(product);
        HashMap<String, Product> res = new HashMap<>();
        res.put("product", savedProduct);
        return responseHandler.handleResponse(HttpStatus.CREATED, res, "Products Created Successfully!");
    }

    @DeleteMapping("/products")
    public ResponseEntity<SuccessResponse> deleteProductById(@RequestParam Integer id){
        productService.deleteProductById(id);
        return responseHandler.handleResponse(HttpStatus.OK, null, "Products Deleted Successfully!");
    }

    @PostMapping("/products/image")
    public ResponseEntity<SuccessResponse> updateProductImage(@RequestParam(name = "file") MultipartFile multipartFile, @RequestParam Integer id){
        productService.updateImage(multipartFile, id);
        return responseHandler.handleResponse(HttpStatus.OK, null, "Products Image Updated Successfully!");
    }

    @GetMapping("/products/search")
    public ResponseEntity<SuccessResponse> searchProduct(
            @RequestParam String keyword,
            @RequestParam Optional<Integer> pageSize,
            @RequestParam Optional<Integer> pageNum,
            @RequestParam Optional<String> sortBy,
            @RequestParam Optional<SortDirections> sortDir){
        List<Product> productList = productService.searchProduct(keyword, pageSize, pageNum, sortBy, sortDir);
        HashMap<String, List<Product>> res = new HashMap<>();
        res.put("product", productList);
        return responseHandler.handleResponse(HttpStatus.OK, res, "Products Fetched Successfully!");
    }

    @GetMapping("/products/search/{categoryId}")
    public ResponseEntity<SuccessResponse> getProductsByCategoryId(
            @PathVariable Integer categoryId,
            @RequestParam Optional<Integer> pageSize,
            @RequestParam Optional<Integer> pageNum
    ){
        List<Product> productList = productService.getProductsByCategoryId( categoryId, pageSize, pageNum);
        HashMap<String, List<Product>> res = new HashMap<>();
        res.put("products", productList);
        return responseHandler.handleResponse(HttpStatus.OK, res, "Products Fetched Successfully!");

    }

    @PutMapping("/products")
    public ResponseEntity<SuccessResponse> updateProduct(@RequestBody UpdateProductRequest product){
        Product updatedProduct = productService.updateProduct(product);
        HashMap<String, Product> res = new HashMap<>();
        res.put("product", updatedProduct);
        return responseHandler.handleResponse(HttpStatus.OK, res, "Products Updated Successfully!");
    }
}
