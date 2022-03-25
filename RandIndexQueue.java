import java.util.Arrays;
import java.util.Random;

public class RandIndexQueue<T> implements MyQ<T>, Shufflable, Indexable<T>
{
	T[] q;
	int moves;
	int size, cap, fIndex, bIndex; // front and back index
	private static final int MAX_CAPACITY = 10000;

	public RandIndexQueue(int s)
	{
		q = (T[]) new Object[s];
		cap = s;
	}

	public int size()
	{
		return size;
	}

	public int capacity()
	{
		return cap;
	}

	public int getMoves()
	{
		return moves;
	}

	public void setMoves(int n)
	{
		moves = n;
	}

	public T getFront()
	{
		if(isEmpty())
		{
			throw new EmptyQueueException();
		}
		
		return q[fIndex];
	}

	public void enqueue(T newEntry)
	{
		if(isFull())
		{
			doubleCapacity();
			bIndex = size;
			fIndex = 0;
		}
		q[bIndex] = newEntry;
		bIndex = (bIndex+1)%cap;
		moves++;
		size++;
	}

	public T dequeue()
	{
        if(isEmpty()) 
        {
        	throw new EmptyQueueException();
        }
		T temp = getFront();
		q[fIndex] = null;
		fIndex = (fIndex+1)%cap;
		moves++;
		size--;	
		return temp;
	}

	private boolean isFull()
	{
		return (size == cap);
	}

	public boolean isEmpty()
	{
		return (size == 0);
	}

	private void doubleCapacity()
	{
      int capNew = cap*2;
      checkCapacity(capNew);
      T[] temp = (T[]) new Object[capNew];
      for(int i = 0; i < size; i++)
      {
      	temp[i] = q[(fIndex+i)%cap];
      }
      cap = capNew;
      q = temp;

	}

	private void checkCapacity(int capacity)
    {
      if (capacity > MAX_CAPACITY)
         throw new IllegalStateException("Attempt to create a bag whose capacity exceeds " +
                                         "allowed maximum of " + MAX_CAPACITY);
    } 

    public void clear()
	{
		q = (T[]) new Object[q.length];
		moves = 0;
		fIndex = 0;
		bIndex = 0;
		size = 0;
	}

	public T get(int i)
	{
		if(i > size)
			throw new IndexOutOfBoundsException();
		return q[(fIndex+i)%cap];
	}

	public void set(int i, T item)
	{
		if(i > size)
			throw new IndexOutOfBoundsException();
		q[(fIndex+i)%cap] = item;
	}

	public void shuffle()
	{
		int swapAt;
		T curVal,swapVal;
		Random rand = new Random();
		for(int i = 0; i < size(); i++)
		{
			swapAt = rand.nextInt(size());
			curVal = get(i);
			swapVal = get(swapAt);
			set(swapAt,curVal);
			set(i,swapVal);
		}
	}

	public RandIndexQueue(RandIndexQueue<T> old)
	{
		q = (T[]) new Object[old.capacity()];
		for(int i = 0; i < old.size(); i++)
		{
			q[i] = old.get(i);
		}
		size = old.size;
		fIndex = 0;
		bIndex = size;
		cap = old.cap;
	}

	public boolean equals(RandIndexQueue<T> rhs)
	{
		if(rhs.size() != size())
			return false;

		for(int i = 0; i < size(); i++)
		{
			if(rhs.get(i) != get(i))
				return false;
		}
		return true;
	}

	public String toString()
	{
		StringBuilder S = new StringBuilder();
		for (int i = 0; i < size; i++)
		{
			S.append(q[(fIndex+i)%cap] + " ");
		}
		return "Contents: " + S.toString();
	}
}