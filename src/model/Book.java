package model;

public class Book {

    private int idBuku;
    private String judul;
    private String penulis;
    private int stok;

    public Book() {
    }

    public Book(int idBuku, String judul, String penulis, int stok) {
        this.idBuku = idBuku;
        this.judul = judul;
        this.penulis = penulis;
        this.stok = stok;
    }

    public int getIdBuku() {
        return idBuku;
    }

    public void setIdBuku(int idBuku) {
        this.idBuku = idBuku;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public void displayInfo() {
        System.out.println("------------------------------");
        System.out.println("ID Buku  : " + idBuku);
        System.out.println("Judul    : " + judul);
        System.out.println("Penulis  : " + penulis);
        System.out.println("Stok     : " + stok);
    }
}