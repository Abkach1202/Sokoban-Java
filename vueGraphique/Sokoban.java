package vueGraphique;

import modele.*;

// Classe permettant de jouer au jeu sokoban
public class Sokoban {
    public static void main(String[] args) {
        ModeleSokoban modeleSokoban = new ModeleSokoban();
        VueSokoban vueSokoban = new VueSokoban(modeleSokoban);
    }
}
