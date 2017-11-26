

import javax.swing.JFrame;


class GameIn15 {

 public static void main(String[] args) {
     JFrame window = new JFrame("Пятнашки");
     window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     window.setContentPane(new GameGUI());
     window.pack();
     window.setVisible(true);  
     window.setResizable(false);
 }
}
