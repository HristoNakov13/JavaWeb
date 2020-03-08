package realestate.util;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.stream.Collectors;

@Component
public class HtmlReaderImpl implements HtmlReader {
    @Override
    public String getFileContent(String filePath) {
        File file = new File(filePath);
        String content = null;

        try (BufferedReader bfr = new BufferedReader(new FileReader(file))){
            content = bfr.lines().collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }
}
