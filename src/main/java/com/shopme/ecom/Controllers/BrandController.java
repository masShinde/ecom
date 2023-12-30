package com.shopme.ecom.Controllers;

import com.shopme.ecom.entities.Brand;
import com.shopme.ecom.entities.CommonResponse;
import com.shopme.ecom.enums.ResponseType;
import com.shopme.ecom.services.BrandService;
import com.shopme.ecom.utils.CommonUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/api")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private CommonUtilities commonUtilities;

    @GetMapping("/brands")
    public ResponseEntity<CommonResponse> getAllBrands(){
        List<Brand> brands = brandService.getAllBrands();
        HashMap<String, List<Brand>> res = new HashMap<>();
        res.put("brands", brands);
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.OK.value(), "Brands Fetched Successfully.", ResponseType.Sucess, res );
        return new ResponseEntity<>(cr,  commonUtilities.getCommonHeaders(), HttpStatus.OK);
    }


    @GetMapping("/brands/id")
    public ResponseEntity<CommonResponse> getBrandById(@RequestParam Integer id){
        Brand brand = brandService.getBrandById(id);
        HashMap<String, Brand> res = new HashMap<>();
        res.put("brand", brand);
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.OK.value(), "Brand Fetched Successfully.", ResponseType.Sucess, res );
        return new ResponseEntity<>(cr,  commonUtilities.getCommonHeaders(), HttpStatus.OK);
    }


    @PostMapping("/brands")
    public ResponseEntity<CommonResponse> createNewBrand(@RequestBody Brand newBrand){
        Brand brand = brandService.createNewBrand(newBrand);
        HashMap<String, Brand> res = new HashMap<>();
        res.put("brand", brand);
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.OK.value(), "Brand Fetched Successfully.", ResponseType.Sucess, res );
        return new ResponseEntity<>(cr,  commonUtilities.getCommonHeaders(), HttpStatus.OK);
    }

    @PutMapping("/brands")
    public ResponseEntity<CommonResponse> updateBrand(@RequestBody Brand newBrand){
        Brand brand = brandService.updateBrand(newBrand);
        HashMap<String, Brand> res = new HashMap<>();
        res.put("brand", brand);
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.OK.value(), "Brand Fetched Successfully.", ResponseType.Sucess, res );
        return new ResponseEntity<>(cr,  commonUtilities.getCommonHeaders(), HttpStatus.OK);
    }

    @PostMapping("/brands/logo")
    public ResponseEntity<CommonResponse> saveBrandLogo(@RequestParam(name = "file") MultipartFile multipartFile, @RequestParam Integer id){
        brandService.saveBrandLogo(multipartFile, id);
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.OK.value(), "Brand logo updated Successfully.", ResponseType.Sucess);
        return new ResponseEntity<>(cr, commonUtilities.getCommonHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/brands")
    public ResponseEntity<CommonResponse> deleteBrand(@RequestParam Integer id){
        brandService.deleteBrand(id);
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.OK.value(), "Brand Deleted Successfully.", ResponseType.Sucess );
        return new ResponseEntity<>(cr,  commonUtilities.getCommonHeaders(), HttpStatus.OK);
    }
}
