package lab1;

public final class Obfuscator {

    private static String SOURCE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 abcdefghijklmnopqrstuvwxyz";
    private static String TARGET = "Q5A8ZWS0XEDC6RFVT9GBY4HNU3J2MI1KO7LP' )qop^hvgwm{kbacye}=z?,.`s";

    public static String obfuscate(String s) {
        char[] result = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int index = SOURCE.indexOf(c);
            result[i] = TARGET.charAt(index);
        }
        return new String(result);
    }

    public static String unobfuscate(String s) {
        char[] result = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int index = TARGET.indexOf(c);
            result[i] = SOURCE.charAt(index);
        }
        return new String(result);
    }

    private Obfuscator(){

    }
}
