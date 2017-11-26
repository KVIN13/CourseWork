


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


class GameGUI extends JPanel {
 private GraphicsPanel    _puzzleGraphics;
 private ModelOfPuzzle _puzzleModel = new ModelOfPuzzle();




 public GameGUI() {

     JButton newGameBut = new JButton("Новая игра");
     newGameBut.addActionListener(new StartNewGame());


     JPanel controlPanel = new JPanel();
     controlPanel.setLayout(new FlowLayout());
     controlPanel.add(newGameBut);
     

     _puzzleGraphics = new GraphicsPanel();
     

     this.setLayout(new BorderLayout());
     this.add(controlPanel, BorderLayout.NORTH);
     this.add(_puzzleGraphics, BorderLayout.CENTER);
 }



 class GraphicsPanel extends JPanel implements MouseListener {
     private static final int ROWS = 4;
     private static final int COLS = 4;
     
     private static final int SizeOfCell = 100;
     private Font _biggerFont;
     
     public GraphicsPanel() {
    	 Timer timer = new Timer(40, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				repaint();
			}
    		 
    	 });
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
     }
 }

}
