package model;

public class Borrow {

    private int idPinjam;
    private int idUser;
    private int idBuku;
    private String tanggalPinjam;

    public Borrow() {
    }

    public Borrow(int idPinjam, int idUser, int idBuku, String tanggalPinjam) {
        this.idPinjam = idPinjam;
        this.idUser = idUser;
        this.idBuku = idBuku;
        this.tanggalPinjam = tanggalPinjam;
    }

    public int getIdPinjam() {
        return idPinjam;
    }

    public void setIdPinjam(int idPinjam) {
        this.idPinjam = idPinjam;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdBuku() {
        return idBuku;
    }

    public void setIdBuku(int idBuku) {
        this.idBuku = idBuku;
    }

    public String getTanggalPinjam() {
        return tanggalPinjam;
    }

    public void setTanggalPinjam(String tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }

    public void displayInfo() {
        System.out.println("------------------------------");
        System.out.println("ID Pinjam       : " + idPinjam);
        System.out.println("ID User         : " + idUser);
        System.out.println("ID Buku         : " + idBuku);
        System.out.println("Tanggal Pinjam  : " + tanggalPinjam);
    }
}