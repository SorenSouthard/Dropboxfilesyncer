package group5;

import javax.swing.*;
import java.awt.*;
import com.dropbox.core.v2.DbxClientV2;

public class OsProjectGUI {

    public static void createAndShowGUI(DbxClientV2 client) {
        JFrame frame = new JFrame("Operating System Project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Panel for labels and text fields (initially hidden)
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        JLabel localPathLabel = new JLabel("Local File Path:");
        JTextField localPathField = new JTextField();
        JLabel dropboxPathLabel = new JLabel("Dropbox File Path:");
        JTextField dropboxPathField = new JTextField();

        // Size for text fields
        Dimension textFieldSize = new Dimension(150, 30);
        localPathField.setPreferredSize(textFieldSize);
        dropboxPathField.setPreferredSize(textFieldSize);

        inputPanel.add(localPathLabel);
        inputPanel.add(localPathField);
        inputPanel.add(dropboxPathLabel);
        inputPanel.add(dropboxPathField);
        frame.getContentPane().add(inputPanel, BorderLayout.CENTER);
        inputPanel.setVisible(false);

        // Create a panel for the sync button and back button
        JPanel buttonPanel = new JPanel();
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Create a button for synchronization
        JButton syncButton = new JButton("Sync");
        buttonPanel.add(syncButton);

        // Create a button to go back
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            inputPanel.setVisible(false); // Hide the input panel
        });
        buttonPanel.add(backButton);

        // Add action listener to sync button
        syncButton.addActionListener(e -> {
            inputPanel.setVisible(true); // Show the input panel
            // List folders available for syncing
            DropboxFolderLister folderLister = new DropboxFolderLister(client);
            folderLister.listFolders();
        });

        frame.setVisible(true);
    }
}

