package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import model.User;

public class Navbar {
    public static ToolBar createNavbar(User currUser) {
        HBox leftNav = new HBox(10); // Holds general navigation items
        HBox rightNav = new HBox(10); // Holds profile/logout or login/register
        leftNav.setPadding(new Insets(10));
        rightNav.setPadding(new Insets(10));

        // Buttons visible to all users
        Button btnLogin = new Button("Login");
        Button btnRegister = new Button("Register");
        Button btnProfile = new Button("Profile");
        Button btnLogout = new Button("Logout");

        // Dynamic Buttons
        Button btnCreateEvent = new Button("Create Event");
        Button btnEvent = new Button("Event");
        Button btnUsers = new Button("Users");
        Button btnInvitation = new Button("Invitation");
        Button btnManageVendor = new Button("Manage Vendor");

        // Add event listeners
        btnLogin.setOnAction(e -> Main.redirect(new LoginPage().scene));
        btnRegister.setOnAction(e -> Main.redirect(new RegisterPage().scene));
        btnProfile.setOnAction(e -> Main.redirect(new ProfilePage().scene));
        btnLogout.setOnAction(e -> {
            Main.currUser = null; // Logout logic
            Main.redirect(new LoginPage().scene);
        });

        // Default navbar when user is not logged in
        if (currUser == null) {
            rightNav.getChildren().addAll(btnLogin, btnRegister);
        } else {
            // Logged in navbar
            rightNav.getChildren().addAll(btnProfile, btnLogout);

            // Role-based buttons
            switch (currUser.getUser_role()) {
                case "Event Organizer":
                    leftNav.getChildren().addAll(btnCreateEvent, btnEvent);
                    break;
                case "Admin":
                    leftNav.getChildren().addAll(btnEvent, btnUsers);
                    break;
                case "Vendor":
                    leftNav.getChildren().addAll(btnInvitation, btnEvent, btnManageVendor);
                    break;
                case "Guest":
                    leftNav.getChildren().addAll(btnInvitation, btnEvent);
                    break;
            }
        }



        HBox navContainer = new HBox(20, leftNav, rightNav);
        navContainer.setStyle("-fx-padding: 10;");
        navContainer.setPrefHeight(40);

        return new ToolBar(navContainer);
    }
}