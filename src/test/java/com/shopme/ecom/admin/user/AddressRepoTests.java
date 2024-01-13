package com.shopme.ecom.admin.user;

import com.shopme.ecom.entities.AddressInfo;
import com.shopme.ecom.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class AddressRepoTests {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void testCreateAddress(){
        User u = new  User(1);
        AddressInfo ad = new AddressInfo("f1", "l1", "123", "a1", "p1", u);
        AddressInfo savedAddress = addressRepository.save(ad);
        assertThat(savedAddress.getId()).isGreaterThan(0);
    }


    @Test
    public void testCreateMultipleAddress(){
        User u1 = new  User(4);
        User u2 = new  User(6);
        AddressInfo ad1 = new AddressInfo("f2", "l2", "123", "a2", "p2", u1);
        AddressInfo ad2 = new AddressInfo("f3", "l3", "123", "a3", "p3", u2);
        List<AddressInfo> savedAddress = (List<AddressInfo>) addressRepository.saveAll(List.of(ad1, ad2));
        assertThat(savedAddress.size()).isEqualTo(2);
    }

    @Test
    public void testFindAddressById(){
        int id = 3;
        AddressInfo savedAddress =  addressRepository.findById(id).get();
        assertThat(savedAddress.getId()).isEqualTo(id);
    }

    @Test
    public void testFindAddressByUser(){
        int id = 6;
        List<AddressInfo> savedAddress =  addressRepository.findByUser(id);
        assertThat(savedAddress.size()).isGreaterThan(0);
    }

    @Test
    public void testUpdateAddressInfo(){
        int id = 1;
        String city = "c1";
        AddressInfo ad = addressRepository.findById(id).get();
        ad.setCity(city);
        AddressInfo savedAddress = addressRepository.save(ad);
        assertThat(savedAddress.getId()).isEqualTo(id);
        assertThat(savedAddress.getCity()).isEqualTo(city);
    }

    @Test
    public void testDeleteAddressById(){
        int id = 1;
        addressRepository.deleteById(id);
        Optional<AddressInfo> ad = addressRepository.findById(id);
        assertThat(ad.isEmpty()).isTrue();

    }
}
