/**
 * The ThreeDAI class represents an AI player for the 4x4x4 Tic Tac Toe game.
 * It evaluates the game state and makes strategic moves to compete against the human player.
 */
public class ThreeDAI {

    private int gridSize;
    private Cube[][][] cubes;
    private WinChecker winChecker;

    /**
     * Constructor for the ThreeDAI class.
     *
     * @param gridSize The size of the game grid.
     * @param cubes    The 3D array representing the game cubes.
     */
    public ThreeDAI(int gridSize, Cube[][][] cubes) {
        this.gridSize = gridSize;
        this.cubes = cubes;
    }

    /**
     * Gets the best move for the AI based on the evaluation of the current game state.
     *
     * @return The Cube representing the best move for the AI.
     */
    public Cube getTheBestMove() {
        int bestValue = -1_000_000_000;
        Cube bestMove = null;

        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                for (int z = 0; z < gridSize; z++) {
                    if (!cubes[x][y][z].captured()) {
                        if (bestValue < cubes[x][y][z].getValue()) {
                            bestValue = cubes[x][y][z].getValue();
                            bestMove = cubes[x][y][z];
                        }
                    }
                }
            }
        }

        return bestMove;
    }
    
    private void setCubesValue() {
    	for (int x = 0; x < gridSize; x++) {
        	for (int y = 0; y < gridSize; y++) {
            	for (int z = 0; z < gridSize; z++) {
            		// have to do only if cube xyz is not captured
            		if(!cubes[x][y][z].captured()) {
            			int value = leadsToWin(x, y, z) + specialCube(x, y, z) + inRow(x, y, z);
            			cubes[x][y][z].setValue(value);
            		}
            	}
         	}
    	}
    }
    
    private int leadsToWin(int x, int y, int z) {
    	winChecker = new WinChecker(gridSize, cubes);
    	
   		cubes[x][y][z].clicked(1);
  		if(winChecker.checkWin() == 1) {
   			cubes[x][y][z].clicked(0);
   			return 1000;
    	}
    		
    	cubes[x][y][z].clicked(2);
   		if(winChecker.checkWin() == 1) {
   			cubes[x][y][z].clicked(0);
   			return 1000000;
   		}    		
    	cubes[x][y][z].clicked(0);
   		return 0; 	
    }
    
    private int specialCube(int x, int y, int z) {
    	if((x == 0||x == gridSize -1) && (y == 0||y == gridSize -1) && (z == 0||z == gridSize -1)) { //corner
    		return 2;
    	}
    	return 0;
    }
    
    private int inRow(int x, int y, int z) {
    	int value = 0;
    	
    	cubes[x][y][z].clicked(2);
    	final int AI_CUBES_IN_ROW_WORTH = 4;
    	
    	value = zLineInRow(x, y, z, AI_CUBES_IN_ROW_WORTH) + yLineInRow(x, y, z, AI_CUBES_IN_ROW_WORTH) + 
    			xLineInRow(x, y, z, AI_CUBES_IN_ROW_WORTH) + frontDiagonal(x, y, z, AI_CUBES_IN_ROW_WORTH) + 
    			topDiagonal(x, y, z, AI_CUBES_IN_ROW_WORTH) + sideDiagonal(x, y, z, AI_CUBES_IN_ROW_WORTH) + 
    			rFrontDiagonal(x, y, z, AI_CUBES_IN_ROW_WORTH) + rTopDiagonal(x, y, z, AI_CUBES_IN_ROW_WORTH) + 
    			rSideDiagonal(x, y, z, AI_CUBES_IN_ROW_WORTH) + diagonalOne(x, y, z, AI_CUBES_IN_ROW_WORTH) +
    			diagonalTwo(x, y, z, AI_CUBES_IN_ROW_WORTH) + diagonalThree(x, y, z, AI_CUBES_IN_ROW_WORTH) +
    			diagonalFour(x, y, z, AI_CUBES_IN_ROW_WORTH);
    	
    	cubes[x][y][z].clicked(1);
    	final int OPPONENT_CUBES_IN_ROW_WORTH = 10;
    		
    	value += zLineInRow(x, y, z, OPPONENT_CUBES_IN_ROW_WORTH) + yLineInRow(x, y, z, OPPONENT_CUBES_IN_ROW_WORTH) + 
    			xLineInRow(x, y, z, OPPONENT_CUBES_IN_ROW_WORTH) + frontDiagonal(x, y, z, OPPONENT_CUBES_IN_ROW_WORTH) + 
    			topDiagonal(x, y, z, OPPONENT_CUBES_IN_ROW_WORTH) + sideDiagonal(x, y, z, OPPONENT_CUBES_IN_ROW_WORTH) + 
    			rFrontDiagonal(x, y, z, OPPONENT_CUBES_IN_ROW_WORTH) + rTopDiagonal(x, y, z, OPPONENT_CUBES_IN_ROW_WORTH) + 
    			rSideDiagonal(x, y, z, OPPONENT_CUBES_IN_ROW_WORTH) + diagonalOne(x, y, z, OPPONENT_CUBES_IN_ROW_WORTH) +
    			diagonalTwo(x, y, z, OPPONENT_CUBES_IN_ROW_WORTH) + diagonalThree(x, y, z, OPPONENT_CUBES_IN_ROW_WORTH) +
    			diagonalFour(x, y, z, OPPONENT_CUBES_IN_ROW_WORTH);
    	cubes[x][y][z].clicked(0);
    	return value;
    }
    
    private int zLineInRow(int x, int y, int z, int worth){
    	int value = 0;
    	for(int i = 0; i < gridSize; i++) {
    		if(i != z && cubes[x][y][i].captured()) {
    			if(cubes[x][y][i].getPlayer() == cubes[x][y][z].getPlayer()) value+=worth;
    			else return 0;
    		}
    	}
    	return value;
	}
    
    private int yLineInRow(int x, int y, int z, int worth){
    	int value = 0;
    	for(int i = 0; i < gridSize; i++) {
    		if(i != y && cubes[x][i][z].captured()) {
    			if(cubes[x][i][z].getPlayer() == cubes[x][y][z].getPlayer()) value+=worth;
    			else return 0;
    		}
    	}
    	return value;
    }
    
    private int xLineInRow(int x, int y, int z, int worth){
    	int value = 0;
    	for(int i = 0; i < gridSize; i++) {
    		if(i != x && cubes[i][y][z].captured()) {
    			if(cubes[i][y][z].getPlayer() == cubes[x][y][z].getPlayer()) value+=worth;
    			else return 0;
    		}
    	}
    	return value;
    }
    
    private int frontDiagonal(int x, int y, int z, int worth){
    	int value = 0;
    	boolean isDiagonal = false;
    	for(int i = 0; i < gridSize; i++) {
    		if(cubes[i][i][z] == cubes[x][y][z]) isDiagonal = true;
    	}
    	if(isDiagonal) {
	    	for(int i = 0; i < gridSize; i++) {
	    		if(isDiagonal && cubes[i][i][z].captured() && cubes[i][i][z] != cubes[x][y][z]) {
		    		if(cubes[i][i][z].getPlayer() == cubes[x][y][z].getPlayer()) value+=worth;
		    		else {
		    			value = 0;
		    			break;
		    		}
	    		}
	    	}
    	}
    	return value;	
    }
    
    private int topDiagonal(int x, int y, int z, int worth){
    	int value = 0;
    	boolean isDiagonal = false;
    	for(int i = 0; i < gridSize; i++) {
    		if(cubes[i][y][i] == cubes[x][y][z]) isDiagonal = true;
    	}
    	if(isDiagonal) {
	    	for(int i = 0; i < gridSize; i++) {
	    		if(cubes[i][y][i].captured() && cubes[i][y][i] != cubes[x][y][z]) {
	    			if(cubes[i][y][i].getPlayer() == cubes[x][y][z].getPlayer()) value+=worth;
		    		else {
		    			value = 0;
		    			break;
		    		}
	    		}
	    	}
    	}
    	return value;	
    }
    
    private int sideDiagonal(int x, int y, int z, int worth){
    	int value = 0;
    	boolean isDiagonal = false;
    	for(int i = 0; i < gridSize; i++) {
    		if(cubes[x][i][i] == cubes[x][y][z]) isDiagonal = true;
    	}
    	if(isDiagonal) {
	    	for(int i = 0; i < gridSize; i++) {
	    		if(cubes[x][i][i].captured() && cubes[x][i][i] != cubes[x][y][z]) {
	    			if(cubes[x][i][i].getPlayer() == cubes[x][y][z].getPlayer()) value+=worth;
		    		else {
		    			value = 0;
		    			break;
		    		}
	    		}
	    	}
    	}
    	return value;
    }
    
    private int rFrontDiagonal(int x, int y, int z, int worth){
    	int value = 0;
    	boolean isDiagonal = false;
    	for(int i = 0; i < gridSize; i++) {
    		if(cubes[gridSize-1-i][gridSize-1-i][z] == cubes[x][y][z]) isDiagonal = true;
    	}
    	if(isDiagonal) {
	    	for(int i = 0; i < gridSize; i++) {
	    		if(cubes[gridSize-1-i][gridSize-1-i][z].captured() && cubes[gridSize-1-i][gridSize-1-i][z] != cubes[x][y][z]) {
	    			if(cubes[gridSize-1-i][gridSize-1-i][z].getPlayer() == cubes[x][y][z].getPlayer()) value+=worth;
		    		else {
		    			value = 0;
		    			break;
		    		}
	    		}
	    	}
    	}
    	return value;
    }
    
    private int rTopDiagonal(int x, int y, int z, int worth){
    	int value = 0;
    	boolean isDiagonal = false;
    	for(int i = 0; i < gridSize; i++) {
    		if(cubes[gridSize-1-i][y][gridSize-1-i] == cubes[x][y][z]) isDiagonal = true;
    	}
    	if(isDiagonal) {
	    	for(int i = 0; i < gridSize; i++) {
	    		if(cubes[gridSize-1-i][y][gridSize-1-i].captured() && cubes[gridSize-1-i][y][gridSize-1-i] != cubes[x][y][z]) {
	    			if(cubes[gridSize-1-i][y][gridSize-1-i].getPlayer() == cubes[x][y][z].getPlayer()) value+=worth;
		    		else {
		    			value = 0;
		    			break;
		    		}
	    		}
	    	}
    	}
    	return value;
    }
    
    private int rSideDiagonal(int x, int y, int z, int worth){    	
    	int value = 0;
    	boolean isDiagonal = false;
    	for(int i = 0; i < gridSize; i++) {
    		if(cubes[x][gridSize-1-i][gridSize-1-i] == cubes[x][y][z]) isDiagonal = true;
    	}
    	if(isDiagonal) {
	    	for(int i = 0; i < gridSize; i++) {
	    		if(cubes[x][gridSize-1-i][gridSize-1-i].captured() && cubes[x][gridSize-1-i][gridSize-1-i] != cubes[x][y][z]) {
	    			if(cubes[x][gridSize-1-i][gridSize-1-i].getPlayer() == cubes[x][y][z].getPlayer()) value+=worth;
		    		else {
		    			value = 0;
		    			break;
		    		}
	    		}
	    	}
    	}
    	return value;
    }
    
    private int diagonalOne (int x, int y, int z, int worth){ 
    	int value = 0;
    	boolean isDiagonal = false;
    	for(int i = 0; i < gridSize; i++) {
    		if(cubes[i][i][i] == cubes[x][y][z]) isDiagonal = true;
    	}
    	if(isDiagonal) {
	    	for(int i = 0; i < gridSize; i++) {
	    		if(cubes[i][i][i].captured() && cubes[i][i][i] != cubes[x][y][z]) {
	    			if(cubes[i][i][i].getPlayer() == cubes[x][y][z].getPlayer()) value+=worth;
		    		else {
		    			value = 0;
		    			break;
		    		}
	    		}
	    	}
    	}
    	return value;
    }
    
    private int diagonalTwo (int x, int y, int z, int worth){ 
    	int value = 0;
    	boolean isDiagonal = false;
    	for(int i = 0; i < gridSize; i++) {
    		if(cubes[gridSize-1-i][i][i] == cubes[x][y][z]) isDiagonal = true;
    	}
    	if(isDiagonal) {
	    	for(int i = 0; i < gridSize; i++) {
	    		if(cubes[gridSize-1-i][i][i].captured() && cubes[gridSize-1-i][i][i] != cubes[x][y][z]) {
	    			if(cubes[gridSize-1-i][i][i].getPlayer() == cubes[x][y][z].getPlayer()) value+=worth;
		    		else {
		    			value = 0;
		    			break;
		    		}
	    		}
	    	}
    	}
    	return value;
    }
    
    private int diagonalThree (int x, int y, int z, int worth){ 
    	int value = 0;
    	boolean isDiagonal = false;
    	for(int i = 0; i < gridSize; i++) {
    		if(cubes[i][gridSize-1-i][i] == cubes[x][y][z]) isDiagonal = true;
    	}
    	if(isDiagonal) {
	    	for(int i = 0; i < gridSize; i++) {
	    		if(cubes[i][gridSize-1-i][i].captured() && cubes[i][gridSize-1-i][i] != cubes[x][y][z]) {
	    			if(cubes[i][gridSize-1-i][i].getPlayer() == cubes[x][y][z].getPlayer()) value+=worth;
		    		else {
		    			value = 0;
		    			break;
		    		}
	    		}
	    	}
    	}
    	return value;
    }
    
    private int diagonalFour (int x, int y, int z, int worth){ 
    	int value = 0;
    	boolean isDiagonal = false;
    	for(int i = 0; i < gridSize; i++) {
    		if(cubes[i][i][gridSize-1-i] == cubes[x][y][z]) isDiagonal = true;
    	}
    	if(isDiagonal) {
	    	for(int i = 0; i < gridSize; i++) {
	    		if(cubes[i][i][gridSize-1-i].captured() && cubes[i][i][gridSize-1-i] != cubes[x][y][z]) {
	    			if(cubes[i][i][gridSize-1-i].getPlayer() == cubes[x][y][z].getPlayer()) value+=worth;
		    		else {
		    			value = 0;
		    			break;
		    		}
	    		}
	    	}
    	}
    	return value;
    }
    
    /**
     * Takes the AI's action, updating the game state based on the evaluated moves.
     */
    public void takeAction() {
	  setCubesValue();
  	  winChecker = new WinChecker(gridSize, cubes);
  		if(ThreeDTicTacToe.turn == ThreeDTicTacToe.Player.AI && winChecker.checkWin() == -1) {
  			getTheBestMove().clicked(2);
  			ThreeDTicTacToe.turn = ThreeDTicTacToe.Player.ONE;
  		}
    }
}
  















