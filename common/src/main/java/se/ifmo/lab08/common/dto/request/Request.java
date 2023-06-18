package se.ifmo.lab08.common.dto.request;


import se.ifmo.lab08.common.dto.Credentials;

import java.io.Serializable;

public abstract class Request implements Serializable {
    private Credentials credentials;
    private final String[] args;

    public Request(String[] args) {
        this.args = args;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public Credentials credentials() {
        return credentials;
    }

    public String[] args() {
        return args;
    }
}
