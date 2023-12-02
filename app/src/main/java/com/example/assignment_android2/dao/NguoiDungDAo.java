package com.example.assignment_android2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.assignment_android2.database.DBHelper;
import com.example.assignment_android2.model.Foob;

import java.util.ArrayList;

public class NguoiDungDAo {
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public NguoiDungDAo(Context context) {
        dbHelper = new DBHelper(context);
        database =dbHelper.getWritableDatabase();
    }
    // Login
    public boolean CheckLogin(String username, String password) {
        database = dbHelper.getReadableDatabase(); // lấy dử liệu

        Cursor cursor = database.rawQuery("SELECT * FROM NGUOIDUNG WHERE tendangnhap = ? AND matkhau = ?", new String[]{username, password});
        return cursor.getCount() > 0; // trả về giá trị true
    }
    // Sign up
    public boolean SignUp(String user, String pass, String name) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase(); // thêm dử liệu

        ContentValues values = new ContentValues();
        values.put("tendangnhap", user);
        values.put("matkhau", pass);
        values.put("hoten", name);

        long check = sqLiteDatabase.insert("NGUOIDUNG", null, values);
        return check != -1;
    }
    // forgot
    public String ForgotPassword(String Email){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase(); // thêm dử liệu
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT matkhau FROM NGUOIDUNG WHERE tendangnhap =?",new String[]{Email});
        if (cursor.getCount() > 0){
            cursor.moveToFirst(); // con trỏ lên đầu
            return cursor.getString(0);
        }else {
            return "";
        }
    }
    // lấy danh sách sản phẩm
    public ArrayList<Foob> getListFood() {
        ArrayList<Foob> list = new ArrayList<>();
        database = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = database.rawQuery("Select * from SANPHAM", null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    list.add(new Foob(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3)));
                }while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("TAG", e.getMessage());
        }
        return list;
    }
    public boolean delete(int masp){
        int row = database.delete("SANPHAM","masp =?",new String[]{String.valueOf(masp)});
        return row != -1;
    }
    // thêm sản phẩm
    public boolean addSanPham(Foob foob){
        ContentValues values = new ContentValues();
        values.put("tensp",foob.getContent());
        values.put("giaban",foob.getGia());
        values.put("soluong",foob.getSoLuong());

        long check = database.insert("SANPHAM",null,values);
        return check != 1; // check = true
    }
    // Update Sản Phẩm
    public boolean UpdateSanPham(Foob foob){
        ContentValues values = new ContentValues();
        values.put("tensp",foob.getContent());
        values.put("giaban",foob.getGia());
        values.put("soluong",foob.getSoLuong());
        int check = database.update("SANPHAM",values,"masp =?",new String[]{String.valueOf(foob.getMaSp())});
        if (check <= 0 ) return false;
        return true;
    }

}
