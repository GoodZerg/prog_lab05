import java.io.BufferedReader;

public interface Collectible {
    public void loadFromCsv(String str);
    public void loadFromStandardInput(BufferedReader _reader, boolean isStandardInput);
    public String convertToCsv();
    public long getId();
    public Integer getDistance();
    public int compareByCord(Object o);
    public void setStartID(long id);
}
