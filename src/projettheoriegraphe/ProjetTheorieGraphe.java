
package projettheoriegraphe;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class ProjetTheorieGraphe {
    
    private int numeroFichier;
    
    private int nombreSommet;

    private static int NULLE = 3000; 

    private ArrayList<ArrayList<Integer>> graphe;
    private ArrayList<Integer> indexes = new ArrayList<Integer>(); // Cette variable sert à supprimer les indices du tableau lors de la recherche de circuit
    private ArrayList<Integer> indexesRang = new ArrayList<Integer>(); // Cette variable sert à stocker les rangs de chaque indice du tableau
    

    private ArrayList<ArrayList<Integer>> ouverture() throws IOException{ // Cette fonction sert a lire un fichier

        Scanner sc = new Scanner(System.in); // On initialise le scanner sur la console 
        String nomFichier = "src/projettheoriegraphe/G"; //On cree le chemin pour obtenir le fichier
        System.out.println("Quelle graphe voulez vous : "); //On demande quel graphe l'utilisateur veut 
        numeroFichier = sc.nextInt();//On recupere le numero du graphe
        nomFichier += numeroFichier + ".txt"; //On complete le nom du fichier avec le numero 
        System.out.println(nomFichier); //On affiche

        ArrayList<ArrayList<Integer>> table = new ArrayList<>(); // On cree un tableau 2D pour stocker le graphe
        Scanner reader = new Scanner(new File(nomFichier)); // on initialise un scanner pour lire le fichier
        if (reader.hasNext()) { // on regarde si il y a quelque chose à lire
            nombreSommet = reader.nextInt(); // on recupere le nombre de sommet et on le stock
            table.add(new ArrayList<>());//On cree la premiere ligne de notre tableau
            table.get(0).add(nombreSommet);//On ajoute sur notre premiere ligne le nombre de sommet
        }

        int compteur = 1; //On cree une variable i pour acceder aux differentes lignes de notre tableau  
        while (reader.hasNext()){ // cette boucle permet de reécuperer le différents chemins et les stocks dans un tableau
            int temp = reader.nextInt();
            table.add(new ArrayList<>());
            table.get(compteur).add(temp);
            temp = reader.nextInt();
            table.get(compteur).add(temp);
            temp = reader.nextInt();
            table.get(compteur).add(temp);
            compteur++;
        }
        return table; // On retourne le tableau qu'on a créé
    }
    
    private void completion(ArrayList<ArrayList<Integer>> table) { // Cette fonction sert a transformer le tableau que l'on a cree à la lecture de fichier en matrice de valeurs
        int longueur = table.get(0).get(0);
        graphe = new ArrayList<>();
        for (int i=0; i<longueur; i++) { // il se déplace de 0 au nombre de sommet
            graphe.add(new ArrayList<>(longueur)); // sa taille est égale à 12
            for (int j=0; j<longueur; j++) {
                graphe.get(i).add(NULLE); 
            }
        }
        for (int i=1; i<table.size(); i++) {
            int predecesseur = table.get(i).get(0);
            int arc = table.get(i).get(1);
            int sucesseur = table.get(i).get(2);
            graphe.get(predecesseur).set(sucesseur, arc); // chaque prédecesseur est associé à son successeur et la valeur de l'arc dans le graphe 
        }
    }
    
    private void MatriceValeurs(ArrayList<ArrayList<Integer>> table) throws IOException { //Cette fonction permet d'afficher notre matrice de valeur 
        System.out.println(); //On saute une ligne 
        for (int j=0; j<table.size(); j++) { //On cree une boucle pour parcourir les lignes 
            afficher1ereligne(table, j); //On affiche la premiere ligne du tableau, mais aussi les premiers caractères de chaque ligne 
            for(int i=0; i<table.get(j).size(); i++) { //On cree une deuxieme boucle pour parcourir les colonnes
                if (table.get(j).get(i) == NULLE) { //Nous permet d'afficher le signe -
                    System.out.print(String.format("%-" + 8 + "s", "-"));
                    String trace = String.format("%-" + 8 + "s", "-");
                    traceExe(trace);
                    //On utilise String.format qui va nous permettre d'utiliser un espace d'une taille que l'on veut que l'on va remplir avec nos valeurs, ainsi l'espace non utilise sera rempli de vide 
                }
                else //Nous permet d'afficher les nombres
                    System.out.print(String.format("%-" + 8 + "s", (table.get(j).get(i))));
                if(table.get(j).get(i) != NULLE)  {
                String trace1 = String.format("%-" + 8 + "s", (table.get(j).get(i)));
                traceExe(trace1);}
            }
        }
        System.out.println("\n\n");//Retours a la ligne 
    }

    private void MatriceAdjacence(ArrayList<ArrayList<Integer>> table) throws IOException {//On va afficher la matrice d'ajacence 
        System.out.println("\n\n\n");//Retours a la ligne
        for (int j=0; j<table.size(); j++) {//On cree une premiere boucle pour parcourir les lignes 
            afficher1ereligne(table, j);//On affiche la premiere ligne du tableau, mais aussi les premiers caractere de chaque ligne 
            for(int i=0; i<table.get(j).size(); i++) {//On cree une deuxieme bouble pour parcourir les colonnes
                if (table.get(j).get(i) != NULLE){//Si la case est differente de nulle, on affiche un 1
                    System.out.print(String.format("%-" + 8 + "s", "1"));
                    String trace0 = String.format("%-" + 8 + "s", "1");
                    traceExe(trace0);
                        }else{ //Sinon on affiche un - 
                                System.out.print(String.format("%-" + 8 + "s", "-"));
                                String trace1 = String.format("%-" + 8 + "s", "-");
                                traceExe(trace1);
                        }
            }
        }
        System.out.println("\n\n");//Retours a la ligne 
        String trace2 = "\n\n";
        traceExe(trace2);
    }
    
    private void afficher1ereligne(ArrayList<ArrayList<Integer>> table, int j) throws IOException {//Cette fonction sert a afficher la premiere ligne et les premiers caracteres de chaque ligne
        if (j==0) { //On regarde si on doit afficher la premiere ligne 
            System.out.print(" ");
            String trace1 = " ";
            traceExe(trace1);
            for (int i=0; i<table.get(0).size(); i++) {//On parcourt le tableau dans les colonnes 
                System.out.print(String.format("%" + 8 + "s", i));//On affiche les différentes colonnes de la premiere ligne 
                String trace = String.format("%" + 8 + "s", i);
                traceExe(trace);
            }
        }
        System.out.print("\n\n" + String.format("%-" + 7 + "s", j));//On affiche les numeros des colonnes 
        String trace2 = "\n\n" + String.format("%-" + 7 + "s", j);
        traceExe(trace2);
    }
    
    private int demidegreExt(ArrayList<ArrayList<Integer>> table, int sommet){ // Cette fonction sert à retourner la valeur du demi degré extérieur d'un sommet donné
        int deg = 0; // Cette variable sert de compteur
            for(int j = 0; j < table.size() ; j++){ 
                if(table.get(sommet).get(j) != NULLE)// Cette condition sert à incrémenter le compteur de 1 à chaque fois qu'il y a une valeur présente dans une cellule de la ligne
                    deg = deg + 1;                   // Nous savons que le demi degré intérieur correspond au nombre de 1 ou V rencontré dans la ligne du sommet
            }                                        // Cette fonction se base sur la matrice des valeurs, ainsi au lieu d'afficher le nombre de v ou 1 , elle retourne le nombre de valeur non vide
        return deg;// On retourne la valeur du demi degré du sommet donné
    }
    
    private int demidegreInt(ArrayList<ArrayList<Integer>> table, int sommet){ // Cette fonction sert à retourner la valeur du demi degré intérieur d'un sommet donné
        int deg = 0; // Cette variable sert de compteur
        for(int i = 0; i < table.size(); i++){
                if(table.get(i).get(sommet) != NULLE ) // Cette condition sert à incrémenter le compteur de 1 à chaque fois qu'il y a une valeur présente dans une cellule de la colonne
                    deg = deg + 1;                     // Nous savons que le demi degré intérieur correspond au nombre de 1 ou V rencontré dans la colonne du sommet
        }                                              // Cette fonction se base sur la matrice des valeurs, ainsi au lieu d'afficher le nombre de v ou 1 , elle retourne le nombre de valeur non vide
         
        return deg; // On retourne la valeur du demi degré du sommet donné
    }
    
    private void exist(ArrayList<Integer> table){ // Cette fonction sert à supprimer les occurences trouver dans la liste de points d'entrées
        for(int i = 0; i < table.size() ; i++){
            for(int j = 0;j < table.size(); j++){ // Nous parcourons toute la liste
                if( i < table.size() && j < table.size()){
                    if(table.get(j) == table.get(i) && i != j) {
                        i = 0;
                        j = 0;
                        table.remove(j); // Cette condition nous permet de voir si une cellule du tableau est égale à une autre cellule dans tout le tableau 
                                         // Et ainsi supprimer toutes les occurences rencontrer.
                    }
                }
            }
        }
    }
    
    private void afficher1ereligneCircuit(ArrayList<ArrayList<Integer>> table, int j) throws IOException{// Cette fonction sert à afficher la première ligne du tableau dans les points d'entrés et de sorties supprimés par la fonction circuit
        
         if (j==0) { //On regarde si on doit afficher la premiere ligne 
            System.out.print(" ");
            String trace0 = " ";
            for (int i=0; i<table.get(0).size(); i++) {//On parcourt le tableau dans les colonnes 
                System.out.print(String.format("%" + 8 + "s", indexes.get(i)));//On affiche les différentes colonnes de la premiere ligne 
                String trace1 = String.format("%" + 8 + "s", indexes.get(i));
                traceExe(trace1);
            }
        }
        System.out.print("\n\n" + String.format("%-" + 7 + "s", indexes.get(j)));//On affiche les numeros des colonnes 
        String trace2 = "\n\n" + String.format("%-" + 7 + "s", indexes.get(j));
        traceExe(trace2);
    }
    
    private void affichageCircuit(ArrayList<ArrayList<Integer>> table) throws IOException{ // Cette fonction sert à afficher le tableau mais sans les colonnes et lignes supprimer par la fonction circuit                 
        System.out.println("\n\n\n");//Retours a la ligne
        String trace0 = "\n\n\n";
        traceExe(trace0);
        for (int j=0; j<table.size(); j++) {//On cree une premiere boucle pour parcourir les lignes 
            afficher1ereligneCircuit(table, j);//On affiche la premiere ligne du tableau(sans les indices supprimés, mais aussi les premiers caractere de chaque ligne 
            for(int i=0; i<table.get(j).size(); i++) {//On cree une deuxieme bouble pour parcourir les colonnes
                if (table.get(j).get(i) != NULLE){//Si la case est differente de nulle, on affiche un 1
                    System.out.print(String.format("%-" + 8 + "s", "1"));
                    String trace1 = String.format("%-" + 8 + "s", "1");
                    traceExe(trace1);
                }else{ //Sinon on affiche un - 
                    System.out.print(String.format("%-" + 8 + "s", "-"));
                    String trace2 = String.format("%-" + 8 + "s", "-");
                    traceExe(trace2);
                }
            }
        }
        System.out.println("\n\n");//Retours a la ligne 
        String trace3 = "\n\n";
        traceExe(trace3);
    }
    
    private boolean circuit(ArrayList<ArrayList<Integer>> table) throws IOException {// Cette fonction sert à trouver la présence d'un circuit ou nous au sein du graphe
        ArrayList<ArrayList<Integer>> Gpartiel; // Création d'un tableau qui va pouvoir subir les modifications apporté au cours du processus de recherche de circuit
        Gpartiel = new ArrayList<ArrayList<Integer>>();
        
        for(int index = 0; index < table.size(); index++) // Copie du tableau initial vers le tableau partiel qui pourra subir les modifications sans affecter le tableau initial
            Gpartiel.add(new ArrayList<Integer>(table.get(index)));
        
        indexes.clear(); 
        
        for(int i = 0; i < Gpartiel.size() ; i++)
            indexes.add(i); // Ajout de tous les indexes du tableau initial dans le tableau indexes en attribut de la fonction de 0 à Gpartiel.size()-1        
        
        ArrayList<Integer> PtEstock = new ArrayList<Integer>(); 
        ArrayList<Integer> PtSstock = new ArrayList<Integer>();
        
        
        ArrayList<Integer> PtE = null;  // Déclaration d'une liste de point d'entrée
        PtE = new ArrayList<Integer>();
        ArrayList<Integer> PtS = null; // Déclaration d'une liste de point de sortie
        PtS = new ArrayList<Integer>();
        
        do{
            PtE.clear();
            for(int i = 0 ; i < Gpartiel.size() ; i++){
                for(int j = 0 ; j < Gpartiel.size() ; j++){
                    if(demidegreInt(Gpartiel,j) == 0){  // Cette condition permet de savoir si une collonne est vide ou non
                        PtE.add(j); // Si elle est vide l'indice de la colonne devient un point d'entrée et ainsi nous le stockons dans la liste de point d'entrée 
                    } 
                }
            }
            
            exist(PtE); // On supprime les occurences rencontré dans la liste de points d'entrées
           
            Collections.sort(PtE); // On ordonne la liste de point d'entrée par ordre croissant
           
            ArrayList<Integer> copieE  = new ArrayList<Integer>(PtE); // Stockage de la liste de points d'entrés car nous allons effectuer un décalage à gauche dans la liste initiale

            ArrayList<Integer> PtEAffichage = new ArrayList<Integer>(); // Permet l'affichage des points d'entrés
            
            // Supression des points d'entrés dans le tableau
            for(int i = 0 ; i < PtE.size(); i++){ 
                indexes.remove((int)PtE.get(i));// On supprime toute les colonnes dans indexes correpondant aux indexe présent dans PtE, voir fonction affichagecircuit, affichage de la première ligne du tableau sans les colonne et les lignes supprimé 
                for(int j = 0 ; j < Gpartiel.size() ; j++){
                    Gpartiel.get(j).remove((int) PtE.get(i)); //suppression colonne
                }
                // Maintenant que nous avons supprimé les colonnes, les indices du tableau Gpartiel se sont décallés vers la gauche
                // Nous devons effectuer un décalage vers la gauche aussi dans la liste de points d'entrées
                for(int k = i ; k < PtE.size() ; k++){
                    PtE.set(k,  PtE.get(k) - 1);
                }
            }

            
        
            for(int i = 0 ; i < copieE.size(); i++){ // nous créons une liste copie qui stocke la liste de point d'entrée, afin de décaler les indices des points d'entrées
                Gpartiel.remove((int)copieE.get(i)); // suppression lignes
                // Ici nous procédons de la même manière que pour la suppression des colonnes 
                 for(int k = i ; k < copieE.size() ; k++){ 
                    copieE.set(k,  copieE.get(k) - 1);   
                }
            }

            for(int i = 0; i < table.size(); i++){
                // Le but est de rajouter les points d'entrée actuel à PtEAffichage
                // Sachant que tous les points d'entrés sont supprimés des indexes du tableau
                // Et qu'après cette condition PtEstock contiendra le points d'entré supprimé
                // Nous ajoutons donc à l'affichage des points d'entrés toutes les valeurs qui ne sont pas présentent dans les indexes du tableau et pas présentent dans PtEstock
                // ici PtEstock sert de sécurité afin de ne pas ajouter à l'affichage les anciens points d'entrés
                if(!indexes.contains(i) && !PtEstock.contains(i)){ 
                    PtEAffichage.add(i);
                    PtEstock.add(i);
                }
            }
            
            if(!PtEAffichage.isEmpty()){ // Affichage des Points d'entrés à supprimer si la liste n'est pas vide
                System.out.print("Point d'entrées : "+ PtEAffichage + "\n"); 
                String trace0 = "Point d'entrées : "+ PtEAffichage + "\n";
                traceExe(trace0);
                System.out.print("Suppression des points d'entrés. \n \n");
                String trace1 = "Suppression des points d'entrés. \n \n";
                traceExe(trace1);
                System.out.print("\n\n\n\n\n");
                String trace2 = "\n\n\n\n\n";
                traceExe(trace2);
                affichageCircuit(Gpartiel);
            }
        }while(!PtE.isEmpty());
       
        // Maintenant que nous avons supprimer tous les points d'entrés existants, nous devons maintenant supprimer les points de sorties existants
        
        do{
            PtS.clear();
            for(int i = 0 ; i < Gpartiel.size() ; i++){
                for(int j = 0 ; j < Gpartiel.size() ; j++){
                    if(demidegreExt(Gpartiel,j) == 0){ // Cette condition permet de savoir si une ligne est vide ou non
                        PtS.add(j); // Si elle est vide l'indice de la ligne devient un point de sortie et ainsi nous le stockons dans la liste de point de sortie
                    } 
                }  
            }
            exist(PtS); // On supprime les occurences rencontré dans la liste de points de sorties
            
            Collections.sort(PtS); // On ordonne la liste de point de sortie par ordre croissant
            
            ArrayList<Integer> copieS = new ArrayList<Integer>(PtS); // Stockage de la liste de point de sortie
            ArrayList<Integer> PtSAffichage = new ArrayList<Integer>();

            // Supression des points de sortie dans le tableau 
            
            for(int i = 0 ; i < PtS.size(); i++){ // Même logique que pour les points d'entrés
                indexes.remove((int)PtS.get(i));
                for(int j = 0 ; j < Gpartiel.size() ; j++){
                    Gpartiel.get(j).remove((int) PtS.get(i));
                }
                for(int k = i ; k < PtS.size() ; k++){
                    PtS.set(k,  PtS.get(k) - 1);
                }
            }
        
            for(int i = 0; i < table.size(); i++){
                // Le but est de rajouter les points d'entrée actuel à PtEAffichage
                // Lors de la suppression des points d'entrés, leurs indices ont été supprimé du tableau
                // Par conséquence au premier tour de boucle, les points d'entrée vont aussi être affiché au premier tour de boucle
                // C'est pour cela que la condition !PtEstock.contains(i) est importante
                // PtEstock contient tous les points d'entrés supprimé, donc nous ajoutons à l'affichage des points de sorties que les chiffres pas présent dans la liste de point d'entré. 
                if(!indexes.contains(i) && !PtSstock.contains(i) && !PtEstock.contains(i)){ 
                    PtSAffichage.add(i);
                    PtSstock.add(i);
                }
            }

            for(int i = 0 ; i < copieS.size(); i++){ // Même procédé que pour la suppression des points d'entrés
                                                    // nous parcourons une liste copie qui stocke la liste de point de sortie, puuis nous décalons les indices des points de sortie
                Gpartiel.remove((int)copieS.get(i));
                 for(int k = i ; k < copieS.size() ; k++){
                    copieS.set(k,  copieS.get(k) - 1);
                }
            }
          
            if(!PtSAffichage.isEmpty() && !Gpartiel.isEmpty()){ // Affichage des Points d'entrés à supprimer si la liste n'est pas vide
                System.out.print("Point de sortie : "+ PtSAffichage + "\n"); 
                String trace0 = "Point d'entrées : "+ PtSAffichage + "\n";
                traceExe(trace0);
                System.out.print("Suppression des points de sorties. \n \n");
                String trace1 = "Suppression des points de sorties. \n \n";
                traceExe(trace1);
                affichageCircuit(Gpartiel);
            }

        }while(!PtS.isEmpty());
        
       
          if(Gpartiel.isEmpty()){
              System.out.print("Le Graphe ne contient pas de circuit.\n");
              String trace = "Le Graphe ne contient pas de circuit.\n";
              traceExe(trace);
              return false;
          }
          else{
              System.out.print("Il n'y a plus de point d'entré et de sortie à supprimer. Le graphe contient donc au moins un circuit.\n");
              String trace = "Il n'y a plus de point d'entré et de sortie à supprimer. Le graphe contient donc au moins un circuit.\n";
              traceExe(trace);
              return true;}
          
          
    } 
   
    private boolean verifcircuit(ArrayList<ArrayList<Integer>> table){ // Cette fonction sert à trouver la présence d'un circuit ou nous au sein du graphe
                                                                      //Cette fonction n'est pas nécessaire, elle nous permet de faire la même chose qu'avec la fonction
                                                                      // circuit, mais sans les affichages afin de ne pas encombrer la console d'affichage inutil pour la determination des rangs
        ArrayList<ArrayList<Integer>> Gpartiel; // Création d'un tableau qui va pouvoir subir les modifications apporté au cours du processus de recherche de circuit
        Gpartiel = new ArrayList<ArrayList<Integer>>();
        
        for(int index = 0; index < table.size(); index++) // Copie du tableau initial vers le tableau partiel qui pourra subir les modifications sans affecter le tableau initial
            Gpartiel.add(new ArrayList<Integer>(table.get(index)));
        
        indexes.clear(); 
        
        for(int i = 0; i < Gpartiel.size() ; i++)
            indexes.add(i); // Ajout de tous les indexes du tableau initial dans le tableau indexes en attribut de la fonction de 0 à Gpartiel.size()-1        
        
        ArrayList<Integer> PtEstock = new ArrayList<Integer>(); 
        ArrayList<Integer> PtSstock = new ArrayList<Integer>();
        
        ArrayList<Integer> PtE = null;  // Déclaration d'une liste de point d'entrée
        PtE = new ArrayList<Integer>();
        ArrayList<Integer> PtS = null; // Déclaration d'une liste de point de sortie
        PtS = new ArrayList<Integer>();
        
        do{
            PtE.clear();
            for(int i = 0 ; i < Gpartiel.size() ; i++){
                for(int j = 0 ; j < Gpartiel.size() ; j++){
                    if(demidegreInt(Gpartiel,j) == 0){  // Cette condition permet de savoir si une collonne est vide ou non
                        PtE.add(j); // Si elle est vide l'indice de la colonne devient un point d'entrée et ainsi nous le stockons dans la liste de point d'entrée 
                    } 
                }
            }
            exist(PtE); // On supprime les occurences rencontré dans la liste de points d'entrées
           
            Collections.sort(PtE); // On ordonne la liste de point d'entrée par ordre croissant
           
            ArrayList<Integer> copieE  = new ArrayList<Integer>(PtE); // Stockage de la liste de points d'entrés car nous allons effectuer un décalage à gauche dans la liste initiale

            ArrayList<Integer> PtEAffichage = new ArrayList<Integer>(); // Permet l'affichage des points d'entrés
            
            // Supression des points d'entrés dans le tableau
            for(int i = 0 ; i < PtE.size(); i++){ 
                indexes.remove((int)PtE.get(i));// On supprime toute les colonnes dans indexes correpondant aux indexe présent dans PtE, voir fonction affichagecircuit, affichage de la première ligne du tableau sans les colonne et les lignes supprimé 
                for(int j = 0 ; j < Gpartiel.size() ; j++){
                    Gpartiel.get(j).remove((int) PtE.get(i)); //suppression colonne
                }
                // Maintenant que nous avons supprimé les colonnes, les indices du tableau Gpartiel se sont décallés vers la gauche
                // Nous devons effectuer un décalage vers la gauche aussi dans la liste de points d'entrées
                for(int k = i ; k < PtE.size() ; k++){
                    PtE.set(k,  PtE.get(k) - 1);
                }
            }

            
        
            for(int i = 0 ; i < copieE.size(); i++){ // nous créons une liste copie qui stocke la liste de point d'entrée, afin de décaler les indices des points d'entrées
                Gpartiel.remove((int)copieE.get(i)); // suppression lignes
                // Ici nous procédons de la même manière que pour la suppression des colonnes 
                 for(int k = i ; k < copieE.size() ; k++){ 
                    copieE.set(k,  copieE.get(k) - 1);   
                }
            }

            for(int i = 0; i < table.size(); i++){
                // Le but est de rajouter les points d'entrée actuel à PtEAffichage
                // Sachant que tous les points d'entrés sont supprimés des indexes du tableau
                // Et qu'après cette condition PtEstock contiendra le points d'entré supprimé
                // Nous ajoutons donc à l'affichage des points d'entrés toutes les valeurs qui ne sont pas présentent dans les indexes du tableau et pas présentent dans PtEstock
                // ici PtEstock sert de sécurité afin de ne pas ajouter à l'affichage les anciens points d'entrés
                if(!indexes.contains(i) && !PtEstock.contains(i)){ 
                    PtEAffichage.add(i);
                    PtEstock.add(i);
                }
            }
            
        }while(!PtE.isEmpty());
       
        // Maintenant que nous avons supprimer tous les points d'entrés existants, nous devons maintenant supprimer les points de sorties existants
        
        do{
            PtS.clear();
            for(int i = 0 ; i < Gpartiel.size() ; i++){
                for(int j = 0 ; j < Gpartiel.size() ; j++){
                    if(demidegreExt(Gpartiel,j) == 0){ // Cette condition permet de savoir si une ligne est vide ou non
                        PtS.add(j); // Si elle est vide l'indice de la ligne devient un point de sortie et ainsi nous le stockons dans la liste de point de sortie
                    } 
                }  
            }
            exist(PtS); // On supprime les occurences rencontré dans la liste de points de sorties
            
            Collections.sort(PtS); // On ordonne la liste de point de sortie par ordre croissant
            
            ArrayList<Integer> copieS = new ArrayList<Integer>(PtS); // Stockage de la liste de point de sortie
            ArrayList<Integer> PtSAffichage = new ArrayList<Integer>();

            // Supression des points de sortie dans le tableau 
            
            for(int i = 0 ; i < PtS.size(); i++){ // Même logique que pour les points d'entrés
                indexes.remove((int)PtS.get(i));
                for(int j = 0 ; j < Gpartiel.size() ; j++){
                    Gpartiel.get(j).remove((int) PtS.get(i));
                }
                for(int k = i ; k < PtS.size() ; k++){
                    PtS.set(k,  PtS.get(k) - 1);
                }
            }
        
            for(int i = 0; i < table.size(); i++){
                // Le but est de rajouter les points d'entrée actuel à PtEAffichage
                // Lors de la suppression des points d'entrés, leurs indices ont été supprimé du tableau
                // Par conséquence au premier tour de boucle, les points d'entrée vont aussi être affiché au premier tour de boucle
                // C'est pour cela que la condition !PtEstock.contains(i) est importante
                // PtEstock contient tous les points d'entrés supprimé, donc nous ajoutons à l'affichage des points de sorties que les chiffres pas présent dans la liste de point d'entré. 
                if(!indexes.contains(i) && !PtSstock.contains(i) && !PtEstock.contains(i)){ 
                    PtSAffichage.add(i);
                    PtSstock.add(i);
                }
            }

            for(int i = 0 ; i < copieS.size(); i++){ // Même procédé que pour la suppression des points d'entrés
                                                    // nous parcourons une liste copie qui stocke la liste de point de sortie, puuis nous décalons les indices des points de sortie
                Gpartiel.remove((int)copieS.get(i));
                 for(int k = i ; k < copieS.size() ; k++){
                    copieS.set(k,  copieS.get(k) - 1);
                }
            }
          
        }while(!PtS.isEmpty());
        
       
          if(Gpartiel.isEmpty()){
              return false;
          }
          else{
              return true;}
          
    }
    
    private void rang(ArrayList<ArrayList<Integer>> table) throws IOException{
        
        ArrayList<Integer> PtE = null;  // Déclaration d'une liste de point d'entrée
        PtE = new ArrayList<Integer>();
        
        ArrayList<Integer> rang = null; // Déclaration d'une liste qui contiendra à chaque tour de boucle le rang des points d'entrés supprimés
        rang = new ArrayList<Integer>();
        
        indexesRang.clear(); 
            
        for(int i = 0; i < table.size() ; i++)
             indexesRang.add(i);
        
        int compteur = 0;
        
        ArrayList<Integer> PtEstock = new ArrayList<Integer>(); 
       
        // Tout d'abord afin de déterminer les rangs de chaque sommet du graphe nous devons vérifier si le graphe contient un circuit ou non
        // Le graphe ne doit pas contenir de circuit si nous voulons être capable d'effectuer le calcul du rang
        
        if(verifcircuit(table) == false){
            
            System.out.print("\n\n----------------------------RANG DES SOMMETS----------------------------\n\n\n");
            String trace0 = "\n\n----------------------------RANG DES SOMMETS----------------------------\n\n\n";
            traceExe(trace0);
            ArrayList<ArrayList<Integer>> Gpartiel; // Création d'un tableau qui va pouvoir subir les modifications apporté au cours du processus de recherche de circuit
            Gpartiel = new ArrayList<ArrayList<Integer>>();
            
            for(int index = 0; index < table.size(); index++) { // Copie du tableau initial vers le tableau partiel qui pourra subir les modifications sans affecter le tableau initial
                Gpartiel.add(new ArrayList<Integer>(table.get(index)));
            }
            
            indexes.clear();
            
            for(int i = 0; i < Gpartiel.size() ; i++)
                indexes.add(i); // Ajout de tous les indexes du tableau initial dans le tableau indexes en attribut de la fonction de 0 à Gpartiel.size()-1        
            
            System.out.print("Nous pouvons ainsi déterminer les rangs de chaque sommet. \n");
            String trace1 = "Nous pouvons ainsi déterminer les rangs de chaque sommet. \n";
            traceExe(trace1);
            
            affichageCircuit(Gpartiel);
            
            
            do{
                PtE.clear();
                rang.clear();
               
                for(int i = 0 ; i < Gpartiel.size() ; i++){
                    for(int j = 0 ; j < Gpartiel.size() ; j++){
                        if(demidegreInt(Gpartiel,j) == 0){  // Cette condition permet de savoir si une collonne est vide ou non
                            PtE.add(j); // Si elle est vide l'indice de la colonne devient un point d'entrée et ainsi nous le stockons dans la liste de point d'entrée 
                            rang.add(compteur); // On ajoute la valeur du compteur à la liste de rang
                        } 
                    }
                }
                compteur += 1; // on incrémente maitenant le compteur de 1 pour avoir la bonne valeur du rang lors de la prochaine itération
                
                exist(PtE); // On supprime les occurences rencontré dans la liste de points d'entrées
                exist(rang);// On supprime les occurences rencontré dans la liste de rang
                
                Collections.sort(PtE); // On ordonne la liste de point d'entrée par ordre croissant
                Collections.sort(rang); // On ordonne la liste par ordre croissant
                
                ArrayList<Integer> copieE  = new ArrayList<Integer>(PtE); // Stockage de la liste de points d'entrés car nous allons effectuer un décalage à gauche dans la liste initiale

                ArrayList<Integer> PtEAffichage = new ArrayList<Integer>(); // Permet l'affichage des points d'entrés
                
               
                // Supression des points d'entrés dans le tableau
                for(int i = 0 ; i < PtE.size(); i++){ 
                    indexes.remove((int)PtE.get(i));// On supprime toute les colonnes dans indexes correpondant aux indexe présent dans PtE, voir fonction affichagecircuit, affichage de la première ligne du tableau sans les colonne et les lignes supprimé 
                    for(int j = 0 ; j < Gpartiel.size() ; j++){
                        Gpartiel.get(j).remove((int) PtE.get(i)); //suppression colonne
                    }
                    // Maintenant que nous avons supprimé les colonnes, les indices du tableau Gpartiel se sont décallés vers la gauche
                    // Nous devons effectuer un décalage vers la gauche aussi dans la liste de points d'entrées
                    for(int k = i ; k < PtE.size() ; k++){
                        PtE.set(k,  PtE.get(k) - 1);
                    }
                }

                for(int i = 0 ; i < copieE.size(); i++){ // nous créons une liste copie qui stocke la liste de point d'entrée, afin de décaler les indices des points d'entrées
                    Gpartiel.remove((int)copieE.get(i)); // suppression lignes
                    // Ici nous procédons de la même manière que pour la suppression des colonnes 
                     for(int k = i ; k < copieE.size() ; k++){ 
                        copieE.set(k,  copieE.get(k) - 1);   
                    }
                }

                for(int i = 0; i < table.size(); i++){
                    // Le but est de rajouter les points d'entrée actuel à PtEAffichage
                    // Sachant que tous les points d'entrés sont supprimés des indexes du tableau
                    // Et qu'après cette condition PtEstock contiendra le points d'entré supprimé
                    // Nous ajoutons donc à l'affichage des points d'entrés toutes les valeurs qui ne sont pas présentent dans les indexes du tableau et pas présentent dans PtEstock
                    // ici PtEstock sert de sécurité afin de ne pas ajouter à l'affichage les anciens points d'entrés
                    if(!indexes.contains(i) && !PtEstock.contains(i)){ 
                        PtEAffichage.add(i);
                        PtEstock.add(i);
                    }
                }
                
                
                for(int i = 0 ; i < table.size(); i++){ // On parcours les indices du tableau initial de 0 à table.size() - 1
                    // PtEAffichage stock les points d'entrés à chaques itérations, et indexesRang stock les rangs de chaque indices du tableau
                    // La condition ci-dessous sert donc à modifier les rangs de chaques indices 
                    if(PtEAffichage.contains(i) && indexesRang.contains(i)){ 
                        indexesRang.set(i, compteur - 1);
                    }
                }
                
                if(!PtEAffichage.isEmpty()){ // Affichage des Points d'entrés à supprimer si la liste n'est pas vide
                    System.out.print("Point d'entrées : "+ PtEAffichage + "\n"); 
                    System.out.print("Rang : "+ rang + "\n"); 
                    System.out.print("\n\n\n");
                    String trace2 = "Point d'entrées : "+ PtEAffichage + "\n";
                    String trace3 = "Rang : "+ rang + "\n";
                    String trace4 = "\n\n\n";
                    traceExe(trace2);
                    traceExe(trace3);
                    traceExe(trace4);
                    affichageCircuit(Gpartiel);  
                }
            }while(!PtE.isEmpty());
            
          
        System.out.print("Sommet: "); // Affichage de tous les sommets 
        String trace5 = "Sommet: ";
        traceExe(trace5);
        for(int i = 0 ; i < table.size(); i++){
            System.out.print(i);
            String trace6 = String.format("%" + 8 + "s", i);
            traceExe(trace6);
            System.out.print(" ");
            String trace7 = " ";
            traceExe(trace7);
        }
          
          
        System.out.print("\nRang  : "); // Affichage des rangs des sommets
        String trace8 = "\nRang  : ";
        traceExe(trace8);
        for(int i = 0 ; i < table.size(); i++){
            System.out.print(indexesRang.get(i));
            String trace9 = String.format("%" + 8 + "s", indexesRang.get(i));
            traceExe(trace9);
            System.out.print(" ");
            String trace7 = " ";
            traceExe(trace7);
            
        }
        System.out.print("\n");
        String trace10 = "\n";
        traceExe(trace10);
        
        }
    }
    
    private boolean valeurNegative(ArrayList<ArrayList<Integer>> table) { //Cette fonction sert a detecter si un graphe à des arcs negatifs 
        for (ArrayList<Integer> rows: table) { //On parcourt donc le tableau sur les lignes 
            for (Integer e: rows) { //et sur les colonnes 
                if (e<0) //Si on trouve une valeur negative on retourne faux (false)
                    return false;
            }
        }
        return true; //Si on ne trouve pas de valeur negative on retourne vrai (true)
    }
    
    private boolean pointEntre(ArrayList<ArrayList<Integer>> table){
        
        ArrayList<Integer> PtE = null;  // Déclaration d'une liste de point d'entrée
        PtE = new ArrayList<Integer>();
       
        
        for(int i = 0 ; i < table.size() ; i++){
            for(int j = 0 ; j < table.size() ; j++){
                if(demidegreInt(table,j) == 0){  // Cette condition permet de savoir si une collonne est vide ou non
                    PtE.add(j); // Si elle est vide l'indice de la colonne devient un point d'entrée et ainsi nous le stockons dans la liste de point d'entrée 
                } 
            }
        }
        exist(PtE); // On supprime les occurences rencontré dans la liste de points d'entrées
           
        Collections.sort(PtE); // On ordonne la liste de point d'entrée par ordre croissant
            
        if(PtE.size() > 0){
            return true;
        }
        else
        return false;    
    }
    
    private boolean pointSortie(ArrayList<ArrayList<Integer>> table){
        ArrayList<Integer> PtS = new ArrayList<Integer>();  // Déclaration d'une liste de point d'entrée
        for(int i = 0 ; i < table.size() ; i++){
            for(int j = 0 ; j < table.size() ; j++){
                if(demidegreExt(table,j) == 0){  // Cette condition permet de savoir si une collonne est vide ou non
                    PtS.add(j); // Si elle est vide l'indice de la colonne devient un point d'entrée et ainsi nous le stockons dans la liste de point d'entrée 
                } 
            }
        }
        exist(PtS); // On supprime les occurences rencontré dans la liste de points d'entrées
           
        Collections.sort(PtS); // On ordonne la liste de point d'entrée par ordre croissant
            
        if(PtS.size() > 0){
            return true;
        }
        else
        return false;  
        
    }
    
    private boolean valeurArc(ArrayList<ArrayList<Integer>> table){ // Cette fonction sert vérifier pour tous les arcs incidents vers l'extérieur à un sommet ont une valeurs identiques
        
        for(int i = 0 ; i < table.size() ; i++){
            for(int j = 0; j < table.size() ; j++){
                if(table.get(i).get(0) != table.get(i).get(j) && (table.get(i).get(0) != NULLE && table.get(i).get(j) != NULLE))
                return false;
            }
        }
        
        return true;
    }
    
    private boolean verificationOrdo(ArrayList<ArrayList<Integer>> table) throws IOException{ // Cette fonction permet de verifier si le graphe est un graphe d'ordonnancement "correct"
        boolean statement = true;
        if(valeurNegative(table)){
            if(!verifcircuit(table)){
                if(pointEntre(table)){
                    if(pointSortie(table)){
                        if(valeurArc(table)){
                            statement = true;
                            System.out.print("Le graphe est un graphe d'ordonnancement \"correct\"");
                            String trace0 = "Le graphe est un graphe d'ordonnancement \"correct\"";
                            traceExe(trace0);
                        }
                        else{
                            System.out.print("Le graphe n'est pas un graphe d'ordonnancement \"correct\"");
                            String trace1 = "Le graphe n'est pas un graphe d'ordonnancement \"correct\"";
                            traceExe(trace1);
                            statement = false;}
                    }
                    else
                        statement = false;
                }
                else
                    statement = false;
            }
            else statement = false;
        }
        else
        statement = false;
        
        return statement;
    }
    
    private void traceExe(String trace) throws IOException {//Cette fonction permet d'ecrire les differentes etapes de l agorithme (dans une fichier texte) 
       
        PrintWriter out = new PrintWriter(new FileWriter("src/projettheoriegraphe/t"+numeroFichier+".txt", true), true);
        out.write(trace);
        out.close();  
      
        out.close();
    }

    public static void main(String[] args) throws IOException {
        ProjetTheorieGraphe projetGraphes = new ProjetTheorieGraphe(); //On cree notre class 
        ArrayList<ArrayList<Integer>> table;//On cree tableau pour stocker
        Scanner sc = new Scanner(System.in); //On initialise le scanner pour la saisie console
        String choix = "Y";
       
        while ("Y".equals(choix)) { //On cree une boucle pour recommencer l'algorithme si on veut 
            table = projetGraphes.ouverture(); //Appelle de la fonction pour lire les fichiers textes et stocker dans le tableau 
            projetGraphes.completion(table);//Appelle de la fonction pour creer les differentes connections
            System.out.print("Voici la matrice des valeurs correpondantes : ");
            String trace0 = "Voici la matrice des valeurs correpondantes : ";
            projetGraphes.traceExe(trace0);
            String tracea = "\n";
            projetGraphes.traceExe(tracea);
            projetGraphes.MatriceValeurs(projetGraphes.graphe);//On affiche la matrice de valeur du graphe 
            projetGraphes.traceExe(tracea);
            System.out.print("Voici la matrice adjacente correpondantes : ");
            String trace1 = "Voici la matrice adjacente correpondantes : ";
            projetGraphes.traceExe(trace1);
            projetGraphes.traceExe(tracea);
            projetGraphes.MatriceAdjacence(projetGraphes.graphe);//On affiche la matrice d'ajacence du graphe 
            System.out.print("\n\n");
            String trace2 = "\n\n";
            projetGraphes.traceExe(trace2);
            System.out.print("\n\n----------------------------CIRCUIT----------------------------\n\n\n");
            String trace3 = "\n\n----------------------------CIRCUIT----------------------------\n\n\n";
            projetGraphes.traceExe(trace3);
            System.out.print("Nous allons mainenant vérifier la présence d'un circuit au sein du graphe : \n\n ");
            String trace4 = "Nous allons mainenant vérifier la présence d'un circuit au sein du graphe : \n\n ";
            projetGraphes.traceExe(trace4);
            projetGraphes.MatriceAdjacence(projetGraphes.graphe);
            projetGraphes.circuit(projetGraphes.graphe);
            projetGraphes.rang(projetGraphes.graphe);
            System.out.print("\n");
            String trace5 = "\n";
            projetGraphes.traceExe(trace5);
            projetGraphes.verificationOrdo(projetGraphes.graphe);
            System.out.print("\n");
            projetGraphes.traceExe(trace5);
            System.out.print("Voulez-vous continuer ? (Y ou N) \n ");
            choix = sc.nextLine();
        }
    }
}