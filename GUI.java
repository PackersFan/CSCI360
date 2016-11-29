import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
/*TODO: ADD STUFF TO ALL BUTTONS Ctrl+F JButtons
 * CHANGE DIMENSIONS EVENTUALLY Ctrl+F setBounds
 * ADD getTallys,printResults to adminMenu() and implement them outside.
*/
public class GUI {	
	
	static JFrame myFrame;	
	
	
	public static void makeGUI(){
		
	
		Container myPanel = myFrame.getContentPane();
		myPanel.setLayout(null);
		
		myFrame.setSize(1280, 720);

		myPanel.add(new JLabel("Welcome to the electronic voting system"));
		
		JMenuBar myMenuBar = new JMenuBar();
		myFrame.setJMenuBar(myMenuBar);
		

		
		JButton adminButton = new JButton("Admin menu");
		adminButton.setVerticalTextPosition(AbstractButton.CENTER);
		adminButton.setHorizontalTextPosition(AbstractButton.CENTER);
		adminButton.setBounds(50,50,200,200);
		adminButton.setToolTipText("This brings up the admin menu");
		adminButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				adminMenu();
			}
		});
		
		
		myPanel.add(adminButton);
		myFrame.getContentPane().setLayout(null);		
		myFrame.setVisible(true);	
		
	}
	
	public static void adminMenu(){
		
		JDialog dialog = new JDialog(myFrame, "Admin menu", true);
		dialog.setSize(1280,720);		
		dialog.setLocationRelativeTo(myFrame);
		
		JButton regVoter = new JButton("Register Voter");
		regVoter.setVerticalTextPosition(AbstractButton.CENTER);
		regVoter.setHorizontalTextPosition(AbstractButton.CENTER);
		regVoter.setBounds(50,50,200,200);
		regVoter.setToolTipText("This brings up menu to register a new voter.");
		regVoter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				registerVoter();				
			}
		});
		
		JButton check = new JButton("Check registration");
		check.setVerticalTextPosition(AbstractButton.CENTER);
		check.setHorizontalTextPosition(AbstractButton.CENTER);
		check.setBounds(50,300,200,200);
		check.setToolTipText("This brings up menu to check registration of a voter.");
		check.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				checkRegistration();
			}
		});
		
		JButton tally = new JButton("Get tally");
		tally.setHorizontalTextPosition(AbstractButton.CENTER);
		tally.setHorizontalTextPosition(AbstractButton.CENTER);
		tally.setBounds(300, 50, 200, 200);
		tally.setToolTipText("This shows the current standings.");
		
		JButton print = new JButton("Print results");
		print.setHorizontalTextPosition(AbstractButton.CENTER);
		print.setHorizontalTextPosition(AbstractButton.CENTER);
		print.setBounds(300, 300, 200, 200);
		print.setToolTipText("This prints the results.");
		
		JButton ballot = new JButton("New ballot");
		ballot.setHorizontalTextPosition(AbstractButton.CENTER);
		ballot.setHorizontalTextPosition(AbstractButton.CENTER);
		ballot.setBounds(550, 50, 200, 200);
		ballot.setToolTipText("This brings up menu to create new ballot.");
		
		JButton candidate = new JButton("Add candidate");
		candidate.setHorizontalTextPosition(AbstractButton.CENTER);
		candidate.setHorizontalTextPosition(AbstractButton.CENTER);
		candidate.setBounds(550, 300, 200, 200);
		candidate.setToolTipText("Brings up menu to add a new candidate.");
		
		dialog.add(regVoter);		
		dialog.add(check);
		dialog.add(tally);
		dialog.add(print);
		dialog.add(ballot);
		dialog.add(candidate);
		dialog.getContentPane().setLayout(null);
		dialog.setVisible(true);
	}
	
	public static void registerVoter(){
		JDialog dialog = new JDialog(myFrame, "Register voter", true);
		dialog.setSize(1280, 720);
		dialog.setLocationRelativeTo(myFrame);
		dialog.setLayout(null);
		dialog.getContentPane().setLayout(null);
		
		JLabel first = new JLabel("First name: ");
		first.setBounds(10,10, 200,50);
		JLabel last = new JLabel("Last name: ");
		last.setBounds(10,70, 200,50);
		JLabel password = new JLabel("Social Security(no hyphens): ");
		password.setBounds(10,130, 200,50);
		
		JTextField fFirst = new JTextField();
		JTextField fLast = new JTextField();
		JTextField fPassword = new JTextField(9);
		fFirst.setBounds(250, 10, 200, 30);
		fLast.setBounds(250, 70, 200, 30);
		fPassword.setBounds(250, 130, 100, 30);
		
		JButton register = new JButton("Register voter");
		register.setHorizontalTextPosition(AbstractButton.CENTER);
		register.setVerticalTextPosition(AbstractButton.CENTER);
		register.setBounds(490, 500, 300, 100);
		
		
		
		dialog.add(first);
		dialog.add(last);
		dialog.add(password);
		dialog.add(register);	
		dialog.add(fFirst);
		dialog.add(fLast);
		dialog.add(fPassword);
		dialog.setVisible(true);
	}
	
	public static void checkRegistration(){
		JDialog dialog = new JDialog(myFrame, "Check registration", true);
		dialog.setSize(1280, 720);
		dialog.setLocationRelativeTo(myFrame);
		dialog.setLayout(null);
		dialog.getContentPane().setLayout(null);
		
		JLabel text = new JLabel("Voter ID: ");
		text.setBounds(50,20, 200,100);
		
		JTextField fText = new JTextField();
		fText.setBounds(250, 50, 200,30);
		
		JButton check = new JButton("Check registration");
		check.setHorizontalTextPosition(AbstractButton.CENTER);
		check.setHorizontalTextPosition(AbstractButton.CENTER);
		check.setBounds(490,500,300,100);
		
		dialog.add(text);
		dialog.add(fText);
		dialog.add(check);
		dialog.setVisible(true);
	}

	public static void main(String[] args){
		myFrame = new JFrame("Electronic Voting System");
		myFrame.getContentPane().setLayout(null);
		myFrame.setLayout(null);		
		makeGUI();
		
	
	}
	

}
