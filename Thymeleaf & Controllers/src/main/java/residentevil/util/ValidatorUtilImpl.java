package residentevil.util;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;

public class ValidatorUtilImpl implements ValidatorUtil {

    private Validator validator;


    public ValidatorUtilImpl() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public <T> boolean isValid(T entity) {
        return this.validator.validate(entity).isEmpty();
    }

    @Override
    public <T> List<String> getErrors(T entity) {
        return this.validator.validate(entity)
                .stream()
                .map(error ->
                        String.format("%s - %s",
                                error.getPropertyPath().toString(),
                                error.getMessage()))
                .collect(Collectors.toList());
    }
}