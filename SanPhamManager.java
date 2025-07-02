import java.util.ArrayList;
import java.util.List;

public class SanPhamManager implements Manager<SanPham> {
    public List<SanPham> sanPhamList = new ArrayList<>();

    @Override
    public boolean add(SanPham obj) {
        return sanPhamList.add(obj);
    }

    @Override
    public void remove(SanPham obj) {
        sanPhamList.remove(obj);
    }

    @Override
    public void edit(int index, SanPham obj) {
        sanPhamList.set(index, obj);
    }

    public List<SanPham> getAll() {
        return sanPhamList;
    }

    public void setSanPhamList(List<SanPham> sanPhamList) {
        this.sanPhamList = sanPhamList;
    }

    public SanPham findByID(String maSanPham) {
        for (SanPham sanPham : sanPhamList) {
            if (sanPham.getMaSanPham().equals(maSanPham)) {
                return sanPham;
            }
        }
        return null;
    }

    public List<SanPham> searchByID(String maSanPham) {
        List<SanPham> searchList = new ArrayList<>();
        for (SanPham sanPham : sanPhamList) {
            if (sanPham.getMaSanPham().contains(maSanPham)) {
                searchList.add(sanPham);
            }
        }
        return searchList;
    }

    public List<SanPham> searchByName(String tenSanPham) {
        List<SanPham> searchList = new ArrayList<>();
        for (SanPham sanPham : sanPhamList) {
            if (sanPham.getTenSanPham().contains(tenSanPham)) {
                searchList.add(sanPham);
            }
        }
        return searchList;
    }

    public List<SanPham> searchByCategory(String danhMucSanPham) {
        List<SanPham> searchList = new ArrayList<>();
        for (SanPham sanPham : sanPhamList) {
            if (sanPham.getDanhMuc().contains(danhMucSanPham)) {
                searchList.add(sanPham);
            }
        }
        return searchList;
    }

}
