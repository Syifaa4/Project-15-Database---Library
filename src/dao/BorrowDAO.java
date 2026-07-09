package dao;

import database.DatabaseConnection;
import model.Borrow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowDAO {

    // =====================================
    // PINJAM BUKU
    // =====================================
    public void pinjamBuku(Borrow borrow) {

        String sql = "INSERT INTO borrow (id_user, id_buku, tanggal_pinjam) VALUES (?, ?, ?)";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, borrow.getIdUser());
            stmt.setInt(2, borrow.getIdBuku());
            stmt.setString(3, borrow.getTanggalPinjam());

            int result = stmt.executeUpdate();

            if (result > 0) {
                System.out.println("\n====================================");
                System.out.println("Peminjaman berhasil.");
                System.out.println("Stok buku telah diperbarui oleh Trigger.");
                System.out.println("====================================");
            }

        } catch (SQLException e) {

            System.out.println("\nGagal melakukan peminjaman.");
            System.out.println("Error : " + e.getMessage());

        }

    }

    // =====================================
    // TAMPILKAN RIWAYAT PEMINJAMAN
    // =====================================
    public void tampilRiwayat() {

        String sql = "SELECT * FROM view_peminjaman";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            System.out.println("\n========================= RIWAYAT PEMINJAMAN =========================");

            System.out.printf("%-10s %-20s %-30s %-15s%n",
                    "ID",
                    "Nama",
                    "Judul Buku",
                    "Tanggal");

            System.out.println("---------------------------------------------------------------------");

            while (rs.next()) {

                System.out.printf("%-10d %-20s %-30s %-15s%n",
                        rs.getInt("id_pinjam"),
                        rs.getString("nama"),
                        rs.getString("judul"),
                        rs.getString("tanggal_pinjam"));

            }

            System.out.println("---------------------------------------------------------------------");

        } catch (SQLException e) {

            System.out.println("\nGagal mengambil data riwayat.");
            System.out.println("Error : " + e.getMessage());

        }

    }

}