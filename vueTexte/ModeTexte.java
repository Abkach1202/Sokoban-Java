package vueTexte;

import modele.*;

// Classe permettant de modeliser la vue textuelle du jeu Sokoban
public class ModeTexte {
    // Attribut Modèle du jeu
    private ModeleSokoban modele;

    /** Le constructeur de la vue textuelle initialise le modèle*/
    public ModeTexte() {
        this.modele = new ModeleSokoban();
    }

    /** Methode permettant de lire une carectère à partir de la classe Outil
     * @param fini Pour savoir si la partie est fini ou pas
     * @return le caractère lu
     */
    private char lireCaractere(boolean fini) {
        System.out.print("Prochaine action ? : ");
        char c = Outil.lireCaractere();
        if (! fini && ! ((c == 'z' || c == 'Z') ||
        (c == 'd' || c == 'D') ||
        (c == 's' || c == 'S') ||
        (c == 'q' || c == 'Q') ||
        (c == 'b' || c == 'B') ||
        (c == 'r' || c == 'R') ||
        (c == 'l' || c == 'L'))) {
            System.out.println("\nErreur ! Vous devez choisir entre un caractère entre 'z', 'd', 's', 'q', 'b', 'r', 'l',\n");
            return lireCaractere(fini);
        }
        if (fini && ! ((c == 'l' || c == 'L') ||
        (c == 'r' || c == 'R') ||
        (c == 'n' || c == 'N') ||
        (c == 'p' || c == 'P'))) {
            System.out.println("\nErreur ! Vous devez choisir entre un caractère entre 'n', 'p', 'r', 'l',\n");
            return lireCaractere(fini);
        }
        return c;
    }

    /** Methode permettant de traiter le caractère selectionné 
     * @param car le caractère selectionné par l'utilisateur
    */
    public void traiteCaractere(char car) {
        if (car == 'z' || car == 'Z') {
            modele.deplace(Direction.HAUT);
        } else if (car == 'd' || car == 'D') {
            modele.deplace(Direction.DROITE);
        } else if (car == 's' || car == 'S') {
            modele.deplace(Direction.BAS);
        } else if (car == 'q' || car == 'Q') {
            modele.deplace(Direction.GAUCHE);
        } else if (car == 'b' || car == 'B') {
            modele.deplaceArriere();
        } else if (car == 'r' || car == 'R') {
            modele.reinitialiser();
            System.out.println("\nOn recommence ? Bonne chance pour la nouvelle partie");
        } else if (car == 'n' || car == 'N') {
            modele.niveauSuivant();
            System.out.println("\nNiveau suivant ! Bonne chance !\n");
        } else if (car == 'p' || car == 'P') {
            modele.niveauPrecedent();
            System.out.println("\nNiveau précédent ! Bonne chance !\n");
        }
    }

    /** Methode permettant de jouer au jeu Sokoban avec la vue textuelle */
    public void jouer() {
        // Message de bienvenu
        System.out.println("\nBienvenue dans le jeu Sokoban !\n\nLe but est de mettre toutes les caisses ($) dans les cibles (.).");
        System.out.println("Il y'a 10 niveau dans le jeu, essayez de passer chaque niveau avec le moins de mouvements possibles.");
        System.out.println("Commandes : 'z' pour le haut, 'd' pour la droite, 's' pour le bas, 'q' pour la gauche, 'b' pour revenir en arrière, 'r' pour recommencer et 'l' pour quitter\nBonne Chance !");
        
        // La boucle du jeu
        System.out.println(modele);
        boolean partieFinie;
        char c = lireCaractere(false);
        while (c != 'l' && c != 'L') {
            traiteCaractere(c);
            System.out.println(modele);
            partieFinie = modele.partieFinie();
            if (partieFinie) System.out.println("Bravo vous avez gagné en "+modele.getNbMouvement()+" mouvements !\n\nAppuyez sur 'n' pour passer au niveau suivant, 'p' pour le niveau précédent, 'r' pour recommencer et 'l' pour quitter");
            c = lireCaractere(partieFinie);
        }
        System.out.println("\nÀ très vite !\n");
    }
}
