package com.lthorup.ratmaze;

import java.awt.Graphics;

import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RatMazeView extends JPanel {

	private RatMaze maze;
	private int count = 1;
	private final int MAZE_SIZE = 40;
	
	public RatMazeView() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(count == 1){
					count = 2;
					maze.cells[0][0].isPath = true;
					maze.solve(maze.cells[0][0]);
					repaint();
				}
				else {
					count = 1;
					maze = new RatMaze(RatMazeView.this, MAZE_SIZE,MAZE_SIZE);
					repaint();
				}
			}
		});
		maze = new RatMaze(this, MAZE_SIZE,MAZE_SIZE);
	}
	
	@Override
	public void paint(Graphics g) {
		maze.paint(g);
	}
}
