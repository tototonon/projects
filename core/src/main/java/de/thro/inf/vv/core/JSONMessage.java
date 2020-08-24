package de.thro.inf.vv.core;


import org.apache.commons.cli.CommandLine;

public class JSONMessage {
    public static CommandLine cmd;
    private String protocol;
    private String username;
    private String password;
    private String ip;

    public JSONMessage(String protocol, String username, String password, String ip) {
        this.protocol = protocol;
        this.username = username;
        this.password = password;
        this.ip = ip;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getIp() {
        return ip;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}