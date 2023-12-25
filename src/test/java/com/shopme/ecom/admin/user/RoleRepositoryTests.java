package com.shopme.ecom.admin.user;

import com.shopme.ecom.entities.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(value = false)
public class RoleRepositoryTests {

    @Autowired
    private RoleRepository repo;

    @Test
    public void testCreateFirstRole(){
        Role adminRole = new Role("admin", "admin Description");
        Role savedRole = repo.save(adminRole);
        assertThat(savedRole.getId()).isGreaterThan(0);
    }

    @Test
    public void createTestRoles(){
        Role salesPerson = new Role("salesPerson", "sales person description");
        Role editor = new Role("editor", "editor description");
        Role shipper = new Role("shipper", "shipper description");
        Role assistant = new Role("assistant", "assistant description");

        repo.saveAll(List.of(salesPerson, editor, shipper, assistant));
    }
}
