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
		//0 = ��ʼ����Ϸ��ͣ״̬
		//1 = �û�������1��
		//2 = �û�������2��
	boolean pause;
		//��Ϸ�Ƿ�����ͣ״̬
		
	int color;
		//0 = ����
		//1 = ����
		//-1 = �հ�
		
	boolean computer;
		//0 = ���˻���ս
		//1 = �˻���ս
	int mode;
		//0 = ��ϰģʽ
		//1 = ʵսģʽ
	MenuBar m_MenuBar = new MenuBar();
	Menu m_game = 		new Menu("��Ϸ");
	Menu m_ngame = 			new Menu("��ʼ����Ϸ");
	MenuItem m_first = 			new MenuItem("����(��Һ�������)");
	MenuItem m_last = 			new MenuItem("����(��Ұ������)");	
	MenuItem m_restart = 		new MenuItem("���¿�ʼ��Ϸ");
	MenuItem m_reset = 			new MenuItem("������Ϸ");
	CheckboxMenuItem m_comp = 		new CheckboxMenuItem("�˻���ս");
	MenuItem m_prac = new MenuItem("�л�Ϊʵսģʽ");
	MenuItem m_int = 		new MenuItem("��ͣ��Ϸ");
	MenuItem m_exit = 		new MenuItem("�˳�");
	MenuItem m_back = 		new MenuItem("����");
	Menu m_file = 		new Menu("��Ϸս��");
	MenuItem m_open = 		new MenuItem("����Ϸս��");
	MenuItem m_save = 		new MenuItem("������Ϸս��");

	
	myFrame()
	{
		super("Connect 6 - ��Ϸ��δ��ʼ - ��ϰģʽ");
		setSize(650, 680);
		setResizable(false);
		
		data = new Vector();
		line = new Vector();
		kernel = new alg(data, line);
		State = 0;
		pause = false;
		computer = true;
		
		//�˵�������ť����
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

		//���ڼ���
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
			m_int.setLabel("������Ϸ");
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
			m_prac.setLabel("�л�Ϊʵսģʽ");
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
				m_prac.setLabel("�л�Ϊ��ϰģʽ");
			}
			else
			{
				m_prac.setLabel("�л�Ϊʵսģʽ");
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
				m_int.setLabel("��ͣ��Ϸ");
				m_int.setEnabled(false);
				m_back.setEnabled(false);
				m_restart.setEnabled(false);
			}
			else
			{
				pause = true;
				m_int.setLabel("������Ϸ");
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
				m_int.setLabel("������Ϸ");
				setmytitle();
			}
			else
			{
				int j;
				j = data.size();
				m_int.setLabel("��ͣ��Ϸ");
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
					msgbox("��ϲ��սʤ�˵��ԣ�����");
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
						msgbox("���Ի��ʤ��������");
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
						msgbox("�ڷ���ʤ������");
					else
						msgbox("�׷���ʤ������");
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
					msgbox(new String("�ļ���ʧ�ܣ�����"));
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
					m_int.setLabel("������Ϸ");
					m_int.setEnabled(true);
				}catch (Exception x)
				{
					x.printStackTrace();
					msgbox(new String("�ļ���ʧ�ܣ�����"));
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
			s += " - ��Ϸ������";
		}
		else
		{
			if (pause)
				s += " - ��Ϸ����ͣ";
			else
				s += " - ��Ϸδ��ʼ";
		}
		if (mode == 0)
			s += " - ��ϰģʽ";
		else
			s += " - ʵսģʽ";
		if (computer)
			s += " - �˻���ս";
		else
			s += " - ˫�˶�ս";
		setTitle(s);
	}
	
	void msgbox(String msg)
	{//������Ϣ��
		myFrame.this.setEnabled(false);
		dlg = new Dialog(myFrame.this, "��Ϸ����");
		dlg.setSize(150,100);
		dlg.setLayout(new FlowLayout(FlowLayout.CENTER,1000,10));
		dlg.add(new Label(msg));
		dlgb = new Button("ȷ��");
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
	{//���ڹر�
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