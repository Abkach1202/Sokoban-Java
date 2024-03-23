package modele;

// Classe permettant de representer un case du jeu Sokoban
public abstract class Case {
    // La representation des differentes nature de cases
    public static final String MURS = "#";
    public static final String VIDE = "/";
    public static final String SOL = " ";
    public static final String DESTINATION = ".";
    public static final String ROBOT = "@";
    public static final String CAISSE = "$";
    public static final String ROBOT_DESTINATION = "+";
    public static final String CAISSE_DESTINATION = "*";

    // Attribut Accessible qui nous dit si la case est accessible par le robot ou pas
    private boolean accessible;

    /** Le constructeur initialise le booléen 
     * @param accessible le booléen à affecter à accessible
    */
    public Case(boolean accessible) {
        this.accessible = accessible;
    }

    /** Getter de accessible, elle permet de savoir si la case est accessible par le robot
     * @return Vrai si la case est accessible et Faux si non
     */
    public boolean estAccessible() {
        return accessible;
    }

    /** Permet de savoir si la case contient une caisse ou pas spécialement pour le sol
     * @return un booléen Vrai si elle contient une caisse et faux si non
     */
    public abstract boolean contientCaisse();

    /** Permet de savoir si la case contient le robot ou pas spécialement pour le sol
     * @return un booléen Vrai si elle contient le robot et faux si non
     */
    public abstract boolean contientRobot();
    
    /** Permet de savoir si la case est une cible ou pas spécialement pour le sol
     * @return un booléen Vrai si elle est une cible et faux si non
     */
    public abstract boolean estCible();

    /** Permet de modifier le contenu du sol
     * @param contenu le nouveau contenu
     */
    public abstract void setContenu(String contenu);

    /** Methode static permettant de creer une case connaissant sa nature
     * @param nature la nature de la case
     */
    public static Case createCase(String nature) {
        switch (nature) {
            case MURS:
                return new Murs();
            case VIDE:
                return new Vide();
            case SOL:
                return new Sol();
            case DESTINATION:
                return new Sol(true);
            case ROBOT:
                return new Sol(ROBOT);
            case CAISSE:
                return new Sol(CAISSE);
            case ROBOT_DESTINATION:
                return new Sol(true, ROBOT);    
            default :
                return new Sol(true, CAISSE); 
        }
    }
}
