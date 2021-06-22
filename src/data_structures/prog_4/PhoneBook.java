package data_structures.prog_4;

import java.util.Iterator;
import java.io.*;

public class PhoneBook<K extends Comparable<K>, V> {
    private int size;
    private DictionaryADT<K, V> phoneBook;

    public PhoneBook(int maxSize) {
        size = maxSize;
        phoneBook = new BalancedTree<>();
        // new Hashtable<>(size);
        // new BinarySearchTree<>();
    }

    /**
     * 
     * @param filename Reads PhoneBook data from a text file and loads the data into
     *                 the PhoneBook. Data is in the form "key=value" where a
     *                 phoneNumber is the key and a name in the format "Last, First"
     *                 is the value.
     */
    public void load(String filename) {
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null) {
                PhoneNumber keyNumber = new PhoneNumber(line.substring(0, 12));
                String valueName = line.substring(13);
                addEntry(keyNumber, valueName);

            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param number
     * @return String Returns the name associated with the given PhoneNumber, if it
     *         is in the PhoneBook, null if it is not.
     */
    public String numberLookup(PhoneNumber number) {
        return phoneBook.getValue((K) number).toString();
    }

    /**
     * 
     * @param name
     * @return PhoneNumber Returns the PhoneNumber associated with the given name
     *         value. There may be duplicate values, return the first one found.
     *         Return null if the name is not in the PhoneBook.
     */
    public PhoneNumber nameLookup(String name) {
        return (PhoneNumber) phoneBook.getKey((V) name);
    }

    /**
     * 
     * @param number
     * @param name
     * @return boolean Adds a new PhoneNumber = name pair to the PhoneBook. All
     *         names should be in the form "Last, First". Duplicate entries are
     *         *not* allowed. Return true if the insertion succeeds otherwise false
     *         (PhoneBook is full or the new record is a duplicate). Does not change
     *         the datafile on disk.
     */
    public boolean addEntry(PhoneNumber number, String name) {
        if (phoneBook.isFull() || phoneBook.getValue((K) number) != null) {
            return false;
        }
        return (phoneBook.add((K) number, (V) name));
    }

    /**
     * 
     * @param number
     * @return boolean Deletes the record associated with the PhoneNumber if it is
     *         in the PhoneBook. Returns true if the number was found and its record
     *         deleted, otherwise false. Does not change the datafile on disk.
     */
    public boolean deleteEntry(PhoneNumber number) {
        return phoneBook.delete((K) number);
    }

    /**
     * Prints a directory of all PhoneNumbers with their associated names, in sorted
     * order (ordered by PhoneNumber).
     */
    public void printAll() {
        Iterator<K> directory = phoneBook.keys();
        while (directory.hasNext()) {
            K temp = directory.next();
            System.out.println(temp.toString() + ": " + phoneBook.getValue(temp));
        }
    }

    /**
     * 
     * @param code Prints all records with the given Area Code in ordered sorted by
     *             PhoneNumber.
     */
    public void printByAreaCode(String code) {
        Iterator<K> areaCodeIterator = phoneBook.keys();
        while (areaCodeIterator.hasNext()) {
            PhoneNumber tempNumber = (PhoneNumber) areaCodeIterator.next();
            if (code.compareTo(tempNumber.areaCode) == 0)
                System.out.println(tempNumber.toString() + ": " + phoneBook.getValue((K) tempNumber));
        }
    }

    /**
     * Prints all of the names in the directory, in sorted order (by name, not by
     * number). There may be duplicates as these are the values.
     */
    public void printNames() {
        Iterator<String> nameDirectory = phoneBook.values();
        while (nameDirectory.hasNext()) {
            System.out.println(nameDirectory.next());
        }
    }

    public static void main(String[] args) {
        PhoneBook book = new PhoneBook(10000);

        book.load("phonenumber.txt");
        book.printByAreaCode("279");

        System.out.println("--------------------------------------------------------");
        // book.printAll();
        // System.out.println("--------------------------------------------------------");
        // book.printNames();
    }
}
