package modele;

// Classe permettant de modeliser un murs du jeu
public class Murs extends Case {
    
    /** Le constructeur fait appel à celui de case avec comme parametre False */
    public Murs() {
        super(false);
    }

    /** un mur n'a jamais de contenu
     * @return Faux car un mur n'a aucun contenu
     */
    public boolean contientCaisse() {
        return false;
    }

    /** un mur n'a jamais de contenu
     * @return Faux car un mur n'a aucun contenu
     */
    public boolean contientRobot() {
        return false;
    }

    /** un mur ne peut pas être cible
     * @return Faux car il ne peut être ciblé
     */
    public boolean estCible() {
        return false;
    }

    /** Methode spéciale pour le sol
     * @param contenu le nouveau contenu
     */
    public void setContenu(String contenu) {}

    /** Permet d'avoir une representation String du mur
     * @return le string representant le mur
     */
    public String toString() {
        return Case.MURS;
    }
}
