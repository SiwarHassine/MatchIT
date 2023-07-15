package com.c2s.dsif.Dto;


public class APIResponse<T> {
    private int recordCount;
    private T response;

    public APIResponse() {}

    public APIResponse(int recordCount, T response) {
        this.recordCount = recordCount;
        this.response = response;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
