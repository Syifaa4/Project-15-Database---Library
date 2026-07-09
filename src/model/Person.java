package model;

public class Person {

    private int idUser;
    private String nama;

    public Person() {
    }

    public Person(int idUser, String nama) {
        this.idUser = idUser;
        this.nama = nama;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void displayInfo() {
        System.out.println("Nama : " + nama);
    }
}