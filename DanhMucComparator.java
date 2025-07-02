import java.util.Comparator;

public class DanhMucComparator implements Comparator<SanPham> {
    @Override
    public int compare(SanPham o1, SanPham o2) {
        return o1.getDanhMuc().compareToIgnoreCase(o2.getDanhMuc());
    }
}