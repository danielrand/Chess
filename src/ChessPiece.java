import java.awt.Color;
import java.awt.Graphics;
//finished
public class ChessPiece {
	//make two2 array of chesspieces with blank spaces as a type of chess piece
	Color outerColor;
	Color innerColor;
	String chessType; 
	double xp, yp;
	public ChessPiece(String type, Color inner, Color outer){
		if (type.equals("Space") || type.equals("Pawn") || type.equals("Bishop") || type.equals("King") || type.equals("Rook") || type.equals("Knight") || type.equals("Queen")){
		chessType = type;
		innerColor = inner;
		outerColor = outer;
		}
		else throw new IllegalArgumentException("\nError: ChessPiece(String arg) must be a chess piece");
	}
	public ChessPiece(String type){
		if (type.equals("Space")) { 
		chessType = type;
		}
		else throw new IllegalArgumentException("\nError: ChessPiece(String arg) must be a chess piece");
	}
	//public double ge
	public void drawPiece(int x, int y, Graphics g){
		if(chessType.equals("Pawn")){//Daniel
			g.setColor(outerColor);
			g.fillOval(x+30, y+10, 32, 32);
			g.fillRect(x+30, y+40, 32, 7);
			g.fillRect(x+35, y+45, 22, 32);
			g.fillRect(x+27, y+75, 37, 8);
			g.fillRect(x+21, y+81, 49, 8);
			g.setColor(innerColor);
			g.fillOval(x+32,y+12,28,28);
			g.fillRect(x+32, y+42, 28, 3);
			g.fillRect(x+37, y+47, 18, 28);
			g.fillRect(x+29, y+77, 33, 4);
			g.fillRect(x+23, y+83, 45, 4);
		}
		else if(chessType.equals("Rook")){//Matthew
			g.setColor(outerColor);
			g.fillRect(x+15, y+20, 60, 10);
			g.fillRect(x+15, y+10, 10, 10);
			g.fillRect(x+40, y+10, 10, 10);
			g.fillRect(x+65, y+10, 10, 10);
			int r1x [] = {x+14, x+19,x+70,x+75};
			int r1y [] = {y+29,y+40,y+40,y+29};
			g.fillPolygon(r1x,r1y,4);
			g.fillRect(x+19, y+40, 51, 25);
			int r3x [] = {x+18,x+13,x+75,x+70};
			int r3y [] = {y+64,y+75,y+75,y+64};
			g.fillPolygon(r3x,r3y,4);
			g.fillRect(x+14, y+75, 61, 7);
			g.fillRect(x+10, y+80, 69, 8);
			g.setColor(innerColor);
			g.fillRect(x+17, y+22, 56, 6);
			g.fillRect(x+17, y+12, 6, 8);
			g.fillRect(x+42, y+12, 6, 8);
			g.fillRect(x+67, y+12, 6, 8);
			int r2x [] = {x+17, x+21,x+68,x+72};
			int r2y [] = {y+30,y+38,y+38,y+30};
			g.fillPolygon(r2x,r2y,4);
			g.fillRect(x+21, y+41,47, 22);
			int r4x [] = {x+20,x+16,x+72,x+68};
			int r4y [] = {y+65,y+73,y+73,y+65};
			g.fillPolygon(r4x,r4y,4);
			g.fillRect(x+16, y+76, 57, 4);
			g.fillRect(x+12, y+82, 65, 4);
		}
		else if (chessType.equals("Knight")){
			x = x +10;
			y = y + 5;
			g.setColor(innerColor);
			int [] ys = {y+20, y+30, y+30, y+5, y+5, y+5, y+5, y+15, y+15, y+75, y+75, y+75, y+75, y+70}; 
			int [] xs = {x+32, x+5, x+5, x+25, x+25, x+40, x+40, x+50, x+50, x+50, x+50, x+10, x+10, x+32};
			g.fillPolygon(xs, ys, 14);
			g.fillArc(x+20, y+20, 25, 50, 90, 180);
			g.setColor(outerColor);

			g.drawArc(x+21, y+21, 25, 50, 90, 180);
			int [] y2s = {y+20, y+30, y+30, y+5, y+5, y+5, y+5, y+15, y+15, y+75, y+75, y+75, y+75, y+70}; 
			int [] x2s = {x+32, x+5, x+5, x+25, x+25, x+40, x+40, x+50, x+50, x+50, x+50, x+10, x+10, x+32};
			g.drawPolygon(x2s, y2s, 14);
			
		}
		else if(chessType.equals("Queen")){//Daniel
			g.setColor(outerColor);
			g.fillOval(x+27, y+7, 36, 23);
			g.fillOval(x+22, y+25, 46, 7);
			int a [] = {x+37,x+30,x+60,x+53};
			int b [] = {y+31,y+80,y+80,y+31};
			g.fillPolygon(a, b, 4);
			g.fillOval(x+23, y+78, 43, 10);
			g.setColor(innerColor);
			g.fillOval(x+29, y+9, 32, 19);
			g.fillOval(x+24, y+27, 42, 3);
			int a2 [] = {x+39,x+32,x+58,x+51};
			int b2 [] = {y+33,y+78,y+78,y+33};
			g.fillPolygon(a2, b2, 4);
			g.fillOval(x+25, y+80, 39, 6);
		}
		else if (chessType.equals("King")){//Daniel
			g.setColor(outerColor);
			g.fillRect(x+37, y+2, 16, 16);
			g.fillRect(x+30, y+5, 30, 9);
			int b [] = {x+25,x+30,x+60,x+65};
			int c [] = {y+15,y+37,y+37,y+15};
			g.fillPolygon(b,c,4);
			g.fillRect(x+26, y+36, 39, 10);
			g.fillRect(x+33, y+44, 24, 30);
			g.fillRect(x+27,y+72,37,10);
			g.fillRect(x+21, y+80, 49, 10);
			g.setColor(innerColor);
			g.fillRect(x+39, y+4, 12, 12);
			g.fillRect(x+32, y+7, 26, 5);
			int b2 [] = {x+28,x+32,x+58,x+62};
			int c2 [] = {y+18,y+35,y+35,y+18};
			g.fillPolygon(b2,c2,4);
			g.fillRect(x+28, y+38, 35, 6);
			g.fillRect(x+35, y+46, 20, 26);
			g.fillRect(x+29,y+74,33,6);
			g.fillRect(x+23, y+82, 45, 6);
		}
		else if (chessType.equals("Bishop")){//Daniel
			g.setColor(innerColor);
			g.fillOval(x+25, y+15, 40, 60);
			g.fillRect(x+40, y+5, 10, 15);
			g.fillRect(x+25, y+70, 40, 15);
			g.setColor(outerColor);
			g.drawOval(x+25, y+15, 40, 60);
			g.drawRect(x+40, y+5, 10, 15);
			g.drawRect(x+25, y+70, 40, 15);
			g.drawLine(x+30, y+25, x+60, y+65);
			g.drawLine(x+35, y+20, x+63, y+58);
		}
	}

}