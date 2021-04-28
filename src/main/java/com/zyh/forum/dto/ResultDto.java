package com.zyh.forum.dto;


public class ResultDto<T> {
    private int code;
    private String message;
    private T data;

    public ResultDto<?> success(){
        ResultDto resultDto=new ResultDto();
        resultDto.setCode(200);
        resultDto.setMessage("成功");
        return resultDto;
    }
    public ResultDto<T> success(T data){
        ResultDto<T> resultDto=new ResultDto<T>();
        resultDto.setCode(200);
        resultDto.setMessage("成功");
        resultDto.setData(data);
        return resultDto;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

