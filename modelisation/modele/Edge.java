package modelisation.modele;

class Edge
{
   final int from;
   final int to;
   final int capacity;
   int used;
   
   /**
    * Constructeur de Edge
    * @param x
    * @param y
    * @param capacity
    * @param used
    */
   Edge(int x, int y, int capacity, int used)
	 {
		this.from = x;
		this.to = y;
		this.capacity = capacity;
		this.used = used;
	 }
  
   /**
    * 
    * @param v
    * @return
    */
   final int other(int v)
	 {
		if (this.from == v) return this.to; else return this.from;
	 }
   
   /**
    * On v�rifi� si la capacit� de l'arr�t est �gal � l'infini repr�sent� par la valeur -1
    * @return
    */
   public boolean infini() {
	   return capacity==-1;
   }
   
   /**
    * Retourne vrai si la capacit� est �gal � la capacit� utilis�e, c'est à dire que l'arête est max
    * @return
    */
   public boolean max() {
	   return capacity == used;
   }
   
   /**
    * Permet d'initialiser la variable used
    * @param used
    */
   public void setUsed(int used) {
	   this.used = used;
   }
   
   /**
    * Permet d'ajouter une valeur � la variable used
    * @param add
    */
   public void addUsed(int add) {
	   this.used = used+add;
   }
   
   /**
    * Retourne la variable used
    * @return
    */
   public int getUsed() {
	   return used;
   }
   
   /**
    * Retourne la capacit� d'une ar�te
    * @return
    */
   public int getCapacity() {
	   return capacity;
   }
   
   /**
    * Retourne la variable from
    * @return
    */
   public int getFrom() {
	   return from;
   }
   
   /**
    * Retourne la variable to 
    * @return
    */
   public int getTo() {
	   return to;
   }
   
}
