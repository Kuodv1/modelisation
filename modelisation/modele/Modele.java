package modelisation.modele;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JOptionPane;

public class Modele extends Observable {

	protected SeamCarving sc;
	protected String lienImg;
	int [][] img;
	int [][] interest;
	int[][] conservSuppr;
	Graph g;
	int choix;
	
	/**
	 * Constructeur de Modele
	 */
	public Modele() {
		sc = new SeamCarving();
		lienImg = "";
		img = null;
		choix = 0;
	}
	
	/**
	 * Retourne le lien de l'image lienImg
	 * @return
	 */
	public String getLink() {
		return lienImg;
	}
	
	/**
	 * Ecrit dans un fichier pgm de la nouvelle image
	 * @param image
	 */
	public void writeImage(int[][] image) {
		System.out.println("Ecriture de la nouvelle image...");
		StringBuilder newName = new StringBuilder(lienImg.substring(0,lienImg.lastIndexOf(File.separator)+1));
		newName.append("resize_");
		newName.append(lienImg.subSequence(lienImg.lastIndexOf(File.separator)+1, lienImg.length()));
		if(lienImg.endsWith(".pgm")) sc.writepgm(image,  newName.toString());
		else if (lienImg.endsWith(".ppm")) sc.writeppm(image, newName.toString());
	}
	
	/**
	 * Affiche les lignes du fichier filePath
	 * @param filePath
	 */
	public void readPgm(String filePath) {
		 img = sc.readpgm(filePath);
		 lienImg = filePath;
		 
		 conservSuppr = new int[img.length][img[0].length];
		 
		 //generationGraph();
		 System.out.println("Image chargé. Réduction possible.");
		 update();
	}
	
	public void readPpm(String filePath) {
		img = sc.readppm(filePath);
		lienImg = filePath;
		 System.out.println("Image chargé. Réduction possible.");
		 
		 conservSuppr = new int[img.length][img[0].length];
		 update();
	}
	
	/**
	 * Conception du tableau des int�rets, g�n�ration du graphe du tableau des interets et �laboration du graphe r�siduel
	 */
	public void generationGraph() {
		interest = interest(img);
		g = toGraph(interest);
		defaultFlot();
		getGraphResidu();
	}
	
	/**
	 * Conception du tableau des inter�ts � partir du tableau image
	 * @param image
	 * @return
	 */
	   public int[][] interest(int[][] image)
	   {		   
		 //initialisation tableau même taille
		   int i,j;
		   int[][] imageInt = new int[image.length][];
		   for(i = 0; i<image.length;i++) {
			   imageInt[i] = new int[image[i].length];
		   }
		   
		   for(i=0;i<image.length;i++)
		   {
			   imageInt[i][0] = Math.abs((image[i][0]-image[i][1]));
			   for(j=1;j<image[i].length-1;j++)
			   {
				   imageInt[i][j] = Math.abs(image[i][j]-(int)((image[i][j-1]+image[i][j+1])/2));
			   }
			   imageInt[i][image[i].length-1] = Math.abs((image[i][image[i].length-2]-image[i][image[i].length-1]));
		   }
		   return imageInt;
	   }
	
	@SuppressWarnings("static-access")
	/**
	 * Conception du graphe
	 * @param itr
	 * @return
	 */
	public Graph toGraph(int[][] itr) {
		Graph graph = new Graph(itr.length*itr[0].length+2);
		graph.buildGraph(itr);
		return graph;
	}
	
	/**
	 * Affichage de la ligne line du graphe g
	 * @param line
	 */
	public void getLine(int line) {
		for(Edge e : g.getLine(line)) {
			System.out.println("From "+e.getFrom()+" -> "+e.getTo()+" : "+e.getUsed()+"/"+e.getCapacity());
		}
	}
	
	/**
	 * Conception de graphe r�siduel
	 */
	public void getGraphResidu() {
		g.getGraphResidu();
	}
	
	public void reduction(int reducLarge, int reducHaut) {
		System.out.println("Debut du traitement de reduction.");
		if(reducLarge>0)reductionLargeur(reducLarge);
		if(reducHaut>0) reductionHauteur(reducHaut);
		
		writeImage(img);
	}
	
	/**
	 * Elaboration de la r�duction de l'image et �criture de la nouvelle image
	 * @param reduc
	 */
	public void reductionLargeur(int reduc) {
		if(img!=null) {
			if(reduc> img[0].length-2) {
				reduc = img[0].length-2;
			}
			for(int depix = 0; depix<reduc; depix++) {
				generationGraph();
				ArrayList<Integer> coupe = g.coupeMin();
				if(img[0].length>2) {
				img = removeColonne(coupe);
				System.out.println("Traitement : "+((depix*100)/reduc)+"%");
				}
				
			}
		}
	}
	
	
	public void reductionHauteur(int reduc) {
		rotationGauche();
		reductionLargeur(reduc);
		rotationDroite();
	}
	
	public void rotationGauche() {
		if(img!=null) {
			int[][] newImage = new int[img[0].length][img.length];
			StringBuilder sb = new StringBuilder();
			for(int i = 0;i<newImage.length;i++) {
				for(int j = 0; j<newImage[i].length;j++) {
					newImage[i][j] = img[j][newImage.length-1-i];
					sb.append(newImage[i][j]+" ");
				}
				sb.append("\n");
			}
			img = newImage;
		}
	}
	
	public void rotationDroite() {
		if(img!=null) {
			int[][] newImage = new int[img[0].length][img.length];
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i<newImage.length;i++) {
				for(int j = 0; j<newImage[i].length;j++) {
					newImage[i][j] = img[newImage[i].length-j-1][i];
					sb.append(newImage[i][j]+" ");
				}
				sb.append("\n");
			}
			img = newImage;
		}
	}
	
	/**
	 * Supprime une colonne
	 * @param coupe
	 * @return
	 */
	public int[][] removeColonne(ArrayList<Integer> coupe) {
		int [][] newImage = new int[img.length][img[0].length-1];
		for(int i=0;i<newImage.length-1;i++) {
			for(int j = 0; j<newImage[i].length;j++) {
				if((coupe.get(i)/coupe.size())>j) {
					newImage[i][j]=img[i][j];
				} else {
					newImage[i][j]=img[i][j+1];
				}
			}
		}
		int ligne = newImage.length-1;
		for(int j= 0; j<newImage[ligne].length;j++) {
			if(coupe.get(coupe.size()-1)/coupe.size()-1>j){
				newImage[ligne][j]=img[ligne][j];
			} else {
				newImage[ligne][j] = img[ligne][j+1];
			}
		}
		
		return newImage;
	}
	
	/**
	 * Calcul du flot par d�faut
	 */
	public void defaultFlot() {
		for(int i = 0;i<interest.length;i++) {
			int min = Integer.MAX_VALUE;
			for(int j = 0;j<interest[i].length;j++) {
				if(min>interest[i][j]) min = interest[i][j];
			}
			for(Edge e : g.getLine(i+1)) {
				e.setUsed(min);
			}
		}
	}
	
	public int[][] getImage() {
		return img;
	}
	
	public int[][] getConserverSupprimer() {
		return conservSuppr;
	}
	
	public void setAction(int choix) {
		this.choix = choix;
		System.out.println("Choix "+choix);
	}
	
	public void zoneSelectionne(int x, int y, boolean chgt) {
		if(choix == 1) {
			if(chgt) conservSuppr[y][x] = 1;
			else conservSuppr[y][x] = 0;
		} else if(choix == 2) {
			if(chgt) conservSuppr[y][x] = -1;
			else conservSuppr[y][x] = 0;
		}
	}
	
	public void recap() {
		int cons =0;
		int suppr = 0;
		for(int i = 0; i<conservSuppr.length;i++) {
			for(int j = 0; j<conservSuppr[0].length;j++) {
				if(conservSuppr[i][j]!=0) {
					if(conservSuppr[i][j]==1) cons++;
					else suppr++;
				}
			}
		}
		System.out.println("conserv : "+cons+" - suppr : "+suppr);
	}
	
	/**
	 * les observateurs du mod�le vont �tre pr�venus du changement
	 */
	public void update() {
		setChanged();
		notifyObservers();
	}
}
