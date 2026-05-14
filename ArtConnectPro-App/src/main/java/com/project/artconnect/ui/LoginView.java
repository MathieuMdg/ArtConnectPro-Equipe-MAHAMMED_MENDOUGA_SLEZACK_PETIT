package com.project.artconnect.ui;

import com.project.artconnect.service.AuthService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginView extends JDialog {

    private final AuthService authService;

    private boolean loginSuccessful = false;

    private JTextField usernameField;
    private JPasswordField passwordField;

    private JLabel errorLabel;

    public LoginView(AuthService authService) {

        this.authService = authService;

        buildUI();
    }

    private void buildUI() {

        setTitle("ArtConnect - Connexion");

        setSize(420, 320);

        setLocationRelativeTo(null);

        setModal(true);

        setResizable(false);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(245,245,245));

        JPanel formPanel = new JPanel(new GridBagLayout());

        formPanel.setOpaque(false);

        formPanel.setBorder(new EmptyBorder(20,30,20,30));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10,10,10,10);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Bienvenue sur ArtConnect");

        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        titleLabel.setForeground(new Color(44,62,80));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        formPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        gbc.gridy++;

        formPanel.add(new JLabel("Nom d'utilisateur"), gbc);

        usernameField = new JTextField();

        usernameField.setPreferredSize(new Dimension(200,30));

        gbc.gridx = 1;

        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        formPanel.add(new JLabel("Mot de passe"), gbc);

        passwordField = new JPasswordField();

        passwordField.setPreferredSize(new Dimension(200,30));

        gbc.gridx = 1;

        formPanel.add(passwordField, gbc);

        errorLabel = new JLabel("");

        errorLabel.setForeground(Color.RED);

        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy++;

        gbc.gridwidth = 2;

        formPanel.add(errorLabel, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(1,2,10,0));

        buttonPanel.setOpaque(false);

        JButton loginButton = new JButton("Se connecter");

        JButton registerButton = new JButton("Créer un compte");

        buttonPanel.add(loginButton);

        buttonPanel.add(registerButton);

        gbc.gridy++;

        formPanel.add(buttonPanel, gbc);

        loginButton.addActionListener(e -> tentativeConnexion());

        registerButton.addActionListener(e -> {

            RegisterView registerView = new RegisterView(authService);

            registerView.setVisible(true);
        });

        passwordField.addActionListener(e -> tentativeConnexion());

        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void tentativeConnexion() {

        String username = usernameField.getText().trim();

        String password = new String(passwordField.getPassword());

        if(username.isEmpty() || password.isEmpty()) {

            errorLabel.setText("Veuillez remplir tous les champs.");

            return;
        }

        boolean success = authService.login(username,password);

        if(success) {

            loginSuccessful = true;

            dispose();

        } else {

            errorLabel.setText("Identifiants incorrects.");

            passwordField.setText("");
        }
    }

    public boolean isLoginSuccessful() {

        return loginSuccessful;
    }
}