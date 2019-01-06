package util;

@SuppressWarnings({"unused", "WeakerAccess"}) //Used in permission command
public class Touple<A, B> {
	public final A a;
	public final B b;

	public Touple(A a, B b) {
		this.a = a;
		this.b = b;
	}
}