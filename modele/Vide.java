package modele;

// Classe permettant de representer le vide
public class Vide extends Case {
    
    /** Le constructeur fait appel à celui de case avec comme parametre False */
    public Vide() {
        super(false);
    }

    /** Le vide n'a jamais de contenu
     * @return Faux car le vide n'a aucun contenu
     */
    public boolean contientCaisse() {
        return false;
    }

    /** Le vide n'a jamais de contenu
     * @return Faux car le vide n'a aucun contenu
     */
    public boolean contientRobot() {
        return false;
    }

    /** Une case vide ne peut pas être cible
     * @return Faux car elle ne peut être cible
     */
    public boolean estCible() {
        return false;
    }

    /** Methode spéciale pour le sol
     * @param contenu le nouveau contenu
     */
    public void setContenu(String contenu) {}

    /** Permet d'avoir une representation String du vide
     * @return le string representant le vide
     */
    public String toString() {
        return Case.VIDE;
    }
}
