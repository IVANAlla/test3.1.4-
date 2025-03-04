package web6.Exception;

import org.springframework.dao.DataIntegrityViolationException;

public class UserUsernameExistException extends DataIntegrityViolationException {
    public UserUsernameExistException(String msg) {
        super(msg);
    }
}
