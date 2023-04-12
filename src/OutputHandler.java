import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class OutputHandler {
    String file;
    java.io.BufferedOutputStream write;
    OutputHandler(String str) {
        file = str;
    }

    public void writeLine(String str) {
        try {
            write.write(str.getBytes(), 0, str.getBytes().length);
        }catch (IOException e){
            System.out.println("Cannot write");
        }
    }

    public void start(){
        try {
            this.write = new BufferedOutputStream(new FileOutputStream(file));
        }catch (IOException e){
            System.out.println("Cannot write");
        }
    }
    public void close(){
        try {
            this.write.close();
        }catch (IOException e){
            System.out.println("Cannot write");
        }
    }
}
