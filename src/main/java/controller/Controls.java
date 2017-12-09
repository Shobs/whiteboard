package main.java.controller;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.JTable;
import main.java.model.*;
import main.java.view.*;


public class Controls  {
	private Canvas canvas;
	private JTable table;
	public Controls(Canvas c) {
		canvas = c;
	}
	


	public JPanel createButtons() {
		JPanel container = new JPanel(); // main VerticalBox which contains all
											// buttons
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		container.setPreferredSize(new Dimension(400, 0));

		JPanel shapes = new JPanel(); // first Horizontal panel which contains
										// all shape
		shapes.setLayout(new BoxLayout(shapes, BoxLayout.LINE_AXIS));
		shapes.add(new JLabel("ADD : "));

		JButton Rect = new JButton("Rect");

		Rect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DRectModel rect = new DRectModel();
				canvas.addShape(rect);
				canvas.paintComponent(canvas.getGraphics());
				table = generateTable(canvas.getShapes());
			}
		});
		shapes.add(Rect);

		JButton oval = new JButton("Oval");
		oval.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DOvalModel oval = new DOvalModel();
				canvas.addShape(oval);
				canvas.paintComponent(canvas.getGraphics());
				table = generateTable(canvas.getShapes());
			}
		});
		shapes.add(oval);

		JButton line = new JButton("Line");
		line.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				table = generateTable(canvas.getShapes());
			}
		});
		shapes.add(line);
		
		
		JComboBox<String> fontC = new JComboBox<String>(
				GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
		fontC.setSelectedItem("Dialog");
		fontC.setMaximumSize(new Dimension(300, 150));

		fontC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.changeFont((String)fontC.getSelectedItem());
			}
		});

		JTextField textS = new JTextField("Hello");
		textS.setMaximumSize(new Dimension(200, 150));
		textS.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
			}

			public void removeUpdate(DocumentEvent e) {
			}

			public void insertUpdate(DocumentEvent e) {
				 canvas.changeContent(textS.getText());
			}
		});
		
		
		JButton text = new JButton("Text");
		text.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DTextModel dText = new DTextModel();
				canvas.addShape(dText);
				table = generateTable(canvas.getShapes());
//				canvas.paintComponent(canvas.getGraphics());
			}
		});
		shapes.add(text);
		container.add(shapes);

		JPanel secondPanel = new JPanel();
		secondPanel.setLayout(new BoxLayout(secondPanel, BoxLayout.LINE_AXIS));
		JButton setColor = new JButton("setColor");
		setColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(canvas.selectedShape != null)
				{
				Color initialBackground = canvas.selectedShape.getModel().getColor();
		        Color background = JColorChooser.showDialog(null,
		            "JColorChooser Sample", initialBackground);
				canvas.selectedShape.getModel().setColor(background);
				
				canvas.repaint();
				}
				
			}

		});
		secondPanel.add(setColor);
		container.add(secondPanel);

		//
		Box thirdPanel = Box.createHorizontalBox();
		JTextField textString = new JTextField("Whiteboard");
		textString.setMaximumSize(new Dimension(200, 25));
		thirdPanel.add(textString);
		JButton scriptButton = new JButton("Edwardian Script");
		scriptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		thirdPanel.add(scriptButton);
		container.add(thirdPanel);

		Box fourthPanel = Box.createHorizontalBox();
		JButton moveToFront = new JButton("Move To Front");
		moveToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(canvas.selectedShape != null){
					
					canvas.shapes.remove(canvas.shapes.indexOf(canvas.selectedShape));
					canvas.shapes.add(canvas.selectedShape);
					canvas.repaint();
				}
			}
		});
		fourthPanel.add(moveToFront);
		JButton moveToBack = new JButton("Move To Back");
		moveToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(canvas.selectedShape != null){
					canvas.shapes.remove(canvas.shapes.indexOf(canvas.selectedShape));
					canvas.shapes.add(0,canvas.selectedShape);
					canvas.repaint();
					table = generateTable(canvas.getShapes());
				}
			}
		});
		fourthPanel.add(moveToBack);
		JButton removeShape = new JButton("Remove Shape");
		removeShape.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(canvas.selectedShape != null)
				{
							canvas.shapes.remove(canvas.shapes.indexOf(canvas.selectedShape));
							//canvas.selectedShape.delete();
							canvas.selectedShape = null;
							canvas.repaint();
				}
				
				
			}
		});
		fourthPanel.add(removeShape);
		container.add(fourthPanel);
		
		JScrollPane scrollPane = new JScrollPane(generateTable(canvas.getShapes()));
		container.add(scrollPane);
		
		for (Component c : container.getComponents()) {
			((JComponent) c).setAlignmentX(Box.LEFT_ALIGNMENT);
		}

		return container;

	}
}

