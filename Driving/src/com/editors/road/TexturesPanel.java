package com.editors.road;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;


class TexturesPanel extends JDialog implements ActionListener,MouseListener{
	
	JButton cancel=null;
	
	private JPanel center;
	private JLabel[] textureLabels;
	
	private int TEXTURE_SIDE_X=100;
	private int TEXTURE_SIDE_Y=100;
	
	private int DELTAY=25;
	private int DELTAX=25;
	private int BORDER=90;

	private int X_SIZE=800;
	private int Y_SIZE=500;
	
	private int columns=5;
	private int selectedIndex=-1;
	

	TexturesPanel(BufferedImage[] textures,int TEXTURE_SIDE_X,int TEXTURE_SIDE_Y){
		
		this.TEXTURE_SIDE_X=TEXTURE_SIDE_X;
		this.TEXTURE_SIDE_Y=TEXTURE_SIDE_Y;
		
		setTitle("Choose texture");
		setLayout(null);
		
		
		
		int HEIGHT=BORDER+(DELTAY+TEXTURE_SIDE_Y)*((int)Math.ceil(textures.length*1.0/columns));
		int WIDTH=BORDER+(TEXTURE_SIDE_X+DELTAX)*columns;
		setSize(20+X_SIZE,Y_SIZE+50);

		setModal(true);
		center=new JPanel(null);
		center.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		JScrollPane scrp=new JScrollPane(center);
		scrp.setBounds(0,0,X_SIZE,Y_SIZE);

		add(scrp);
		
	
		
		int r=10;
		
		textureLabels=new JLabel[textures.length];
		
		for(int i=0;i<textures.length;i++){
			
		
			textureLabels[i]=new JLabel();
			//System.out.println(BORDER/2+(DELTAX+TEXTURE_SIDE)*(i%columns)+","+r);
			textureLabels[i].setBounds(BORDER/2+(DELTAX+TEXTURE_SIDE_X)*(i%columns),r,TEXTURE_SIDE_X,TEXTURE_SIDE_Y);
			Border border=BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
			textureLabels[i].setBorder(border);
			
			BufferedImage icon=new BufferedImage(TEXTURE_SIDE_X,TEXTURE_SIDE_Y,BufferedImage.TYPE_3BYTE_BGR);
			icon.getGraphics().drawImage(textures[i],0,0,textureLabels[i].getWidth(),textureLabels[i].getHeight(),null);
			ImageIcon ii=new ImageIcon(icon);
			textureLabels[i].setIcon(ii);
				
			center.add(textureLabels[i]);
			
			//System.out.println(textureLabels[i].getBounds());
			
			textureLabels[i].addMouseListener(this);
			
			if(i%columns==columns-1)
				r+=DELTAY+TEXTURE_SIDE_Y;
		}
		if((textures.length)%columns!=0)
			r+=TEXTURE_SIDE_Y;
				
		r+=30;
		
		cancel=new JButton("Cancel");
		cancel.setBounds(100,r,80,20);
		center.add(cancel);
		cancel.addActionListener(this);
		//System.out.println(cancel.getBounds());
		
        setVisible(true);
		
	}
	
	

	public int getSelectedIndex() {
		
		return selectedIndex;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object obj = e.getSource();
		if(obj==cancel){
			selectedIndex=-1;
			dispose();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
		
		Object obj = arg0.getSource();
		
		for(int i=0;i<textureLabels.length;i++){
			
			if(obj==textureLabels[i]){
				selectedIndex=i;
				dispose();
			}
		}	
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
