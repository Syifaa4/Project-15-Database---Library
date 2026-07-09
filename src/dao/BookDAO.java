package dao;

import database.DatabaseConnection;
import model.Book;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDAO {

    // =====================================================
    // Tambah Buku (Stored Procedure)
    // =====================================================
    public void tambahBuku(Book book) {

        String sql = "{CALL tambah_buku(?, ?, ?)}";

        try (
                Connection conn = DatabaseConnection.getConnection();
                CallableStatement stmt = conn.prepareCall(sql)
        ) {

            stmt.setString(1, book.getJudul());
            stmt.setString(2, book.getPenulis());
            stmt.setInt(3, book.getStok());

            stmt.execute();

            System.out.println("\n====================================");
            System.out.println("Data buku berhasil ditambahkan.");
            System.out.println("====================================");

        } catch (SQLException e) {

            System.out.println("\nGagal menambahkan buku.");
            System.out.println("Error : " + e.getMessage());

        }
    }

    // =====================================================
    // Tampilkan Semua Buku
    // =====================================================
    public void tampilBuku() {

        String sql = "SELECT * FROM books";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            System.out.println("\n========================= DAFTAR BUKU =========================");

            System.out.printf("%-5s %-30s %-25s %-5s%n",
                    "ID",
                    "Judul",
                    "Penulis",
                    "Stok");

            System.out.println("-----------------------------------------------------------------------");

            while (rs.next()) {

                Book book = new Book();

                book.setIdBuku(rs.getInt("id_buku"));
                book.setJudul(rs.getString("judul"));
                book.setPenulis(rs.getString("penulis"));
                book.setStok(rs.getInt("stok"));

                System.out.printf("%-5d %-30s %-25s %-5d%n",
                        book.getIdBuku(),
                        book.getJudul(),
                        book.getPenulis(),
                        book.getStok());

            }

            System.out.println("-----------------------------------------------------------------------");

        } catch (SQLException e) {

            System.out.println("\nGagal mengambil data buku.");
            System.out.println("Error : " + e.getMessage());

        }

    }

    // =====================================================
    // Total Buku (Function MySQL)
    // =====================================================
    public int totalBuku() {

        String sql = "SELECT total_buku() AS total";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {

            System.out.println("\nGagal mengambil total buku.");
            System.out.println("Error : " + e.getMessage());

        }

        return 0;
    }

    // =====================================================
    // Update Stok Buku (UPDATE)
    // =====================================================
    public void updateStokBuku(int idBuku, int stokBaru) {

        String sql = "UPDATE books SET stok = ? WHERE id_buku = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, stokBaru);
            stmt.setInt(2, idBuku);

            int result = stmt.executeUpdate();

            if (result > 0) {
                System.out.println("\nStok buku berhasil diperbarui.");
            } else {
                System.out.println("\nID Buku tidak ditemukan.");
            }

        } catch (SQLException e) {

            System.out.println("\nGagal memperbarui stok buku.");
            System.out.println("Error : " + e.getMessage());

        }
    }

    // =====================================================
    // Hapus Buku (DELETE)
    // =====================================================
    public void hapusBuku(int idBuku) {

        String sql = "DELETE FROM books WHERE id_buku = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, idBuku);

            int result = stmt.executeUpdate();

            if (result > 0) {
                System.out.println("\nBuku berhasil dihapus.");
            } else {
                System.out.println("\nID Buku tidak ditemukan.");
            }

        } catch (SQLException e) {

            System.out.println("\nGagal menghapus buku.");
            System.out.println("Error : " + e.getMessage());

        }
    }
}