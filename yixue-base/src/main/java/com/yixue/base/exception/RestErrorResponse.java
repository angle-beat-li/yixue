package com.yixue.base.exception;

import java.io.Serializable;

/**
 * RestErrorResponse$
 *
 * @author liy
 * @date 2023/11/7$
 */
public class RestErrorResponse implements Serializable {

    private String errMessage;

    public RestErrorResponse(String errMessage){
        this.errMessage= errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}

