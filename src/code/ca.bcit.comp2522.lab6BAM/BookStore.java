package ca.bcit.comp2522.lab6BAM;

import java.util.*;

public class BookStore<T extends Literature> {
    private static final int MIN_AMOUNT_BOOKS = 0;
    private static final double ZERO_CHANCE = 0.0;
    private static final double RATIO_TO_PERCENTAGE = 100.0;
    private static final int CONTAINS_NOTHING = 0;
    private static final int END_YEAR = 9;
    private static final int EMPTY_total_LENGTH = 0;

    private final String name;
    private final List<T> items;
    private final Map<String, List<T>> itemMap;

    public BookStore(final String name) {
        this.name = name;
        this.items = new ArrayList<>();
        this.itemMap = new HashMap<>();
    }

    /**
     * provides a structure for representing and displaying information about a bookstore,
     * such as its name and the number of items it holds.
     */
    static class BookStoreInfo {
        public void displayInfo(final String storeName, final int itemCount) {
            System.out.println("BookStore: " + storeName + ", Items: " + itemCount);
        }
    }
    /**
     * provides functionality for maintaining a list of items and calculating statistical data,
     * such as the average title length of those items.
     *
     * @param <T> the type of items in the list, which must provide a {@code getTitle()} method
     */
    class NovelStatistics {
        final private List<T> items;
        final int totalLength;

        items = new ArrayList<>();
        totalLength = EMPTY_TOTAL_LENGTH;

        public double averageTitleLength() {
            for (T item : items) {
                totalLength += item.getTitle().length();
            }
            return (double) totalLength / items.size();
        }
    }

    /**
     * Adds an item to the bookstore.
     *
     * @param item The literary item to add.
     */
    public void addItem(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }
        items.add(item) ;

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
            System.out.println(item.getTitle());
        }
    }

    /**
     * Prints the title of all books in the bookstore in uppercase.
     */
    public void printAllTitles() {
        for (final T item : items) {
            final String title;
            title = item.getTitle();
            System.out.println(title.toUpperCase());
        }
    }

    /**
     * Prints titles that contain the word "the" (case-insensitive).
     *
     * @param keyword The keyword to search for in titles.
     */
    public void printBookTitle(final String keyword) {
        items.forEach((item) -> {
            if(item.getTitle().contains(keyword)) {
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

        itemsCopy.forEach((item) -> item.getTitle().toUpperCase());
    }

    /**
     * Prints the longest book title.
     */
    public void getLongest() {
        String longestTitle = "";

        for (final T item : items) {
            if (item != null) {
                final String title;
                title = item.getTitle();
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
     * @param yearPublished Year to search for.
     * @return true if found, false otherwise.
     */
    public boolean isThereABookWrittenIn(final int yearPublished) {
        for (final T item : items) {
            if (item instanceof Novel novel) {
                if (novel.getYearPublished() == yearPublished) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Counts how many books contain the specified word in their titles.
     *
     * @param word The word to search for.
     * @return The count of books containing the word.
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
     * Calculates the percentage of books published between startYear and endYear.
     *
     * @param startYear Start of the interval (inclusive).
     * @param endYear   End of the interval (inclusive).
     * @return The percentage of books published in the interval.
     */
    public double whichPercentWrittenBetween(final int startYear, final int endYear) {
        if (startYear > endYear) {
            return ZERO_CHANCE;
        }

        final int totalBooks;
        totalBooks = items.size();
        if (totalBooks == 0) {
            return ZERO_CHANCE;
        }

        int booksInRange = MIN_AMOUNT_BOOKS;

        for (final T item : items) {
            if (item instanceof Novel novel) {
                final int year;
                year = novel.getYearPublished();
                if (year >= startYear && year <= endYear) {
                    booksInRange++;
                }
            }
        }

        return ((double) booksInRange / totalBooks) * RATIO_TO_PERCENTAGE;
    }

    /**
     * Prints the list of books by the specified decade.
     *
     * @param decade The starting year of the decade (e.g., 1990 for the 1990s).
     */
    public void printGroupByDecade(final int decade) {
        final int endYear;
        endYear = decade + END_YEAR;

        final StringBuilder sb;
        sb = new StringBuilder();
        sb.append("Books from the ")
                .append(decade)
                .append("s:\n");

        boolean booksFound = false;

        for (final T item : items) {
            if (item instanceof Novel novel) {
                final int year;
                year = novel.getYearPublished();
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

        if (!booksFound) {
            sb.append("No books in this decade.\n");
        }

        System.out.println(sb.toString());
    }

    /**
     * Returns the oldest book in the store.
     *
     * @return The oldest book, or null if the store is empty.
     */
    public T getOldestBook() {
        T oldestItem;
        int oldestYear;

        oldestItem = null;
        oldestYear = Integer.MAX_VALUE;

        for (final T item : items) {
            if (item instanceof Novel novel) {
                if (novel.getYearPublished() < oldestYear) {
                    oldestYear = novel.getYearPublished();
                    oldestItem = item;
                }
            }
        }

        return oldestItem;
    }

    /**
     * Prints all titles using an Iterator.
     */
    private void printAllTitlesUsingIterator() {
        System.out.println("\nAll Titles in the BookStore:");

        for (final T item : items) {
            System.out.println(item.getTitle());
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
        List<String> sortedTitles;
        sortedTitles = new ArrayList<>();

        for (final T item : items) {
            if (!item.getTitle().toLowerCase().contains("the")) {
                sortedTitles.add(item.getTitle());
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
     * @param titleLength The desired length of the title.
     * @return A list of books with titles matching the specified length.
     */
    public List<T> getBooksThisLength(final int titleLength) {
        final List<T> titles;
        titles = new ArrayList<>();

        for (final T item : items) {
            if (item.getTitle().length() == titleLength) {
                titles.add(item);
            }
        }
        return titles;
    }

    /**
     * Getter for the items list.
     *
     * @return An unmodifiable view of the items list.
     */
    public List<T> getItems() {
        return Collections.unmodifiableList(items);
    }

    // Optional: Main method for testing
    public static void main(final String[] args) {
        BookStore<Literature> store = new BookStore<>("Diverse Literature Collection");

        store.addItem(new Novel("War and Peace", "Leo Tolstoy", 1869));
        store.addItem(new ComicBook("Spider-Man"));
        store.addItem(new Magazine("National Geographic"));


        System.out.println("Printing all items in the store:");
        store.printItems();

        System.out.println("\nAll Titles in UPPERCASE:");
        store.printAllTitles();

        System.out.println("\nBook Titles Containing 'the':");
        store.printBookTitle("the");

        System.out.println("\nAll Titles in Alphabetical Order:");
        store.printTitlesInAlphaOrder();

        System.out.println("\nBooks from the 1800s:");
        store.printGroupByDecade(1800);

        System.out.println("\nLongest Book Title:");
        store.getLongest();

        System.out.println("\nIs there a book written in 1869?");
        System.out.println(store.isThereABookWrittenIn(1869));

        System.out.println("\nHow many books contain 'Peace'?");
        System.out.println(store.howManyBooksContain("Peace"));

        System.out.println("\nPercentage of books written between 1800 and 2000:");
        System.out.println(store.whichPercentWrittenBetween(1800, 2000) + "%");

        System.out.println("\nOldest book:");
        final Literature oldest;
        oldest = store.getOldestBook();
        if (oldest != null) {
            if (oldest instanceof Novel oldestNovel) {
                System.out.println(oldestNovel.getTitle() + " by " + oldestNovel.getAuthorName() + ", " +
                        oldestNovel.getYearPublished());
            } else {
                System.out.println(oldest.getTitle());
            }
        } else {
            System.out.println("No books available.");
        }

        System.out.println("\nBooks with titles 15 characters long:");
        List<Literature> fifteenCharTitles = store.getBooksThisLength(15);
        fifteenCharTitles.forEach(item -> System.out.println(item.getTitle()));
    }
}
