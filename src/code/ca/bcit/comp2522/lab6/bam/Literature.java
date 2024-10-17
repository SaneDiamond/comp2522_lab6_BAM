package ca.bcit.comp2522.lab6.bam;

/**
 * Represents Literature with a title
 * *
 * @author Andre Bernard Chang Dizon
 * @author Ben Nguyen
 * @author Marcus Vinicius Santos Lages
 *
 * @version 1.0
 */
public abstract class Literature {

    public abstract String getTitle();

    @Override
    public String toString() {

        return getTitle();
    }
}
