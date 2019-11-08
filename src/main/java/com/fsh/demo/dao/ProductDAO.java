package com.fsh.demo.dao;

import com.fsh.demo.models.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface ProductDAO extends CrudRepository<Product, Long> {
    public List<Product> findByNameLike(String name);
    // public List<Product> findByDateOfBirthGreaterThan(Date date);
}
