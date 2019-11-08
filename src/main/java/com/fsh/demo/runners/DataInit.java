package com.fsh.demo.runners;

import com.fsh.demo.dao.ProductDAO;
import com.fsh.demo.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

enum MonthName {
    January ("JAN"),
    February ("FEB"),
    March ("MAR"),
    April ("APR"),
    May ("MAY"),
    June ("JUN"),
    July ("JUL"),
    August ("AUG"),
    September ("SEP"),
    October ("OCT"),
    November ("NOV"),
    December ("DEC");

    private final String monthcode;

    public String getMonthcode() {
        return monthcode;
    }

    private MonthName(String s) {
        monthcode = s;
    }


    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return monthcode.equals(otherName);
    }

    public String toString() {
        return this.monthcode;
    }
}

@Component
public class DataInit implements ApplicationRunner {
    private ProductDAO productDAO;

    @Autowired
    public DataInit(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    /*
    * Set up the database with some initial data
    *
    * */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        long count = productDAO.count();

        if (count == 0) {

            System.out.println("Saving a few items in the database!");

            Arrays.asList("001","002","003","004","005").forEach(cls -> {
                Arrays.asList(2020, 2021, 2022).forEach(year -> {
                    makeProduct("AFT", "Aftonbladet", cls, MonthName.January, year);
                    makeProduct("AFT", "Aftonbladet", cls, MonthName.February, year);
                    makeProduct("AFT", "Aftonbladet", cls, MonthName.March, year);
                    makeProduct("AFT", "Aftonbladet", cls, MonthName.April, year);
                    makeProduct("AFT", "Aftonbladet", cls, MonthName.May, year);
                    makeProduct("AFT", "Aftonbladet", cls, MonthName.June, year);
                    makeProduct("AFT", "Aftonbladet", cls, MonthName.July, year);
                    makeProduct("AFT", "Aftonbladet", cls, MonthName.August, year);
                    makeProduct("AFT", "Aftonbladet", cls, MonthName.September, year);
                    makeProduct("AFT", "Aftonbladet", cls, MonthName.October, year);
                    makeProduct("AFT", "Aftonbladet", cls, MonthName.November, year);
                    makeProduct("AFT", "Aftonbladet", cls, MonthName.December, year);
                });
            });
        }

        List<Product> list = productDAO.findByNameLike("Apple Inc");
        if (list == null || list.size() == 0) {
            System.out.println("Apple Inc not found, sorry!");
        }
        else {
            System.out.println("Apple Inc FOUND!");
        }
    }

    private int productId = 1;
    private void makeProduct(String underlying, String longname, String cls, MonthName monthname, int year) {

        String symbol = String.format("%s%s%s%d", underlying, cls,monthname.getMonthcode(), year);
        String name = String.format("%s klass %s %s %d", longname, cls, monthname.getMonthcode(), year);
        String productIdStr = String.format("%d",productId++);
        Product p = new Product(productIdStr, symbol, name);
        productDAO.save(p);
    }
}
