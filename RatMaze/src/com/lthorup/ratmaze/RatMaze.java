package com.lthorup.ratmaze;

import java.awt.Color;
import java.awt.Graphics;

public class RatMaze {
	
	private RatMazeView view;
	private int sizeX, sizeY;
	public Cell[][] cells;
	private Neighbor[] neighbors = { new Neighbor(1,0), new Neighbor(0,1), new Neighbor(-1,0), new Neighbor(0,-1) };
	private final int CELL_SIZE = 10;
	
	public RatMaze(RatMazeView view, int sizeX, int sizeY) {
		this.view = view;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		cells = new Cell[sizeX][sizeY];
		for (int y = 0; y < sizeY; y++)
			for (int x = 0; x < sizeX; x++)
				cells[x][y] = new Cell(x,y);
		cells[0][0].wall[3] = false;
		cells[sizeX-1][sizeY-1].wall[1] = false;
		Cell startCell = cells[randomInt(0,sizeX-1)][randomInt(0,sizeY-1)];
		startCell.visited = true;
		buildFrom(startCell);
	}
	
	private void buildFrom(Cell c) {
		int index = randomInt(0,3);
		for (int i = 0; i < 4; i++) {
			int nx = c.x + neighbors[index].dx;
			int ny = c.y + neighbors[index].dy;
			if (nx >= 0 && nx <= (sizeX-1) && ny >= 0 && ny <= (sizeY-1) && ! cells[nx][ny].visited) {
				Cell n = cells[nx][ny];
				c.wall[index] = false;
				int op = index + 2;
				if (op > 3)
					op -= 4;
				n.visited = true;
				n.wall[op] = false;
				buildFrom(n);
			}
			index += 1;
			if (index == 4)
				index = 0;
		}
	}
	
	public boolean solve(Cell c) {
		if(c.x == sizeX-1 && c.y == sizeY-1)
			return true;
		for(int i =0; i<4; i++){
			int nx = c.x + neighbors[i].dx;
			int ny = c.y + neighbors[i].dy;
			if(!c.wall[i] && ny >= 0 && ny < sizeY && !cells[nx][ny].isPath){
				cells[nx][ny].isPath = true;
				if(solve(cells[nx][ny]))
					return true;
			}
		}
		c.isPath = false;
		return false;
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, view.getBounds().width, view.getBounds().height);
		g.setColor(Color.GREEN);
		for (int y = 0; y < sizeY; y++)
			for (int x = 0; x < sizeX; x++)
				cells[x][y].paint(g);
	}
	
    private int randomInt(int min, int max) {
        return (int)((max-min+1) * Math.random() + min);
    }
	
	class Cell {
		public int x, y;
		public boolean visited;
		public boolean isPath;
		public boolean[] wall = new boolean[4];
		public Cell(int x, int y) {
			this.x = x;
			this.y = y;
			this.visited = false;
			this.isPath = false;
			for (int i = 0; i < 4; i++)
				wall[i] = true;
		}
		public void paint(Graphics g) {
			int left = x * CELL_SIZE + CELL_SIZE;
			int right = left + CELL_SIZE;
			int top = y * CELL_SIZE + CELL_SIZE;
			int bottom = top + CELL_SIZE;
			g.setColor(Color.GREEN);
			if (wall[0])
				g.drawLine(right, top, right, bottom);
			if (wall[1])
				g.drawLine(left, bottom, right, bottom);
			if (wall[2])
				g.drawLine(left, top, left, bottom);
			if (wall[3])
				g.drawLine(left, top, right, top);
			if(isPath){
				g.setColor(Color.RED);
				g.fillOval(left + (CELL_SIZE/4), top + (CELL_SIZE/4), CELL_SIZE/2, CELL_SIZE/2);
			}
		}
	}
	
	class Neighbor {
		public int dx, dy;
		Neighbor(int dx, int dy) { this.dx = dx; this.dy = dy; }
	}
}
