package com.shopme.ecom.services;

import com.shopme.ecom.admin.user.AddressRepository;
import com.shopme.ecom.dto.addressDtos.CreateAddressRequest;
import com.shopme.ecom.dto.addressDtos.UpdateAddressRequest;
import com.shopme.ecom.entities.AddressInfo;
import com.shopme.ecom.entities.User;
import com.shopme.ecom.exceptions.Brand.BrandInternalErrorException;
import org.apache.commons.math3.analysis.function.Add;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserService userService;


    public AddressInfo saveNewAddress(CreateAddressRequest addressInfo, Integer userId){
        User associatedUser = userService.findUserById(userId);
        try{
            AddressInfo newAddress = new AddressInfo();
            newAddress.setAddressLine(addressInfo.getAddressLine());
            newAddress.setCity(addressInfo.getCity());
            newAddress.setCountry(addressInfo.getCountry());
            newAddress.setFirstName(addressInfo.getFirstName());
            newAddress.setLastName(addressInfo.getLastName());
            newAddress.setPostalCode(addressInfo.getPostalCode());
            newAddress.setPhoneNumber(addressInfo.getPhoneNumber());
            newAddress.setUser(associatedUser);
            newAddress.setState(addressInfo.getState());
            newAddress.setDefault(addressInfo.isDefault());
            return addressRepository.save(newAddress);
        }catch (Exception ex){
            throw new BrandInternalErrorException("Error while Saving Address! Please try again.");
        }
    }


    public List<AddressInfo> getSavedAddress(Integer userId){
        try {
           return addressRepository.findByUser(userId);
        }catch (Exception ex){
            throw new BrandInternalErrorException("Error while Fetching Address! Please try again.");
        }
    }


    public AddressInfo updateAddress(UpdateAddressRequest addressInfo, Integer userId){
        User associatedUser = userService.findUserById(userId);
        try {
            AddressInfo newAddressInfo = new AddressInfo();
            newAddressInfo.setDefault(addressInfo.isDefault());
            newAddressInfo.setId(addressInfo.getId());
            newAddressInfo.setState(addressInfo.getState());
            newAddressInfo.setFirstName(addressInfo.getFirstName());
            newAddressInfo.setLastName(addressInfo.getLastName());
            newAddressInfo.setUser(associatedUser);
            newAddressInfo.setCity(addressInfo.getCity());
            newAddressInfo.setPhoneNumber(addressInfo.getPhoneNumber());
            newAddressInfo.setCountry(addressInfo.getCountry());
            newAddressInfo.setAddressLine(addressInfo.getAddressLine());
            newAddressInfo.setPostalCode(addressInfo.getPostalCode());
           return addressRepository.save(newAddressInfo);
        }catch (Exception ex){
            throw new BrandInternalErrorException("Error while updating address! Please try again.");
        }
    }

    public void deleteAddressById(Integer addressId){
        try {
            addressRepository.deleteById(addressId);
        }catch (Exception ex){
            throw new BrandInternalErrorException("Error while deleting address! Please try again.");
        }
    }
}
