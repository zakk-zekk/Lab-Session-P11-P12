package lab.kasir;

import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.*;

public class KasirFrame extends JFrame {
    private JTextField txtMenu, txtHarga, txtJumlah;
    private JTextArea areaStruk;
    private JLabel lblTotal;
    private JLabel lblStatus;
    private JButton btnTambah, btnHitung, btnCetak;
    private Pesanan pesanan = new Pesanan();

    public KasirFrame() {
        super("Kasir Kantin UNS");
        setSize(400, 480);
        setLayout(null);

        // --- Tahap 3: komponen ---
        JLabel lblJudul = new JLabel("Kasir Kantin UNS");
        lblJudul.setBounds(20, 10, 200, 25);

        JLabel lblMenu = new JLabel("Nama Menu :");
        lblMenu.setBounds(20, 45, 90, 25);
        txtMenu = new JTextField();
        txtMenu.setBounds(120, 45, 240, 25);

        JLabel lblHarga = new JLabel("Harga     :");
        lblHarga.setBounds(20, 80, 90, 25);
        txtHarga = new JTextField();
        txtHarga.setBounds(120, 80, 240, 25);

        JLabel lblJumlah = new JLabel("Jumlah    :");
        lblJumlah.setBounds(20, 115, 90, 25);
        txtJumlah = new JTextField();
        txtJumlah.setBounds(120, 115, 240, 25);

        btnTambah = new JButton("Tambah");
        btnTambah.setBounds(40, 155, 140, 30);
        btnHitung = new JButton("Hitung Total");
        btnHitung.setBounds(200, 155, 140, 30);

        areaStruk = new JTextArea();
        areaStruk.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaStruk);
        scroll.setBounds(20, 200, 340, 110);

        lblTotal = new JLabel("Total Bayar : Rp 0");
        lblTotal.setBounds(20, 315, 340, 25);

        // --- Tahap 4: tombol cetak + label status ---
        btnCetak = new JButton("Cetak Struk");
        btnCetak.setBounds(120, 345, 140, 30);
        lblStatus = new JLabel("Status : -");
        lblStatus.setBounds(20, 385, 340, 25);

        add(lblJudul);
        add(lblMenu);   add(txtMenu);
        add(lblHarga);  add(txtHarga);
        add(lblJumlah); add(txtJumlah);
        add(btnTambah); add(btnHitung);
        add(scroll);
        add(lblTotal);
        add(btnCetak);
        add(lblStatus);

        btnTambah.addActionListener(e -> tambah());
        btnHitung.addActionListener(e -> lblTotal.setText(
                "Total Bayar : " + rupiah(pesanan.hitungTotal())));

        // Tahap 4: penantian 2 detik di thread baru agar jendela tidak macet
        btnCetak.addActionListener(e -> {
            new Thread(() -> {
                setStatus("Mencetak struk...");
                try { Thread.sleep(2000); } catch (InterruptedException ex) { }
                setStatus("Struk selesai");
            }).start();
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void tambah() {
        String menu = txtMenu.getText().trim();
        String hargaStr = txtHarga.getText().trim();
        String jumlahStr = txtJumlah.getText().trim();

        if (menu.isEmpty() || hargaStr.isEmpty() || jumlahStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
            return;
        }
        try {
            double harga = Double.parseDouble(hargaStr);
            int jumlah = Integer.parseInt(jumlahStr);

            pesanan.tambahItem(harga, jumlah);
            areaStruk.append(menu + "  " + rupiah(harga) + " x " + jumlah
                    + " = " + rupiah(harga * jumlah) + "\n");

            txtMenu.setText("");
            txtHarga.setText("");
            txtJumlah.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harga dan jumlah harus berupa angka!");
        }
    }

    /** Label Swing diubah lewat event dispatch thread agar aman dari thread lain. */
    private void setStatus(String teks) {
        SwingUtilities.invokeLater(() -> lblStatus.setText("Status : " + teks));
    }

    private String rupiah(double nilai) {
        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("id-ID"));
        nf.setMaximumFractionDigits(0);
        return "Rp " + nf.format(nilai);
    }
}