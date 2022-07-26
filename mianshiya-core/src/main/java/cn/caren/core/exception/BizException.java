package cn.caren.core.exception;


public class BizException extends RuntimeException{

    private final String code;

    private String errorMsg;

    public BizException(ExceptionType exceptionType,String errorMsg){
        super(exceptionType.getMessage());
        this.code = exceptionType.getCode();
        this.errorMsg = errorMsg;
    }

    public BizException(ExceptionType exceptionType){
        super(exceptionType.getMessage());
        this.code = exceptionType.getCode();
    }



    public String getCode() {
        return code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
