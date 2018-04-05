package com.example.usuario.chronotasker.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.usuario.chronotasker.App;
import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.model.User;
import com.example.usuario.chronotasker.data.prefs.PreferencesHelper;
import com.example.usuario.chronotasker.ui.about.AboutActivity;
import com.example.usuario.chronotasker.ui.home.HomeActivity;
import com.example.usuario.chronotasker.ui.signup.SignupActivity;

/**
 * Clase Activity de la vista principal, Login, desde la que
 * el usuario accede validando sus credenciales pudiendo recordarlos y
 * puede también registrarse accediendo a la vista de Registro.
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 * @see android.support.v7.app.AppCompatActivity
 */
public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    private TextInputEditText edtName, edtPassword;
    private CheckBox chbRemember;
    private Button btnLogin, btnSignup;
    private ViewGroup parent;
    private LoginContract.Presenter presenter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Guarda vista para Snackbar
        parent = findViewById(android.R.id.content);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Inicializa Presenter y lo asigna
        presenter = new LoginPresenter(this);

        //Inicializa vistas
        edtName = (TextInputEditText) ((TextInputLayout) findViewById(R.id.tilName)).getEditText();
        edtPassword = (TextInputEditText) ((TextInputLayout) findViewById(R.id.tilPassword)).getEditText();
        chbRemember = findViewById(R.id.chbRemember);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.txvSignup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.validateFields(edtName.getText().toString(), edtPassword.getText().toString());
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Comprueba que si hay una sesión guardada anterior para iniciar la aplicación
        if (App.getApp()
                .getmPreferencesHelper().getCurrentUserRemember())
            navigateToHome();
    }

    /**
     * Arranca la ActivityPrincipal, HomeActivity, y finaliza.
     */
    public void navigateToHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_start, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_menu_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void errorEmptyField() {
        Snackbar.make(parent, getResources().getString(R.string.error_name_empty), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void errorNameLengthInvalid() {
        Snackbar.make(parent, getResources().getString(R.string.error_name_length_invalid), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void errorPasswordLengthInvalid() {
        Snackbar.make(parent, getResources().getString(R.string.error_password_length_invalid), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void errorPasswordFormatInvalid() {
        Snackbar.make(parent, getResources().getString(R.string.error_password_format_invalid), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void loginUser() {
        presenter.loginUser(new User(
                -1,
                edtName.getText().toString(),
                "",
                edtPassword.getText().toString()
        ));
    }

    @Override
    public void onUserFound() {
        PreferencesHelper helper = App.getApp().getmPreferencesHelper();
        helper.setCurrentUserName(edtName.getText().toString());
        helper.setCurrentUserPassword(edtPassword.getText().toString());
        helper.setCurrentUserRemember(chbRemember.isChecked());
        navigateToHome();
    }

    @Override
    public void onDatabaseError(String message) {
        Snackbar.make(parent, message, Snackbar.LENGTH_SHORT).show();
    }

}
