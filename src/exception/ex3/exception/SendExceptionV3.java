package exception.ex3.exception;

public class SendExceptionV3 extends NetworkClientExceptionV3{

    private final String sendData;

    public String getSendData() {
        return sendData;
    }

    public SendExceptionV3(String sendData, String message) {
        super(message);
        this.sendData = sendData;
    }
}
