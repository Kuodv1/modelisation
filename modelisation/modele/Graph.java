package modelisation.modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.io.*;

public class Graph
{
   private ArrayList<Edge>[] adj;
   private final int V;
   int E;

   /**
    * Constructeur de Graph
    * @param N
    */
   public Graph(int N)
	 {
		this.V = N;
		this.E = 0;
		 adj = (ArrayList<Edge>[]) new ArrayList[N];
		for (int v= 0; v < N; v++)
		  adj[v] = new ArrayList<Edge>(8);
		
	 }

   /**
    * Retourne le nombre de sommet V
    * @return
    */
   public int vertices()
	 {
		return V;
	 }
   
   /**
    * Ajoute une ar�te � l'arraylist adj
    * @param e
    */
   public void addEdge(Edge e)
	 {
		int v = e.from;
		int w = e.to;
		adj[v].add(e);
		adj[w].add(e);
	 }
   
   /**
    * Retourne un it�rateur sur un ensemble d'�l�ments de type Edge.
    * @param v
    * @return
    */
   public final Iterable<Edge> adj(int v)
	 {
		return adj[v];
	 }      
   
   /**
    * Retourne un it�rateur sur un ensemble d'�l�ments de type Edge.
    * @return
    */
   public final Iterable<Edge> edges()
	 {
		ArrayList<Edge> list = new ArrayList<Edge>();
        for (int v = 0; v < V; v++)
            for (Edge e : adj(v)) {
                if (e.to != v)
                    list.add(e);
            }
        return list;
    }
   
   /**
    * Ecrit le point de d�part et le point d'arriv� d'une ar�te dans le fichier s
    * @param s
    */
   public void writeFile(String s)
	 {
		try
		  {			 
			 PrintWriter writer = new PrintWriter(s, "UTF-8");
			 writer.println("digraph G{");
			 for (Edge e: edges())
			   writer.println(e.from + "->" + e.to + "[label=\"" + e.used  +"/" + e.capacity + "\"];");
			 writer.println("}");
			 writer.close();
		  }
		catch (IOException e)
		  {
		  }						
	 }
   
   /**
    * Construit le graphe du tableau itr
    * @param itr
    */
   public void buildGraph(int[][] itr) {
		//initialisation arête reliant S
		int numSommet = 1;
		for(int i = 0;i<itr.length;i++) {
			addEdge(new Edge(0,numSommet,-1,0));
			addEdge(new Edge(numSommet,numSommet+itr.length,itr[i][0],0));
			numSommet++;
		}
		
		//initialisation des arêtes interne aux graphs et des arêtes finales
		for(int j = 1;j<itr[0].length; j++) {
			for(int i = 0;i<itr.length;i++) {
				int sommetDest = numSommet+itr.length;
				if(numSommet+itr.length>=V-1) {
					sommetDest = V-1;
				}
				addEdge(new Edge(numSommet,sommetDest,itr[i][j],0));
				addEdge(new Edge(numSommet,numSommet-itr.length,-1,0));
				if(i==0) {
					addEdge(new Edge(numSommet,numSommet-itr.length+1,-1,0));
				} else if(i==itr.length-1) {
					addEdge(new Edge(numSommet,numSommet-itr.length-1,-1,0));
				} else {
					addEdge(new Edge(numSommet,numSommet-itr.length+1,-1,0));
					addEdge(new Edge(numSommet,numSommet-itr.length-1,-1,0));
				}
				numSommet++;
			}
		}
   }
   
   /**
    * Construit le graphe du tableau itr
    * @param itr
    */
   public void buildGraph(int[][][] itr) {
		buildGraph(itr[0]);
		//edge from, to , capacity, used
		int numSommet = 1;
		for(int j = 0; j<itr[0][0].length;j++) {
			addEdge(new Edge(numSommet,numSommet+1,itr[2][0][j],0));
			numSommet++;
			for(int i = 1; i<itr[0].length-1;i++) {
				addEdge(new Edge(numSommet,numSommet+1,itr[2][i][j],0));
				addEdge(new Edge(numSommet,numSommet-1,itr[1][i][j],0));
				numSommet++;
			}
			addEdge(new Edge(numSommet,numSommet-1,itr[1][itr[1].length-1][j],0));
			numSommet++;
		}
		
   }
   
   /**
    * Retourne la position de l'ar�te � l'aide des param�tres from et to
    * @param from
    * @param to
    * @return
    */
   public int existEdge(int from, int to) {
	   boolean trouve = false;
	   int i = 0;
		  while(!trouve && i<adj[from].size()) {
			  if(adj[from].get(i).getTo()==to) {
				  trouve = true;
			  }
			  i++;
		  }
	   if(trouve) {
		   i--;
	   } else { i=-1;}
	   return i;
   }
   
   /**
    * Modifie la capacité d'une arête si celle-ci se trouve dans l'arraylist des arêtes à supprimer
    * @param suppr
    */
   public void modifInteretSuppr(ArrayList<Integer> suppr) {
	   for(int i = 0; i<suppr.size();i++) {
		   for(Edge e : adj[suppr.get(i)]) {
			   if(e.to == suppr.get(i) && e.from>suppr.get(i)) {
				   e.setCapacity(0);
			   }
		   }
	   }
   }
   
   /**
    * Modifie la capacité d'une arête à -1 si celle-ci se trouve dans l'arraylist des arêtes à conserver
    * @param conserv
    */
   public void modifInteretConserv(ArrayList<Integer> conserv) {
	   for(int i = 0; i<conserv.size();i++) {
		   for(Edge e : adj[conserv.get(i)]) {
			   e.setCapacity(-1);
		   }
	   }
   }
   
   /**
    * Retourne l'ar�te � l'aide des param�tres from et to
    * @param from
    * @param to
    * @return
    */
   public Edge getEdge(int from, int to) {
	   	Edge edge = adj[from].get(0);
	   	int num = existEdge(from,to);
	   	if(num!=-1) {
	   		edge = adj[from].get(num);
	   	}
		  return edge;
   }
   
   /**
    * Retourne le nombre de ligne
    * @return
    */
   public int getNbLine() {
	   return adj[0].size();
   }
   
   /**
    * Retourne ne nombre de colonne
    * @return
    */
   public int getNbColonne() {
	   return (adj.length-2)/getNbLine();
   }
   
   /**
    * Retourne une arraylist de sommet d'une ligne du graphe
    * @param numLigne
    * @return 
    */
   public ArrayList<Edge> getLine(int numLigne) {
	   int numSommet = numLigne;
	   ArrayList<Edge> result = new ArrayList<Edge>();
	   if(!(numLigne<1 || numLigne>adj[0].size())){
		  int line = adj[0].size();
		  int colonne = (adj.length-2)/line;
		  
		  result.add(getEdge(0,numLigne));//première arête
		  int i;
		  for(i = 0;i<colonne-1;i++) {
			  result.add(getEdge(numSommet,numSommet+line));
			  numSommet+=line;
		  }
		  result.add(getEdge(numSommet,adj.length-1));//dernière arête
	   }
	   
	   return result;
   }
   
   /**
    * Conception de graphe r�siduel
    */
   public void getGraphResidu() {
	   
	   boolean flotMax = false;
	   while(!flotMax) {
		   ArrayList<Edge> check = graphResiduIte();
		   if(check.isEmpty()) {
			   //System.out.println("Flot max atteint");
			   flotMax = true;
		   } else {
			   toFlotMax(check);
			   remetZeroMarquage();
		   }
	   }   
   }
   
   /**
    * Demarquage des arêtes infinis
    */
   public void remetZeroMarquage() {
	   for(int i = 0; i<adj.length;i++) {
		   for(Edge e : adj[i]) {
			   if(e.infini()) {
				   e.marquage=0;
			   }
		   }
	   }
   }
   
   
   /**
    * Retourne le graphe r�siduel
    * @return
    */
   public ArrayList<Edge> graphResiduIte() {
	   ArrayList<Edge> test = new ArrayList<Edge>();
	   ArrayList<Edge> result = new ArrayList<Edge>();
	   HashMap<Integer,Edge> visit = new HashMap<Integer,Edge>();
	   boolean trouve = false;
	   
	   
	   for(Edge e : adj[0]) {
		   test.add(e);
		   visit.put(e.to, e);
	   }
	   
	   while(!test.isEmpty() && !trouve) {
		   Edge check = test.get(0);
		   int sommet = 0;
		   if(check.getTo()==adj.length-1) { result.add(check); trouve = true;} 
		   else {
			   //Si l'arête est marqué, alors elle est infini, et permet d'atteindre un sommet inexploré. Pour avoir les bonnes arêtes de ce sommet, on vérifie par le marquage la destination souhaité
			   if(check.marquage==1) sommet = check.from;
			   else sommet = check.to;
			   for(Edge e : adj[sommet]) {
				   if(!e.max() && !visit.containsKey(e.to) && sommet==e.from) { //traitement des arêtes directs
					   test.add(e);
					   visit.put(e.to, e);
				   } else if(e.infini() && e.used>0 && !visit.containsKey(e.from)){ //traitement des arêtes infinis avec une utilisation >0 et qui permette l'accès à un sommet inexploré
					   test.add(e);
					   visit.put(e.from, e);
					   e.marquage = 1; //marqué l'arête pour se souvenir qu'on la parcours à contre sens
				   }
			   }
			   test.remove(0);
		   }
			
			   
	   }
	   
	   if(trouve) {
		   Edge retrace = result.get(0);
		   int sommet = 0;
		   while(retrace.from!=0) {
			   if(retrace.marquage==1) { //le marquage permet de faire l'inversion si nécessaire
				   sommet = retrace.to;
			   }
			   else sommet = retrace.from;
			   retrace = visit.get(sommet);
			   result.add(retrace);
		   }
	   }
	   return result;
   }
   
   /**
    * Effectue la recherche du flot max
    * @param chemin
    */
   public void toFlotMax(ArrayList<Edge> chemin) {
	   
	   int min = Integer.MAX_VALUE;
	   int temp = Integer.MAX_VALUE;
	   for(Edge e : chemin) {
		   if(e.capacity!=-1) {
			   temp = 0;
			   if(existEdge(e.to, e.from)!=-1) { //si !=1, alors il existe une arête retour
					Edge a = getEdge(e.to, e.from); // la prendre
					temp+= a.used; // ajouter son utilisation à ce que l'on peut envoyer
				}
			   temp+= (e.capacity-e.used);
		   } else if(e.marquage==1) temp = e.used; //utilisation d'une arête infini mais dans le sens retour
		   if(temp < min) min = temp;
		   temp = Integer.MAX_VALUE;
	   }
	   
	   for(Edge e : chemin) {
		   if(min>(e.capacity-e.used) && !e.infini()) {
			   int tempD = e.capacity-e.used;
			   e.addUsed(tempD);
			   Edge a = getEdge(e.to,e.from); // l'arête retour infini ou non
			   a.addUsed(tempD-min); // on lui retire le surplus qu'on a
			  // if(a.used<0 || (a.capacity<a.used)) System.out.println(a.used +"/"+a.capacity); // test d'erreur
		   } else if(e.marquage==1) {//cas arête infini dans le sens retour
			   e.addUsed(-min);
		   }
		   else e.addUsed(min); //cas arête infini dans le bon sens ou arête avec capacité suffisante
		   //if((e.used<0 || (e.capacity<e.used)) && !e.infini()) System.out.println(e.used+"/"+e.capacity); //autre test d'erreur
	   }
   }
   
   /**
    * Retourne la valeur de la coupe min
    * @return
    */
   public ArrayList<Integer> coupeMin() {
	   ArrayList<Edge> test = new ArrayList<Edge>();
	   HashSet<Integer> visitHs = new HashSet<Integer>();
	   for(Edge e : adj[0]) {
		   test.add(e);
		   visitHs.add(e.to);
	   }
	   
	   visitHs.add(0);
	   
	   while(!test.isEmpty()) {
		  Edge check = test.get(0);
		   for(Edge e : adj[check.to]) {
			   if(!e.max() && !visitHs.contains(e.to) && check.to==e.from) {
				   test.add(e);
				   visitHs.add(e.to);
			   }
		   }
		   test.remove(0);
		}

	   ArrayList<Integer> coupe = new ArrayList<Integer>();
	   for(int i = 1; i<=getNbLine();i++) {
		   int j = i;
		   while(visitHs.contains(j)) {
			   j+=getNbLine();
		   }
		   coupe.add(j-getNbLine());
	   }
	   
	   
	   return coupe;
   }

   
}
