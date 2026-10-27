package br.com.fooddelivery.identity;

public final class Cpf {
    private Cpf() {}

    public static boolean isValid(String value) {
        if (value == null) return false;
        String cpf = value.replaceAll("\\D", "");
        if (!cpf.matches("\\d{11}") || cpf.chars().distinct().count() == 1) return false;
        int first = checkDigit(cpf.substring(0, 9), 10);
        int second = checkDigit(cpf.substring(0, 9) + first, 11);
        return cpf.charAt(9) - '0' == first && cpf.charAt(10) - '0' == second;
    }

    private static int checkDigit(String value, int weight) {
        int sum = 0;
        for (char digit : value.toCharArray()) sum += (digit - '0') * weight--;
        int remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }
}
