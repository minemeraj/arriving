package com.editors.autocars;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.Point3D;
import com.editors.DoubleTextField;

public class AutocarTemplatePanel extends JDialog implements ActionListener{

	private int TEMPLATE_TYPE;
	private final int TEMPLATE_TYPE_ELLIPSE=0;
	private final int TEMPLATE_TYPE_RECTANGLE=1;
	
	private final int WIDTH=300;
	private final int BOTTOM_HEIGHT=100;
	private final int HEIGHT=280;
	
	private JTabbedPane jtb;
	private JButton generate;
	private JButton delete;
	
	private DoubleTextField x_radius;
	private DoubleTextField y_radius;
	private DoubleTextField center_x;
	private DoubleTextField center_y;
	private DoubleTextField num_points;
	private DoubleTextField x_length;
	private DoubleTextField y_length;
	private DoubleTextField num_x_points;
	private DoubleTextField num_y_points;
	private DoubleTextField n_w_x;
	private DoubleTextField n_w_y;
	

	public AutocarTemplatePanel(){
	
		TEMPLATE_TYPE=-1;
		setSize(WIDTH,HEIGHT+BOTTOM_HEIGHT);
		setLayout(null);
		setModal(true);
		setTitle("Choose template");
		
		jtb=new JTabbedPane();
		jtb.setBounds(0, 0, WIDTH, HEIGHT);
	    add(jtb); 
	    jtb.add("Ellipse",getCirclePanel());
	    jtb.add("Rectangle",getRectanglePanel());
	    
	    generate=new JButton("generate template");
	    generate.setBounds(10,HEIGHT+10,150,20);
	    generate.addActionListener(this);
	    add(generate);
	    delete=new JButton("Cancel");
	    delete.setBounds(180,HEIGHT+10,100,20);
	    delete.addActionListener(this);
	    add(delete);
	
		setVisible(true);
	
	}
	
	private Component getRectanglePanel() {
		
		JPanel rectangle=new JPanel();
		rectangle.setLayout(null);
		
		int r=10;
		int column=100;

		JLabel jlb=new JLabel("N-W x");
		jlb.setBounds(5, r, 100, 20);
		rectangle.add(jlb);
		n_w_x=new DoubleTextField();
		n_w_x.setBounds(column, r, 100, 20);
		rectangle.add(n_w_x);
		
		r+=30;

		jlb=new JLabel("N-W y");
		jlb.setBounds(5, r, 100, 20);
		rectangle.add(jlb);
		n_w_y=new DoubleTextField();
		n_w_y.setBounds(column, r, 100, 20);
		rectangle.add(n_w_y);
		
		r+=30;

		 jlb=new JLabel("X Length");
		jlb.setBounds(5, r, 100, 20);
		rectangle.add(jlb);

		x_length=new DoubleTextField();
		x_length.setBounds(column, r, 100, 20);
		rectangle.add(x_length);

		r+=30;
		
		jlb=new JLabel("Y Lengths");
		jlb.setBounds(5, r, 100, 20);
		rectangle.add(jlb);

		y_length=new DoubleTextField();
		y_length.setBounds(column, r, 100, 20);
		rectangle.add(y_length);


		r+=30;

		jlb=new JLabel("Num X Points");
		jlb.setBounds(5, r, 100, 20);
		rectangle.add(jlb);
		num_x_points=new DoubleTextField();
		num_x_points.setBounds(column, r, 100, 20);
		rectangle.add(num_x_points);
		
		r+=30;

		jlb=new JLabel("Num Y Points");
		jlb.setBounds(5, r, 100, 20);
		rectangle.add(jlb);
		num_y_points=new DoubleTextField();
		num_y_points.setBounds(column, r, 100, 20);
		rectangle.add(num_y_points);
		
	
		
		
		return rectangle;
		
	}

	private Component getCirclePanel() {
		
		JPanel sphere=new JPanel();
		sphere.setLayout(null);
		
		int r=10;
		int column=100;

		JLabel jlb=new JLabel("X Radius");
		jlb.setBounds(5, r, 100, 20);
		sphere.add(jlb);

		x_radius=new DoubleTextField();
		x_radius.setBounds(column, r, 100, 20);
		sphere.add(x_radius);

		r+=30;
		
		jlb=new JLabel("Y Radius");
		jlb.setBounds(5, r, 100, 20);
		sphere.add(jlb);

		y_radius=new DoubleTextField();
		y_radius.setBounds(column, r, 100, 20);
		sphere.add(y_radius);

		r+=30;

		jlb=new JLabel("Center x");
		jlb.setBounds(5, r, 100, 20);
		sphere.add(jlb);
		center_x=new DoubleTextField();
		center_x.setBounds(column, r, 100, 20);
		sphere.add(center_x);
		
		r+=30;

		jlb=new JLabel("Center y");
		jlb.setBounds(5, r, 100, 20);
		sphere.add(jlb);
		center_y=new DoubleTextField();
		center_y.setBounds(column, r, 100, 20);
		sphere.add(center_y);

		r+=30;

		jlb=new JLabel("Num Points");
		jlb.setBounds(5, r, 100, 20);
		sphere.add(jlb);
		num_points=new DoubleTextField();
		num_points.setBounds(column, r, 100, 20);
		sphere.add(num_points);
		
		
		return sphere;
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		Object o=arg0.getSource();

		if(o==generate){
			
			if(jtb.getSelectedIndex()==0)
				TEMPLATE_TYPE=TEMPLATE_TYPE_ELLIPSE;
			else if(jtb.getSelectedIndex()==1)
				TEMPLATE_TYPE=TEMPLATE_TYPE_RECTANGLE;
			else
				TEMPLATE_TYPE=-1;
			
			dispose();	
		}
		else if(o==delete){

			TEMPLATE_TYPE=-1;
			dispose();	
		}
		
	}


	public LinkedList getTemplate(){

		LinkedList ll=null;

		if(TEMPLATE_TYPE==-1) 
			return null;


		if(TEMPLATE_TYPE==TEMPLATE_TYPE_ELLIPSE){

			try{

				ll=new LinkedList();

				double x0=center_x.getvalue();
				double y0=center_y.getvalue();

				double radius_x=x_radius.getvalue();
				double radius_y=y_radius.getvalue();

				int npoints=(int) num_points.getvalue();

				for (int i = 0; i < npoints; i++) {

					double x=x0+radius_x*Math.cos(2*i*Math.PI/npoints);
					double y=y0+radius_y*Math.sin(2*i*Math.PI/npoints);

					Point3D p=new Point3D(x,y,0);

					ll.push(p);
				}

			}catch (Exception e) {
				e.printStackTrace();
			}

			return ll;

		}
		else if(TEMPLATE_TYPE==TEMPLATE_TYPE_RECTANGLE){

			try{

				ll=new LinkedList();

				double lx=x_length.getvalue();
				double ly=y_length.getvalue();

				int num_x=(int) num_x_points.getvalue();
				int num_y=(int) num_y_points.getvalue();

				double nw_x=(int) n_w_x.getvalue();
				double nw_y=(int) n_w_y.getvalue();


				for (int i = 0; i < num_x; i++) {

					double x=nw_x+i*lx/(num_x-1);
					double y=nw_y;

					Point3D p=new Point3D(x,y,0);
					ll.push(p);


				}


				for (int j = 1; j < num_y-1; j++) {

					double x=nw_x+lx;
					double y=nw_y+j*ly/(num_y-1);

					Point3D p=new Point3D(x,y,0);
					ll.push(p);


				}

				for (int i = num_x-1; i >=0; i--) {


					double x=nw_x+i*lx/(num_x-1);
					double y=nw_y+ly;

					Point3D p=new Point3D(x,y,0);
					ll.push(p);

				}


				for (int j = num_y-2; j >0; j--) {

					double x=nw_x;
					double y=nw_y+j*ly/(num_y-1);

					Point3D p=new Point3D(x,y,0);

					ll.push(p);



				}

			}catch (Exception e) {
				e.printStackTrace();
			}

			return ll;

		}

		return null;

	}	
	
	
}
