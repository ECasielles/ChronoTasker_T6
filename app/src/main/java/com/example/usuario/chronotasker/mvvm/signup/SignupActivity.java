package com.example.usuario.chronotasker.mvvm.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.model.User;
import com.example.usuario.chronotasker.mvvm.about.AboutActivity;

/**
 * Clase Activity de la vista de registro, Signup, desde la que
 * el usuario crea una nueva cuenta para el acceso a la aplicación.
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 * @see android.support.v7.app.AppCompatActivity
 */
public class SignupActivity extends AppCompatActivity implements SignupContract.View {
    private TextInputEditText edtUser, edtEmail, edtPassword;
    private Button btnSignup;
    private SignupContract.Presenter presenter;
    private ViewGroup parent;
    private Toolbar toolbar;

    //TODO: Clase About debe estar mejor
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Guarda vista para Snackbar
        parent = findViewById(android.R.id.content);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtUser = (TextInputEditText) ((TextInputLayout) findViewById(R.id.tilName)).getEditText();
        edtEmail = (TextInputEditText) ((TextInputLayout) findViewById(R.id.tilEmail)).getEditText();
        edtPassword = (TextInputEditText) ((TextInputLayout) findViewById(R.id.tilPassword)).getEditText();
        btnSignup = findViewById(R.id.btnSignUp);

        presenter = new SignupPresenter(this);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.validateFields(
                        edtUser.getText().toString(),
                        edtEmail.getText().toString(),
                        edtPassword.getText().toString()
                );
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

    @Override
    public void addUser() {
        presenter.addUser(new User(
                -1,
                edtUser.getText().toString(),
                edtEmail.getText().toString(),
                edtPassword.getText().toString()
        ));
    }

    @Override
    public void onUserAdded() {
        finish();
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
    public void errorEmailFormatInvalid() {
        Snackbar.make(parent, getResources().getString(R.string.error_email_format_invalid), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDatabaseError(String message) {
        Snackbar.make(parent, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
