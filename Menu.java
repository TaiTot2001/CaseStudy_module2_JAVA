import java.util.List;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner;
    private final SanPhamManager sanPhamManager;
    private final Validator validator = new Validator();
    private final SanPhamFileIO sanPhamFileIO = new SanPhamFileIO();

    public Menu(Scanner scanner, SanPhamManager sanPhamManager) {
        this.scanner = scanner;
        this.sanPhamManager = sanPhamManager;
    }

    void display() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║--------- CHƯƠNG TRÌNH QUẢN LÝ SẢN PHẨM ----------║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.println("║        1. Xem danh sách sản phẩm                 ║");
        System.out.println("║--------------------------------------------------║");
        System.out.println("║        2. Thêm sản phẩm mới                      ║");
        System.out.println("║--------------------------------------------------║");
        System.out.println("║        3. Cập nhật sản phẩm                      ║");
        System.out.println("║--------------------------------------------------║");
        System.out.println("║        4. Xóa sản phẩm                           ║");
        System.out.println("║--------------------------------------------------║");
        System.out.println("║        5. Tìm kiếm sản phẩm                      ║");
        System.out.println("║--------------------------------------------------║");
        System.out.println("║        6. Sắp xếp sản phẩm                       ║");
        System.out.println("║--------------------------------------------------║");
        System.out.println("║        7. Ghi vào file                           ║");
        System.out.println("║--------------------------------------------------║");
        System.out.println("║        8. Đọc từ file                            ║");
        System.out.println("║--------------------------------------------------║");
        System.out.println("║        9. Thoát                                  ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.print("⇒ Chọn chức năng (1 -> 9): ");
    }

    void danhSachSanPham() {
        System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                          DANH SÁCH SẢN PHẨM                                                             ║");
        System.out.println("╠════════════╦════════════════════════╦══════════════════╦════════════╦════════════════════════╦════════════════════════╦═════════════════╣");
        System.out.printf("║ %-10s ║ %-22s ║ %-16s ║ %-10s ║ %-22s ║ %-22s ║ %-15s ║\n",
                "    Mã", "         Tên", "      Giá", " Số lượng", "        Mô tả", "      Danh mục", "    Nhà SX");
        System.out.println("╠════════════╬════════════════════════╬══════════════════╬════════════╬════════════════════════╬════════════════════════╬═════════════════╣");

        if (!sanPhamManager.getAll().isEmpty()) {
            for (int i = 0; i < sanPhamManager.getAll().size(); i++) {
                SanPham sp = sanPhamManager.getAll().get(i);
                System.out.printf("║ %-10s ║ %-22s ║ %-16.2f ║ %-10d ║ %-22s ║ %-22s ║ %-15s ║\n",
                        sp.getMaSanPham(), sp.getTenSanPham(), sp.getGiaSanPham(), sp.getSoLuong(),
                        sp.getMoTa(), sp.getDanhMuc(), sp.getNhaSanXuat());

                if (i < sanPhamManager.getAll().size() - 1) {
                    System.out.println("╠════════════╬════════════════════════╬══════════════════╬════════════╬════════════════════════╬════════════════════════╬═════════════════╣");
                }
            }
        } else {
            System.out.println("║                                                       Danh sách sản phẩm trống!                                                         ║");
        }

        System.out.println("╚════════════╩════════════════════════╩══════════════════╩════════════╩════════════════════════╩════════════════════════╩═════════════════╝");
    }

    void themSanPham() {
        System.out.println("══════════════════ THÊM SẢN PHẨM ══════════════════");
        SanPham sanPhamMoi = new SanPham();
        nhapSanPham(sanPhamMoi);
        if (sanPhamManager.add(sanPhamMoi)) {
            System.out.println("Bạn đã thêm sản phẩm thành công !");
            danhSachSanPham();
        } else {
            System.out.println("Có lỗi xảy ra! Vui lòng thử lại.");

        }
    }

    void capNhatSanPham() {
        System.out.println("══════════════════ CẬP NHẬT SẢN PHẨM ══════════════════");
        String maSanPham = inputStr("Nhập vào mã sản phẩm cần sửa: ");
        SanPham sanPham = sanPhamManager.findByID(maSanPham);

        if (sanPham != null) {
            int index = sanPhamManager.getAll().indexOf(sanPham);
            if (index != -1) {
                sanPham.setMaSanPham(nhapMaSP());
                sanPham.setTenSanPham(inputStr("Nhập tên sản phẩm: "));
                sanPham.setGiaSanPham(nhapGiaSanPham());
                sanPham.setSoLuong(nhapSoLuong());
                sanPham.setMoTa(inputStr("Nhập mô tả Sản Phẩm: "));
                sanPham.setDanhMuc(inputStr("Danh mục: "));
                sanPham.setNhaSanXuat(inputStr("Nhà sản xuất: "));
                sanPhamManager.edit(index, sanPham);
                System.out.println("Sản phẩm đã được sửa thành công !");
            }
        } else {
            System.out.println("Mã sản phẩm trên không tồn tại!");
        }
    }

    void xoaSanPham() {
        System.out.println("══════════════════ XÓA SẢN PHẨM ══════════════════");
        String maSanPham = inputStr("Nhập vào mã sản phẩm cần xóa: ");
        SanPham sanPham = sanPhamManager.findByID(maSanPham);
        if (sanPham != null) {
            String confirm = inputStr("Bạn thực sự muốn xóa sản phẩm này? [Yes/No]:");
            if (confirm.equalsIgnoreCase("Yes")) {
                sanPhamManager.remove(sanPham);
                System.out.println("Sản phẩm đã được xóa !");
                danhSachSanPham();
            } else {
                System.out.println("Bạn đã hủy xóa sản phẩm thành công!");
            }
        } else {
            System.out.println("Mã sản phẩm trên không tồn tại!");
        }

    }

    void timKiemSanPham() {
        System.out.println("══════════════════ TÌM KIẾM SẢN PHẨM ══════════════════");
        int choiceSearch;
        do {
            System.out.println("1. Tìm theo mã sản phẩm. ");
            System.out.println("2. Tìm theo tên sản phẩm. ");
            System.out.println("3. Tìm theo danh mục sản phẩm.");
            System.out.println("4. Thoát. ");
            System.out.print("⇒ Chọn chức năng (1 -> 4): ");
            choiceSearch = scanner.nextInt();
            scanner.nextLine();
            switch (choiceSearch) {
                case 1:
                    timTheoMaSP();
                    break;
                case 2:
                    timTheoTenSP();
                    break;
                case 3:
                    timTheoDanhMucSP();
                    break;
                case 4:
                    System.out.println("--------------------------------------------------Bạn đã quay lại menu chính từ chức năng tìm kiếm sản phẩm.--------------------------------------------------");
                    return;
                default:
                    System.out.println("Không có lựa chọn này!");
            }
        } while (choiceSearch != 0);
    }

    void sapXepSanPham() {
        System.out.println("══════════════════ SẮP XẾP SẢN PHẨM ══════════════════");
        int choiceSearch;
        do {
            System.out.println("1. Sắp xếp theo số lượng. ");
            System.out.println("2. Sắp xếp theo danh mục. ");
            System.out.println("3. Thoát. ");
            System.out.print("⇒ Chọn chức năng (1 -> 3): ");
            choiceSearch = scanner.nextInt();
            scanner.nextLine();
            switch (choiceSearch) {
                case 1:
                    sapXepSoLuong();
                    break;
                case 2:
                    sapXepDanhMuc();
                    break;
                case 3:
                    System.out.println("--------------------------------------------------Bạn đã quay lại menu chính từ chức năng sắp xếp sản phẩm.--------------------------------------------------");
                    return;
                default:
                    System.out.println("Không có lựa chọn này!");
            }
        } while (choiceSearch != 0);
    }

    private void sapXepDanhMuc() {
        System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                               ***  SẮP XẾP SẢN PHẨM THEO DANH MỤC  ***                                                  ║");
        List<SanPham> sanPhamList = sanPhamManager.getAll();
        sanPhamList.sort(new DanhMucComparator().reversed());
        hienThi(sanPhamList);
    }

    private void sapXepSoLuong() {
        System.out.println("══════════════════ SẮP XẾP THEO SỐ LƯỢNG ══════════════════");
        int choice;
        do {
            System.out.println("1. Sắp xếp tăng dần. ");
            System.out.println("2. Sắp xếp giảm dần. ");
            System.out.println("3. Thoát. ");
            System.out.print("⇒ Chọn chức năng (1 or 2): ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    tangSoLuong();
                    break;
                case 2:
                    giamSoLuong();
                    break;
                case 3:
                    System.out.println("--------------------------------------------------Bạn đã thoát khỏi chức năng sắp xếp theo số lượng--------------------------------------------------");
                    return;
                default:
                    System.out.println("Không có lựa chọn này!");
            }
        } while (choice != 0);
    }

    void ghiVaoFile() {
        System.out.println("══════════════════ GHI VÀO FILE ══════════════════");
        String confirm = inputStr("Cảnh báo: Lựa chọn này sẽ tiếp tục ghi nội dung mới vào file! [Yes/No]: ");
        if (confirm.equalsIgnoreCase("Yes")) {
            if (sanPhamFileIO.writeCSVFile(sanPhamManager.getAll(), "sanpham.CSV")) {
                System.out.println("Ghi vào file \"sanpham.CSV\" thành công!");
            } else {
                System.out.println("Đã xảy ra lỗi ghi file!");
            }
        }
    }

    void docTuFile() {
        System.out.println("══════════════════ ĐỌC TỪ FILE ══════════════════");
        String confirm = inputStr("Cảnh báo: Lựa chọn này sẽ ghi đè danh sách hiện tại trong chương trình! [Yes/No]: ");
        if (confirm.equalsIgnoreCase("Yes")) {
            List<SanPham> docFileList = sanPhamFileIO.readCSV("sanpham.CSV"); // đảm bảo đồng bộ đường dẫn
            if (docFileList == null) {
                System.out.println("Đọc file thất bại: Không thể mở file hoặc file không đúng định dạng.");
            } else if (docFileList.isEmpty()) {
                System.out.println("File rỗng hoặc không chứa sản phẩm hợp lệ.");
            } else {
                sanPhamManager.setSanPhamList(docFileList);
                System.out.println("Đọc dữ liệu từ file \"sanpham.CSV\" thành công!");
            }
        } else {
            System.out.println("Đã hủy đọc file.");
        }
    }

    private void tangSoLuong() {
        System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                             ***  SẮP XẾP SỐ LƯỢNG SẢN PHẨM TĂNG DẦN  ***                                                ║");
        List<SanPham> sanPhamList = sanPhamManager.getAll();
        sanPhamList.sort(new SoLuongComparator());
        hienThi(sanPhamList);
    }

    private void giamSoLuong() {
        System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                             ***  SẮP XẾP SỐ LƯỢNG SẢN PHẨM GIẢM DẦN  ***                                                ║");

        List<SanPham> sanPhamList = sanPhamManager.getAll();
        sanPhamList.sort(new SoLuongComparator().reversed());
        hienThi(sanPhamList);
    }

    private void timTheoMaSP() {
        System.out.println("══════════════════ TÌM THEO MÃ SẢN PHẨM ══════════════════");
        String maSanPham = inputStr("Nhập mã sản phẩm: ");
        List<SanPham> sanPhamList = sanPhamManager.searchByID(maSanPham);
        if (!sanPhamList.isEmpty()) {
            hienThi(sanPhamList);
        } else {
            System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                 **  Không tìm thấy sản phẩm phù hợp với mã sản phẩm bạn đã nhập ! **                                    ║");
            hienThi(sanPhamList);
        }
    }

    private void timTheoTenSP() {
        System.out.println("══════════════════ TÌM THEO TÊN SẢN PHẨM ══════════════════");
        String tenSanPham = inputStr("Nhập tên sản phẩm: ");
        List<SanPham> sanPhamList = sanPhamManager.searchByName(tenSanPham);
        if (!sanPhamList.isEmpty()) {
            hienThi(sanPhamList);
        } else {
            System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                 **  Không tìm thấy sản phẩm phù hợp với tên sản phẩm bạn đã nhập ! **                                   ║");
            hienThi(sanPhamList);
        }

    }

    private void timTheoDanhMucSP() {
        System.out.println("══════════════════ TÌM THEO DANH MỤC SẢN PHẨM ══════════════════");
        String danhMucSanPham = inputStr("Nhập danh mục sản phẩm: ");
        List<SanPham> sanPhamList = sanPhamManager.searchByCategory(danhMucSanPham);
        if (!sanPhamList.isEmpty()) {
            hienThi(sanPhamList);
        } else {
            System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                            **  Không tìm thấy sản phẩm phù hợp với tên danh mục sản phẩm bạn đã nhập ! **                               ║");
            hienThi(sanPhamList);
        }
    }

    private static void hienThi(List<SanPham> sanPhamList) {
        System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                          DANH SÁCH SẢN PHẨM                                                             ║");
        System.out.println("╠════════════╦════════════════════════╦══════════════════╦════════════╦════════════════════════╦════════════════════════╦═════════════════╣");
        System.out.printf("║ %-10s ║ %-22s ║ %-16s ║ %-10s ║ %-22s ║ %-22s ║ %-15s ║\n",
                "    Mã", "   Tên", "     Giá", " Số lượng", "        Mô tả", "      Danh mục", "    Nhà SX");
        System.out.println("╠════════════╬════════════════════════╬══════════════════╬════════════╬════════════════════════╬════════════════════════╬═════════════════╣");
        if(!sanPhamList.isEmpty()) {
            for (int i = 0; i < sanPhamList.size(); i++) {
                SanPham sp = sanPhamList.get(i);
                System.out.printf("║ %-10s ║ %-22s ║ %-16.2f ║ %-10d ║ %-22s ║ %-22s ║ %-15s ║\n",
                        sp.getMaSanPham(), sp.getTenSanPham(), sp.getGiaSanPham(), sp.getSoLuong(),
                        sp.getMoTa(), sp.getDanhMuc(), sp.getNhaSanXuat());
                if (i < sanPhamList.size() - 1) {
                    System.out.println("╠════════════╬════════════════════════╬══════════════════╬════════════╬════════════════════════╬════════════════════════╬═════════════════╣");
                }
            }
        }else{
            System.out.println("║                                                      Danh sách sản phẩm trống !                                                         ║");
        }
        System.out.println("╚════════════╩════════════════════════╩══════════════════╩════════════╩════════════════════════╩════════════════════════╩═════════════════╝");
    }

    private void nhapSanPham(SanPham sanPham) {
        sanPham.setMaSanPham(nhapMaSanPham());
        sanPham.setTenSanPham(inputStr("Nhập tên sản phẩm: "));
        sanPham.setGiaSanPham(nhapGiaSanPham());
        sanPham.setSoLuong(nhapSoLuong());
        sanPham.setMoTa(inputStr("Nhập mô tả Sản Phẩm: "));
        sanPham.setDanhMuc(inputStr("Danh mục: "));
        sanPham.setNhaSanXuat(inputStr("Nhà sản xuất: "));
    }

    //Kiểm tra trùng id
    private String nhapMaSanPham() {
        String str;
        while (true) {
            System.out.print("Nhập vào mã sản phẩm (** Mã phải có ít nhất 1 chữ và số): ");
            str = scanner.nextLine();
            if (str.isEmpty()) {
                System.out.println("Trường dữ liệu không được bỏ trống. Vui lòng nhập lại !");
            } else if (!validator.isValidID(str)) {
                System.out.println("Định dạng mã sản phẩm không hợp lệ (phải chứa ít nhất 1 chữ và 1 số)!");
            } else if (checkID(str)) {
                System.out.println("Mã sản phẩm đã tồn tại! Vui lòng nhập lại....");
            } else {
                break;
            }
        }
        return str.toLowerCase();
    }

    //Không kiểm tra trùng id
    private String nhapMaSP() {
        String str;
        while (true) {
            System.out.print("Nhập vào mã sản phẩm (** Mã phải có ít nhất 1 chữ và số): ");
            str = scanner.nextLine();
            if (str.isEmpty()) {
                System.out.println("Trường dữ liệu không được bỏ trống. Vui lòng nhập lại !");
            } else if (!validator.isValidID(str)) {
                System.out.println("Định dạng mã sản phẩm không hợp lệ (phải chứa ít nhất 1 chữ và 1 số)!");
            } else {
                break;
            }
        }
        return str.toLowerCase();
    }

    private boolean checkID(String str) {
        for (SanPham sp : sanPhamManager.sanPhamList) {
            if (str.equalsIgnoreCase(sp.getMaSanPham())) {
                return true;
            }
        }
        return false;
    }

    private int nhapSoLuong() {
        while (true) {
            System.out.print("Nhập số lượng: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Trường dữ liệu không được bỏ trống. Vui lòng nhập lại !");
                continue;
            }

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số nguyên hợp lệ!");
            }
        }
    }

    private Double nhapGiaSanPham() {
        while (true) {
            System.out.print("Nhập giá sản phẩm: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Trường dữ liệu không được bỏ trống. Vui lòng nhập lại !");
                continue;
            }
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số hợp lệ (có thể chứa phần thập phân)!");
            }
        }
    }

    private String inputStr(String title) {
        String str;
        while (true) {
            System.out.print(title);
            str = scanner.nextLine();
            if (str.isEmpty()) {
                System.out.println("Trường dữ liệu không được bỏ trống. Vui lòng nhập lại!");
            } else {
                break;
            }
        }
        return str.toLowerCase();
    }
}
