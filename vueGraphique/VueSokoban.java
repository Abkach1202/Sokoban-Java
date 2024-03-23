package vueGraphique;

import modele.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

// Classe permettant d'afficher le jeu Sokoban
public class VueSokoban implements KeyListener {
    
    // Constante des images
    private static final ImageIcon[] LESIMAGES = {
        new ImageIcon(VueSokoban.class.getResource("/images/mur.gif")),
        new ImageIcon(VueSokoban.class.getResource("/images/sol.gif")),
        new ImageIcon(VueSokoban.class.getResource("/images/but.gif")),
        new ImageIcon(VueSokoban.class.getResource("/images/caisse1.gif")),
        new ImageIcon(VueSokoban.class.getResource("/images/caisse2.gif")),
        new ImageIcon(VueSokoban.class.getResource("/images/haut.gif")),
        new ImageIcon(VueSokoban.class.getResource("/images/droite.gif")),
        new ImageIcon(VueSokoban.class.getResource("/images/bas.gif")),
        new ImageIcon(VueSokoban.class.getResource("/images/gauche.gif")), 
    };

    // Constante pour acceder aux images et son initialisation
    private static final Map<String, Integer> LESINDICES = initIndices();

    private static Map<String, Integer> initIndices() {
        Map<String, Integer> indice = new HashMap<>();
        indice.put(Case.MURS, 0);
        indice.put(Case.SOL, 1);
        indice.put(Case.DESTINATION, 2);
        indice.put(Case.CAISSE, 3);
        indice.put(Case.CAISSE_DESTINATION, 4);
        indice.put(Case.ROBOT, 5);
        indice.put(Case.ROBOT_DESTINATION, 5);
        return indice;
    }
    
    // Attribut this afin d'utiliser dans les classes anonymes
    private VueSokoban that = this;
    // Attribut Modèle du jeu
    private ModeleSokoban modele;
    // Attribut pour le nombre de ligne
    private int nbLigne;
    // Attribut pour le nombre de colonne 
    private int nbColonne;
    // Attribut pour la fenetre
    private JFrame fenetre;
    // Attribut pour les labels images
    private JLabel[][] lesLabels;
    // Attribut pour afficher le niveau
    private JLabel labelNiveau;
    // Attribut pour afficher le nombre de mouvement
    private JLabel labelmouvement;
    // Attributs pour contenir les labels images
    private JPanel contLabel1;
    // Attributs pour contenir les labels textuels
    private JPanel contLabel2;
    
    /** Le constructeur de la vue construit la fenetre et tous ses composants
     * @param modele le modele du jeu
     */
    public VueSokoban(ModeleSokoban modele) {
        // Initialisation des attributs
        this.modele = modele;
        this.nbLigne = modele.getNbLignes();
        this.nbColonne = modele.getNbColonnes();
        this.labelNiveau = new JLabel("Niveau du jeu : " + modele.getNiveau());
        this.labelmouvement = new JLabel("Nombre de mouvements : " + modele.getNbMouvement());

        // Création de La fenetre du jeu
        fenetre = new JFrame("Sokoban Graphique");

        // Initialisation des labels d'affichage et de leur conteneur 
        contLabel1 = new JPanel(new GridLayout(nbLigne, nbColonne));
        lesLabels = creeLabels(contLabel1);
        
        // Initialisation du conteneur des labels textuels
        contLabel2 = new JPanel(new BorderLayout());
        contLabel2.add(labelNiveau, BorderLayout.NORTH);
        contLabel2.add(labelmouvement);
        
        // Pour garder l'affichage inchangée quand on agrandit la fenetre
        contLabel1.setMaximumSize(contLabel1.getPreferredSize());
        contLabel2.setMaximumSize(new Dimension((int) contLabel1.getPreferredSize().getWidth(), (int) contLabel2.getPreferredSize().getHeight()));
        
        // Création des différents action à mettre dans la barre du menu

        // Action pour passer au niveau suivant
        Action suivant = new AbstractAction("Niveau suivant") {
            public void actionPerformed(ActionEvent e) {
                modele.niveauSuivant();
                that.metAJourVue();
            }
        };

        // Action pour passer au niveau précédent
        Action precedent = new AbstractAction("Niveau précédent") {
            public void actionPerformed(ActionEvent e) {
                modele.niveauPrecedent();
                that.metAJourVue();
            }
        };

        // Action pour recommencer la partie
        Action recommencer = new AbstractAction("Recommencer") {
            public void actionPerformed(ActionEvent e) {
                modele.reinitialiser();
                contLabel1.removeAll();
                lesLabels = creeLabels(contLabel1);
                changeLesTextes();
            }
        };

        // Action pour quitter le jeu
        Action quitter = new AbstractAction("Quitter") {
            public void actionPerformed(ActionEvent e) {
                fenetre.dispose();
            }
        };

        // Action pour avoir les informations du jeu
        Action savoir = new AbstractAction("À Savoir") {
            public void actionPerformed(ActionEvent e) {
                String titre = "À Savoir";
                String message = "Le but est de mettre toutes les caisses dans les cibles en noir.\nDeplacez le robot à l'aide des flèches de deplacement de votre clavier.\nUtilisez ESPACE pour revenir en arrière.\n\n";
                message += "Il faut savoir que ni le robot ni une caisse ne peut passer à travers un mur (en bleu).\nAussi le robot ne peut pas pousser deux caisses en un seul deplacement";
                JOptionPane.showMessageDialog(fenetre, message, titre, JOptionPane.INFORMATION_MESSAGE);
            }
        };

        // Action pour avoir les informations du jeu
        Action propos = new AbstractAction("À Propos") {
            public void actionPerformed(ActionEvent e) {
                String titre = "À propos de l'appli";
                String message = "Crée par Abdoulaye KATCHALA MELE";
                JOptionPane.showMessageDialog(fenetre, message, titre, JOptionPane.INFORMATION_MESSAGE);
            }
        };

        // Création de la barre du menu et ajout de ses composants
        JMenuBar menu = new JMenuBar();
        JMenu menuJeu = new JMenu("Jeu");
        JMenu menuAide = new JMenu("Aide");
        
        // Création des JMenuItem à ajouter dans le menu
        JMenuItem savoirMenuItem = new JMenuItem(savoir);
        JMenuItem proposMenuItem = new JMenuItem(propos);

        // Création et ajout d'accelerateur (Ctrl+?) au JMenuItem
        JMenuItem suivantMenuItem = new JMenuItem(suivant);
        suivantMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        
        JMenuItem precedentMenuItem = new JMenuItem(precedent);
        precedentMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK));
        
        JMenuItem recommencerMenuItem = new JMenuItem(recommencer);
        recommencerMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
        
        JMenuItem quitterMenuItem = new JMenuItem(quitter);
        quitterMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));
        
        // Ajout des composants dans le menu
        menuJeu.add(suivantMenuItem);
        menuJeu.add(precedentMenuItem);
        menuJeu.add(recommencerMenuItem);
        menuJeu.add(quitterMenuItem);
        menuAide.add(savoirMenuItem);
        menuAide.add(proposMenuItem);
        menu.add(menuJeu);
        menu.add(menuAide);

        // Placement des conteneurs
        Container conteneur = fenetre.getContentPane();
        conteneur.setLayout(new BoxLayout(conteneur, BoxLayout.Y_AXIS));
        conteneur.add(contLabel1);
        conteneur.add(Box.createVerticalStrut(10));
        conteneur.add(contLabel2);
        fenetre.setJMenuBar(menu);

        // Ajout des ecouteurs d'évènement de clic
        fenetre.addKeyListener(this);

        // Calcul de la position de la fenêtre pour la centrer
        fenetre.pack();
        Dimension dimensionEcran = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimensionEcran.getWidth() - fenetre.getWidth()) / 2);
        int y = (int) ((dimensionEcran.getHeight() - fenetre.getHeight()) / 2);
        fenetre.setLocation(x, y);

        // Les dernières configurations sur la fenetre
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setVisible(true);
    }

    /** Permet de changer l'image correspondant à labe 
     * @param nature le string representant la nature de la case
     * @param label le label dont on va changer l'image
    */
    private void metImage(JLabel label, String nature) {
        if (LESINDICES.containsKey(nature)) {
            int indice = LESINDICES.get(nature);
            if (nature == Case.ROBOT || nature == Case.ROBOT_DESTINATION) indice += modele.getRobotSens();
            label.setIcon(LESIMAGES[indice]);
        }
    }

    /** Permet de creer et ajouter les labels du jeu dans le conteneur
     * @param contLabel le conteneur qui va contenir les labels
     * @return une matrice de JLabel pour garder les labels
     */
    private JLabel[][] creeLabels(Container contLabel) {
        // Initialisation des variables à utiliser
        JLabel[][] labels = new JLabel[nbLigne][nbColonne];
        JLabel label;
        String nature;

        // Création des labels et ajout dans le conteneur
        for (int i=0; i<nbLigne; i++) {
            for (int j=0; j<nbColonne; j++) {
                label = new JLabel();
                nature = modele.getString(i, j);
                metImage(label, nature);
                labels[i][j] = label;
                contLabel.add(label);
            }
        }
        return labels;
    }

    /** Permet de changer l'image des labels dans la liste en paramètre
     * @param coord Liste de points contenant les coordonnées des labels
     */
    private void changeLesImages(java.util.List<Point> coord) {
        // Initialisation des valeurs à utiliser
        int lig, col;
        String nature;

        // Pour chaque point dans coord, il recupère l'image et modifie le label
        for (Point p : coord) {
            lig = (int) p.getX();
            col = (int) p.getY();
            nature = modele.getString(lig, col);
            metImage(lesLabels[lig][col], nature);
        }
    }

    /** Permet de changer le texte des labels de niveau et de mouvements */
    private void changeLesTextes() {
        // Met à jour le niveau et le nombre de mouvement
        labelNiveau.setText("Niveau du jeu : " + modele.getNiveau());
        labelmouvement.setText("Nombre de mouvements : " + modele.getNbMouvement());
    }

    /** Permet de mettre à jour la vue après passage à un niveau */
    private void metAJourVue() {
        // Met à jour le nombre de ligne et de colonne
        nbLigne = modele.getNbLignes();
        nbColonne = modele.getNbColonnes();
        
        // Reinitialise et met à jour le conteneur des labels
        contLabel1.removeAll();
        contLabel1.setLayout(new GridLayout(nbLigne, nbColonne));
        lesLabels = creeLabels(contLabel1);
        
        // Met à jour les textes
        changeLesTextes();

        // Arrange les taille pour l'affichage
        contLabel1.setMaximumSize(contLabel1.getPreferredSize());
        contLabel2.setMaximumSize(new Dimension((int) contLabel1.getPreferredSize().getWidth(), (int) contLabel2.getPreferredSize().getHeight()));
    }

    /** Fonction permettant de cloturer la partie si elle est finie */
    private void cloturePartie() {
        // Le message à afficher au joueur
        String message = "Bravo ! Vous avez gagné la partie en " + modele.getNbMouvement() + " mouvements\n\nProchaine étape ?";
        
        // Création d'un JOptionPane pour feliciter le joueur et lui demander la prochaine etape
        String[] options = {"Niveau Précédent", "Recommencer", "Niveau Suivant"};
        int choix = JOptionPane.showOptionDialog(fenetre, message, "Gagné !", JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[2]);
        
        // Selon le choix fait par le joueur, il execute
        switch (choix) {
            case JOptionPane.YES_OPTION:
                modele.niveauPrecedent();
                that.metAJourVue();
                break;
            case JOptionPane.NO_OPTION:
                modele.reinitialiser();
                contLabel1.removeAll();
                lesLabels = creeLabels(contLabel1);
                changeLesTextes();
                break;
            case JOptionPane.CANCEL_OPTION:
                modele.niveauSuivant();
                that.metAJourVue();
                break;
        }
    }
    
    /** Invoqué quand une touche a été pressée
     * @param e le keyEvent issu du clic
     */
    public void keyPressed(KeyEvent e){
        // recupère le code de la touche cliquée
        int code = e.getKeyCode();
        java.util.List<Point> coord;
        switch (code) {
            // Si il s'agit de la flèche du Haut
            case KeyEvent.VK_UP :
                coord = modele.deplace(Direction.HAUT);
                break;
            
            // Si il s'agit de la flèche Droite
            case KeyEvent.VK_RIGHT :
                coord = modele.deplace(Direction.DROITE);
                break;
            
            // Si il s'agit de la flèche du Bas
            case KeyEvent.VK_DOWN :
                coord = modele.deplace(Direction.BAS);
                break;
            
            // Si il s'agit de la flèche du Gauche
            case KeyEvent.VK_LEFT :
                coord = modele.deplace(Direction.GAUCHE);
                break;

            // Si il s'agit de l'espace
            case KeyEvent.VK_SPACE :
                coord = modele.deplaceArriere();
                break;
            
            default :
                coord = new ArrayList<>();
        }
        changeLesImages(coord);
        changeLesTextes();
        if (modele.partieFinie()) cloturePartie();
    }
    
    /** Invoqué quand une touche imprimable a été pressée
     * @param e le keyEvent issu du clic
     */
    public void keyTyped(KeyEvent e) {}

    

    /** Invoqué quand une touche a été lachée
     * @param e le keyEvent issu du clic
     */
    public void keyReleased(KeyEvent e) {}
}
