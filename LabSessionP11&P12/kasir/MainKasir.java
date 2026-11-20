package lab.kasir;

/** Tahap 1 (uji logika Pesanan) dan Tahap 2 (dua kasir sebagai thread). */
public class MainKasir {
    public static void main(String[] args) throws InterruptedException {
        // ===== Tahap 1 =====
        System.out.println("=== Tahap 1: Uji Pesanan ===");
        Pesanan pesanan = new Pesanan();

        pesanan.tambahItem(15000, 2);   // Nasi Goreng
        pesanan.tambahItem(5000, 3);    // Es Teh
        System.out.println("Setelah 2 menu -> subtotal: " + pesanan.getSubtotal()
                + ", total: " + pesanan.hitungTotal());

        pesanan.tambahItem(25000, 1);   // Ayam Bakar
        System.out.println("Setelah 3 menu -> subtotal: " + pesanan.getSubtotal()
                + ", total: " + pesanan.hitungTotal());

        pesanan.reset();
        System.out.println("Setelah reset  -> subtotal: " + pesanan.getSubtotal());

        // ===== Tahap 2 =====
        System.out.println();
        System.out.println("=== Tahap 2: Dua Kasir ===");
        Kasir dina = new Kasir("Dina");
        Kasir eko = new Kasir("Eko");

        dina.start();
        eko.start();

        dina.join();
        eko.join();
        System.out.println("Semua pelanggan selesai dilayani");
    }
}
