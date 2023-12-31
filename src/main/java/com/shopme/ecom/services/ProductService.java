package com.shopme.ecom.services;


import com.shopme.ecom.admin.user.ProductRepository;
import com.shopme.ecom.entities.Product;
import com.shopme.ecom.enums.SortDirections;
import com.shopme.ecom.exceptions.CategoryExceptions.CategoryInternalException;
import com.shopme.ecom.exceptions.Products.ProductAlreadyExistsException;
import com.shopme.ecom.exceptions.Products.ProductBadRequestException;
import com.shopme.ecom.exceptions.Products.ProductInternalErrorException;
import com.shopme.ecom.exceptions.Products.ProductNotFoundException;
import com.shopme.ecom.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private Integer pageNum = 1;
    private Integer pageSize = 7;

    public List<Product> getAllProducts(Optional<Integer> pageSize,
                                        Optional<Integer> pageNum,
                                        Optional<String> sortBy,
                                        Optional<SortDirections> sortDir){
        Sort sort = Sort.by(sortBy.orElse("id"));
        if(sortDir.orElse(SortDirections.ASC) == SortDirections.DESC)
            sort = sort.descending();
        Pageable page = PageRequest.of(pageNum.orElse(this.pageNum) -1 , pageSize.orElse(this.pageSize), sort);
        List<Product> productList = new ArrayList<>();
        try {
            Iterable<Product> products = productRepository.findAll(page);
            for(Product p : products){
                productList.add(p);
            }
        }catch (Exception ex){
            throw new CategoryInternalException("Error while fetching the categories!");
        }
        return productList;
    }

    public List<Product> getProductsByCategoryId(Integer id, Optional<Integer> pageSize, Optional<Integer> pageNum){
        Pageable page = PageRequest.of(pageNum.orElse(1)-1, pageSize.orElse(this.pageSize));
        try {
            return productRepository.getProductByCategory(id, page).getContent();
        }catch (Exception ex){
            throw new ProductInternalErrorException("Error while fetching Products! Please try again.");
        }
    }

    public Product getProductById(Integer id){
        try {
            Optional<Product> product = productRepository.findById(id);
            if(product.isEmpty())
                throw new ProductNotFoundException("Product Not found!");
            return product.get();
        }catch (Exception ex){
            if(ex.getLocalizedMessage() == null)
                throw new ProductInternalErrorException("Error while fetching Product! Please try again.");
            throw new ProductNotFoundException(ex.getLocalizedMessage());
        }
    }

    public Product createNewProduct(Product product){
        checkIfUniqueProduct(product);
        try{
            return productRepository.save(product);
        }catch(Exception ex){
            throw new ProductInternalErrorException("Error while saving Product! Please try again.");
        }
    }

    public void deleteProductById(Integer id){
        try{
            productRepository.deleteById(id);
        }catch (Exception ex){
            throw new ProductInternalErrorException("Error while deleting Product! Please try again.");
        }
    }

    public Product updateProduct(Product product){
        checkIfUniqueProduct(product);
        try{
            return productRepository.save(product);
        }catch (Exception ex){
            throw new ProductInternalErrorException("Error while Updating Product! Please try again.");
        }
    }

    public void updateImage(MultipartFile multipartFile, Integer id){
        if(multipartFile.isEmpty())
            throw new ProductBadRequestException("Please provide valid image!");
        String fileName = multipartFile.getOriginalFilename();
        String uploadDir = "products-photos/" + id ;
        Product product = getProductById(id);
        try {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            product.setPhotos(uploadDir+"/"+fileName);
            updateProduct(product);
        }catch (Exception ex){
            throw new ProductInternalErrorException("Error while Updating Product Image! Please try again.");
        }
    }

    public List<Product> searchProduct(String keyword,
                                       Optional<Integer> pageSize,
                                       Optional<Integer> pageNum,
                                       Optional<String> sortBy,
                                       Optional<SortDirections> sortDir){
        Sort sort = Sort.by(sortBy.orElse("id"));
        if(sortDir.orElse(SortDirections.ASC) == SortDirections.DESC)
            sort = sort.descending();
        Pageable page = PageRequest.of(pageNum.orElse(this.pageNum) - 1 , pageSize.orElse(this.pageSize), sort);
        List<Product> productList = new ArrayList<>();
        try {
            Iterable<Product> products = productRepository.searchProduct(keyword, page);
            for(Product p : products){
                productList.add(p);
            }
        }catch (Exception ex){
            throw new CategoryInternalException("Error while fetching the categories!");
        }
        return productList;
    }

    private void checkIfUniqueProduct(Product product){
        if(product.getId() != null){
            List<Product> searchedProducts = productRepository.searchUnique(product.getName(), product.getAlias(), product.getId());
            if(!searchedProducts.isEmpty())
                throw new ProductAlreadyExistsException("Product with given name or alias already exists!");
        }else{
            List<Product> searchedProducts = productRepository.searchUniqueProduct(product.getName(), product.getAlias());
            if(!searchedProducts.isEmpty())
                throw new ProductAlreadyExistsException("Product with given name or alias already exists!");
        }
    }

}
