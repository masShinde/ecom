package com.shopme.ecom.controllers;

import com.shopme.ecom.dto.addressDtos.CreateAddressRequest;
import com.shopme.ecom.dto.addressDtos.UpdateAddressRequest;
import com.shopme.ecom.entities.AddressInfo;
import com.shopme.ecom.entities.SuccessResponse;
import com.shopme.ecom.services.AddressService;
import com.shopme.ecom.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private ResponseHandler responseHandler;

    @GetMapping("/address")
    public ResponseEntity<SuccessResponse> getAddressList(@RequestParam Integer userId){
        List<AddressInfo> addresses = addressService.getSavedAddress(userId);
        HashMap<String, List<AddressInfo>> res = new HashMap<>();
        res.put("address", addresses);
        return responseHandler.handleResponse(HttpStatus.OK, res, "Addresses fetched successfully.");
    }

    @PostMapping("/address")
    public ResponseEntity<SuccessResponse> addNewAddress(@RequestBody CreateAddressRequest addressInfo, @RequestParam Integer userId){
        AddressInfo saveNewAddress = addressService.saveNewAddress(addressInfo, userId);
        HashMap<String, AddressInfo> res = new HashMap<>();
        res.put("address", saveNewAddress);
        return responseHandler.handleResponse(HttpStatus.CREATED, res, "Address created successfully.");
    }

    @PutMapping("/address")
    public ResponseEntity<SuccessResponse> updateAddress(@RequestBody UpdateAddressRequest addressInfo, @RequestParam Integer userId){
        AddressInfo saveNewAddress = addressService.updateAddress(addressInfo, userId);
        HashMap<String, AddressInfo> res = new HashMap<>();
        res.put("address", saveNewAddress);
        return responseHandler.handleResponse(HttpStatus.OK, res, "Address updated successfully.");

    }

    @DeleteMapping("/address")
    public ResponseEntity<SuccessResponse> deleteAddressById( @RequestParam Integer addressId){
        addressService.deleteAddressById(addressId);
        return responseHandler.handleResponse(HttpStatus.OK, null, "Address Deleted successfully.");
    }

}
