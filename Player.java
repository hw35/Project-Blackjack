public class Player extends BlackjackCards
{
	public Player(int num)
	{
		super(num);
	}
	public boolean checkDraw()
	{
		if(getValue() < 17)
		{
			return true;
		}
		return false;
	}
}