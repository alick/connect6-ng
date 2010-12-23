import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class alg
{
	private Vector data;
		//IO�����ڴ沿�֣�����ÿһ�����ӵ�λ������ɫ
	private Vector line;
		//O�����ڴ沿�֣�����ÿһ���ߵ�λ������ɫ
	private int ChessBoard[][];
	
	static int[] os = new int[]{0, 0, 2, 20, 65535, 65535, 65535};
	static int[] ms = new int[]{0, 0, 1, 10, 35, 35, 999999};	
	alg(Vector _data)
	{
		data = _data;
		ChessBoard = new int[19][];
		for (int i=0; i<19; i++)
		{
			ChessBoard[i] = new int[19];
			for (int j=0; j<19; j++)
			{
				ChessBoard[i][j] = -1;
			}
		}
	}
	
	void set(Vector _data)
	{
		data = _data;
	}
	
	void clrp()
	{
		for (int i=0; i<19; i++)
		{
			for (int j=0; j<19; j++)
			{
				ChessBoard[i][j] = -1;
			}
		}
	}
	
	void cal(int color)
	{//����֮����ö��ɫΪcolor���ӵ�λ�ã�ֱ�ӽ����ݲ���data��
		int size;
		size = data.size();
		if (size == 0)
		{
			data.add(new mypoint(9, 9, 0));
			return;
		}
		mypoint p;
		clrp();
		for (int i=0; i<size; i++)
		{
			p = (mypoint)data.elementAt(i);
			ChessBoard[p.getx()][p.gety()] = p.getcolor();
		}

		int x1, x2, y1, y2, _x1, _x2, _y1, _y2;
		int v, vmax;
		int k;
		k = 1;
		_x1 = _x2 = _y1 = _y2 = 0;
		vmax = -99999999;
		for (x1=0; x1<19; x1++)//ö��֮����ö���λ��
		for (x2=0; x2<19; x2++)
		for (y1=0; y1<19; y1++)
		for (y2=0; y2<19; y2++)
		{
			if (  (ChessBoard[x1][y1] != -1)
				||(ChessBoard[x2][y2] != -1)
				||(x1 == x2 && y1 == y2)
				||!check(x1, y1)
				||!check(x2, y2) )
			{
				continue;
			}
			
			ChessBoard[x1][y1] = color;
			ChessBoard[x2][y2] = color;
			
			v = value(color);//����÷�
			if (vmax < v)
			{
				vmax = v;
				k = 2;
				_x1 = x1;
				_x2 = x2;
				_y1 = y1;
				_y2 = y2;
			}
			else if (vmax == v)
			{//����÷���ͬ�����ѡ��һ��������֤��ͬ�÷ֵ������·��ȸ���
				if (Math.random() * k <= 1)
				{
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
		data.add(new mypoint(_x1, _y1, color));
		data.add(new mypoint(_x2, _y2, color));
	}
	
	boolean check(int x, int y)
	{
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
				+ ChessBoard[x-2][y+2]	+ ChessBoard[x][y+2]	+ ChessBoard[x+2][y+2] != -9) )
		{
			return true;
		}
		return false;
	}
	
	int value(int color)
	{//���㵱ǰ���̸���ɫ�ĵ÷�
		SixPoint l = new SixPoint();
		
		int _value;
		_value = 0;
		
		for (int i=0; i<19; i++)
		{//ˮƽ����
			l.add(ChessBoard[i][0]);
			l.add(ChessBoard[i][1]);
			l.add(ChessBoard[i][2]);
			l.add(ChessBoard[i][3]);
			l.add(ChessBoard[i][4]);
			for (int j=5; j<19; j++)
			{
				l.add(ChessBoard[i][j]);
				_value += ms[l.score(color)];
				_value -= os[l.score(1-color)];
			}
		}
		
		for (int i=0; i<19; i++)
		{//��ֱ����
			l.add(ChessBoard[0][i]);
			l.add(ChessBoard[1][i]);
			l.add(ChessBoard[2][i]);
			l.add(ChessBoard[3][i]);
			l.add(ChessBoard[4][i]);
			for (int j=5; j<19; j++)
			{
				l.add(ChessBoard[j][i]);
				_value += ms[l.score(color)];
				_value -= os[l.score(1-color)];
			}
		}
		
		//"\"����
		for (int i=0; i<14; i++)
		{
			l.add(ChessBoard[0][i]);
			l.add(ChessBoard[1][i+1]);
			l.add(ChessBoard[2][i+2]);
			l.add(ChessBoard[3][i+3]);
			l.add(ChessBoard[4][i+4]);
			for (int j=5; i+j < 19; j++)
			{
				l.add(ChessBoard[j][i+j]);
				_value += ms[l.score(color)];
				_value -= os[l.score(1-color)];
			}
		}
		for (int i=1; i<14; i++)
		{
			l.add(ChessBoard[i][0]);
			l.add(ChessBoard[i+1][1]);
			l.add(ChessBoard[i+2][2]);
			l.add(ChessBoard[i+3][3]);
			l.add(ChessBoard[i+4][4]);
			for (int j=5; i+j < 19; j++)
			{
				l.add(ChessBoard[i+j][j]);
				_value += ms[l.score(color)];
				_value -= os[l.score(1-color)];
			}
		}
		
		//"/"����
		for (int i=5; i<19; i++)
		{
			l.add(ChessBoard[0][i]);
			l.add(ChessBoard[1][i-1]);
			l.add(ChessBoard[2][i-2]);
			l.add(ChessBoard[3][i-3]);
			l.add(ChessBoard[4][i-4]);
			for (int j=5; j <= i; j++)
			{
				l.add(ChessBoard[j][i-j]);
				_value += ms[l.score(color)];
				_value -= os[l.score(1-color)];
			}
		}
		for (int i=1; i<14; i++)
		{
			l.add(ChessBoard[i][18]);
			l.add(ChessBoard[i+1][17]);
			l.add(ChessBoard[i+2][16]);
			l.add(ChessBoard[i+3][15]);
			l.add(ChessBoard[i+4][14]);
			for (int j=5; i + j < 19; j++)
			{
				l.add(ChessBoard[i+j][18-j]);
				_value += ms[l.score(color)];
				_value -= os[l.score(1-color)];
			}
		}		
		return _value;
	}
	
	boolean hadsix(int color)
	{//�����Ƿ��д���ɫ������
		int ChessBoardState[][];
		ChessBoardState = new int[19][];
		int i, j;
		
		for (i=0; i<19; i++)
		{//ˮƽ����
			ChessBoardState[i] = new int[19];
			ChessBoardState[i][0] = (ChessBoard[i][0] == color) ? 1 : 0;
			for (j=1; j<19; j++)
			{
				ChessBoardState[i][j] = (ChessBoard[i][j] == color) ? (ChessBoardState[i][j-1] + 1) : 0;
				if (ChessBoardState[i][j] == 6)
				{	
					return true;
				}
			}
		}
		for (j=0; j<19; j++)
		{//��ֱ����
			ChessBoardState[0][j] = (ChessBoard[0][j] == color) ? 1 : 0;
			for (i=1; i<19; i++)
			{
				ChessBoardState[i][j] = (ChessBoard[i][j] == color) ? (ChessBoardState[i-1][j] + 1) : 0;
				if (ChessBoardState[i][j] == 6)
				{	
					return true;
				}
			}
		}
		
		for (i=0; i<19; i++)
		{//"\"����
			ChessBoardState[0][i] = (ChessBoard[0][i] == color) ? 1 : 0;
			ChessBoardState[i][0] = (ChessBoard[i][0] == color) ? 1 : 0;
		}
		for (i=1; i<19; i++)
			for (j=1; j<19; j++)
			{
				ChessBoardState[i][j] = (ChessBoard[i][j] == color) ? (ChessBoardState[i-1][j-1] + 1) : 0;
				if (ChessBoardState[i][j] == 6)
				{	
					return true;
				}
			}
			
		for (i=0; i<19; i++)
		{//"/"����
			ChessBoardState[0][i] = (ChessBoard[0][i] == color) ? 1 : 0;
			ChessBoardState[i][18] = (ChessBoard[i][18] == color) ? 1 : 0;
		}
		for (i=1; i<19; i++)
			for (j=17; j>=0; j--)
			{
				ChessBoardState[i][j] = (ChessBoard[i][j] == color) ? (ChessBoardState[i-1][j+1] + 1) : 0;
				if (ChessBoardState[i][j] == 6)
				{	
					return true;
				}
			}
		
		return false;
	}
	
	boolean hadsix()
	{//�����Ƿ��������������ֺڰף�
		mypoint p;
		int size = data.size();
		clrp();
		for (int i=0; i<size; i++)
		{
			p = (mypoint)data.elementAt(i);
			ChessBoard[p.getx()][p.gety()] = p.getcolor();
		}
		
		return (hadsix(0)||hadsix(1));
	}
}


class SixPoint
{//�����ϵ�����6��λ�ö���
	int i[] = new int[6];
	int colors[] = new int[3];
	int CurrentPoint;
	SixPoint()
	{
		CurrentPoint = 0;
		colors[1] = colors[2] = 0;
		colors[0] = 6;
		for (int s=0; s<6; s++)
			i[s] = -1;
	}
	
	void add(int color)
	{//�ڶ����м���һ�����ӣ�ͬʱ�������һ��
		colors[i[CurrentPoint] + 1]--;
		i[CurrentPoint++] = color;
		CurrentPoint %= 6;
		colors[color + 1]++;
	}
	
	int score(int color)
	{//���ص�ǰ������Ӧ��ɫ�ĵ÷�
		if (colors[1 - color + 1] != 0)
			return 0;
		return colors[color + 1];
	}
}

class mypoint implements Serializable
{//��¼���꼰��ɫ
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