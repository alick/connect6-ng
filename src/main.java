import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class main
{
	public static void main(String[] args)
	{
        myFrame form = new myFrame();
	}
}

class myFrame extends Frame
{
	Vector data;
	Vector line;
	alg kernel;
	Dialog dlg;
	Button dlgb;
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
	MenuBar m_MenuBar = new MenuBar();
	Menu m_game = 		new Menu("游戏");
	Menu m_ngame = 			new Menu("开始新游戏");
	MenuItem m_first = 			new MenuItem("先手(玩家黑棋先行)");
	MenuItem m_last = 			new MenuItem("后手(玩家白棋后行)");	
	MenuItem m_restart = 		new MenuItem("重新开始游戏");
	MenuItem m_reset = 			new MenuItem("重置游戏");
	CheckboxMenuItem m_comp = 		new CheckboxMenuItem("人机对战");
	MenuItem m_prac = new MenuItem("切换为实战模式");
	MenuItem m_int = 		new MenuItem("暂停游戏");
	MenuItem m_exit = 		new MenuItem("退出");
	MenuItem m_back = 		new MenuItem("悔棋");
	Menu m_file = 		new Menu("游戏战绩");
	MenuItem m_open = 		new MenuItem("打开游戏战绩");
	MenuItem m_save = 		new MenuItem("保存游戏战绩");

	
	myFrame()
	{
		super("Connect 6 - 游戏尚未开始 - 练习模式");
		setSize(650, 680);
		setResizable(false);
		
		data = new Vector();
		line = new Vector();
		kernel = new alg(data, line);
		State = 0;
		pause = false;
		computer = true;
		
		//菜单栏及按钮监听
		setMenuBar(m_MenuBar);
		m_MenuBar.add(m_game);
			m_game.add(m_ngame);
				m_ngame.add(m_first);	m_first.addActionListener(new afirst());
										m_first.setShortcut(new MenuShortcut(KeyEvent.VK_F2));				
				m_ngame.add(m_last);	m_last.addActionListener(new alast());
										m_last.setShortcut(new MenuShortcut(KeyEvent.VK_F3));
			m_game.add(m_restart);		m_restart.addActionListener(new arestart());	m_restart.setEnabled(false);
										m_restart.setShortcut(new MenuShortcut(KeyEvent.VK_F5));
			m_game.add(m_reset);		m_reset.addActionListener(new areset());
										m_reset.setShortcut(new MenuShortcut(KeyEvent.VK_F1));
			m_game.add(m_comp);			m_comp.addItemListener(new acomp());			m_comp.setState(true);
			m_game.add(m_prac);			m_prac.addActionListener(new aprac());
			m_game.add(m_int);			m_int.addActionListener(new aint());			m_int.setEnabled(false);
										m_int.setShortcut(new MenuShortcut(KeyEvent.VK_F4));
			m_game.add(m_back);			m_back.addActionListener(new aback());			m_back.setEnabled(false);
										m_back.setShortcut(new MenuShortcut(KeyEvent.VK_LEFT));
		m_game.add(m_exit);				m_exit.addActionListener(new aexit());
		m_MenuBar.add(m_file);
			m_file.add(m_open);			m_open.addActionListener(new aopen());
										m_open.setShortcut(new MenuShortcut(KeyEvent.VK_O));
			m_file.add(m_save);			m_save.addActionListener(new asave());
										m_save.setShortcut(new MenuShortcut(KeyEvent.VK_S));			

		//窗口监听
		addWindowListener(new Wclose());
		addMouseListener(new amouse());
		
		FileInputStream istream;
		ObjectInputStream p;
		try{
			istream = new FileInputStream("close.c6db");
			p = new ObjectInputStream(istream);
			data = (Vector)p.readObject();
			istream.close();
			kernel.set(data);
		}catch (Exception x)
		{
			x.printStackTrace();
		};
		
		if (data.size() != 0)
		{
			int s;
			pause = true;
			State = 0;
			m_int.setEnabled(true);
			m_int.setLabel("继续游戏");
		}
		
		repaint();
		
		setmytitle();
		setVisible(true);
	}

	public void paint(Graphics g)
	{
		int i, j;
		mypoint p;
		g.setColor(new Color(240, 120, 20));
		g.fillRect(20, 60, 600, 600);
		g.setColor(Color.darkGray);
		for (i=0; i<19; i++)
		{
			g.drawLine(50, 90 + 30 * i, 590, 90 + 30 * i);
			g.drawLine(50 + 30 * i, 90, 50 + 30 * i, 630);
		}
		g.setColor(Color.black);
		g.fillOval(320-3, 360-3, 7, 7);
		g.fillOval(140-3, 180-3, 7, 7);
		g.fillOval(140-3, 540-3, 7, 7);
		g.fillOval(500-3, 180-3, 7, 7);
		g.fillOval(500-3, 540-3, 7, 7);
		
		j = data.size();
		for (i=0; i<j; i++)
		{
			p = (mypoint)data.elementAt(i);
			if (p.getcolor() == 0)
			{	g.setColor(new Color(20, 20, 20));}
			else
			{	g.setColor(Color.white);}
			g.fillOval(50 + 30 * p.getx() - 10, 90 + 30 * p.gety() - 10, 21, 21);
		}
	}
	
	class afirst implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			data.clear();
			repaint();
			State = 1;
			color = 0;
			pause = false;
			setmytitle();
			m_back.setEnabled(false);
			m_restart.setEnabled(false);
		}
	}
	
	class alast implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			data.clear();
			data.add(new mypoint(9, 9, 0));
			repaint();
			State = 2;
			color = 1;
			pause = false;
			setmytitle();
			m_back.setEnabled(false);
			m_restart.setEnabled(false);
		}
	}
	
	class arestart implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int p;
			int ccolor;
			if (computer)
			{
				p = data.size();
				if (p % 2 != 0)
					ccolor = 1 - ((mypoint)data.elementAt(p-1)).getcolor();
				else
					ccolor = 1 - ((mypoint)data.elementAt(p-2)).getcolor();
				if (ccolor == 0)
				{
					data.clear();
					repaint();
					State = 1;
					color = 0;
					setmytitle();
					m_back.setEnabled(false);
					m_restart.setEnabled(false);
				}
				else
				{
					data.clear();
					data.add(new mypoint(9, 9, 0));
					repaint();
					State = 2;
					color = 1;
					setmytitle();
					m_back.setEnabled(false);
					m_restart.setEnabled(false);
				}
			}
			else
			{
				data.clear();
				repaint();
				State = 1;
				color = 0;
				setmytitle();
				m_back.setEnabled(false);
				m_restart.setEnabled(false);				
			}
		}
	}
	
	class areset implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			data.clear();
			repaint();
			State = 0;
			mode = 0;
			pause = false;
			computer = true;
			m_comp.setState(true);
			m_int.setEnabled(false);
			m_back.setEnabled(false);
			m_restart.setEnabled(false);
			m_prac.setLabel("切换为实战模式");
			setmytitle();
		}
	}
	
	class acomp implements ItemListener
	{
		public void itemStateChanged(ItemEvent e)
		{
			computer = m_comp.getState();
			State = 0;
			data.clear();
			repaint();
			setmytitle();
			if (!computer)
				m_last.setEnabled(false);
			else
				m_last.setEnabled(true);
		}
	}
	
	class aprac implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			data.clear();
			repaint();
			State = 0;
			pause = false;
			if (mode == 0)
			{
				m_prac.setLabel("切换为练习模式");
			}
			else
			{
				m_prac.setLabel("切换为实战模式");
			}
			mode = 1 - mode;
			setmytitle();
		}
	}
	
	class aback implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int i, j;
			j = data.size();
			if (j == 0)
			{	return;}
			if (!computer)
			{
				data.remove(j - 1);
				j--;
				if (j % 2 == 0)
				{
					State = 1;
					color = ((mypoint)data.elementAt(j-1)).getcolor();
				}
				else
				{
					State = 2;
					color = 1 - ((mypoint)data.elementAt(j-1)).getcolor();
				}
			}
			else
			{
				if (j % 2 == 0)
				{
					data.remove(j - 1);
					State++;
				}
				else if (j == 3)
				{
					data.clear();
					State = 1;
				}
				else
				{
					data.remove(j - 1);
					data.remove(j - 2);
					data.remove(j - 3);
					data.remove(j - 4);
				}
			}
			if (data.size() <= 1)
			{
				pause = false;
				m_int.setLabel("暂停游戏");
				m_int.setEnabled(false);
				m_back.setEnabled(false);
				m_restart.setEnabled(false);
			}
			else
			{
				pause = true;
				m_int.setLabel("继续游戏");
				State = 0;
				
			}
			repaint();
			setmytitle();
		}
	}
	
	class aexit implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			ObjectOutputStream p;
			FileOutputStream ostream;
			try{
				ostream = new FileOutputStream("close.c6db");
				p = new ObjectOutputStream(ostream);
				p.writeObject(data);
				p.flush();ostream.close();
			}catch (Exception x)
			{
				x.printStackTrace();
			};
			System.exit(0);
		}		
	}
	
	class aint implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (!pause)
			{
				State = 0;
				pause = true;
				m_int.setLabel("继续游戏");
				setmytitle();
			}
			else
			{
				int j;
				j = data.size();
				m_int.setLabel("暂停游戏");
				if (j % 2 == 0)
				{
					State = 1;
					color = ((mypoint)data.elementAt(j-1)).getcolor();
				}
				else
				{
					State = 2;
					color = 1 - ((mypoint)data.elementAt(j-1)).getcolor();
				}
				pause = false;
				setmytitle();
			}
		}
	}
	
	class amouse extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			if (State == 0 || pause)
				return;
			int x, y;
			x = e.getX();
			y = e.getY();
			if (  ((x - 35) % 30 > 25)
				||((y - 75) % 30 > 25))return;
			x = (x - 35)/30;
			y = (y - 75)/30;
			if (  (x < 0)||(x > 18)
				||(y < 0)||(y > 18))return;
			
			
			int i, j;
			
			j = data.size();
			for (i=0; i<j; i++)
				if (  (((mypoint)data.elementAt(i)).getx() == x)
					&&(((mypoint)data.elementAt(i)).gety() == y))
				return;
				
			Graphics g = myFrame.this.getGraphics();
			
			if (color == 0)
			{g.setColor(new Color(20, 20, 20));}
			else
			{g.setColor(Color.white);}
			
			g.fillOval(50 + 30 * x - 10, 90 + 30 * y - 10, 21, 21);
			
			m_restart.setEnabled(true);
			m_int.setEnabled(true);
			
			mypoint p;
			p = new mypoint(x, y, color);
			data.add(p);
			if (mode == 0)
				m_back.setEnabled(true);
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
						State = 2;
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
	
	class asave implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			FileDialog f = new FileDialog(myFrame.this,"save",FileDialog.SAVE);
			f.setVisible(true);
			String dir = f.getDirectory();
			String fname = f.getFile();
			if ((dir != null) && (fname != null))
			{
				String file = (new String(fname)).toLowerCase();
				if (!file.endsWith(".c6db"))
				{file = file + ".c6db";}
				file = dir + file;
				ObjectOutputStream p;
				FileOutputStream ostream;
				try{
					ostream = new FileOutputStream(file);
					p = new ObjectOutputStream(ostream);
					System.out.println(file);
					p.writeObject(data);
					p.flush();ostream.close();
				}catch (Exception x)
				{
					x.printStackTrace();
					msgbox(new String("文件打开失败！！！"));
				};
			}
		}
	}
	
	class aopen implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			FileDialog f = new FileDialog(myFrame.this,"open",FileDialog.LOAD);
			f.setVisible(true);
			String dir = f.getDirectory();
			String fname = f.getFile();
			if ((dir != null) && (fname != null))
			{
				String file = (new String(fname)).toLowerCase();
				if (!file.endsWith(".c6db"))
				{file = file + ".c6db";}
				file = dir + file;
				FileInputStream istream;
				ObjectInputStream p;
				try{
					istream = new FileInputStream(file);
					p = new ObjectInputStream(istream);
					data = (Vector)p.readObject();
					istream.close();
					kernel.set(data);
					pause = true;
					State = 0;
					m_int.setLabel("继续游戏");
					m_int.setEnabled(true);
				}catch (Exception x)
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
	{//弹出消息窗
		myFrame.this.setEnabled(false);
		dlg = new Dialog(myFrame.this, "游戏结束");
		dlg.setSize(150,100);
		dlg.setLayout(new FlowLayout(FlowLayout.CENTER,1000,10));
		dlg.add(new Label(msg));
		dlgb = new Button("确定");
		dlg.add(dlgb);
		dlg.setVisible(true);
		dlgb.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{	
				myFrame.this.setEnabled(true);
				myFrame.this.dlg.dispose();
			}
		});
		dlg.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				myFrame.this.setEnabled(true);
				myFrame.this.dlg.dispose();
			}
		});
		return;
	}	
	class Wclose extends WindowAdapter
	{//窗口关闭
		public void windowClosing(WindowEvent e)
		{
			if (!pause)
			{
				data.clear();
			}
			ObjectOutputStream p;
			FileOutputStream ostream;
			try{
				ostream = new FileOutputStream("close.c6db");
				p = new ObjectOutputStream(ostream);
				p.writeObject(data);
				p.flush();ostream.close();
			}catch (Exception x)
			{
				x.printStackTrace();
			};
			System.exit(0);
		}
	}
}

class mypoint implements Serializable
{
	private int x, y, color;
	mypoint(int _x, int _y, int _color)
	{
		x = _x;
		y = _y;
		color = _color;
	}
	int getx()
	{return x;}
	int gety()
	{return y;}
	int getcolor()
	{return color;}
}

class myline
{
	private int x1, y1, x2, y2, color;
	myline(int _x1, int _y1, int _x2, int _y2, int _color)
	{
		x1 = _x1;
		y1 = _y1;
		x2 = _y2;
		y2 = _y2;
		color = _color;
	}
	int getx1()
	{return x1;}
	int getx2()
	{return x2;}
	int gety1()
	{return y1;}
	int gety2()
	{return y2;}
	int getcolor()
	{return color;}
}