import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SanPhamManager sanPhamManager = new SanPhamManager();
        Menu menu = new Menu(scanner, sanPhamManager);

        try {
            while (true) {
                menu.display();
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        menu.danhSachSanPham();
                        break;
                    case 2:
                        menu.themSanPham();
                        break;
                    case 3:
                        menu.capNhatSanPham();
                        break;
                    case 4:
                        menu.xoaSanPham();
                        break;
                    case 5:
                        menu.timKiemSanPham();
                        break;
                    case 6:
                        menu.sapXepSanPham();
                        break;
                    case 7:
                        menu.ghiVaoFile();
                        break;
                    case 8:
                        menu.docTuFile();
                        break;
                    case 9:
                        return;
                    default:
                        System.out.println("No choice!");

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
