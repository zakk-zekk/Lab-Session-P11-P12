package lab.kasir;

import javax.swing.SwingUtilities;

/** Menjalankan jendela kasir (Tahap 3 dan 4). */
public class MainGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new KasirFrame());
    }
}
