package modelisation.modele;

public class Selection {

	protected int[][] select;
	protected Selection pere;
	
	/**
	 * Constucteur Selection
	 */
	public Selection(int height, int width) {
		select = new int[height][width];
		pere = null;
	}
	
	/**
	 * Constucteur Selection
	 * @param pere
	 */
	public Selection(Selection pere) {
		select = new int[pere.getHauteur()][pere.getLargeur()];
		for(int i = 0; i<pere.getHauteur();i++) {
			this.select[i] = (int[]) pere.getSelect()[i].clone();
		}
		
		this.pere = pere;
		
	}
	
	/**
	 * Retourne le tableau select
	 * @return
	 */
	public int[][] getSelect() {
		return select;
	}
	
	/**
	 * Initialise la variable select
	 * @param select
	 */
	public void setSelect(int[][] select) {
		this.select = select;
	}
	
	/**
	 * Retourne la hauteur du tableau select
	 * @return
	 */
	public int getHauteur() {
		return select.length;
	}
	
	/**
	 * Retourne la largeur du tableau select
	 * @return
	 */
	public int getLargeur() {
		return select[0].length;
	}
	
	/**
	 * Retourne la selection principale que l'utilisateur à selectionné
	 * @return
	 */
	public Selection getPere() {
		if(pere!=null) return pere;
		else return this;
	}
	
	/**
	 * Retourne un booleen pour annuler la selection de l'utilisateur
	 * @return
	 */
	public boolean annulerSelectPossible() {
		return pere!=null;
	}
	
	/**
	 * Retourne la valeur d'une case du tableau select
	 * @param ligne
	 * @param colonne
	 * @return
	 */
	public int getValue(int ligne, int colonne) {
		return select[ligne][colonne];
	}
	
	/**
	 * Initialise une case du tableau select
	 * @param ligne
	 * @param colonne
	 * @param newValue
	 */
	public void setValue(int ligne, int colonne, int newValue) {
		select[ligne][colonne] = newValue;
	}
	
	/**
	 * Permet la rotation gauche par l'intermediaire d'un nouveau tableau
	 */
	public void rotationGauche() {
			int[][] newSelect = new int[getLargeur()][getHauteur()];
			
			for(int i = 0;i<newSelect.length;i++) {
				for(int j = 0; j<newSelect[i].length;j++) {
					newSelect[i][j] = select[j][newSelect.length-1-i];
				}
			}
			
			this.select = newSelect;
	}
	
	/**
	 * Permet la rotation droite par l'intermediaire d'un nouveau tableau
	 */
	public void rotationDroite() {
			int[][] newSelect = new int[getLargeur()][getHauteur()];
			for(int i = 0; i<newSelect.length;i++) {
				for(int j = 0; j<newSelect[i].length;j++) {
					newSelect[i][j] = select[newSelect[i].length-j-1][i];
				}
			}
			this.select = newSelect;
	}
	
	/**
	 * Effectue une supression de pixel sur la zone selectionnée par l'utilisateur
	 * @param x
	 * @param y
	 */
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
	
	/**
	 * Retourne un booleen selon la valeur de x
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean possibleGauche(int x, int y) {
		return x>0;
	}
	
	/**
	 * Retourne un booleen selon la valeur de y
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean possibleBasGauche(int x, int y) {
		return y+1<getHauteur()&& x>0;
	}
	
	/**
	 * Retourne un booleen selon la valeur de y et de la hauteur du tableau select
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean possibleBas(int x, int y) {
		return y+1<getHauteur();
	}
	
	 /**
	 * Retourne un booleen selon la valeur de y et x et de la hauteur et de la largeur du tableau select
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean possibleBasDroite(int x, int y) {
		return y+1<getHauteur()&& x+1<getLargeur();
	}
	
	/**
	 * Retourne un booleen selon la valeur de x et de la largeur du tableau select
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean possibleDroite(int x, int y) {
		return x+1<getLargeur();
	}
	
	/**
	 * Retourne un booleen selon la valeur de y et x avec la largeur du tableau select
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean possibleHautDroite(int x, int y) {
		return y>0&&x+1<getLargeur();
	}
	
	/**
	 * Retourne un booleen selon la valeur de y
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean possibleHaut(int x, int y) {
		return y>0;
	}
	
	/**
	 * Retourne un booleen selon la valeur de y et x 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean possibleHautGauche(int x, int y) {
		return y>0 && x>0;
	}
}
