# Sistem Manajemen Toko Madura 

Tugas Individu Mata Kuliah **Pemrograman Berorientasi Objek**

----

## 1. Identitas Mahasiswa

| Keterangan | Isi |
|---|---|
| Nama | **Muhammad Risky Alpianur** |
| NIM | **2509116101** |
| Prodi | **Sistem Informasi** |
| Mata Kuliah | Pemrograman Berorientasi Objek |
| Studi Kasus | Sistem Manajemen Toko Madura |

---

## 2. Penjelasan Studi Kasus

Toko Madura adalah toko kelontong yang buka 24 jam dan menjual bermacam barang
kebutuhan harian. Pencatatan barang yang masih manual membuat pemilik toko sulit
mengetahui stok yang menipis, harga jual yang seharusnya, serta rekap penjualan.

Program ini dibuat untuk membantu pemilik toko mengelola data barang dagangan
melalui menu konsol. Barang di toko dikelompokkan menjadi tiga jenis yang punya
karakteristik dan aturan harga berbeda:

| Jenis | Atribut khusus | Aturan harga jual |
|---|---|---|
| makanan | tanggal kedaluwarsa, kemasan/curah | harga beli + margin 15% |
| minuman | volume (ml), dingin/suhu ruang | harga beli + margin 20% + Rp1.000 bila dingin |
| Rokok | merek, isi per bungkus | harga beli + margin 10% + cukai Rp250/batang, minimal usia pembeli 18 tahun |.

### Fitur Program

adapun untuk fitur program sebagai berikut:

1. **Create** – Tambah produk baru (memilih kategori makanan / minuman / Rokok).
2. **Read** – Menampilkan seluruh produk dalam bentuk tabel, melihat detail satu
   produk, dan mencari produk berdasarkan nama atau kode.
3. **Update** – Mengubah nama, harga beli, stok, serta atribut khusus tiap kategori.
4. **Delete** – Menghapus produk disertai konfirmasi.
5. Transaksi penjualan: mengurangi stok otomatis, validasi usia pembeli untuk
   rokok, dan mencetak struk beserta uang kembalian.
6. Laporan toko: jumlah produk per kategori, nilai persediaan, total pendapatan,
   dan daftar produk yang perlu di-*restock*.

---

## 3. Diagram Kelas & Hierarki Class

```
                    +--------------------------+
                    |    Produk (abstract)     |   <-- SUPER CLASS
                    +--------------------------+
                    | - kode : String          |
                    | - nama : String          |
                    | - hargaBeli : double     |
                    | - stok : int             |
                    +--------------------------+
                    | + getKategori() : String        (abstract)
                    | + hitungHargaJual() : double    (abstract)
                    | + getInfoTambahan() : String    (abstract)
                    | + kurangiStok(int) : boolean
                    | + getStatusStok() : String
                    | + tampilkanDetail() : void
                    +--------------------------+
                                 ^
            (extends)            |            (extends)
        +--------------------+---+---+--------------------+
        |                    |                            |
+-----------------+  +------------------+      +--------------------+
|    makanan      |  |     minuman      |      |       Rokok        |   <-- SUB CLASS
+-----------------+  +------------------+      +--------------------+
| - tglKadaluarsa |  | - volumeMl : int |      | - merek : String   |
| - kemasan       |  | - dingin : bool  |      | - jumlahBatang     |
+-----------------+  +------------------+      +--------------------+
| + hitungHarga   |  | + hitungHarga    |      | + hitungHargaJual()|
|   Jual() (15%)  |  |   Jual() (20%)   |      |   (10% + cukai)    |
|                 |  |                  |      | + bolehDibeli(int) |
+-----------------+  +------------------+      +--------------------+

        +-------------------------------+              +-----------+
        |           dataToko            |<>----------- |  Produk   |
        +-------------------------------+  1        *  +-----------+
        | - daftarProduk : List<Produk> |
        | - totalPendapatan : double    |
        +-------------------------------+
        | + tambahProduk(Produk)   [C]  |
        | + cariByKode / cariByNama [R] |
        | + ubahProduk(...)        [U]  |
        | + hapusProduk(String)    [D]  |
        | + jualProduk(String,int)      |
        +-------------------------------+
                        ^
                        | dipakai oleh
                 +--------------+
                 |     Main     |  (menu CLI / entry point)
                 +--------------+
```

Versi Mermaid:

```mermaid
classDiagram
    class Produk {
        <<abstract>>
        -String kode
        -String nama
        -double hargaBeli
        -int stok
        +getKategori()* String
        +hitungHargaJual()* double
        +getInfoTambahan()* String
        +kurangiStok(int) boolean
        +tampilkanDetail() void
    }
    class makanan {
        -String tanggalKadaluarsa
        -boolean kemasan
        +hitungHargaJual() double
    }
    class minuman {
        -int volumeMl
        -boolean dingin
        +hitungHargaJual() double
    }
    class Rokok {
        -String merek
        -int jumlahBatang
        +hitungHargaJual() double
        +bolehDibeli(int) boolean
    }
    class dataToko   {
        -List~Produk~ daftarProduk
        +tambahProduk(Produk) boolean
        +cariByKode(String) Produk
        +ubahProduk(...) boolean
        +hapusProduk(String) boolean
        +jualProduk(String, int) double
    }
    Produk <|-- makanan
    Produk <|-- minuman
    Produk <|-- Rokok
    dataToko o-- Produk
    Main ..> dataToko
```

### Daftar File

| File | Peran |
|---|---|
| `src/Produk.java` | Super-class abstrak, atribut & perilaku umum semua produk |
| `src/makanan.java` | Sub-class Produk |
| `src/minuman.java` | Sub-class Produk |
| `src/Rokok.java` | Sub-class Produk |
| `src/dataToko.java` | Pengelola data (operasi CRUD + transaksi + laporan) |
| `src/main.java` | Menu CLI / titik awal program |

---

## 4. Penjelasan Bagian Kode yang Menerapkan Inheritance

Relasi inheritance pada program ini adalah **Produk (super-class)** yang
diturunkan menjadi **makanan, minuman, dan Rokok (sub-class)** menggunakan
kata kunci extends.

**a. Super-class Produk (file src/Produk.java)**

```java
public abstract class Produk {
    private String kode;
    private String nama;
    private double hargaBeli;
    private int stok;

    public Produk(String kode, String nama, double hargaBeli, int stok) { ... }

    // Kontrak yang wajib diisi ulang oleh setiap sub-class
    public abstract String getKategori();
    public abstract double hitungHargaJual();
    public abstract String getInfoTambahan();

    // Perilaku umum yang langsung diwariskan
    public boolean kurangiStok(int jumlah) { ... }
    public void tampilkanDetail() { ... }
}
```

Kelas ini dibuat abstract karena "produk" hanyalah konsep umum; yang benar-benar
dijual di toko selalu berupa makanan, minuman, atau rokok.

**b. Sub-class memanggil constructor super-class dengan `super(...)`**

```java
public class minuman extends Produk {
    private int volumeMl;
    private boolean dingin;

    public minuman(String kode, String nama, double hargaBeli, int stok,
                   int volumeMl, boolean dingin) {
        super(kode, nama, hargaBeli, stok);   // atribut umum diurus super-class
        this.volumeMl = volumeMl;             // atribut khusus sub-class
        this.dingin = dingin;
    }
}
```

**c. Method overriding: aturan harga tiap sub-class berbeda**

```java
// makanan.java
@Override
public double hitungHargaJual() {
    return Math.ceil(getHargaBeli() * 1.15 / 100.0) * 100;      // margin 15%
}

// Minuman.java
@Override
public double hitungHargaJual() {
    double harga = getHargaBeli() * 1.20;                        // margin 20%
    if (dingin) harga += BIAYA_PENDINGINAN;                      // + biaya kulkas
    return Math.ceil(harga / 100.0) * 100;
}

// Rokok.java
@Override
public double hitungHargaJual() {
    return Math.ceil((getHargaBeli() * 1.10
            + CUKAI_PER_BATANG * jumlahBatang) / 100.0) * 100;   // margin + cukai
}
```

**d. Memanggil method milik induk dengan `super.namaMethod()`**

```java
// Rokok.java
@Override
public void tampilkanDetail() {
    super.tampilkanDetail();   // memakai ulang tampilan milik super-class
    System.out.println("  Peringatan      : Dilarang menjual kepada anak di bawah umur!");
}
```

**e. Polymorphism – satu koleksi menampung semua turunan**

```java
// TokoMadura.java
private final List<Produk> daftarProduk = new ArrayList<>();

// Main.java
for (Produk p : data) {
    System.out.println(p.toBarisTabel());  // otomatis memakai aturan harga
}                                          // sesuai kelas aslinya
```

Objek Makanan, Minuman, dan Rokok disimpan dalam satu List<Produk> yang
sama. Saat `hitungHargaJual()` dipanggil, Java otomatis menjalankan versi method
milik sub-class masing-masing (*dynamic method dispatch*).

**f. Pemakaian atribut khusus sub-class dengan instanceof**

```java
// Main.java - validasi usia hanya berlaku untuk Rokok
if (p instanceof Rokok r) {
    int usia = bacaInt("Usia pembeli   : ");
    if (!r.bolehDibeli(usia)) {
        System.out.println("[!] Transaksi ditolak. Pembeli di bawah 18 tahun.");
        return;
    }
}
```

---

## 5. Screenshot Program Berjalan

**`Menu Utama`**

<img width="1000" height="328" alt="image" src="https://github.com/user-attachments/assets/a8ba2b35-07b6-459c-b452-48abd64076b4" />

**`Daftar Produk` (Sebelum Tambah Produk)**

<img width="912" height="327" alt="image" src="https://github.com/user-attachments/assets/473d3b56-eb9d-4db0-a9c3-1416cf46afc0" />

**`Tambah Produk`**

<img width="927" height="312" alt="image" src="https://github.com/user-attachments/assets/e30d1224-aa76-4726-8149-79b9d05c7d19" />

**`Daftar Produk` (Sesudah Tambah Produk)**

<img width="1015" height="320" alt="image" src="https://github.com/user-attachments/assets/cf6ebb3f-fd23-406c-ae18-cdbad42a0c47" />

**`Ubah Produk`**

<img width="1167" height="617" alt="image" src="https://github.com/user-attachments/assets/1e6a273e-0774-4482-b2c4-465435e42515" />

**`Hapus Produk`**

<img width="797" height="167" alt="image" src="https://github.com/user-attachments/assets/36548461-00db-4047-800e-9ed7045e9891" />

**`Daftar Produk` (Sebelum dihapus Produk)**

<img width="1015" height="320" alt="image" src="https://github.com/user-attachments/assets/cf6ebb3f-fd23-406c-ae18-cdbad42a0c47" />

**`Daftar Produk` (Sesudah dihapus Produk)**

<img width="980" height="325" alt="image" src="https://github.com/user-attachments/assets/6d4453e8-a598-4924-ad17-fbe2ca7a42f8" />

**`struk & transaksi`**

<img width="1212" height="591" alt="image" src="https://github.com/user-attachments/assets/d3197fbb-a151-4537-90f3-77713d47744a" />

