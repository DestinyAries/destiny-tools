package com.destiny.common.exception.handler;

import com.destiny.common.constant.SymbolConstant;
import lombok.Data;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * record error info
 * @Author Destiny
 * @Version 1.0.0
 */
@Data
public class BindErrorInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * the location of the affected object
     * or the affected object's field
     */
    private String affectedLocation;
    /**
     * the error message
     */
    private String message;
    /**
     * the rejected value from object's field
     */
    private Object rejectedValue;

    public BindErrorInfo() {
    }

    public BindErrorInfo(String affectedLocation, String message) {
        this.affectedLocation = affectedLocation;
        this.message = message;
    }

    public BindErrorInfo(String affectedLocation, String message, Object rejectedValue) {
        this.affectedLocation = affectedLocation;
        this.message = message;
        this.rejectedValue = rejectedValue;
    }

    /**
     * Get the error info from {@link BindingResult}
     * @param bindingResult
     * @return
     */
    public static String getInfoByBindingResult(BindingResult bindingResult) {
        List<BindErrorInfo> errorInfoList = new ArrayList<>(10);
        bindingResult.getAllErrors().forEach(error -> {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                errorInfoList.add(new BindErrorInfo(fieldError.getObjectName() + SymbolConstant.POINT_CHAR + fieldError.getField(),
                        error.getDefaultMessage(), fieldError.getRejectedValue()));
            } else {
                errorInfoList.add(new BindErrorInfo(error.getObjectName(), error.getDefaultMessage()));
            }
        });
        return errorInfoList.toString();
    }
}
