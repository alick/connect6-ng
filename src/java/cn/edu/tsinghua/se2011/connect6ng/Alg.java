package cn.edu.tsinghua.se2011.connect6ng;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/** This class implements the core algorithm. */
class Alg {
    //IO共享内存部分，储存每一个棋子的位置与颜色
    private Vector<MyPoint> data;
    private int ChessBoard[][];

    static int[] os = new int[] {0, 0, 2, 20, 65535, 65535, 65535};
    static int[] ms = new int[] {0, 0, 1, 10, 35, 35, 999999};
    /** Class constructor. */
    Alg(Vector<MyPoint> _data) {
        data = _data;
        ChessBoard = new int[19][];
        for (int i=0; i<19; i++) {
            ChessBoard[i] = new int[19];
            for (int j=0; j<19; j++) {
                ChessBoard[i][j] = -1;
            }
        }
    }

    void set(Vector<MyPoint> _data) {
        data = _data;
    }

    void clrp() {
        for (int i=0; i<19; i++) {
            for (int j=0; j<19; j++) {
                ChessBoard[i][j] = -1;
            }
        }
    }

    /** 计算之后两枚颜色为color的子的位置，直接将数据插入data中。 */
    void placeTwoStones(int color) {
        int size;
        size = data.size();
        if (size == 0) {
            data.add(new MyPoint(9, 9, 0));
            return;
        }
        MyPoint p;
        clrp();
        for (int i=0; i<size; i++) {
            p = (MyPoint)data.elementAt(i);
            ChessBoard[p.getx()][p.gety()] = p.getcolor();
        }

        int x1, x2, y1, y2, _x1, _x2, _y1, _y2;
        int v, vmax;
        int k;
        k = 1;
        _x1 = _x2 = _y1 = _y2 = 0;
        vmax = -99999999;
        for (x1=0; x1<19; x1++)//枚举之后两枚棋的位置
            for (y1=0; y1<19; y1++)
                for (x2=0; x2<19; x2++)
                    for (y2=0; y2<19; y2++) {
                        // Do not compute with the dual double pairs,
                        // i.e. (x1, y1; x2, y2) ~ (x2, y2; x1, y1)
                        if (x1 * 19 + y1 > x2 * 19 + y2) {
                            continue;
                        }
                        if (  (ChessBoard[x1][y1] != -1)
                                ||(ChessBoard[x2][y2] != -1)
                                ||(x1 == x2 && y1 == y2)
                                ||!hasStonesAround(x1, y1)
                                ||!hasStonesAround(x2, y2) ) {
                            continue;
                        }

                        ChessBoard[x1][y1] = color;
                        ChessBoard[x2][y2] = color;

                        v = getTotalScore(color);//计算得分
                        if (vmax < v) {
                            vmax = v;
                            k = 2;
                            _x1 = x1;
                            _x2 = x2;
                            _y1 = y1;
                            _y2 = y2;
                        } else if (vmax == v) {
                            //如果得分相同则随机选择一个，但保证相同得分的所有下法等概率
                            if (Math.random() * k <= 1) {
                                _x1 = x1;
                                _x2 = x2;
                                _y1 = y1;
                                _y2 = y2;
                            }
                            k++;
                        }
                        ChessBoard[x1][y1] = -1;
                        ChessBoard[x2][y2] = -1;
                    }
//		System.out.println(vmax);	//TestCode
        data.add(new MyPoint(_x1, _y1, color));
        data.add(new MyPoint(_x2, _y2, color));
    }

    /** Checks whether the point (x, y) has stones around it.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     */
    boolean hasStonesAround(int x, int y) {
        x = Math.min(x, 16);
        y = Math.min(y, 16);
        x = Math.max(x, 2);
        y = Math.max(y, 2);
        int i;
        i = 1;
        if (  (	  ChessBoard[x-1][y-1]	+ ChessBoard[x][y-1]	+ ChessBoard[x+1][y-1]
                  + ChessBoard[x-1][y]	+ ChessBoard[x][y]		+ ChessBoard[x+1][y]
                  + ChessBoard[x-1][y+1]	+ ChessBoard[x][y+1]	+ ChessBoard[x+1][y+1] != -9)
                ||(	  ChessBoard[x-2][y-2]	+ ChessBoard[x][y-2]	+ ChessBoard[x+2][y-2]
                      + ChessBoard[x-2][y]	+ ChessBoard[x][y]		+ ChessBoard[x+2][y]
                      + ChessBoard[x-2][y+2]	+ ChessBoard[x][y+2]	+ ChessBoard[x+2][y+2] != -9) ) {
            return true;
        }
        return false;
    }

    /** Calculates and returns the total score of one color(side).
     *
     * @param color the color of the stone
     * @return the total score of the color in the whole chess board
     */
    int getTotalScore(int color) {
        SixPointsLine l = new SixPointsLine();

        int score = 0;

        //水平方向
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 5; j++) {
                l.push(ChessBoard[i][j]);
            }
            for (int j = 5; j < 19; j++) {
                l.push(ChessBoard[i][j]);
                score += ms[l.getScore(color)];
                score -= os[l.getScore(1-color)];
            }
        }

        //竖直方向
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 5; j++) {
                l.push(ChessBoard[j][i]);
            }
            for (int j=5; j<19; j++) {
                l.push(ChessBoard[j][i]);
                score += ms[l.getScore(color)];
                score -= os[l.getScore(1-color)];
            }
        }

        //"\"方向
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 5; j++) {
                l.push(ChessBoard[j][i + j]);
            }
            for (int j = 5; i + j < 19; j++) {
                l.push(ChessBoard[j][i + j]);
                score += ms[l.getScore(color)];
                score -= os[l.getScore(1-color)];
            }
        }
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 5; j++) {
                l.push(ChessBoard[i + j][j]);
            }
            for (int j = 5; i + j < 19; j++) {
                l.push(ChessBoard[i + j][j]);
                score += ms[l.getScore(color)];
                score -= os[l.getScore(1-color)];
            }
        }

        //"/"方向
        for (int i = 5; i < 19; i++) {
            for (int j = 0; j < 5; j++) {
                l.push(ChessBoard[j][i - j]);
            }
            for (int j = 5; j <= i; j++) {
                l.push(ChessBoard[j][i-j]);
                score += ms[l.getScore(color)];
                score -= os[l.getScore(1-color)];
            }
        }
        for (int i = 1; i < 14; i++) {
            for (int j = 0; j < 5; j++) {
                l.push(ChessBoard[i + j][18 - j]);
            }
            for (int j = 5; i + j < 19; j++) {
                l.push(ChessBoard[i + j][18 - j]);
                score += ms[l.getScore(color)];
                score -= os[l.getScore(1-color)];
            }
        }
        return score;
    }

    boolean hadsix(int color) {
        //返回是否有此颜色的六连
        int ChessBoardState[][];
        ChessBoardState = new int[19][];
        int i, j;

        for (i=0; i<19; i++) {
            //水平方向
            ChessBoardState[i] = new int[19];
            ChessBoardState[i][0] = (ChessBoard[i][0] == color) ? 1 : 0;
            for (j=1; j<19; j++) {
                ChessBoardState[i][j] = (ChessBoard[i][j] == color) ? (ChessBoardState[i][j-1] + 1) : 0;
                if (ChessBoardState[i][j] == 6) {
                    return true;
                }
            }
        }
        for (j=0; j<19; j++) {
            //竖直方向
            ChessBoardState[0][j] = (ChessBoard[0][j] == color) ? 1 : 0;
            for (i=1; i<19; i++) {
                ChessBoardState[i][j] = (ChessBoard[i][j] == color) ? (ChessBoardState[i-1][j] + 1) : 0;
                if (ChessBoardState[i][j] == 6) {
                    return true;
                }
            }
        }

        for (i=0; i<19; i++) {
            //"\"方向
            ChessBoardState[0][i] = (ChessBoard[0][i] == color) ? 1 : 0;
            ChessBoardState[i][0] = (ChessBoard[i][0] == color) ? 1 : 0;
        }
        for (i=1; i<19; i++)
            for (j=1; j<19; j++) {
                ChessBoardState[i][j] = (ChessBoard[i][j] == color) ? (ChessBoardState[i-1][j-1] + 1) : 0;
                if (ChessBoardState[i][j] == 6) {
                    return true;
                }
            }

        for (i=0; i<19; i++) {
            //"/"方向
            ChessBoardState[0][i] = (ChessBoard[0][i] == color) ? 1 : 0;
            ChessBoardState[i][18] = (ChessBoard[i][18] == color) ? 1 : 0;
        }
        for (i=1; i<19; i++)
            for (j=17; j>=0; j--) {
                ChessBoardState[i][j] = (ChessBoard[i][j] == color) ? (ChessBoardState[i-1][j+1] + 1) : 0;
                if (ChessBoardState[i][j] == 6) {
                    return true;
                }
            }

        return false;
    }

    boolean hadsix() {
        //返回是否有六连（不区分黑白）
        MyPoint p;
        int size = data.size();
        clrp();
        for (int i=0; i<size; i++) {
            p = (MyPoint)data.elementAt(i);
            ChessBoard[p.getx()][p.gety()] = p.getcolor();
        }

        return (hadsix(0)||hadsix(1));
    }
}


/** This class describes the line made up of six adjacent points.
 * It is a FIFO queue. It give scores for each color(side) of the game.
 */
class SixPointsLine {
    //棋盘上的连续6个位置队列
    int queue[] = new int[6];
    // Number of stones with each side(color)
    int cnts[] = new int[3];
    // Indicates where to push new element.
    int offset;

    /** Class constructor. */
    SixPointsLine() {
        // Init with six points with no stones.
        for (int k = 0; k < 6; k++)
            queue[k] = -1;
        cnts[0] = 0;
        cnts[1] = 0;
        cnts[2] = 6;

        offset = 0;
    }

    /** 在队列中加入一个棋子，同时弹出最后一个。 */
    void push(int color) {
        // We use modulus to map colors(0, 1, -1) to
        // array index(0, 1, 2).
        cnts[(queue[offset] + 3) % 3]--;
        queue[offset++] = color;
        offset %= 6;
        cnts[(color + 3) % 3]++;
    }

    /** 返回当前队列相应颜色的得分。 */
    int getScore(int color) {
        int i = (color + 3) % 3;
        if (i < 2 && cnts[1 - i] != 0) {
            return 0;
        } else {
            return cnts[i];
        }
    }
}

class MyPoint implements Serializable {
    //记录坐标及颜色
    private int x, y, color;
    MyPoint(int _x, int _y, int _color) {
        x = _x;
        y = _y;
        color = _color;
    }
    int getx() {
        return x;
    }
    int gety() {
        return y;
    }
    int getcolor() {
        return color;
    }
}
