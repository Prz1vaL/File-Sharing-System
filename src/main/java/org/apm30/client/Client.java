package org.apm30.client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {


    public static void main(String[] args) {

        final File[] fileToSend = new File[1];

        // GUI using Java Swing


        JFrame jFrame = new JFrame("APM30 Client Side");
        jFrame.setSize(400, 400);
        jFrame.setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel jLabel = new JLabel("MyFile Sender");
        jLabel.setFont(new Font("Arial", Font.BOLD, 20));
        jLabel.setBorder(new EmptyBorder(20, 0, 20, 0));
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel jFileName = new JLabel("Choose a file to send");
        jFileName.setFont(new Font("Arial", Font.BOLD, 15));
        jFileName.setBorder(new EmptyBorder(50, 0, 0, 0));
        jFileName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel jpButton = new JPanel();
        jpButton.setBorder(new EmptyBorder(70, 0, 10, 0));

        JButton jButtonChoose = new JButton("Choose MyFile");
        jButtonChoose.setPreferredSize(new Dimension(150, 75));
        jButtonChoose.setFont(new Font("Arial", Font.BOLD, 15));

        JButton jButtonSend = new JButton("Send MyFile");
        jButtonSend.setPreferredSize(new Dimension(150, 75));
        jButtonSend.setFont(new Font("Arial", Font.BOLD, 15));

        jpButton.add(jButtonChoose);
        jpButton.add(jButtonSend);

        jButtonChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setDialogTitle("Choose a file to send");
                if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    fileToSend[0] = jFileChooser.getSelectedFile();
                    jFileName.setText("The file being sent to the server is: " + fileToSend[0].getName());
                }
            }
        });

        jButtonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileToSend[0] != null) {
                    try {
                        String fileName = fileToSend[0].getName();
                        FileInputStream fi = new FileInputStream(fileToSend[0].getAbsolutePath());
                        Socket socket = new Socket("localhost", 8080);

                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        byte[] fileNameBytes = fileName.getBytes();

                        byte[] fileContentBytes = new byte[(int) fileToSend[0].length()];
                        fi.read(fileContentBytes);

                        dos.writeInt(fileNameBytes.length);
                        dos.write(fileNameBytes);

                        dos.writeInt(fileContentBytes.length);
                        dos.write(fileContentBytes);

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                } else {
                    jFileName.setText("No MyFile Selected, Please select a file and try again.");
                }
            }
        });

        jFrame.add(jLabel);
        jFrame.add(jFileName);
        jFrame.add(jpButton);
        jFrame.setVisible(true);

    }

    public void start() {
        main(null);
    }
}
