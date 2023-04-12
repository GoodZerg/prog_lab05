import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class FileReader {
    private final InputStreamReader reader;

    FileReader(String file_name) {
        try {
            File asdasd = new File(file_name);
            System.out.println(asdasd.exists());
            FileInputStream asd = new FileInputStream(asdasd);
            this.reader = new InputStreamReader(asd);
        }catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }
    public String get() {
        StringBuilder str = new StringBuilder();
        try {
            while (reader.ready()) {
                str.append(reader.read());
            }
        }catch (java.io.IOException exception) {
            throw new RuntimeException
                    ("reading Error\njava.io.IOException:::::" +
                            exception.getMessage());
        }
        return str.toString();
    }

    public void close() {
        try {
            reader.close();
        }catch (java.io.IOException exception) {
            throw new RuntimeException
                    ("reading Error\njava.io.IOException:::::" +
                            exception.getMessage());
        }
    }
}
