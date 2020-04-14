package residentevil.util;

import java.util.List;

public interface ValidatorUtil {

    <T> boolean isValid(T entity);

    <T> List<String> getErrors(T entity);
}
