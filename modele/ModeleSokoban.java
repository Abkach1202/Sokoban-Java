package modele;

import java.util.*;
import java.awt.Point;

// Classe permettant de modeliser le jeu Sokoban
public class ModeleSokoban {
    // Constante de nombre de niveau
    public static final int NB_NIVEAU = 10; 

    // Attribut carte qui contient la carte du jeu
    public Carte carte;
    // Attribut niveau qui indique le niveau du joueur
    private int niveau;
    // Attrinut lesMouvements qui stocke les mouvements du joueur afin de revenir en arrière
    private List<Direction> lesMouvements;
    // Attribut estDeplaceCaisse qui dit por chaque mouvement si une caisse a été deplacé pour l'utiliser quand on revient en arrière
    private List<Boolean> estDeplaceCaisse;

    /** Le constructeur du Modèle du jeu initialise les valeurs
     * @param niveau le niveau à partir de laquelle le jeu commence
     */
    public ModeleSokoban(int niveau) {
        this.niveau = niveau;
        this.carte = new Carte(nomMap());
        this.lesMouvements = new ArrayList<>();
        this.estDeplaceCaisse = new ArrayList<>();
    }

    /** Le constructeur du Modèle du jeu initialise les valeurs*/
    public ModeleSokoban() {
        this.niveau = 1;
        this.carte = new Carte(nomMap());
        this.lesMouvements = new ArrayList<>();
        this.estDeplaceCaisse = new ArrayList<>();
    }

    /** Fonction permettant d'avoir le nom du niveau passé en paramètre
     * @return le nom du niveau
     */
    private String nomMap() {
        return "/maps/map" + niveau + ".txt";
    }

    /** Getter de niveau
     * @return le niveau du jeu
     */
    public int getNiveau() {
        return niveau;
    }

    /** Getter du nombre de Mouvement
     * @return le nombre de mouvements
     */
    public int getNbMouvement() {
        return lesMouvements.size();
    }

    /** Getter de nombre de ligne
     * @return le nombre de ligne de la carte
     */
    public int getNbLignes() {
        return carte.getNbLignes();
    }

    /** Getter de nombre de colonne
     * @return le nombre de colonne de la carte
     */
    public int getNbColonnes() {
        return carte.getNbColonnes();
    }

    /** Getter du sens du robot
     * @return le sens du robot
     */
    public int getRobotSens() {
        return carte.getRobotSens();
    }

    /** Permet d'avoir la representation String d'une case de la carte
     * @param lig la ligne de la case
     * @param col la colonne de la case
     * @return un String representant la case de la carte 
     */
    public String getString(int lig, int col) {
        return carte.getString(lig, col);
    }

    /** Permet d'avoir la matrice d'affichage de la vue textuelle
     * @return la matrice de string qui represente la carte
     */
    public String toString() {
        String message = "";
        message += "\nNiveau de map : " + getNiveau() + " (" + ModeleSokoban.NB_NIVEAU + " niveaux au total)\n";
        message += "Nombre de mouvements : " + getNbMouvement() + "\n";
        message += carte.toString();
        return message;
    }

    /** Permet de faire un deplacement sur la carte
     * @param deplacement un point contenant le deplacement en ligne et en colonne du robot
     * @return une liste de case à modifier sur la vue
     */
    public List<Point> deplace(Direction direction) {
        List<Point> liste = carte.deplacement(direction);
        // Si le deplacement à eu lieu donc la liste des points n'est pas vide 
        if (! liste.isEmpty()) {
            // On garde le deplacement dans les mouvements au cas où on revient en arrière
            lesMouvements.add(direction);
            estDeplaceCaisse.add(liste.size() == 3);
        }
        return liste;
    }

    /** Permet de revenir en arrière après un deplacement sur la carte
     * @param deplacement un point contenant le deplacement en ligne et en colonne du robot
     * @return une liste de case à modifier sur la vue
     */
    public List<Point> deplaceArriere() {
        // Il deplace en arrière si la liste des mouvements précédents n'est pas vide
        if (! lesMouvements.isEmpty()) {
            return carte.deplacementArriere(lesMouvements.remove(lesMouvements.size()-1), estDeplaceCaisse.remove(estDeplaceCaisse.size()-1));
        }
        return new ArrayList<Point>();
    }

    /** Permet de recommencer une partie du jeu Sokoban avec le meme niveau 
      Elle crée une nouvelle carte et vide la liste des mouvements
    */
    public void reinitialiser() {
        carte = new Carte(nomMap());
        lesMouvements.clear();
        estDeplaceCaisse.clear();
    }

    /** Permet de passer au niveau suivant
      Elle crée une nouvelle carte et vide la liste des mouvements
     */
    public void niveauSuivant() {
        if (niveau < NB_NIVEAU) niveau++;
        reinitialiser();
    }
    
    /** Permet de passer au niveau précédent
      Elle crée une nouvelle carte et vide la liste des mouvements
     */
    public void niveauPrecedent() {
        if (niveau > 1) niveau--;
        reinitialiser();
    }

    /** Permet de savoir si la partie est finie ou pas
     * @return un Vrai si la partie est finie et Faux si non
     */
    public boolean partieFinie() {
        return carte.gagne();
    }
}
