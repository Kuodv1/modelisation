package modelisation.modele;

class Edge
{
   final int from;
   final int to;
   int capacity;
   int used;
   
   //Apparation du marquage, permettant de retrouver des arêtes infinis utilisés à contre sens
   int marquage;
   
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
		marquage = 0;
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
    * Permet d'initialiser la variable capacity
    * @param capacity
    */
   public void setCapacity(int capacity) {
	   this.capacity = capacity;
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
