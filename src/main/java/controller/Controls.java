package main.java.controller;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

import main.java.model.*;
import main.java.view.*;

public class Controls {
	private Canvas canvas;
	private JTextField textString ;
	private JPanel container;
	private JTable table;
	private JScrollPane tablePane;

	public Controls(Canvas c) {
		canvas = c;
		container = new JPanel();
		table = generateTable(c.getShapes());
		tablePane = new JScrollPane(table);
	}

	public JPanel createButtons() {
		 // main VerticalBox which contains all
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
			}
		});
		shapes.add(Rect);

		JButton oval = new JButton("Oval");
		oval.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DOvalModel oval = new DOvalModel();
				canvas.addShape(oval);
				canvas.paintComponent(canvas.getGraphics());
			}
		});
		shapes.add(oval);

		JButton line = new JButton("Line");
		line.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DLineModel line = new DLineModel();
				canvas.addShape(line);
				canvas.paintComponent(canvas.getGraphics());
			}
		});
		shapes.add(line);

		JButton text = new JButton("Text");
		text.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DTextModel dText = new DTextModel();
				canvas.addShape(dText);
				canvas.paintComponent(canvas.getGraphics());
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
		textString = new JTextField("Hello");
		textString.setMaximumSize(new Dimension(200, 30));
		textString.setEditable(false);
		textString.getDocument().addDocumentListener(new DocumentListener() {

			public void changedUpdate(DocumentEvent e) {
				canvas.changeContent(textString.getText());

			}
			public void removeUpdate(DocumentEvent e) {
				canvas.changeContent(textString.getText());
			}
			public void insertUpdate(DocumentEvent e) {
				canvas.changeContent(textString.getText());
			}
		});


		thirdPanel.add(textString);
		JComboBox<String> fontC = new JComboBox<String>(
				GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
		fontC.setSelectedItem("Dialog");
		fontC.setMaximumSize(new Dimension(200, 30));

		fontC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.changeFont((String)fontC.getSelectedItem());
			}
		});
		thirdPanel.add(fontC);
		container.add(thirdPanel);

		Box fourthPanel = Box.createHorizontalBox();
		JButton moveToFront = new JButton("Move To Front");
		moveToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(canvas.selectedShape != null){	
					canvas.shapes.remove(canvas.shapes.indexOf(canvas.selectedShape));
					canvas.shapes.add(canvas.selectedShape);
					canvas.repaint();
					reDraw();
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
					reDraw();
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
					canvas.selectedShape = null;
					canvas.repaint();
					reDraw();
				}
			}
		});
		fourthPanel.add(removeShape);
		
		container.add(fourthPanel);
	
		container.add(tablePane);
		
		for (Component c : container.getComponents()) 
			((JComponent) c).setAlignmentX(Box.LEFT_ALIGNMENT);
		return container;
	}

	public void reDraw(){
		textString.setEditable((canvas.selectedShape instanceof DText));
		if(canvas.selectedShape instanceof DText )
			textString.setText(((DTextModel)canvas.selectedShape.getModel()).getStr());
		
		container.remove(tablePane);
		table = generateTable(canvas.getShapes());
		tablePane = new JScrollPane(table);
		container.add(tablePane);
		container.revalidate();
		container.repaint();
	}

	public JTable generateTable(ArrayList<DShape> shapes){
		String[] columnNames = {"X", "Y", "Width", "Height" };

		Object[][] data = new Object[shapes.size()][4];
		for(int i = 0; i < shapes.size(); i++){
			data[i][0] = shapes.get(i).getModel().getX();
			data[i][1] = shapes.get(i).getModel().getY();
			data[i][2] = shapes.get(i).getModel().getWidth();
			data[i][3] = shapes.get(i).getModel().getHeight();
		}
		
		JTable table = new JTable(data, columnNames);
		table.setFillsViewportHeight(true);
		return table;
	}
}

