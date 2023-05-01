package org.apm30.server;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static ArrayList<MyFile> myFiles = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        int fileID = 0;

        // Creating a UI for the server
        JFrame jFrame = new JFrame("APM30 Server Side");
        jFrame.setSize(400, 400);
        jFrame.setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        JScrollPane jScrollPane = new JScrollPane(jPanel);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        JLabel jLabel = new JLabel("MyFile Receiver (Server side)");
        jLabel.setFont(new Font("Arial", Font.BOLD, 20));
        jLabel.setBorder(new EmptyBorder(20, 0, 20, 0));
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        jFrame.add(jLabel);
        jFrame.add(jScrollPane);
        jFrame.setVisible(true);

        // Creating a socket to accept the connection from the client
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            try {
                // Accepting the connection from the client
                Socket socket = serverSocket.accept();
                // Creating a DataInputStream object to read the data from the client
                DataInputStream dis = new DataInputStream(socket.getInputStream());

                int fileNameLength = dis.readInt();
                if (fileNameLength > 0) {
                    byte[] fileNameBytes = new byte[fileNameLength];
                    dis.readFully(fileNameBytes, 0, fileNameBytes.length);
                    String fileName = new String(fileNameBytes);

                    int fileContentLength = dis.readInt();
                    if (fileContentLength > 0) {
                        byte[] fileContentBytes = new byte[fileContentLength];
                        dis.readFully(fileContentBytes, 0, fileContentBytes.length);

                        JPanel jpFileRow = new JPanel();
                        jpFileRow.setLayout(new BoxLayout(jpFileRow, BoxLayout.X_AXIS));

                        JLabel jlFileName = new JLabel(fileName);
                        jlFileName.setFont(new Font("Arial", Font.BOLD, 15));
                        jlFileName.setBorder(new EmptyBorder(10, 0, 10, 0));

                        if (getFileExtension(fileName).equalsIgnoreCase("txt")) {
                            jpFileRow.setName(String.valueOf(fileID));
                            jpFileRow.addMouseListener(getMyMouseListener());

                            jpFileRow.add(jlFileName);
                            jPanel.add(jpFileRow);
                            jFrame.validate();
                        } else {
                            jpFileRow.setName(String.valueOf(fileID));
                            jpFileRow.addMouseListener(getMyMouseListener());


                            jpFileRow.add(jlFileName);
                            jPanel.add(jpFileRow);

                            jFrame.validate();
                        }
                        myFiles.add(new MyFile(fileID, fileName, fileContentBytes, getFileExtension(fileName)));
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private static MouseListener getMyMouseListener() {

        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JPanel jPanel = (JPanel) e.getSource();
                int fileID = Integer.parseInt(jPanel.getName());

                for (MyFile myFile : myFiles) {
                    if (myFile.getId() == fileID) {
                        JFrame jfPreview = createFrame(myFile.getName(), myFile.getData(), myFile.getFileExtension());
                        jfPreview.setVisible(true);
                    }
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }

    public static JFrame createFrame(String fileName, byte[] fileData, String fileExtension) {
        JFrame jFrame = new JFrame("APM MyFile Downloader");
        jFrame.setSize(400, 400);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        JLabel jLabel = new JLabel("MyFile Downloader");
        jLabel.setFont(new Font("Arial", Font.BOLD, 20));
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        jLabel.setBorder(new EmptyBorder(20, 0, 10, 0));

        JLabel jPrompt = new JLabel("Are you sure you want to download " + fileName + "?");
        jPrompt.setFont(new Font("Arial", Font.BOLD, 15));
        jPrompt.setBorder(new EmptyBorder(10, 0, 10, 0));
        jPrompt.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton jYes = new JButton("Yes");
        jYes.setPreferredSize(new Dimension(100, 50));
        jYes.setFont(new Font("Arial", Font.BOLD, 15));

        JButton jNo = new JButton("No");
        jNo.setPreferredSize(new Dimension(100, 50));
        jNo.setFont(new Font("Arial", Font.BOLD, 15));


        JLabel jlFileContent = new JLabel();
        jlFileContent.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel jpButtons = new JPanel();
        jpButtons.setBorder(new EmptyBorder(10, 0, 10, 0));
        jpButtons.add(jYes);
        jpButtons.add(jNo);

        if (fileExtension.equalsIgnoreCase("txt")) {
            jlFileContent.setText("<html>" + new String(fileData) + "</html>");
        } else {
            jlFileContent.setIcon(new ImageIcon(fileData));
        }

        jYes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File fileToDownload = new File("src/main/resources/downloads/" + fileName);
                try {
                    FileOutputStream fos = new FileOutputStream(fileToDownload);
                    fos.write(fileData);
                    fos.close();
                    jFrame.dispose();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        jNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.dispose();
            }
        });

        jPanel.add(jLabel);
        jPanel.add(jPrompt);
        jPanel.add(jlFileContent);
        jPanel.add(jpButtons);
        jFrame.add(jPanel);

        return jFrame;
    }

    public static String getFileExtension(String fileName) {
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            return fileName.substring(i + 1);
        } else {
            return "No extension found.";
        }
    }

    public void start() throws IOException {
        main(null);
    }
}
