import java.io.*;

public class FileReader {
    private final BufferedReader reader;

    FileReader(String file_name) {
        try {
            this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(file_name)));
        }catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }
    public String get() {
        try {
            return reader.readLine();
        }catch (java.io.IOException exception) {
            throw new RuntimeException
                    ("reading Error\njava.io.IOException:::::" +
                            exception.getMessage());
        }
    }

    public boolean ready() {
        try {
            return reader.ready();
        }catch (java.io.IOException exception) {
            throw new RuntimeException
                    ("reading Error\njava.io.IOException:::::" +
                            exception.getMessage());
        }
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
