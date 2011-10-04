package cn.edu.tsinghua.se2011.connect6ng;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Connect6 {
    public static void main(String[] args) {
        MyFrame form = new MyFrame();
    }
}

class MyFrame extends Frame {
    Vector<MyPoint> data;
    Alg kernel;
    Dialog myDialog;
    Button myDialogBotton;
    int State;
    //0 = 初始、游戏暂停状态
    //1 = 用户还须下1子
    //2 = 用户还须下2子
    boolean pause;
    //游戏是否处于暂停状态

    int color;
    //0 = 黑子
    //1 = 白子
    //-1 = 空白

    boolean computer;
    //0 = 非人机对战
    //1 = 人机对战
    int mode;
    //0 = 练习模式
    //1 = 实战模式

    //菜单栏
    MenuBar menu_MenuBar = new MenuBar();
    Menu menu_game = 		new Menu("游戏");
    Menu menu_ngame = 			new Menu("开始新游戏");
    MenuItem menu_first = 			new MenuItem("先手(玩家黑棋先行)");
    MenuItem menu_last = 			new MenuItem("后手(玩家白棋后行)");
    MenuItem menu_restart = 		new MenuItem("重新开始游戏");
    MenuItem menu_reset = 			new MenuItem("重置游戏");
    CheckboxMenuItem menu_comp = 		new CheckboxMenuItem("人机对战");
    MenuItem menu_prac = new MenuItem("切换为实战模式");
    MenuItem menu_int = 		new MenuItem("暂停游戏");
    MenuItem menu_exit = 		new MenuItem("退出");
    MenuItem menu_back = 		new MenuItem("悔棋");
    Menu menu_file = 		new Menu("游戏战绩");
    MenuItem menu_open = 		new MenuItem("打开游戏战绩");
    MenuItem menu_save = 		new MenuItem("保存游戏战绩");


    @SuppressWarnings("unchecked")
    MyFrame()
    {
        super("Connect 6 - 游戏尚未开始 - 练习模式");
        setSize(650, 680);
        setResizable(false);

        //初始化数据
        data = new Vector<MyPoint>();
        kernel = new Alg(data);
        State = 0;
        pause = false;
        computer = true;

        //菜单栏及按钮监听
        setMenuBar(menu_MenuBar);
        menu_MenuBar.add(menu_game);
        menu_game.add(menu_ngame);
        menu_ngame.add(menu_first);
        menu_first.addActionListener(new ack_menu_first());
        menu_first.setShortcut(new MenuShortcut(KeyEvent.VK_F2));
        menu_ngame.add(menu_last);
        menu_last.addActionListener(new ack_menu_last());
        menu_last.setShortcut(new MenuShortcut(KeyEvent.VK_F3));
        menu_game.add(menu_restart);
        menu_restart.addActionListener(new ack_menu_restart());
        menu_restart.setEnabled(false);
        menu_restart.setShortcut(new MenuShortcut(KeyEvent.VK_F5));
        menu_game.add(menu_reset);
        menu_reset.addActionListener(new ack_menu_reset());
        menu_reset.setShortcut(new MenuShortcut(KeyEvent.VK_F1));
        menu_game.add(menu_comp);
        menu_comp.addItemListener(new ack_menu_comp());
        menu_comp.setState(true);
        menu_game.add(menu_prac);
        menu_prac.addActionListener(new ack_menu_prac());
        menu_game.add(menu_int);
        menu_int.addActionListener(new ack_menu_int());
        menu_int.setEnabled(false);
        menu_int.setShortcut(new MenuShortcut(KeyEvent.VK_F4));
        menu_game.add(menu_back);
        menu_back.addActionListener(new ack_menu_back());
        menu_back.setEnabled(false);
        menu_back.setShortcut(new MenuShortcut(KeyEvent.VK_LEFT));
        menu_game.add(menu_exit);
        menu_exit.addActionListener(new ack_menu_exit());
        menu_MenuBar.add(menu_file);
        menu_file.add(menu_open);
        menu_open.addActionListener(new ack_menu_open());
        menu_open.setShortcut(new MenuShortcut(KeyEvent.VK_O));
        menu_file.add(menu_save);
        menu_save.addActionListener(new ack_menu_save());
        menu_save.setShortcut(new MenuShortcut(KeyEvent.VK_S));

        //窗口监听
        addWindowListener(new Wclose());
        addMouseListener(new amouse());

        //打开游戏时读取文件
        FileInputStream istream;
        ObjectInputStream iFstream;
        try {
            istream = new FileInputStream("close.c6db");
            iFstream = new ObjectInputStream(istream);
            // the Xlint:unchecked warning is suppressed
            data = (Vector<MyPoint>)iFstream.readObject();
            istream.close();
            kernel.set(data);
        } catch (Exception x)
        {
            x.printStackTrace();
        };

        if (data.size() != 0)
        {
            pause = true;
            State = 0;
            menu_int.setEnabled(true);
            menu_int.setLabel("继续游戏");
        }

        repaint();
        setmytitle();
        setVisible(true);
    }

    public void paint(Graphics g)
    {   //界面绘制
        int i, Size;
        MyPoint p;
        g.setColor(new Color(240, 120, 20));
        g.fillRect(20, 60, 600, 600);				//绘制背景
        g.setColor(Color.darkGray);					//绘制网格
        for (i=0; i<19; i++)
        {
            g.drawLine(50, 90 + 30 * i, 590, 90 + 30 * i);
            g.drawLine(50 + 30 * i, 90, 50 + 30 * i, 630);
        }

        g.setColor(Color.black);
        g.fillOval(10 * 30 + 20 - 3, 10 * 30 + 60 - 3, 7, 7);			//绘制5个标志点
        g.fillOval( 4 * 30 + 20 - 3,  4 * 30 + 60 - 3, 7, 7);
        g.fillOval( 4 * 30 + 20 - 3, 16 * 30 + 60 - 3, 7, 7);
        g.fillOval(16 * 30 + 20 - 3,  4 * 30 + 60 - 3, 7, 7);
        g.fillOval(16 * 30 + 20 - 3, 16 * 30 + 60 - 3, 7, 7);

        Size = data.size();							//绘制棋子
        for (i=0; i<Size; i++)
        {
            p = (MyPoint)data.elementAt(i);
            if (p.getcolor() == 0)
            {
                g.setColor(new Color(20, 20, 20));
            }
            else
            {
                g.setColor(Color.white);
            }

            g.fillOval(50 + 30 * p.getx() - 10, 90 + 30 * p.gety() - 10, 21, 21);
        }
    }

    class ack_menu_first implements ActionListener
    {   //新游戏->先手 事件响应
        public void actionPerformed(ActionEvent e)
        {
            data.clear();
            repaint();
            State = 1;
            color = 0;
            pause = false;
            setmytitle();
            menu_back.setEnabled(false);
            menu_restart.setEnabled(false);
        }
    }

    class ack_menu_last implements ActionListener
    {   //新游戏->后手 事件响应
        public void actionPerformed(ActionEvent e)
        {
            data.clear();
            data.add(new MyPoint(9, 9, 0));
            repaint();
            State = 2;
            color = 1;
            pause = false;
            setmytitle();
            menu_back.setEnabled(false);
            menu_restart.setEnabled(false);
        }
    }

    class ack_menu_restart implements ActionListener
    {   //重新开始游戏 事件响应
        public void actionPerformed(ActionEvent e)
        {
            int p;
            int ccolor;
            if (computer)
            {
                p = data.size();
                if (p % 2 != 0)
                {
                    ccolor = 1 - ( (MyPoint)data.elementAt(p - 1)).getcolor();
                }
                else
                {
                    ccolor = 1 - ( (MyPoint)data.elementAt(p - 2)).getcolor();
                }

                if (ccolor == 0)
                {
                    data.clear();
                    repaint();
                    State = 1;
                    color = 0;
                    setmytitle();
                    menu_back.setEnabled(false);
                    menu_restart.setEnabled(false);
                }
                else
                {
                    data.clear();
                    data.add(new MyPoint(9, 9, 0));
                    repaint();
                    State = 2;
                    color = 1;
                    setmytitle();
                    menu_back.setEnabled(false);
                    menu_restart.setEnabled(false);
                }
            }
            else
            {
                data.clear();
                repaint();
                State = 1;
                color = 0;
                setmytitle();
                menu_back.setEnabled(false);
                menu_restart.setEnabled(false);
            }
        }
    }

    class ack_menu_reset implements ActionListener
    {   //重置游戏 事件响应
        public void actionPerformed(ActionEvent e)
        {
            data.clear();
            repaint();
            State = 0;
            mode = 0;
            pause = false;
            computer = true;
            menu_comp.setState(true);
            menu_int.setEnabled(false);
            menu_back.setEnabled(false);
            menu_restart.setEnabled(false);
            menu_prac.setLabel("切换为实战模式");
            setmytitle();
        }
    }

    class ack_menu_comp implements ItemListener
    {   //选择或取消人机对战功能 事件响应
        public void itemStateChanged(ItemEvent e)
        {
            computer = menu_comp.getState();
            State = 0;
            data.clear();
            repaint();
            setmytitle();
            if (!computer)
            {
                menu_last.setEnabled(false);
            }
            else
            {
                menu_last.setEnabled(true);
            }
        }
    }

    class ack_menu_prac implements ActionListener
    {   //模式选择 事件响应
        public void actionPerformed(ActionEvent e)
        {
            data.clear();
            repaint();
            State = 0;
            pause = false;
            if (mode == 0)
            {
                menu_prac.setLabel("切换为练习模式");
            }
            else
            {
                menu_prac.setLabel("切换为实战模式");
            }

            mode = 1 - mode;
            setmytitle();
        }
    }

    class ack_menu_back implements ActionListener
    {   //悔棋 事件响应
        public void actionPerformed(ActionEvent e)
        {
            int i, Size;
            Size = data.size();
            if (Size == 0)
            {
                return;
            }

            if (!computer)
            {
                data.remove(Size - 1);
                Size--;
                if (Size % 2 == 0)
                {
                    State = 1;
                    color = ((MyPoint)data.elementAt(Size - 1)).getcolor();
                }
                else
                {
                    State = 2;
                    color = 1 - ((MyPoint)data.elementAt(Size - 1)).getcolor();
                }
            }
            else
            {
                if (Size % 2 == 0)
                {
                    data.remove(Size - 1);
                    State++;
                }
                else if (Size == 3)
                {
                    data.clear();
                    State = 1;
                }
                else
                {
                    data.remove(Size - 1);
                    data.remove(Size - 2);
                    data.remove(Size - 3);
                    data.remove(Size - 4);
                }
            }

            if (data.size() <= 1)
            {
                pause = false;
                menu_int.setLabel("暂停游戏");
                menu_int.setEnabled(false);
                menu_back.setEnabled(false);
                menu_restart.setEnabled(false);
            }
            else
            {
                pause = true;
                menu_int.setLabel("继续游戏");
                State = 0;

            }

            repaint();
            setmytitle();
        }
    }

    class ack_menu_exit implements ActionListener
    {   //退出 事件响应
        public void actionPerformed(ActionEvent e)
        {
            ObjectOutputStream oFstream;
            FileOutputStream ostream;
            try {
                ostream = new FileOutputStream("close.c6db");
                oFstream = new ObjectOutputStream(ostream);
                oFstream.writeObject(data);
                oFstream.flush();
                ostream.close();
            } catch (Exception x)
            {
                x.printStackTrace();
            };

            System.exit(0);
        }
    }

    class ack_menu_int implements ActionListener
    {   //暂停游戏事件响应
        public void actionPerformed(ActionEvent e)
        {
            if (!pause)
            {
                State = 0;
                pause = true;
                menu_int.setLabel("继续游戏");
                setmytitle();
            }
            else
            {
                int Size;
                Size = data.size();
                menu_int.setLabel("暂停游戏");
                if (Size % 2 == 0)
                {
                    State = 1;
                    color = ((MyPoint)data.elementAt(Size-1)).getcolor();
                }
                else
                {
                    State = 2;
                    color = 1 - ((MyPoint)data.elementAt(Size-1)).getcolor();
                }

                pause = false;
                setmytitle();
            }
        }
    }

    class amouse extends MouseAdapter
    {   //鼠标在棋盘上点击 事件响应
        public void mouseClicked(MouseEvent e)
        {
            //分析无效操作，包括非法点击，点击不可靠等
            if (State == 0 || pause)
            {
                return;
            }

            int x, y;
            x = e.getX();
            y = e.getY();
            if (  ((x - 35) % 30 > 25)
                    ||((y - 75) % 30 > 25) )
            {
                return;
            }

            x = (x - 35)/30;
            y = (y - 75)/30;
            if (  (x < 0)||(x > 18)
                    ||(y < 0)||(y > 18) )
            {
                return;
            }

            int i, Size;
            Size = data.size();
            for (i=0; i<Size; i++)	//重复下子
                if (  (((MyPoint)data.elementAt(i)).getx() == x)
                        &&(((MyPoint)data.elementAt(i)).gety() == y) )
                {
                    return;
                }

            Graphics g = MyFrame.this.getGraphics();

            if (color == 0)
            {
                g.setColor(new Color(20, 20, 20));
            }
            else
            {
                g.setColor(Color.white);
            }

            g.fillOval(50 + 30 * x - 10, 90 + 30 * y - 10, 21, 21);

            menu_restart.setEnabled(true);
            menu_int.setEnabled(true);

            MyPoint p;
            p = new MyPoint(x, y, color);
            data.add(p);
            if (mode == 0)
            {
                menu_back.setEnabled(true);
            }

            State--;
            if (computer)
            {
                if (kernel.hadsix())
                {
                    msgbox("恭喜你战胜了电脑！！！");
                    State = 0;
                    setmytitle();
                    repaint();
                }
                else if (State == 0)
                {
                    kernel.cal(1 - color);
                    repaint();
                    if (kernel.hadsix())
                    {
                        msgbox("电脑获得胜利！！！");
                        setmytitle();
                    }
                    else
                    {
                        State = 2;
                    }
                }
            }
            else
            {
                if (kernel.hadsix())
                {
                    if (color == 0)
                        msgbox("黑方获胜！！！");
                    else
                        msgbox("白方获胜！！！");
                    State = 0;
                    setmytitle();
                    repaint();
                }
                else if (State == 0)
                {
                    State = 2;
                    color = 1 - color;
                }
            }
        }
    }

    class ack_menu_save implements ActionListener
    {   //保存 事件响应
        public void actionPerformed(ActionEvent e)
        {
            FileDialog myFileDialog = new FileDialog(MyFrame.this,"save",FileDialog.SAVE);
            myFileDialog.setVisible(true);
            String dir = myFileDialog.getDirectory();
            String fname = myFileDialog.getFile();
            if ((dir != null) && (fname != null))
            {
                String fullFileName = (new String(fname)).toLowerCase();
                if (!fullFileName.endsWith(".c6db"))
                {
                    fullFileName = fullFileName + ".c6db";
                }
                fullFileName = dir + fullFileName;
                ObjectOutputStream oFstream;
                FileOutputStream ostream;
                try {
                    ostream = new FileOutputStream(fullFileName);
                    oFstream = new ObjectOutputStream(ostream);
//					System.out.println(fullFileName);		//TestCode
                    oFstream.writeObject(data);
                    oFstream.flush();
                    ostream.close();
                } catch (Exception x)
                {
                    x.printStackTrace();
                    msgbox(new String("文件打开失败！！！"));
                };
            }
        }
    }

    class ack_menu_open implements ActionListener
    {   //打开 事件响应
        @SuppressWarnings("unchecked")
        public void actionPerformed(ActionEvent e)
        {
            FileDialog f = new FileDialog(MyFrame.this,"open",FileDialog.LOAD);
            f.setVisible(true);
            String dir = f.getDirectory();
            String fname = f.getFile();
            if ((dir != null) && (fname != null))
            {
                String file = (new String(fname)).toLowerCase();
                if (!file.endsWith(".c6db"))
                {
                    file = file + ".c6db";
                }
                file = dir + file;
                FileInputStream istream;
                ObjectInputStream p;
                try {
                    istream = new FileInputStream(file);
                    p = new ObjectInputStream(istream);
                    // the Xlint:unchecked warning is suppressed
                    data = (Vector<MyPoint>)p.readObject();
                    istream.close();
                    kernel.set(data);
                    pause = true;
                    State = 0;
                    menu_int.setLabel("继续游戏");
                    menu_int.setEnabled(true);
                } catch (Exception x)
                {
                    x.printStackTrace();
                    msgbox(new String("文件打开失败！！！"));
                };

                repaint();
                setmytitle();
            }
        }
    }

    void setmytitle()
    {
        String s;
        s = new String("Connect 6");
        if (State != 0)
        {
            s += " - 游戏进行中";
        }
        else
        {
            if (pause)
                s += " - 游戏已暂停";
            else
                s += " - 游戏未开始";
        }
        if (mode == 0)
            s += " - 练习模式";
        else
            s += " - 实战模式";
        if (computer)
            s += " - 人机对战";
        else
            s += " - 双人对战";
        setTitle(s);
    }

    void msgbox(String msg)
    {   //弹出消息窗
        Point position = MyFrame.this.getLocation();
        MyFrame.this.setEnabled(false);
        position.translate(250, 300);
        myDialog = new Dialog(MyFrame.this, "游戏结束");
        myDialog.setSize(150,100);
        myDialog.setLayout(new FlowLayout(FlowLayout.CENTER,1000,10));
        myDialog.add(new Label(msg));
        myDialogBotton = new Button("确定");
        myDialog.add(myDialogBotton);
        myDialog.setLocation(position);
        myDialog.setResizable(false);
        myDialog.setVisible(true);
        myDialogBotton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                MyFrame.this.setEnabled(true);
                MyFrame.this.myDialog.dispose();
            }
        });
        myDialog.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                MyFrame.this.setEnabled(true);
                MyFrame.this.myDialog.dispose();
            }
        });
        return;
    }
    class Wclose extends WindowAdapter
    {   //窗口关闭
        public void windowClosing(WindowEvent e)
        {
            if (!pause)
            {
                data.clear();
            }

            ObjectOutputStream oFstream;
            FileOutputStream ostream;
            try {
                ostream = new FileOutputStream("close.c6db");
                oFstream = new ObjectOutputStream(ostream);
                oFstream.writeObject(data);
                oFstream.flush();
                ostream.close();
            } catch (Exception x)
            {
                x.printStackTrace();
            };

            System.exit(0);
        }
    }
}
