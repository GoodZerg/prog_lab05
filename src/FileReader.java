import java.io.InputStreamReader;

public class FileReader {
    private final InputStreamReader reader = new InputStreamReader(System.in); ///TODO not System.in

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
