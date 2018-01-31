package net.bogus.interview;

public class UncompressString {

	public static void main(String[] args) {
		System.out.println(uncompress("ab2c3d"));
		System.out.println(uncompress("ab+"));
		System.out.println(uncompress("abcd%3"));
	}
	
	public static String uncompress(String input) {
		StringBuffer output = new StringBuffer();
		
		int count = 1;
		for (int i = 0; i < input.length(); i++) {
			char val = input.charAt(i);
			if (Character.isLetter(val)) {
				while(count-- > 0) {
					output.append(val);
				}
				count = 1;
			} else if (Character.isDigit(val)) {
				count = val - '0';
                	i++;
				for (; i < input.length() && Character.isDigit(input.charAt(i)); i++) {
					count *= 10;
					count += input.charAt(i) - '0';
				}
				i--;
			} else if (val == '+') {
				output.append(output.charAt(output.length() - 1));
			} else if (val == '%') {
				int cursor = 0;
				i++;
				for (; i < input.length() && Character.isDigit(input.charAt(i)); i++) {
					cursor *= 10;
					cursor += input.charAt(i) - '0';
				}
				i--;
				int end = output.length();
				int start = output.length() - cursor > 0 ? output.length() - cursor :  0;
				output.append(output.subSequence(start, end));
			}
		}
		
		
		return output.toString();
	}
	
}
