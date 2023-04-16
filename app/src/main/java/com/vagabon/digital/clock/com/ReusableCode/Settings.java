package com.vagabon.digital.clock.com.ReusableCode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Settings {
    public void settings_option(Context context, String mode, String key, Boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(mode, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean settings_check(Context context, String mode, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(mode, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    public void set_theme(Context context, String mode, String key, int position) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(mode, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, position);
        editor.apply();

    }

    public int get_theme(Context context, String mode, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(mode, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }
}
