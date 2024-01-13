package com.shopme.ecom.services;


import com.shopme.ecom.admin.user.BrandRepository;
import com.shopme.ecom.admin.user.CategoryRepository;
import com.shopme.ecom.admin.user.ProductRepository;
import com.shopme.ecom.dto.productDtos.CreateProductRequest;
import com.shopme.ecom.dto.productDtos.UpdateProductRequest;
import com.shopme.ecom.entities.Brand;
import com.shopme.ecom.entities.Category;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

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

    public Product createNewProduct(CreateProductRequest product){
        Product newProduct = new Product();
        Brand associatedBrand = brandService.getBrandById(product.getBrand());
        Category associatedCategory = categoryService.findCategoryById(product.getCategory());
        newProduct.setName(product.getName());
        newProduct.setAlias(product.getAlias());
        newProduct.setShortDescription(product.getShortDescription());
        newProduct.setFullDescription(product.getFullDescription());
        newProduct.setPhotos(product.getPhotos());
        newProduct.setBrand(associatedBrand);
        newProduct.setCost(product.getCost());
        newProduct.setEnabled(product.isEnabled());
        newProduct.setCategory(associatedCategory);
        newProduct.setDiscountPercent(product.getDiscountPercent());
        newProduct.setPrice(product.getPrice());
        newProduct.setInStock(product.isInStock());
        newProduct.setCreatedTime(new Date());
        newProduct.setUpdatedTime(new Date());
        checkIfUniqueProduct(newProduct);
        try{
            return productRepository.save(newProduct);
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

    public Product updateProduct(UpdateProductRequest product){
        Product requestedProduct = getProductById(product.getId());
        Category associatedCategory = categoryService.findCategoryById(product.getCategory());
        Brand associateBrand = brandService.getBrandById(product.getBrand());
        requestedProduct.setUpdatedTime(new Date());
        requestedProduct.setInStock(product.isInStock());
        requestedProduct.setDiscountPercent(product.getDiscountPercent());
        requestedProduct.setPrice(product.getPrice());
        requestedProduct.setCost(product.getCost());
        requestedProduct.setCategory(associatedCategory);
        requestedProduct.setBrand(associateBrand);
        requestedProduct.setName(product.getName());
        requestedProduct.setEnabled(product.isEnabled());
        requestedProduct.setFullDescription(product.getFullDescription());
        requestedProduct.setShortDescription(product.getShortDescription());
        requestedProduct.setAlias(product.getAlias());
        checkIfUniqueProduct(requestedProduct);
        try{
            return productRepository.save(requestedProduct);
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
            productRepository.save(product);
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
