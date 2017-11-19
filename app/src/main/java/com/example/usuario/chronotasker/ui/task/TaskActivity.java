package com.example.usuario.chronotasker.ui.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.ui.about.AboutActivity;
import com.example.usuario.chronotasker.ui.settings.AccountSettingsActivity;
import com.example.usuario.chronotasker.ui.settings.GeneralSettingsActivity;

public class TaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
    }


    /**
     * Infla el menú de la esquina superior derecha
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_activity_main, menu);
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
            case R.id.action_menu_general_settings:
                startActivity(new Intent(TaskActivity.this, GeneralSettingsActivity.class));
                break;
            case R.id.action_menu_account_settings:
                startActivity(new Intent(TaskActivity.this, AccountSettingsActivity.class));
                break;
            case R.id.action_menu_about:
                startActivity(new Intent(TaskActivity.this, AboutActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
