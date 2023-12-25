package com.shopme.ecom.utils;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import com.shopme.ecom.entities.User;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class UserPDFExporter extends AbstractExporter{

    public void export(List<User> users, HttpServletResponse response) throws IOException {
        super.setResponseHeaders(response, "application/pdf", ".pdf");
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(10);
        table.setWidths(new float[]{1.5f, 3.5f, 3.0f, 3.0f, 3.0f, 1.5f});
        writeTableHeader(table);
        writeTableData(table, users);
        document.open();
        document.add(table);
        document.close();
    }

    private void writeTableData(PdfPTable table, List<User> users) {
        for(User u: users){
            table.addCell(String.valueOf(u.getId()));
            table.addCell(u.getEmail());
            table.addCell(u.getFirstName());
            table.addCell(u.getLastName());
            table.addCell(u.getRoles().toString());
            table.addCell(String.valueOf(u.isEnabled()));
        }
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
        cell.setPhrase(new Phrase("User ID", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Email", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("First Name", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Last Name", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Roles", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Enabled", font));
        table.addCell(cell);
    }

}
