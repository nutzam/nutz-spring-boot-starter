package tech.riemann.nutz.demo.exception;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 */
public class BizException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @param message
     */
    private BizException(String message) {
        super(message);
    }

    private BizException() {}

    public static BizException create(String message) {
        return new BizException(message);
    }

}
