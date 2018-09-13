package party.lemons.arcaneworld.util;

/**
 * Created by Sam on 10/09/2018.
 */
public class Pair<A, B>
{
	private A a;
	private B b;

	public static <A, B> Pair<A, B> of(A a, B b)
	{
		return new Pair(a, b);
	}

	public Pair(A a, B b)
	{
		this.a = a;
		this.b = b;
	}

	public A getFirst()
	{
		return a;
	}
	public B getSecond()
	{
		return b;
	}
}
