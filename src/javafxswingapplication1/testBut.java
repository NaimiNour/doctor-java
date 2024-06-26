/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package javafxswingapplication1;

/**
 *
 * @author Bureau
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import sql_classes.SqlConnectionMethods;
import java.util.HashSet;
import java.util.Set;

public class testBut extends JFrame {

    private SqlConnectionMethods sqlClass = new SqlConnectionMethods();
    private String user_name, type, name;

    public testBut(String user_name, String type, String name) {

        // setTitle("Ten Buttons");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }

        });
        this.user_name = user_name;
        this.type = type;
        ImageIcon icon = new ImageIcon("C:/Users/Bureau/Documents/NetBeansProjects/JavaFXSwingApplication1/src/javafxswingapplication1/appointment (2).png"); // Replace with the actual path to your icon image
        setIconImage(icon.getImage());
        // Create the label with "Salle d'attente" text and blue color
        JLabel label = new JLabel("Salle d'attente");
        // Create a font with specific attributes
        Font font = new Font("Arial", Font.BOLD | Font.ITALIC, 24);

        // Set the font to the label
        label.setFont(font);
        label.setForeground(Color.BLUE);
        // Set custom font for the label

        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        //Create Button
        JButton but = new JButton("Retour");
        but.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/left-arrow (1).png"))); // NOI18N
        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ReturnWindow();
            }
        });

        add(but, BorderLayout.WEST);

        // Create the panel for the buttons and set a GridLayout with 2 rows and 5 columns
        JPanel buttonsPanel = new JPanel(new GridLayout(3, 5));
        add(buttonsPanel, BorderLayout.CENTER);

        // Create ten buttons with loop and add them to the buttons panel
        //    button.setText("Naimi Nour");
        Set<String> buttonTexts = new HashSet<>();

        try {
            ResultSet result = sqlClass.execSelect("SELECT attente.name, attente.family_name, attente.time, patients.allergie "
                    + "FROM patients "
                    + "JOIN rdv ON patients.id_patient = rdv.id_patient "
                    + "JOIN attente ON rdv.id_rdv = attente.id_rdv ", "succss to get All patients ", "faild to get All patients because: ");

            if (!result.wasNull()) {
                while (result.next()) {

                    String data = result.getString("name");
                    String data1 = result.getString("family_name");
                    String data2 = result.getString("time");
                    String data3 = result.getString("allergie");
                    buttonTexts.add(data + " " + data1 + " " + data2 + " " + data3);

                    //   button.setText(data + " " + data1);
                    // Create buttons with text from the database
                }
            } else {
                System.out.println("null");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            sqlClass.alertERROR("faild to print All patients because: " + ex.getMessage());
        }
        for (String buttonText : buttonTexts) {

            // Split the input string by spaces
            String[] parts = buttonText.split(" ");

            // Check if there are at least two parts
            if (parts.length >= 3) {
                // Combine the first two parts with a space to get the desired substring
                String result = parts[0] + " " + parts[1];
                //      System.out.println(result); // This will print "naimi nour"

                JButton button = new JButton("<html>" + result + " <br> " + parts[2] + "</html>");
                if (buttonText.contains("daibe")) {

                    button.setBackground(Color.red);

                    // Create a font with specific attributes
                    Font font1 = new Font("Arial", Font.BOLD | Font.CENTER_BASELINE, 14);
                    button.setFont(font1);

                } else if (buttonText.contains("Collégue")) {
                    button.setBackground(Color.GREEN);
                    // Create a font with specific attributes
                    Font font1 = new Font("Arial", Font.BOLD | Font.CENTER_BASELINE, 12);
                    button.setFont(font1);
                } else {
                    // Create a font with specific attributes
                    Font font1 = new Font("Arial", Font.BOLD | Font.CENTER_BASELINE, 14);
                    button.setFont(font1);
                }

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Split the input string by spaces
                        if (user_name.equals("user1")) {

                            String[] parts = buttonText.split(" ");

                            // Check if there are at least two parts
                            if (parts.length >= 2) {
                                // Combine the first two parts with a space to get the desired substring
                                String result = parts[0] + " " + parts[1];
                                //    // This will print "naimi nour"

                                openNewWindow(result);
                                deletePatient(result);

                            }

                        } else if (user_name.equals("user2")) {
                            ReturnWindow();
                            System.out.println(user_name);
                        }

                    }
                });

                // Set preferred size for buttons (adjust the dimensions as needed)
                button.setPreferredSize(new Dimension(400, 100));
                buttonsPanel.add(button);
            }

            // Set the preferred size of the frame
            // This will resize the frame to a larger size
            setPreferredSize(new Dimension(750, 300));
            // Set the size and make the frame visible
            pack();
            setLocationRelativeTo(null); // Center the frame on the screen
            setVisible(true);
        }
    }

    public void openNewWindow(String name) {
        try {

            setVisible(false);
            //  Principal principal= new Principal(new javax.swing.JFrame(), true,this.user_name,this.type);
            patient2 p2;
            p2 = new patient2(this.user_name, this.type, name);
            p2.setVisible(true);

        } catch (SQLException ex) {
            Logger.getLogger(testBut.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ReturnWindow() {

        setVisible(false);
        Principal p2;
        p2 = new Principal(this.user_name, this.type, this.name);
        p2.setVisible(true);

    }

    private void deletePatient(String name) {

        if (sqlClass.showConfirmDialog("Confirmation", "Voulez-vous vraiment supprimer RDV patient=" + name + " ??") == 0) {
            int result = sqlClass.execDeleteOrUpdateOrInsert(
                    "DELETE FROM `attente`  where  CONCAT(name, ' ',family_name)  like '%" + name + "%' ;",
                    "succss to delete rdv(name=" + name + ") de la salle d'attente",
                    "faild to delete rdv(name=" + name + ") de la salle d'attente because: "
            );
            if (result == 1) {
                sqlClass.alertInfo("Suppression du patient=" + name + "de la salle d'attente");
            } else {
                sqlClass.alertERROR("Failed to delete rdv patient=" + name + "de la salle d'attente");
            }

        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new testBut());
    }

    private testBut() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
