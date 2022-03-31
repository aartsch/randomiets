package nl.hu.cisq1.lingo.trainer.domain.exceptions;

public class WordDoesNotExistException extends RuntimeException {
    public WordDoesNotExistException(String message) {
        super(message);
    }
}
