package com.editors.road;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.DrawObject;
import com.SPLine;
import com.SPNode;

public class RoadEditorMassModifiy extends JDialog implements ActionListener{

	private int WIDTH=230;
	private int HEIGHT=260;

	private JPanel center;

	private JButton update;

	private JButton cancel;

	private Object returnValue=null;

	private static int MODE_NO_MODE=-1;

	private int mode=MODE_NO_MODE;
	private JRadioButton alignMaxX;
	private JRadioButton alignMinX;
	private JRadioButton alignMaxY;
	private JRadioButton alignMinY;

	private static int MODE_MAX_X=0;
	private static int MODE_MIN_X=1;
	private static int MODE_MAX_Y=2;
	private static int MODE_MIN_Y=3;

	public RoadEditorMassModifiy(){

		mode=MODE_NO_MODE;

		returnValue=null;

		setTitle("Mass modify");
		setLayout(null);

		setSize(WIDTH,HEIGHT);
		setModal(true);

		center=new JPanel(null);
		center.setBounds(0,0,WIDTH,HEIGHT);
		add(center);

		int r=10;

		JLabel lbl=new JLabel("Allignement");
		lbl.setBounds(10,r,100,20);
		center.add(lbl);

		r+=30;
		alignMaxX=new JRadioButton("Max x");
		alignMaxX.setBounds(10,r,100,20);
		center.add(alignMaxX);


		r+=30;
		alignMinX=new JRadioButton("Min x");
		alignMinX.setBounds(10,r,100,20);
		center.add(alignMinX);

		r+=30;
		alignMaxY=new JRadioButton("Max y");
		alignMaxY.setBounds(10,r,100,20);
		center.add(alignMaxY);

		r+=30;
		alignMinY=new JRadioButton("Min y");
		alignMinY.setBounds(10,r,100,20);
		center.add(alignMinY);

		ButtonGroup bg=new ButtonGroup();
		bg.add(alignMaxX);
		bg.add(alignMinX);
		bg.add(alignMaxY);
		bg.add(alignMinY);

		r+=30;

		update=new JButton("Update");
		update.setBounds(10,r,80,20);
		center.add(update);
		update.addActionListener(this);

		cancel=new JButton("Cancel");
		cancel.setBounds(100,r,80,20);
		center.add(cancel);
		cancel.addActionListener(this);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		Object obj = arg0.getSource();

		if(obj==update){

			update();
			dispose();

		}
		else if(obj==cancel){
			returnValue=null;
			dispose();
		}

	}

	private void update() {

		returnValue=this;

		if(alignMaxX.isSelected())
			mode=MODE_MAX_X;
		else if(alignMinX.isSelected())
			mode=MODE_MIN_X;
		else if(alignMaxY.isSelected())
			mode=MODE_MAX_Y;
		else if(alignMinY.isSelected())
			mode=MODE_MIN_Y;
		else {

			returnValue=null;
		}


	}

	public Object getReturnValue() {
		return returnValue;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public static void massModifyObjects(RoadEditorMassModifiy ret, ArrayList<DrawObject> drawObjects) {

		if(drawObjects==null)
			return;

		int sz=drawObjects.size();

		double minX=0;
		double maxX=0;
		double minY=0;
		double maxY=0;

		int counter=0;

		for (int i = 0; i <sz ; i++) {

			DrawObject dro=drawObjects.get(i);

			if(dro.isSelected()){

				if(counter==0){

					minX=dro.getX();
					maxX=dro.getX();
					minY=dro.getY();
					maxY=dro.getY();
				}else{

					if(dro.getX()<minX){

						minX=dro.getX();

					}

					if(dro.getY()<minY){

						minY=dro.getY();

					}

					if(dro.getX()>maxX){

						maxX=dro.getX();

					}

					if(dro.getY()>maxY){

						maxY=dro.getY();

					}

				}

				counter++;
			}

		}	

		for (int i = 0; i <sz ; i++) {

			DrawObject dro=drawObjects.get(i);

			if(dro.isSelected()){

				if(ret.getMode()==MODE_MAX_X){

					dro.setX(maxX);


				}else if(ret.getMode()==MODE_MIN_X){

					dro.setX(minX);


				}else if(ret.getMode()==MODE_MAX_Y){

					dro.setY(maxY);

				}else if(ret.getMode()==MODE_MIN_Y){

					dro.setY(minY);
				}
			}
		}

	}

	public static void massModifySplines(RoadEditorMassModifiy ret, ArrayList<SPLine> splines) {

		double minX=0;
		double maxX=0;
		double minY=0;
		double maxY=0;

		int counter=0;

		for (int i = 0; i < splines.size(); i++) {
			SPLine spline = (SPLine) splines.get(i);

			ArrayList<SPNode> nodes = spline.getNodes();
			if(nodes==null)
				continue;
			int sz=nodes.size();

			for (int k = 0; k < sz; k++) {

				SPNode node = (SPNode) nodes.get(k);

				if(node.isSelected()){

					if(counter==0){

						minX=node.getX();
						maxX=node.getX();
						minY=node.getY();
						maxY=node.getY();
					}else{

						if(node.getX()<minX){

							minX=node.getX();

						}

						if(node.getY()<minY){

							minY=node.getY();

						}

						if(node.getX()>maxX){

							maxX=node.getX();

						}

						if(node.getY()>maxY){

							maxY=node.getY();

						}

					}

					counter++;
				}
			}


		}

		//update

		for (int i = 0; i < splines.size(); i++) {
			SPLine spline = (SPLine) splines.get(i);

			ArrayList<SPNode> nodes = spline.getNodes();
			if(nodes==null)
				continue;
			int sz=nodes.size();

			for (int k = 0; k < sz; k++) {

				SPNode node = (SPNode) nodes.get(k);

				if(node.isSelected()){

					if(ret.getMode()==MODE_MAX_X){

						node.setX(maxX);


					}else if(ret.getMode()==MODE_MIN_X){

						node.setX(minX);


					}else if(ret.getMode()==MODE_MAX_Y){

						node.setY(maxY);

					}else if(ret.getMode()==MODE_MIN_Y){

						node.setY(minY);
					}
				}		
			}
		}	

	}

}
