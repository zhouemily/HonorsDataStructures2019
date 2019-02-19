/**
 * The Card class represents a card that has a rank,
 * suit and a state of face up or down.
 *
 * @author Emily Zhou
 * @version 11-3-2018
 */
public class Card
{
    public static final int RANK_ACE = 1;
    public static final int RANK_KING = 13;
    private static final String SUIT_CLUBS = "c";
    private static final String SUIT_DIAMONDS = "d";
    private static final String SUIT_HEARTS = "h";
    private static final String SUIT_SPADES = "s";
    //1 to 13, 1=Ace and 13=King
    private int rank;
    //"c", "d", "h" and "s"
    private String suit;
    private boolean isFaceUp = false;

    /**
     * Constructor for objects of class Card.
     * 
     * @param rank the rank of the card
     * @param suit the suit of the card
     */
    public Card(int rank, String suit)
    {
        this.rank = rank;
        this.suit = suit;
        isFaceUp = false;
    }

    /**
     * Constructor for objects of class Card
     * that takes rank and index of suit. 
     * 
     * The indexes of suits are
     * 0 - "c"; 1 - "d"; 2 - "h"; 3 - "s"
     * 
     * @param rank the rank of the card
     * @param suitIndex the index of suit
     */
    public Card(int rank, int suitIndex)
    {
        this(rank, getSuit(suitIndex));
    }

    /**
     * Get the rank of this card.
     *
     * @return the rank of this card
     */
    public int getRank()
    {
        return rank;
    }

    /**
     * Get the suit of this card.
     * 
     * @return the suit of this card
     */
    public String getSuit()
    {
        return suit;
    }

    /**
     * Check if this card is a red colored card.
     * 
     * @return true if the suit is "d" or "h"
     */
    public boolean isRed()
    {
        return suit.equals(SUIT_DIAMONDS) || suit.equals(SUIT_HEARTS);
    }

    /**
     * Check if this card is face up.
     * 
     * @return true if face up
     */
    public boolean isFaceUp()
    {
        return isFaceUp;
    }

    /**
     * Make the card face up.
     */
    public void turnUp()
    {
        isFaceUp = true;
    }

    /**
     * Make the card face down.
     */
    public void turnDown()
    {
        isFaceUp = false;
    }

    /**
     * Get the file name contains the image of this card
     */
    public String getFileName()
    {
        if (!isFaceUp) 
        {
            return "cards/back.gif";
        }
        String rankStr = String.valueOf(rank);
        switch(rank) 
        {
            case 1: rankStr = "a";
            break;
            case 10: rankStr = "t";
            break;
            case 11: rankStr = "j";
            break;
            case 12: rankStr = "q";
            break;
            case 13: rankStr = "k";
        }
        return "cards/"+rankStr+suit+".gif";
    }

    /**
     * Convert a suit index to suit string.
     * 
     * @param suitIndex the index of the suit
     * @return the suit string
     */
    private static String getSuit(int suitIndex)
    {
        switch(suitIndex)
        {
            case 0: return SUIT_CLUBS;
            case 1: return SUIT_DIAMONDS;
            case 2: return SUIT_HEARTS;
            case 3: return SUIT_SPADES;
            default: throw new RuntimeException("Illegal suit index: " + suitIndex);
        }
    }
}