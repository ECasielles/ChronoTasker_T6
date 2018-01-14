package com.example.usuario.chronotasker.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.prefs.ChronoTaskerApplication;
import com.example.usuario.chronotasker.data.prefs.PreferencesHelper;
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
public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    private static final int REQUEST_CODE_OK = 0;
    private static final int RESULT_CODE_LOGIN = 1;
    private static final int RESULT_CODE_CANCEL = 2;
    private TextInputEditText edtName, edtPassword;
    private CheckBox chbRemember;
    private Button btnLogin, btnSignup;
    private ViewGroup parent;
    private LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Guarda vista para Snackbar
        parent = findViewById(android.R.id.content);

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
                presenter.validate(edtName.getText().toString(), edtPassword.getText().toString(), chbRemember.isChecked());
                navigateToHome();
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(LoginActivity.this, SignupActivity.class), REQUEST_CODE_OK);
            }
        });
    }

    //TODO: onActivityResult de SignupActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_OK)
            switch (resultCode) {
                case RESULT_CODE_LOGIN:
                    break;
                case RESULT_CODE_CANCEL:
                    break;
            }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Comprueba que si hay una sesión guardada anterior para iniciar la aplicación
        if (ChronoTaskerApplication.getContext()
                .getPreferencesHelper().getCurrentUserRemember())
            navigateToHome();
    }

    /**
     * Arranca la ActivityPrincipal, TaskActivity, y finaliza.
     */
    public void navigateToHome() {
        startActivity(new Intent(this, TaskActivity.class));
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

    //COMUNICACION CON LOGINPRESENTER
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
    public void addUserPreferences(String name, String password, Boolean remember) {
        PreferencesHelper helper = ChronoTaskerApplication.getContext().getPreferencesHelper();
        helper.setCurrentUserName(name);
        helper.setCurrentUserPassword(password);
        helper.setCurrentUserRemember(remember);
    }

}
