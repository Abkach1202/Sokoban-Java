package modele;

// Classe permettant de representer le sol dans le jeu
public class Sol extends Case {
    
    // Attribut cible qui dit si il est une case destination
    private boolean cible;
    // Attribut qui indique le contenu du sol
    private String contenu;

    /** Le constructeur du sol initialise les attributs cible et contenu passés en paramètre 
     * @param cible booléen disant si le sol est ciblé ou pas
     * @param contenu String indiquant le contenu du sol
    */
    public Sol(boolean cible, String contenu) {
        super(true);
        this.cible = cible;
        this.contenu = contenu;
        
    }

    /** Le constructeur du sol initialise l'attributs cible qui sera passée en paramètre et l'attribut contenu à rien 
     * @param cible booléen disant si le sol est ciblé ou pas
    */
    public Sol(boolean cible) {
        this(cible, Case.VIDE);
    }

    /** Le constructeur du sol initialise l'attributs cible à false et l'attribut contenu qui sera passée en paramètre 
     * @param contenu String indiquant le contenu du sol
    */
    public Sol(String contenu) {
        this(false, contenu);
    }

    /** Le constructeur du sol initialise l'attributs cible à Faux et l'attribut contenu à rien 
     * @param cible booléen disant si le sol est ciblé ou pas
    */
    public Sol() {
        this(false, Case.VIDE);
    }

    /** Permet de savoir si ce sol contient une caisse ou pas
     * @return un booléen Vrai si elle contient une caisse et faux si non
     */
    public boolean contientCaisse() {
        return contenu.equals(Case.CAISSE);
    }

    /** Permet de savoir si ce sol contient le robot ou pas
     * @return un booléen Vrai si elle contient le robot et faux si non
     */
    public boolean contientRobot() {
        return contenu.equals(Case.ROBOT);
    }

    /** Permet de modifier le contenu du sol
     * @param contenu le nouveau contenu
     */
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
    
    /** Permet de savoir si ce sol est une cible ou pas
     * @return un booléen Vrai si elle est une cible et faux si non
     */
    public boolean estCible() {
        return cible;
    }

    /** Permet d'avoir une representation String du mur
     * @return le string representant le mur
     */
    public String toString() {
        if (cible) {
            if (contientCaisse()) return Case.CAISSE_DESTINATION;
            if (contientRobot()) return Case.ROBOT_DESTINATION;
            return Case.DESTINATION;
        }
        if (contientCaisse()) return Case.CAISSE;
        if (contientRobot()) return Case.ROBOT;
        return Case.SOL;
    }
}
