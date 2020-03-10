package exodia.util.pdfwriter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class PdfWriterImpl implements PdfWriter{
    private final PDType1Font DEFAULT_FONT_TYPE = PDType1Font.COURIER;
    private final int DEFAULT_FONT_SIZE = 14;

    private PDType1Font fontType;
    private int fontSize;

    public PdfWriterImpl() {
        this.fontType = DEFAULT_FONT_TYPE;
        this.fontSize = DEFAULT_FONT_SIZE;
    }

    public PDDocument createPdfFile(String fileName, String content) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.setFont(this.getFontType(), this.getFontSize());
        contentStream.beginText();
        contentStream.showText(content);
        contentStream.endText();
        contentStream.close();

        document.save(fileName + ".pdf");
        document.close();

        return document;
    }

    public PDType1Font getFontType() {
        return fontType;
    }

    public void setFontType(PDType1Font fontType) {
        this.fontType = fontType;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
}
