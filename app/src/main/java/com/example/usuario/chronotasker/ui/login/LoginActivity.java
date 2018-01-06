package com.example.usuario.chronotasker.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.db.repository.UserRepository;
import com.example.usuario.chronotasker.ui.about.AboutActivity;
import com.example.usuario.chronotasker.ui.task.TaskActivity;

/**
 * Clase Activity de la vista principal, Login, desde la que
 * el usuario accede validando sus credenciales pudiendo recordarlos y
 * puede también registrarse accediendo a la vista de Registro.
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 * @see android.support.v7.app.AppCompatActivity
 */
public class LoginActivity extends AppCompatActivity {

    TextInputEditText edtUserData, edtPassword;
    TextView txvSignup;
    CheckBox chbRemember;
    Button btnLogin;
    boolean rememberUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: Shapes y personalizar vistas
        setContentView(R.layout.activity_login);
        edtUserData = (TextInputEditText) ((TextInputLayout) findViewById(R.id.tilUser)).getEditText();
        edtPassword = (TextInputEditText) ((TextInputLayout) findViewById(R.id.tilPassword)).getEditText();
        chbRemember = findViewById(R.id.chbRemember);
        btnLogin = findViewById(R.id.btnLogin);
        txvSignup = findViewById(R.id.txvSignup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UserRepository.getInstance().validateCredentials(
                        edtUserData.getText().toString(), edtPassword.getText().toString()))
                    navigateToHome();
                else
                    Snackbar.make(findViewById(R.id.btnLogin), "Credenciales no válidos", Snackbar.LENGTH_SHORT).show();
            }
        });
        chbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                rememberUser = b;
            }
        });
        txvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }


    /**
     * Infla el menú de la esquina superior derecha
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_activities_start, menu);
        return super.onCreateOptionsMenu(menu);
    }
    /**
     * Muestra las opciones del menú y su comportamiento
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_menu_about:
                startActivity(new Intent(LoginActivity.this, AboutActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void navigateToHome(){
        startActivity(new Intent(LoginActivity.this, TaskActivity.class));
        finish();
    }
}
