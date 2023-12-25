package com.shopme.ecom.admin.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;


public class PasswordEncoderTest {

    @Test
    public void testEncodePassword(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pass = "nam2020";
        String encodedPassword = passwordEncoder.encode(pass);
        System.out.println(encodedPassword);
        boolean isMatch = passwordEncoder.matches(pass, encodedPassword);
        assertThat(isMatch).isTrue();
    }

}
