package modelisation.modele;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;

public class Modele extends Observable {

	protected SeamCarving sc;
	protected String lienImg;
	int [][] img;
	int [][] interest;
	Selection conservSuppr;
	protected String methode;
	Graph g;
	int choix; //0 = pas de selection, 1 = conservation, 2 = supprimer
	
	/**
	 * Constructeur de Modele
	 */
	public Modele() {
		sc = new SeamCarving();
		lienImg = "";
		img = null;
		methode = "classique";
		choix = 0;
	}
	
	/**
	 * Retourne le lien de l'image lienImg
	 * @return
	 */
	public String getLink() {
		return lienImg;
	}
	
	public String getMethode() {
		return methode;
	}
	
	public void setMethode(String methode) {
		this.methode = methode;
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
		
		 conservSuppr = new Selection(img.length,img[0].length);
		 
		 System.out.println("Image chargé. Réduction possible.");
		 update();
	}
	
	public void readPpm(String filePath) {
		img = sc.readppm(filePath);
		lienImg = filePath;
		 System.out.println("Image chargé. Réduction possible.");
		 
		 conservSuppr = new Selection(img.length,img[0].length);
		 update();
	}
	
	/**
	 * Conception du tableau des int�rets, g�n�ration du graphe du tableau des interets et �laboration du graphe r�siduel
	 */
	public void generationGraph() {
		ArrayList<Integer> suppr = new ArrayList<Integer>();
		ArrayList<Integer> conserv = new ArrayList<Integer>();
		if(lienImg.endsWith(".pgm")) {
			interest = interest(img,suppr, conserv);
		}
		else if (lienImg.endsWith(".ppm")) interest = interestPPM(img,suppr,conserv);
		g = toGraph(interest);
		if(suppr.size()>0) g.modifInteretSuppr(suppr);
		if(conserv.size()>0) g.modifInteretConserv(conserv);
		defaultFlot();
		getGraphResidu();
	}
	
	public void generationGraphEnergie() {
		ArrayList<Integer> suppr = new ArrayList<Integer>();
		ArrayList<Integer> conserv = new ArrayList<Integer>();
		int[][][] interestE = new int[0][0][0];
		if(lienImg.endsWith(".pgm")) {
			interestE = interestEnergieAvant(img,suppr,conserv);
		} else if (lienImg.endsWith(".ppm")) {
			interestE = interestEnergieAvant(img,suppr,conserv);
		}
		g = toGraph(interestE);
		if(suppr.size()>0) g.modifInteretSuppr(suppr);
		if(conserv.size()>0) g.modifInteretConserv(conserv);
		getGraphResidu();
	}
	
	/**
	 * Conception du tableau des inter�ts � partir du tableau image
	 * @param image
	 * @return
	 */
	   public int[][] interest(int[][] image, ArrayList<Integer> suppr, ArrayList<Integer> conserv)
	   {		   
		 //initialisation tableau même taille
		   int i,j;
		   int[][] imageInt = new int[image.length][image[0].length];
		   
		   for(i=0;i<image.length;i++)
		   {
			   imageInt[i][image[i].length-1] = 1 + Math.abs((image[i][image[i].length-2]-image[i][image[i].length-1]));
			   conservSupprPixel(i, image[i].length-1, imageInt,suppr,conserv);
			   for(j=image[i].length-2;j>0;j--)
			   {
				   imageInt[i][j] = 1+ Math.abs(image[i][j]-(int)((image[i][j-1]+image[i][j+1])/2));
				   conservSupprPixel(i, j, imageInt,suppr,conserv);
			   }
			   imageInt[i][0] = 1 + Math.abs((image[i][0]-image[i][1]));
			   conservSupprPixel(i,0, imageInt,suppr,conserv);
		   }
		   
		   return imageInt;
	   }

	   
	   public int[][][] interestEnergieAvant(int[][] image, ArrayList<Integer> suppr, ArrayList<Integer> conserv)
	   {		   
		 //initialisation tableau même taille
		   int i,j;
		   int[][][] imageInt = new int[3][image.length][image[0].length];//0 arrête vers droite - 1 arrête vers Haut - 2 arrête vers bas

		   for(j = 1;j<image[0].length-1;j++) {
			   imageInt[0][0][j] = 1 + interestDroite(0,j,image);
			   imageInt[1][0][j] = 0;
			   imageInt[2][0][j] = 1 + interestBas(0,j,image);
			   conservSupprPixelEnergie(0, j, imageInt,suppr,conserv);
			   
			   for(i = 1;i<image.length-1;i++) {
				  imageInt[0][i][j] = 1 + interestDroite(i,j,image);
				  imageInt[1][i][j] = 1 + interestHaut(i,j,image);
				  imageInt[2][i][j] = 1 + interestBas(i,j,image);
				  conservSupprPixelEnergie(i,j, imageInt,suppr,conserv);
			   }
			   
			   imageInt[0][image.length-1][j] = 1 + interestDroite(image.length-1,j,image);
			   imageInt[1][image.length-1][j] = 1 + interestHaut(image.length-1,j,image);
			   imageInt[2][image.length-1][j] = 0;
			   conservSupprPixelEnergie(image.length-1, j, imageInt,suppr,conserv);
			   
		   }
		   
		   //i=0
		   imageInt[0][0][0] = 1 + image[0][1];
		   imageInt[0][0][image[0].length-1] = 1 + image[0][image[0].length-2];
		   imageInt[1][0][0] = 0;
		   imageInt[1][0][image[0].length-1] = 0;
		   imageInt[2][0][0] = 1 + image[1][0];
		   imageInt[2][0][image[0].length-1] = 1 + interestBas(0,image[0].length-1,image);
		   
		   conservSupprPixelEnergie(0,0, imageInt,suppr,conserv);
		   conservSupprPixelEnergie(0,image[0].length-1, imageInt,suppr,conserv);
		   
		   for(i = 1;i<image.length-1;i++) {
			   imageInt[0][i][0] = 1 + image[i][1];
			   imageInt[0][i][image[i].length-1] = 1 + image[i][image[i].length-2];
			   imageInt[1][i][0] = 1 + image[i-1][0];
			   imageInt[1][i][image[i].length-1] = 1 + interestHaut(i,image[i].length-1,image);
			   imageInt[2][i][0] = 1 + image[i+1][0];
			   imageInt[2][i][image[i].length-1] = 1 + interestBas(i,image[i].length-1,image);
			   
			   conservSupprPixelEnergie(i, 0, imageInt,suppr,conserv);
			   conservSupprPixelEnergie(i, image[i].length-1, imageInt,suppr,conserv);
		   }
		   
		   //i=image.length-1
		   i = image.length-1;
		   imageInt[0][i][0] = 1 + image[i][1];
		   imageInt[0][i][image[i].length-1] = 1 + image[i][image[i].length-2];
		   imageInt[1][i][0] = 1 + image[i-1][0];
		   imageInt[1][i][image[i].length-1] = 1 + interestHaut(i,image[i].length-1,image);
		   imageInt[2][i][0] = 0;
		   imageInt[2][i][image[i].length-1] = 0;
		   
		   conservSupprPixelEnergie(i, 0, imageInt,suppr,conserv);
		   conservSupprPixelEnergie(i, image[i].length-1, imageInt,suppr,conserv);
		   
		   return imageInt;
	   }
	   
	   public void conservSupprPixel(int i, int j, int[][] itr, ArrayList<Integer> suppr, ArrayList<Integer> conserv) {
		   if(conservSuppr.getValue(i, j)!=0) {
			   if(conservSuppr.getValue(i, j)==-1) supprPixel(i,j,itr,suppr);
			   else conservPixel(i,j,itr,conserv);
		   }
	   }

	   public void conservSupprPixelEnergie(int i, int j, int[][][] itr, ArrayList<Integer> suppr, ArrayList<Integer> conserv){
		   if(conservSuppr.getValue(i, j)!=0) {
			   if(conservSuppr.getValue(i, j)==-1) supprPixelEnergie(i,j,itr,suppr);
			   else conservPixelEnergie(i,j,itr,conserv);
		   }
	   }
	   
	   public void supprPixel(int i, int j, int[][] imageInt, ArrayList<Integer> suppr) {
		   suppr.add((imageInt.length*j)+i+1);
		   imageInt[i][j] = 0;
	   }
	   
	   public void supprPixelEnergie(int i, int j, int[][][] imageInt, ArrayList<Integer> suppr) {
		   suppr.add((imageInt[0].length*j)+i+1);
		   imageInt[0][i][j] = 0;
		   imageInt[1][i][j] = 0;
		   imageInt[2][i][j] = 0;
	   }
	   
	   public void conservPixel(int i, int j, int[][] imageInt, ArrayList<Integer> conserv) {
		   conserv.add((imageInt.length*j)+1+i);
	   }
	   
	   public void conservPixelEnergie(int i, int j, int[][][] imageInt, ArrayList<Integer> conserv) {
		   conserv.add((imageInt[0].length*j)+1+i);
	   }
	   
	   public int interestDroite(int i, int j, int[][] image) {
		   //(i,j) -> (i,j+1) |M(i,j+1) - M(i,j-1)|
		   return Math.abs(image[i][j+1]-image[i][j-1]);
	   }
	   
	   public int interestHaut(int i, int j, int[][] image) {
		   //(i,j) -> (i-1,j) |M(i-1,j) - M(i,j-1)|
		   return Math.abs(image[i-1][j] - image[i][j-1]);
	   }
	   
	   public int interestBas(int i, int j, int[][] image) {
		   //(i,j) -> (i+1,j) |M(i+1,j) - M(i,j-1)|
		   return Math.abs(image[i+1][j]-image[i][j-1]);
	   }
	   
	   public int[][] interestPPM(int[][] image, ArrayList<Integer> suppr, ArrayList<Integer> conserv) {
		   //initialisation tableau même taille
		   int i,j;
		   int[][] imageInt = new int[image.length][image[0].length];
		   
		   int[] rgb;
		   int[] rgb2;
		   int[] rgb3;
		   
		   for(i=0;i<image.length;i++)
		   {
			   rgb = getRGB(image[i][image[i].length-2]);
			   rgb2 = getRGB(image[i][image[i].length-1]);
			   imageInt[i][image[i].length-1] = 1;
			   for(int couleur = 0; couleur<3;couleur++) imageInt[i][image[i].length-1] += Math.abs(rgb[couleur]-rgb2[couleur]);
			   conservSupprPixel(i, image[i].length-1, imageInt,suppr,conserv);
			   for(j=image[i].length-2;j>0;j--)
			   {
				   rgb = getRGB(image[i][j]);
				   rgb2 = getRGB(image[i][j-1]);
				   rgb3 = getRGB(image[i][j+1]);
				   imageInt[i][j] = 1;
				   for(int couleur = 0; couleur<3;couleur++) imageInt[i][j] += Math.abs(rgb[couleur] -(int)((rgb2[couleur]+rgb3[couleur])/2));
				   conservSupprPixel(i, j, imageInt,suppr,conserv);
			   }
			   rgb = getRGB(image[i][0]);
			   rgb2 = getRGB(image[i][1]);
			   imageInt[i][0] = 1;
			   for(int couleur = 0; couleur<3;couleur++) imageInt[i][j] += Math.abs(rgb[couleur]-rgb2[couleur]);
			   conservSupprPixel(i, 0, imageInt,suppr,conserv);
		   }
		   
		   return imageInt;
	   }
	   
	   public int[][][] interestEnergieAvantPPM(int[][] image, ArrayList<Integer> suppr, ArrayList<Integer> conserv)
	   {		   
		 //initialisation tableau même taille
		   int i,j;
		   int[][][] imageInt = new int[3][image.length][image[0].length];//0 arrête vers droite - 1 arrête vers Haut - 2 arrête vers bas

		   for(j = 1;j<image[0].length-1;j++) {
			   imageInt[0][0][j] = 1 + interestDroitePPM(0,j,image);
			   imageInt[1][0][j] = 0;
			   imageInt[2][0][j] = 1 + interestBasPPM(0,j,image);
			   
			   for(i = 1;i<image.length-1;i++) {
				  imageInt[0][i][j] = 1 + interestDroitePPM(i,j,image);
				  imageInt[1][i][j] = 1 + interestHautPPM(i,j,image);
				  imageInt[2][i][j] = 1 + interestBas(i,j,image);
			   }
			   
			   imageInt[0][image.length-1][j] = 1 + interestDroitePPM(image.length-1,j,image);
			   imageInt[1][image.length-1][j] = 1 + interestHautPPM(image.length-1,j,image);
			   imageInt[2][image.length-1][j] = 0;
			   
		   }
		   
		   //i=0
		   imageInt[0][0][0] = 1 + interestRGBCase(0,1,image);
		   imageInt[0][0][image[0].length-1] = 1 + interestRGBCase(0,image[0].length-2,image);
		   imageInt[1][0][0] = 0;
		   imageInt[1][0][image[0].length-1] = 0;
		   imageInt[2][0][0] = 1 + interestRGBCase(1,0,image);
		   imageInt[2][0][image[0].length-1] = 1 + interestBasPPM(0,image[0].length-1,image);
		   
		   for(i = 1;i<image.length-1;i++) {
			   imageInt[0][i][0] = 1 + interestRGBCase(i,1,image);
			   imageInt[0][i][image[i].length-1] = 1 + interestRGBCase(i,image[i].length-2,image);
			   imageInt[1][i][0] = 1 + interestRGBCase(i-1,0,image);
			   imageInt[1][i][image[i].length-1] = 1 + interestHautPPM(i,image[i].length-1,image);
			   imageInt[2][i][0] = 1 + interestRGBCase(i+1,0,image);
			   imageInt[2][i][image[i].length-1] = 1 + interestBasPPM(i,image[i].length-1,image);
		   }
		   
		   //i=image.length-1
		   i = image.length-1;
		   imageInt[0][i][0] = 1 + image[i][1];
		   imageInt[0][i][image[i].length-1] = 1 + image[i][image[i].length-2];
		   imageInt[1][i][0] = 1 + interestRGBCase(i-1,0,image);
		   imageInt[1][i][image[i].length-1] = 1 + interestHautPPM(i,image[i].length-1,image);
		   imageInt[2][i][0] = 0;
		   imageInt[2][i][image[i].length-1] = 0;
		   
		   return imageInt;
	   }
	   
	   public int interestDroitePPM(int i, int j, int[][] image) {
		   int rgb[] = getRGB(image[i][j+1]);
		   int rgb2[] = getRGB(image[i][j-1]);
		   int ret = 0;
		   for(int couleur = 0; couleur<3;couleur++) ret+= Math.abs(rgb[couleur]-rgb2[couleur]);
		   return ret;
	   }
	   
	   public int interestHautPPM(int i, int j, int[][] image) {
		   int rgb[] = getRGB(image[i-1][j]);
		   int rgb2[] = getRGB(image[i][j-1]);
		   int ret = 0;
		   for(int couleur = 0; couleur<3;couleur++) ret+= Math.abs(rgb[couleur]-rgb2[couleur]);
		   return ret;
	   }
	   
	   public int interestBasPPM(int i, int j, int[][] image) {
		   int rgb[] = getRGB(image[i+1][j]);
		   int rgb2[] = getRGB(image[i][j-1]);
		   int ret = 0;
		   for(int couleur = 0; couleur<3;couleur++) ret+= Math.abs(rgb[couleur]-rgb2[couleur]);
		   return ret;
	   }
	   
	   public int interestRGBCase(int i, int j, int[][]image) {
		   int ret = 0;
		   int rgb[] = getRGB(image[i][j]);
		   for(int couleur = 0; couleur<3;couleur++) ret+=rgb[couleur];
		   return ret;
	   }
	   
	   public int[] getRGB(int caseTab) {
		   int[] rgb = new int[3];
		   int i = caseTab;
			rgb[2] = (i/(1000000));
	   		i = i%1000000;
	   		rgb[1] = (i/(1000));
	   		i = i%1000;
	   		rgb[0] = i;
	   		return rgb;
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
	
	public Graph toGraph(int[][][] itr) {
		Graph graph = new Graph(itr[0].length*itr[0][0].length+2);
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
		conservSuppr = new Selection(img.length, img[0].length);
		update();
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
				System.out.println("Traitement : "+((depix*100)/reduc)+"%");
				if(methode.equals("Classique")) generationGraph();
				else if(methode.equals("Energie avant")) generationGraphEnergie();
				ArrayList<Integer> coupe = g.coupeMin();
				if(img[0].length>2) {
				img = removeColonne(coupe);
				}
				
			}
			System.out.println("Traitement : 100%.");
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
			conservSuppr.rotationGauche();
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
			conservSuppr.rotationDroite();
		}
	}
	
	/**
	 * Supprime une colonne
	 * @param coupe
	 * @return
	 */
	public int[][] removeColonne(ArrayList<Integer> coupe) {
		int [][] newImage = new int[img.length][img[0].length-1];
		Selection newConservSuppr = new Selection(conservSuppr.getHauteur(),conservSuppr.getLargeur()-1);
		for(int i=0;i<newImage.length-1;i++) {
			for(int j = 0; j<newImage[i].length;j++) {
				if((coupe.get(i)/coupe.size())>j) {
					newImage[i][j]=img[i][j];
					newConservSuppr.setValue(i, j, conservSuppr.getValue(i, j));
				} else {
					newImage[i][j]=img[i][j+1];
					newConservSuppr.setValue(i, j, conservSuppr.getValue(i, j+1));
				}
			}
		}
		int ligne = newImage.length-1;
		for(int j= 0; j<newImage[ligne].length;j++) {
			if(coupe.get(coupe.size()-1)/coupe.size()-1>j){
				newImage[ligne][j]=img[ligne][j];
				newConservSuppr.setValue(ligne, j, conservSuppr.getValue(ligne, j));
			} else {
				newImage[ligne][j] = img[ligne][j+1];
				newConservSuppr.setValue(ligne, j, conservSuppr.getValue(ligne, j+1));
			}
		}
		conservSuppr = newConservSuppr;
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
		return conservSuppr.getSelect();
	}
	
	public void setAction(int choix) {
		this.choix = choix;
	}

	
	public void select(Point p1, Point p2){
		if(choix!=0) {
			Point pUn;
			Point pDeux;
			if(p1.getX()<p2.getX()) {
				if(p1.getY()<p2.getY()) {
					pUn = p1;
					pDeux = p2;
				} else {
					pUn = new Point((int)p1.getX(),(int)p2.getY());
					pDeux = new Point((int)p2.getX(),(int)p1.getY());
				}
			} else {
				if(p1.getY()<p2.getY()) {
					pUn = new Point((int)p2.getX(),(int)p1.getY());
					pDeux = new Point((int)p1.getX(),(int)p2.getY());
				}else {
					pUn = p2;
					pDeux = p1;
				}
			}
			
			int newValue = 0;
			if(choix == 1) { // conserver
				newValue = 1;
			} else if (choix == 2) { // supprimer
				newValue = -1;
			}
			
			conservSuppr = new Selection(conservSuppr);
			
			for(int i = (int)pUn.getY();i<(int)pDeux.getY();i++) {
				for(int j = (int)pUn.getX(); j<(int)pDeux.getX();j++) {
					conservSuppr.setValue(i, j, newValue);
				}
			}
			update();
		}
	}
	
	public void supprimerSelection(int x, int y) {
		if(conservSuppr.getValue(y, x)!=0) {
			conservSuppr = new Selection(conservSuppr);
			conservSuppr.supprimerSelection(x,y);
			update();
		}
	}
	
	public void annulerSelection() {
		conservSuppr = conservSuppr.getPere();
		update();
	}
	
	public boolean annulerSelectPossible() {
		return conservSuppr.annulerSelectPossible();
	}

	
	public int getHauteur() {
		if(img!=null) return img.length;
		else return 0;
	}
	
	public int getLargeur() {
		if(img!=null) return img[0].length;
		else return 0;
	}

	/**
	 * les observateurs du mod�le vont �tre pr�venus du changement
	 */
	public void update() {
		setChanged();
		notifyObservers();
	}
}
