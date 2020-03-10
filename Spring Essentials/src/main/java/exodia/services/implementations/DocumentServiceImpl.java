package exodia.services.implementations;

import exodia.domain.entities.Document;
import exodia.domain.models.binding.documents.DocumentScheduleBindingModel;
import exodia.domain.models.view.documents.DocumentDetailsViewModel;
import exodia.domain.models.view.documents.DocumentHomeViewModel;
import exodia.domain.models.view.documents.DocumentPrintViewModel;
import exodia.repositories.DocumentRepository;
import exodia.services.DocumentService;
import exodia.util.messages.ThrowMessages;
import exodia.util.pdfwriter.PdfWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {
    private final int TITLE_ABBREVIATION_LENGTH = 13;

    private ModelMapper modelMapper;
    private DocumentRepository documentRepository;
    private PdfWriter pdfWriter;

    @Autowired
    public DocumentServiceImpl(ModelMapper modelMapper, DocumentRepository documentRepository, PdfWriter pdfWriter) {
        this.modelMapper = modelMapper;
        this.documentRepository = documentRepository;
        this.pdfWriter = pdfWriter;

        this.init();
    }

    @Override
    public String scheduleDocument(DocumentScheduleBindingModel document) {
        Document documentEntity = this.modelMapper.map(document, Document.class);
        this.documentRepository.saveAndFlush(documentEntity);

        return documentEntity.getId();
    }

    @Override
    public List<DocumentHomeViewModel> getAllDocuments() {
        return this.documentRepository
                .findAll()
                .stream()
                .map(document -> this.modelMapper.map(document, DocumentHomeViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public DocumentDetailsViewModel getDetailsDocumentById(String id) {
        Document document = this.getDocumentById(id);

        return this.modelMapper.map(document, DocumentDetailsViewModel.class);
    }

    @Override
    public DocumentPrintViewModel getPrintDocumentById(String id) {
        Document document = this.getDocumentById(id);

        return this.modelMapper.map(document, DocumentPrintViewModel.class);
    }

    @Override
    public String print(String id) throws IOException {
        Document document = this.getDocumentById(id);
//        PDDocument file = this.pdfWriter.createPdfFile(document.getTitle(), document.getContent());

        this.documentRepository.deleteById(id);

        return document.getContent();
    }

    private Document getDocumentById(String id) {
        Optional<Document> document = this.documentRepository.findById(id);

        if (document.isEmpty()) {
            throw new IllegalArgumentException(ThrowMessages.INVALID_DOCUMENT_ID);
        }

        return document.get();
    }

    private void init() {
        Converter<String, String> titleAbbreviation = new Converter<String, String>() {
            @Override
            public String convert(MappingContext<String, String> context) {
                String title = context.getSource();

                return title.length() <= TITLE_ABBREVIATION_LENGTH
                        ? title
                        : title.substring(0, TITLE_ABBREVIATION_LENGTH) + "...";
            }
        };

        this.modelMapper
                .createTypeMap(Document.class, DocumentHomeViewModel.class)
                .addMappings(mapper -> mapper.using(titleAbbreviation).map(Document::getTitle, DocumentHomeViewModel::setTitle));
    }
}