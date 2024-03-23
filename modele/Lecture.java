package modele;

import java.io.*;
import java.util.*;


// Classe permettant de lire un fichier map
public class Lecture {
    // Attributs matrice
    private List<List<String>> matrice;
    // Attribut pour le nombre de sol cible
    private int nbCible;

    /** Le constructeur initialise la matrice
     * @param nomMap le nom du fichier map à lire
     */
    public Lecture(String nomMap) {
        this.nbCible = 0;
        this.matrice = initMatrice(nomMap);
    }

    /** Permet de créer la matrice à l'aide d'un File et d'un Scanner
     * @param nomMap le nom du fichier map à lire
     * @return la matrice issue de la map
     */
    private List<List<String>> initMatrice(String nomMap) {
        List<List<String>> tmp = new ArrayList<>();
        try {
            // On utilise gerRessourceAsStream parceque on transfrome en fichier .jar
            Scanner lecteur = new Scanner(new BufferedReader(new InputStreamReader(Lecture.class.getResourceAsStream(nomMap))));
            String text, c;
            List<String> ligne;

            // Tant que le fichier a une prochaine ligne
            while (lecteur.hasNextLine()) {
                
                // Il crée une liste et ajoute les differents caractère de la ligne du fichier dans la liste
                text = lecteur.nextLine();
                ligne = new ArrayList<>();
                for (int i=0; i<text.length(); i++) {
                    c = String.valueOf(text.charAt(i));
                    ligne.add(c);
                    
                    // Compte le nombre de case cible en meme temps
                    if (c.equals(Case.DESTINATION) || c.equals(Case.CAISSE_DESTINATION) || c.equals(Case.ROBOT_DESTINATION)) nbCible++;
                }
                tmp.add(ligne);
            } 
            lecteur.close();
            return tmp;
        } catch (Exception e) {
            System.out.println("Le programme ne trouve pas le fichier " + nomMap + " !");
            return tmp;
        }
    }

    /** Getter de matrice
     * @return la matrice lue
     */
    public List<List<String>> getMatrice() {
        return matrice;
    }
    
    /** Getter de nombre de ligne
     * @return le nombre de ligne de la matrice
     */
    public int getNbLignes() {
        return matrice.size();
    }

    /** Getter de nombre de colonne
     * @return le nombre de colonne de la matrice
     */
    public int getNbColonnes() {
        return (! matrice.isEmpty()) ? matrice.get(0).size():0;
    }

    /** Getter de nombre de sol cible
     * @return le nombre de sol cible de la matrice
     */
    public int getNbCible() {
        return nbCible;
    }
}
