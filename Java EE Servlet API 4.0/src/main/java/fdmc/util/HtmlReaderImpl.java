package fdmc.util;

import java.io.*;
import java.net.URL;
import java.util.stream.Collectors;

public class HtmlReaderImpl implements HtmlReader {
    private final String VIEWS_DIRECTORY_NAME = "views\\";

    @Override
    public String getFileContent(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        File htmlFile = new File(classLoader.getResource(VIEWS_DIRECTORY_NAME + fileName).getFile());

        String content = null;
        try (BufferedReader bfr = new BufferedReader(new FileReader(htmlFile))) {
            content = bfr.lines()
                    .collect(Collectors.joining("\r\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }
}