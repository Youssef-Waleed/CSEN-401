package exceptions;

import java.lang.Exception;

public class GameActionException extends Exception {
	public GameActionException() {
		super();
	}

	public GameActionException(String s) {
		super(s);
	}
}
