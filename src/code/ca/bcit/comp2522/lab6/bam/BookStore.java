package ca.bcit.comp2522.lab6.bam;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Collections;

/**
 * Represents a bookstore that holds a collection of literature items.
 *
 * @author Ben Nguyen
 * @author Andre Dizon
 * @author Marcus Lages
 *
 * @version 1.0
 */
public class BookStore<T extends Literature> {

    private static final int MIN_AMOUNT_BOOKS = 0;
    private static final double ZERO_CHANCE = 0.0;
    private static final double RATIO_TO_PERCENTAGE = 100.0;
    private static final int CONTAINS_NOTHING = 0;
    private static final int END_YEAR = 9;
    private static final int EMPTY_TOTAL_LENGTH = 0;

    private final String name;
    private final List<T> items;
    private final Map<String, List<T>> itemMap;

    /**
     * Constructs a new Bookstore object with the specified name.
     *
     * @param name the name of the bookstore
     */
    public BookStore(final String name) {

        this.name = name;
        this.items = new ArrayList<>();
        this.itemMap = new HashMap<>();
    }

    /**
     * Provides a structure for representing and displaying information about a bookstore,
     * such as its name and the number of items it holds.
     */
    static class BookStoreInfo {

        /**
         * Displays information about the bookstore.
         *
         * @param storeName the name of the bookstore
         * @param itemCount the number of items in the bookstore
         */
        public void displayInfo(final String storeName,
                                final int itemCount) {

            System.out.println("BookStore: " + storeName + ", Items: " + itemCount);
        }
    }

    /**
     * Provides functionality for maintaining a list of items and calculating statistical data,
     * such as the average title length of those items.
     */
    class NovelStatistics {

        /**
         * Calculates the average length of the titles of the items in the bookstore.
         *
         * @return the average title length, or 0 if the bookstore is empty
         */
        public double averageTitleLength() {

            int totalLength;
            totalLength = EMPTY_TOTAL_LENGTH;

            for (final T item : items) {

                totalLength += item.getTitle().length();
            }

            if (items.isEmpty()) {

                return EMPTY_TOTAL_LENGTH;
            }

            return (double) totalLength / items.size();
        }
    }

    /**
     * Adds an item to the bookstore.
     *
     * @param item the literary item to add
     * @throws IllegalArgumentException if the item is null
     */
    public void addItem(final T item) {

        if (item == null) {

            throw new IllegalArgumentException("Item cannot be null.");
        }

        items.add(item);

        // For items that have an author (e.g., Novel), add to the map
        if (item instanceof Novel novel) {

            final String author;
            author = novel.getAuthorName();
            itemMap.computeIfAbsent(author, k -> new ArrayList<>()).add(item);
        }
    }

    /**
     * Prints the titles of all items in the bookstore.
     */
    public void printItems() {

        for (final T item : items) {

            if (item != null) {

                System.out.println(item.getTitle());
            }
        }
    }

    /**
     * Prints the titles of all books in the bookstore in uppercase.
     */
    public void printAllTitles() {

        for (final T item : items) {

            final String title;
            title = item.getTitle();

            if (title != null) {

                System.out.println(title.toUpperCase());
            }
        }
    }

    /**
     * Prints titles that contain the specified keyword (case-insensitive).
     *
     * @param keyword the keyword to search for in titles
     * @throws IllegalArgumentException if the keyword is null
     */
    public void printBookTitle(final String keyword) {

        if (keyword == null) {

            throw new IllegalArgumentException("Keyword cannot be null");
        }

        items.forEach((item) -> {

            if (item.getTitle().toLowerCase().contains(keyword.toLowerCase())) {

                System.out.println(item.getTitle());
            }
        });
    }

    /**
     * Prints the titles of all books in alphabetical order.
     */
    public void printTitlesInAlphaOrder() {

        final List<T> itemsCopy;
        itemsCopy = new ArrayList<>(items);

        // Sort based on title
        itemsCopy.sort(Comparator.comparing(T::getTitle, String::compareToIgnoreCase));

        itemsCopy.forEach((item) -> {

            final String title;
            title = item.getTitle();

            if (title != null) {

                final String titleUpperCase;
                titleUpperCase = title.toUpperCase();

                System.out.println(title);
            }
        });
    }

    /**
     * Prints the longest book title.
     */
    public void getLongest() {

        String longestTitle;
        longestTitle = null;

        for (final T item : items) {

            if (item != null) {

                if (longestTitle == null) {

                    longestTitle = item.getTitle();
                    continue;
                }

                final String title = item.getTitle();

                if (title.length() > longestTitle.length()) {

                    longestTitle = title;
                }
            }
        }

        System.out.println(longestTitle);
    }

    /**
     * Checks if there's any book published in the specified year in the catalog.
     *
     * @param yearPublished the year to search for
     * @return {
     * @code true} if a book from that year is found, {
     * @code false} otherwise
     */
    public boolean isThereABookWrittenIn(final int yearPublished) {

        for (final T item : items) {

            if (item != null) {

                if (item instanceof Novel novel) {

                    if (novel.getYearPublished() == yearPublished) {

                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Counts how many books contain the specified word in their titles.
     *
     * @param word the word to search for
     * @return the count of books containing the word
     * @throws IllegalArgumentException if the word is null or blank
     */
    public int howManyBooksContain(final String word) {

        if (word == null) {

            throw new IllegalArgumentException("Word cannot be null.");
        }

        if (word.isBlank()) {

            throw new IllegalArgumentException("Word cannot be blank.");
        }

        int count;
        count = CONTAINS_NOTHING;

        for (final T item : items) {

            if (item.getTitle().toLowerCase().contains(word.toLowerCase())) {

                count++;
            }
        }

        return count;
    }

    /**
     * Calculates the percentage of books published between {
     * @code startYear} and {
     * @code endYear}.
     *
     * @param startYear start of the interval (inclusive)
     * @param endYear   end of the interval (inclusive)
     * @return the percentage of books published in the interval
     */
    public double whichPercentWrittenBetween(final int startYear,
                                             final int endYear) {

        // A larger start year than end year is invalid, chance is ZERO_CHANCE
        if (startYear > endYear) {

            return ZERO_CHANCE;
        }

        final int totalBooks;
        totalBooks = items.size();

        if (totalBooks == MIN_AMOUNT_BOOKS) {

            return ZERO_CHANCE;
        }

        int booksInRange;
        booksInRange = MIN_AMOUNT_BOOKS;

        for (final T item : items) {

            if (item != null) {

                if (item instanceof Novel novel) {

                    final int year = novel.getYearPublished();

                    if (year >= startYear && year <= endYear) {


                        booksInRange++;
                    }
                }
            }
        }

        return ((double) booksInRange / totalBooks) * RATIO_TO_PERCENTAGE;
    }

    /**
     * Prints the list of books by the specified decade.
     *
     * @param decade the starting year of the decade (e.g., 1990 for the 1990s)
     */
    public void printGroupByDecade(final int decade) {

        final int endYear = decade + END_YEAR;

        final StringBuilder sb;
        sb = new StringBuilder();

        sb.append("Books from the ")
                .append(decade)
                .append("s:\n");

        boolean booksFound;
        booksFound = false;

        for (final T item : items) {

            if (item != null) {

                if (item instanceof Novel novel) {

                    final int year = novel.getYearPublished();

                    if (year >= decade && year <= endYear) {

                        sb.append("- ")
                                .append(item.getTitle())
                                .append(" (")
                                .append(year)
                                .append(")\n");
                        booksFound = true;
                    }
                }
            }
        }
        if (!booksFound) {

            sb.append("No books in this decade.\n");
        }
        System.out.println(sb.toString());
    }

    /**
     * Returns the oldest book in the store.
     *
     * @return the oldest book, or {
     * @code null} if the store is empty
     */
    public T getOldestBook() {

        T oldestItem;
        int oldestYear;

        oldestItem = null;
        oldestYear = Integer.MAX_VALUE;

        for (final T item : items) {

            if (item != null) {

                if (item instanceof Novel novel) {

                    if (novel.getYearPublished() < oldestYear) {

                        oldestYear = novel.getYearPublished();
                        oldestItem = item;
                    }
                }
            }
        }

        return oldestItem;
    }

    /**
     * Prints all titles using an iterator.
     */
    private void printAllTitlesUsingIterator() {

        System.out.println("\nAll Titles in the BookStore:");

        for (final T item : items) {

            if (item != null) {

                System.out.println(item.getTitle());
            }
        }
    }

    /**
     * Removes all items whose title contains the word "the" (case-insensitive).
     */
    private void removeItemsWithTitleContainingThe() {

        System.out.println("\nRemoving items with titles containing \"the\"...");

        final Iterator<T> iterator;
        iterator = items.iterator();

        while (iterator.hasNext()) {

            final T item;
            final String title;

            item = iterator.next();
            title = item.getTitle();

            if (title.toLowerCase().contains("the")) {

                iterator.remove();
                System.out.println("Removed: " + title);
            }
        }
    }

    /**
     * Prints the items sorted by title (excluding titles containing "the").
     */
    private void printSortedItems() {

        System.out.println("\nItems sorted by title (excluding titles containing \"the\"):");
        final List<String> sortedTitles;

        sortedTitles = new ArrayList<>();

        for (final T item : items) {

            if (item != null) {

                if (!item.getTitle().toLowerCase().contains("the")) {

                    sortedTitles.add(item.getTitle());
                }
            }
        }

        sortedTitles.sort(String.CASE_INSENSITIVE_ORDER);

        for (final String title : sortedTitles) {

            System.out.println(title);
        }
    }

    /**
     * Retrieves all books with titles of a specific length.
     *
     * @param titleLength the desired length of the title
     * @return a list of books with titles matching the specified length
     */
    public List<T> getBooksThisLength(final int titleLength) {

        final List<T> titles;
        titles = new ArrayList<>();

        if (items != null) {

            for (final T item : items) {

                if (item != null) {

                    if (item.getTitle().length() == titleLength) {

                        titles.add(item);
                    }
                }
            }
        }
        return titles;
    }

    /**
     * Adds novels from the bookstore's items to the provided collection.
     *
     * @param novelCollection the collection to which novels will be added
     */
    public void addNovelsToCollection(final List<? super Novel> novelCollection) {

        if (items != null && novelCollection != null) {

            for (final T item : items) {

                if (item != null) {

                    if (item instanceof Novel) {

                        novelCollection.add((Novel) item);
                    }
                }
            }
        }
    }

    /**
     * Getter for the items list.
     *
     * @return an unmodifiable view of the items list
     */
    public List<T> getItems() {

        return Collections.unmodifiableList(items);
    }

    /**
     * Main method for testing the {
     * @code BookStore} class.
     *
     * @param args command-line arguments
     */
    public static void main(final String[] args) {

        final BookStore<Literature> store;
        final List<Novel> novelList;

        store = new BookStore<>("Diverse Literature Collection");

        store.addItem(new ComicBook("The Amazing Spider-Man"));
        store.addItem(new Magazine("National Geographic"));
        store.addItem(new Novel("War and Peace", "Leo Tolstoy", 1867));

        novelList = new ArrayList<>();

        System.out.println("Printing all items in the store:");
        store.printItems();

        System.out.println("\nAll Titles in UPPERCASE:");
        store.printAllTitles();

        System.out.println("\nBook Titles Containing 'the':");
        store.printBookTitle("the");

        System.out.println("\nAll Titles in Alphabetical Order:");
        store.printTitlesInAlphaOrder();

        System.out.println("\nLongest Book Title:");
        store.getLongest();

        System.out.println("\nIs there a book written in 1869?");
        System.out.println(store.isThereABookWrittenIn(1869));

        System.out.println("\nHow many books contain 'Peace'?");
        System.out.println(store.howManyBooksContain("Peace"));

        System.out.println("\nPercentage of books written between 1800 and 2000:");
        System.out.println(store.whichPercentWrittenBetween(1800, 2000) + "%");

        System.out.println("\nOldest book:");
        System.out.println(store.getOldestBook());

        System.out.println("\nNovels in bookstore:");
        store.addNovelsToCollection(novelList);
        System.out.println(novelList);

        System.out.println("\nBooks with titles 15 characters long:");
        List<Literature> fifteenCharTitles = store.getBooksThisLength(15);
        fifteenCharTitles.forEach(item -> System.out.println(item.getTitle()));
    }
}
