package br.com.fooddelivery.identity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CnpjTest {
    @Test void acceptsValidCnpjInFormattedOrPlainForm(){assertTrue(Cnpj.isValid("11.222.333/0001-81"));assertTrue(Cnpj.isValid("11222333000181"));}
    @Test void rejectsInvalidAndRepeatedDigits(){assertFalse(Cnpj.isValid("11.222.333/0001-80"));assertFalse(Cnpj.isValid("00.000.000/0000-00"));assertFalse(Cnpj.isValid("123"));}
}
