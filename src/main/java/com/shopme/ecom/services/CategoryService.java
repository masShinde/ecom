package com.shopme.ecom.services;

import com.shopme.ecom.admin.user.CategoryRepository;
import com.shopme.ecom.entities.Category;
import com.shopme.ecom.enums.SortDirections;
import com.shopme.ecom.exceptions.CategoryExceptions.CategoryInternalException;
import com.shopme.ecom.exceptions.CategoryExceptions.CategoryNotFoundException;
import com.shopme.ecom.exceptions.CategoryExceptions.CategoryUniqueException;
import com.shopme.ecom.exceptions.CommonExceptions.InvalidImageException;
import com.shopme.ecom.enums.DuplicateTypes;
import com.shopme.ecom.utils.FileUploadUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    public List<Category> listAllCategories( Integer pageNum, Integer pageSize, String sortBy, SortDirections sortDir){
        List<Category> categoryList = new ArrayList<>();
        Sort sort = Sort.by(sortBy);
        if(sortDir == SortDirections.DESC)
            sort = sort.descending();
        else
            sort = sort.ascending();
        Pageable page = PageRequest.of(pageNum -1, pageSize, sort);
        try {
            Iterable<Category> categories = categoryRepository.findAll(page);
            for(Category c : categories){
                categoryList.add(c);
            }
        }catch (Exception ex){
            throw new CategoryInternalException("Error while fetching the categories!");
        }
        return categoryList;
    }

    public Category createCategory(Category newCategory){
        DuplicateTypes result = checkIfUnique(newCategory.getId(), newCategory.getName(), newCategory.getAlias());
        if( result == DuplicateTypes.duplicateName)
            throw new CategoryUniqueException("Category Name must be unique!");
        if(result == DuplicateTypes.duplicateAlias)
            throw new CategoryUniqueException("Category Alias must be unique!");
        try {
            return categoryRepository.save(newCategory);
        }catch (Exception ex){
            throw new CategoryInternalException("Error while saving the category! Please try again.");
        }
    }

    public Category findCategoryById(int id){
        try {
            return categoryRepository.findById(id).get();
        }catch (Exception ex){
            throw new CategoryNotFoundException("Category not found!");
        }
    }

    public Category updateCategory(Category category){
        findCategoryById(category.getId());
        try{
            return categoryRepository.save(category);
        }catch (Exception ex){
            throw new CategoryInternalException("Error while updating category! Please try again.");
        }
    }

    public void saveCategoryImage(int id, MultipartFile  multipartFile){
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        String uploadDir = "category-photos/" + id ;
        if(!multipartFile.isEmpty()){
            try {
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
                Category category = findCategoryById(id);
                category.setImage(uploadDir+"/"+fileName);
                updateCategory(category);
            }catch (Exception ex){
                throw new CategoryInternalException("Error saving Image, please try again!");
            }
        }else{
            throw new InvalidImageException("Please provide valid image!");
        }
    }

    public void deleteById(int id){
        findCategoryById(id);
        try {
            categoryRepository.deleteById(id);
        }catch (Exception ex){
            throw new CategoryInternalException("Error while deleting the Category! Please try again.");
        }
    }

    public List<Category> searchCategory(Integer pageSize, Integer pageNum, String keyword){
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        List<Category> filteredCategories = categoryRepository.search(keyword, pageable).getContent();
        return  filteredCategories;
    }

    public DuplicateTypes checkIfUnique(Integer id, String name, String alias){
        boolean isNew = id == null || id == 0;
        Category c = categoryRepository.findByName(name);

        if(isNew){
            if(c != null)
                return DuplicateTypes.duplicateName;
            else{
                c = categoryRepository.findByAlias(alias);
                if(c != null)
                    return DuplicateTypes.duplicateAlias;
            }
        }else{
            if(c != null && c.getId() != id){
                return DuplicateTypes.duplicateName;
            }else{
                c = categoryRepository.findByAlias(alias);
                if( c!= null && c.getId() != id )
                    return DuplicateTypes.duplicateAlias;
            }
        }
        return DuplicateTypes.None;
    }

}
