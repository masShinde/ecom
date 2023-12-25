package com.shopme.ecom.utils;

import com.shopme.ecom.entities.User;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;

import java.io.IOException;

import java.util.List;

public class UserExcelExporter extends AbstractExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public UserExcelExporter(){
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine(){
        sheet = workbook.createSheet("Users");

        XSSFRow row = sheet.createRow(0);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        cellStyle.setFont(font);
        createCell(row, 0, "User Id", cellStyle);
        createCell(row, 1, "Email", cellStyle);
        createCell(row, 2, "First Name", cellStyle);
        createCell(row, 3, "Last Name", cellStyle);
        createCell(row, 4, "Roles", cellStyle);
        createCell(row, 5, "Enabled", cellStyle);
    }

    private void createCell(XSSFRow row, int columnIndex, Object value, CellStyle style){
        XSSFCell cell = row.createCell(columnIndex);
        sheet.autoSizeColumn(columnIndex);
        if(value instanceof Integer)
            cell.setCellValue((Integer) value);
        else if(value instanceof Boolean)
            cell.setCellValue((Boolean) value);
        else
            cell.setCellValue((String) value);
        cell.setCellStyle(style);
    }

    public void export(List<User> users, HttpServletResponse response) throws IOException {
        super.setResponseHeaders(response, "application/octet-stream", ".xslx");
        writeHeaderLine();
        writeDataLines(users);
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void writeDataLines(List<User> users) {
        int rowIndex = 1;

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        cellStyle.setFont(font);

        for(User u: users){
            XSSFRow row = sheet.createRow(rowIndex++);
            int columnIndex = 0;

            createCell(row, columnIndex++, u.getId(), cellStyle);
            createCell(row, columnIndex++, u.getEmail(), cellStyle);
            createCell(row, columnIndex++, u.getFirstName(), cellStyle);
            createCell(row, columnIndex++, u.getLastName(), cellStyle);
            createCell(row, columnIndex++, u.getRoles().toString(), cellStyle);
            createCell(row, columnIndex, u.isEnabled(), cellStyle);
        }
    }

}
