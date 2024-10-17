package ca.bcit.comp2522.lab6.bam;

/**
 * Represents a Comic book with a title
 * Extends the literature abstract class.
 *
 * @author Andre Bernard Chang Dizon
 * @author Ben Nguyen
 * @author Marcus Vinicius Santos Lages
 *
 * @version 1.0
 */
public class ComicBook extends Literature {

    private final String title;

    public ComicBook(final String title) {

        this.title = title;
    }


    @Override
    public String getTitle() {

        return title;
    }
}