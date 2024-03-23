package vueTexte;

import java.io.IOException;

// Classe permettant de lire une caractère
public class Outil {
    
    /** Methode static permettant de lire une carectère à partir de l'entréé
     * @return le caractère lu
     */
    public static char lireCaractere(){
        int rep = ' ', buf;
        try {
            rep = System.in.read();
            buf = rep;
            while (buf != '\n')
                buf = System.in.read();
        } catch (IOException e) {
            System.out.println("Erreur dans la lecture de carectère");
        }
        return (char) rep;
    }
}