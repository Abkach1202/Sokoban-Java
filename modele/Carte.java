package modele;

import java.util.*;
import java.awt.Point;

// Classe permettant de representer la carte d'un niveau du jeu
public class Carte {
    // Attribut nbLigne qui nous donne le nombre de ligne de la carte
    private int nbLigne;
    // Attrinbut nbColonne qui nous donne le nombre de colonne de la carte
    private int nbColonne;
    // Attribut lesCases contenant toutes les cases de la carte
    private Case[][] lesCases;
    // Attribut robot representant le robot dans la carte
    private Robot robot;
    // Attribut pour garder les sols cibles
    private Sol[] lesCibles;

    /** Le constructeur fait appel à la classe Lecture afin de lire le fichier passé en paramètre 
     * @param nomMap le nom  de la map dont on veut la carte
    */
    public Carte(String nomMap) {
        // Lecture de la map en paramètre
        Lecture lecteur = new Lecture(nomMap);
        List<List<String>> matrice = lecteur.getMatrice();
        int cptCible = 0;

        // Initialisation de certains attributs
        this.nbLigne = lecteur.getNbLignes();
        this.nbColonne = lecteur.getNbColonnes();
        this.lesCases = new Case[nbLigne][nbColonne];
        this.lesCibles = new Sol[lecteur.getNbCible()];
        
        // Initialisation des cases et du robot
        for (int i=0; i<nbLigne; i++) {
            for (int j=0; j<nbColonne; j++) {
                lesCases[i][j] = Case.createCase(matrice.get(i).get(j));
                if (lesCases[i][j].contientRobot()) this.robot = new Robot(i, j);
                if (lesCases[i][j].estCible()) {
                    
                    // Garde les cases cibles aussi
                    this.lesCibles[cptCible] = (Sol)lesCases[i][j];
                    cptCible++;
                }
            }
        }
    }

    /** Getter de nombre de ligne
     * @return le nombre de ligne de la carte
     */
    public int getNbLignes() {
        return nbLigne;
    }

    /** Getter de nombre de colonne
     * @return le nombre de colonne de la carte
     */
    public int getNbColonnes() {
        return nbColonne;
    }

    /** Getter du sens du robot
     * @return le sens du robot
     */
    public int getRobotSens() {
        return robot.getSens();
    }

    /** Permet d'avoir la representation String d'une case de la carte
     * @param lig la ligne de la case
     * @param col la colonne de la case
     * @return un String representant la case de la carte 
     */
    public String getString(int lig, int col) {
        return lesCases[lig][col].toString();
    }

    /** Permet d'avoir une representation String de la carte du jeu
     * @return le string representant le mur
     */
     public String toString() {
        String message = "";
        for (int i=0; i<nbColonne+2; i++) {
            message += "_";
        }
        message += "\n";
        for (int i=0; i<nbLigne; i++) {
            message += "|";
            for (int j=0; j<nbColonne; j++) {
                message += lesCases[i][j].toString();
            }
            message += "|\n";
        }
        for (int i=0; i<nbColonne+2; i++) {
            message += "–";
        }
        message += "\n";
        return message;
    }

    /** Permet de savoir si le deplacement du robot vers un sens est possible ou pas
     * @param inc_lig le deplacement en ligne à tester
     * @param inc_col le deplacement en colonne à tester
     * @return Vrai si le deplacement est possible ou Faux si non
     */
    private boolean deplacementPossible(Direction direction) {
        // Initialisation des variables à utiliser
        int inc_lig = direction.getIncLig();
        int inc_col = direction.getIncCol();
        int lig = robot.getLigne() + inc_lig;
        int col = robot.getColonne() + inc_col;

        // Verifie si c'est une case et si elle est accessible
        if (lig < 0 || lig >= nbLigne || col < 0 || col >= nbColonne || ! lesCases[lig][col].estAccessible()) {
            return false;
        }

        // Verifie si la case contient une caisse
        if (lesCases[lig][col].contientCaisse()) {
            // Si oui on verifie si la caisse est deplaçable
            lig += inc_lig;
            col += inc_col;
            if (lig < 0 || lig >= nbLigne || col < 0 || col >= nbColonne || ! lesCases[lig][col].estAccessible() || lesCases[lig][col].contientCaisse()) {
                return false;
            }
        }
        return true;
    }

    /** Permet de deplacer le robot dans un sens su la carte
     * @param inc_lig le deplacement en ligne à effectuer
     * @param inc_col le deplacement en colonne à effectuer7
     * @return un liste de points représentant la liste des labels à changer dans la vue graphique
    */
    public List<Point> deplacement(Direction direction) {
        List<Point> coord = new ArrayList<>();

        // Fait le deplacement si le deplacement est possible
        if (deplacementPossible(direction)) {
            int inc_lig = direction.getIncLig();
            int inc_col = direction.getIncCol();
            int lig = robot.getLigne() + inc_lig;
            int col = robot.getColonne() + inc_col;
            
            // Deplace la caisse si il y'a une caisse
            if (lesCases[lig][col].contientCaisse()) {
                lesCases[lig+inc_lig][col+inc_col].setContenu(Case.CAISSE);
                coord.add(new Point(lig+inc_lig, col+inc_col));
            }

            // Deplace le robot
            lesCases[lig][col].setContenu(Case.ROBOT);
            lesCases[lig-inc_lig][col-inc_col].setContenu(Case.SOL);
            robot.deplaceToi(inc_lig, inc_col, direction.getSens());
            
            // Ajoute les différents cases à modifier dans la vue (Graphique)
            coord.add(new Point(lig, col));
            coord.add(new Point(lig-inc_lig, col-inc_col));
        }

        return coord;
    }

    /** Permet de deplacer le robot dans un sens su la carte
     * @param inc_lig le deplacement en ligne à effectuer
     * @param inc_col le deplacement en colonne à effectuer7
     * @return un liste de points représentant la liste des labels à changer dans la vue graphique
    */
    public List<Point> deplacementArriere(Direction direction, boolean estDeplaceCaisse) {
        // Initialisation des variables à utiliser
        List<Point> coord = new ArrayList<>();
        int inc_lig = direction.getIncLig();
        int inc_col = direction.getIncCol();
        int lig = robot.getLigne();
        int col = robot.getColonne();
        
        // Deplace le robot en arrière
        lesCases[lig][col].setContenu(Case.SOL);
        lesCases[lig-inc_lig][col-inc_col].setContenu(Case.ROBOT);
        robot.deplaceToi(-1*inc_lig, -1*inc_col, (direction.getSens()));
        
        // Ajoute les différents cases à modifier dans la vue (Graphique)
        coord.add(new Point(lig, col));
        coord.add(new Point(lig-inc_lig, col-inc_col));

        // Deplace la caisse si il y'a une caisse
        if (estDeplaceCaisse) {
            lesCases[lig+inc_lig][col+inc_col].setContenu(Case.SOL);
            lesCases[lig][col].setContenu(Case.CAISSE);
            coord.add(new Point(lig+inc_lig, col+inc_col));
        }

        return coord;
    }

    /** Permet de savoir si le joueur a gagné ou pas
     * @return Vrai si le joueur a gagné et Faux si non
     */
    public boolean gagne() {
        for (Sol sol : lesCibles) {
            if (! sol.contientCaisse()) return false;
        }
        return true;
    }
}
