package coolfrostdrags;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JButton;


public class GUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static JTextField textField;
	public static JTextField textField_1;
	public static JTextField textField_2;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		
		//LABELS ===================================================================================================
		setTitle("Coolpopo's Frost Dragon Killer");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFrostDragonSettings = new JLabel("Frost Dragon Settings");
		lblFrostDragonSettings.setFont(new Font("Impact", Font.PLAIN, 18));
		lblFrostDragonSettings.setBounds(251, 11, 160, 26);
		contentPane.add(lblFrostDragonSettings);
		
		JLabel lblMadeByJosh = new JLabel("MADE BY Coolpopo");
		lblMadeByJosh.setFont(new Font("Segoe Script", Font.PLAIN, 19));
		lblMadeByJosh.setBounds(10, 225, 231, 26);
		contentPane.add(lblMadeByJosh);
		
		JLabel lblInformation = new JLabel("INFORMATION");
		lblInformation.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblInformation.setBounds(34, 21, 143, 14);
		contentPane.add(lblInformation);
		
		JLabel lblHpToEat = new JLabel("Hp to Eat");
		lblHpToEat.setBounds(318, 51, 57, 14);
		contentPane.add(lblHpToEat);
		
		JLabel lblNewLabel = new JLabel("1. Enter a number between 1-99 for hp to eat\r\n");
		lblNewLabel.setBounds(10, 76, 231, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblMakeSure = new JLabel("2. Make sure that Sharks and pots are in the ");
		lblMakeSure.setBounds(10, 101, 231, 14);
		contentPane.add(lblMakeSure);
		
		JLabel lblFirstSlotOf = new JLabel("FIRST slot of your bank");
		lblFirstSlotOf.setBounds(20, 114, 198, 14);
		contentPane.add(lblFirstSlotOf);
		
		JLabel label = new JLabel("");
		label.setBounds(10, 150, 46, 14);
		contentPane.add(label);
		
		JLabel lblYouCanUse = new JLabel("3. You can use range but does not use ");
		lblYouCanUse.setBounds(10, 136, 197, 14);
		contentPane.add(lblYouCanUse);
		
		JLabel lblRangePots = new JLabel("range pots");
		lblRangePots.setBounds(20, 150, 119, 14);
		contentPane.add(lblRangePots);
		
		JLabel lblSharksTo = new JLabel(" # sharks to withdraw");
		lblSharksTo.setBounds(308, 76, 116, 14);
		contentPane.add(lblSharksTo);
		
		JLabel lblRenewalsTo = new JLabel("# renewals to withdraw");
		lblRenewalsTo.setBounds(318, 101, 116, 14);
		contentPane.add(lblRenewalsTo);
		
		// TEXTFIELD =================================================================
		
		textField = new JTextField();
		textField.setBounds(251, 48, 57, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		
		
		textField_1 = new JTextField();
		textField_1.setBounds(251, 73, 57, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		
		
		textField_2 = new JTextField();
		textField_2.setBounds(251, 98, 57, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		
		
		// CHECKBOX ===============================================================
		
		final JCheckBox chckbxUseDung = new JCheckBox("Use 100 Dung Area");
		chckbxUseDung.setBounds(251, 174, 119, 23);
		contentPane.add(chckbxUseDung);
		
		final JCheckBox chckbxUseQuickPrayers = new JCheckBox("Use Quick Prayers");
		chckbxUseQuickPrayers.setBounds(251, 148, 124, 23);
		contentPane.add(chckbxUseQuickPrayers);
		
		// BUTTON ====================================================================
		JButton start = new JButton("Start");
		start.setBounds(251, 216, 119, 23);
		
		start.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent evt) {
			
				try {
					int hpToEat = Integer.parseInt(GUI.textField.getText());
					int numSharksWithdraw = Integer.parseInt(GUI.textField_1.getText());
					int numRenewalsWithdraw = Integer.parseInt(GUI.textField_2.getText());
					Frost.hp = hpToEat;
					Frost.numFood = numSharksWithdraw;
					Frost.numPrayer = numRenewalsWithdraw;
					} catch(NumberFormatException nfe) {
						GUI.textField.setText("");
						GUI.textField_1.setText("");
						GUI.textField_2.setText("");
					}
				if (chckbxUseDung.isSelected()){
					Frost.use100Dung = true;
				}
				if (chckbxUseQuickPrayers.isSelected()){
					Frost.useQuickPrayer = true;
				}
			        setVisible(false);
					
			 
			}
			
		});		
		
		contentPane.add(start);				
		
	
		
	
	}	
}
