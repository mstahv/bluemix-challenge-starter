package org.bluemix.challenge.backend;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A dummy data service that mimics a typical real world Java EE service. In
 your application, use DummyDataService.createDemoService() method to get a
 reference to class loader wide service.
 */
public class DummyDataService {

    private static DummyDataService instance;

    private final HashMap<Long, Customer> contacts = new HashMap<>();
    private long nextId = 0;

    /**
     * @return all available Customer objects.
     */
    public synchronized List<Customer> findAll() {
        return findAll(null);
    }

    /**
     * Finds all Customer's that match given filter.
     *
     * @param stringFilter filter that returned objects should match or
     * null/empty string if all objects should be returned.
     * @return list a Customer objects
     */
    public synchronized List<Customer> findAll(String stringFilter) {
        ArrayList arrayList = new ArrayList();
        for (Customer contact : contacts.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.
                        isEmpty())
                        || contact.toString().toLowerCase()
                        .contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(DummyDataService.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<Customer>() {

            @Override
            public int compare(Customer o1, Customer o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }

    /**
     * Finds all Customer's that match given filter and limits the resultset.
     *
     * @param stringFilter filter that returned objects should match or
     * null/empty string if all objects should be returned.
     * @param start the index of first result
     * @param maxresults maximum result count
     * @return list a Customer objects
     */
    public synchronized List<Customer> findAll(String stringFilter, int start,
            int maxresults) {
        ArrayList arrayList = new ArrayList();
        for (Customer contact : contacts.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.
                        isEmpty())
                        || contact.toString().toLowerCase()
                        .contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(DummyDataService.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<Customer>() {

            @Override
            public int compare(Customer o1, Customer o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        int end = start + maxresults;
        if (end > arrayList.size()) {
            end = arrayList.size();
        }
        return arrayList.subList(start, end);
    }

    /**
     * @return the amount of all customers in the system
     */
    public synchronized long count() {
        return contacts.size();
    }

    /**
     * Deletes a customer from a system
     *
     * @param value the Customer to be deleted
     */
    public synchronized void delete(Customer value) {
        contacts.remove(value.getId());
    }

    /**
     * Persists or updates customer in the system. Also assigns an identifier
     * for new Customer instances.
     *
     * @param entry
     */
    public synchronized void save(Customer entry) {
        if (entry.getId() == null) {
            entry.setId(nextId++);
        }
        try {
            entry = (Customer) entry.clone();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        contacts.put(entry.getId(), entry);
    }

    /**
     * Use this method to get a reference to the demo data service.
     *
     * @return the customer service reference
     */
    public static DummyDataService createDemoService() {
        if (instance == null) {

            final DummyDataService contactService = new DummyDataService();

            Random r = new Random(0);
            Calendar cal = Calendar.getInstance();
            for (int i = 0; i < 100; i++) {
                Customer contact = new Customer();
                contact.setFirstName(fnames[r.nextInt(fnames.length)]);
                contact.setLastName(lnames[r.nextInt(fnames.length)]);
                contact.setEmail(contact.getFirstName().toLowerCase() + "@"
                        + contact.getLastName().toLowerCase() + ".com");
                contact.setPhone("+ 358 555 " + (100 + r.nextInt(900)));
                cal.set(1930 + r.nextInt(70),
                        r.nextInt(11), r.nextInt(28));
                contact.setBirthDate(cal.getTime());
                contactService.save(contact);
            }
            instance = contactService;
        }

        return instance;
    }

    // Create dummy data by randomly combining first and last names
    static String[] fnames = {"Peter", "Alice", "John", "Mike", "Olivia",
        "Nina", "Alex", "Rita", "Dan", "Umberto", "Henrik", "Rene", "Lisa",
        "Linda", "Timothy", "Daniel", "Brian", "George", "Scott",
        "Jennifer"};
    static String[] lnames = {"Smith", "Johnson", "Williams", "Jones",
        "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor",
        "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin",
        "Thompson", "Young", "King", "Robinson"};

}
