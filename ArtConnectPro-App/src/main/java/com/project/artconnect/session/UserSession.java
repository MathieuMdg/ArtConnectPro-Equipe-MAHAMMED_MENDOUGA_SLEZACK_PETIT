package com.project.artconnect.session;

import com.project.artconnect.model.AppUser;

public class UserSession {

    private static AppUser currentUser;

    private UserSession() {
    }

    public static void login(AppUser user) {

        currentUser = user;
    }

    public static void logout() {

        currentUser = null;
    }

    public static AppUser getCurrentUser() {

        return currentUser;
    }

    public static boolean isLoggedIn() {

        return currentUser != null;
    }

    public static boolean isAdmin() {

        return currentUser != null
                && "ADMIN".equals(currentUser.getRole());
    }
}