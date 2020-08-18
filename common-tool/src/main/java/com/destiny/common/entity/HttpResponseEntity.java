package com.destiny.common.entity;

import com.destiny.common.enumeration.GlobalServerCodeEnum;
import com.destiny.common.enumeration.ServerCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.io.Serializable;
import java.util.List;

/**
 * HTTP response entity
 * @Author Destiny
 * @Version 1.0.0
 */
@ApiModel(value = "HttpResponseEntity", description = "header-http Status Code；body-response body")
public class HttpResponseEntity<T> extends ResponseEntity<ResultEntity> implements Serializable {
    private static final long serialVersionUID = 1L;

    public HttpResponseEntity(HttpStatus status) {
        super(status);
    }

    public HttpResponseEntity(ResultEntity body) {
        super(body, HttpStatus.OK);
    }

    public HttpResponseEntity(ResultEntity body, HttpStatus status) {
        super(body, status);
    }

    public HttpResponseEntity(MultiValueMap<String, String> headers, HttpStatus status) {
        super(headers, status);
    }

    public HttpResponseEntity(ResultEntity body, MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
    }

    public static HttpResponseEntity success() {
        return new HttpResponseEntity(ResultEntity.success(), HttpStatus.OK);
    }

    public static <T> HttpResponseEntity<T> success(T data) {
        return new HttpResponseEntity(ResultEntity.success(data));
    }

    public static <T> HttpResponseEntity<T> success(List<T> list) {
        return new HttpResponseEntity(ResultEntity.success(list));
    }

    public static <T> HttpResponseEntity<T> success(PageInfo pageInfo, Class<T> targetClass) {
        return new HttpResponseEntity(ResultEntity.success(pageInfo, targetClass));
    }

    public static <T> HttpResponseEntity<T> success(ResultEntity<T> resultEntity) {
        return new HttpResponseEntity(resultEntity);
    }

    /**
     * the client request success, but server encounter an exception, this
     * exception is known and defined.
     * @param serverCode
     * @return
     */
    public static HttpResponseEntity failure(ServerCode serverCode) {
        return new HttpResponseEntity(ResultEntity.failure(serverCode));
    }

    /**
     * the client request success, but server encounter an exception, this
     * exception is known and defined.
     * @param resultEntity
     * @return
     */
    public static HttpResponseEntity failure(ResultEntity resultEntity) {
        return new HttpResponseEntity(resultEntity);
    }

    /**
     * the client request success, but server encounter an exception, this
     * exception is known and defined.
     * @param serverCode
     * @return
     */
    public static HttpResponseEntity failure(ServerCode serverCode, String message) {
        return new HttpResponseEntity(ResultEntity.failure(serverCode, message));
    }

    /**
     * the client request error
     * @param serverCode
     * @return
     */
    public static HttpResponseEntity clientError(ServerCode serverCode) {
        return new HttpResponseEntity(ResultEntity.failure(serverCode), HttpStatus.BAD_REQUEST);
    }

    /**
     * the client request is unauthorized
     * @return
     */
    public static HttpResponseEntity clientUnauthorized() {
        return new HttpResponseEntity(ResultEntity.failure(GlobalServerCodeEnum.REQUEST_UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }

    /**
     * the signature of client request is invalid.
     * @return
     */
    public static HttpResponseEntity clientSignatureError() {
        return new HttpResponseEntity(ResultEntity.failure(GlobalServerCodeEnum.REQUEST_SIGNATURE_ERROR), HttpStatus.FORBIDDEN);
    }

    /**
     * the client request is forbidden
     * @param serverCode
     * @return
     */
    public static HttpResponseEntity clientForbidden(ServerCode serverCode) {
        return new HttpResponseEntity(ResultEntity.failure(serverCode), HttpStatus.FORBIDDEN);
    }

    /**
     * server error
     * @return
     */
    public static HttpResponseEntity serverError() {
        return new HttpResponseEntity(ResultEntity.failure(GlobalServerCodeEnum.API_HANDLE_EXCEPTION), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Return the HTTP status code of the response.
     * @return the HTTP status as an HttpStatus enum entry
     */
    @JsonIgnore
    @Override
    public HttpStatus getStatusCode() {
        return super.getStatusCode();
    }

    /**
     * Return the HTTP status code of the response.
     * @return the HTTP status as an int value
     */
    @JsonIgnore
    @Override
    public int getStatusCodeValue() {
        return super.getStatusCodeValue();
    }

}
