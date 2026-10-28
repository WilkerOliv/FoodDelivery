package br.com.fooddelivery.identity;

public final class Cnpj {
    private Cnpj() {}
    public static String normalize(String value) { return value == null ? null : value.replaceAll("\\D", ""); }
    public static boolean isValid(String value) {
        String c = normalize(value);
        if (c == null || !c.matches("\\d{14}") || c.chars().distinct().count() == 1) return false;
        int[] w1={5,4,3,2,9,8,7,6,5,4,3,2}, w2={6,5,4,3,2,9,8,7,6,5,4,3,2};
        return digit(c.substring(0,12),w1)==c.charAt(12)-'0' && digit(c.substring(0,13),w2)==c.charAt(13)-'0';
    }
    private static int digit(String value,int[] weights) { int sum=0;for(int i=0;i<weights.length;i++)sum+=(value.charAt(i)-'0')*weights[i];int r=sum%11;return r<2?0:11-r; }
}
