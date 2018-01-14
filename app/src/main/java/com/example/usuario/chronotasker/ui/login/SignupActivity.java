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
import android.widget.Toast;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.db.repository.UserRepository;
import com.example.usuario.chronotasker.data.prefs.ChronoTaskerApplication;
import com.example.usuario.chronotasker.data.prefs.PreferencesHelper;
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

    //TODO: Clase About debe estar mejor
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        edtUser = (TextInputEditText) ((TextInputLayout) findViewById(R.id.tilName)).getEditText();
        edtEmail = (TextInputEditText) ((TextInputLayout) findViewById(R.id.tilEmail)).getEditText();
        edtPassword = (TextInputEditText) ((TextInputLayout) findViewById(R.id.tilPassword)).getEditText();
        btnSignup = (Button) findViewById(R.id.btnSignUp);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UserRepository.getInstance().addUser(
                        edtUser.getText().toString(), edtPassword.getText().toString(),
                        edtEmail.getText().toString()))
                    navigateToHome();
                else
                    Snackbar.make(findViewById(R.id.btnLogin), "Credenciales no válidos", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_activity_start, menu);
        return super.onCreateOptionsMenu(menu);
    }

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
        showPreferences();
        finish();
    }

    private void showPreferences() {
        PreferencesHelper preferencesHelper =
                ((ChronoTaskerApplication) getApplicationContext()).getPreferencesHelper();
        preferencesHelper.setCurrentUserName(edtUser.getText().toString());
        String message = "Tu usuario de sesión es: " + preferencesHelper.getCurrentUserName();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
