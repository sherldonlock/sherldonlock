package com.nicechat.utils;

/**
 * 通用返回对象
 */
public class CommonResult<T> {
    private long resultCode;
    private String resultMessage;
    private T data;
    private String message;
    protected CommonResult() {
    }

    protected CommonResult(long code, String message, T data) {
        this.resultCode = code;
        this.resultMessage = message;
        this.data = data;
    }

    public CommonResult(Long resultCode, String resultMessage, String message){
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.message = message;
        this.data = null;
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 成功返回结果
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> success(String message) {
        if(message == null || "".equals(message)){
            message = ResultCode.SUCCESS.getMessage();
        }
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), message, null);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     * @param message 错误信息
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode, String message) {
        return new CommonResult<T>(errorCode.getCode(), message, null);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(ResultCode.FAILED.getCode(), message, null);
    }


    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> CommonResult<T> failed(String message,T data) {
        return new CommonResult<T>(ResultCode.FAILED.getCode(), message, data);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> CommonResult<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T> CommonResult<T> validateFailed(String message) {
        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T> CommonResult<T> validateFailed(String message, T data) {
        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message, data);
    }

    /**
     * 未登录返回结果
     */
    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未登录返回结果
     */
    public static <T> CommonResult<T> unauthorized(String message) {
        if(message == null || "".equals(message)){
            message = ResultCode.UNAUTHORIZED.getMessage();
        }
        return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), message, null);
    }

    /**
     * 未授权返回结果
     */
    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> CommonResult<T> forbidden(String message, T data) {
        if(message == null || "".equals(message)){
            message = ResultCode.FORBIDDEN.getMessage();
        }
        return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(), message, data);
    }

    /**
     * 无数据结果
     */
    public static <T> CommonResult<T> noData(String message) {
        return new CommonResult<T>(ResultCode.NO_DATA.getCode(), message==null?ResultCode.FORBIDDEN.getMessage():message, null);
    }

    public static <T> CommonResult<T> noData(String message, T data) {
        return new CommonResult<T>(ResultCode.NO_DATA.getCode(), message==null?ResultCode.FORBIDDEN.getMessage():message, data);
    }

    /**
     * 找不到页面
     */
    public static <T> CommonResult<T> notFound(String message) {
        return new CommonResult<T>(ResultCode.NOTFOUND.getCode(), message==null?ResultCode.NOTFOUND.getMessage():message, null);
    }

    public long getResultCode() {
        return resultCode;
    }

    public void setResultCode(long resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
