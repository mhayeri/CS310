package data_structures.prog_4;

import java.util.Iterator;

public class PhoneNumber implements Comparable<PhoneNumber> {
    String areaCode, prefix, number;
    String phoneNumber;

    /**
     * 
     * @param n The parameter is a phone number in the form xxx-xxx-xxxx, which is
     *          area code - prefix - number. The phone number is validate, and an
     *          IllegalArgumentException is thrown if it is invalid.
     */
    public PhoneNumber(String n) {
        verify(n);
        areaCode = n.substring(0, 3);
        prefix = n.substring(4, 7);
        number = n.substring(8);
    }

    public int compareTo(PhoneNumber number) {
        return phoneNumber.compareTo(number.phoneNumber);
    }

    /**
     * Returns an int representing the hash code of the phone number.
     */
    public int hashCode() {
        byte[] b = phoneNumber.getBytes();
        long hashVal = 0;
        for (int i = b.length - 1; i >= 0; i--) {
            hashVal = (hashVal << 5) + b[i];
        }
        return (int) hashVal;
    }

    /**
     * 
     * @param n Privte method to validate the Phone Number.
     */
    private void verify(String n) {
        if ((n.length() != 12) || ((n.charAt(3) != '-') || (n.charAt(7) != '-')))
            throw new IllegalArgumentException();

        byte[] b = n.getBytes();
        for (int i = 0; i < b.length; i++) {
            if (i == 3 || i == 7)
                continue;
            if (b[i] < 0x30 || b[i] > 0x39)
                throw new IllegalArgumentException();
        }
    }

    public String getAreaCode() {
        return areaCode;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getNumber() {
        return number;
    }

    public String toString() {
        return phoneNumber;
    }

}
