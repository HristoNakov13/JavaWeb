package exodia.config;

import exodia.util.pdfwriter.PdfWriter;
import exodia.util.pdfwriter.PdfWriterImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppBeanConfiguration {

    @Bean(name = "modelMapper")
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean(name = "pdfWriter")
    public PdfWriter pdfWriter() {
        return new PdfWriterImpl();
    }
}
