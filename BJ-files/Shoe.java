public class Shoe extends BlackjackCards
{
	int fullSize;
	public Shoe(int num)
	{
		super(num*52); 
		fullSize = num*52;
		for (int i = 0; i < num; i++)
		{
			for(Card.Suits suit : Card.Suits.values())
			{
				for(Card.Ranks rank : Card.Ranks.values())
					enqueue(new Card(suit, rank));
			}
		}
		shuffle();
	}

	//check in BJ.java
	public boolean needsRefill()
	{
		if(size() <= fullSize/4)
		{
			return true;
		}
		return false;
	}

	public void refill(BlackjackCards pile)
	{
		for(int i = 0; i<pile.size(); i++)
			enqueue(pile.dealCard());
		shuffle();
	}
}