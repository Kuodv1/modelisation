package modelisation.modele;

import java.util.ArrayList;
import java.io.*;
import java.util.*;
public class SeamCarving
{
	
	protected static int maxVal;//sers pour la réécriture du fichier pgm
	/**
	 * Constructeur SeamCarving
	 */
	public SeamCarving() {
		maxVal = 0;
	}
	
	/**
	 * Ecrit dans le fichier filename les valeurs du tableau image
	 * @param image
	 * @param filename
	 */
	   public static void writepgm(int[][] image, String filename)
	   {
		   PrintWriter writer = null;
		   try{
			   int i,j,boucle;
			   boucle = 0;
		        writer = new PrintWriter(filename);
		        writer.println("P2");
		        writer.println(image[0].length+" "+image.length);
		        writer.println(maxVal);
		        for(i = 0;i<image.length;i++)
		        {
		        	for(j =0;j<image[0].length;j++)
		        	{
		        		
		        		String texte = image[i][j]+" ";
		        		writer.write(texte,0,texte.length());
		        		boucle++;
		        		if(boucle==70) {
		        			writer.println("");
		        			boucle=0;
		        		}
		        	}
		        	boucle = 0;
		        	writer.println(""); 
		        }
		       // writer.println("");
		        writer.close();
		        
		   }catch(IOException ex){
		       ex.printStackTrace();
		   }
		   System.out.println("Ecriture fini.");
	   }
	

	   /**
	    * Affiche les lignes du fichier fn
	    * @param fn
	    * @return
	    */
	   public int[][] readpgm(String fn)
		 {		
	        try {
				InputStream f=new FileInputStream(fn); 
	            BufferedReader d = new BufferedReader(new InputStreamReader(f));
	            String magic = d.readLine();
	            String line = d.readLine();
			   while (line.startsWith("#")) {
				  line = d.readLine();
			   }
			   Scanner s = new Scanner(line);
			   int width = s.nextInt();
			   int height = s.nextInt();		   
			   line = d.readLine();
			   s = new Scanner(line);
			   maxVal = s.nextInt();
			   int[][] im = new int[height][width];
			   s = new Scanner(d);
			   int count = 0;
			   while (count < height*width) {
				  im[count / width][count % width] = s.nextInt();
				  count++;
			   }
			   return im;
	        }
			
	        catch(Throwable t) {
	            t.printStackTrace(System.err) ;
	            return null;
	        }
	    }
	   
	   public int[][] readppm(String fn)
		 {		
	        try {
					InputStream f=new FileInputStream(fn); 
		            BufferedReader d = new BufferedReader(new InputStreamReader(f));
		            String magic = d.readLine();
		            String line = d.readLine();
				   while (line.startsWith("#")) {
					  line = d.readLine();
				   }
				   Scanner s = new Scanner(line);
				   int width = s.nextInt();
				   int height = s.nextInt();		   
				   line = d.readLine();
				   s = new Scanner(line);
				   maxVal = s.nextInt();
				   int[][] im = new int[height][width];
				   s = new Scanner(d);
				   int count = 0;
				   int rouge = 0;
				   int vert = 0;
				   int bleu = 0;
				   while (count < height*width) {
					   rouge = s.nextInt();
					   vert = s.nextInt();
					   bleu = s.nextInt();
					  im[count / width][count % width] = rouge+vert*1000+bleu*1000000;
					  count++;
				   }
				   return im;
	        }
			
	        catch(Throwable t) {
	            t.printStackTrace(System.err) ;
	            return null;
	        }
	    }

	   
	   public int[][][] readppmDeux(String fn)
		 {		
	        try {
					InputStream f=new FileInputStream(fn); 
		            BufferedReader d = new BufferedReader(new InputStreamReader(f));
		            String magic = d.readLine();
		            String line = d.readLine();
				   while (line.startsWith("#")) {
					  line = d.readLine();
				   }
				   Scanner s = new Scanner(line);
				   int width = s.nextInt();
				   int height = s.nextInt();		   
				   line = d.readLine();
				   s = new Scanner(line);
				   maxVal = s.nextInt();
				   int[][][] im = new int[3][][];
				   s = new Scanner(d);
				   int count = 0;
				   int [][]rouge = new int[height][width];
				   int [][]vert = new int[height][width];
				   int [][]bleu = new int[height][width];
				   while (count < height*width) {
					   rouge[count/width][count%width] = s.nextInt();
					   vert[count/width][count%width] = s.nextInt();
					   bleu[count/width][count%width] = s.nextInt();
					   
					  count++;
				   }
				   im[0] = rouge;
				   im[1] = vert;
				   im[2] = bleu;
				   return im;
	        }
			
	        catch(Throwable t) {
	            t.printStackTrace(System.err) ;
	            return null;
	        }
	    }
   
	   public static void writeppm(int[][] image, String filename)
	   {
		   PrintWriter writer = null;
		   try{
			   int i,j,boucle;
			   int retrait;
			   boucle = 0;
		        writer = new PrintWriter(filename);
		        writer.println("P3");
		        writer.println(image[0].length+" "+image.length);
		        writer.println(maxVal);
		        for(i = 0;i<image.length;i++)
		        {
		        	for(j =0;j<image[i].length;j++)
		        	{
		        		//rouge - vert - bleu
		        		String bleu = ((int)(image[i][j]/(1000000))+" ");
		        		image[i][j] = image[i][j]%1000000;
		        		String vert = ((int)(image[i][j]/(1000))+" ");
		        		image[i][j] = image[i][j]%1000;
		        		String rouge = image[i][j]+" ";
		        		writer.write(rouge,0,rouge.length());
		        		boucle++;
		        		if(boucle==70) {
		        			writer.println("");
		        			boucle=0;
		        		}
		        		writer.write(vert,0,vert.length());
		        		boucle++;
		        		if(boucle==70) {
		        			writer.println("");
		        			boucle=0;
		        		}
		        		writer.write(bleu,0,bleu.length());
		        		boucle++;
		        		if(boucle==70) {
		        			writer.println("");
		        			boucle=0;
		        		}
		        	}
		        	boucle = 0;
		        	writer.println(""); 
		        }
		       // writer.println("");
		        writer.close();
		        
		   }catch(IOException ex){
		       ex.printStackTrace();
		   }
		   System.out.println("Ecriture fini.");
	   }
	   
}
