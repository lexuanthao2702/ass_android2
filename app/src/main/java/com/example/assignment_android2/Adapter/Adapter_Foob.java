package com.example.assignment_android2.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_android2.Fragment.QLSP_Fragment;
import com.example.assignment_android2.R;
import com.example.assignment_android2.dao.NguoiDungDAo;
import com.example.assignment_android2.model.Foob;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class Adapter_Foob extends RecyclerView.Adapter<Adapter_Foob.ViewHolderFood> {
    private Context context;
    private ArrayList<Foob> list;
    NguoiDungDAo nguoiDungDAo;

    public Adapter_Foob(Context context, ArrayList<Foob> list) {
        this.context = context;
        this.list = list;
        nguoiDungDAo = new NguoiDungDAo(context);
    }

    @NonNull
    // hàm tạo view
    @Override
    public ViewHolderFood onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rcv, parent, false);
        return new ViewHolderFood(view);
    }

    // hàm gán dử liệu
    @Override
    public void onBindViewHolder(@NonNull ViewHolderFood holder, @SuppressLint("RecyclerView") int position) {
        // set dử liệu
        holder.tvContent.setText(list.get(position).getContent());
        NumberFormat numberFormat = new DecimalFormat("#,###");
        double myNumber = list.get(position).getGia();
        String formattedNumber = numberFormat.format(myNumber);
        holder.tvGia.setText(formattedNumber + " VND");
        holder.tvSoLuong.setText(String.valueOf(list.get(position).getSoLuong()));

        // delete
        holder.tvDetele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int masp = list.get(holder.getAdapterPosition()).getMaSp();
                boolean check = nguoiDungDAo.delete(masp);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete From");
                builder.setIcon(R.drawable.warning_icon);
                builder.setMessage("Warning: Are You Sure You Want To Delete It?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (check) {
                            list.clear();
                            list = nguoiDungDAo.getListFood();
                            notifyItemRemoved(holder.getAdapterPosition());
                            Toast.makeText(context, "Delete Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Delete Field", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        // Update SP
        holder.tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUpdate(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        //Set số lượng item hiển thị lên RecyclerView
        return list.size();
    }

    public static class ViewHolderFood extends RecyclerView.ViewHolder {
        TextView tvContent, tvGia, tvSoLuong, tvUpdate,     tvDetele;
        ImageView imageView;

        public ViewHolderFood(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv1);
            tvGia = itemView.findViewById(R.id.tvGia);
            tvSoLuong = itemView.findViewById(R.id.tvSL);
            tvUpdate = itemView.findViewById(R.id.btnUpdate);
            tvDetele = itemView.findViewById(R.id.btnDelete);
            imageView = itemView.findViewById(R.id.imgView);

        }
    }

    private void showDialogUpdate(Foob foob) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.alertdialog_form_update, null);
        builder.setView(view);

        //hiển thị
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // bo góc adialog

        //ánh xạ
        EditText tensp = view.findViewById(R.id.edtSP);
        EditText giaban = view.findViewById(R.id.edtGiaBan);
        EditText soluong = view.findViewById(R.id.edtSL);
        Button btnUpdate = view.findViewById(R.id.btnUpdate);
        Button btncCancel = view.findViewById(R.id.btnCancel);

        // đưa dử liệu cần sửa lên edt
        tensp.setText(foob.getContent());
        giaban.setText(String.valueOf(foob.getGia()));
        soluong.setText(String.valueOf(foob.getSoLuong()));

        // Cancel
        btncCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        //Update
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int masp = foob.getMaSp();
                String edttensp = tensp.getText().toString();
                String edtgiaban = giaban.getText().toString();
                String edtsoluong = soluong.getText().toString();

                if (edttensp.isEmpty() || edtgiaban.isEmpty() || edtsoluong.isEmpty()) {
                    Toast.makeText(context, "The data field cannot be Empty", Toast.LENGTH_SHORT).show();
                } else {
                    Foob foob1 = new Foob(masp, edttensp, Integer.parseInt(edtgiaban), Integer.parseInt(edtsoluong));
                    boolean check = nguoiDungDAo.UpdateSanPham(foob1);
                    if (check) {
                        Toast.makeText(context, "Update Successfully", Toast.LENGTH_SHORT).show();
                        list.clear();
                        list = nguoiDungDAo.getListFood();
                        notifyDataSetChanged();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(context, "Update Field", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}