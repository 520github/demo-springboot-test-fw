package org.sunso.demo.model;


import lombok.Data;

@Data
public class ResData<T> {

    public static final String SUCCESS = "S10000";
    public static final String FAIL = "E10000";   // 通用错误码
    public static final String NOT_ROLE = "E20000"; // 没有权限
    public static final String NOT_LOGIN = "S40000"; // 未登陆错误
    public static final String LIMIT = "S30000"; // 限流


    private String code;

    private String msg;

    private T data;

    private boolean success;

    public static <T> ResData<T> success() {
        return success(null);
    }

    public static <T> ResData<T> success(T data) {
        ResData<T> result = new ResData<>();
        result.setCode(SUCCESS);
        result.setMsg("查询成功");
        result.setData(data);
        result.setSuccess(true);
        return result;
    }

    public static <T> ResData<T> success(String msg, T data) {
        ResData<T> result = new ResData<>();
        result.setCode(SUCCESS);
        result.setMsg(msg);
        result.setData(data);
        result.setSuccess(true);
        return result;
    }

    public static <T> ResData<T> success(String msg) {
        ResData<T> result = new ResData<>();
        result.setCode(SUCCESS);
        result.setMsg(msg);
        result.setSuccess(true);
        return result;
    }

    public static <T> ResData<T> text(T text) {
        ResData<T> result = new ResData<>();
        result.setCode(SUCCESS);
        result.setMsg("查询成功");
        result.setData(text);
        result.setSuccess(true);
        return result;
    }

    public static <T> ResData<T> error(String msg) {
        ResData<T> result = new ResData<>();
        result.setCode(FAIL);
        result.setMsg(msg);
        result.setSuccess(false);
        return result;
    }


    public static <T> ResData<T> noLogin() {
        ResData<T> result = new ResData<>();
        result.setCode(NOT_LOGIN);
        result.setMsg("请先登录");
        result.setSuccess(false);
        return result;
    }

    public static <T> ResData<T> noSign() {
        ResData<T> result = new ResData<>();
        result.setCode(NOT_ROLE);
        result.setMsg("请求验证失败");
        result.setSuccess(false);
        return result;
    }

    public static <T> ResData<T> accessFast() {
        ResData<T> result = new ResData<>();
        result.setCode(LIMIT);
        result.setMsg("访问速度过快");
        result.setSuccess(false);
        return result;
    }

    public static <T> ResData<T> notFunc() {
        ResData<T> result = new ResData<>();
        result.setCode(NOT_ROLE);
        result.setMsg("您没有权限");
        result.setSuccess(false);
        return result;
    }

    public static <T> ResData<T> error(String code, String msg) {
        ResData<T> result = new ResData<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setSuccess(false);
        return result;
    }
}
