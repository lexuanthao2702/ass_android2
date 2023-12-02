package com.example.assignment_android2.model;

public class Foob {
    private int maSp;
    private String Content;
    private int Gia;
    private int SoLuong;
    private String hinhanh;

    public Foob() {
    }

    public Foob(String content, int gia, int soLuong, String hinhanh) {
        Content = content;
        Gia = gia;
        SoLuong = soLuong;
        this.hinhanh = hinhanh;
    }

    public Foob(int maSp, String content, int gia, int soLuong, String hinhanh) {
        this.maSp = maSp;
        Content = content;
        Gia = gia;
        SoLuong = soLuong;
        this.hinhanh = hinhanh;
    }

    public Foob(int maSp, String content, int gia, int soLuong) {
        this.maSp = maSp;
        Content = content;
        Gia = gia;
        SoLuong = soLuong;
    }

    public Foob(String content, int gia, int soLuong) {
        Content = content;
        Gia = gia;
        SoLuong = soLuong;
    }

    public int getMaSp() {
        return maSp;
    }

    public void setMaSp(int maSp) {
        this.maSp = maSp;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getGia() {
        return Gia;
    }

    public void setGia(int gia) {
        Gia = gia;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    @Override
    public String toString() {
        return "Foob{" +
                "maSp=" + maSp +
                ", Content='" + Content + '\'' +
                ", Gia=" + Gia +
                ", SoLuong=" + SoLuong +
                '}';
    }
}

