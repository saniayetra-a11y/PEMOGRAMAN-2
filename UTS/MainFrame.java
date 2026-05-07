import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

// CLASS PRODUCT
class Product {

    private String nama;
    private String kategori;
    private int harga;

    public Product(String nama, String kategori, int harga) {
        this.nama = nama;
        this.kategori = kategori;
        this.harga = harga;
    }

    public String getNama() {
        return nama;
    }

    public String getKategori() {
        return kategori;
    }

    public int getHarga() {
        return harga;
    }
}

// CLASS PRODUCT MANAGER
class ProductManager {

    Stack<Product> stackProduk = new Stack<>();

    // tambah produk
    public void tambahProduk(Product p) {
        stackProduk.push(p);

        System.out.println("Produk ditambahkan : " + p.getNama());
    }

    // hapus produk
    public Product hapusProduk() throws Exception {

        if (stackProduk.isEmpty()) {
            throw new Exception("Stok produk kosong!");
        }

        Product p = stackProduk.pop();

        System.out.println("Produk dihapus : " + p.getNama());

        return p;
    }

    // tampil semua produk
    public ArrayList<Product> getSemuaProduk() {
        return new ArrayList<>(stackProduk);
    }

    // searching
    public Product cariProduk(String nama) {

        for (Product p : stackProduk) {

            if (p.getNama().equalsIgnoreCase(nama)) {
                return p;
            }
        }

        return null;
    }

    // sorting harga
    public ArrayList<Product> sortHarga() {

        ArrayList<Product> list = new ArrayList<>(stackProduk);

        Collections.sort(list, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getHarga() - o2.getHarga();
            }
        });

        return list;
    }

    // sorting kategori
    public ArrayList<Product> sortKategori() {

        ArrayList<Product> list = new ArrayList<>(stackProduk);

        Collections.sort(list, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getKategori().compareTo(o2.getKategori());
            }
        });

        return list;
    }
}

// CLASS GUI
public class MainFrame extends JFrame {

    JTextField txtNama, txtKategori, txtHarga, txtCari;

    JButton btnTambah, btnHapus, btnTampil,
            btnCari, btnSortHarga, btnSortKategori;

    JTable table;
    DefaultTableModel model;

    ProductManager manager = new ProductManager();

    public MainFrame() {

        setTitle("Aplikasi Manajemen Produk");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ABSOLUTE LAYOUT
        setLayout(null);

        // LABEL
        JLabel lblNama = new JLabel("Nama Produk");
        lblNama.setBounds(20, 20, 100, 25);
        add(lblNama);

        JLabel lblKategori = new JLabel("Kategori");
        lblKategori.setBounds(20, 60, 100, 25);
        add(lblKategori);

        JLabel lblHarga = new JLabel("Harga");
        lblHarga.setBounds(20, 100, 100, 25);
        add(lblHarga);

        JLabel lblCari = new JLabel("Cari Nama");
        lblCari.setBounds(20, 140, 100, 25);
        add(lblCari);

        // TEXTFIELD
        txtNama = new JTextField();
        txtNama.setBounds(120, 20, 200, 25);
        add(txtNama);

        txtKategori = new JTextField();
        txtKategori.setBounds(120, 60, 200, 25);
        add(txtKategori);

        txtHarga = new JTextField();
        txtHarga.setBounds(120, 100, 200, 25);
        add(txtHarga);

        txtCari = new JTextField();
        txtCari.setBounds(120, 140, 200, 25);
        add(txtCari);

        // BUTTON
        btnTambah = new JButton("Tambah");
        btnTambah.setBounds(350, 20, 120, 30);
        add(btnTambah);

        btnHapus = new JButton("Hapus");
        btnHapus.setBounds(350, 60, 120, 30);
        add(btnHapus);

        btnTampil = new JButton("Tampilkan");
        btnTampil.setBounds(350, 100, 120, 30);
        add(btnTampil);

        btnCari = new JButton("Cari");
        btnCari.setBounds(350, 140, 120, 30);
        add(btnCari);

        btnSortHarga = new JButton("Sort Harga");
        btnSortHarga.setBounds(500, 20, 150, 30);
        add(btnSortHarga);

        btnSortKategori = new JButton("Sort Kategori");
        btnSortKategori.setBounds(500, 60, 150, 30);
        add(btnSortKategori);

        // TABEL
        model = new DefaultTableModel();

        model.addColumn("Nama Produk");
        model.addColumn("Kategori");
        model.addColumn("Harga");

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 200, 740, 220);

        add(scrollPane);

        // EVENT TAMBAH
        btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    String nama = txtNama.getText();
                    String kategori = txtKategori.getText();

                    int harga = Integer.parseInt(txtHarga.getText());

                    Product p = new Product(nama, kategori, harga);

                    manager.tambahProduk(p);

                    tampilkanData(manager.getSemuaProduk());

                    JOptionPane.showMessageDialog(null,
                            "Produk berhasil ditambahkan");

                    txtNama.setText("");
                    txtKategori.setText("");
                    txtHarga.setText("");

                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(null,
                            "Harga harus angka!");
                }
            }
        });

        // EVENT HAPUS
        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    Product p = manager.hapusProduk();

                    tampilkanData(manager.getSemuaProduk());

                    JOptionPane.showMessageDialog(null,
                            "Produk dihapus : " + p.getNama());

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(null,
                            ex.getMessage());
                }
            }
        });

        // EVENT TAMPIL
        btnTampil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                tampilkanData(manager.getSemuaProduk());
            }
        });

        // EVENT SORT HARGA
        btnSortHarga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                tampilkanData(manager.sortHarga());
            }
        });

        // EVENT SORT KATEGORI
        btnSortKategori.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                tampilkanData(manager.sortKategori());
            }
        });

        // EVENT SEARCH
        btnCari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nama = txtCari.getText();

                Product p = manager.cariProduk(nama);

                model.setRowCount(0);

                if (p != null) {

                    model.addRow(new Object[]{
                        p.getNama(),
                        p.getKategori(),
                        p.getHarga()
                    });

                } else {

                    JOptionPane.showMessageDialog(null,
                            "Produk tidak ditemukan");
                }
            }
        });
    }

    // METHOD TAMPIL DATA
    public void tampilkanData(ArrayList<Product> list) {

        model.setRowCount(0);

        for (Product p : list) {

            model.addRow(new Object[]{
                p.getNama(),
                p.getKategori(),
                p.getHarga()
            });
        }
    }

    // MAIN
    public static void main(String[] args) {

        new MainFrame().setVisible(true);
    }
}