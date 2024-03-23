package modele;

// Classe permettant de modeliser le robot dans le jeu
public class Robot {
    // Attributs ligne qui dit la ligne où se trouve le robot
    private int ligne;
    // Attribut colonne qui dit la colonne où se trouve le robot
    private int colonne;
    // Attribut direction qui nous dit la direction du robot
    private int sens;

    /** Le consructeur initialise la ligne et la colonne passée en paramètre, la direction est prise au hasard
     * @param ligne la ligne où il se trouve
     * @param colonne la colonne où il se trouve
     */
    public Robot(int ligne, int colonne) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.sens = 2;
    }

    /** Getter de l'abscisse du robot 
     * @return la ligne où se trouve le robot
    */
    public int getLigne() {
        return ligne;
    }

    /** Getter de l'ordonné du robot 
     * @return la colonne où se trouve le robot
    */
    public int getColonne() {
        return colonne;
    }

    /** Getter du sens du robot 
     * @return le sens du le robot
    */
    public int getSens() {
        return sens;
    }

    /** Permet au robot de se deplacer en changeant sa ligne et sa colonne 
     * @param inc_lig le deplacement en ligne que le robot va effectuer
     * @param inc_col le deplacement en colonne que le robot va effectuer
    */
    public void deplaceToi(int inc_lig, int inc_col, int sens) {
        ligne += inc_lig;
        colonne += inc_col;
        this.sens = sens;
    }
    
}
