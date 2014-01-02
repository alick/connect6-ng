package connect6ng;

import java.io.Serializable;


/** This class records the point with side/color information.
 */
@SuppressWarnings("serial")
class MyPoint implements Serializable {
    //记录坐标及颜色
    private int x, y, color;

    /** Class constructor.
     *
     * @param _x the x-coordinate
     * @param _y the y-coordinate
     * @param _color the color of the stone
     */
    MyPoint(int _x, int _y, int _color) {
        x = _x;
        y = _y;
        color = _color;
    }
    /** Gets the x-coordinate.
     *
     * @return the x-coordinate
     */
    int getX() {
        return x;
    }
    /** Gets the y-coordinate.
     *
     * @return the y-coordinate
     */
    int getY() {
        return y;
    }
    /** Returns the color of the stone.
     *
     * @return the color of the stone
     */
    int getColor() {
        return color;
    }
}
