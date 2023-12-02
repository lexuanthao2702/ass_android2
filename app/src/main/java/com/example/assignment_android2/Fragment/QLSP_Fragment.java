package com.example.assignment_android2.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment_android2.Adapter.Adapter_Foob;
import com.example.assignment_android2.LoginActivity;
import com.example.assignment_android2.R;
import com.example.assignment_android2.dao.NguoiDungDAo;
import com.example.assignment_android2.model.Foob;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class QLSP_Fragment extends Fragment {
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private NguoiDungDAo nguoiDungDAo;

    private LoginActivity login = new LoginActivity();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_q_l_s_p_,container,false);
        //ánh xạ
        recyclerView = view.findViewById(R.id.Recyclerview);
        floatingActionButton = view.findViewById(R.id.floatAdd);

        nguoiDungDAo = new NguoiDungDAo(getContext());;
        loaddata();
        //add
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogAdd();

            }
        });

        return view;
    }
    private void loaddata(){
        ArrayList<Foob> list = nguoiDungDAo.getListFood();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        Adapter_Foob adapter_foob = new Adapter_Foob(getContext(),list);
        recyclerView.setAdapter(adapter_foob);
    }
    private void ShowDialogAdd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add,null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // bo góc adialog
        //xử lý chức năng
        final EditText edtSp = view.findViewById(R.id.edtSP);
        final EditText edtGiaBan = view.findViewById(R.id.edtGiaBan);
        final EditText edtSL = view.findViewById(R.id.edtSL);
        final Button btnAdd = view.findViewById(R.id.btnAdd);
        final Button btnCancel = view.findViewById(R.id.btnCancel);
        //add
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSp = edtSp.getText().toString();
                String gia = edtGiaBan.getText().toString();
                String soluong = edtSL.getText().toString();
                //validate
                if (login.vatlidateFom(edtSp,"product") || login.vatlidateFom(edtGiaBan,"Number") || login.vatlidateFom(edtSL,"Number")){
                    return;
                } else {
                    Foob foob = new Foob(tenSp, Integer.parseInt(gia), Integer.parseInt(soluong));
                    boolean check = nguoiDungDAo.addSanPham(foob);
                    if (check) {
                        Toast.makeText(getContext(), "Add Successfully", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                        loaddata();
                    } else {
                        Toast.makeText(getContext(), "Add Field", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        //cancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}