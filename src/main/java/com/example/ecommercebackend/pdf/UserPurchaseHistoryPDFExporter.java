package com.example.ecommercebackend.pdf;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.example.ecommercebackend.model.PurchaseHistory;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;


public class UserPurchaseHistoryPDFExporter {
    private List<PurchaseHistory> purchaseHistories;

    public UserPurchaseHistoryPDFExporter(List<PurchaseHistory> purchaseHistories) {
        this.purchaseHistories = purchaseHistories;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.GRAY);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("product name", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("price", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("quantity", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("delivery charge", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (PurchaseHistory purchaseHistory : purchaseHistories) {
            table.addCell(String.valueOf(purchaseHistory.getName()));
            table.addCell(String.valueOf(purchaseHistory.getPrice()));
            table.addCell(String.valueOf(purchaseHistory.getQuantity()));
            table.addCell(String.valueOf(purchaseHistory.getDeliveryCharge()));

        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Purchase History", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {5f, 2.0f, 2.0f, 2.0f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}