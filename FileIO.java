import java.util.List;

public interface FileIO<T> {
    boolean writeCSVFile(List<T> lists, String csvFile);
    List<T> readCSV(String csvFile);
}