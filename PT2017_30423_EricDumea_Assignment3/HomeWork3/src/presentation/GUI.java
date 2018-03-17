package presentation;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Field;
import com.mysql.jdbc.PreparedStatement;

import bll.*;
import connection.ConnectionFactory;
import model.Client;
import model.Order;
import model.Product;

import com.itextpdf.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64.OutputStream;

/**
 * 
 * @author dumea
 *	@class 
 *
 */

public class GUI implements ActionListener {
	
	private static JFrame c, p, f, o;
	private static JPanel p0,p1,p2,p3, ccontent, pcontent, ocontent;
	private static JButton b0= new JButton("Client");
	private static JButton b1= new JButton("Product");
	private static JButton b2= new JButton("Order");
	
	private JTable table;
	private JTable ptable;
	private JTable ctable;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField ptextField;
	private JTextField ptextField_1;
	private JTextField ptextField_2;
	private JTextField textFieldAmount;
	
	private JButton btnBack1;
	private JComboBox comboBoxP, comboBox1;
	private JButton btnBackProd;
	private JButton btnBackorder;
	
	private JButton btnAddProd;
	private JButton btnDelProd;
	private JButton btnUpdateProd;
	
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	
	private JButton btnOrder;

	
	private int pid=-3;
	
	public DefaultTableModel mp,mc;
	
	 /**
	  * @category constructors
	  * 
	  * Constructor for the GUI
	  */

	public GUI(){
		f= new JFrame("Main Menu");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(350, 100);
		
		p = new JFrame("Products");
		p.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		p.setSize(600, 480);
		
		c = new JFrame("Clients");
		c.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		c.setSize(550, 470);
		
		o = new JFrame("Order");
		o.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		o.setSize(379, 200);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		f.setLocation(dim.width/2-f.getSize().width/2, dim.height/2-f.getSize().height/2);
		c.setLocation(dim.width/2-c.getSize().width/2, dim.height/2-c.getSize().height/2);
		p.setLocation(dim.width/2-p.getSize().width/2, dim.height/2-p.getSize().height/2);
		o.setLocation(dim.width/2-o.getSize().width/2, dim.height/2-o.getSize().height/2);
		
		p0= new JPanel();
		p0.setLayout(new FlowLayout());
		p2= new JPanel();
		p2.setLayout(new FlowLayout());
		p1= new JPanel();
		ccontent = new JPanel();
		//ccontent.setLayout(new BoxLayout(ccontent, BoxLayout.PAGE_AXIS));
		pcontent = new JPanel();
		//pcontent.setLayout(new BoxLayout(pcontent, BoxLayout.PAGE_AXIS));
		pcontent.setLayout(null);
		
		
		p0.add(b0);
		p0.add(b1);
		p0.add(b2);
		
		ccontent.add(p1);
		p1.setLayout(null);
		
		JLabel l2 = new JLabel("Client management");
		l2.setBounds(41, 11, 175, 20);
		p1.add(l2);
		
		//ctable = new JTable();
		customersShow();


		
		btnNewButton = new JButton("Add");
		btnNewButton.setBounds(226, 10, 89, 23);
		p1.add(btnNewButton);
		
		btnNewButton_1 = new JButton("Update");
		btnNewButton_1.setBounds(325, 10, 89, 23);
		p1.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("Delete");
		btnNewButton_2.setBounds(424, 10, 89, 23);
		p1.add(btnNewButton_2);
		
		textField = new JTextField();
		textField.setBounds(345, 79, 168, 20);
		p1.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(347, 164, 166, 20);
		p1.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(345, 250, 168, 20);
		p1.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(390, 300, 50, 20);
		p1.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(345, 64, 46, 14);
		p1.add(lblName);
		
		JLabel lblid = new JLabel("id:");
		lblid.setBounds(390, 286, 46, 14);
		p1.add(lblid);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(345, 149, 46, 14);
		p1.add(lblAddress);
		
		JLabel lblEmai = new JLabel("E-mail");
		lblEmai.setBounds(345, 235, 46, 14);
		p1.add(lblEmai);
		
		btnBack1 = new JButton("Back");
		btnBack1.setBounds(384, 359, 89, 23);
		p1.add(btnBack1);
	
		
		pcontent.setLayout(null);
		
		JLabel l1 = new JLabel("Product management");
		l1.setBounds(24, 11, 218, 20);
		pcontent.add(l1);
		
		productShow();
		
		btnAddProd = new JButton("Add");
		btnAddProd.setBounds(252, 10, 89, 23);
		pcontent.add(btnAddProd);
		
		btnDelProd = new JButton("Delete");
		btnDelProd.setBounds(350, 10, 89, 23);
		pcontent.add(btnDelProd);
		
		btnUpdateProd = new JButton("Update");
		btnUpdateProd.setBounds(450, 10, 89, 23);
		pcontent.add(btnUpdateProd);
		
		btnBackProd = new JButton("Back");
		btnBackProd.setBounds(422, 339, 89, 23);
		pcontent.add(btnBackProd);
		
		ptextField = new JTextField();
		ptextField.setBounds(402, 108, 137, 20);
		pcontent.add(ptextField);
		ptextField.setColumns(10);
		
		ptextField_1 = new JTextField();
		ptextField_1.setBounds(403, 182, 136, 20);
		pcontent.add(ptextField_1);
		ptextField_1.setColumns(10);
		
		ptextField_2 = new JTextField();
		ptextField_2.setBounds(402, 268, 137, 20);
		pcontent.add(ptextField_2);
		ptextField_2.setColumns(10);
		
		JLabel lblProdName = new JLabel("Name:");
		lblProdName.setBounds(402, 91, 46, 14);
		pcontent.add(lblProdName);
		
		JLabel lblStock = new JLabel("Stock");
		lblStock.setBounds(402, 166, 46, 14);
		pcontent.add(lblStock);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(402, 253, 46, 14);
		pcontent.add(lblPrice);
		
		
		ocontent = new JPanel();
		ocontent.setBounds(0, 0, 329, 143);

		ocontent.setLayout(null);
		
		textFieldAmount = new JTextField();
		textFieldAmount.setBounds(206, 30, 89, 20);
		ocontent.add(textFieldAmount);
		textFieldAmount.setColumns(10);
		
		btnOrder = new JButton("Order");
		btnOrder.setBounds(206, 61, 89, 23);
		ocontent.add(btnOrder);
		btnBackorder = new JButton("Back");
		btnBackorder.setBounds(206, 95, 89, 23);
		ocontent.add(btnBackorder);
		
		JLabel lblAmount = new JLabel("Amount:");
		lblAmount.setBounds(206, 11, 57, 14);
		ocontent.add(lblAmount);
		
		comboBox = new JComboBox();
		comboBox.setBounds(30, 30, 139, 20);
		ocontent.add(comboBox);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setBounds(30, 85, 139, 20);
		ocontent.add(comboBox_1);
		
		
		f.setContentPane(p0);
		c.setContentPane(p1);
		p.setContentPane(pcontent);
		o.setContentPane(ocontent);
		
		b0.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		btnBackorder.addActionListener(this);
		btnBack1.addActionListener(this);
		btnBackProd.addActionListener(this);
		btnAddProd.addActionListener(this);
		btnDelProd.addActionListener(this);
		btnUpdateProd.addActionListener(this);
		btnNewButton.addActionListener(this);
		btnNewButton_1.addActionListener(this);
		btnNewButton_2.addActionListener(this);
		btnOrder.addActionListener(this);
		btnBackorder.addActionListener(this);
		
		f.setVisible(true);
	}
	
	private void updatePTable(){
		
		for(Product pr: ProductBLL.selectAll()){
			String[] rowData={Integer.toString(pr.getId()),pr.getName(), Integer.toString(pr.getAmount()), Double.toString(pr.getPrice())};
			mp.addRow(rowData);
		}
	}
	
	private void updateCTable(){
		mc.setRowCount(0);
		
		for(Client cl: ClientBLL.selectAll()){
			String[] rowData={Integer.toString(cl.getId()), cl.getName(), cl.getAddress(), cl.getEmail()};
			mc.addRow(rowData);
		}
	}
	
	
	public static List<Object> retrieveProperties(Object object) 
	{
		Object obj;
		List<Object> props = new ArrayList<>();
		for (java.lang.reflect.Field field : object.getClass().getDeclaredFields()) 
		{
			field.setAccessible(true);
			try
			{
				obj = field.get(object);
				props.add(obj);
			} 
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e) 
			{
				e.printStackTrace();
			}
		}
		return props;
	}
	
	JTable createTable(List<Object> objects, Object columnNames[])
	{
		JTable table = null;
		List<Object> properties = retrieveProperties(objects.get(0)); 
		Object rowData[][] = new Object[objects.size()][properties.size()]; 
		int i = 0; 
		for(Object obj: objects) 
		{
			properties = retrieveProperties(objects.get(i)); 
			int j = 0; 
			for(Object prop: properties)
				rowData[i][j++] = prop; 
			i++;
		}
		table = new JTable(rowData,columnNames);
		return table;
	}
	
	public void customersShow(){
		Connection con = (Connection) ConnectionFactory.getConnection();
		try{
			List<Object> obj = new ArrayList<>();
			Object collumns[] = {"id", "name", "address", "email"};
			
			PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement("SELECT * FROM client");
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				int cid = rs.getInt("id");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String email = rs.getString("email");
				System.out.println(name+address+email);
				Client customer = new Client (cid, name, address, email);
				obj.add(customer);
			}
			ctable = createTable (obj, collumns);
			ctable.setBounds(10, 42, 325, 378);
			p1.add(ctable);

		}
		catch (SQLException e){
			System.out.println("nope, not today.");
		}
	}
	
	public void productShow(){
		Connection con = (Connection) ConnectionFactory.getConnection();
		try{
			List<Object> obj = new ArrayList<>();
			Object collumns[] = {"id", "name", "address", "email"};
			
			PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement("SELECT * FROM product");
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				int cid = rs.getInt("id");
				String name = rs.getString("name");
				double price =Double.parseDouble( rs.getString("price"));
				int amount = Integer.parseInt( rs.getString("amount"));
				System.out.println(name+price+amount);
				Product prod = new Product(cid, name, amount, price);
				obj.add(prod);
			}
			
			ptable = createTable (obj, collumns);
			//mc=(DefaultTableModel) ctable.getModel();
			ptable.setBounds(24, 60, 360, 330);
			pcontent.add(ptable);

		}
		catch (SQLException e){
			System.out.println("O dat fail 1!");
		}
	}
	
	public void fillCombos(){
		try {
			Connection con = (Connection) ConnectionFactory.getConnection();
			PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement("SELECT name FROM client");
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next())
			{
			    
			        comboBox.addItem(rs.getString("name"));
			}
			con = (Connection) ConnectionFactory.getConnection();
			preparedStatement = (PreparedStatement) con.prepareStatement("SELECT name FROM product");
			rs = preparedStatement.executeQuery();
			while (rs.next())
			{
			    	System.out.println(rs.getString("name"));
			        comboBox_1.addItem(rs.getString("name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b0){ // Client
			c.setVisible(true);
			f.setVisible(false);
			
			//updateCTable();
			customersShow();
			ctable.setEnabled(true);
		}else if(e.getSource()==b1){ // Product
			p.setVisible(true);
			f.setVisible(false);
			
			
			
		}else if(e.getSource()==b2){// order
			o.setVisible(true);
			f.setVisible(false);
			fillCombos();
		}else if(e.getSource()==btnBack1){
			f.setVisible(true);
			c.dispose();
		}else if(e.getSource()==btnBackProd){
			f.setVisible(true);
			p.dispose();
			
		}else if(e.getSource()==this.btnBackorder){
			f.setVisible(true);
			o.dispose();
		}else if(e.getSource()==btnAddProd){
			Product produs = new Product(ptextField.getText(), Integer.parseInt(ptextField_1.getText()),Double.parseDouble(ptextField_2.getText()));
			ProductBLL pbll = new ProductBLL();
			pid=pbll.insertProduct(produs);
			productShow();
			ptable.repaint();
			
		}else if(e.getSource()==btnDelProd){
			//Product produs = new Product(ptextField.getText(), Integer.parseInt(ptextField_1.getText()),Double.parseDouble(ptextField_2.getText()));
			ProductBLL pbll = new ProductBLL();
			pbll.delProduct(ptextField.getText());
			productShow();
			ptable.repaint();
			
		}else if(e.getSource()==btnNewButton){
			Client c = new Client(textField.getText(),textField_1.getText(), textField_2.getText());
			ClientBLL cbll = new ClientBLL();
			cbll.insertClient(c);
			customersShow();
			ctable.repaint();
			
		}else if(e.getSource()==btnNewButton_2){
			ClientBLL cbll = new ClientBLL();
			int id=Integer.parseInt(textField_3.getText());
			cbll.delClient(id);
			customersShow();
			ctable.repaint();
			
		}else if(e.getSource()==btnNewButton_1){
			Client c = new Client(Integer.parseInt(textField_3.getText()),textField.getText(),textField_1.getText(), textField_2.getText());
			ClientBLL cbll = new ClientBLL();
			cbll.updateClient(c);
			customersShow();
			ctable.repaint();
			
		}else if(e.getSource()==btnUpdateProd){
			Product produs = new Product(ptextField.getText(), Integer.parseInt(ptextField_1.getText()),Double.parseDouble(ptextField_2.getText()));
			ProductBLL cbll = new ProductBLL();
			cbll.updateProduct(produs);
			productShow();
			ptable.repaint();
		
		}else if(e.getSource()==btnOrder){
			Order o = new Order(comboBox.getSelectedItem().toString(), comboBox_1.getSelectedItem().toString(), Integer.parseInt(textFieldAmount.getText()));
			OrderBLL obll = new OrderBLL();
			
			obll.insertOrder(o);
			
			try {
				File file2=new File("D:\\"+"\\"+"PT\\"+ o.getCname()+ " Bill.pdf");
				
				FileOutputStream file = new FileOutputStream(file2);
				  Document document = new Document();

				PdfWriter.getInstance(document, file);
				document.open();
				document.add(new Paragraph(new Date().toString()));
				document.add(new Paragraph("\n The Client "+ o.getCname()+" has just realized an order consisting of " +o.getAm() + " " +" pices of " + o.getPname() +", having to pay the sum of " + ProductBLL.findProductByName(o.getPname()).getPrice()*o.getAm()));

				document.addTitle(" "+o.getCname()+" Bill");
				document.close();

				file.close();

				if (Desktop.isDesktopSupported())
					Desktop.getDesktop().open(file2);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		
	}
}
