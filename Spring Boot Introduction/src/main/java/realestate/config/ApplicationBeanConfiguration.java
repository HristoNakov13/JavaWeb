package realestate.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import realestate.util.HtmlReader;
import realestate.util.HtmlReaderImpl;

import javax.validation.Validation;
import javax.validation.Validator;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean(name = "modelMapper")
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
