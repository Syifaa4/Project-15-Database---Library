# LibraryCLI — Sistem Manajemen Perpustakaan Sederhana

Aplikasi Command Line Interface (CLI) untuk mengelola data buku dan proses peminjaman buku, dibangun menggunakan **Java** dan **MySQL**. Project ini dibuat untuk memenuhi tugas mata kuliah **Pemrograman Berorientasi Objek (PBO)**, dengan menerapkan seluruh konsep OOP serta fitur database dasar dan lanjutan.

## Deskripsi

Pengguna dapat menambahkan data buku, melihat daftar buku, melakukan peminjaman buku, serta melihat riwayat peminjaman. Seluruh data disimpan secara permanen di database MySQL.

## Teknologi yang Digunakan

- **Bahasa**: Java
- **Database**: MySQL (XAMPP)
- **Konektor**: MySQL Connector/J (JDBC)
- **IDE**: Visual Studio Code

## Struktur Project

```
project/
├── lib/
│   └── mysql-connector-j-x.x.x.jar
├── src/
│   ├── main/
│   │   └── Main.java
│   ├── model/
│   │   ├── Person.java
│   │   ├── User.java
│   │   ├── Book.java
│   │   └── Borrow.java
│   ├── database/
│   │   └── DatabaseConnection.java
│   ├── dao/
│   │   ├── BookDAO.java
│   │   └── BorrowDAO.java
│   ├── service/
│   │   └── MenuService.java
│   └── exception/
│       └── CustomException.java
├── database.sql
└── README.md
```

## Konsep PBO yang Diimplementasikan

| Konsep | Implementasi |
|---|---|
| Class | `Book`, `Person`, `User`, `Borrow`, `DatabaseConnection`, `BookDAO`, `BorrowDAO`, `MenuService`, `Main` |
| Object | Objek `Book`, `User`, `Borrow` dibuat saat program berjalan |
| Inheritance | `User` mewarisi `Person` |
| Polymorphism | Method `displayInfo()` di-override pada `User` |
| Encapsulation | Seluruh atribut `private`, diakses lewat getter/setter |
| Package | `model`, `database`, `dao`, `service`, `main`, `exception` |
| Exception Handling | `CustomException` + try-catch untuk koneksi database, input, dan query SQL |

## Fitur Database

### Database Dasar
- `INSERT` — tambah buku, tambah user, tambah peminjaman
- `SELECT` — lihat daftar buku, lihat riwayat peminjaman
- `UPDATE` — update stok buku (tersedia di `BookDAO`)
- `DELETE` — hapus buku (tersedia di `BookDAO`)

### Database Lanjutan
- **Stored Procedure** `tambah_buku` — menambahkan data buku
- **Function** `total_buku()` — mengembalikan jumlah total buku
- **Trigger** — mengurangi stok buku otomatis setelah peminjaman
- **View** `view_peminjaman` — menampilkan gabungan data buku, user, dan peminjaman

## Struktur Database

**Database**: `library_db`

| Tabel | Kolom |
|---|---|
| `books` | `id_buku` (AUTO_INCREMENT), `judul`, `penulis`, `stok` |
| `users` | `id_user` (AUTO_INCREMENT), `nama` |
| `borrow` | `id_pinjam` (AUTO_INCREMENT), `id_user`, `id_buku`, `tanggal_pinjam` |

## Struktur Menu

```
Menu Utama
├── 1. Kelola Buku
│   ├── 1. Tambah Buku
│   ├── 2. Lihat Daftar Buku
│   └── 3. Kembali
├── 2. Peminjaman
│   ├── 1. Pinjam Buku
│   ├── 2. Lihat Riwayat
│   └── 3. Kembali
└── 3. Keluar
```

## Cara Menjalankan

### 1. Persiapan
- Pastikan **XAMPP** aktif dan **MySQL** sudah running.
- Import `database.sql` ke MySQL untuk membuat database `library_db` beserta tabel, procedure, function, trigger, dan view.
- Pastikan file `mysql-connector-j-x.x.x.jar` ada di folder `lib/`.

### 2. Compile
```bash
javac -cp "lib/*" -d bin src/main/*.java src/model/*.java src/dao/*.java src/database/*.java src/service/*.java src/exception/*.java
```

### 3. Jalankan
Windows:
```bash
java -cp "bin;lib/*" main.Main
```

macOS/Linux:
```bash
java -cp "bin:lib/*" main.Main
```

## Konfigurasi Database

Koneksi database diatur di `src/database/DatabaseConnection.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/library_db";
private static final String USER = "root";
private static final String PASSWORD = "";
```

Sesuaikan `USER` dan `PASSWORD` jika konfigurasi MySQL kamu berbeda dari default XAMPP.

## Kontributor
𓍢🌷͙֒Syifa Nurul Afifah (20240040286)

## Mata Kuliah
Pemrograman Berorientasi Objek (PBO)