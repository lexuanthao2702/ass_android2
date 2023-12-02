package com.example.assignment_android2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment_android2.dao.NguoiDungDAo;
import com.example.assignment_android2.utill.SendMail;

public class LoginActivity extends AppCompatActivity {
    private NguoiDungDAo nguoiDungDAo;
    private SendMail sendMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // ánh xạ
        EditText edtuser = findViewById(R.id.edUsername);
        EditText edPass = findViewById(R.id.edPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView tvFOrgot = findViewById(R.id.tvForgotpassword);
        TextView tvSignup = findViewById(R.id.tvSignUp);

        nguoiDungDAo = new NguoiDungDAo(this);
        sendMail = new SendMail();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validate
                if (vatlidateFom(edtuser,"username") || vatlidateFom(edPass,"password")){
                    return;
                }else {
                    String use = edtuser.getText().toString();
                    String pass = edPass.getText().toString();

                    boolean check = nguoiDungDAo.CheckLogin(use,pass);

                    if (check){ // check == true
                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    }else{
                        Toast.makeText(LoginActivity.this, "Login Field", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        tvFOrgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogFogot();
            }
        });
    }
    private void ShowDialogFogot(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_forgot,null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        // ánh xạ
        EditText edtEmail = view.findViewById(R.id.edEmail);
        Button btnGui = view.findViewById(R.id.btnSend);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        // send Mail
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String matkhau = nguoiDungDAo.ForgotPassword(email);
                if (matkhau.equals("")){
                    Toast.makeText(LoginActivity.this, "Không tìm thấy tài khoản", Toast.LENGTH_SHORT).show();
                }else {
                    sendMail.Send(LoginActivity.this,email,"Lấy Lại Mật khẩu","Mật Khẩu Của bản là: "+matkhau);
                    alertDialog.dismiss();
                }
            }
        });
    }

    public boolean vatlidateFom(EditText editText,String fileName){
        String text = editText.getText().toString().trim();
        if (text.isEmpty()){
            editText.setError("Please enter "+fileName);
             return true;
        }
        return false;

    }
}