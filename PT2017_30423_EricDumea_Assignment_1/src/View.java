import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class View implements ActionListener {

	JFrame appFrame;
	JLabel firstP,secondP,thirdP;
	JTextField p1,p2,rez;
	JPanel c; //content pane
	
	private JButton addb=new JButton("Add");
	private JButton subb=new JButton("Substract");
	private JButton mulb=new JButton("Multiply");
	private JButton divb=new JButton("Divide");
	private JButton derb=new JButton("Derive P1");
	private JButton intb=new JButton("Integrate P1");
	private JButton clearb=new JButton("Clear");
	private JButton exitb=new JButton("Exit App");
	private JButton infob= new JButton("Info");
	
	public View(){
		appFrame=new JFrame("Polynome Calculator");
		appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		appFrame.setSize(550, 220);
		
		JPanel panel00= new JPanel();
		JPanel panel0 = new JPanel(); //panel0 - label+p1
		JPanel panel1 = new JPanel(); //panel1 - label+p2
		JPanel panel2 = new JPanel(); //panel2 - label+rez
		JPanel panel3 = new JPanel(); //panel3 - buttons
		JPanel panel4 = new JPanel(); //panel4 - exit&clear
		
		panel00.setLayout(new FlowLayout());
		panel0.setLayout(new FlowLayout());
		panel1.setLayout(new FlowLayout());
		panel2.setLayout(new FlowLayout());
		panel3.setLayout(new FlowLayout());
		panel4.setLayout(new FlowLayout());
		
		firstP=new JLabel("First polynomial:");
		secondP=new JLabel("Second polynomial:");
		thirdP= new JLabel("Result:");
		
		p1= new JTextField(20);
		p2= new JTextField(20);
		rez= new JTextField(35);
		rez.setEditable(false);
		
		
		panel0.add(firstP);
		panel0.add(p1);
		
		panel1.add(secondP);
		panel1.add(p2);
		
		panel2.add(thirdP);
		panel2.add(rez);
		
		panel3.add(addb);
		panel3.add(subb);
		panel3.add(mulb);
		panel3.add(divb);
		panel3.add(derb);
		panel3.add(intb);
		
		panel4.add(exitb);
		panel4.add(clearb);
		panel4.add(infob);
		
		c= new JPanel();
		c.setLayout(new BoxLayout(c, BoxLayout.PAGE_AXIS));
		
		//panel00.add(new JLabel("<html><b>The input will be the form cx^e - where c is the coefficient and e the exponent</b></html>"));
		c.add(panel00);
		c.add(panel0);
		c.add(panel1);
		c.add(panel3);
		c.add(panel2);
		c.add(panel4);
		
		appFrame.setContentPane(c);
		
		addb.addActionListener(this);
		subb.addActionListener(this);
		mulb.addActionListener(this);
		divb.addActionListener(this);
		derb.addActionListener(this);
		intb.addActionListener(this);
		clearb.addActionListener(this);
		exitb.addActionListener(this);
		infob.addActionListener(this);
		
		
		
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==addb){
			addP();
		} else if(e.getSource()==subb){
			subP();
		} else if(e.getSource()==mulb){
			mulP();
		} else if(e.getSource()==divb){
			divP();
		} else if(e.getSource()==derb){
			derP();
		} else if(e.getSource()==intb){
			intP();
		} else if(e.getSource()==clearb){
			clear();
		} else if(e.getSource()==exitb){
			System.exit(0);
		} else if(e.getSource()==infob){
			JOptionPane.showMessageDialog(c, "The input will be the form cx^e - where c is the coefficient and e the exponent.\nThe Division and Integration work "
					+ "only on the polynomials introduced in the first text box.", "Info", 3);
		}
	}
	
	private void clear() {
		rez.setText("");
		p1.setText("");
		p2.setText("");
	}

	private void intP() {
		Polynome a= new Polynome(p1.getText());
		String c= Operation.integrate(a);
		rez.setText(c);
	}

	private void derP() {
		Polynome a= new Polynome(p1.getText());
		Polynome c= Operation.derive(a);
		rez.setText(c.printPoly());
	}

	private void divP() {
		Polynome a= new Polynome(p1.getText());
		Polynome b= new Polynome(p2.getText());
		//Polynome a= new Polynome("12x^4-6x^3+2x^1+7x^0");
		//Polynome b= new Polynome("3x^2+2x^0");
		String c= Operation.divide(a,b);
		rez.setText(c);
	}

	private void mulP() {
		Polynome a= new Polynome(p1.getText());
		Polynome b= new Polynome(p2.getText());
		Polynome c= Operation.multiply(a, b);
		rez.setText(c.printPoly());
	}

	private void subP() {
		Polynome a= new Polynome(p1.getText());
		Polynome b= new Polynome(p2.getText());
		Polynome c= Operation.sub(a, b);
		rez.setText(c.printPoly());
	}

	public void addP(){
		Polynome a= new Polynome(p1.getText());
		Polynome b= new Polynome(p2.getText());
		Polynome c= Operation.add(a, b);
		rez.setText(c.printPoly());
	}
	
	public static void testing(){
		Polynome a= new Polynome("12x^4-6x^3+2x^1+7x^0");
		Polynome b= new Polynome("3x^2+2x^0");
		
		System.out.println("a:  "+a.printPoly());
		System.out.println("b:  "+b.printPoly());
		System.out.println("");
		Polynome c=Operation.add(a, b);
		System.out.println("add:  "+c.printPoly());
		
		c=Operation.sub(a, b);
		System.out.println("sub:  "+c.printPoly());
		
		c=Operation.derive(a);
		System.out.println("der:  "+c.printPoly());
		
		System.out.println("div:  "+Operation.divide(a, b));
		System.out.println("int: "+Operation.integrate(a));
		
		c=Operation.multiply(a,b);
		System.out.println("mul:  "+c.printPoly());
	}
	
	public static void main(String[] args) {
		View v=new View();
		v.appFrame.setVisible(true);
		//testing();
		
	}
}
