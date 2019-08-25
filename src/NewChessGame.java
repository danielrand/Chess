import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.Timer;
// finished
public class NewChessGame extends JFrame implements MouseListener{

	ChessPiece [] [] pieces = new ChessPiece [8] [8];
	Color [] [] colors = new Color [8] [8];
	Color [] [] borders = new Color [8] [8];
	boolean [] rooksMove = {false,false,false,false};
	boolean [] kingsMove = {false,false};
	int oldRow, oldCol, clickCount=0, boxRow=0, boxCol, turnCounter=1;
	static int seconds;
	boolean isLegalMove = false;
	boolean winner = false, clicked, check;
	double clickX=0, clickY=0;
	boolean colored = false;
	public NewChessGame(String title) {
		super(title);
		addMouseListener(this);
		setLayout(null);	
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (i <= 1){
					colors[i][j] = Color.black;
					borders[i][j] = Color.white;
				}
				else if (i >= 6) {
					colors[i][j] = Color.white;
					borders[i][j] = Color.black;				
				}
				else {
					colors[i][j] = null;
					borders[i][j] = null;
				}
				System.out.print(colors[i][j]);
				if (i > 1 && i < 6)
					pieces[i][j] = new ChessPiece ("Space");
				else if (i == 1 || i == 6) //pawns 
					pieces[i][j] = new ChessPiece ("Pawn", colors[i][j], borders[i][j]);
				else if(j == 0 || j == 7)
					pieces[i][j] = new ChessPiece ("Rook", colors[i][j], borders[i][j]);
				else if(j == 1 || j == 6)
					pieces[i][j] = new ChessPiece ("Knight", colors[i][j], borders[i][j]);
				else if(j == 2 || j == 5)
					pieces[i][j] = new ChessPiece ("Bishop", colors[i][j], borders[i][j]);
				else if(j == 3)
					pieces[i][j] = new ChessPiece ("Queen", colors[i][j], borders[i][j]);
				else if(j == 4)
					pieces[i][j] = new ChessPiece ("King", colors[i][j], borders[i][j]);
			}System.out.println();
		}
	}
	
	public static void main(String[] args) {
		NewChessGame myWindow = new NewChessGame("Chess");
		myWindow.setVisible(true);
	}
	public void paint(Graphics g) {
		this.resize(749, 773);
		this.setBackground(Color.gray);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		drawBoard(g);
		 setIgnoreRepaint(true);

	}
	public void drawBoard(Graphics b) {
		int x = 14, y = 38;
		int boxes=0;
		for(int i = 0; i<8; i++){
			for(int j = 0; j < 8; j++){
				boxes++;
				movePiece(oldRow,oldCol,boxRow,boxCol,b);
				if(boxes%2==0){
					Color newColor = new Color(90, 39, 41);
					b.setColor(newColor);
				}
				else{
					b.setColor(Color.white);
				}	
				if (pieceSelected()){
					if (i == boxRow && j == boxCol)
						b.setColor(Color.black);
				}
				b.fillRect(x, y, 90, 90);
				pieces[i][j].drawPiece(x, y, b);
				b.setColor(Color.white);
				b.fillRect(900, 400, 200, 140);
				b.setColor(Color.black);
				b.fillRect(905, 405, 190, 130);
				b.setColor(Color.white);
				if (turnCounter % 2 == 1)
					b.drawString ("white to move",910,430);
				else		
					b.drawString ("black to move",910,430);
				x+=90;
			} y+=90; x=14; boxes++;	
		}
	}
	public void movePiece (int firstRow, int firstCol, int newRow, int newCol, Graphics g){ // where the magic happens
		check = false;
		int tempBox = boxRow; int tempCol = boxCol;
		boxRow = oldRow; boxCol = oldCol;
		if(clickCount>0 && !pieces[firstRow][firstCol].chessType.equals("Space") && pieceSelected()){
			isLegalMove =false;
			boolean inTheWay = false;
			boolean castleKing = false;
			boolean castleQueen = false;

			if (pieces[firstRow][firstCol].chessType.equals("Pawn")){ // Daniel
				if(pieces[firstRow][firstCol].innerColor.equals(Color.black) && turnCounter % 2 == 0){
					if ((newRow == firstRow+1 || (newRow == firstRow+2 && firstRow == 1)) && newCol == firstCol
							&& pieces[newRow][newCol].chessType.equals("Space"))
						isLegalMove = true;
					else if (newRow == firstRow + 1 && (newCol == firstCol-1 || newCol == firstCol +1) 
							&& !pieces[newRow][newCol].chessType.equals("Space") && !pieces[newRow][newCol].innerColor.equals(Color.black))
						isLegalMove = true;	
				}
				else if(pieces[firstRow][firstCol].innerColor.equals(Color.white))
					if ((newRow == firstRow-1 || (newRow == firstRow-2 && firstRow == 6)) && newCol == firstCol
							&& pieces[newRow][newCol].chessType.equals("Space"))
						isLegalMove = true;
					else if (newRow == firstRow - 1 && (newCol == firstCol-1 || newCol == firstCol+1) 
							&& !pieces[newRow][newCol].chessType.equals("Space") && !pieces[newRow][newCol].innerColor.equals(Color.white))
						isLegalMove = true;	
			}
			if (pieces[firstRow][firstCol].chessType.equals("Bishop") || pieces[firstRow][firstCol].chessType.equals("Queen")){ // Daniel + Matthew
				int temp = 0;
				if(pieces[firstRow][firstCol].innerColor.equals(Color.black) && turnCounter % 2 == 0){
					for(int p=0; p<=8; p++){
					if (newRow == firstRow+p && newCol == firstCol+p && (pieces[newRow][newCol].chessType.equals("Space") || !pieces[newRow][newCol].innerColor.equals(Color.black))){
						temp = firstCol + 1;
						for (int i = firstRow+1; i<newRow; i++){
							if (!pieces[i][temp].chessType.equals("Space")){ System.out.println("IN THE WAY!");
							inTheWay = true;}
							temp++;
						}
							isLegalMove = true;
					}
					if (newRow == firstRow+p && newCol == firstCol-p && (pieces[newRow][newCol].chessType.equals("Space") || !pieces[newRow][newCol].innerColor.equals(Color.black))){
							temp = firstCol - 1;
							for (int i = firstRow+1; i<newRow; i++){
								if (!pieces[i][temp].chessType.equals("Space")){ System.out.println("IN THE WAY!");
									inTheWay = true;}
								temp--;
							}
						isLegalMove = true;
					}
					if (newRow == firstRow-p && newCol == firstCol+p && (pieces[newRow][newCol].chessType.equals("Space") || !pieces[newRow][newCol].innerColor.equals(Color.black))){
						temp = firstCol + 1;
						for (int i = firstRow-1; i>newRow; i--){
							if (!pieces[i][temp].chessType.equals("Space")){ System.out.println("IN THE WAY!");
								inTheWay = true;}
							temp++;
						}
						isLegalMove = true;
					}
					if (newRow == firstRow-p && newCol == firstCol-p && (pieces[newRow][newCol].chessType.equals("Space") || !pieces[newRow][newCol].innerColor.equals(Color.black))){
						temp = firstCol - 1;
						for (int i = firstRow-1; i>newRow; i--){
							if (!pieces[i][temp].chessType.equals("Space")){ System.out.println("IN THE WAY!");
								inTheWay = true;}
							temp--;
						}
						isLegalMove = true;
					}
					}		
				}
				else if(pieces[firstRow][firstCol].innerColor.equals(Color.white))
						for(int q=0; q<=8; q++){
						if (newRow == firstRow+q && newCol == firstCol+q && (pieces[newRow][newCol].chessType.equals("Space") || !pieces[newRow][newCol].innerColor.equals(Color.white))){
							temp = firstCol + 1;
							for (int i = firstRow+1; i<newRow; i++){
								if (!pieces[i][temp].chessType.equals("Space")){ System.out.println("IN THE WAY!");
								inTheWay = true;}
								temp++;
							}
							isLegalMove = true;
						}
						if (newRow == firstRow+q && newCol == firstCol-q && (pieces[newRow][newCol].chessType.equals("Space") || !pieces[newRow][newCol].innerColor.equals(Color.white))){
							temp = firstCol - 1;
							for (int i = firstRow+1; i<newRow; i++){
								if (!pieces[i][temp].chessType.equals("Space")){ System.out.println("IN THE WAY!");
									inTheWay = true;}
								temp--;
							}
							isLegalMove = true;
						}
						if (newRow == firstRow-q && newCol == firstCol+q && (pieces[newRow][newCol].chessType.equals("Space") || !pieces[newRow][newCol].innerColor.equals(Color.white))){
							temp = firstCol + 1;
							for (int i = firstRow-1; i>newRow; i--){
								if (!pieces[i][temp].chessType.equals("Space")){ System.out.println("IN THE WAY!");
									inTheWay = true;}
								temp++;
							}
							isLegalMove = true;
						}
						if (newRow == firstRow-q && newCol == firstCol-q && (pieces[newRow][newCol].chessType.equals("Space") || !pieces[newRow][newCol].innerColor.equals(Color.white))){
							temp = firstCol - 1;
							for (int i = firstRow-1; i>newRow; i--){
								if (!pieces[i][temp].chessType.equals("Space")){ System.out.println("IN THE WAY!");
									inTheWay = true;}
								temp--;
							}
							isLegalMove = true;
						}
						}
				if (inTheWay)
					isLegalMove = false;
			}
			if (pieces[firstRow][firstCol].chessType.equals("Rook") // Daniel
					|| pieces[firstRow][firstCol].chessType.equals("Queen")){
				int temp = 0;
				if(pieces[firstRow][firstCol].innerColor.equals(Color.black) && turnCounter % 2 == 0){
					for(int p=0; p<=8; p++){
					if (newRow == firstRow+p && newCol == firstCol && (pieces[newRow][newCol].chessType.equals("Space") || !pieces[newRow][newCol].innerColor.equals(Color.black))){
						temp = firstCol;
						for (int i = firstRow+1; i<newRow; i++){
							if (!pieces[i][temp].chessType.equals("Space")){ System.out.println("IN THE WAY!");
							inTheWay = true;}
						}
							isLegalMove = true;
					}
					if (newRow == firstRow-p && newCol == firstCol && (pieces[newRow][newCol].chessType.equals("Space") || !pieces[newRow][newCol].innerColor.equals(Color.black))){
						temp = firstCol;
						for (int i = firstRow-1; i>newRow; i--){
							if (!pieces[i][temp].chessType.equals("Space")){ System.out.println("IN THE WAY!");
								inTheWay = true;}
						}
						isLegalMove = true;
					}
					if (newRow == firstRow && newCol == firstCol+p && (pieces[newRow][newCol].chessType.equals("Space") || !pieces[newRow][newCol].innerColor.equals(Color.black))){
						for (int i = firstCol+1; i<newCol; i++){
							if (!pieces[firstRow][i].chessType.equals("Space")){ System.out.println("IN THE WAY!");
								inTheWay = true;}
						}
						isLegalMove = true;
					}
					if (newRow == firstRow && newCol == firstCol-p && (pieces[newRow][newCol].chessType.equals("Space") || !pieces[newRow][newCol].innerColor.equals(Color.black))){
						for (int i = firstCol-1; i>newCol; i--){
							if (!pieces[firstRow][i].chessType.equals("Space")){ System.out.println("IN THE WAY!");
								inTheWay = true;}
						}
						isLegalMove = true;
					}
					}
				}
				else if(pieces[firstRow][firstCol].innerColor.equals(Color.white))
						for(int q=0; q<=8; q++){
							if (newRow == firstRow+q && newCol == firstCol && (pieces[newRow][newCol].chessType.equals("Space") || !pieces[newRow][newCol].innerColor.equals(Color.white))){
								temp = firstCol;
								for (int i = firstRow+1; i<newRow; i++){
									if (!pieces[i][temp].chessType.equals("Space")){ System.out.println("IN THE WAY!");
									inTheWay = true;}
								}
									isLegalMove = true;
							}
							if (newRow == firstRow-q && newCol == firstCol && (pieces[newRow][newCol].chessType.equals("Space") || !pieces[newRow][newCol].innerColor.equals(Color.white))){
									temp = firstCol;
									for (int i = firstRow-1; i>newRow; i--){
										if (!pieces[i][temp].chessType.equals("Space")){ System.out.println("IN THE WAY!");
											inTheWay = true;}
									}
								isLegalMove = true;
							}
							if (newRow == firstRow && newCol == firstCol+q && (pieces[newRow][newCol].chessType.equals("Space") || !pieces[newRow][newCol].innerColor.equals(Color.white))){
								for (int i = firstCol+1; i<newCol; i++){
									if (!pieces[firstRow][i].chessType.equals("Space")){ System.out.println("IN THE WAY!");
										inTheWay = true;}
								}
								isLegalMove = true;
							}
							if (newRow == firstRow && newCol == firstCol-q && (pieces[newRow][newCol].chessType.equals("Space") || !pieces[newRow][newCol].innerColor.equals(Color.white))){
								for (int i = firstCol-1; i>newCol; i--){
									if (!pieces[firstRow][i].chessType.equals("Space")){ System.out.println("IN THE WAY!");
										inTheWay = true;}
								}
								isLegalMove = true;
							}						}
				if (inTheWay)
					isLegalMove = false;
				else {
					if (firstRow == 0 && firstCol == 0)
						rooksMove[0]=true;
					else if (firstRow == 0 && firstCol == 7)
						rooksMove[1]=true;
					if (firstRow == 7 && firstCol == 0)
						rooksMove[2]=true;
					if (firstRow == 7 && firstCol == 7)
						rooksMove[3]=true;
				}
			}
			if (pieces[firstRow][firstCol].chessType.equals("Knight")){
				if(pieces[firstRow][firstCol].innerColor.equals(Color.black) && turnCounter%2 == 0){	
					if ((newRow == firstRow+2 && newCol == firstCol+1) && (pieces[newRow][newCol].chessType.equals("Space") || pieces[newRow][newCol].innerColor.equals(Color.white)))
						isLegalMove = true;
					if ((newRow == firstRow+2 && newCol == firstCol-1) && (pieces[newRow][newCol].chessType.equals("Space") || pieces[newRow][newCol].innerColor.equals(Color.white)))
						isLegalMove = true;
					if ((newRow == firstRow+1 && newCol == firstCol+2) && (pieces[newRow][newCol].chessType.equals("Space") || pieces[newRow][newCol].innerColor.equals(Color.white)))
						isLegalMove = true;
					if ((newRow == firstRow+1 && newCol == firstCol-2) && (pieces[newRow][newCol].chessType.equals("Space") || pieces[newRow][newCol].innerColor.equals(Color.white)))
						isLegalMove = true;
					if ((newRow == firstRow-1 && newCol == firstCol+2) && (pieces[newRow][newCol].chessType.equals("Space") || pieces[newRow][newCol].innerColor.equals(Color.white)))
						isLegalMove = true;
					if ((newRow == firstRow-1 && newCol == firstCol-2) && (pieces[newRow][newCol].chessType.equals("Space") || pieces[newRow][newCol].innerColor.equals(Color.white)))
						isLegalMove = true;
					if ((newRow == firstRow-2 && newCol == firstCol+1) && (pieces[newRow][newCol].chessType.equals("Space") || pieces[newRow][newCol].innerColor.equals(Color.white)))
						isLegalMove = true;
					if ((newRow == firstRow-2 && newCol == firstCol-1) && (pieces[newRow][newCol].chessType.equals("Space") || pieces[newRow][newCol].innerColor.equals(Color.white)))
						isLegalMove = true;	
				}
				else if(pieces[firstRow][firstCol].innerColor.equals(Color.white))
					for(int c = 0; c<=8; c++){
						if ((newRow == firstRow+2 && newCol == firstCol+1) && (pieces[newRow][newCol].chessType.equals("Space") || pieces[newRow][newCol].innerColor.equals(Color.black)))
							isLegalMove = true;
						if ((newRow == firstRow+2 && newCol == firstCol-1) && (pieces[newRow][newCol].chessType.equals("Space") || pieces[newRow][newCol].innerColor.equals(Color.black)))
							isLegalMove = true;
						if ((newRow == firstRow+1 && newCol == firstCol+2) && (pieces[newRow][newCol].chessType.equals("Space") || pieces[newRow][newCol].innerColor.equals(Color.black)))
							isLegalMove = true;
						if ((newRow == firstRow+1 && newCol == firstCol-2) && (pieces[newRow][newCol].chessType.equals("Space") || pieces[newRow][newCol].innerColor.equals(Color.black)))
							isLegalMove = true;
						if ((newRow == firstRow-1 && newCol == firstCol+2) && (pieces[newRow][newCol].chessType.equals("Space") || pieces[newRow][newCol].innerColor.equals(Color.black)))
							isLegalMove = true;
						if ((newRow == firstRow-1 && newCol == firstCol-2) && (pieces[newRow][newCol].chessType.equals("Space") || pieces[newRow][newCol].innerColor.equals(Color.black)))
							isLegalMove = true;
						if ((newRow == firstRow-2 && newCol == firstCol+1) && (pieces[newRow][newCol].chessType.equals("Space") || pieces[newRow][newCol].innerColor.equals(Color.black)))
							isLegalMove = true;
						if ((newRow == firstRow-2 && newCol == firstCol-1) && (pieces[newRow][newCol].chessType.equals("Space") || pieces[newRow][newCol].innerColor.equals(Color.black)))
							isLegalMove = true;	
					}
			}
			else if (pieces[firstRow][firstCol].chessType.equals("King") ){ // Daniel
				if (pieces[firstRow][firstCol].innerColor.equals(Color.black) && turnCounter % 2 ==0){
				if (newRow == firstRow - 1 && (newCol == firstCol || newCol == firstCol + 1 || newCol == firstCol -1)
						|| newRow == firstRow + 1 && (newCol == firstCol || newCol == firstCol + 1 || newCol == firstCol -1)
						|| newRow == firstRow && (newCol == firstCol +1 || newCol == firstCol -1)){
					if (!pieces[newRow][newCol].chessType.equals("Space") && !pieces[newRow][newCol].innerColor.equals(pieces[firstRow][firstCol].innerColor))
						isLegalMove = true;
					else if (pieces[newRow][newCol].chessType.equals("Space"))
						isLegalMove = true;
				}
				if (isLegalMove)
					kingsMove[0] = true;
				else if (firstRow == 0 && firstCol == 4 && newRow == firstRow && (newCol == firstCol + 2
						|| newCol == firstCol - 2)){
					if (newCol == firstCol + 2 && pieces[firstRow][firstCol + 3].chessType.equals("Rook") && !rooksMove[1] && !kingsMove[0]
							&& pieces[firstRow][firstCol + 1].chessType.equals("Space") && pieces[firstRow][firstCol + 1].chessType.equals("Space")){
						castleKing = true; kingsMove[0] = true; }
					else if (newCol == firstCol - 2 && pieces[firstRow][firstCol - 4].chessType.equals("Rook") && !rooksMove[0] && !kingsMove[0]
							&& pieces[firstRow][firstCol - 1].chessType.equals("Space") && pieces[firstRow][firstCol - 2].chessType.equals("Space")
							&& pieces[firstRow][firstCol - 3].chessType.equals("Space")){
						castleQueen = true; kingsMove[0] = true; }
				}
				}
				else if (pieces[firstRow][firstCol].innerColor.equals(Color.white) && turnCounter % 2 ==1){
				if (newRow == firstRow - 1 && (newCol == firstCol || newCol == firstCol + 1 || newCol == firstCol -1)
						|| newRow == firstRow + 1 && (newCol == firstCol || newCol == firstCol + 1 || newCol == firstCol -1)
						|| newRow == firstRow && (newCol == firstCol +1 || newCol == firstCol -1)){
					if (!pieces[newRow][newCol].chessType.equals("Space") && !pieces[newRow][newCol].innerColor.equals(pieces[firstRow][firstCol].innerColor))
						isLegalMove = true;
					else if (pieces[newRow][newCol].chessType.equals("Space"))
						isLegalMove = true;
				}
				if (isLegalMove)
					kingsMove[1] = true;
				else if (firstRow == 7 && firstCol == 4 && newRow == firstRow && (newCol == firstCol + 2
						|| newCol == firstCol - 2)){
					if (newCol == firstCol + 2 && pieces[firstRow][firstCol + 3].chessType.equals("Rook") && !rooksMove[3] && !kingsMove[1]
							&& pieces[firstRow][firstCol + 1].chessType.equals("Space") && pieces[firstRow][firstCol + 1].chessType.equals("Space")){
						castleKing = true; kingsMove[1] = true; }
					else if (newCol == firstCol - 2 && pieces[firstRow][firstCol - 4].chessType.equals("Rook") && !rooksMove[2] && !kingsMove[1]
							&& pieces[firstRow][firstCol - 1].chessType.equals("Space") && pieces[firstRow][firstCol - 2].chessType.equals("Space")
							&& pieces[firstRow][firstCol - 3].chessType.equals("Space")){
						castleQueen = true; kingsMove[1] = true; }
				}
				}
			}
		if (castleKing){
			pieces [newRow] [newCol] = pieces [firstRow] [firstCol];
			pieces [firstRow] [firstCol] = new ChessPiece ("Space");
			pieces [newRow][newCol-1] = pieces [newRow] [newCol+1];
			pieces [newRow][newCol+1] = new ChessPiece ("Space");
			clicked = false;
		}
		else if (castleQueen){
			pieces [newRow] [newCol] = pieces [firstRow] [firstCol];
			pieces [firstRow] [firstCol] = new ChessPiece ("Space");
			pieces [newRow][newCol+1] = pieces[newRow] [newCol-2];
			pieces [newRow][newCol-2] = new ChessPiece ("Space");
			clicked = false;
		}
		if (isLegalMove || castleKing || castleQueen){
			turnCounter++;
			if (turnCounter % 2 == 1){
				g.setColor(Color.white);
				g.fillRect(0, 0, 750, 773);
				g.setColor(Color.black);
				g.drawRect(0, 22, 748, 750);
				g.drawRect(1, 23, 746, 748);
				g.drawRect(2, 24, 744, 746);
				g.fillRect(12, 36, 724, 724);
				}
			else {
				g.setColor(Color.black);
				g.fillRect(0, 0, 750, 900);
				g.setColor(Color.white);
				g.drawRect(0, 22, 748, 750);
				g.drawRect(1, 23, 746, 748);
				g.drawRect(2, 24, 744, 746);
				g.fillRect(12, 36, 724, 724);
			}
			g.setColor(Color.black);
			g.fillRect(12, 36, 724, 724);
		}
		if (isLegalMove && pieces[firstRow][firstCol].chessType.equals("Pawn") && (newRow == 7 || newRow == 0) && !castleQueen && !castleKing){
		//pawn becoming queen at end of board
			pieces [newRow] [newCol] = new ChessPiece ("Queen",pieces [firstRow] [firstCol].innerColor,pieces [firstRow] [firstCol].outerColor);
			pieces [firstRow] [firstCol] = new ChessPiece ("Space");
			clicked = false;
		}
		else if(isLegalMove && !castleQueen && !castleKing){
			pieces [newRow] [newCol] = pieces [firstRow] [firstCol];
			pieces [firstRow] [firstCol] = new ChessPiece ("Space");
			clicked = false;
		}
		
		}
		boxRow = tempBox; boxCol = tempCol;		
	}

	@Override
	public void mouseClicked(MouseEvent ME) {
		repaint();
		if(clicked){
			oldRow = boxRow;
			oldCol = boxCol;
		}
		System.out.println("old row" + oldRow + "old Col" + oldCol);
		if((int)(ME.getPoint().getY()-38)/90 != boxRow || (int)(ME.getPoint().getX()-14)/90 != boxCol && (int)(ME.getPoint().getX())<733 && (int)(ME.getPoint().getY())<758){
			boxRow = (int)(ME.getPoint().getY()-38)/90;
			boxCol = (int)(ME.getPoint().getX()-14)/90;
		}
		if(turnCounter%2==1)
		if (pieces[boxRow][boxCol].chessType != "Space" && pieces[boxRow][boxCol].innerColor.equals(Color.white) && (int)(ME.getPoint().getX())<733 && (int)(ME.getPoint().getY())<758)
			clicked = true;
		if(turnCounter%2==0)
		if (pieces[boxRow][boxCol].chessType != "Space" && pieces[boxRow][boxCol].innerColor.equals(Color.black) && (int)(ME.getPoint().getX())<733 && (int)(ME.getPoint().getY())<758)
			clicked = true;
		System.out.println("Event Coordinates: (" + boxRow + "," + boxCol + ")");
			System.out.println(clicked);
			clickCount++;
	}
	public boolean pieceSelected () {
		if (pieces[boxRow][boxCol].chessType != "Space" && clicked && pieces[boxRow][boxCol].innerColor.equals(Color.white) && turnCounter % 2 == 1) 
			return true;
		else if (pieces[boxRow][boxCol].chessType != "Space" && clicked && pieces[boxRow][boxCol].innerColor.equals(Color.black) && turnCounter % 2 == 0) 
				return true;
		return false;
	}	/*
	public int[] findOppKing (ChessPiece a) {
		Color oppKing; 
		int [] loc = new int [2];
		if(a.innerColor.equals(Color.black)){
			oppKing = Color.white;
		}
		else {
			oppKing = Color.black;
		}
		for (int i = 0; i<8; i++){
			for (int j = 0; j<8; j++){
				if (pieces[i][j].chessType.equals("King") && pieces[i][j].innerColor.equals(oppKing)){
					System.out.println("row " + i + "col " + j);
					loc[0] = i;
					loc[1] = j;
					return loc; 
				}			
			}
		}
		return null;
		
	}*/
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}