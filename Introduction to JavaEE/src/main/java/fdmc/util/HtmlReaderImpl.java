package fdmc.util;

import java.io.*;

public class HtmlReaderImpl implements HtmlReader {
    @Override
    public String getFileContent(String fileName) throws IOException {
        File htmlFile = new File(fileName);

        BufferedReader bfr = new BufferedReader(new FileReader(htmlFile));

        StringBuilder content = new StringBuilder();
        String line;

        while ((line = bfr.readLine()) != null) {
            content.append(line)
                    .append(System.lineSeparator());
        }

        return content.toString();
    }
}
