package service;

import dao.BookDAO;
import dao.BorrowDAO;
import database.DatabaseConnection;
import exception.CustomException;
import model.Book;
import model.Borrow;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import java.util.Scanner;

public class MenuService {

    private final Scanner scanner = new Scanner(System.in);
    private final BookDAO bookDAO = new BookDAO();
    private final BorrowDAO borrowDAO = new BorrowDAO();

    // =====================================================
    // START APLIKASI
    // =====================================================
    public void start() {

        boolean running = true;

        while (running) {

            try {

                tampilkanMenuUtama();
                int pilihan = bacaPilihan();

                switch (pilihan) {

                    case 1:
                        menuKelolaBuku();
                        break;

                    case 2:
                        menuPeminjaman();
                        break;

                    case 3:
                        running = false;
                        System.out.println("\nTerima kasih telah menggunakan LibraryCLI.");
                        break;

                    default:
                        throw new CustomException("Pilihan tidak tersedia. Silakan pilih 1-3.");
                }

            } catch (CustomException e) {

                System.out.println("\n[ERROR] " + e.getMessage());

            } catch (Exception e) {

                System.out.println("\n[ERROR TIDAK TERDUGA] " + e.getMessage());

            }

        }

        scanner.close();
    }

    // =====================================================
    // MENU UTAMA
    // =====================================================
    private void tampilkanMenuUtama() {
        System.out.println("\n========================================");
        System.out.println("     SISTEM MANAJEMEN PERPUSTAKAAN");
        System.out.println("========================================");
        System.out.println("1. Kelola Buku");
        System.out.println("2. Peminjaman");
        System.out.println("3. Keluar");
        System.out.println("========================================");
        System.out.print("Pilih menu : ");
    }

    // =====================================================
    // SUBMENU KELOLA BUKU
    // =====================================================
    private void menuKelolaBuku() {

        boolean back = false;

        while (!back) {

            try {

                System.out.println("\n---------- KELOLA BUKU ----------");
                System.out.println("1. Tambah Buku");
                System.out.println("2. Lihat Daftar Buku");
                System.out.println("3. Kembali");
                System.out.print("Pilih menu : ");

                int pilihan = bacaPilihan();

                switch (pilihan) {

                    case 1:
                        prosesTambahBuku();
                        break;

                    case 2:
                        prosesLihatBuku();
                        break;

                    case 3:
                        back = true;
                        break;

                    default:
                        throw new CustomException("Pilihan tidak tersedia. Silakan pilih 1-3.");
                }

            } catch (CustomException e) {

                System.out.println("\n[ERROR] " + e.getMessage());

            }

        }
    }

    // =====================================================
    // SUBMENU PEMINJAMAN
    // =====================================================
    private void menuPeminjaman() {

        boolean back = false;

        while (!back) {

            try {

                System.out.println("\n---------- PEMINJAMAN ----------");
                System.out.println("1. Pinjam Buku");
                System.out.println("2. Lihat Riwayat");
                System.out.println("3. Kembali");
                System.out.print("Pilih menu : ");

                int pilihan = bacaPilihan();

                switch (pilihan) {

                    case 1:
                        prosesPinjamBuku();
                        break;

                    case 2:
                        borrowDAO.tampilRiwayat();
                        break;

                    case 3:
                        back = true;
                        break;

                    default:
                        throw new CustomException("Pilihan tidak tersedia. Silakan pilih 1-3.");
                }

            } catch (CustomException e) {

                System.out.println("\n[ERROR] " + e.getMessage());

            }

        }
    }

    // =====================================================
    // PROSES TAMBAH BUKU
    // =====================================================
    private void prosesTambahBuku() throws CustomException {

        System.out.println("\n----- TAMBAH BUKU -----");

        System.out.print("Judul   : ");
        String judul = scanner.nextLine().trim();

        if (judul.isEmpty()) {
            throw new CustomException("Judul tidak boleh kosong.");
        }

        System.out.print("Penulis : ");
        String penulis = scanner.nextLine().trim();

        if (penulis.isEmpty()) {
            throw new CustomException("Penulis tidak boleh kosong.");
        }

        int stok = bacaAngka("Stok    : ");

        if (stok <= 0) {
            throw new CustomException("Stok harus lebih dari 0.");
        }

        Book book = new Book();
        book.setJudul(judul);
        book.setPenulis(penulis);
        book.setStok(stok);

        bookDAO.tambahBuku(book);
    }

    // =====================================================
    // PROSES LIHAT DAFTAR BUKU (memanggil Function total_buku())
    // =====================================================
    private void prosesLihatBuku() {

        bookDAO.tampilBuku();

        int total = bookDAO.totalBuku();

        System.out.println("Total seluruh buku di perpustakaan : " + total);
    }

    // =====================================================
    // PROSES PINJAM BUKU
    // =====================================================
    private void prosesPinjamBuku() throws CustomException {

        System.out.println("\n----- PINJAM BUKU -----");

        System.out.print("ID Buku       : ");
        int idBuku = bacaAngka("");

        if (!bukuTersedia(idBuku)) {
            throw new CustomException("ID Buku tidak ditemukan atau stok habis.");
        }

        System.out.print("Nama Peminjam : ");
        String nama = scanner.nextLine().trim();

        if (nama.isEmpty()) {
            throw new CustomException("Nama peminjam tidak boleh kosong.");
        }

        int idUser = getOrCreateUserId(nama);

        User user = new User(idUser, nama);
        user.displayInfo();

        Borrow borrow = new Borrow();
        borrow.setIdUser(idUser);
        borrow.setIdBuku(idBuku);
        borrow.setTanggalPinjam(LocalDate.now().toString());

        borrowDAO.pinjamBuku(borrow);
    }

    // =====================================================
    // CEK KETERSEDIAAN BUKU BERDASARKAN ID
    // =====================================================
    private boolean bukuTersedia(int idBuku) throws CustomException {

        String sql = "SELECT stok FROM books WHERE id_buku = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, idBuku);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("stok") > 0;
                }

                return false;
            }

        } catch (SQLException e) {
            throw new CustomException("Gagal memeriksa data buku: " + e.getMessage());
        }
    }

    // =====================================================
    // CARI id_user BERDASARKAN NAMA, JIKA BELUM ADA MAKA DIBUAT
    // =====================================================
    private int getOrCreateUserId(String nama) throws CustomException {

        String cariSql = "SELECT id_user FROM users WHERE nama = ?";
        String insertSql = "INSERT INTO users (nama) VALUES (?)";

        try (Connection conn = DatabaseConnection.getConnection()) {

            try (PreparedStatement cariStmt = conn.prepareStatement(cariSql)) {

                cariStmt.setString(1, nama);

                try (ResultSet rs = cariStmt.executeQuery()) {

                    if (rs.next()) {
                        return rs.getInt("id_user");
                    }

                }

            }

            try (PreparedStatement insertStmt = conn.prepareStatement(
                    insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                insertStmt.setString(1, nama);
                insertStmt.executeUpdate();

                try (ResultSet keys = insertStmt.getGeneratedKeys()) {

                    if (keys.next()) {
                        return keys.getInt(1);
                    }

                }

            }

            throw new CustomException("Gagal mendapatkan ID user.");

        } catch (SQLException e) {
            throw new CustomException("Gagal memproses data user: " + e.getMessage());
        }
    }

    // =====================================================
    // HELPER INPUT
    // =====================================================
    private int bacaPilihan() {

        while (true) {

            try {

                int pilihan = Integer.parseInt(scanner.nextLine().trim());
                return pilihan;

            } catch (NumberFormatException e) {

                System.out.print("Input harus berupa angka. Coba lagi : ");

            }

        }
    }

    private int bacaAngka(String label) {

        while (true) {

            try {

                if (!label.isEmpty()) {
                    System.out.print(label);
                }

                return Integer.parseInt(scanner.nextLine().trim());

            } catch (NumberFormatException e) {

                System.out.print("Input harus berupa angka. Coba lagi : ");

            }

        }
    }

}