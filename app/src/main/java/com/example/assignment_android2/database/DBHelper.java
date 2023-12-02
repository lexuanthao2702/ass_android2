package com.example.assignment_android2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "ANDROID2", null, 1); // tọa csdl
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sNguoiDung = "CREATE TABLE NGUOIDUNG(tendangnhap TEXT PRIMARY KEY, matkhau TEXT, hoten TEXT)";
        db.execSQL(sNguoiDung);
        String sSanPham = "CREATE TABLE SANPHAM(masp INTEGER PRIMARY KEY AUTOINCREMENT,tensp TEXT, giaban INTEGER,soluong INTEGER,hinhanh text)";
        db.execSQL(sSanPham);

        String dNguoiDung = "INSERT INTO NGUOIDUNG VALUES('thao','123','lê xuân thảo'),('cuyduyduc','123','duc'),('nguyentienphuoc','123','phuoc')";
        db.execSQL(dNguoiDung);
        String dSanPham = "INSERT INTO SANPHAM VALUES(1,'Phô Mai Que',100000,10,''),(2,'Trà Sữa',200000,10,''),(3,'Bánh',50000,10,'')";
        db.execSQL(dSanPham);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS NGUOIDUNG");
            db.execSQL("DROP TABLE IF EXISTS SANPHAM");
            onCreate(db);
        }
    }
}
