public class Blackjack 
{
	//dealer and player draw cards alternatively
	public static void setUpRound(BlackjackCards shoe, BlackjackCards pHand, BlackjackCards dHand)
	{
		drawCard(pHand,shoe);
		drawCard(dHand,shoe);
		drawCard(pHand,shoe);
		drawCard(dHand,shoe);
	}

	public static void drawCard(BlackjackCards hand, BlackjackCards shoe)
	{
		hand.enqueue(shoe.dealCard());
	}

	//plays round and returns who wins: -1 is dealer win, 1 is player win, 0 is push
	public static int playRound(int totalRounds, int roundNum, boolean shouldTrace, Shoe shoe, Player pHand, Player dHand, Shoe discard)
	{
		setUpRound(shoe, pHand, dHand);
		if(shouldTrace)
		{
			System.out.println();
			System.out.println("Round " + roundNum + " beginning");
			System.out.println("Player: " + pHand.toString() + " : " + pHand.getValue());
			System.out.println("Dealer: " + dHand.toString() + " : " + dHand.getValue());
		}

		theirTurn(shoe, pHand, shouldTrace, "Player", "Dealer"); //player drawing algorithm
		
		if(checkVal(pHand) < 0) //checking who wins and returning their ints
		{
			return -1;
		}
		else
		{
			theirTurn(shoe, dHand, shouldTrace, "Dealer", "Player");
			if(checkVal(dHand) < 0)
				return 1;
			else
			{
				if(pHand.getValue() > dHand.getValue())
				{
					printWin("Player", shouldTrace);
					return 1;
				}
				else if(pHand.getValue() < dHand.getValue())
				{
					printWin("Dealer", shouldTrace);
					return -1;
				}
				else
				{
					printPush(shouldTrace);
					return 0;
				}
			}
		}
	}

	//checking if shoe needs reshuffling
	public static void endRound(Shoe discard, Shoe shoe, int roundNum)
	{
		if(shoe.needsRefill())
		{
			shoe.refill(discard);
			System.out.println("Reshuffling the shoe in round " + roundNum);
			System.out.println();
		}
	}

	//algorithm for player/dealer to determine if they should hit or stand
	public static void theirTurn(Shoe shoe, Player hand, boolean shouldTrace, String currPlayer, String otherPlayer) //return checkVal() to check bust, pass in if its dealer or player
	{
		while(hand.checkDraw())
		{
			drawCard(hand,shoe);
			if(shouldTrace)
				System.out.println(currPlayer + " hits: " + hand.get(hand.size()-1));
		}
		if(shouldTrace)
		{
			if(checkVal(hand) < 0 )
			{
				System.out.println(currPlayer + " BUSTS: " + hand.toString()+ " : " + hand.getValue());
				System.out.println("Result: " + otherPlayer + " Wins!");
				System.out.println();
			}
			else
				System.out.println(currPlayer + " STANDS: " + hand.toString() + " : " + hand.getValue());
		}	
	}

	//checking of player busts
	public static int checkVal(Player hand)
	{
		if(hand.getValue() > 21)
			return -1;
		else 
			return 1;
	}

	//prints out final results summed
	public static void outcome(int pw, int dw, int p, int rounds)
	{
		System.out.println("After " + rounds + " rounds, here are the results: ");
		System.out.println("\t" + "Dealer Wins: " + dw);
		System.out.println("\t" + "Player Wins: " + pw);
		System.out.println("\t" + "Pushes: " + p);
	}

	//prints who wins
	public static void printWin(String winner, boolean shouldTrace)
	{
		if(shouldTrace)
		{
			System.out.println("Result: " + winner + " Wins!");
			System.out.println();
		}
	}

	//prints a push
	public static void printPush(boolean shouldTrace)
	{
		if(shouldTrace)
		{
			System.out.println("Result: Push!");
			System.out.println();
		}
	}

	public static void main(String [] args)
	{
		int playerWin = 0;
		int dealerWin = 0;
		int push = 0;

		int rounds = Integer.parseInt(args[0]);
		int decks = Integer.parseInt(args[1]);
		int trace = Integer.parseInt(args[2]);

		Shoe shoe = new Shoe(decks);
		Shoe discardPile = new Shoe(decks);		
		Player playerHand = new Player(12);
		Player dealerHand = new Player(12);

		System.out.println("Starting Blackjack with " + rounds + " rounds and " + decks + " decks in the shoe");

		boolean shouldTrace = true; //if the round is to be traced

		for(int i = 0; i < rounds; i++)
		{
			if(i>=trace)
				shouldTrace = false;
			
			int winner = playRound(rounds, i, shouldTrace, shoe, playerHand, dealerHand, discardPile);

			//incrementing the number of player wins, dealer wins and pushes for the result print
			switch(winner)
			{
				case -1: 
					dealerWin++;
					break;
				case 1:
					playerWin++;
					break;
				case 0:
					push++;
					break;
			}

			//after each round, puts all the drawn cards into discard pile
			for(int j = playerHand.size(); j>0; j--)
			{
				drawCard(discardPile,playerHand);
			}
			for(int k = dealerHand.size(); k>0; k--)
			{
				drawCard(discardPile,dealerHand);
			}
			
			//clear value of player and dealer hands after each round
			playerHand.val = 0;
			dealerHand.val = 0;
			
			//reshuffling shoe if needed
			endRound(discardPile, shoe, i);
		}	
		outcome(playerWin,dealerWin,push,rounds);
	}
}