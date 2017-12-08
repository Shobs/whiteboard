package main.java;

import java.awt.BorderLayout;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.MouseInputListener;

import main.java.controller.Canvas;
import main.java.controller.Controls;
import main.java.view.DText;

/*
 * whiteboard (main class)
 * 
 */
public class Whiteboard extends JFrame 
{
	Canvas canvas;
	Controls controls;

	/*
	 * constructor call super to create Jframe
	 */
	public Whiteboard() {
		super("WhiteBoard");
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);// set the defualt close
														// option

		BorderLayout bl = new BorderLayout();
		super.setLayout(bl); // setting up frame to border layout
		canvas = new Canvas();
		controls = new Controls(canvas);
		
		JPanel buttons = controls.createButtons();
		canvas.setControls(controls);
		super.add(canvas, BorderLayout.CENTER); // center of Frame is canvas
		super.add(buttons, BorderLayout.WEST);
		
		this.pack();
		super.setVisible(true);
	}

	public static void main(String[] args) {
		Whiteboard board = new Whiteboard();
	}

	
}
