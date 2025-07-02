import java.util.Comparator;

public class SoLuongComparator implements Comparator<SanPham> {
    @Override
    public int compare(SanPham o1, SanPham o2) {
        return Integer.compare(o1.getSoLuong(), o2.getSoLuong());
    }
}