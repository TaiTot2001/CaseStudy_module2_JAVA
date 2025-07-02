import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class SanPhamFileIO implements FileIO<SanPham> {
    @Override
    public boolean writeCSVFile(List<SanPham> sanPhamList, String csvFile) {
        try {
            File file = new File(csvFile);
            if (!file.exists()) file.createNewFile();

            Writer writer = new BufferedWriter(new FileWriter(file));
            for (SanPham sanPham : sanPhamList) {
                String text = sanPham.getMaSanPham() + ";" +
                        sanPham.getTenSanPham() + ";" +
                        sanPham.getGiaSanPham() + ";" +
                        sanPham.getSoLuong() + ";" +
                        sanPham.getMoTa() + ";" +
                        sanPham.getDanhMuc() + ";" +
                        sanPham.getNhaSanXuat() + "\n";
                writer.write(text);
            }
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<SanPham> readCSV(String csvFile) {
        List<SanPham> sanPhamList = new ArrayList<>();
        try {
            File file = new File("sanpham.CSV");
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String csvSplitBy = ";";
                if (line.contains(csvSplitBy)) {
                    String[] data = line.split(csvSplitBy);
                    SanPham sanPham = new SanPham();
                    sanPham.setMaSanPham(data[0]);
                    sanPham.setTenSanPham(data[1]);
                    sanPham.setGiaSanPham(Double.parseDouble(data[2]));
                    sanPham.setSoLuong(Integer.parseInt(data[3]));
                    sanPham.setMoTa(data[4]);
                    sanPham.setDanhMuc(data[5]);
                    sanPham.setNhaSanXuat(data[6]);
                    sanPhamList.add(sanPham);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sanPhamList;
    }
}
