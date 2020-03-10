package exodia.util.pdfwriter;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;

public interface PdfWriter {
    PDDocument createPdfFile(String filename, String content) throws IOException;
}
