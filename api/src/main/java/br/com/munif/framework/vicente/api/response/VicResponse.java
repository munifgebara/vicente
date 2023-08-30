package br.com.munif.framework.vicente.api.response;

public class VicResponse<T> {

    public final String className = "VicResponse";
    private String code;
    private T data;


    public VicResponse(String code, T data) {
        this.code = code;
        this.data = data;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
