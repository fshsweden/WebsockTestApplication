package com.fsh.demo.controllers;

import com.fsh.demo.dao.ProductDAO;
import com.fsh.demo.models.Order;
import com.fsh.demo.models.Product;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductsController
{
    Gson gson = new Gson();


    @Autowired
    ProductDAO productDAO;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path="/ownorders")
    public String getOwnOrders() {

        Order o1 = new Order("AAPL", 1, 10.0);
        Order o2 = new Order("AAPL", 3, 11.0);
        Order o3 = new Order("MSFT", 10, 0.08);
        Order o4 = new Order("AMZN", 15, 15.50);

        ArrayList<Order> ol = new ArrayList<Order>();
        ol.add(o1);
        ol.add(o2);
        ol.add(o3);
        ol.add(o4);

        System.out.println("Sending:" + gson.toJson(ol) + "");
        return gson.toJson(ol);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path="/products")
    public String getProducts() {
        List<Product> products = (List<Product>) productDAO.findAll();
        System.out.println("Sending:" + gson.toJson(products) + "");
        return gson.toJson(products);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path="/start")
    public String startSendingUpdates() {
        List<Product> products = (List<Product>) productDAO.findAll();
        System.out.println("Sending:" + gson.toJson(products) + "");
        return gson.toJson(products);
    }

}

