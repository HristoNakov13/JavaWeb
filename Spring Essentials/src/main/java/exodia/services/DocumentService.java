package exodia.services;

import exodia.domain.models.binding.documents.DocumentScheduleBindingModel;
import exodia.domain.models.view.documents.DocumentDetailsViewModel;
import exodia.domain.models.view.documents.DocumentHomeViewModel;
import exodia.domain.models.view.documents.DocumentPrintViewModel;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.List;

public interface DocumentService {
    String scheduleDocument(DocumentScheduleBindingModel document);

    List<DocumentHomeViewModel> getAllDocuments();

    DocumentDetailsViewModel getDetailsDocumentById(String id);

    DocumentPrintViewModel getPrintDocumentById(String id);

    String print(String id) throws IOException;
}
