package com.project.artconnect.ui;

import com.project.artconnect.service.AuthService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegisterView extends JDialog {

    private final AuthService authService;

    private JTextField usernameField;

    private JPasswordField passwordField;

    private JTextField emailField;

    private JTextField nameField;

    private JLabel messageLabel;

    public RegisterView(AuthService authService) {

        this.authService = authService;

        buildUI();
    }

    private void buildUI() {

        setTitle("Créer un compte");

        setSize(420,420);

        setLocationRelativeTo(null);

        setModal(true);

        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(new EmptyBorder(20,30,20,30));

        panel.setBackground(new Color(245,245,245));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10,10,10,10);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Création de compte");

        title.setFont(new Font("Arial",Font.BOLD,22));

        title.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        panel.add(title,gbc);

        gbc.gridwidth = 1;

        gbc.gridy++;

        panel.add(new JLabel("Nom"),gbc);

        nameField = new JTextField();

        gbc.gridx = 1;

        panel.add(nameField,gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        panel.add(new JLabel("Email"),gbc);

        emailField = new JTextField();

        gbc.gridx = 1;

        panel.add(emailField,gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        panel.add(new JLabel("Username"),gbc);

        usernameField = new JTextField();

        gbc.gridx = 1;

        panel.add(usernameField,gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        panel.add(new JLabel("Password"),gbc);

        passwordField = new JPasswordField();

        gbc.gridx = 1;

        panel.add(passwordField,gbc);

        JButton registerButton = new JButton("Créer le compte");

        gbc.gridx = 0;
        gbc.gridy++;

        gbc.gridwidth = 2;

        panel.add(registerButton,gbc);

        messageLabel = new JLabel("");

        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy++;

        panel.add(messageLabel,gbc);

        registerButton.addActionListener(e -> register());

        add(panel);
    }

    private void register() {

        String name = nameField.getText().trim();

        String email = emailField.getText().trim();

        String username = usernameField.getText().trim();

        String password = new String(passwordField.getPassword());

        if(name.isEmpty() || email.isEmpty()
                || username.isEmpty() || password.isEmpty()) {

            messageLabel.setForeground(Color.RED);

            messageLabel.setText("Tous les champs sont obligatoires.");

            return;
        }

        boolean success = authService.register(
                name,
                email,
                username,
                password
        );

        if(success) {

            messageLabel.setForeground(new Color(0,128,0));

            messageLabel.setText("Compte créé avec succès.");

        } else {

            messageLabel.setForeground(Color.RED);

            messageLabel.setText("Erreur lors de la création.");
        }
    }
}