package _04_Base64_Decoder;

@SuppressWarnings("serial")
class CharacterException extends Exception {
	
	final char C;
	
	CharacterException(char c) {
		super();
		C = c;
	}
}