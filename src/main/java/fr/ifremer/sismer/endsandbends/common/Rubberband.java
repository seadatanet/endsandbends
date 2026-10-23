package fr.ifremer.sismer.endsandbends.common;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

/**
 * Draw "rubberband" selection with mouse
 */

public abstract class Rubberband implements MouseListener, MouseMotionListener
{

    private Component _component = null;
    private Vector _arListeners = new Vector();
    public int startX, startY, lastX, lastY;

    public Rubberband(Component a_Component)
    {
        setComponent(a_Component);
    }

    public void addJobStateListener(RuberbandListener listener)
    {
        if (_arListeners == null)
            _arListeners = new Vector();
        _arListeners.add(listener);
    }

    public void removeJobStateListener(RuberbandListener listener)
    {
        if (_arListeners != null)
            _arListeners.remove(listener);
    }

    public void fireActionDone()
    {
        if (_arListeners == null)
            return;
        for (int index = 0; index < _arListeners.size(); index++)
            ((RuberbandListener) (_arListeners.elementAt(index))).actionDone(
                startX, startY, lastX - startX, lastY - startY);
    }

    public void release()
    {
        _component.removeMouseListener(this);
        _component.removeMouseMotionListener(this);
        _component = null;
    }

    public void setComponent(Component a_Component)
    {
        _component = a_Component;
        _component.addMouseListener(this);
        _component.addMouseMotionListener(this);
    }

    /**
     * Draw the rectangle, adjusting the x, y, w, h to correctly accommodate for
     * the opposite corner of the rubberband box relative to the start position.
     */

    protected abstract void drawShape(Graphics g, int startX, int startY,
            int stopX, int stopY);

    /**
     * When the user presses the mouse, record the location of the top-left
     * corner of rectangle.
     */
    public void mousePressed(MouseEvent event)
    {
        startX = event.getX();
        startY = event.getY();
        lastX = startX;
        lastY = startY;
    }

    /**
     * Erase the last rectangle when the user releases the mouse.
     */
    public void mouseReleased(MouseEvent event)
    {
        Graphics g = _component.getGraphics();
        g.setXORMode(Color.lightGray);
        drawShape(g, startX, startY, lastX, lastY);
        fireActionDone();
    }

    public void mouseClicked(MouseEvent e)
    {
    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }

    public void mouseMoved(MouseEvent e)
    {
    }

    /**
     * This draws a rubberband rectangle, from the location where the mouse was
     * first clicked to the location where the mouse is dragged.
     */
    public void mouseDragged(MouseEvent event)
    {
        int x = event.getX();
        int y = event.getY();

        Graphics g = _component.getGraphics();
        g.setXORMode(Color.lightGray);
        drawShape(g, startX, startY, x, y);
        drawShape(g, startX, startY, lastX, lastY);

        lastX = x;
        lastY = y;
    }

}