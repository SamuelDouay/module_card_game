package game.dutch;

import game.dutch.enums.CardValue;
import game.dutch.util.Card;
import game.dutch.util.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final String YES = "o";
    private static final String THROW = "j";
    private static final Scanner scanner = new Scanner(System.in);
    
    private static final String ASCII_TITLE = """
            ██████╗ ██╗   ██╗████████╗ ██████╗██╗  ██╗
            ██╔══██╗██║   ██║╚══██╔══╝██╔════╝██║  ██║
            ██║  ██║██║   ██║   ██║   ██║     ███████║
            ██║  ██║██║   ██║   ██║   ██║     ██╔══██║
            ██████╔╝╚██████╔╝   ██║   ╚██████╗██║  ██║
            ╚═════╝  ╚═════╝    ╚═╝    ╚═════╝╚═╝  ╚═╝
            """;
            
    private static final String CARDS_ART = """
            .------..------..------..------..------.
            |D.--. ||U.--. ||T.--. ||C.--. ||H.--. |
            | :/\\: || (\\/) || :/\\: || :/\\: || :/\\: |
            | (__) || :\\/: || (__) || :\\/: || (__) |
            | '--'D|| '--'U|| '--'T|| '--'C|| '--'H|
            `------'`------'`------'`------'`------'
            """;
        
    public static void main(String[] args) {
        logger.info("Démarrage du jeu Dutch");
        displayWelcome();
        
        Dutch dutch = new Dutch();
        dutch.initGame();
        logger.info("Initialisation du jeu terminée");

        Player player = new Player(0, dutch.discardCardToPlayers());
        logger.info("Nouveau joueur créé avec ID: 0");
        
        performInitialCardReview(player);
        gameLoop(dutch, player);
        displayGameResults(player);
        
        logger.info("Fin du jeu");
    }
    
    private static void displayWelcome() {
        System.out.println(ASCII_TITLE);
        System.out.println("\nBienvenue au jeu Dutch!\n");
        System.out.println(CARDS_ART);
        System.out.println("\nPréparez-vous à jouer!\n");
    }
    
    private static void performInitialCardReview(Player player) {
        logger.debug("Début de la revue initiale des cartes");
        System.out.println(player);
        System.out.println("Regardes une carte, tapes son index");
        int firstCardIndex = scanner.nextInt();
        System.out.println(player.lookCard(firstCardIndex));
        logger.debug("Première carte regardée à l'index: " + firstCardIndex);
        
        System.out.println("Regardes une deuxième carte, tapes son index");
        int secondCardIndex = scanner.nextInt();
        System.out.println(player.lookCard(secondCardIndex));
        logger.debug("Deuxième carte regardée à l'index: " + secondCardIndex);
    }
    
    private static void gameLoop(Dutch dutch, Player player) {
        while (!player.isDutch()) {
            logger.debug("Début d'un nouveau tour");
            displayGameState(dutch);
            
            Card pickedCard = handleCardPicking(dutch, player);
            handleCardExchange(dutch, player, pickedCard);
            handleOptionalCardThrow(dutch, player);
            
            if (checkForDutchCall()) {
                logger.info("Le joueur a crié Dutch!");
                player.shoutDutch();
            }
        }
    }
    
    private static void displayGameState(Dutch dutch) {
        int pickaxeSize = dutch.getPickaxe().size();
        System.out.println("Nombre de cartes dans la pioche : " + pickaxeSize);
        logger.debug("État de la pioche: " + pickaxeSize + " cartes restantes");
        
        if (!dutch.getDiscard().isEmpty()) {
            Card topCard = dutch.getDiscard().peek();
            System.out.println("Dernière carte de la défausse : " + topCard.lookCard());
            logger.debug("Carte au sommet de la défausse: " + topCard.lookCard());
        }
    }
    
    private static Card handleCardPicking(Dutch dutch, Player player) {
        Card pickedCard = player.pick(dutch.getPickaxe());
        logger.debug("Carte piochée: " + pickedCard.lookCard());
        System.out.println("Premier tour - Tu pioches une nouvelle carte : " + pickedCard.lookCard());
        return pickedCard;
    }
    
    private static void handleCardExchange(Dutch dutch, Player player, Card pickedCard) {
        System.out.println("Veux tu la jetter (j) ou l'échanger (e) ?");
        String choice = scanner.next();
        logger.debug("Choix du joueur pour la carte piochée: " + choice);
        
        if (choice.equals(THROW)) {
            dutch.getDiscard().push(pickedCard);
            logger.debug("Carte jetée directement: " + pickedCard.lookCard());
        } else {
            executeCardExchange(dutch, player, pickedCard);
        }
        
        handleQueenCard(dutch, player);
    }
    
    private static void executeCardExchange(Dutch dutch, Player player, Card pickedCard) {
        System.out.println(player);
        System.out.println("Avec quel carte veux - tu l'échanger ?");
        System.out.println("Tapes son index :");
        
        int exchangeIndex = scanner.nextInt();
        Card exchangedCard = player.exchangeCard(pickedCard, exchangeIndex);
        dutch.getDiscard().push(exchangedCard);
        logger.debug("Échange effectué - Carte jetée: " + exchangedCard.lookCard());
        System.out.println("Tu viens de jeter cette carte" + exchangedCard.lookCard());
    }
    
    private static void handleQueenCard(Dutch dutch, Player player) {
        if (dutch.getDiscard().peek().equals(new Card(CardValue.QUEEN, null, 0))) {
            logger.info("Reine détectée! Action spéciale activée");
            System.out.println("""
                    👑 C'est la reine !!!!!
                    Tu as le droit de regarder une carte
                    """);
            System.out.println("Regardes une carte, tapes son index");
            int cardIndex = scanner.nextInt();
            System.out.println(player.lookCard(cardIndex));
            logger.debug("Carte regardée suite à la reine: index " + cardIndex);
            
            handleOptionalCardThrow(dutch, player);
        }
    }
    
    private static void handleOptionalCardThrow(Dutch dutch, Player player) {
        System.out.println("Souhaites tu veux jeter une autre carte de ta main ? o/n");
        if (scanner.next().equals(YES)) {
            executeCardThrow(dutch, player);
        }
    }
    
    private static void executeCardThrow(Dutch dutch, Player player) {
        System.out.println("Tapes son index :");
        int index = scanner.nextInt();
        Card cardToThrow = player.discard(index);
        logger.debug("Tentative de jeter la carte: " + cardToThrow.lookCard());
        
        if (cardToThrow.equals(dutch.getDiscard().peek())) {
            dutch.getDiscard().push(cardToThrow);
            System.out.println("🎉 Bien joué !");
            logger.info("Carte correctement jetée");
        } else {
            handleIncorrectCardThrow(player, index, cardToThrow, dutch);
        }
    }
    
    private static void handleIncorrectCardThrow(Player player, int index, Card card, Dutch dutch) {
        logger.warn("Tentative incorrecte de jeter une carte");
        System.out.println("❌ Tu t'es trompé, reprends ta carte + 2 nouvelles cartes");
        player.recoveryCard(index, card);
        player.addCard(dutch.getPickaxe().pop());
        player.addCard(dutch.getPickaxe().pop());
        logger.debug("2 cartes de pénalité ajoutées au joueur");
    }
    
    private static boolean checkForDutchCall() {
        System.out.println("Veux tu crier game.dutch ? o/n");
        return scanner.next().equals(YES);
    }
    
    private static void displayGameResults(Player player) {
        System.out.println("\n" + CARDS_ART);
        System.out.println("🎮 Fin du jeu 🎮");
        System.out.println("Joueur 0 : " + player.getPoint() + " points");
        logger.info("Fin de partie - Score du joueur: " + player.getPoint());
    }
}