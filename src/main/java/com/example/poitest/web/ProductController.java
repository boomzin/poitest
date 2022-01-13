package com.example.poitest.web;

import com.example.poitest.Util.XlsHandler;
import com.example.poitest.model.Product;
import com.example.poitest.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProductController.class);

    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    @GetMapping
    public void LoadXls() {
        log.info("Start parsing xls, mapping rows to product objects");
        long start = System.currentTimeMillis();
        List<Product> productList = XlsHandler.loadFromXls();
        log.info("Parsing complete, time - " + (System.currentTimeMillis() - start));
        log.info("Writing objects into base");
        start = System.currentTimeMillis();
        productRepository.saveAll(productList);
        log.info("Writing object into base complete, time - " + (System.currentTimeMillis() - start));
        log.info("Reading data from db");
        start = System.currentTimeMillis();
        productList = productRepository.findAll();
        log.info("Reading complete, time - " + (System.currentTimeMillis() - start));
        log.info("Writing data to a new file");
        start = System.currentTimeMillis();
        XlsHandler.loadToXls(productList);
        log.info("Writing complete, time - " + (System.currentTimeMillis() - start));
    }
}
