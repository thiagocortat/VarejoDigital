package com.varejodigital.model;

import com.google.gson.annotations.SerializedName;
import com.varejodigital.ParseApplication;
import com.varejodigital.utilities.Serializabler;

import net.grandcentrix.tray.TrayAppPreferences;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Thiago Cortat on 11/07/15.
 */
public class User implements Serializable {

    private static final String USER_KEY = "user_current_key";

    public static final String ROLE_ADMINISTRATOR   = "ADMINISTRADOR";
    public static final String ROLE_MANAGER          = "GERENTE";
    public static final String ROLE_STORE_KEEPER    = "LOJISTA";
    public static final String ROLE_AUDITOR         = "AUDITOR";

    /**
     * list : ["ADMINISTRADOR","LOJISTA"]
     */
    @SerializedName("list")
    private List<String> roles;

    public static User getCurrentInstance() {
        TrayAppPreferences appPreferences = new TrayAppPreferences(ParseApplication.getAppContext());
        String userString = appPreferences.getString(USER_KEY, null);
        return (userString != null ? (User) Serializabler.toObject(userString) : null);
    }

    public static void saveCurrentUser(User user) {
        TrayAppPreferences appPreferences = new TrayAppPreferences(ParseApplication.getAppContext());
        appPreferences.put(USER_KEY, Serializabler.toString(user));
    }

    public static void resetCurrentUser() {

        TrayAppPreferences appPreferences = new TrayAppPreferences(ParseApplication.getAppContext());
        appPreferences.remove(USER_KEY);
    }



    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
