package android.example.com.split.data.exception;

public class StringArgumentLengthException extends Exception {

    private int maxLength;

    public StringArgumentLengthException(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMaxLength() {
        return maxLength;
    }
}
