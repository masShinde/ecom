package com.shopme.ecom.controllers;

import com.shopme.ecom.dto.brandDtos.CreateBrandRequest;
import com.shopme.ecom.dto.brandDtos.UpdateBrandRequest;
import com.shopme.ecom.entities.Brand;
import com.shopme.ecom.entities.SuccessResponse;
import com.shopme.ecom.services.BrandService;
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
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private ResponseHandler responseHandler;

    @GetMapping("/brands")
    public ResponseEntity<SuccessResponse> getAllBrands(@RequestParam Optional<Integer> pageNum, @RequestParam Optional<Integer> pageSize){
        List<Brand> brands = brandService.getAllBrands(pageNum, pageSize);
        HashMap<String, List<Brand>> res = new HashMap<>();
        res.put("brands", brands);
        return responseHandler.handleResponse(HttpStatus.OK, res, "Brands Fetched Successfully.");
    }


    @GetMapping("/brands/{id}")
    public ResponseEntity<SuccessResponse> getBrandById(@PathVariable Integer id){
        Brand brand = brandService.getBrandById(id);
        HashMap<String, Brand> res = new HashMap<>();
        res.put("brand", brand);
        return responseHandler.handleResponse(HttpStatus.OK, res, "Brand Fetched Successfully.");
    }


    @PostMapping("/brands")
    public ResponseEntity<SuccessResponse> createNewBrand(@RequestBody CreateBrandRequest newBrand){
        Brand brand = brandService.createNewBrand(newBrand);
        HashMap<String, Brand> res = new HashMap<>();
        res.put("brand", brand);
        return responseHandler.handleResponse(HttpStatus.CREATED, res, "Brand Created Successfully.");
    }

    @PutMapping("/brands")
    public ResponseEntity<SuccessResponse> updateBrand(@RequestBody UpdateBrandRequest newBrand){
        Brand brand = brandService.updateBrand(newBrand);
        HashMap<String, Brand> res = new HashMap<>();
        res.put("brand", brand);
        return responseHandler.handleResponse(HttpStatus.OK, res, "Brand Updated successfully.");
    }

    @PostMapping("/brands/logo")
    public ResponseEntity<SuccessResponse> saveBrandLogo(@RequestParam(name = "file") MultipartFile multipartFile, @RequestParam Integer id){
        brandService.saveBrandLogo(multipartFile, id);
        return responseHandler.handleResponse(HttpStatus.OK, null, "Brand logo updated Successfully.");
    }

    @DeleteMapping("/brands")
    public ResponseEntity<SuccessResponse> deleteBrand(@RequestParam Integer id){
        brandService.deleteBrand(id);
        return responseHandler.handleResponse(HttpStatus.OK, null, "Brand Deleted Successfully.");
    }
}
