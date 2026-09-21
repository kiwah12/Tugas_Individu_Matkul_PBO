/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author MSI THIN 15
 */
public class Rokok extends Produk {
 
    public static final double MARGIN = 0.10;
    public static final double CUKAI_PER_BATANG = 250;
    public static final int BATAS_USIA = 18;
 
    private String merek;
    private int jumlahBatang;
 
    public Rokok(String kode, String nama, double hargaBeli, int stok,
                 String merek, int jumlahBatang) {
        super(kode, nama, hargaBeli, stok);
        this.merek = merek;
        this.jumlahBatang = jumlahBatang;
    }
 
    @Override
    public String getKategori() {
        return "Rokok";
    }

    @Override
    public double hitungHargaJual() {
        double harga = getHargaBeli() * (1 + MARGIN) + (CUKAI_PER_BATANG * jumlahBatang);
        return Math.ceil(harga / 100.0) * 100;
    }
 
    @Override
    public String getInfoTambahan() {
        return "Merek " + merek + ", isi " + jumlahBatang
                + " batang, min. usia " + BATAS_USIA + " tahun";
    }

    public boolean bolehDibeli(int usiaPembeli) {
        return usiaPembeli >= BATAS_USIA;
    }

    @Override
    public void tampilkanDetail() {
        super.tampilkanDetail(); 
        System.out.println("  Peringatan      : Dilarang menjual kepada anak di bawah umur!");
    }
 
    public String getMerek() { return merek; }
    public void setMerek(String merek) { this.merek = merek; }
 
    public int getJumlahBatang() { return jumlahBatang; }
    public void setJumlahBatang(int jumlahBatang) { this.jumlahBatang = jumlahBatang; }
}
