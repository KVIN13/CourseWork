

class ModelOfPuzzle {
 private static final int ROWS = 4;
 private static final int COLS = 4;
 private Rect[][] _contents;  
 private Rect     _freeRect; 
 private int if15; 
 public ModelOfPuzzle() {
     _contents = new Rect[ROWS][COLS];
     reset();   }          

 public boolean endGame(){
	 if15 = 0;
	 for (int row=0; row<ROWS; row++) {
         for (int col=0; col<COLS; col++) {
        	if (_contents[row][col].isInFinalPosition(row, col)){
        		if15++;
        	} 
        	
 }}
	 if (if15 >= 15){
		return true;
	} else {return false;}
	 }
 String getValue(int row, int col) {
     return _contents[row][col].getValue();
 }

 public void reset() {
     for (int row=0; row<ROWS; row++) {
         for (int col=0; col<COLS; col++) {
             _contents[row][col] = new Rect(row, col, "" + (row*COLS+col+1));
         }
     }
     _freeRect = _contents[ROWS-1][COLS-1];
     _freeRect.setValue(null);
     
     for (int row=0; row<ROWS; row++) {
         for (int col=0; col<COLS; col++) {
             swapRect(row, col, (int)(Math.random()*ROWS)
                               , (int)(Math.random()*COLS));
         }
     }
 }
 
/* public void reset() {
     for (int row=0; row<ROWS; row++) {
         for (int col=0; col<COLS; col++) {
             _contents[row][col] = new Rect(row, col, "" + (row*COLS+col+1));
         }
     }
     _freeRect = _contents[ROWS-1][COLS-1];
     _freeRect.setValue(null);
 }*/
 public boolean moveRect(int r, int c) {
     return checkFree(r, c, -1, 0) || checkFree(r, c, 1, 0)
         || checkFree(r, c, 0, -1) || checkFree(r, c, 0, 1);
 }
 
 
 private boolean checkFree(int row, int col, int rdelta, int cdelta) {
     int rowNeighbor = row + rdelta;
     int colNeighbor = col + cdelta;
     if (isLegalRowCol(rowNeighbor, colNeighbor) 
               && _contents[rowNeighbor][colNeighbor] == _freeRect) {
         swapRect(row, col, rowNeighbor, colNeighbor);
         return true;
     }
     return false;
 }
 
 
 public boolean isLegalRowCol(int r, int c) {
     return r>=0 && r<ROWS && c>=0 && c<COLS;
 }
 

 private void swapRect(int row1, int col1, int row2, int col2) {
     Rect temp = _contents[row1][col1];
     _contents[row1][col1] = _contents[row2][col2];
     _contents[row2][col2] = temp;
     
     
         }
     
 }
class Rect {

 private int _row;     
 private int _col;    
 private String _value;  

 public Rect(int row, int col, String value) {
     _row = row;
     _col = col;
     _value = value;
 }
 public void setValue(String newValue) {
     _value = newValue;
 }
 public String getValue() {
     return _value;
 }



 public boolean isInFinalPosition(int r, int c) {
     return r==_row && c==_col;
 }
}
