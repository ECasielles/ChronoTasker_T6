package com.example.usuario.chronotasker.ui.about;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.usuario.chronotasker.R;

import mehdi.sakout.aboutpage.AboutPage;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.about_icon_email)
                .setDescription("Somos desarrolladores")
                .addEmail("enriquecasielles@gmail.com")
                .addWebsite("http://ecasielles.github.io/")
                .addFacebook("Enrique Casielles")
                .addTwitter("icenri")
                .addYoutube("Icenri")
                .addGitHub("ECasielles")
                .create();

        setContentView(aboutPage);
    }
}
