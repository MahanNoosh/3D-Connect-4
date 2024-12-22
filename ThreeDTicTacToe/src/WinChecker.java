/**
 * WinChecker class is responsible for checking the game's winning conditions.
 */
public class WinChecker {
    
    private int gridSize;
    private Cube[][][] cubes;
    private int occupiedCubes=0;
    private Cube[] winerCubes = null;
    private boolean[][][] winerCubesHelper;
    
    /**
     * Constructor for WinChecker.
     * @param gridSize Size of the game grid
     * @param cubes 3D array representing the game cubes
     */
    public WinChecker(int gridSize, Cube[][][] cubes) {
        this.gridSize = gridSize;
        this.cubes = cubes;
    }
    
    /**
     * Checks if there is a winner or a tie in the game.
     * @return 1 if there is a winner, 0 for a tie, -1 if the game is still ongoing
     */
    public int checkWin() {
        winerCubesHelper = new boolean[gridSize][gridSize][gridSize];
        winerCubes = new Cube[gridSize];
        boolean state = false;
        for(int i = 0; i < gridSize; i++) {
            for(int j = 0; j < gridSize; j++) {
                state = state || 
                        zLineInRow(i, j, gridSize-1) || 
                        yLineInRow(i, gridSize-1, j) || 
                        xLineInRow(gridSize-1, i, j);
            }
            state = state || 
                    frontDiagonal(gridSize-1,gridSize-1, i) ||
                    sideDiagonal(i ,gridSize-1, gridSize-1) ||
                    topDiagonal(gridSize-1, i, gridSize-1) ||
                    
                    rFrontDiagonal(gridSize-1,0 , i) ||    // Reverse
                    rSideDiagonal(i ,gridSize-1, 0) ||
                    rTopDiagonal(0, i, gridSize-1);
        }
        state = state ||
                diagonalOne(gridSize-1, gridSize-1, gridSize-1)    ||
                diagonalTwo(0, gridSize-1, gridSize-1)    ||
                diagonalThree(gridSize-1, 0, gridSize-1)    ||
                diagonalFour(gridSize-1, gridSize-1, 0);
        
        if(state) return 1;
        else {
            for (int x = 0; x < gridSize; x++) {
                for (int y = 0; y < gridSize; y++) {
                    for (int z = 0; z < gridSize; z++) {
                        if(cubes[x][y][z].captured()) occupiedCubes++;
                    }
                }
            }
            if(occupiedCubes == gridSize * gridSize * gridSize) return 0;
            else return -1;
        }
    }
    
    /**
     * Returns an array of winning cubes if there is a winner.
     * @return Array of winning cubes
     */
    public Cube[] getWinerCubes() {
        if(checkWin() == 1) {
            int i = 0;
            for (int x = 0; x < gridSize; x++) {
                for (int y = 0; y < gridSize; y++) {
                    for (int z = 0; z < gridSize; z++) {
                        if(winerCubesHelper[x][y][z]) {
                            winerCubes[i] = cubes[x][y][z];
                            i++;
                        }
                    }
                }
            }
        }
        return winerCubes;
    }
	

	private boolean zLineInRow(int x, int y, int z){
		
		if(z==0) return true;

		if(cubes[x][y][z].captured() && cubes[x][y][z].getPlayer() == cubes[x][y][z-1].getPlayer()) {
			winerCubesHelper[x][y][z] = true; 
			winerCubesHelper[x][y][z-1] = true; 
			return zLineInRow(x, y, z-1);
		}
		winerCubesHelper = new boolean[gridSize][gridSize][gridSize];
		return false;
	}

	private boolean yLineInRow(int x, int y, int z) {
		
		if(y==0) return true;
		
		if(cubes[x][y][z].captured() && cubes[x][y][z].getPlayer() == cubes[x][y-1][z].getPlayer()) {
			winerCubesHelper[x][y][z] = true;
			winerCubesHelper[x][y-1][z] = true; 
			return yLineInRow(x, y-1, z);
		}
		winerCubesHelper = new boolean[gridSize][gridSize][gridSize];
		return false;
	}

	private boolean xLineInRow(int x, int y, int z) {
		
		if(x==0) return true;
			
		if(cubes[x][y][z].captured() && cubes[x][y][z].getPlayer() == cubes[x-1][y][z].getPlayer()) {
			winerCubesHelper[x][y][z] = true; 
			winerCubesHelper[x-1][y][z] = true; 
			return xLineInRow(x-1, y, z);
		}
		winerCubesHelper = new boolean[gridSize][gridSize][gridSize];
		return false;
	}

	private boolean frontDiagonal(int x, int y, int z) {
		
		if(x==0 && y==0) return true;
			
		if(cubes[x][y][z].captured() && cubes[x][y][z].getPlayer() == cubes[x-1][y-1][z].getPlayer()) {
			winerCubesHelper[x][y][z] = true; 
			winerCubesHelper[x-1][y-1][z] = true; 
			return frontDiagonal(x-1, y-1, z);
		}
		winerCubesHelper = new boolean[gridSize][gridSize][gridSize]; 
		return false;
	}
	private boolean sideDiagonal(int x, int y, int z) {
		
		if(z==0 && y==0) return true;
			
		if(cubes[x][y][z].captured() && cubes[x][y][z].getPlayer() == cubes[x][y-1][z-1].getPlayer()) {
			winerCubesHelper[x][y][z] = true; 
			winerCubesHelper[x][y-1][z-1] = true; 
			return sideDiagonal(x, y-1, z-1);
		}
		winerCubesHelper = new boolean[gridSize][gridSize][gridSize]; 
		return false;
	}
	private boolean topDiagonal(int x, int y, int z) {
		
		if(x==0 && z==0) return true;
			
		if(cubes[x][y][z].captured() && cubes[x][y][z].getPlayer() == cubes[x-1][y][z-1].getPlayer()) {
			winerCubesHelper[x][y][z] = true; 
			winerCubesHelper[x-1][y][z-1] = true; 
			return topDiagonal(x-1, y, z-1);
		}
		winerCubesHelper = new boolean[gridSize][gridSize][gridSize];
		return false;
	}

	private boolean rFrontDiagonal(int x, int y, int z) {
		
		if(x==0 && y==gridSize-1) return true;
			
		if(cubes[x][y][z].captured() && cubes[x][y][z].getPlayer() == cubes[x-1][y+1][z].getPlayer()) {
			winerCubesHelper[x][y][z] = true; 
			winerCubesHelper[x-1][y+1][z] = true; 
			return rFrontDiagonal(x-1, y+1, z);
		}
		winerCubesHelper = new boolean[gridSize][gridSize][gridSize];
		return false;
	}
	private boolean rSideDiagonal(int x, int y, int z) {
		
		if(z==gridSize-1 && y==0) return true;
			
		if(cubes[x][y][z].captured() && cubes[x][y][z].getPlayer() == cubes[x][y-1][z+1].getPlayer()) {
			winerCubesHelper[x][y][z] = true; 
			winerCubesHelper[x][y-1][z+1] = true; 
			return rSideDiagonal(x, y-1, z+1);
		}
		winerCubesHelper = new boolean[gridSize][gridSize][gridSize]; 
		return false;
	}
	private boolean rTopDiagonal(int x, int y, int z) {
		
		if(x==gridSize-1 && z==0) return true;
			
		if(cubes[x][y][z].captured() && cubes[x][y][z].getPlayer() == cubes[x+1][y][z-1].getPlayer()) {
			winerCubesHelper[x][y][z] = true;
			winerCubesHelper[x+1][y][z-1] = true; 
			return rTopDiagonal(x+1, y, z-1);
		}
		winerCubesHelper = new boolean[gridSize][gridSize][gridSize];
		return false;
	}

	private boolean diagonalOne(int x, int y, int z){
		
		if(x==0 && y==0 && z==0) return true;
			
		if(cubes[x][y][z].captured() && cubes[x][y][z].getPlayer() == cubes[x-1][y-1][z-1].getPlayer()) {
			winerCubesHelper[x][y][z] = true; 
			winerCubesHelper[x-1][y-1][z-1] = true; 
			return diagonalOne(x-1, y-1, z-1);
		}
		winerCubesHelper = new boolean[gridSize][gridSize][gridSize];
		return false;
	}

	private boolean diagonalTwo(int x, int y, int z){
		
		if(x==gridSize-1 && y==0 && z==0) return true;
			
		if(cubes[x][y][z].captured() && cubes[x][y][z].getPlayer() == cubes[x+1][y-1][z-1].getPlayer()) {
			winerCubesHelper[x][y][z] = true; 
			winerCubesHelper[x+1][y-1][z-1] = true; 
			return diagonalTwo(x+1, y-1, z-1);
		}
		winerCubesHelper = new boolean[gridSize][gridSize][gridSize]; 
		return false;
	}

	private boolean diagonalThree(int x, int y, int z){
		
		if(x==0 && y==gridSize-1 && z==0) return true;

		if(cubes[x][y][z].captured() && cubes[x][y][z].getPlayer() == cubes[x-1][y+1][z-1].getPlayer()) {
			winerCubesHelper[x][y][z] = true; 
			winerCubesHelper[x-1][y+1][z-1] = true; 
			return diagonalThree(x-1, y+1, z-1);
		}
		winerCubesHelper = new boolean[gridSize][gridSize][gridSize];
		return false;
	}

	private boolean diagonalFour(int x, int y, int z){
		
		if(x==0 && y==0 && z==gridSize-1) return true;

		if(cubes[x][y][z].captured() && cubes[x][y][z].getPlayer() == cubes[x-1][y-1][z+1].getPlayer()) {
			winerCubesHelper[x][y][z] = true; 
			winerCubesHelper[x-1][y-1][z+1] = true; 
			return diagonalFour(x-1, y-1, z+1);
		}
		winerCubesHelper = new boolean[gridSize][gridSize][gridSize];
		return false;
	}


}


