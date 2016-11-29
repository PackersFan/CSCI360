import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
/*TODO: ADD STUFF TO registerVoter()
 * 
 * 
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
		check.setBounds(50,400,200,200);
		check.setToolTipText("This brings up menu to check registration of a voter.");
		check.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				checkRegistration();
			}
		});
		
		dialog.add(regVoter);
		dialog.add(check);
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
		text.setBounds(50,50, 200,100);
		
		dialog.add(text);
		dialog.setVisible(true);
	}

	public static void main(String[] args){
		myFrame = new JFrame("Electronic Voting System");
		myFrame.getContentPane().setLayout(null);
		myFrame.setLayout(null);
		//registerVoter();
		makeGUI();
		
	
	}
	

}
