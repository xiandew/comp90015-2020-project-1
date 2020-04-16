package server;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;

public class ServerGUI {

	private JFrame frmDictionaryServer;
	public JTextField textFieldEnterPortNumber;
	public JButton btnSelectDictFile;
	public String selectedDictionaryFilePath = null;
	public JButton btnStartServer;

	/**
	 * Create the application.
	 */
	public ServerGUI() {
		initialize();
	}

	public void launch() {
		frmDictionaryServer.setVisible(true);
	}
	
	public void showMessageDialog(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDictionaryServer = new JFrame();
		frmDictionaryServer.setTitle("Dictionary Server - COMP90015 Assignment 1, fall 2020");
		frmDictionaryServer.setBounds(100, 100, 721, 391);
		frmDictionaryServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDictionaryServer.getContentPane().setLayout(null);

		textFieldEnterPortNumber = new JTextField();
		textFieldEnterPortNumber.setFont(new Font("Courier New", Font.PLAIN, 15));
		textFieldEnterPortNumber.setBounds(370, 91, 244, 24);
		frmDictionaryServer.getContentPane().add(textFieldEnterPortNumber);
		textFieldEnterPortNumber.setColumns(10);

		JLabel lblEnterPortNumber = new JLabel("Enter port number");
		lblEnterPortNumber.setFont(new Font("Courier New", Font.PLAIN, 15));
		lblEnterPortNumber.setBounds(79, 95, 165, 18);
		frmDictionaryServer.getContentPane().add(lblEnterPortNumber);

		JLabel lblSelectDictFile = new JLabel("Select the dictionary file");
		lblSelectDictFile.setFont(new Font("Courier New", Font.PLAIN, 15));
		lblSelectDictFile.setBounds(79, 139, 234, 18);
		frmDictionaryServer.getContentPane().add(lblSelectDictFile);

		btnSelectDictFile = new JButton("Choose File...");
		btnSelectDictFile.setFont(new Font("Courier New", Font.PLAIN, 15));
		btnSelectDictFile.setBounds(370, 134, 244, 27);

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON File", "json");
		chooser.setFileFilter(filter);
		btnSelectDictFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File dictionaryFile = chooser.getSelectedFile();
					btnSelectDictFile.setText(dictionaryFile.getName());
					selectedDictionaryFilePath = dictionaryFile.getAbsolutePath();
				}
			}
		});

		frmDictionaryServer.getContentPane().add(btnSelectDictFile);

		btnStartServer = new JButton("Start Server");
		btnStartServer.setFont(new Font("Courier New", Font.PLAIN, 15));
		btnStartServer.setBounds(242, 255, 226, 27);
		frmDictionaryServer.getContentPane().add(btnStartServer);
	}
}
