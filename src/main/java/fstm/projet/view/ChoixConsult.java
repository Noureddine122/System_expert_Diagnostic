package fstm.projet.view;

import fstm.projet.model.bo.Client;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;

public class ChoixConsult extends JFrame {

    /**
     * Create the frame.
     */

    public ChoixConsult(Client c) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 729, 476);
        JPanel contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(null);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnNewButton = new JButton();
        btnNewButton.setBackground(Color.WHITE);
        btnNewButton.setIcon(new ImageIcon("/home/noureddine/Documents/System_expert-master (5)/System_expert-master/src/main/java/fstm/projet/view/dig.png"));

        btnNewButton.addActionListener(e -> {
            setVisible(false);
            new ConsulterDiag(c).setVisible(true);
        });

        btnNewButton.setBounds(22, 146, 151, 173);
        contentPane.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("");
        btnNewButton_1.setIcon(new ImageIcon("/home/noureddine/Documents/System_expert-master (5)/System_expert-master/src/main/java/fstm/projet/view/nouveau.png"));
        btnNewButton_1.addActionListener(e -> {
            setVisible(false);
            new ClientConsultation(c).setVisible(true);
        });
        btnNewButton_1.setForeground(Color.WHITE);
        btnNewButton_1.setBackground(Color.WHITE);

        btnNewButton_1.setBounds(276, 146, 163, 173);
        contentPane.add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("");
        btnNewButton_2.setIcon(new ImageIcon("/home/noureddine/Documents/System_expert-master (5)/System_expert-master/src/main/java/fstm/projet/view/reg.png"));
        btnNewButton_2.addActionListener(e -> {
            setVisible(false);
            new Consulter_regi(c).setVisible(true);
        });


        btnNewButton_2.setForeground(Color.WHITE);
        btnNewButton_2.setBackground(Color.WHITE);

        btnNewButton_2.setBounds(525, 146, 163, 173);
        contentPane.add(btnNewButton_2);

        JLabel lblNewLabel = new JLabel(" Mes diagnostic");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel.setForeground(Color.BLUE);
        lblNewLabel.setBounds(10, 87, 189, 35);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel(" Nouveau diagnostic");
        lblNewLabel_1.setForeground(Color.BLUE);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblNewLabel_1.setBounds(265, 87, 189, 35);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel(" Etat regions");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblNewLabel_2.setForeground(Color.BLUE);
        lblNewLabel_2.setBounds(525, 92, 143, 22);
        contentPane.add(lblNewLabel_2);
    }
}
