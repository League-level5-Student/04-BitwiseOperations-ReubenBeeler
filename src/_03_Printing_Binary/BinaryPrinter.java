package _03_Printing_Binary;

public class BinaryPrinter {
	/*
	 * Complete the methods below so they print the passed-in parameter in binary.
	 * Do not use the Integer.toBinaryString method!
	 * Use the main method to test your methods.
	 */

	
	public static void printByteBinary(byte b) {
		printByteBinary(b, (byte) 0);
	}
	
	private static void printByteBinary(byte b, byte i) {
		// We first want to print the bit in the one's place
		// Shift b seven bits to the right
		// Use the & operator to "mask" the bit in the one's place
		// This can be done by "anding" (&) it with the value of 1
		// Print the result using System.out.print (NOT System.out.println)
		System.out.print((b >> 7) & 1);
		//Use this method to print the remaining 7 bits of b
		if (i < 7) printByteBinary((byte) (b << 1), (byte) (i + 1));
	}
	
	public static void printShortBinary(short s) {
		// Create 2 byte variables
		byte[] bytes = {0, 0};
		// Use bit shifting and masking (&) to save the first
		// 8 bits of s in one byte, and the second 8 bits of
		// s in the other byte
		for (byte i = 0; i < bytes.length; i++) {
			for (byte j = 0; j < 8; j++) {
				bytes[i] <<= 1;
				bytes[i] += (byte) ((s >> 15) & 1);
				s <<= 1;
			}
		}
		// Call printByteBinary twice using the two bytes
		for (byte i = 0; i < bytes.length; i++) {
			printByteBinary(bytes[i]);
		}
		// Make sure they are in the correct order
	}
	
	public static void printIntBinary(int i) {
		// Create 2 short variables
		short[] shorts = {0, 0};
		// Use bit shifting and masking (&) to save the first
		// 16 bits of i in one short, and the second 16 bits of
		// i in the other short
		for (byte it = 0; it < shorts.length; it++) {
			for (byte j = 0; j < 16; j++) {
				shorts[it] <<= 1;
				shorts[it] += (short) ((i >> 31) & 1);
				i <<= 1;
			}
		}
		// Call printShortBinary twice using the two short variables
		for (byte it = 0; it < shorts.length; it++) {
			printShortBinary(shorts[it]);
		}
		// Make sure they are in the correct order
	}
	
	public static void printLongBinary(long l) {
		// Use the same method as before to complete this method
		int[] ints = {0, 0};

		for (byte i = 0; i < ints.length; i++) {
			for (byte j = 0; j < 32; j++) {
				ints[i] <<= 1;
				ints[i] += (int) ((l >> 63) & 1);
				l <<= 1;
			}
		}
		
		for (byte i = 0; i < ints.length; i++) {
			printIntBinary(ints[i]);
		}
	}
	
	public static void main(String[] args) {
		printByteBinary((byte) 11);
		System.out.println();
		printShortBinary((short) 121);
		System.out.println();
		printIntBinary((int) 1331);
		System.out.println();
		printLongBinary((long) 14641);
	}
}
