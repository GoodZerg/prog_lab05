public interface Collectible {
    public void loadFromCsv(String str);
    public void loadFromStandardInput();
    public String convertToCsv();
    public long getId();

}
