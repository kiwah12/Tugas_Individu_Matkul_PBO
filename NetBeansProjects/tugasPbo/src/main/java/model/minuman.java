/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author MSI THIN 15
 */
public class minuman extends Produk {
 
    public static final double MARGIN = 0.20;          // keuntungan 20%
    public static final double BIAYA_PENDINGINAN = 1000; // tambahan jika dingin
 
    private int volumeMl;
    private boolean dingin;
 
    public minuman(String kode, String nama, double hargaBeli, int stok,
                   int volumeMl, boolean dingin) {
        super(kode, nama, hargaBeli, stok);
        this.volumeMl = volumeMl;
        this.dingin = dingin;
    }
 
    @Override
    public String getKategori() {
        return "Minuman";
    }
 
    /** Harga jual = harga beli + margin 20% (+ biaya pendinginan bila dingin). */
    @Override
    public double hitungHargaJual() {
        double harga = getHargaBeli() * (1 + MARGIN);
        if (dingin) {
            harga += BIAYA_PENDINGINAN;
        }
        return Math.ceil(harga / 100.0) * 100;
    }
 
    @Override
    public String getInfoTambahan() {
        return volumeMl + " ml, " + (dingin ? "dingin (kulkas)" : "suhu ruang");
    }
 
    public int getVolumeMl() { return volumeMl; }
    public void setVolumeMl(int volumeMl) { this.volumeMl = volumeMl; }
 
    public boolean isDingin() { return dingin; }
    public void setDingin(boolean dingin) { this.dingin = dingin; }
}
