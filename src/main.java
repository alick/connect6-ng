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
	alg kernel;
	Dialog myDialog;
	Button myDialogBotton;
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

	//�˵���
	MenuBar menu_MenuBar = new MenuBar();
	Menu menu_game = 		new Menu("��Ϸ");
	Menu menu_ngame = 			new Menu("��ʼ����Ϸ");
	MenuItem menu_first = 			new MenuItem("����(��Һ�������)");
	MenuItem menu_last = 			new MenuItem("����(��Ұ������)");	
	MenuItem menu_restart = 		new MenuItem("���¿�ʼ��Ϸ");
	MenuItem menu_reset = 			new MenuItem("������Ϸ");
	CheckboxMenuItem menu_comp = 		new CheckboxMenuItem("�˻���ս");
	MenuItem menu_prac = new MenuItem("�л�Ϊʵսģʽ");
	MenuItem menu_int = 		new MenuItem("��ͣ��Ϸ");
	MenuItem menu_exit = 		new MenuItem("�˳�");
	MenuItem menu_back = 		new MenuItem("����");
	Menu menu_file = 		new Menu("��Ϸս��");
	MenuItem menu_open = 		new MenuItem("����Ϸս��");
	MenuItem menu_save = 		new MenuItem("������Ϸս��");

	
	myFrame()
	{
		super("Connect 6 - ��Ϸ��δ��ʼ - ��ϰģʽ");
		setSize(650, 680);
		setResizable(false);
		
		//��ʼ������
		data = new Vector();
		kernel = new alg(data);
		State = 0;
		pause = false;
		computer = true;
		
		//�˵�������ť����
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

		//���ڼ���
		addWindowListener(new Wclose());
		addMouseListener(new amouse());
		
		//����Ϸʱ��ȡ�ļ�
		FileInputStream istream;
		ObjectInputStream iFstream;
		try{
			istream = new FileInputStream("close.c6db");
			iFstream = new ObjectInputStream(istream);
			data = (Vector)iFstream.readObject();
			istream.close();
			kernel.set(data);
		}catch (Exception x)
		{
			x.printStackTrace();
		};
		
		if (data.size() != 0)
		{
			pause = true;
			State = 0;
			menu_int.setEnabled(true);
			menu_int.setLabel("������Ϸ");
		}
		
		repaint();
		setmytitle();
		setVisible(true);
	}

	public void paint(Graphics g)
	{//�������
		int i, Size;
		mypoint p;
		g.setColor(new Color(240, 120, 20));
		g.fillRect(20, 60, 600, 600);				//���Ʊ���
		g.setColor(Color.darkGray);					//��������
		for (i=0; i<19; i++)
		{
			g.drawLine(50, 90 + 30 * i, 590, 90 + 30 * i);
			g.drawLine(50 + 30 * i, 90, 50 + 30 * i, 630);
		}
		
		g.setColor(Color.black);
		g.fillOval(10 * 30 + 20 - 3, 10 * 30 + 60 - 3, 7, 7);			//����5����־��
		g.fillOval( 4 * 30 + 20 - 3,  4 * 30 + 60 - 3, 7, 7);
		g.fillOval( 4 * 30 + 20 - 3, 16 * 30 + 60 - 3, 7, 7);
		g.fillOval(16 * 30 + 20 - 3,  4 * 30 + 60 - 3, 7, 7);
		g.fillOval(16 * 30 + 20 - 3, 16 * 30 + 60 - 3, 7, 7);
		
		Size = data.size();							//��������
		for (i=0; i<Size; i++)
		{
			p = (mypoint)data.elementAt(i);
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
	{//����Ϸ->���� �¼���Ӧ
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
	{//����Ϸ->���� �¼���Ӧ
		public void actionPerformed(ActionEvent e)
		{
			data.clear();
			data.add(new mypoint(9, 9, 0));
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
	{//���¿�ʼ��Ϸ �¼���Ӧ
		public void actionPerformed(ActionEvent e)
		{
			int p;
			int ccolor;
			if (computer)
			{
				p = data.size();
				if (p % 2 != 0)
				{
					ccolor = 1 - ( (mypoint)data.elementAt(p - 1)).getcolor();
				}
				else
				{
					ccolor = 1 - ( (mypoint)data.elementAt(p - 2)).getcolor();
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
					data.add(new mypoint(9, 9, 0));
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
	{//������Ϸ �¼���Ӧ
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
			menu_prac.setLabel("�л�Ϊʵսģʽ");
			setmytitle();
		}
	}
	
	class ack_menu_comp implements ItemListener
	{//ѡ���ȡ���˻���ս���� �¼���Ӧ
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
	{//ģʽѡ�� �¼���Ӧ
		public void actionPerformed(ActionEvent e)
		{
			data.clear();
			repaint();
			State = 0;
			pause = false;
			if (mode == 0)
			{
				menu_prac.setLabel("�л�Ϊ��ϰģʽ");
			}
			else
			{
				menu_prac.setLabel("�л�Ϊʵսģʽ");
			}
			
			mode = 1 - mode;
			setmytitle();
		}
	}
	
	class ack_menu_back implements ActionListener
	{//���� �¼���Ӧ
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
					color = ((mypoint)data.elementAt(Size - 1)).getcolor();
				}
				else
				{
					State = 2;
					color = 1 - ((mypoint)data.elementAt(Size - 1)).getcolor();
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
				menu_int.setLabel("��ͣ��Ϸ");
				menu_int.setEnabled(false);
				menu_back.setEnabled(false);
				menu_restart.setEnabled(false);
			}
			else
			{
				pause = true;
				menu_int.setLabel("������Ϸ");
				State = 0;
				
			}
			
			repaint();
			setmytitle();
		}
	}
	
	class ack_menu_exit implements ActionListener
	{//�˳� �¼���Ӧ
		public void actionPerformed(ActionEvent e)
		{
			ObjectOutputStream oFstream;
			FileOutputStream ostream;
			try{
				ostream = new FileOutputStream("close.c6db");
				oFstream = new ObjectOutputStream(ostream);
				oFstream.writeObject(data);
				oFstream.flush();ostream.close();
			}catch (Exception x)
			{
				x.printStackTrace();
			};
			
			System.exit(0);
		}		
	}
	
	class ack_menu_int implements ActionListener
	{//��ͣ��Ϸ�¼���Ӧ
		public void actionPerformed(ActionEvent e)
		{
			if (!pause)
			{
				State = 0;
				pause = true;
				menu_int.setLabel("������Ϸ");
				setmytitle();
			}
			else
			{
				int Size;
				Size = data.size();
				menu_int.setLabel("��ͣ��Ϸ");
				if (Size % 2 == 0)
				{
					State = 1;
					color = ((mypoint)data.elementAt(Size-1)).getcolor();
				}
				else
				{
					State = 2;
					color = 1 - ((mypoint)data.elementAt(Size-1)).getcolor();
				}
				
				pause = false;
				setmytitle();
			}
		}
	}
	
	class amouse extends MouseAdapter
	{//����������ϵ�� �¼���Ӧ
		public void mouseClicked(MouseEvent e)
		{
			//������Ч�����������Ƿ������������ɿ���
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
			for (i=0; i<Size; i++)	//�ظ�����
				if (  (((mypoint)data.elementAt(i)).getx() == x)
					&&(((mypoint)data.elementAt(i)).gety() == y) )
			{
				return;
			}
			
			Graphics g = myFrame.this.getGraphics();
			
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
			
			mypoint p;
			p = new mypoint(x, y, color);
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
	
	class ack_menu_save implements ActionListener
	{//���� �¼���Ӧ
		public void actionPerformed(ActionEvent e)
		{
			FileDialog myFileDialog = new FileDialog(myFrame.this,"save",FileDialog.SAVE);
			myFileDialog.setVisible(true);
			String dir = myFileDialog.getDirectory();
			String fname = myFileDialog.getFile();
			if ((dir != null) && (fname != null))
			{
				String fullFileName = (new String(fname)).toLowerCase();
				if (!fullFileName.endsWith(".c6db"))
				{fullFileName = fullFileName + ".c6db";}
				fullFileName = dir + fullFileName;
				ObjectOutputStream oFstream;
				FileOutputStream ostream;
				try{
					ostream = new FileOutputStream(fullFileName);
					oFstream = new ObjectOutputStream(ostream);
//					System.out.println(fullFileName);		//TestCode
					oFstream.writeObject(data);
					oFstream.flush();
					ostream.close();
				}catch (Exception x)
				{
					x.printStackTrace();
					msgbox(new String("�ļ���ʧ�ܣ�����"));
				};
			}
		}
	}
	
	class ack_menu_open implements ActionListener
	{//�� �¼���Ӧ
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
					menu_int.setLabel("������Ϸ");
					menu_int.setEnabled(true);
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
		myDialog = new Dialog(myFrame.this, "��Ϸ����");
		myDialog.setSize(150,100);
		myDialog.setLayout(new FlowLayout(FlowLayout.CENTER,1000,10));
		myDialog.add(new Label(msg));
		myDialogBotton = new Button("ȷ��");
		myDialog.add(myDialogBotton);
		myDialog.setVisible(true);
		myDialogBotton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{	
				myFrame.this.setEnabled(true);
				myFrame.this.myDialog.dispose();
			}
		});
		myDialog.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				myFrame.this.setEnabled(true);
				myFrame.this.myDialog.dispose();
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
			
			ObjectOutputStream oFstream;
			FileOutputStream ostream;
			try{
				ostream = new FileOutputStream("close.c6db");
				oFstream = new ObjectOutputStream(ostream);
				oFstream.writeObject(data);
				oFstream.flush();
				ostream.close();
			}catch (Exception x)
			{
				x.printStackTrace();
			};
			
			System.exit(0);
		}
	}
}

