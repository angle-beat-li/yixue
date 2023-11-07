package com.yixue.base.exception;

/**
 * yixueException$
 *
 * @author liy
 * @date 2023/11/7$
 */
public class YixueException extends RuntimeException{
    private String errMessage;

    public YixueException() {
        super();
    }

    public YixueException(String errMessage) {
        super(errMessage);
        this.errMessage = errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public static void cast(CommonError commonError){
        throw new YixueException(commonError.getErrMessage());
    }
    public static void cast(String errMessage){
        throw new YixueException(errMessage);
    }
}

