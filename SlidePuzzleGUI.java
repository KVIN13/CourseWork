package qwer;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


class SlidePuzzleGUI extends JPanel {
 private GraphicsPanel    _puzzleGraphics;
 private SlidePuzzleModel _puzzleModel = new SlidePuzzleModel();




 public SlidePuzzleGUI() {

     JButton newGameButton = new JButton("New Game");
     newGameButton.addActionListener(new NewGameAction());


     JPanel controlPanel = new JPanel();
     controlPanel.setLayout(new FlowLayout());
     controlPanel.add(newGameButton);
     

     _puzzleGraphics = new GraphicsPanel();
     

     this.setLayout(new BorderLayout());
     this.add(controlPanel, BorderLayout.NORTH);
     this.add(_puzzleGraphics, BorderLayout.CENTER);
 }



 class GraphicsPanel extends JPanel implements MouseListener {
     private static final int ROWS = 4;
     private static final int COLS = 4;
     
     private static final int CELL_SIZE = 80;
     private Font _biggerFont;
     
     public GraphicsPanel() {
    	 Timer timer = new Timer(40, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				repaint();
			}
    		 
    	 });
         _biggerFont = new Font("SansSerif", Font.BOLD, CELL_SIZE/2);
         this.setPreferredSize(
                new Dimension(CELL_SIZE * COLS, CELL_SIZE*ROWS));
         this.setBackground(Color.black);
         this.addMouseListener(this);
         }
     int x,y;
     public void paintComponent(Graphics g) {
         super.paintComponent(g);
         for (int r=0; r<ROWS; r++) {
             for (int c=0; c<COLS; c++) {
                  x = c * CELL_SIZE;
                  y = r * CELL_SIZE;
                 String text = _puzzleModel.getFace(r, c);
                 if (text != null) {
                     g.setColor(Color.red);
                     g.fillRect(x+2, y+2, CELL_SIZE-4, CELL_SIZE-4);
                     g.setColor(Color.black);
                     g.setFont(_biggerFont);
                     g.drawString(text, x+20, y+(3*CELL_SIZE)/4);
                 }
             }}
         }
     
     
     
     public void mousePressed(MouseEvent e) {

         int col = e.getX()/CELL_SIZE;
         int row = e.getY()/CELL_SIZE;
         
         if (!_puzzleModel.moveTile(row, col)) {
         
             Toolkit.getDefaultToolkit().beep();
         }
         
         this.repaint();  
     }
     

     public void mouseClicked (MouseEvent e) {}
     public void mouseReleased(MouseEvent e) {}
     public void mouseEntered (MouseEvent e) {}
     public void mouseExited  (MouseEvent e) {}
 }
 

 public class NewGameAction implements ActionListener {
     public void actionPerformed(ActionEvent e) {
         _puzzleModel.reset();
         _puzzleGraphics.repaint();
     }
 }

}