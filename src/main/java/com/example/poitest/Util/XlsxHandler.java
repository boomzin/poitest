package com.example.poitest.Util;

import com.example.poitest.model.Product;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class XlsxHandler {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(XlsxHandler.class);

    public static List<Product> loadFromXlsx() {

        List<Product> productList = new ArrayList<>();

        FileInputStream file = null;
        try {
            file = new FileInputStream(("c:\\kocheridi\\xlsx_price.xlsx "));
        } catch (FileNotFoundException e) {
            log.info(e.getMessage());
        }

        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(file);
        } catch (IOException e) {
            log.info(e.getMessage());
        }

        assert workbook != null;
        XSSFSheet sheet = workbook.getSheetAt(0);
        int i = 0;
        for (Row row : sheet) {
            Product product = new Product();
            try {
                product.setVendorCode(row.getCell(0).getStringCellValue());
                product.setName(row.getCell(2).getStringCellValue());
                product.setDescription(row.getCell(6).getStringCellValue());
                product.setPrice(BigDecimal.valueOf(row.getCell(5).getNumericCellValue()));
            } catch (Exception e) {
                System.out.println("Skipped row - " + row.getRowNum());
            }
            if (!(product.getPrice() == null)) {
                productList.add(product);
            }
        }

        return productList;
    }

    public static void loadToXlsx(List<Product> productList) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("New price");

        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(1, 12000);
        sheet.setColumnWidth(2, 14000);
        sheet.setColumnWidth(3, 8000);
        sheet.setColumnWidth(4, 10000);

        XSSFRow header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont headerFont = workbook.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 16);
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        XSSFCellStyle warningStyle = workbook.createCellStyle();
        warningStyle.setFillForegroundColor(IndexedColors.ROSE.getIndex());
        warningStyle.setFillPattern(FillPatternType.FINE_DOTS);

        XSSFFont warningFont = workbook.createFont();
        warningFont.setFontName("Calibri");
        warningFont.setFontHeightInPoints((short) 14);
        warningFont.setBold(true);
        warningStyle.setFont(warningFont);

        XSSFCellStyle regularStyle = workbook.createCellStyle();
        regularStyle.setWrapText(true);


        XSSFCell headerCell = header.createCell(0);
        headerCell.setCellValue("Артикул");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Наименование");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Описание");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Цена");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Цена с наценкой 20%");
        headerCell.setCellStyle(headerStyle);

        for (int i = 0; i < productList.size(); i++) {
            XSSFRow row = sheet.createRow(i + 1);
            BigDecimal extraCharge = productList.get(i).getPrice().multiply(BigDecimal.valueOf(0.2));
            XSSFCellStyle currentStyle = regularStyle;
            if (extraCharge.intValue() >= 10000) {
                currentStyle = warningStyle;
            }
            for (int j = 0; j < 5; j++) {
                Cell cell = row.createCell(0);
                cell.setCellValue(productList.get(i).getVendorCode());
                cell.setCellStyle(currentStyle);
                cell = row.createCell(1);
                cell.setCellValue(productList.get(i).getName());
                cell.setCellStyle(currentStyle);
                cell = row.createCell(2);
                cell.setCellValue(productList.get(i).getDescription());
                cell.setCellStyle(currentStyle);
                cell = row.createCell(3);
                cell.setCellValue(productList.get(i).getPrice().setScale(2, RoundingMode.HALF_UP).doubleValue());
                cell.setCellStyle(currentStyle);
                cell = row.createCell(4);
                cell.setCellValue(productList.get(i).getPrice().add(extraCharge).doubleValue());
                cell.setCellStyle(currentStyle);
            }
        }

        try {
            FileOutputStream outputStream = new FileOutputStream("c:\\kocheridi\\New_price_XLSX.xlsx ");
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
