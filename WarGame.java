/*
Created by Edgar Alamillo
CIS18A Final
The purpose of this program is to simulate casino war card game
*/
import java.util.*;	// to use different data structures

interface Cards
{
	public int getRank();
	public String toString();
	
}

// Card class representing a single playing card
class Card implements Cards
{
    private int rank;
    private String suit;

    public Card(int rank, String suit) //Used to store information on the card
    {
        this.rank = rank;
        this.suit = suit;
    }

    public int getRank() //return rank for comparison
    {
        return rank;
    }

    @Override	//makes sure to return card and not a address
    public String toString() 
    {
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        return ranks[rank - 2] + " of " + suit;
    }
}

// Deck class to handle shuffling and dealing
class Deck 
{
    private List<Card> cards;	// use list to make the whole deck

    public Deck() 
    {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        cards = new ArrayList<>();
        for (String suit : suits) 	//Creates the whole deck
        {
            for (int rank = 2; rank <= 14; rank++) 
            {
                cards.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(cards);	//Used to shuffle the deck
    }

    public Card drawCard() 
    {
        return cards.isEmpty() ? null : cards.remove(0); //Used to check if empty and return 
    }

    public int size() 
    {
        return cards.size();	//Gets size of the deck
    }
}

// Player class representing each player
class Player 
{
    private Queue<Card> hand;	// queue to hold the players hand

    public Player() 
    {
        hand = new LinkedList<>();	//Creates the hand for each player
    }

    public void addCard(Card card) 
    {
        hand.offer(card);			//Used to add card when player wins
    }

    public Card playCard() 
    {
        return hand.poll();			//Used to take card if player loses
    }

    public int cardCount() 
    {
        return hand.size();			//Called on to check each deck size
    }
}
class Total // class to hold total money and win/loss record
{
	private int total = 0;
	private Queue<String> outcome = new LinkedList<>();
	
	int getTotal()
	{
		return(total);
	}
	
	void addTotal(int wager)
	{
		total += wager;
	}
	
	void subTotal(int wager)
	{
		total -= wager;
	}
	
	void printOutcomes(int r)		//Uses a queue to store and print outcomes
	{
		for(int i = 0; i < r; i++)
		{
		System.out.print("Round " + (i + 1) + ": ");
		System.out.println(outcome.poll());
		}
	}
	
	void addLoss()
	{
		outcome.add("Loss");
	}
	
	void addWin()
	{
		outcome.add("Win");
	}
	
}

// WarGame class to manage the game logic
class WarGame extends Total
{
    private Player player;
    private Player dealer;
    
    public WarGame() 	//Constructor to create deck and players hands
    {
        player = new Player();
        dealer = new Player();
        Deck deck = new Deck();

        while (deck.size() > 0) 
        {
            player.addCard(deck.drawCard());
            if (deck.size() > 0) 
            {
                dealer.addCard(deck.drawCard());
            }
        }
    }

    public void play() 	//Main menu and selections
    {
    	System.out.println("**************");
    	System.out.println("* Casino War *");
    	System.out.println("**************");
    	System.out.println("\nPlease select an option and hit enter");
    	System.out.println("1. Play game ");
    	System.out.println("2. Read rules");
    	System.out.println("3. Exit game");
    	
    	Scanner o = new Scanner(System.in);	//uses user input to get choice
    	try 
    	{
    		int choice = o.nextInt();
    		switch(choice)
        	{
        		case 1:
        			int r = 1;
        			int wager = 0;
        			WagerLoop(r, wager);
        			break;
        		case 2:
        			GameRules();
        			break;
        		case 3:
        			System.exit(0);
        			break;
        		default:
        			System.out.println("Error not one of the choices");
        			play();
        	
        	}
    	}
    	catch (Exception e)	//look for user errors
    	{
    		System.out.println("Error not one of the choices");
    		play();

    	}
    }
    
    void WagerLoop(int r, int wager)	// Starts the game and start of game loop
    {
    	System.out.println("Round " + r);	//r is used to keep track of rounds
    										//checks if anyone has zero cards
    	if (player.cardCount() < 1) 
        {
            System.out.println("Dealer wins the game!");
            printOutcomes(r);
            System.out.println("You've won $" + getTotal());
			System.out.println("Thanks for playing");
            System.exit(0);
        } 
        else 
        if (dealer.cardCount() < 1)
        {
            System.out.println("Player wins the game!");
            printOutcomes(r);
            System.out.println("You've won $" + getTotal());
			System.out.println("Thanks for playing");
            System.exit(0);
        }
        else
        {
        	Scanner bet = new Scanner(System.in);			//takes user input for wager
        	try 
        	{
        		System.out.println("Please enter how much you would like to wager(Must be over $1)");
        		wager = bet.nextInt();
        		if(wager == 0)							//makes sure the bet is over 0
        		{
        			System.out.println("Must be over $1");
        			WagerLoop(1,wager);
        		}
        		else
        		{
        			GameLoop(wager);
        		}
        	}
        	
        	catch (Exception e)					//checks for errors
        	{
        		System.out.println("Error invalid number returning to main menu");
        		play();

        	}
        }
    	
    	System.out.println("Would you like to play again?");		//Brings up choices to play again or cash out
    	System.out.println("1. yes");
    	System.out.println("2. no and cash out");
    	Scanner myObj = new Scanner(System.in);
    	
    	try 
    	{
    		int choice = myObj.nextInt();
    		switch(choice)
    		{
    		case 1: 
    			WagerLoop(++r, wager);
    			break;
    		case 2:
                printOutcomes(r);
    			System.out.println("You've won $" + getTotal());
    			System.out.println("Thanks for playing");
    			System.exit(0);
    			break;
    		default: 
    			System.out.println("Error incorrect choice returning to main menu");
        		play();
    		
    		}
    	}
    	
    	catch (Exception e)
    	{
    		System.out.println("Error incorrect choice returning to main menu");
    		play();

    	}
    	
    }
    void GameLoop(int wager)					//This is where the actual game is played
    {
    	Card card1 = player.playCard();			//draws a card for the player and dealer
        Card card2 = dealer.playCard();

        System.out.println("Player plays: " + card1);
        System.out.println("Dealer plays: " + card2);

        if (card1.getRank() > card2.getRank()) 			//checks for who won and keeps track of win/loss and wager
        {
            player.addCard(card1);
            player.addCard(card2);
            System.out.println("Player wins the round!");
            System.out.println("Player has " + player.cardCount() + " cards left.");
            System.out.println("Dealer has " + dealer.cardCount() + " cards left.\n");
            addWin();
            addTotal(wager);
        } 
        else 
        if (card2.getRank() > card1.getRank()) 
        {
            dealer.addCard(card1);
            dealer.addCard(card2);
            System.out.println("Dealer wins the round!");
            System.out.println("Player has " + player.cardCount() + " cards left.");
            System.out.println("Dealer has " + dealer.cardCount() + " cards left.\n");
            addLoss();
            subTotal(wager);
        } 
        else 
        {
            System.out.println("It's a tie!");								//tie in which it gives the option to surrender or go to war
            System.out.println("Would you like to go to war or surrender?");
            System.out.println("1.War\n2.Surrender\n");
        	Scanner w = new Scanner(System.in);
            try 
            {
        		int c = w.nextInt();
        		switch(c)
        		{
        		case 1: 						//war option
        			War(card1, card2, wager);
        			break;
        		case 2:							//Surrender option
        			dealer.addCard(card1);
                    dealer.addCard(card2);
                    System.out.println("Player had Surrendered");
                    System.out.println("Player has " + player.cardCount() + " cards left.");
                    System.out.println("Dealer has " + dealer.cardCount() + " cards left.\n");
                    addLoss();
                    subTotal(wager/2);
        			break;
        		default: 
        			System.out.println("Error invalid choice returning to main menu");
            		play();
        		
        		}
        	}
        	
        	catch (Exception e)
        	{
        		System.out.println("Error invalid choice returning to main menu");
        		play();

        	}
           
        }
        
    }
    
    void War(Card card1, Card card2, int wager)		//This is just for the war option 
    {
    	Card card3 = player.playCard();
        Card card4 = dealer.playCard();

        System.out.println("Player plays: " + card3);
        System.out.println("Dealer plays: " + card4);

        if (card3.getRank() > card4.getRank()) 
        {
        	player.addCard(card1);
        	player.addCard(card2);
            player.addCard(card3);
            player.addCard(card4);
            System.out.println("Player wins the round!");
            System.out.println("Player has " + player.cardCount() + " cards left.");
            System.out.println("Dealer has " + dealer.cardCount() + " cards left.\n");
            addWin();
            addTotal(wager*2);
        } 
        else 
        	dealer.addCard(card1);
        	dealer.addCard(card2);
            dealer.addCard(card3);
            dealer.addCard(card4);
            System.out.println("Dealer wins the round!");
            System.out.println("Player has " + player.cardCount() + " cards left.");
            System.out.println("Dealer has " + dealer.cardCount() + " cards left.\n");
            addLoss();
            subTotal(wager*2);
    }
    
    void GameRules()				//Allow the user to read the rules before hand 
    {
    	System.out.println("\nThe goal of the game is to have a higher card than your opponent. The rankings start at the lowest of 2 and go "
    			+ "\nall the way up to the highest ace. Each player draws one card. The highest card is the winner of that round and takes "
    			+ "\nthe losers card. If anyone loses all their cards then they have offically lost the game. If a tie happens then war starts."
    			+ "\nEach player must put down an additional card if they go to war or may surrender to keep half their wager. "
    			+ "\nWinner takes all cards");
    	System.out.println();
    	play();
    }
    
    public static void main(String[] args) //Main function creates an object and calls the start of the game
    {
        WarGame game = new WarGame();
        game.play();
    }
}
