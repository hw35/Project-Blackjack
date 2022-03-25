public class BlackjackCards extends RandIndexQueue<Card>
{
	int val; //sum of value in hand
	int ace; //number of aces that are value 11 in hand (aces that are valued as 1 don't count here)

	public BlackjackCards(int size)
	{
		super(size);		
	}

	//overwrites the enqueue in RandIndexQueue class for the purpose of Assig1B.java
	public void enqueue(Card newEntry)
	{
		super.enqueue(newEntry);
		int cur = newEntry.value();				

		if(cur == 11)
			ace++;
		if(size() == 0)
			val = 0;
		else
		{
			//if next card has the potential for val to go over 21
			if(val > 10)
			{
				if(cur == 11)
				{
					val = val + 1;
					ace--;
				}
				else if(ace > 0 && (val + cur > 21))
				{
					val = val + cur - 10;
					ace--;
				}
				else
					val += cur;
			}
			else
				val += cur;
		}
	}

	public int getValue()
	{
		return val;
	}

	public Card dealCard()
	{
		return dequeue();
	}
}