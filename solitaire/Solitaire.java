import java.util.*;
/**
 * This is the classic Klondike Solitaire card game.
 * 
 * Once you start the game, the elapsed time is displayed
 * on the game panel, and you have the option to start over
 * the game anytime by click "Start Over" on the game panel. 
 *
 * @author Emily Zhou
 * @version 11-5-2018
 */
public class Solitaire
{
    /**
     * The main method to run the game.
     * 
     * @param args no arguments expected to play this game.
     */
    public static void main(String[] args)
    {
        new Solitaire();
    }

    private Stack<Card> stock;
    private Stack<Card> waste;
    private Stack<Card>[] foundations;
    private Stack<Card>[] piles;
    private SolitaireDisplay display;

    /**
     * The constructor to create a Solitaire object.
     */
    public Solitaire()
    {
        init();
        display = new SolitaireDisplay(this);
    }
    
    /**
     * Initialize the game.
     */
    private void init()
    {
        foundations = new Stack[4];
        piles = new Stack[7];
        for (int i = 0; i < 4; i++)
        {
            foundations[i] = new Stack<Card>();
        }
        for (int i = 0; i < 7; i++)
        {
            piles[i] = new Stack<Card>();
        }
        stock = new Stack<Card>();
        waste = new Stack<Card>();
        createStock();
        deal();    
    }
    
    /**
     * This is called when the start over is clicked to restart the game.
     */
    public void startOverClicked()
    {
        System.out.println("StartOver clicked");
        init();
        display.unselect();
        display.resetTimeElapsed();
    }
    
    /**
     * Peek the card on top of the stock.
     * 
     * @returns the card on top of the stock,
     *         or null if the stock is empty
     */         
    public Card getStockCard()
    {
        if (stock.isEmpty())
        {
            return null;
        }
        return stock.peek();
    }

    /**
     * Peek the card on top of the waste.
     * 
     * @returns the card on top of the waste,
     *         or null if the waste is empty
     */         
    public Card getWasteCard()
    {
        if (waste.isEmpty())
        {
            return null;
        }
        return waste.peek();
    }

    /**
     * Peek the card on top of the specified foundation.
     * 
     * @precondition:  0 <= index < 4
     * @postcondition: returns the card on top of the given
     *               foundation, or null if the foundation
     *               is empty
     * @param index the index into the foundation array             
     * @return the card on top of the given foundation
     *             or null if the foundation is empty
     *               
     */              
    public Card getFoundationCard(int index)
    {
        Stack<Card> foundation = foundations[index];
        if (foundation.isEmpty())
        {
            return null;
        }
        return foundation.peek();
    }

    /**
     * Get a reference to a given pile.
     * 
     * @precondition:  0 <= index < 7
     * @postcondition: returns a reference to the given pile
     * 
     * @param index the index into the array of piles
     * @return the reference of given pile
     */
    public Stack<Card> getPile(int index)
    {
        return piles[index];
    }

    /**
     * This method is called when the stock is clicked.
     */
    public void stockClicked()
    {
        System.out.println("stock clicked");
        if (display.isWasteSelected() || display.isPileSelected())
            return;
        if (!stock.isEmpty())
        {
            dealThreeCards();
        }
        else 
        {
            resetStock();
        }
    }

    /**
     * This method is called when the waste is clicked.
     */
    public void wasteClicked()
    {
        System.out.println("waste clicked");   
        if (display.isWasteSelected())
        {
            display.unselect();
            return;
        }
        if (!waste.isEmpty() && !display.isWasteSelected() && !display.isPileSelected())
        {
            display.selectWaste();
        }
    }

    /**
     * This method is called when a given foundation is clicked.
     * 
     * @precondition:  0 <= index < 4
     * @param index the index of array of foundations that is clicked
     */
    //
    public void foundationClicked(int index)
    {
        System.out.println("foundation #" + index + " clicked");
        if (display.isWasteSelected())
        {
            if (canAddToFoundation(waste.peek(),index))
            {
                foundations[index].push(waste.pop());
                display.unselect();
                return;
            }
        }
        if (display.isPileSelected())
        {
            Stack<Card> pile = getPile(display.selectedPile());
            if (canAddToFoundation(pile.peek(),index))
            {
                foundations[index].push(pile.pop());
                display.unselect();
            }
        }
    }

    /**
     * This method is called when a given pipe is clicked.
     * 
     * @precondition:  0 <= index < 7
     * @param index the index of the array of piles that is clicked
     */
    public void pileClicked(int index)
    {
        System.out.println("pile #" + index + " clicked");
        Stack<Card> pile = getPile(index);
        if (!pile.isEmpty() && pile.peek().isFaceUp())
        {
            if (!display.isWasteSelected() && !display.isPileSelected())
            {
                display.selectPile(index);
                return;
            }
        }
        if (!display.isPileSelected() && !display.isWasteSelected() &&
            !pile.isEmpty() && !pile.peek().isFaceUp())
        {
            pile.peek().turnUp();
            return;
        }  
        if (display.isPileSelected() && (display.selectedPile() == index))
            display.unselect();
        if (display.isPileSelected() && (display.selectedPile() != index))
        {
            Stack<Card> selected = removeFaceUpCards(display.selectedPile());
            if (canAddToPile(selected.peek(),index))
            {
                addToPile(selected,index);
                display.unselect();
            }
            else
            {
                addToPile(selected,display.selectedPile());
            }
        }
        if (display.isWasteSelected())
        {
            Card w = waste.peek();
            if (canAddToPile(w,index))
            {
                getPile(index).push(waste.pop());
                display.unselect();
            }
        }
    }

    /**
     * Create the stock stack with 52 randomly shuffed cards.
     */
    private void createStock()
    {
        List<Card> deck = new ArrayList<Card>();
        for (int i = 0; i < 4; i++)
        {
            for (int j = 1; j <= 13; j++)
            {
                deck.add(new Card(j, i));
            }
        }
        Random r = new Random();
        while (!deck.isEmpty())
        {
            int num = r.nextInt(deck.size());
            Card c = deck.remove(num);
            stock.push(c);
        }
    }

    /**
     * Deal cards from stock to piles. 
     */
    private void deal()
    {
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j <= i; j++)
            {
                getPile(i).push(stock.pop());
            }
            getPile(i).peek().turnUp();
        }
    }

    /**
     * Deal 3 cards from stock to waste.
     * If less than 3 cards left in stock, deal all of them to waste.
     */
    private void dealThreeCards()
    {
        for (int i = 0; i < 3; i++)
        {
            if (!stock.isEmpty())
            {
                Card top = stock.pop();
                top.turnUp();
                waste.push(top);
            }
        }
    }

    /**
     * Reset the stock to move all cards from waste to stock.
     */
    private void resetStock()
    {
        while (!waste.isEmpty())
        {
            Card c = waste.pop();
            c.turnDown();
            stock.push(c);
        }
    }

    /**
     * Check if a card can be legally added to a pile.
     * 
     * @precondition: 0 <= index < 7
     * @postcondition: Returns true if the given card can be
     *        legally moved to the top of the given pile
     * @param card the card to check
     * @param index the index into the array of piles
     * @return true if the card can be legally moved to the pile
     *         false otherwise
     */
    private boolean canAddToPile(Card card, int index)
    {
        Stack<Card> pile = getPile(index);
        if (pile.isEmpty())
        {
            if (card.getRank() == Card.RANK_KING)
                return true;
            else
                return false;
        }
        if (!pile.peek().isFaceUp())
            return false;
        if ((pile.peek().isRed() && !card.isRed()) || (!pile.peek().isRed() && card.isRed()))
            if (card.getRank() == pile.peek().getRank()-1)
                return true;
        return false;
    }

    /**
     * Remove all face-up cards on the top of a pile.
     * 
     * @precondition:  0 <= index < 7
     * @postcondition: Removes all face-up cards on the top of
     *        the given pile; returns a stack
     *        containing these cards
     * @param index the index into the array of piles
     * @return a Stack of cards that removed from the pile
     */
    private Stack<Card> removeFaceUpCards(int index)
    {
        Stack<Card> pile = getPile(index);
        Stack<Card> temp = new Stack<Card>();
        while (!pile.isEmpty() && pile.peek().isFaceUp())
        {
            temp.push(pile.pop());
        }
        return temp;
    }

    /**
     * Add a stack of cards to a given pile.
     * 
     * @precondition: 0 <= index < 7
     * @postcondition: Removes elements from cards, and adds
     *                 them to the given pile.
     * @param cards a stack of cards that to be added to the given pile
     * @param index the index into the array of piles
     */
    //
    private void addToPile(Stack<Card> cards, int index)
    {
        while (!cards.isEmpty())
        {
            getPile(index).push(cards.pop());
        }
    }

    /**
     * Check if a card can be legally added to a foundation.
     * 
     * @precondition: 0 <= index < 4
     * @postcondition: Returns true if the given card can be
     *     legally moved to the top of the given foundation
     * @param card the card to check
     * @param index the index into the array of foundations
     * @return true if the card can be legally added to
     *         the given foundation otherwise false
     */ 
    private boolean canAddToFoundation(Card card, int index)
    {
        Card fc = getFoundationCard(index);
        if (fc == null)
        {
            if (card.getRank() == Card.RANK_ACE)
            {
                return true;
            }
            return false;
        }
        if (fc.getSuit().equals(card.getSuit()) && (fc.getRank() == card.getRank()-1))
        {
            return true;
        }
        return false;
    }
}