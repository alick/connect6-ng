package connect6ng;


/** @file __FILE__ 
 * @brief  棋子位置队列操作
 * @author 侯奇
 * @author 卢嘉勋
 * @author 刘菁菁
 * @date 2013-12-30
 * @version 1.0.0
 * 
 */

/**
 * @brief 连续六子队列管理
 * @author lujx
 *
 */
class SixPointsLine {
    //棋盘上的连续6个位置队列
    int queue[] = new int[6];
    // Number of stones with each side(color)
    int cnts[] = new int[3];
    // Indicates where to push new element.
    int offset;

    /**@brief  构造函数，构造六子队列
     * 
     */
    SixPointsLine() {
        // Init with six points with no stones.
        for (int k = 0; k < 6; k++)
            queue[k] = -1;
        cnts[0] = 0;
        cnts[1] = 0;
        cnts[2] = 6;

        offset = 0;
    }

    /**@brief  在队列中加入一个棋子，同时弹出最后一个。
     *
     * @param color the color of the stone
     */
    void push(int color) {
        // We use modulus to map colors(0, 1, -1) to
        // array index(0, 1, 2).
        cnts[(queue[offset] + 3) % 3]--;
        queue[offset++] = color;
        offset %= 6;
        cnts[(color + 3) % 3]++;
    }

    /**@brief  返回当前队列相应颜色的得分。
     *
     * @param color the color of the stone
     */
    int getScore(int color) {
        int i = (color + 3) % 3;
        if (i < 2 && cnts[1 - i] != 0) {
            return 0;
        } else {
            return cnts[i];
        }
    }
}

