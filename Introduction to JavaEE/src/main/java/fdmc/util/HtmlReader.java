package fdmc.util;

import java.io.IOException;

public interface HtmlReader {
    String getFileContent(String filePath) throws IOException;
}
