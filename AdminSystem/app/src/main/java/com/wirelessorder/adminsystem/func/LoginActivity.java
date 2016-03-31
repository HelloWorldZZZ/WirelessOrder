package com.wirelessorder.adminsystem.func;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wirelessorder.adminsystem.R;
import com.wirelessorder.adminsystem.service.AdminService;
import com.wirelessorder.adminsystem.utils.Utils;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setActionBarTitle(R.string.log_in);
        initView();
    }

    private void initView() {
        final EditText editUser = (EditText) findViewById(R.id.userName);
        final EditText editPassword = (EditText) findViewById(R.id.password);
        Button loginBtn = (Button) findViewById(R.id.login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.hasNetwork(LoginActivity.this)) {
                    return;
                }
                String userName = editUser.getText().toString();
                String password = editPassword.getText().toString();
                if(userName.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, getString(R.string.login_mention),
                            Toast.LENGTH_SHORT).show();
                } else {
                    AdminService adminService = new AdminService(LoginActivity.this);
                    if (adminService.isAdminMatch(userName, password)) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    } else {
                        Toast.makeText(LoginActivity.this, getString(R.string.login_fail),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}
