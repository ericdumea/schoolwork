import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class GUI implements ActionListener {

	public static JFrame f,aux;
	public static JPanel p0;
	private JButton b0=new JButton("Exit");
	private JButton b1=new JButton("Start!");
	private JButton b2=new JButton("Stats");
	private JPanel panel00= new JPanel();
	private JPanel panel0 = new JPanel(); 
	private JPanel panel1 = new JPanel(); 
	private JPanel panel2 = new JPanel(); 
	private JPanel panel3 = new JPanel(); 
	private JPanel panel4 = new JPanel();
	private JPanel panel1aux=new JPanel();
	private JPanel panel2aux=new JPanel();
	private JPanel caux= new JPanel();
	public static  JProgressBar pb[]= new JProgressBar[10];
	public static JTextArea ta = new JTextArea();
	private Model m;
	//private JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9;
	private JTextField min_st, max_st, min_arr, max_arr, sim_time,queue_nr;
	
	int min_st_int;
	int max_st_int;
	int min_arr_int;
	int max_arr_int;
	int sim_time_int;
	int queue_nr_int;
	
	public GUI(){
		f= new JFrame("QueueSim v1.0");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(600, 400);
		
		p0= new JPanel();
		p0.setLayout(new FlowLayout());
		panel00.setLayout(new FlowLayout());
		panel0.setLayout(new FlowLayout());
		panel1.setLayout(new FlowLayout());
		panel2.setLayout(new FlowLayout());
		panel3.setLayout(new FlowLayout());
		panel4.setLayout(new FlowLayout());
		
		//l1=new JLabel("Simulation Interval: ");
		//l2=new JLabel("Arriving time: Min:");
		//l3=new JLabel("Service time: Min:");
		//l4=new JLabel("Nr of queues: ");
		
		min_st= new JTextField(4);
		max_st= new JTextField(4);
		min_arr= new JTextField(4);
		max_arr= new JTextField(4);
		sim_time= new JTextField(6);
		queue_nr= new JTextField(4);
		
		
		JScrollPane scroll = new JScrollPane (ta);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setPreferredSize(new Dimension(400, 150));

		ta.setText("");
		
		
		panel1.add(new Label("Sim Time: "));
		panel1.add(sim_time);
		
		panel1.add(new Label(" Client nr: "));
		panel1.add(this.queue_nr);
		panel1.add(b1);
		panel1.add(b2);
		panel1.add(b0);
		
		panel2.add(new Label("Min Arrival Time: "));
		panel2.add(this.min_arr);
		panel2.add(new Label(" Max Arrival Time: "));
		panel2.add(this.max_arr);
		
		panel3.add(new Label("Min Service Time: "));
		panel3.add(min_st);
		panel3.add(new Label(" Max Service Time: "));
		panel3.add(max_st);
		
		p0.setLayout(new BoxLayout(p0, BoxLayout.PAGE_AXIS));
		
		p0.add(panel1);
		p0.add(panel2);
		p0.add(panel3);
		p0.add(panel00);
		p0.add(scroll);
		f.setContentPane(p0);
		
		aux=new JFrame("Stats");
		aux.setSize(200,200);
		aux.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		caux.add(new JLabel(""));
		caux.setLayout(new FlowLayout());
		aux.setContentPane(caux);
		
		b0.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		
		//test
		min_st.setText("10");
		max_st.setText("15");
		min_arr.setText("1");
		max_arr.setText("5");
		sim_time.setText("10000");
		queue_nr.setText("4");
		
		
		
		f.pack();
		f.setVisible(true);
	}
	
	public void getData(){
		min_st_int=Integer.parseInt(min_st.getText());
		max_st_int=Integer.parseInt(max_st.getText());;
		min_arr_int=Integer.parseInt(min_arr.getText());;
		max_arr_int=Integer.parseInt(max_st.getText());;
		sim_time_int=Integer.parseInt(sim_time.getText());;
		queue_nr_int=Integer.parseInt(queue_nr.getText());;
		
	}
	
	public static void updatep(int i, int size){
		pb[i].setValue(size);
		p0.add(pb[i]);
		p0.revalidate();
		p0.repaint();
		pb[i].setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b0){
			System.exit(0);
		}else if(e.getSource()==b1){
			getData();
			int i;
			int client_nr=30;
			//int qnr=4;
			Queues c[]= new Queues[this.queue_nr_int];
			for( i=0; i<this.queue_nr_int ;i++){
				c[i]= new Queues("Queue "+(i+1),i);
				pb[i]=new JProgressBar(0,client_nr/queue_nr_int);
				pb[i].setValue(0);
				pb[i].setStringPainted(false);
				//pb[i]=Queues.p;
				p0.add(pb[i]);
				p0.revalidate();
				p0.repaint();
				c[i].start();
			}
			
			m = new Model(queue_nr_int,c,client_nr,this.min_arr_int,this.max_arr_int,this.min_st_int,this.max_st_int);
			
			m.start();
			
		}else if(e.getSource()==b2){
			
			caux.add(new JLabel("Avg waiting time: "+m.getWtime()));
			caux.repaint();
			
			aux.setVisible(true);
		}
	}
	

}
