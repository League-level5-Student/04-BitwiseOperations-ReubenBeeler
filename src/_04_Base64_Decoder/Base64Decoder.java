package _04_Base64_Decoder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Base64Decoder {
	/*
	 * Base 64 is used for encoding binary data with text.
	 * Each number 0-63 is mapped to a character. 
	 * NOTE: THIS IS NOT THE SAME AS ASCII OR UNICODE ENCODING!
	 * Since the numbers 0 through 63 can be represented using 6 bits, 
	 * every four (4) characters will represent twenty four (24) bits of data.
	 * 4 * 6 = 24
	 * 
	 * For this exercise, we won't worry about what happens if the total bits being converted
	 * do not divide evenly by 24.
	 * 
	 * If one char is 8 bits, is this an efficient way of storing binary data?
	 * (hint: no)
	 * 
	 * It is, however, useful for things such as storing media data inside an HTML file (for web development),
	 * so that way a web site does not have to look for an image, sound, library, or whatever in a separate location.
	 * 
	 * View this link for a full description of Base64 encoding
	 * https://en.wikipedia.org/wiki/Base64
	 */
	
	
	final static char[] base64Chars = { // List<Character> base64Chars = List.of(...)?
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 
		'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 
		'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
	};
	
	//1. Complete this method so that it returns the element in
	//   the base64Chars array that corresponds to the passed in char.
	public static byte convertBase64Char(char c) throws CharacterException {
		for (byte i = 0; i < 64; i++) {
			if (c == base64Chars[i]) {
				return i;
			}
		}
		throw new CharacterException(c);
	}
	
	//2. Complete this method so that it will take in a string that is 4 
	//   characters long and return an array of 3 bytes (24 bits). The byte 
	//   array should be the binary value of the encoded characters.
	public static byte[] convert4CharsTo24Bits(String s) throws CharacterException, Exception {
		char[] chars = s.toCharArray();
		if (chars.length != 4) throw new Exception("String length != 4");
		byte[] singles = new byte[chars.length];
		for (byte i = 0; i < singles.length; i++) {
			singles[i] = convertBase64Char(chars[i]);
		}
		byte[] combined = {0, 0, 0};
		byte address = 0;
		for (byte i = 0; i < 3; i++) {
			for (byte j = 0; j < 8; j++) {
				combined[i] = (byte) ((int) combined[i] << 1);
				combined[i] += (singles[address / 6] >> 5) & 1;
				singles[address / 6] <<= 1;
				address++;
			}
		}
		return combined;
	}
	
	//3. Complete this method so that it takes in a string of any length
	//   and returns the full byte array of the decoded base64 characters.
	public static byte[] base64StringToByteArray(String file) {
		try {
			FileReader fr = new FileReader(new File(file));
			List<Byte> bytes = new ArrayList<>();
			HashMap<Integer, Character> errors = new HashMap<>();
			String group = "";
			int it = 0;
			for (int c = fr.read(); c != -1; it++) {
				if (group.length() == 4) {
					try {
						for (byte b : convert4CharsTo24Bits(group)) {
							bytes.add(b);
						}
					} catch (Exception e) {
						if (e instanceof CharacterException) {
							errors.put(it, ((CharacterException) e).C);
						}
					}
					group = "";
				}
				group += (char) c;
			}
			if (errors.isEmpty() && group.equals("")) {
				byte[] finalBytes = new byte[bytes.size()];
				for (int i = 0; i < finalBytes.length; i++) {
					finalBytes[i] = bytes.get(i);
				}
				return finalBytes;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String binString(byte b) {
		String s = "";
		for (byte i = 0; i < 8; i++) {
			s += (b >> 7) & 1;
			b <<= 1;
		}
		return s;
	}
}
