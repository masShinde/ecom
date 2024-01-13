package com.shopme.ecom.services;

import com.shopme.ecom.admin.user.BrandRepository;
import com.shopme.ecom.dto.brandDtos.IBrandRequest;
import com.shopme.ecom.dto.brandDtos.CreateBrandRequest;
import com.shopme.ecom.dto.brandDtos.UpdateBrandRequest;
import com.shopme.ecom.entities.Brand;
import com.shopme.ecom.entities.Category;
import com.shopme.ecom.exceptions.Brand.BrandBadRequestException;
import com.shopme.ecom.exceptions.Brand.BrandInternalErrorException;
import com.shopme.ecom.exceptions.Brand.BrandNotFoundException;
import com.shopme.ecom.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryService categoryService;

    private final Integer pageNum = 1;
    private final Integer pageSize = 5;


    public List<Brand> getAllBrands(Optional<Integer> pageNum, Optional<Integer> pageSize){
        try{
            Pageable pageable = PageRequest.of(pageNum.orElse(this.pageNum - 1), pageSize.orElse(this.pageSize));
            return (List<Brand>) brandRepository.findAll();
        }catch (Exception ex){
            throw new BrandInternalErrorException("Error while fetching brands! Please try again.");
        }
    }

    public Brand getBrandById(Integer id){
        try{
            Optional<Brand> brand = brandRepository.findById(id);
            if(brand.isEmpty())
                throw new BrandNotFoundException("Brand not found!");
            return brand.get();
        }catch (Exception ex){
            if(ex.getLocalizedMessage() == null)
                throw new BrandInternalErrorException("Error while fetching brands! Please try again.");
            throw new BrandNotFoundException(ex.getLocalizedMessage());
        }
    }

    public Brand createNewBrand(CreateBrandRequest newBrand){

        if(newBrand.getName() == null || newBrand.getLogo() == null)
            throw new BrandBadRequestException("Brand should have a name and logo!");

        try {
            Brand brand = new Brand();
            brand.setName(newBrand.getName());
            brand.setCategories(getAssociatedCategories(newBrand));
            brand.setLogo(newBrand.getLogo());
            return brandRepository.save(brand);
        }catch (Exception ex){
            throw new BrandInternalErrorException("Error while creating new Brand! Please try again.");
        }
    }

    public void saveBrandLogo(MultipartFile multipartFile, Integer id){
        String fileName = multipartFile.getOriginalFilename();
        String uploadDir = "brand-logos/" + id ;
        if(multipartFile.isEmpty())
            throw new BrandBadRequestException("Please provide valid image for Brand logo!");
        Brand brand = getBrandById(id);
        try{
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            brand.setLogo(uploadDir+"/"+fileName);
            brandRepository.save(brand);
        }catch (Exception ex){
            throw new BrandInternalErrorException("Error while updating logo! Please try again.");
        }
    }

    public Brand updateBrand(UpdateBrandRequest brand){
        if(brand.getName() == null || brand.getLogo() == null || brand.getId() == null)
            throw new BrandBadRequestException("Brand should have a name, id and logo!");
        try {
            Brand newBrand = new Brand();
            newBrand.setName(brand.getName());
            newBrand.setLogo(brand.getLogo());
            newBrand.setCategories(getAssociatedCategories(brand));
            newBrand.setId(brand.getId());
            return brandRepository.save(newBrand);

        }catch (Exception ex){
            throw new BrandInternalErrorException("Error while creating new Brand! Please try again.");
        }
    }



    public void deleteBrand(Integer id){
        if(id < 1)
            throw new BrandBadRequestException("Please provide valid Id.");
        try {
             brandRepository.deleteById(id);
        }catch (Exception ex){
            throw new BrandInternalErrorException("Error while creating new Brand! Please try again.");
        }
    }


    private Set<Category> getAssociatedCategories(IBrandRequest brand){
        Set<Category> associatedCategories = new HashSet<>();
        for (Integer i: brand.getCategories()){
            associatedCategories.add(categoryService.findCategoryById(i));
        }
        return associatedCategories;
    }

}
