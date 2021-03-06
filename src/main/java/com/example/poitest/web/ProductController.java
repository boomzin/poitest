package com.example.poitest.web;

import com.example.poitest.Util.XlsHandler;
import com.example.poitest.Util.XlsxHandler;
import com.example.poitest.model.Product;
import com.example.poitest.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProductController.class);

    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    @GetMapping("/xls")
    public String processXls() {
        StringBuilder stringBuilder = new StringBuilder();
        log.info("Start parsing xls, mapping rows to product objects. ");
        stringBuilder.append("Start parsing xls, mapping rows to product objects. ");
        long start = System.currentTimeMillis();
        List<Product> productList = XlsHandler.loadFromXls();
        log.info("Parsing complete, time - " + (System.currentTimeMillis() - start));
        stringBuilder.append("Parsing complete, time - ").append(System.currentTimeMillis() - start);
        log.info("Writing objects into base");
        stringBuilder.append("Writing objects into base");
        start = System.currentTimeMillis();
        productRepository.saveAll(productList);
        log.info("Writing object into base complete, time - " + (System.currentTimeMillis() - start));
        stringBuilder.append("Writing object into base complete, time - ").append(System.currentTimeMillis() - start);
        log.info("Reading data from db");
        stringBuilder.append("Reading data from db");
        start = System.currentTimeMillis();
        productList = productRepository.findAll();
        log.info("Reading complete, time - " + (System.currentTimeMillis() - start));
        stringBuilder.append("Reading complete, time - ").append(System.currentTimeMillis() - start);
        log.info("Writing data to a new file");
        stringBuilder.append("Writing data to a new file");
        start = System.currentTimeMillis();
        XlsHandler.loadToXls(productList);
        log.info("Writing complete, time - " + (System.currentTimeMillis() - start));
        stringBuilder.append("Writing complete, time - ").append(System.currentTimeMillis() - start);
        return stringBuilder.toString();
    }

    @Transactional
    @GetMapping("/xlsx")
    public String processXlsx() {
        StringBuilder stringBuilder = new StringBuilder();
        log.info("Start parsing xlsx, mapping rows to product objects. ");
        stringBuilder.append("Start parsing xls, mapping rows to product objects. ");
        long start = System.currentTimeMillis();
        List<Product> productList = XlsxHandler.loadFromXlsx();
        log.info("Parsing complete, time - " + (System.currentTimeMillis() - start));
        stringBuilder.append("Parsing complete, time - ").append(System.currentTimeMillis() - start);
        log.info("Writing objects into base");
        stringBuilder.append("Writing objects into base. ");
        start = System.currentTimeMillis();
        productRepository.saveAll(productList);
        log.info("Writing object into base complete, time - " + (System.currentTimeMillis() - start));
        stringBuilder.append("Writing object into base complete, time - ").append(System.currentTimeMillis() - start);
        log.info("Reading data from db");
        stringBuilder.append("Reading data from db");
        start = System.currentTimeMillis();
        productList = productRepository.findAll();
        log.info("Reading complete, time - " + (System.currentTimeMillis() - start));
        stringBuilder.append("Reading complete, time - ").append(System.currentTimeMillis() - start);
        log.info("Writing data to a new file");
        stringBuilder.append("Writing data to a new file");
        start = System.currentTimeMillis();
        XlsxHandler.loadToXlsx(productList);
        log.info("Writing complete, time - " + (System.currentTimeMillis() - start));
        stringBuilder.append("Writing complete, time - ").append(System.currentTimeMillis() - start);
        return stringBuilder.toString();
    }
}
