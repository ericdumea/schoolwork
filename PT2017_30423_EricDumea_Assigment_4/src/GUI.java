import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;



public class GUI implements ActionListener,MouseListener {
	public static JFrame  a, f;
	private JTable ctable;
	private JTextField textName;
	private JTextField textcnp;
	private JTextField textAddr;
	public DefaultTableModel ma,mc;
	private JTable atable;
	private JTextField textSum;
	private JTextField textBalance;
	private JComboBox cbPerson;
	private JComboBox cbType;
	private JButton btnAddClient, btnRemoveClient, btnUpdateClient,btnAddAcc,btnRemoveAcc,btnWithdraw,btnDeposit;
	Bank bank;
	private MouseListener ml;
	private int selectedid = -4;
	private long selectedCNP = -9;
	
	GUI(Bank b){
		f=new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")));
		f.setTitle("Client");
		f.getContentPane().setLayout(null);
		f.setSize(540, 428);
		
		btnAddClient = new JButton("Add");
		btnAddClient.setBounds(297, 20, 51, 23);
		f.getContentPane().add(btnAddClient);
		
		ctable = new JTable();
		ctable.setBounds(10, 24, 278, 354);
		f.getContentPane().add(ctable);
		
		btnRemoveClient = new JButton("Remove");
		btnRemoveClient.setBounds(358, 20, 71, 23);
		f.getContentPane().add(btnRemoveClient);
		
		btnUpdateClient = new JButton("Update");
		btnUpdateClient.setBounds(439, 20, 74, 23);
		f.getContentPane().add(btnUpdateClient);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(297, 99, 46, 14);
		f.getContentPane().add(lblName);
		
		textName = new JTextField();
		textName.setBounds(297, 112, 216, 20);
		f.getContentPane().add(textName);
		textName.setColumns(10);
		
		JLabel lblSocialSecurityNumber = new JLabel("Social Security Number (CNP)");
		lblSocialSecurityNumber.setBounds(297, 162, 148, 14);
		f.getContentPane().add(lblSocialSecurityNumber);
		
		textcnp = new JTextField();
		textcnp.setBounds(297, 187, 216, 20);
		f.getContentPane().add(textcnp);
		textcnp.setColumns(10);
		
		textAddr = new JTextField();
		textAddr.setBounds(297, 259, 216, 20);
		f.getContentPane().add(textAddr);
		textAddr.setColumns(10);
		
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setBounds(297, 240, 46, 14);
		f.getContentPane().add(lblAddress);
		
		
		a = new JFrame("Account");
		a.getContentPane().setLayout(null);
		a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		a.setSize(456, 428);
		
		atable = new JTable();
		atable.setBounds(10, 56, 223, 322);
		a.getContentPane().add(atable);
		a.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")));		
		
		cbPerson = new JComboBox();
		cbPerson.setBounds(10, 11, 223, 20);
		a.getContentPane().add(cbPerson);
		
		btnAddAcc = new JButton("Add");
		btnAddAcc.setBounds(243, 10, 89, 23);
		a.getContentPane().add(btnAddAcc);
		
		btnRemoveAcc = new JButton("Remove");
		btnRemoveAcc.setBounds(342, 10, 89, 23);
		a.getContentPane().add(btnRemoveAcc);
		
		btnWithdraw = new JButton("Withdraw");
		btnWithdraw.setBounds(243, 344, 89, 23);
		a.getContentPane().add(btnWithdraw);
		
		btnDeposit = new JButton("Deposit");
		btnDeposit.setBounds(342, 344, 89, 23);
		a.getContentPane().add(btnDeposit);
		
		textSum = new JTextField();
		textSum.setBounds(271, 303, 128, 20);
		a.getContentPane().add(textSum);
		textSum.setColumns(10);
		
		JLabel lblSum = new JLabel("Sum:");
		lblSum.setBounds(250, 278, 46, 14);
		a.getContentPane().add(lblSum);
		
		JLabel lblType = new JLabel("Type:");
		lblType.setBounds(250, 56, 46, 14);
		a.getContentPane().add(lblType);
		
		cbType = new JComboBox();
		cbType.setBounds(271, 81, 128, 20);
		a.getContentPane().add(cbType);
		
		JLabel lblBalance = new JLabel("Balance:");
		lblBalance.setBounds(250, 125, 46, 14);
		a.getContentPane().add(lblBalance);
		
		textBalance = new JTextField();
		textBalance.setBounds(268, 150, 131, 20);
		a.getContentPane().add(textBalance);
		textBalance.setColumns(10);
		
		btnAddAcc.addActionListener(this);
		btnAddClient.addActionListener(this);
		btnDeposit.addActionListener(this);
		btnRemoveAcc.addActionListener(this);
		btnRemoveClient.addActionListener(this);
		btnUpdateClient.addActionListener(this);
		btnWithdraw.addActionListener(this);
		cbPerson.addActionListener(this);
		atable.addMouseListener(this);
		ctable.addMouseListener(this);
		
		this.bank = b;
		mc=new DefaultTableModel();
		mc.addColumn("SSN/CNP");
		mc.addColumn("Name");
		mc.addColumn("Address");
		ctable.setModel(mc);
		
		ma=new DefaultTableModel();
		ma.addColumn("ID");
		ma.addColumn("Balance");
		ma.addColumn("Type");
		atable.setModel(ma);
		
		updateCtable();
		updatePersonCB();
		updateAtable();
		
		
		
		a.setVisible(true);
		f.setVisible(true);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==cbPerson){
			updateAtable();
		}else if(e.getSource()==btnAddAcc){
			addAcc();
		}else if(e.getSource()==this.btnAddClient){
			addClient();
		}else if(e.getSource()==this.btnRemoveAcc){
			remAcc();
		}else if(e.getSource()==this.btnWithdraw){
			withdraw();
		}else if(e.getSource()==this.btnDeposit){
			deposit();
		}else if(e.getSource()==this.btnRemoveClient){
			remClient();
		}else if(e.getSource() == this.btnUpdateClient){
			editClient();
		}
	}
	
	public void editClient(){
		for(Map.Entry<Person,ArrayList<Account> > entry : bank.getA().entrySet()){
			Person p=entry.getKey();
			if(p.getCnp() == selectedCNP && selectedCNP!=-9){
				p.setAddr(this.textAddr.getText());
			}
		}
		updatePersonCB();
		Start.ss(bank);
	}
	
	public void remClient(){
		for(Map.Entry<Person,ArrayList<Account> > entry : bank.getA().entrySet()){
			Person p=entry.getKey();
			if(p.getCnp() == selectedCNP && selectedCNP!=-9){
				bank.getA().remove(p);
			}
		}
		updatePersonCB();
		Start.ss(bank);
	}
	
	public void deposit(){
		for(Map.Entry<Person,ArrayList<Account> > entry : bank.getA().entrySet()){
			Person p=entry.getKey();
			if(p.getName()==(String)cbPerson.getSelectedItem()){
				if(this.selectedid!=-4){
					bank.dMoney(selectedid, p,Double.parseDouble(this.textSum.getText()));
				}
					
			}
		}
		updateAtable();
		Start.ss(bank);
	}
	
	public void withdraw(){
		for(Map.Entry<Person,ArrayList<Account> > entry : bank.getA().entrySet()){
			Person p=entry.getKey();
			if(p.getName()==(String)cbPerson.getSelectedItem()){
				if(this.selectedid!=-4){
					bank.wMoney(selectedid, p,Double.parseDouble(this.textSum.getText()));
				}
					
			}
		}
		updateAtable();
		Start.ss(bank);
	}
	
	public void addClient(){
		bank.addPerson(Long.parseLong(this.textcnp.getText()),textName.getText(),textAddr.getText());
		updateCtable();
		updatePersonCB();
		Start.ss(bank);
	}
	
	public void updateCtable(){
		textcnp.setText("");
		textName.setText("");
		textAddr.setText("");
		mc.setRowCount(0);
		
		for(Map.Entry<Person,ArrayList<Account> > entry : bank.getA().entrySet()){
			Person p=entry.getKey();
			String[] rowData={Long.toString(p.getCnp()),p.getName(),p.getAddr()};
			mc.addRow(rowData);
		}
	}
	
	public void updatePersonCB(){
		cbPerson.removeAllItems();
		for(Map.Entry<Person,ArrayList<Account> > entry : bank.getA().entrySet()){
			Person p=entry.getKey();
			cbPerson.addItem(p.getName());
		}
	}
	
	public void addAcc(){
		for(Map.Entry<Person,ArrayList<Account> > entry : bank.getA().entrySet()){
			Person p=entry.getKey();
			if(p.getName()==(String)cbPerson.getSelectedItem()){
				bank.addAcc((String)cbType.getSelectedItem(), Double.parseDouble(textBalance.getText()), p);
				updateAtable();
				Start.ss(bank);
			}
		}
	}
	
	public void accData(){
		if(!(selectedid==-4 || selectedCNP==-9)){
		int idd =Integer.parseInt( (String) ma.getValueAt(atable.getSelectedRow(), 0));
		this.selectedid=idd;
		this.selectedCNP = Long.parseLong( (String) mc.getValueAt(ctable.getSelectedRow(), 0));
		}
		else{
			int idd =Integer.parseInt( (String) ma.getValueAt(0, 0));
			this.selectedid=idd;
			this.selectedCNP = Long.parseLong( (String) mc.getValueAt(0, 0));
		}
		System.out.println(selectedCNP+ " "+selectedid);
	}
	
	public void updateAtable(){
		cbType.removeAllItems();
		cbType.addItem("Choose one");
		cbType.addItem("Saving");
		cbType.addItem("Spending");
		textBalance.setText("");
		textSum.setText("");
		ma.setRowCount(0);
		
		for(Map.Entry<Person,ArrayList<Account> > entry : bank.getA().entrySet()){
			Person p=entry.getKey();
			if(p.getName()==(String)cbPerson.getSelectedItem()){
				for(Account x : bank.getA().get(p)){
					String[] rowData={Integer.toString(x.getId()), x.getType(), Double.toString(x.getSum())};
					ma.addRow(rowData);
				}
			}
		}
		
	}
	
	public void remAcc(){
		for(Map.Entry<Person,ArrayList<Account> > entry : bank.getA().entrySet()){
			Person p=entry.getKey();
			if(p.getName()==(String)cbPerson.getSelectedItem()){
				if(this.selectedid!=-4){
					bank.removeAcc(selectedid, p);
				}
					
			}
		}
		updateAtable();
		Start.ss(bank);
	}



	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		accData();
		
	}



	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

