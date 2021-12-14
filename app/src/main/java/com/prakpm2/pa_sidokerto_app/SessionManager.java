package com.prakpm2.pa_sidokerto_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String JenisUser = "JenisUser";
    public static final String FotoUser = "FotoUser";
    public static final String NamaUser = "NamaUser";
    public static final String IdUser = "IdUser";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String id, String nama, String jenis, String foto){
        editor.putBoolean(LOGIN, true);
        editor.putString(IdUser, id);
        editor.putString(NamaUser, nama);
        editor.putString(JenisUser, jenis);
        editor.putString(FotoUser, foto);
        editor.apply();
    }

    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

//    public void checkLogin(){
//
//        if (!this.isLoggin()){
//            Intent i = new Intent(context, RegisterPennyewa.class);
//            context.startActivity(i);
//            ((DashboardPenyewa) context).finish();
//        }
//    }

    public HashMap<String, String> getUserDetail(){

        HashMap<String, String> user = new HashMap<>();
        user.put(IdUser, sharedPreferences.getString(IdUser, null));
        user.put(NamaUser, sharedPreferences.getString(NamaUser, null));
        user.put(JenisUser, sharedPreferences.getString(JenisUser, null));
        user.put(FotoUser, sharedPreferences.getString(FotoUser, null));
        return user;
    }

    public void logoutGuru(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginMainActivity.class);
        context.startActivity(i);
        ((MainActivity) context).finish();
    }

    public void logoutSiswa(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginMainActivity.class);
        context.startActivity(i);
        ((DashboardSiswa) context).finish();
    }
}
