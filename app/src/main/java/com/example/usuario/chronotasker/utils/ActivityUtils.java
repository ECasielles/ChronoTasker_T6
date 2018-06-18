package com.example.usuario.chronotasker.utils;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import static android.support.v4.util.Preconditions.checkNotNull;


/**
 * Provee de métodos para ayudar a las Activities a cargar su UI.
 */
public class ActivityUtils {

    @SuppressLint("RestrictedApi")
    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        fragmentManager
                .beginTransaction()
                .add(frameId, fragment)
                .commit();
    }

    @SuppressLint("RestrictedApi")
    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, String tag) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        fragmentManager
                .beginTransaction()
                .add(fragment, tag)
                .commit();
    }

    @SuppressLint("RestrictedApi")
    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId, String tag) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        fragmentManager
                .beginTransaction()
                .add(frameId, fragment, tag)
                .commit();
    }

    @SuppressLint("RestrictedApi")
    public static void replaceFragmentInActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        fragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(frameId, fragment)
                .commit();
    }

    @SuppressLint("RestrictedApi")
    public static void replaceFragmentInActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId, String tag) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        fragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(frameId, fragment, tag)
                .commit();
    }

}

