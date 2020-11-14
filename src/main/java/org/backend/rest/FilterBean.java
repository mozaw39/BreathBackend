package org.backend.rest;

import javax.ws.rs.QueryParam;

public class FilterBean {
    private @QueryParam("userid") int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
