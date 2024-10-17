package ca.bcit.comp2522.lab6.bam;

/**
 * Represents a novel with a title, author's name, and year of publication.
 * Implements the Comparable interface to allow novels to be compared based on their titles.
 *
 * @author Andre Bernard Chang Dizon
 * @author Ben Nguyen
 * @author Marcus Vinicius Santos Lages
 *
 * @version 1.0
 */
public class Novel
        extends Literature
        implements Comparable<Novel> {

    private final String title;
    private final String authorName;
    private final int yearPublished;

    public Novel(final String title,
                 final String authorName,
                 final int yearPublished) {

        this.title = title;
        this.authorName = authorName;
        this.yearPublished = yearPublished;
    }

    @Override
    public String getTitle() {

        return title;
    }

    public String getAuthorName() {

        return authorName;
    }

    public int getYearPublished() {

        return yearPublished;
    }

    /**
     * Compares a novel to another novel based on their title (alphabetically).
     *
     * @param novel the object to be compared.
     * @return 0: if they have the same title,
     *         less than 0: if this novel comes before than the specified object (alphabetically)
     *         greater than 0: if this novel comes after than the specified object (alphabetically)
     */
    @Override
    public int compareTo(final Novel novel) {

        final int comparison;
        comparison = title.compareTo(novel.title);

        return comparison;
    }
}
