/**
 * @author Xiande Wen (905003)
 */
package client;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;

public class MyWindow {
	private static JPanel panelWrapper;
	private static JTextField textFieldEnterNewWord;
	private static JTextField textFieldWordToRemove;

	public static void main(String[] args) {
		// Creating instance of JFrame
		JFrame frmSimpleDictionary = new JFrame("My First Swing Example");
		frmSimpleDictionary.setTitle("Simple Dictionary - COMP90015 Assignment 1, fall 2020");
		// Setting the width and height of frame
		frmSimpleDictionary.setSize(764, 408);
		frmSimpleDictionary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*
		 * Creating panel. This is same as a div tag in HTML We can create several
		 * panels and add them to specific positions in a JFrame. Inside panels we can
		 * add text fields, buttons and other components.
		 */
		panelWrapper = new JPanel();
		panelWrapper.setLayout(null);
		// adding panel to frame
		frmSimpleDictionary.getContentPane().add(panelWrapper);

		JPanel menuPanel = new JPanel();
		menuPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		menuPanel.setBounds(14, 13, 718, 43);
		panelWrapper.add(menuPanel);
		menuPanel.setLayout(null);

		ButtonGroup buttonGroupMenu = new ButtonGroup();

		JRadioButton btnMenuQueryWordMeaning = new JRadioButton("Query Word Meaning");
		btnMenuQueryWordMeaning.setName("QueryWordMeaning");
		btnMenuQueryWordMeaning.setBounds(37, 9, 191, 27);
		menuPanel.add(btnMenuQueryWordMeaning);
		btnMenuQueryWordMeaning.setFont(new Font("Courier New", Font.BOLD, 15));
		buttonGroupMenu.add(btnMenuQueryWordMeaning);

		JRadioButton btnMenuAddNewWord = new JRadioButton("Add New Word");
		btnMenuAddNewWord.setName("AddNewWord");
		btnMenuAddNewWord.setBounds(306, 9, 137, 27);
		menuPanel.add(btnMenuAddNewWord);
		btnMenuAddNewWord.setFont(new Font("Courier New", Font.BOLD, 15));
		buttonGroupMenu.add(btnMenuAddNewWord);

		JRadioButton btnMenuRemoveWord = new JRadioButton("Remove Word");
		btnMenuRemoveWord.setName("RemoveWord");
		btnMenuRemoveWord.setBounds(524, 9, 127, 27);
		menuPanel.add(btnMenuRemoveWord);
		btnMenuRemoveWord.setFont(new Font("Courier New", Font.BOLD, 15));
		buttonGroupMenu.add(btnMenuRemoveWord);

		JPanel panelQueryWordMeaning = new JPanel();
		panelQueryWordMeaning.setBounds(14, 85, 718, 264);
		panelWrapper.add(panelQueryWordMeaning);
		panelQueryWordMeaning.setLayout(null);

		JLabel lblEnterAWord = new JLabel("Enter a word");
		lblEnterAWord.setFont(new Font("Courier New", Font.PLAIN, 15));
		lblEnterAWord.setBounds(40, 40, 108, 24);
		panelQueryWordMeaning.add(lblEnterAWord);
		JTextField textFieldEnterAWord = new JTextField(20);
		textFieldEnterAWord.setFont(new Font("Courier New", Font.PLAIN, 15));
		textFieldEnterAWord.setBounds(169, 39, 383, 24);
		panelQueryWordMeaning.add(textFieldEnterAWord);

		JButton btnLookup = new JButton("Lookup");
		btnLookup.setFont(new Font("Courier New", Font.PLAIN, 15));
		btnLookup.setBounds(586, 40, 96, 24);
		panelQueryWordMeaning.add(btnLookup);

		JLabel lblQueryWordMeaningTitle = new JLabel("Query the meaning(s) of a given word");
		lblQueryWordMeaningTitle.setFont(new Font("Courier New", Font.BOLD, 16));
		lblQueryWordMeaningTitle.setBounds(40, 9, 643, 18);
		panelQueryWordMeaning.add(lblQueryWordMeaningTitle);

		JScrollPane scrollPaneWordMeaningOutput = new JScrollPane();
		scrollPaneWordMeaningOutput.setBounds(40, 79, 643, 139);
		panelQueryWordMeaning.add(scrollPaneWordMeaningOutput);

		JTextArea textAreaWordMeaningOutput = new JTextArea();
		scrollPaneWordMeaningOutput.setViewportView(textAreaWordMeaningOutput);
		textAreaWordMeaningOutput.setEditable(false);
		textAreaWordMeaningOutput.setFont(new Font("Courier New", Font.PLAIN, 15));

		JPanel panelAddNewWord = new JPanel();
		panelAddNewWord.setLayout(null);
		panelAddNewWord.setBounds(14, 85, 718, 264);
		panelWrapper.add(panelAddNewWord);

		JLabel lblEnterNewWord = new JLabel("Enter new word");
		lblEnterNewWord.setFont(new Font("Courier New", Font.PLAIN, 15));
		lblEnterNewWord.setBounds(40, 40, 126, 24);
		panelAddNewWord.add(lblEnterNewWord);

		textFieldEnterNewWord = new JTextField(20);
		textFieldEnterNewWord.setFont(new Font("Courier New", Font.PLAIN, 15));
		textFieldEnterNewWord.setBounds(297, 40, 383, 24);
		panelAddNewWord.add(textFieldEnterNewWord);

		JButton btnAddNewWord = new JButton("Add");
		btnAddNewWord.setFont(new Font("Courier New", Font.PLAIN, 15));
		btnAddNewWord.setBounds(584, 229, 96, 24);
		panelAddNewWord.add(btnAddNewWord);

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

		JComboBox<?> comboBoxSelectWordType = new JComboBox<Object>();
		comboBoxSelectWordType.setBounds(297, 76, 383, 24);
		panelAddNewWord.add(comboBoxSelectWordType);

		JScrollPane scrollPaneEnterWordDefinition = new JScrollPane();
		scrollPaneEnterWordDefinition.setBounds(297, 116, 383, 100);
		panelAddNewWord.add(scrollPaneEnterWordDefinition);

		JTextArea textAreaEnterWordDefinition = new JTextArea();
		scrollPaneEnterWordDefinition.setViewportView(textAreaEnterWordDefinition);

		JPanel panelRemoveWord = new JPanel();
		panelRemoveWord.setBounds(14, 85, 718, 264);
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

		JButton btnRemoveWord = new JButton("Remove");
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

		JTextArea textAreaRemoveWordOutput = new JTextArea();
		scrollPaneRemoveWordOutput.setViewportView(textAreaRemoveWordOutput);
		textAreaRemoveWordOutput.setFont(new Font("Courier New", Font.PLAIN, 15));
		textAreaRemoveWordOutput.setEditable(false);

		btnLookup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});

		// Switch panel when corresponding radio button is selected
		HashMap<JRadioButton, JPanel> mapButtonToPanel = new HashMap<>();
		mapButtonToPanel.put(btnMenuQueryWordMeaning, panelQueryWordMeaning);
		mapButtonToPanel.put(btnMenuAddNewWord, panelAddNewWord);
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

		// Setting the frame visibility to true
		frmSimpleDictionary.setVisible(true);
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