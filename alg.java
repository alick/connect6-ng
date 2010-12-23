import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class alg
{
	private Vector data;
		//IO共享内存部分，储存每一个棋子的位置与颜色
	private Vector line;
		//O共享内存部分，储存每一条线的位置与颜色
	private int cp[][];
	
	int ms[];
	int os[];
	
	alg(Vector _data, Vector _line)
	{
		data = _data;
		line = _line;
		cp = new int[19][];
		for (int i=0; i<19; i++)
		{
			cp[i] = new int[19];
			for (int j=0; j<19; j++)
				cp[i][j] = -1;
		}
		
		os = new int[]{0, 0, 2, 20, 65535, 65535, 65535};
		ms = new int[]{0, 0, 1, 10, 35, 35, 999999};
		
	}
	
	void set(Vector _data)
	{
		data = _data;
	}
	
	void clrp()
	{
		for (int i=0; i<19; i++)
			for (int j=0; j<19; j++)
				cp[i][j] = -1;
	}
	
	void cal(int color)
	{
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
			cp[p.getx()][p.gety()] = p.getcolor();
		}

		int x1, x2, y1, y2, _x1, _x2, _y1, _y2;
		int v, vmax;
		int k;
		k = 1;
		_x1 = _x2 = _y1 = _y2 = 0;
		vmax = -99999999;
		for (x1=0; x1<19; x1++)
		for (x2=0; x2<19; x2++)
		for (y1=0; y1<19; y1++)
		for (y2=0; y2<19; y2++)
		{
			if (  (cp[x1][y1] != -1)
				||(cp[x2][y2] != -1))
				continue;
			if (x1 == x2 && y1 == y2)
				continue;
			if (!check(x1, y1) || !check(x2, y2))
				continue;
			
			cp[x1][y1] = color;
			cp[x2][y2] = color;
			
			v = value(color);
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
			{
				if (Math.random() * k <= 1)
				{
					_x1 = x1;
					_x2 = x2;
					_y1 = y1;
					_y2 = y2;
				}
				k++;
			}
			cp[x1][y1] = -1;
			cp[x2][y2] = -1;
		}
		System.out.println(vmax);
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
		if (  cp[x-i][y-i] + cp[x][y-i] + cp[x+i][y-i]
			+ cp[x-i][y] + cp[x][y] + cp[x+i][y]
			+ cp[x-i][y+i] + cp[x][y+i] + cp[x+i][y+i] != -9)
			return true;
		i = 2;
		if (  cp[x-i][y-i] + cp[x][y-i] + cp[x+i][y-i]
			+ cp[x-i][y] + cp[x][y] + cp[x+i][y]
			+ cp[x-i][y+i] + cp[x][y+i] + cp[x+i][y+i] != -9)
			return true;
		return false;
	}
	
	int value(int color)
	{
		sixp l = new sixp();
		
		int _value;
		_value = 0;
		
		for (int i=0; i<19; i++)
		{//水平方向
			l.add(cp[i][0]);
			l.add(cp[i][1]);
			l.add(cp[i][2]);
			l.add(cp[i][3]);
			l.add(cp[i][4]);
			for (int j=5; j<19; j++)
			{
				l.add(cp[i][j]);
				_value += ms[l.score(color)];
				_value -= os[l.score(1-color)];
			}
		}
		
		for (int i=0; i<19; i++)
		{//竖直方向
			l.add(cp[0][i]);
			l.add(cp[1][i]);
			l.add(cp[2][i]);
			l.add(cp[3][i]);
			l.add(cp[4][i]);
			for (int j=5; j<19; j++)
			{
				l.add(cp[j][i]);
				_value += ms[l.score(color)];
				_value -= os[l.score(1-color)];
			}
		}
		
		//"\"方向
		for (int i=0; i<14; i++)
		{
			l.add(cp[0][i]);
			l.add(cp[1][i+1]);
			l.add(cp[2][i+2]);
			l.add(cp[3][i+3]);
			l.add(cp[4][i+4]);
			for (int j=5; i+j < 19; j++)
			{
				l.add(cp[j][i+j]);
				_value += ms[l.score(color)];
				_value -= os[l.score(1-color)];
			}
		}
		for (int i=1; i<14; i++)
		{
			l.add(cp[i][0]);
			l.add(cp[i+1][1]);
			l.add(cp[i+2][2]);
			l.add(cp[i+3][3]);
			l.add(cp[i+4][4]);
			for (int j=5; i+j < 19; j++)
			{
				l.add(cp[i+j][j]);
				_value += ms[l.score(color)];
				_value -= os[l.score(1-color)];
			}
		}
		
		//"/"方向
		for (int i=5; i<19; i++)
		{
			l.add(cp[0][i]);
			l.add(cp[1][i-1]);
			l.add(cp[2][i-2]);
			l.add(cp[3][i-3]);
			l.add(cp[4][i-4]);
			for (int j=5; j <= i; j++)
			{
				l.add(cp[j][i-j]);
				_value += ms[l.score(color)];
				_value -= os[l.score(1-color)];
			}
		}
		for (int i=1; i<14; i++)
		{
			l.add(cp[i][18]);
			l.add(cp[i+1][17]);
			l.add(cp[i+2][16]);
			l.add(cp[i+3][15]);
			l.add(cp[i+4][14]);
			for (int j=5; i+j < 19; j++)
			{
				l.add(cp[i+j][18-j]);
				_value += ms[l.score(color)];
				_value -= os[l.score(1-color)];
			}
		}		
		return _value;
	}
	
	void calline()
	{
	}
	
	boolean hadsix(int color)
	{
		int cps[][];
		cps = new int[19][];
		int i, j;
		
		for (i=0; i<19; i++)
		{//水平方向
			cps[i] = new int[19];
			cps[i][0] = (cp[i][0] == color) ? 1 : 0;
			for (j=1; j<19; j++)
			{
				cps[i][j] = (cp[i][j] == color) ? (cps[i][j-1] + 1) : 0;
				if (cps[i][j] == 6)
				{	return true;}
			}
		}
		for (j=0; j<19; j++)
		{//竖直方向
			cps[0][j] = (cp[0][j] == color) ? 1 : 0;
			for (i=1; i<19; i++)
			{
				cps[i][j] = (cp[i][j] == color) ? (cps[i-1][j] + 1) : 0;
				if (cps[i][j] == 6)
				{	return true;}
			}
		}
		
		for (i=0; i<19; i++)
		{//"\"方向
			cps[0][i] = (cp[0][i] == color) ? 1 : 0;
			cps[i][0] = (cp[i][0] == color) ? 1 : 0;
		}
		for (i=1; i<19; i++)
			for (j=1; j<19; j++)
			{
				cps[i][j] = (cp[i][j] == color) ? (cps[i-1][j-1] + 1) : 0;
				if (cps[i][j] == 6)
				{	return true;}
			}
			
		for (i=0; i<19; i++)
		{//"/"方向
			cps[0][i] = (cp[0][i] == color) ? 1 : 0;
			cps[i][18] = (cp[i][18] == color) ? 1 : 0;
		}
		for (i=1; i<19; i++)
			for (j=17; j>=0; j--)
			{
				cps[i][j] = (cp[i][j] == color) ? (cps[i-1][j+1] + 1) : 0;
				if (cps[i][j] == 6)
				{	return true;}
			}
		
		return false;
	}
	
	boolean hadsix()
	{		
		mypoint p;
		int size = data.size();
		clrp();
		for (int i=0; i<size; i++)
		{
			p = (mypoint)data.elementAt(i);
			cp[p.getx()][p.gety()] = p.getcolor();
		}
		return (hadsix(0)||hadsix(1));
	}
}


class sixp
{
	int i[] = new int[6];
	int colors[] = new int[3];
	int j;
	sixp()
	{
		j = 0;
		colors[1] = colors[2] = 0;
		colors[0] = 6;
		for (int s=0; s<6; s++)
			i[s] = -1;
	}
	
	void add(int color)
	{
		colors[i[j] + 1]--;
		i[j++] = color;
		j %= 6;
		colors[color + 1]++;
	}
	
	int score(int color)
	{
		if (colors[1 - color + 1] != 0)
			return 0;
		return colors[color + 1];
	}
}