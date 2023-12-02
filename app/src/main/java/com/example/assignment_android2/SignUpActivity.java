package com.example.assignment_android2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment_android2.dao.NguoiDungDAo;

public class SignUpActivity extends AppCompatActivity {
    private NguoiDungDAo nguoiDungDAo;

    private LoginActivity login = new LoginActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // ánh xạ
        EditText edtUse = findViewById(R.id.edUsername);
        EditText edPass = findViewById(R.id.edPassword);
        EditText edCfPass = findViewById(R.id.edConfimPass);
        EditText edFullName = findViewById(R.id.edtFullname);
        Button btnSiguup = findViewById(R.id.btnSignUp);
        Button btnBack = findViewById(R.id.btnBack);

        nguoiDungDAo = new NguoiDungDAo(this);

        btnSiguup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validate
                if (login.vatlidateFom(edtUse, "username") || login.vatlidateFom(edPass, "password") || login.vatlidateFom(edCfPass, "Comfim password") || login.vatlidateFom(edFullName, "full name")) {
                    return;
                } else {
                    String user = edtUse.getText().toString();
                    String pass = edPass.getText().toString();
                    String cfPass = edCfPass.getText().toString();
                    String fullname = edFullName.getText().toString();

                    if (!pass.equals(cfPass)) {
                        Toast.makeText(SignUpActivity.this, "check password again", Toast.LENGTH_SHORT).show();
                        edCfPass.setError("check password again");
                    } else {
                        boolean check = nguoiDungDAo.SignUp(user, pass, fullname);
                        if (check) { // check == true
                            Toast.makeText(SignUpActivity.this, "Sign Up Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Sign Up field", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}