package com.editors.road;

/**
 *
 * MAIN PROGRAM ENGINE, TRY TO SET A CONSTANT FRAME RATE OF 20
 * @author Piazza Francesco Giovanni ,Betacom s.r.l. http://www.betacom.it
 *
 */


public class RoadEditorThread extends Thread{

    private RoadEditor editor;


    public RoadEditorThread(RoadEditor editor) {
        super();
        this.editor = editor;

    }

    @Override
    public void run() {

        editor.setWaitBeforeMovingOrDraggingMouse(true);
        editor.updateEntitiesIfMousePressed();
        editor.draw();
        editor.setWaitBeforeMovingOrDraggingMouse(false);

        editor.setMouseMovedOrDragged(false);


    }



}
