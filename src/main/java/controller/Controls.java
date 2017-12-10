package main.java.controller;

import java.awt.*;
import java.awt.event.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import javafx.scene.control.TextInputDialog;
import main.java.model.*;
import main.java.view.*;
import main.java.model.DShapeModel;

public class Controls {
	Canvas canvas;
	JTextField textString ;
	JPanel container;
	JLabel clientOrServer;
	private JTable table;
	private JScrollPane tablePane;
	private ArrayList<ObjectOutputStream> outputs = new ArrayList<ObjectOutputStream>();
	protected boolean isServer = false;
	public Controls(Canvas c) {
		canvas = c;
		table= generateTable(canvas.getShapes());
		tablePane = new JScrollPane(table);
	}
	


	public JPanel createButtons() {
		 container = new JPanel(); // main VerticalBox which contains all
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
				if(isServer)
					sendRemote("add", rect);
				canvas.paintComponent(canvas.getGraphics());

			}
		});
		shapes.add(Rect);

		JButton oval = new JButton("Oval");
		oval.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DOvalModel oval = new DOvalModel();
				canvas.addShape(oval);
				if(isServer)
					sendRemote("add", oval);
				canvas.paintComponent(canvas.getGraphics());

			}
		});
		shapes.add(oval);

		JButton line = new JButton("Line");
		line.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DLineModel line = new DLineModel();
				canvas.addShape(line);
				if(isServer)
					sendRemote("add", line);
				canvas.paintComponent(canvas.getGraphics());

			}
		});
		shapes.add(line);
		
		
		JButton text = new JButton("Text");
		text.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DTextModel dText = new DTextModel();
				canvas.addShape(dText);
				if(isServer)
					sendRemote("add", dText);
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
				if(isServer)
					sendRemote("modify", canvas.selectedShape.getModel());
				
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
					if(isServer)
						sendRemote("front", canvas.selectedShape.getModel());
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
					if(isServer)
						sendRemote("back", canvas.selectedShape.getModel());
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
							//canvas.selectedShape.delete();
							if(isServer)
								sendRemote("remove", canvas.selectedShape.getModel());
							canvas.selectedShape = null;
							
							canvas.repaint();
							reDraw();
				}
				
				
			}
		});
		fourthPanel.add(removeShape);
		Box fifth = Box.createHorizontalBox();
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		fifth.add(saveButton);
		
		JButton openButton = new JButton("Open");
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				open();
			}
		});
		
		fifth.add(openButton);
		fifth.add(saveButton);
		
		
		JButton saveAsButton = new JButton("Save As PNG");
		saveAsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAsImage();
			}
		});
		
		fifth.add(saveAsButton);		
		container.add(fourthPanel);
		
		container.add(fifth);
		
		
		//Server and Clinets 
		Box sixth = Box.createHorizontalBox();
		JButton startServerButton = new JButton("Start Server");
		startServerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startServer();
			}
		});
		JButton startClientButton = new JButton("Start Client");
		startClientButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startClient();
			}
		});
		
		sixth.add(startServerButton);
		sixth.add(startClientButton);
		
		clientOrServer = new JLabel("");
		sixth.add(clientOrServer);
		
		
		container.add(sixth);
		
		container.add(tablePane);
		
		for (Component c : container.getComponents()) {
			((JComponent) c).setAlignmentX(Box.LEFT_ALIGNMENT);
		}

		return container;

	}
	

	public void reDraw()
	{
		 textString.setEditable((canvas.selectedShape instanceof DText));
		if(canvas.selectedShape instanceof DText )
		{
		  textString.setText(((DTextModel)canvas.selectedShape.getModel()).getStr());
		}
		
		container.remove(tablePane);
		table = generateTable(canvas.getShapes());
		
		tablePane = new JScrollPane(table);
		container.add(tablePane);
		container.revalidate();
		container.repaint();
	}
	
	private void save() {

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("C:\\Users\\anis\\Documents\\CS151"));
		int retrival = chooser.showSaveDialog(null);
		if (retrival == JFileChooser.APPROVE_OPTION) {
			try {
				XMLEncoder e = new XMLEncoder(
						new BufferedOutputStream(new FileOutputStream(chooser.getSelectedFile() + ".xml")));
				DShapeModel[] modelShapes = canvas.getModels();
				e.writeObject(modelShapes);
				e.flush();
				e.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}
	
	//make protected
	private void open() {
		
		JFileChooser fchooser = new JFileChooser();
		
		int retrival = fchooser.showOpenDialog(null);
		if (retrival == JFileChooser.APPROVE_OPTION) {
			XMLDecoder d = null;
			try {

				d = new XMLDecoder(new BufferedInputStream(new FileInputStream(fchooser.getSelectedFile())));
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
				return;
			}
			Object res = d.readObject();
			canvas.loadModels((DShapeModel[]) res);
			d.close();
		}
	}
	
	private void saveAsImage() {

		JFileChooser fChooser = new JFileChooser();
		fChooser.setCurrentDirectory(new File("/home/me/Documents"));
		int retrival = fChooser.showSaveDialog(null);
		if (retrival == JFileChooser.APPROVE_OPTION) {
			
			try {
				ImageIO.write(canvas.BufferedImage(), "PNG", new File(fChooser.getSelectedFile()+".PNG"));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}

	}
	
	private void startServer(){
		JTextField PortNumber = new JTextField();
		PortNumber.setText("47000");
		
		final JComponent[] inputs = new JComponent[] {
		        new JLabel("Please Enter Port Number"),
		        PortNumber   
		};
		int result = JOptionPane.showConfirmDialog(null, inputs, "Server", JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_OPTION,new ImageIcon("Images/url.png"));
		if (result == JOptionPane.OK_OPTION) {
			clientOrServer.setText("Server Mode");
			isServer = true;
			WelcomeServer ws = new WelcomeServer(Integer.parseInt(PortNumber.getText()));
			ws.start();
			
		}else{
			return;
		}
	}
	private void startClient(){
		JTextField PortNumber = new JTextField();
		PortNumber.setText("47000");
		
		final JComponent[] inputs = new JComponent[] {
		        new JLabel("Please Enter Port Number"),
		        PortNumber  
		};
		int result = JOptionPane.showConfirmDialog(null, inputs, "Client", JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_OPTION,new ImageIcon("Images/url.png"));
		if (result == JOptionPane.OK_OPTION) {
			clientOrServer.setText("Client Mode");
			ClientHandler ch = new ClientHandler("127.0.0.1", Integer.parseInt(PortNumber.getText()) );
			ch.start();
		}else{
			return;
		}
	}
	

	class WelcomeServer extends Thread
	{
		private int portNumber;
		WelcomeServer(int p)
		{
			portNumber = p;
		}
		
		public void run()
		{
			 try{
				   ServerSocket serverSocket = new ServerSocket(portNumber);
				   while(true){
					   Socket toClient = null;
					   
					   toClient = serverSocket.accept();
					   clientOrServer.setText("connected to Client");
					   outputs.add(new ObjectOutputStream(toClient.getOutputStream()));
					  
				   }	
			   }catch(IOException ex){
				   ex.printStackTrace();
			   }
		}
	}
	
	private class ClientHandler extends Thread {
        private String name;
        private int port;
        ClientHandler(String name, int port) {
        	this.setDaemon(true);
            this.name = name;
            this.port = port;
        }
   
        public void run() {
            try {
                
                Socket toServer = new Socket(name, port);
              
                ObjectInputStream in = new ObjectInputStream(toServer.getInputStream());
                System.out.println("client: connected!");
               
               
                while (true) {
                	
                	String action = (String) in.readObject();
                    String xmlString = (String) in.readObject();
                    XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(xmlString.getBytes()));
                    DShapeModel model = (DShapeModel) decoder.readObject();
                    System.out.println("action");
                    DShape shape = model.createShape();
//                    shape.setId(model.getId());
//                    shape.setWholeText(model.getWholeText());
//                    shape.setText(model.getWholeText());
//                    shape.setFontName(model.getFontName());
//                    if(model instanceof DTextModel)
//                    {
//                      Font font = new Font(((DTextModel)model).getType(), 10, 10);
//                    }
//                    shape.setFont(font);
                    int index = 0;
                    for(int i = 0; i < canvas.shapes.size(); i++) {
                    	if(action.equals("add"))
                    		break;
                    	if(canvas.shapes.get(i) == shape)
                    		index = i;
                    }
                 
                    switch(action) {
                    case "add":
                    	System.out.println("working");
                    	canvas.shapes.add(shape);
                    	canvas.paintComponent(canvas.getGraphics());
                    	reDraw();
                    	//tableData.add(shape.getShapeModel());
                    	break;
                    case "remove":
                    	canvas.shapes.remove(index);
                    	canvas.paintComponent(canvas.getGraphics());
                    	reDraw();
                    	//tableData.remove(index);
                    	break;
                    case "front":
                    	canvas.shapes.remove(index);
                    	//tableData.remove(index);
                    	canvas.shapes.add(shape);
                    	canvas.paintComponent(canvas.getGraphics());
                    	reDraw();
                    	//tableData.add(shape.getShapeModel());
                    	break;
                    case "back":
                    	canvas.shapes.remove(index);
                    	//tableData.remove(index);
                    	canvas.shapes.add(0, shape);
                    	canvas.paintComponent(canvas.getGraphics());
                    	reDraw();
                    	//tableData.add(0, shape.getShapeModel());
                    	break;
                    case "modify":
                    	
                    	canvas.shapes.get(index).setModel(model);
                    	canvas.paintComponent(canvas.getGraphics());
                    	reDraw();
                    	break;
                    case "selected":
                    	canvas.setSelectedShape(shape);
                    	canvas.paintComponent(canvas.getGraphics());
                    	reDraw();
                    	break;
                    }
                    canvas.paintComponent(canvas.getGraphics());
                  //  table.setItems(tableData);
                    //table.refresh();
                    //canvas.redraw();
                    
                }
            }
            catch (Exception ex) { // IOException and ClassNotFoundException
            	ex.printStackTrace();
                System.err.println("Connection interrupted");
            }
            // Could null out client ptr.
            // Note that exception breaks out of the while loop,
            // thus ending the thread.
       }
    }
	
	public JTable generateTable(ArrayList<DShape> shapes){
		String[] col = {"X", "Y", "Width","Height"};
		
		Object[][] data = new Object[shapes.size()][4];
		for(int i = 0; i < shapes.size(); i++){
			data[i][0] = shapes.get(i).getModel().getX();
			data[i][1] = shapes.get(i).getModel().getY();
			data[i][2] = shapes.get(i).getModel().getWidth();
			data[i][3] = shapes.get(i).getModel().getHeight();
		}
		JTable table= new JTable(data, col);
		table.setFillsViewportHeight(true);
		return table;
	}
	
	public synchronized void sendRemote(String action, DShapeModel shape) {
        // Convert the message object into an xml string.
        OutputStream memStream = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(memStream);
        encoder.writeObject(shape);
        encoder.close();
        String xmlString = memStream.toString();
        
        Iterator<ObjectOutputStream> it = outputs.iterator();
        while (it.hasNext()) {
            ObjectOutputStream out = it.next();
            try {
            	out.writeObject(action);
                out.flush();
                out.writeObject(xmlString);
                out.flush();
            }
            catch (Exception ex) {
                ex.printStackTrace();
                System.err.println("Connection interrupted");
                it.remove();
                
            }
        }
	}
}

