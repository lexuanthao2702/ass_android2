package com.example.assignment_android2;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.assignment_android2.Adapter.Adapter_Foob;
import com.example.assignment_android2.Fragment.CaiDat_Fragment;
import com.example.assignment_android2.Fragment.GioiThieu_Fragment;
import com.example.assignment_android2.Fragment.QLSP_Fragment;
import com.example.assignment_android2.dao.NguoiDungDAo;
import com.example.assignment_android2.model.Foob;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
        private DrawerLayout drawerLayout;
        private Toolbar toolbar;
        private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ánh xaj
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toobar);
        navigationView = findViewById(R.id.navView);

        // setup toobar
        setSupportActionBar(toolbar); // đưa thuộc tính lên toobar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // hiện thị chức năng các nút
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setTitle("Quản Lý Sản Phẩm");

        //set fragmetn mặt định
        getSupportFragmentManager().beginTransaction().replace(R.id.linearlayout, new QLSP_Fragment()).commit();

        //nhán item navigation
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId() == R.id.mQLSP) {
                    fragment = new QLSP_Fragment();

                } else if (item.getItemId() == R.id.mGioiThieu) {
                    fragment = new GioiThieu_Fragment();

                } else if (item.getItemId() == R.id.mSetting) {
                    fragment = new CaiDat_Fragment();

                } else if (item.getItemId() == R.id.mLogout) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.linearlayout, fragment).commit();
                getSupportActionBar().setTitle(item.getTitle());
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    // nút nhấn toobar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    // xử lý back
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}