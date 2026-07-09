package model;

public class User extends Person {

    public User() {
        super();
    }

    public User(int idUser, String nama) {
        super(idUser, nama);
    }

    @Override
    public void displayInfo() {
        System.out.println("===== DATA USER =====");
        System.out.println("ID User : " + getIdUser());
        System.out.println("Nama    : " + getNama());
    }
}