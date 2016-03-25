package com.main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class HelpPanel extends JDialog implements ActionListener{
	
	public static final String AUTOCAR_EDITOR_HELP_TEXT="<html><body>" +
			
	    	"<h3>Autocar Editor</h3>"+
            "<p>The cyan point is the center starting position of the auotcar</p>"+ 
		"</body></html>";
	
	public static final String OBJECT_EDITOR_HELP_TEXT="<html><body>" +
	
	    	"Editor short keys:"+
	        "<ul>"+ 
	        "<li>i: insert points</li>"+
	        "<li>b: change selected object</li>"+
	        "<li>p: change selected point</li>"+
	        "<li>n: start build polygon</li>"+
	        "<li>l: build polygon</li>"+
	        "<li>t: select all</li></ul>"+
	        "<li>e: deselect all</li></ul>"+
	        "View keys:<br>" +
	        "<ul>"+
	        "<li>f1: zoom in</li>"+
	        "<li>f2: zoom out</li>"+
	        "<li>q rotate +</li>"+
	        "<li>w rotate-</li>"+
	        "<li>a rotate teta +</li>"+
	        "<li>s rotate teta -</li>"+
	        "</ul>"+ 
	        "<p>To build Polygon:</p>"+
	        "<ol>" +
	        "<li>Press <b>Start polygon points sequence</b></li>" +
	        "<li>Select points in anti-clockwise order.</li>" +
	        "<li>Press <b>Build polygon</b></li>" +
	        "</ol>"+
	        "<p>Select Polygon from the <b>lines list</b></p>"+
	        "<p>Select points from the <b>points list</b> or clicking on them</p>"+
		"</body></html>";
	
	
	public static final String CUBIC_EDITOR_HELP_TEXT="<html><body>" +
		"<p>Use <b>Edit->Add cube</b> to start</p>" +
		"<p>Navigate through the lattice using" +
		" the buttons." +
		"Then you can hide and show them.</p>"+ 
		"<p>You can select blocks with mouse selecting vertices, or moving" +
		" with the buttons.</p>"+ 
        "<br>View keys:<br>" +
        "<ul>"+
        "<li><p>Move lattice with arrow keys or mouse wheel.</p></li>"+
        "<li>+ zoom in</li>"+
        "<li>- zoom out</li>"+
        "<li>q rotate +</li>"+
        "<li>w rotate-</li>"+
        "</ul>"+ 	
		"</body></html>";
	
	
	public static final String ROAD_EDITOR_HELP_TEXT="<html><body>" +

		"<p>Select tiles with left mouse button in the area, select object by clicking on the number.</p>"+	
        "<br>View keys:<br>" +
        "<ul>" +
        "<li><p>Move with arrow keys or mouse wheel.</p></li>"+
        "<li>F1 zoom in</li>"+
        "<li>F2 zoom out</li>" +
        "</ul>"+
        "<p>To build Polygon:</p>"+
        "<ol>" +
        "<li>Press <b>Start polygon points sequence</b></li>" +
        "<li>Select points in anti-clockwise order.</li>" +
        "<li>Press <b>Build polygon</b></li>" +
        "</ol>"+
        "<p>Other commands:</p>"+
        "<ul>" +
        "<li><b>&lt;</b> to invert polygon</li>"+
        "</ul>"+
		"</body></html>";
	
	public static final String BLOCK_EDITOR_HELP_TEXT="<html><body>" +
			
		"<p>Use <b>Edit->Start Block</b> to start</p>" +
		"<p>You must add blocks to navigate through the lattice using" +
		" the buttons." +
		"Then you can hide and show them.</p>"+ 
		"<p>You can select blocks with mouse selecting vertices, or moving" +
		" with the buttons.</p>"+ 
        "<br>View keys:<br>" +
        "<ul>"+
        "<li><p>Move lattice with arrow keys or mouse wheel.</p></li>"+
        "<li>+ zoom in</li>"+
        "<li>- zoom out</li>"+
        "<li>q rotate +</li>"+
        "<li>w rotate-</li>"+
        "</ul>"+ 	
		"</body></html>";
	
	public static final String IPERVIEW_EDITOR_HELP_TEXT="<html><body>" +
			
        "<p>Move mesh with arrow keys or mouse wheel.</p>" +
        "<p>View keys:</p>" +
        "<ul>"+
        "<li>F1 zoom in</li>"+
        "<li>F2 zoom out</li>"+
        "<li>q rotate +</li>"+
        "<li>w rotate-</li>"+
        "</ul>"+ 
        "<p>To build Polygon:</p>"+
        "<ol>" +
        "<li>Press <b>Start polygon points sequence</b></li>" +
        "<li>Select points in anti-clockwise order.</li>" +
        "<li>Press <b>Build polygon</b></li>" +
        "</ol>"+
        "<p>Select Polygon from the <b>lines list</b></p>"+
        "<p>Select points from the <b>points list</b> or clicking on them</p>"+
		"</body></html>";
	

	JButton exit=null;
	JPanel bottom=null;
	private JEditorPane center;
	
	public HelpPanel(int w, int h,int locX,int locY,String text,JFrame owner){
		
		super(owner);
		setTitle("Help");
		setSize(w,h);
		
		center=new JEditorPane("text/html",text);
	
		
		JScrollPane jscp=new JScrollPane(center);
		add(jscp);
		setLocation(locX,locY);
		center.setCaretPosition(0);
		
		bottom=new JPanel();
		
		exit=new JButton("Exit");
		exit.addActionListener(this);
		bottom.add(exit);
		
		add(BorderLayout.SOUTH,bottom);
		
		setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object o = arg0.getSource();
		
		
		if(o==exit)
			exit();
	}

	
	public void exit(){
		
		dispose();
		
	}
}
