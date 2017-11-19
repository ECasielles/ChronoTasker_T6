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

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.db.repository.UserRepository;
import com.example.usuario.chronotasker.ui.about.AboutActivity;
import com.example.usuario.chronotasker.ui.task.TaskActivity;

/**
 * Clase Activity de la vista de registro, Signup, desde la que
 * el usuario crea una nueva cuenta para el acceso a la aplicación.
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 * @see android.support.v7.app.AppCompatActivity
 */
public class SignupActivity extends AppCompatActivity {
    TextInputEditText edtUser, edtEmail, edtPassword;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        edtUser = (TextInputEditText) ((TextInputLayout) findViewById(R.id.tilUser)).getEditText();
        edtEmail = (TextInputEditText) ((TextInputLayout) findViewById(R.id.tilEmail)).getEditText();
        edtPassword = (TextInputEditText) ((TextInputLayout) findViewById(R.id.tilPassword)).getEditText();
        btnSignup = (Button) findViewById(R.id.btnSignUp);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Faltaría presenter, adapter, repository, interactor, etc.
                if(UserRepository.getInstance().addUser(
                        edtUser.getText().toString(), edtPassword.getText().toString(),
                        edtEmail.getText().toString()))
                    navigateToHome();
                else
                    Snackbar.make(findViewById(R.id.btnLogin), "Credenciales no válidos", Snackbar.LENGTH_SHORT).show();
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
                startActivity(new Intent(SignupActivity.this, AboutActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void navigateToHome(){
        startActivity(new Intent(SignupActivity.this, TaskActivity.class));
        finish();
    }
}
