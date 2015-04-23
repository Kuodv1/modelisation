package modelisation.modele;

public class Selection {

	protected int[][] select;
	protected Selection pere;
	
	public Selection(int height, int width) {
		select = new int[height][width];
		pere = null;
	}
	
	public Selection(Selection pere) {
		select = new int[pere.getHauteur()][pere.getLargeur()];
		for(int i = 0; i<pere.getHauteur();i++) {
			this.select[i] = (int[]) pere.getSelect()[i].clone();
		}
		
		this.pere = pere;
		
	}
	
	public int[][] getSelect() {
		return select;
	}
	
	public void setSelect(int[][] select) {
		this.select = select;
	}
	
	public int getHauteur() {
		return select.length;
	}
	
	public int getLargeur() {
		return select[0].length;
	}
	
	public Selection getPere() {
		if(pere!=null) return pere;
		else return this;
	}
	
	public boolean annulerSelectPossible() {
		return pere!=null;
	}
	
	public int getValue(int ligne, int colonne) {
		return select[ligne][colonne];
	}
	
	public void setValue(int ligne, int colonne, int newValue) {
		select[ligne][colonne] = newValue;
	}
	
	public void rotationGauche() {
			int[][] newSelect = new int[getLargeur()][getHauteur()];
			for(int i = 0;i<newSelect.length;i++) {
				for(int j = 0; j<newSelect[i].length;j++) {
					newSelect[i][j] = select[j][newSelect.length-1-i];
				}
			}
	}
	
	public void rotationDroite() {
			int[][] newSelect = new int[getLargeur()][getHauteur()];
			for(int i = 0; i<newSelect.length;i++) {
				for(int j = 0; j<newSelect[i].length;j++) {
					newSelect[i][j] = select[newSelect[i].length-j-1][i];
				}
			}
	}
	
	public void supprimerSelection(int x, int y) {
		int aSuppr = select[y][x];
		int xDep = x;
		int yDep = y;
		while(possibleHaut(x,y)&&select[y-1][x]==aSuppr) {
			select[y-1][x]=0;
			y--;
			
			while(possibleGauche(x,y)&&select[y][x-1]==aSuppr) {
				select[y][x-1]=0;
				x--;
			}
			x = xDep;
			while(possibleDroite(x,y)&&select[y][x+1]==aSuppr) {
				select[y][x+1]=0;
				x++;
			}
			x = xDep;
		}
		y = yDep;
		while(possibleBas(x,y)&&select[y+1][x]==aSuppr) {
			select[y+1][x]=0;
			y++;
			while(possibleGauche(x,y)&&select[y][x-1]==aSuppr) {
				select[y][x-1]=0;
				x--;
			}
			x = xDep;
			while(possibleDroite(x,y)&&select[y][x+1]==aSuppr) {
				select[y][x+1]=0;
				x++;
			}
			x = xDep;
		}
		y = yDep;
		select[yDep][xDep]=0;
		while(possibleGauche(x,y)&&select[y][x-1]==aSuppr) {
			select[y][x-1]=0;
			x--;
		}
		x = xDep;
		while(possibleDroite(x,y)&&select[y][x+1]==aSuppr) {
			select[y][x+1]=0;
			x++;
		}
		x = xDep;
	}
	
	public boolean possibleGauche(int x, int y) {
		return x>0;
	}
	
	public boolean possibleBasGauche(int x, int y) {
		return y+1<getHauteur()&& x>0;
	}
	
	public boolean possibleBas(int x, int y) {
		return y+1<getHauteur();
	}
	
	public boolean possibleBasDroite(int x, int y) {
		return y+1<getHauteur()&& x+1<getLargeur();
	}
	
	public boolean possibleDroite(int x, int y) {
		return x+1<getLargeur();
	}
	
	public boolean possibleHautDroite(int x, int y) {
		return y>0&&x+1<getLargeur();
	}
	
	public boolean possibleHaut(int x, int y) {
		return y>0;
	}
	
	public boolean possibleHautGauche(int x, int y) {
		return y>0 && x>0;
	}
}
