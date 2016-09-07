package com.editors.road;

/**
 * 
 * MAIN PROGRAM ENGINE, TRY TO SET A CONSTANT FRAME RATE OF 20
 * @author Piazza Francesco Giovanni ,Betacom s.r.l. http://www.betacom.it
 *
 */


public class RoadEditorThread extends Thread{

	private RoadEditor editor;

	private int delay=0;

	public RoadEditorThread(RoadEditor editor,int delay) {
		super();
		this.editor = editor;
		this.delay = delay;
	}

	public void run() {

		try {
			sleep(delay);
		} catch (InterruptedException e) {
		}
		editor.setWaitBeforeMovingMouse(true);
		editor.draw();
		editor.setWaitBeforeMovingMouse(false);
		
		editor.setMouseMoved(false);
		

	}



}
