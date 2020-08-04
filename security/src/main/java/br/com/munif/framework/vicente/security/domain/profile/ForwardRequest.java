package br.com.munif.framework.vicente.security.domain.profile;

import br.com.munif.framework.vicente.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.envers.Audited;
import org.springframework.http.HttpMethod;

import javax.persistence.*;

/**
 * @author munif
 */
@Entity
@Audited
public class ForwardRequest extends RequestAction {

    @Column(name = "url")
    private String url;
    @Enumerated(EnumType.STRING)
    @Column(name = "method")
    private HttpMethod method;
    @Column(name = "auth_header_name")
    private String authorizationHeaderName = "Authorization";
    @Column(name = "default_auth_header")
    private String defaultAuthorizationHeader;

    public ForwardRequest() {
    }

    public ForwardRequest(String url, HttpMethod method) {
        this.url = url;
        this.method = method;
    }

    public ForwardRequest(String url, HttpMethod method, OperationFilter operationFilter, String authorizationHeaderName, String defaultAuthorizationHeader) {
        this.url = url;
        this.method = method;
        this.authorizationHeaderName = authorizationHeaderName;
        this.defaultAuthorizationHeader = defaultAuthorizationHeader;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getAuthorizationHeaderName() {
        return authorizationHeaderName;
    }

    public void setAuthorizationHeaderName(String authorizationHeaderName) {
        this.authorizationHeaderName = authorizationHeaderName;
    }

    public String getDefaultAuthorizationHeader() {
        return defaultAuthorizationHeader;
    }

    public void setDefaultAuthorizationHeader(String defaultAuthorizationHeader) {
        this.defaultAuthorizationHeader = defaultAuthorizationHeader;
    }

    @Override
    public String toString() {
        return "ForwardRequest{" +
                "url='" + url + '\'' +
                ", method=" + method +
                ", operationFilter=" + super.getOperationFilter() +
                ", authorizationHeaderName='" + authorizationHeaderName + '\'' +
                ", defaultAuthorizationHeader='" + defaultAuthorizationHeader + '\'' +
                '}';
    }
}