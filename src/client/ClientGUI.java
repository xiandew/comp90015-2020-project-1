/**
 * @author Xiande Wen (905003)
 */
package client;

import java.util.HashMap;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;

import utils.PartOfSpeech;

public class ClientGUI {

	public JPanel panelConnectServer = new JPanel();
	public JTextField textFieldEnterServerAddr;
	public JTextField textFieldEnterPortNumber;

	public JFrame frmDictionaryClient;
	private JPanel panelWrapper;

	public JTextField textFieldQueryWordMeaning;
	public JButton btnQueryWordMeaning;
	public JTextArea textAreaWordMeaningOutput;

	public JTextField textFieldEnterNewWord;
	public JComboBox<?> comboBoxSelectWordType;
	public JTextArea textAreaEnterWordDefinition;
	public JTextArea textAreaAddingNewWordOutput;
	
	public JButton btnAddNewWord;

	public JTextField textFieldWordToRemove;
	public JButton btnRemoveWord;
	public JTextArea textAreaRemoveWordOutput;

	/**
	 * Create the application.
	 */
	public ClientGUI() {
		initialize();
	}

	public void launch() {
		frmDictionaryClient.setVisible(true);
	}

	public void showMessageDialog(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Dialog asking server address and port number
		panelConnectServer.setBounds(60, 312, 554, 97);
		JLabel lblEnterServerAddr = new JLabel("Enter server address");
		lblEnterServerAddr.setFont(new Font("Courier New", Font.PLAIN, 15));

		textFieldEnterServerAddr = new JTextField();
		textFieldEnterServerAddr.setFont(new Font("Courier New", Font.PLAIN, 15));
		textFieldEnterServerAddr.setColumns(10);

		JLabel lblEnterPortNumber = new JLabel("Enter port number");
		lblEnterPortNumber.setFont(new Font("Courier New", Font.PLAIN, 15));

		textFieldEnterPortNumber = new JTextField();
		textFieldEnterPortNumber.setFont(new Font("Courier New", Font.PLAIN, 15));
		textFieldEnterPortNumber.setColumns(10);

		GroupLayout gl_panelConnectServer = new GroupLayout(panelConnectServer);
		gl_panelConnectServer.setHorizontalGroup(gl_panelConnectServer.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelConnectServer.createSequentialGroup().addGap(37)
						.addGroup(gl_panelConnectServer.createParallelGroup(Alignment.LEADING)
								.addComponent(lblEnterPortNumber).addComponent(lblEnterServerAddr))
						.addGap(41)
						.addGroup(gl_panelConnectServer.createParallelGroup(Alignment.LEADING)
								.addComponent(textFieldEnterPortNumber, GroupLayout.PREFERRED_SIZE, 256,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(textFieldEnterServerAddr, GroupLayout.PREFERRED_SIZE, 256,
										GroupLayout.PREFERRED_SIZE))
						.addGap(141)));
		gl_panelConnectServer
				.setVerticalGroup(
						gl_panelConnectServer.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelConnectServer.createSequentialGroup().addContainerGap()
										.addGroup(gl_panelConnectServer.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblEnterServerAddr)
												.addComponent(textFieldEnterServerAddr, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(gl_panelConnectServer.createParallelGroup(Alignment.LEADING)
												.addComponent(lblEnterPortNumber).addComponent(textFieldEnterPortNumber,
														GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
										.addGap(45)));
		panelConnectServer.setLayout(gl_panelConnectServer);

		// Main frame
		frmDictionaryClient = new JFrame("My First Swing Example");
		frmDictionaryClient.setTitle("Dictionary Client - COMP90015 Assignment 1, fall 2020");
		// Setting the width and height of frame
		frmDictionaryClient.setSize(764, 408);
		frmDictionaryClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*
		 * Creating panel. This is same as a div tag in HTML We can create several
		 * panels and add them to specific positions in a JFrame. Inside panels we can
		 * add text fields, buttons and other components.
		 */
		panelWrapper = new JPanel();
		panelWrapper.setLayout(null);
		// adding panel to frame
		frmDictionaryClient.getContentPane().add(panelWrapper, BorderLayout.CENTER);

		ButtonGroup buttonGroupMenu = new ButtonGroup();

		JPanel panelQueryWordMeaning = new JPanel();
		panelQueryWordMeaning.setBounds(14, 35, 718, 264);
		panelWrapper.add(panelQueryWordMeaning);
		panelQueryWordMeaning.setLayout(null);

		JLabel lblEnterAWord = new JLabel("Enter a word");
		lblEnterAWord.setFont(new Font("Courier New", Font.PLAIN, 15));
		lblEnterAWord.setBounds(40, 40, 108, 24);
		panelQueryWordMeaning.add(lblEnterAWord);
		textFieldQueryWordMeaning = new JTextField(20);
		textFieldQueryWordMeaning.setFont(new Font("Courier New", Font.PLAIN, 15));
		textFieldQueryWordMeaning.setBounds(169, 39, 383, 24);
		panelQueryWordMeaning.add(textFieldQueryWordMeaning);

		btnQueryWordMeaning = new JButton("Lookup");
		btnQueryWordMeaning.setFont(new Font("Courier New", Font.PLAIN, 15));
		btnQueryWordMeaning.setBounds(586, 40, 96, 24);
		panelQueryWordMeaning.add(btnQueryWordMeaning);

		JLabel lblQueryWordMeaningTitle = new JLabel("Query the meaning(s) of a given word");
		lblQueryWordMeaningTitle.setFont(new Font("Courier New", Font.BOLD, 16));
		lblQueryWordMeaningTitle.setBounds(40, 9, 643, 18);
		panelQueryWordMeaning.add(lblQueryWordMeaningTitle);

		JScrollPane scrollPaneWordMeaningOutput = new JScrollPane();
		scrollPaneWordMeaningOutput.setBounds(40, 79, 643, 139);
		panelQueryWordMeaning.add(scrollPaneWordMeaningOutput);

		textAreaWordMeaningOutput = new JTextArea();
		scrollPaneWordMeaningOutput.setViewportView(textAreaWordMeaningOutput);
		textAreaWordMeaningOutput.setEditable(false);
		textAreaWordMeaningOutput.setFont(new Font("Courier New", Font.PLAIN, 15));

		btnAddNewWord = new JButton("Add");
		btnAddNewWord.setBounds(586, 3, 96, 24);
		panelQueryWordMeaning.add(btnAddNewWord);
		btnAddNewWord.setFont(new Font("Courier New", Font.PLAIN, 15));

		JPanel panelAddNewWord = new JPanel();
		panelAddNewWord.setLayout(null);
		panelAddNewWord.setBounds(14, 35, 718, 287);
		panelWrapper.add(panelAddNewWord);

		JLabel lblEnterNewWord = new JLabel("Enter new word");
		lblEnterNewWord.setFont(new Font("Courier New", Font.PLAIN, 15));
		lblEnterNewWord.setBounds(40, 40, 126, 24);
		panelAddNewWord.add(lblEnterNewWord);

		textFieldEnterNewWord = new JTextField(20);
		textFieldEnterNewWord.setFont(new Font("Courier New", Font.PLAIN, 15));
		textFieldEnterNewWord.setBounds(297, 40, 383, 24);
		panelAddNewWord.add(textFieldEnterNewWord);

		JLabel lblAddNewWordTitle = new JLabel("Add a new word");
		lblAddNewWordTitle.setFont(new Font("Courier New", Font.BOLD, 16));
		lblAddNewWordTitle.setBounds(40, 9, 643, 18);
		panelAddNewWord.add(lblAddNewWordTitle);

		JLabel lblSelectWordType = new JLabel("Select the word type");
		lblSelectWordType.setFont(new Font("Courier New", Font.PLAIN, 15));
		lblSelectWordType.setBounds(40, 77, 180, 24);
		panelAddNewWord.add(lblSelectWordType);

		JLabel lblEnterWordDefinition = new JLabel("Enter the definition");
		lblEnterWordDefinition.setFont(new Font("Courier New", Font.PLAIN, 15));
		lblEnterWordDefinition.setBounds(40, 112, 180, 24);
		panelAddNewWord.add(lblEnterWordDefinition);

		comboBoxSelectWordType = new JComboBox<PartOfSpeech>(PartOfSpeech.values());
		comboBoxSelectWordType.setBounds(297, 76, 383, 24);
		panelAddNewWord.add(comboBoxSelectWordType);

		JScrollPane scrollPaneEnterWordDefinition = new JScrollPane();
		scrollPaneEnterWordDefinition.setBounds(297, 116, 383, 58);
		panelAddNewWord.add(scrollPaneEnterWordDefinition);

		textAreaEnterWordDefinition = new JTextArea();
		scrollPaneEnterWordDefinition.setViewportView(textAreaEnterWordDefinition);
		
		JScrollPane scrollPaneAddingNewWordOutput = new JScrollPane();
		scrollPaneAddingNewWordOutput.setBounds(37, 187, 643, 58);
		panelAddNewWord.add(scrollPaneAddingNewWordOutput);

		textAreaAddingNewWordOutput = new JTextArea();
		scrollPaneAddingNewWordOutput.setViewportView(textAreaAddingNewWordOutput);

		JPanel panelRemoveWord = new JPanel();
		panelRemoveWord.setBounds(14, 35, 718, 264);
		panelWrapper.add(panelRemoveWord);
		panelRemoveWord.setLayout(null);

		JLabel lblEnterTheWord = new JLabel("Enter the word");
		lblEnterTheWord.setFont(new Font("Courier New", Font.PLAIN, 15));
		lblEnterTheWord.setBounds(40, 40, 126, 24);
		panelRemoveWord.add(lblEnterTheWord);

		textFieldWordToRemove = new JTextField(20);
		textFieldWordToRemove.setFont(new Font("Courier New", Font.PLAIN, 15));
		textFieldWordToRemove.setBounds(180, 40, 383, 24);
		panelRemoveWord.add(textFieldWordToRemove);

		btnRemoveWord = new JButton("Remove");
		btnRemoveWord.setFont(new Font("Courier New", Font.PLAIN, 15));
		btnRemoveWord.setBounds(589, 40, 96, 24);
		panelRemoveWord.add(btnRemoveWord);

		JLabel lblRemoveWordTitle = new JLabel("Remove an existing word");
		lblRemoveWordTitle.setFont(new Font("Courier New", Font.BOLD, 16));
		lblRemoveWordTitle.setBounds(40, 9, 643, 18);
		panelRemoveWord.add(lblRemoveWordTitle);

		JScrollPane scrollPaneRemoveWordOutput = new JScrollPane();
		scrollPaneRemoveWordOutput.setBounds(40, 89, 643, 139);
		panelRemoveWord.add(scrollPaneRemoveWordOutput);

		textAreaRemoveWordOutput = new JTextArea();
		scrollPaneRemoveWordOutput.setViewportView(textAreaRemoveWordOutput);
		textAreaRemoveWordOutput.setFont(new Font("Courier New", Font.PLAIN, 15));
		textAreaRemoveWordOutput.setEditable(false);

		// Switch panel when corresponding radio button is selected
		HashMap<JRadioButton, JPanel> mapButtonToPanel = new HashMap<>();

		JMenuBar menuBar = new JMenuBar();
		frmDictionaryClient.setJMenuBar(menuBar);

		JMenu mnMenu = new JMenu("Click here to choose another function");
		mnMenu.setFont(new Font("Courier New", Font.BOLD, 15));
		menuBar.add(mnMenu);

		JRadioButton btnMenuQueryWordMeaning = new JRadioButton("Query Word Meaning");
		mnMenu.add(btnMenuQueryWordMeaning);
		btnMenuQueryWordMeaning.setName("QueryWordMeaning");
		btnMenuQueryWordMeaning.setFont(new Font("Courier New", Font.BOLD, 15));
		buttonGroupMenu.add(btnMenuQueryWordMeaning);
		mapButtonToPanel.put(btnMenuQueryWordMeaning, panelQueryWordMeaning);

		JRadioButton btnMenuAddNewWord = new JRadioButton("Add New Word");
		mnMenu.add(btnMenuAddNewWord);
		btnMenuAddNewWord.setName("AddNewWord");
		btnMenuAddNewWord.setFont(new Font("Courier New", Font.BOLD, 15));
		buttonGroupMenu.add(btnMenuAddNewWord);
		mapButtonToPanel.put(btnMenuAddNewWord, panelAddNewWord);

		JRadioButton btnMenuRemoveWord = new JRadioButton("Remove Word");
		mnMenu.add(btnMenuRemoveWord);
		btnMenuRemoveWord.setName("RemoveWord");
		btnMenuRemoveWord.setFont(new Font("Courier New", Font.BOLD, 15));
		buttonGroupMenu.add(btnMenuRemoveWord);
		mapButtonToPanel.put(btnMenuRemoveWord, panelRemoveWord);

		ActionListener switchPanelListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switchPanel((JRadioButton) e.getSource(), mapButtonToPanel);
			}
		};

		for (JRadioButton menuButton : mapButtonToPanel.keySet()) {
			menuButton.addActionListener(switchPanelListener);
		}

		// Display "QueryWordMeaning" by default
		btnMenuQueryWordMeaning.setSelected(true);
		switchPanel(btnMenuQueryWordMeaning, mapButtonToPanel);
	}

	private static void switchPanel(JRadioButton selectedBtn, HashMap<JRadioButton, JPanel> mapButtonToPanel) {
		for (JRadioButton menuButton : mapButtonToPanel.keySet()) {
			boolean isVisible = false;
			if (menuButton.equals(selectedBtn)) {
				isVisible = true;
			}
			mapButtonToPanel.get(menuButton).setVisible(isVisible);
		}
	}
}