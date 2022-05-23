package DatabaseRelated;

import UserRelated.Employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JDialog {
    private JPasswordField passwordField2;
    private JButton enterButton;
    private JButton cancelButton;
    private JPanel loginPanel;
    private JTextField email;
    private JButton registerButton;
    public static Employee employee;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Connect cn = new Connect();

    public Login(JFrame parent) {
        super(parent);
        setTitle("DatabaseRelated.Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(600, 550));
        setModal(true);

        setUndecorated(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegister();
            }
        });
        setVisible(true);
    }

    private void loginUser() {
        String email = Login.this.email.getText();
        String password = String.valueOf(passwordField2.getPassword());
        employee = authenticate(email, password);
        if (employee != null) {
            dispose();
            JOptionPane.showMessageDialog(null, "Welcome " + employee.getName());
            Inventory nuevo = new Inventory(null);
            nuevo.setEmployee(employee);
            nuevo.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(Login.this,
                    "Incorrect email or password.", "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showRegister() {
        Register nuevo = new Register(null);
        nuevo.setVisible(true);
    }

    private Employee authenticate(String email, String password) {
        Employee em = null;
        try {
            String sql = "SELECT * FROM usuarios WHERE correo=? AND password=?";
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                em = new Employee();
                em.setID(rs.getInt("id"));
                em.setName(rs.getString("nombre"));
                em.setEmail( rs.getString("correo"));
                em.setPassword(rs.getString("password"));
                em.setAdmin(rs.getBoolean("esAdmin"));
            }
            rs.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return em;
    }
}
