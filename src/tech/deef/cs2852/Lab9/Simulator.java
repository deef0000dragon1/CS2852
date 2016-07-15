package tech.deef.cs2852.Lab9;

import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Simulator extends JFrame implements KeyListener {

	private DNS dns;
	private static Stack<String> undoStack;
	private Stack<String> redoStack;

	private static final long serialVersionUID = 1L;
	private static JButton startButton;
	private static JButton stopButton;
	private static JButton updateButton;
	private static JButton addButton;
	private static JButton deleteButton;
	private static JButton undoButton;
	private static JButton redoButton;
	private static JButton exitButton;
	private static JFormattedTextField domainTextField;
	private static JTextField IPTextField;
	private static JLabel domainLabel;
	private static JLabel IPLabel;
	private static Simulator Sim;
	private JPanel topButtonPannel;
	private JPanel domainTextFieldPannel;
	private JPanel IPTextFieldPannel;
	private JPanel bottomButtonPannel;

	public static void main(String[] args) {
		Sim = new Simulator();
		Sim.createGui();
	}

	public Simulator() {
		super();
		undoStack = new Stack<String>();
		redoStack = new Stack<String>();
	}

	private void createGui() {
		startButton = new JButton();
		stopButton = new JButton();
		updateButton = new JButton();
		addButton = new JButton();
		deleteButton = new JButton();
		undoButton = new JButton();
		redoButton = new JButton();
		exitButton = new JButton();

		domainTextField = new JFormattedTextField();
		IPTextField = new JTextField();

		domainLabel = new JLabel();
		IPLabel = new JLabel();

		topButtonPannel = new JPanel();
		domainTextFieldPannel = new JPanel();
		IPTextFieldPannel = new JPanel();
		bottomButtonPannel = new JPanel();
		FlowLayout topButtonLayout = new FlowLayout();
		FlowLayout domainTextFieldLayout = new FlowLayout(FlowLayout.TRAILING);
		FlowLayout IPTextFieldLayout = new FlowLayout(FlowLayout.TRAILING);
		FlowLayout bottomButtonLayout = new FlowLayout();
		FlowLayout fullLayout = new FlowLayout();

		// set the pannels with theri respective layouts
		topButtonPannel.setLayout(topButtonLayout);
		domainTextFieldPannel.setLayout(domainTextFieldLayout);
		IPTextFieldPannel.setLayout(IPTextFieldLayout);
		bottomButtonPannel.setLayout(bottomButtonLayout);

		// set item properties.
		{
			domainTextField.addKeyListener(this);

			startButton.setText("Start");
			startButton.addActionListener(e -> {
				startTrigger();
			});

			stopButton.setText("Stop");
			stopButton.addActionListener(e -> {
				stopTrigger();
			});

			updateButton.setText("Update");
			updateButton.addActionListener(e -> {
				updateTrigger();
			});

			domainTextField.setColumns(21);
			// unsure how to limit character count
			IPTextField.setColumns(21);

			domainLabel.setText("Domain Name:");
			IPLabel.setText("IP Address:      ");

			addButton.setText("Add");
			addButton.addActionListener(e -> {
				addTrigger();
			});

			deleteButton.setText("Delete");
			deleteButton.addActionListener(e -> {
				deleteTrigger();
			});

			undoButton.setText("Undo");
			undoButton.addActionListener(e -> {
				undoTrigger();
			});

			redoButton.setText("Redo");
			redoButton.addActionListener(e -> {
				redoTrigger();
			});

			exitButton.setText("Exit");
			exitButton.addActionListener(e -> {
				exitTrigger();
			});
		}

		{// add items to the layouts.
			// top Line
			topButtonPannel.add(startButton);
			topButtonPannel.add(stopButton);
			topButtonPannel.add(updateButton);
			// second Line
			domainTextFieldPannel.add(domainLabel);
			domainTextFieldPannel.add(domainTextField);
			// Third Line
			IPTextFieldPannel.add(IPLabel);
			IPTextFieldPannel.add(IPTextField);
			// Bottom Line
			bottomButtonPannel.add(addButton);
			bottomButtonPannel.add(deleteButton);
			bottomButtonPannel.add(undoButton);
			bottomButtonPannel.add(redoButton);
			bottomButtonPannel.add(exitButton);
		} // end adding items to layout lines.

		// set main layout and add the levels.
		setLayout(fullLayout);
		add(topButtonPannel);
		add(domainTextFieldPannel);
		add(IPTextFieldPannel);
		add(bottomButtonPannel);

		// set button states at startup.
		stopButton.setEnabled(false);
		updateButton.setEnabled(false);
		addButton.setEnabled(false);
		deleteButton.setEnabled(false);
		undoButton.setEnabled(false);
		redoButton.setEnabled(false);
		domainTextField.setEnabled(false);
		IPTextField.setEnabled(false);

		setResizable(false);
		setSize(360, 200);
		setVisible(true);
		setTitle("DNS SERVER");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	private void startTrigger() {
		dns = new DNS(fileFinder("Enter the DNS file location.").getAbsolutePath());
		if (dns.start()) {
			stopButton.setEnabled(true);
			updateButton.setEnabled(true);
			addButton.setEnabled(true);
			deleteButton.setEnabled(true);
			domainTextField.setEnabled(true);
			IPTextField.setEnabled(true);
			startButton.setEnabled(false);
		}
	}

	private void stopTrigger() {

		if (dns.stop()) {
			startButton.setEnabled(true);
			stopButton.setEnabled(false);
			updateButton.setEnabled(false);
			addButton.setEnabled(false);
			deleteButton.setEnabled(false);
			undoButton.setEnabled(false);
			redoButton.setEnabled(false);
			domainTextField.setEnabled(false);
			IPTextField.setEnabled(false);
		}
	}

	private void updateTrigger() {
		File updateFile = fileFinder("Enter the update File");

		Scanner s = null;

		try {
			s = new Scanner(updateFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (s.hasNextLine()) {
			dns.update(s.nextLine());
		}

	}

	private void addTrigger() {
		String domain = domainTextField.getText();
		String ip = IPTextField.getText();
		String cmd = ("ADD " + ip + " " + domain);
		undoStack.push(cmd);
		undoButton.setEnabled(true);
	}

	private void deleteTrigger() {
		String domain = domainTextField.getText();
		String ip = IPTextField.getText();
		String cmd = ("DEL " + ip + " " + domain);
		undoStack.push(cmd);
		undoButton.setEnabled(true);
	}

	private void undoTrigger() {
		if (!undoStack.isEmpty()) {
			String cmd = undoStack.pop();
			String undoCmd = "";
			if (cmd.indexOf("ADD") != -1) {
				undoCmd = "DEL" + cmd.substring(3);
				dns.update(undoCmd);
				redoButton.setEnabled(true);
				redoStack.push(cmd);
			} else if (cmd.indexOf("DEL") != -1) {
				undoCmd = "ADD" + cmd.substring(3);
				dns.update(undoCmd);
				redoButton.setEnabled(true);
				redoStack.push(cmd);
			}

		}
		if (!undoStack.isEmpty()) {
			undoButton.setEnabled(false);
		}

	}

	private void redoTrigger() {
		if (!redoStack.isEmpty()) {
			String cmd = redoStack.pop();
			dns.update(cmd);
			undoStack.push(cmd);
		}
		if (undoStack.isEmpty()) {
			redoButton.setEnabled(false);
		}
	}

	private void exitTrigger() {

		stopTrigger();
		System.exit(NORMAL);
	}

	private static File fileFinder(String titleText) {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".TXT", "txt");
		chooser.setFileFilter(filter);
		chooser.setDialogTitle(titleText);
		int returnVal = chooser.showOpenDialog(null);
		// gets the file using a file chooser
		// lists must be initalized once for addition of points to work.
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return new File(chooser.getSelectedFile().getPath());
		} else {
			return null;
		}
	}

	@Override
	public void keyPressed(KeyEvent key) {
		if (key.getKeyCode() == KeyEvent.VK_ENTER) {
			updateTrigger();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}
}
