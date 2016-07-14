package my.personal.exception;

/**
 * Created by atifsaleem on 12/7/16.
 */
public class IllegalTimeException extends RuntimeException {
    private int seconds;

    public IllegalTimeException() {
        super();
    }

    public IllegalTimeException(String message, int seconds) {
        super(message);
        this.seconds = seconds;
    }

    public IllegalTimeException(String message, int seconds, Throwable cause) {
        super(message, cause);
        this.seconds = seconds;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " for time (in secs) :" + seconds;
    }

    public int getSeconds() {
        return seconds;
    }

}

