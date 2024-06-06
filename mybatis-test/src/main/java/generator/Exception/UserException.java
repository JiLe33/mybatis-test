package generator.Exception;

public class UserException extends Exception {
    // 用户自定义异常类
    public UserException() {

    }
    public UserException(String message) {
        super(message);
    }
}
