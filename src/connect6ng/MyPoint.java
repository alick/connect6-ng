/** @file MyPoint.java 
 * @brief  记录棋子的位置和颜色
 * @author 侯奇
 * @author 卢嘉勋
 * @author 刘菁菁
 * @date 2013-12-30
 * 
 */
package connect6ng;

import java.io.Serializable;
/**@brief  记录棋子的位置和颜色
 */
@SuppressWarnings("serial")
class MyPoint implements Serializable {
    //记录坐标及颜色
    private int x, y, color;

    /** @brief Class constructor.
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
    /** @brief Gets the x-coordinate.
     *
     * @return the x-coordinate
     */
    int getX() {
        return x;
    }
    /**@brief  Gets the y-coordinate.
     *
     * @return the y-coordinate
     */
    int getY() {
        return y;
    }
    /** @brief Returns the color of the stone.
     *
     * @return the color of the stone
     */
    int getColor() {
        return color;
    }
}
