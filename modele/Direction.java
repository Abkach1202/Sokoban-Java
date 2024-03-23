package modele;

/** Enumération des directions de deplacement et de sens du robot */
public enum Direction {
    // Les differentes instances énuméré
    HAUT(-1, 0, 0),
    DROITE(0, 1, 1),
    BAS(1, 0, 2),
    GAUCHE(0, -1, 3);
    
    // Attribut de deplacement en ligne
    private int inc_lig;
    //Attribut de deplacement en colonne
    private int inc_col;
    //Attribut du sens du robot après deplacement
    private int sens;

    /** Le contructeur initialise les valeurs des incrémentations et du sens
     * @param inc_lig le deplacement en ligne du robot
     * @param inc_col le deplacement en colonne du robot
     * @param sens le sens du robot après deplacement
    */
    private Direction(int inc_lig, int inc_col, int sens) {
        this.inc_lig = inc_lig;
        this.inc_col = inc_col;
        this.sens = sens;
    }

    /** Getter du deplacement en ligne
     * @return la valeur de inc_lig
     */
    public int getIncLig() {
        return inc_lig;
    }

    /** Getter du deplacement en colonne
     * @return la valeur de inc_col
     */
    public int getIncCol() {
        return inc_col;
    }
    
    /** Getter du sens après deplacemenr
     * @return la valeur de sens
     */
    public int getSens() {
        return sens;
    }

    /** Permet d'afficher la couleur
     * @return un string qui represente la couleur
     */
    public String toString() {
        return "(lig : "+inc_lig+", col : "+inc_col+", sens : "+sens+")";
    }
}