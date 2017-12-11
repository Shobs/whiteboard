package main.java;

import java.awt.*;
import javax.swing.*;
import main.java.controller.*;
import main.java.controller.Canvas;

public class Whiteboard extends JFrame {
	private Canvas canvas;
	private Controls controls;

	//Creates controls panel and canvas panel for Whiteboard
	public Whiteboard() {
		super("WhiteBoard");
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);

		BorderLayout bl = new BorderLayout();
		super.setLayout(bl); 
		canvas = new Canvas();
		controls = new Controls(canvas);

		JPanel buttons = controls.createButtons();
		canvas.setControls(controls);
		super.add(canvas, BorderLayout.CENTER);
		super.add(buttons, BorderLayout.WEST);

		this.pack();
		super.setVisible(true);
	}

	public static void main(String[] args) {
		Whiteboard board = new Whiteboard();
	}
}
