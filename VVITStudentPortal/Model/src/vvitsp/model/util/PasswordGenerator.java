package vvitsp.model.util;

import java.math.BigInteger;

import java.security.SecureRandom;

public final class PasswordGenerator {

    private SecureRandom random = new SecureRandom();

    public PasswordGenerator() {
        super();
    }

    public String newPassword() {
        return new BigInteger(50, random).toString(32);
    }

    public static void main(String[] args) {
        PasswordGenerator p = new PasswordGenerator();
        System.out.println(p.newPassword());
        System.out.println(p.newPassword());
        System.out.println(p.newPassword());
        System.out.println(p.newPassword());
        System.out.println(p.newPassword());
        System.out.println(p.newPassword());
        System.out.println(p.newPassword());
        System.out.println(p.newPassword());
    }
}
