package com.shopme.ecom.Controllers;


import com.shopme.ecom.entities.CommonResponse;
import com.shopme.ecom.entities.Product;
import com.shopme.ecom.enums.ResponseType;
import com.shopme.ecom.enums.SortDirections;
import com.shopme.ecom.services.ProductService;
import com.shopme.ecom.utils.CommonUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CommonUtilities commonUtilities;

    @GetMapping("/products")
    public ResponseEntity<CommonResponse> getAllProducts(
            @RequestParam Optional<Integer> pageSize,
            @RequestParam Optional<Integer> pageNum,
            @RequestParam Optional<String> sortBy,
            @RequestParam Optional<SortDirections> sortDir){

        List<Product> productList = productService.getAllProducts( pageSize, pageNum, sortBy, sortDir);
        HashMap<String, List<Product>> res = new HashMap<>();
        res.put("products", productList);
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.OK.value(), "Products Fetched Successfully!", ResponseType.Sucess,  res );
        return new ResponseEntity<>(cr, commonUtilities.getCommonHeaders(), HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<CommonResponse> getProductById(@PathVariable Integer id){
        Product product = productService.getProductById(id);
        HashMap<String, Product> res = new HashMap<>();
        res.put("product", product);
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.OK.value(), "Product Fetched Successfully!", ResponseType.Sucess, res);
        return  new ResponseEntity<>(cr, commonUtilities.getCommonHeaders(), HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<CommonResponse> createNewProduct(@RequestBody Product product){
        Product savedProduct = productService.createNewProduct(product);
        HashMap<String, Product> res = new HashMap<>();
        res.put("product", product);
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.CREATED.value(), "Product Created Successfully!", ResponseType.Sucess, res);
        return new ResponseEntity<>(cr, commonUtilities.getCommonHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping("/products")
    public ResponseEntity<CommonResponse> deleteProductById(@RequestParam Integer id){
        productService.deleteProductById(id);
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.OK.value(), "Product Deleted Successfully!", ResponseType.Sucess);
        return new ResponseEntity<>(cr, commonUtilities.getCommonHeaders(), HttpStatus.OK);
    }

    @PostMapping("/products/image")
    public ResponseEntity<CommonResponse> updateProductImage(@RequestParam(name = "file") MultipartFile multipartFile, @RequestParam Integer id){
        productService.updateImage(multipartFile, id);
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.OK.value(), "Product Image Updated Successfully!", ResponseType.Sucess);
        return new ResponseEntity<>(cr, commonUtilities.getCommonHeaders(), HttpStatus.OK);
    }

    @GetMapping("/products/search")
    public ResponseEntity<CommonResponse> searchProduct(
            @RequestParam String keyword,
            @RequestParam Optional<Integer> pageSize,
            @RequestParam Optional<Integer> pageNum,
            @RequestParam Optional<String> sortBy,
            @RequestParam Optional<SortDirections> sortDir){
        List<Product> productList = productService.searchProduct(keyword, pageSize, pageNum, sortBy, sortDir);
        HashMap<String, List<Product>> res = new HashMap<>();
        res.put("product", productList);
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.OK.value(), "Product Updated Successfully!", ResponseType.Sucess, res);
        return new ResponseEntity<>(cr, commonUtilities.getCommonHeaders(), HttpStatus.OK);
    }

    @GetMapping("/products/search/{categoryId}")
    public ResponseEntity<CommonResponse> getProductsByCategoryId(
            @PathVariable Integer categoryId,
            @RequestParam Optional<Integer> pageSize,
            @RequestParam Optional<Integer> pageNum
    ){
        List<Product> productList = productService.getProductsByCategoryId( categoryId, pageSize, pageNum);
        HashMap<String, List<Product>> res = new HashMap<>();
        res.put("products", productList);
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.OK.value(), "Products Fetched Successfully!", ResponseType.Sucess, res);
        return new ResponseEntity<>(cr, commonUtilities.getCommonHeaders(), HttpStatus.OK);

    }

    @PutMapping("/products")
    public ResponseEntity<CommonResponse> updateProduct(@RequestBody Product product){
        Product updatedProduct = productService.updateProduct(product);
        HashMap<String, Product> res = new HashMap<>();
        res.put("product", product);
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.OK.value(), "Product Updated Successfully!", ResponseType.Sucess, res);
        return new ResponseEntity<>(cr, commonUtilities.getCommonHeaders(), HttpStatus.OK);
    }
}
