public interface Collectible {
    public void loadFromCsv(String str);
    public void loadFromString(String str);
    public String convertToCsv();
    public long getId();

}
