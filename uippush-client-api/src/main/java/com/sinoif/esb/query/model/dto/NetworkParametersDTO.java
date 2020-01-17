package com.sinoif.esb.query.model.dto;

import java.io.Serializable;

/**
 * 网络参数
 */
public class NetworkParametersDTO implements Serializable {

    private String hostName;

    private String domainName;

    private String[] dnsServers;

    private String ipv4DefaultGateway;

    private String ipv6DefaultGateway;

    public String getHostName() {
        return hostName;
    }

    public NetworkParametersDTO setHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    public String getDomainName() {
        return domainName;
    }

    public NetworkParametersDTO setDomainName(String domainName) {
        this.domainName = domainName;
        return this;
    }

    public String[] getDnsServers() {
        return dnsServers;
    }

    public NetworkParametersDTO setDnsServers(String[] dnsServers) {
        this.dnsServers = dnsServers;
        return this;
    }

    public String getIpv4DefaultGateway() {
        return ipv4DefaultGateway;
    }

    public NetworkParametersDTO setIpv4DefaultGateway(String ipv4DefaultGateway) {
        this.ipv4DefaultGateway = ipv4DefaultGateway;
        return this;
    }

    public String getIpv6DefaultGateway() {
        return ipv6DefaultGateway;
    }

    public NetworkParametersDTO setIpv6DefaultGateway(String ipv6DefaultGateway) {
        this.ipv6DefaultGateway = ipv6DefaultGateway;
        return this;
    }
}