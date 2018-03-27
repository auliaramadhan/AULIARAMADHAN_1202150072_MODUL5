package com.example.auliaramadhan.auliaramadhan_1202150072_modul5;

/**
 * Created by Aulia Ramadhan on 23/03/2018.
 * kelas ini untuk  membuat objek yang akan digunakan
 * untuk menyimpan data yang diambil dari database
 */


public class ToDoList {
    private int id;
    private String namaKegiatan;
    private String kegiatan;
    private int prioritas;

    public void setId(int id) {
        this.id = id;
    }

    public void setNamaKegiatan(String namaKegiatan) {
        this.namaKegiatan = namaKegiatan;
    }

    public void setKegiatan(String kegiatan) {
        this.kegiatan = kegiatan;
    }

    public void setPrioritas(int prioritas) {
        this.prioritas = prioritas;
    }

    public ToDoList() {
    }

    public String getNamaKegiatan() {
        return namaKegiatan;
    }

    public String getKegiatan() {
        return kegiatan;
    }

    public int getPrioritas() {
        return prioritas;
    }
}