


import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;


class GameGUI extends JPanel {
 private GraphicsPanel    _puzzleGraphics;
 private ModelOfPuzzle _puzzleModel = new ModelOfPuzzle();
 static int minute, second;
 private static javax.swing.Timer t;
 public final DecimalFormat dc = new DecimalFormat("00");

 public GameGUI() {

     JButton newGameBut = new JButton("����� ����");
     newGameBut.addActionListener(new StartNewGame());
     JLabel time = new JLabel();
     JButton bestTime = new JButton("�������");
     bestTime.addActionListener(new OpenTime());
     JPanel controlPanel = new JPanel();
     controlPanel.setLayout(new FlowLayout());
     controlPanel.add(newGameBut);
     controlPanel.add(time);
     controlPanel.add(bestTime);
     _puzzleGraphics = new GraphicsPanel();

     
     t = new javax.swing.Timer(
         1000,
         new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 time.setText(dc.format(minute) + ":" + dc.format(second));
                 second++;
                 if (second >= 60) {
                     second %= 60;
                     minute++;
                 }
             }
         });
     t.start();
     this.setLayout(new BorderLayout());
     this.add(controlPanel, BorderLayout.NORTH);
     this.add(_puzzleGraphics, BorderLayout.CENTER);
 }


// TODO ������� ����� ���������
 class GraphicsPanel extends JPanel implements MouseListener {
     private static final int ROWS = 4;
     private static final int COLS = 4;
     
     private static final int SizeOfCell = 100;
     private Font _biggerFont;

     public GraphicsPanel() {
 
         _biggerFont = new Font("SansSerif", Font.BOLD, SizeOfCell/2);
         this.setPreferredSize(
                new Dimension(SizeOfCell * COLS, SizeOfCell*ROWS));
         this.setBackground(Color.black);
         this.addMouseListener(this);
         }
     int x,y;
     public void paintComponent(Graphics g) {
         super.paintComponent(g);
         for (int row=0; row<ROWS; row++) {
             for (int col=0; col<COLS; col++) {
                  x = col * SizeOfCell;
                  y = row * SizeOfCell;
                 String text = _puzzleModel.getValue(row, col);
                 if (text != null) {
                     g.setColor(Color.red);
                     g.fillRect(x+2, y+2, SizeOfCell-4, SizeOfCell-4);
                     g.setColor(Color.black);
                     g.setFont(_biggerFont);
                     g.drawString(text, x+20, y+(3*SizeOfCell)/4);
                 }
             }}
         }
     
     
    
     public void mousePressed(MouseEvent e) {

         int col = e.getX()/SizeOfCell;
         int row = e.getY()/SizeOfCell;
         
         if (!_puzzleModel.moveRect(row, col)) {
         
             Toolkit.getDefaultToolkit().beep();
         } 
         
         this.repaint();
         if (_puzzleModel.endGame()){
        	 EndGame endG = new EndGame();
        	 endG.setPreferredSize(new Dimension(500, 200));
        	 endG.setVisible(true);  
        	 endG.pack();
        	 endG.setResizable(false);
        	 t.stop();
        	 GameIn15.window.setVisible(false); 
         }
     }
     

     public void mouseClicked (MouseEvent e) {}
     public void mouseReleased(MouseEvent e) {}
     public void mouseEntered (MouseEvent e) {}
     public void mouseExited  (MouseEvent e) {}
 }
 

 public class StartNewGame implements ActionListener {
     public void actionPerformed(ActionEvent e) {
         _puzzleModel.reset();
         
         _puzzleGraphics.repaint();
         second = 0;
         minute = 0;
         t.restart();
     }
 }
 public class EndGame extends JFrame{
	 EndGame(){
		 addWindowListener(new WindowAdapter()
		 {
		     public void windowClosing(WindowEvent e)
		     {
		    	 GameIn15.window.setVisible(true); 
		     }
		 });
		 JPanel pan = new JPanel();
		 pan.setPreferredSize(new Dimension(200,200));
		 JLabel finish = new JLabel("Congratulation. Your time - "+dc.format(minute) + ":" + dc.format(second)+" Enter your name");
		 JTextField name = new JTextField(15);
		 name.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	if(OpenTime.players.isEmpty()){
	            	OpenTime.players.add(name.getText() + " "+dc.format(minute) + ":" + dc.format(second));
	            }else{ boolean added = false;
	            	for (int i=0; i < OpenTime.players.size();i++){
	            	String str = OpenTime.players.get(i);
	            	String [] p = str.split(" ");
	            	String [] pp = p[1].split("\\D");
	            	int sec = (Integer.parseInt(pp[0])*60)+Integer.parseInt(pp[1]);
	            	int tmp = (minute*60)+second;
	            	if(sec <= tmp){
	            		OpenTime.players.add(i, name.getText() + " "+dc.format(minute) + ":" + dc.format(second));
	            		System.out.println("L="+OpenTime.players.size());
	            		added = true;
	            		break;
	            	}
	            }if (!added){OpenTime.players.add(name.getText() + " "+dc.format(minute) + ":" + dc.format(second));}} 
	            	
	            	}
	        });
		 add(pan);
		 pan.setLayout(new FlowLayout());
		 pan.add(finish);
		 pan.add(name);
	 }
 }
 public static class OpenTime extends JFrame implements ActionListener {
	 //TODO Make non-static!
	 static final ArrayList<String> players = new ArrayList<>();
	 public OpenTime(){
		 this.setLayout(new FlowLayout());
	 }
     public void actionPerformed(ActionEvent e) {
    	 JPanel pn = new JPanel();
    	 this.add(pn);
    	 pn.setLayout(new FlowLayout());
    	 DefaultListModel listModel = new DefaultListModel();
    	 if(!players.isEmpty()){
    	 for (int i=0; i<players.size();i++){
    		 listModel.addElement(players.get(i));
    	 }}
    	 JList tbl = new JList(listModel);
    	// tbl.setFocusable(false);
    	 tbl.setLayoutOrientation(JList.VERTICAL);
    	 tbl.setVisibleRowCount(5);
    	 pn.add(tbl);
    	// tbl.revalidate();
    	// tbl.repaint();
    	 pn.add(new JScrollPane(tbl));
    	// tbl.updateUI();
    	// pn.updateUI();
    	 setPreferredSize(new Dimension(100, 150));
    	 setVisible(true);  
    	 pack();
    	 setResizable(true);
     }
 }
}
