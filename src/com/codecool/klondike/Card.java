package com.codecool.klondike;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.sql.SQLOutput;
import java.util.*;

public class Card extends ImageView {
    private SuitType suit;
    private boolean faceDown;
    private RankType rank;

    private Image backFace;
    private Image frontFace;
    private Pile containingPile;
    private DropShadow dropShadow;

    static Image cardBackImage;
    private static final Map<String, Image> cardFaceImages = new HashMap<>();
    public static final int WIDTH = 150;
    public static final int HEIGHT = 215;

    public Card(int suit, int rank, boolean faceDown) {
        this.suit = SuitType.getSuitById(suit);
        this.rank = RankType.getRankById(rank);
        this.faceDown = faceDown;
        this.dropShadow = new DropShadow(2, Color.gray(0, 0.75));
        backFace = cardBackImage;
        frontFace = cardFaceImages.get(getShortName());
        setImage(faceDown ? backFace : frontFace);
        setEffect(dropShadow);
    }

    public int getSuit() {
        return suit.showSuitId();
    }

    public int getRank() {
        return rank.showRankId();
    }

    public boolean isFaceDown() {
        return faceDown;
    }

    public String getShortName() {
        return "S" + suit.suitId + "R" + rank.rankId;
    }

    public DropShadow getDropShadow() {
        return dropShadow;
    }

    public Pile getContainingPile() {
        return containingPile;
    }

    public void setContainingPile(Pile containingPile) {
        this.containingPile = containingPile;
    }

    public void moveToPile(Pile destPile) {
        this.getContainingPile().getCards().remove(this);
        destPile.addCard(this);
    }

    public void flip() {
        faceDown = !faceDown;
        setImage(faceDown ? backFace : frontFace);
    }

    @Override
    public String toString() {
        return "The " + "Rank" + rank.rankId + " of " + "Suit" + suit.suitId;
    }

    public static boolean isOppositeColor(Card card1, Card card2) {
        if((card1.suit.suitName.equals("hearts") || card1.suit.suitName.equals("diamonds")) &&
            (card2.suit.suitName.equals("clubs") || card2.suit.suitName.equals("spades") )) {
                return true;
        } else {
            return false;
        }
    }

    public static boolean isSameSuit(Card card1, Card card2) {
        return card1.getSuit() == card2.getSuit();
    }

    public static List<Card> createNewDeck() {
        List<Card> result = new ArrayList<>();
        for (int suit = 1; suit < 5; suit++) {
            for (int rank = 1; rank < 14; rank++) {
                result.add(new Card(suit, rank, true));
            }
        }
        return result;
    }

    public static void loadCardImages() {
        cardBackImage = new Image("card_images/card_back.png");
        for (SuitType suit: SuitType.values()) {
            for (RankType rank: RankType.values()) {
                String cardName = suit.suitName + rank.rankId;
                String cardId = "S" + suit.suitId + "R" + rank.rankId;
                String imageFileName = "card_images/" + cardName + ".png";
                cardFaceImages.put(cardId, new Image(imageFileName));
            }
        }
    }

    public enum SuitType {
        HEARTS(1, "hearts"),
        DIAMONDS(2, "diamonds"),
        SPADES(3, "spades"),
        CLUBS(4, "clubs");

        int suitId;
        String suitName;

        SuitType(int s, String sn) {
            this.suitId = s;
            this.suitName = sn;
        }

        int showSuitId() {
            return suitId;
        }

        String showSuitName() {
            return suitName;
        }

        private static final Map<Integer, SuitType> BY_ID_MAP = new LinkedHashMap<>();
        static {
            for (SuitType st: SuitType.values()) {
                    BY_ID_MAP.put(st.suitId, st);
            }
        }

        public static SuitType getSuitById(int id) {
            return BY_ID_MAP.get(id);
        }


    }

    public enum RankType {
        ACE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10),
        JACK(11),
        QUEEN(12),
        KING(13);

        int rankId;

        RankType(int r) {
            rankId = r;
        }

        int showRankId() {
            return rankId;
        }

        private static final Map<Integer, RankType> BY_ID_MAP = new LinkedHashMap<>();
        static {
            for (RankType rt: RankType.values()) {
                BY_ID_MAP.put(rt.rankId, rt);
            }
        }

        public static RankType getRankById(int id) {
            return BY_ID_MAP.get(id);
        }


    }
}
