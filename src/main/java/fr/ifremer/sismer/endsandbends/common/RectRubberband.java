package fr.ifremer.sismer.endsandbends.common;

import java.awt.Component;
import java.awt.Graphics;

/**
 * Draw "rubberband" rectangles when the user drags the mouse.
 */

public class RectRubberband extends Rubberband
{

    public RectRubberband(Component a_Component)
    {
        super(a_Component);
    }

    /**
     * Draw the rectangle
     */
    @Override
    protected void drawShape(Graphics g, int startX, int startY, int stopX,
            int stopY)
    {
        int x, y, w, h;
        x = StrictMath.min(startX, stopX);
        y = StrictMath.min(startY, stopY);
        w = StrictMath.abs(startX - stopX);
        h = StrictMath.abs(startY - stopY);
        int xs[] = {x, x + w, x + w, x, x};
        int ys[] = {y, y, y + h, y + h, y};
        g.drawPolygon(xs, ys, 5);
    }

}