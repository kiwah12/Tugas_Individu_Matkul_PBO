/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author MSI THIN 15
 */
public class makanan extends Produk {
 
    public static final double MARGIN = 0.15;
 
    private String tanggalKadaluarsa; 
    private boolean kemasan;          
 
    public makanan(String kode, String nama, double hargaBeli, int stok,
                   String tanggalKadaluarsa, boolean kemasan) {
        super(kode, nama, hargaBeli, stok);
        this.tanggalKadaluarsa = tanggalKadaluarsa;
        this.kemasan = kemasan;
    }
 
    @Override
    public String getKategori() {
        return "Makanan";
    }

    @Override
    public double hitungHargaJual() {
        double harga = getHargaBeli() * (1 + MARGIN);
        return Math.ceil(harga / 100.0) * 100;
    }
 
    @Override
    public String getInfoTambahan() {
        return (kemasan ? "Kemasan" : "Curah") + ", exp: " + tanggalKadaluarsa;
    }
 
    public String getTanggalKadaluarsa() { return tanggalKadaluarsa; }
    public void setTanggalKadaluarsa(String tanggalKadaluarsa) {
        this.tanggalKadaluarsa = tanggalKadaluarsa;
    }
 
    public boolean isKemasan() { return kemasan; }
    public void setKemasan(boolean kemasan) { this.kemasan = kemasan; }
}
 
