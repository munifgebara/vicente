package br.com.munif.framework.vicente.security.domain.profile;

import br.com.munif.framework.vicente.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.envers.Audited;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.*;

/**
 * @author munif
 */
@Entity
@Audited
@Table(name = "vic_forward_request")
public class ForwardRequest extends BaseEntity {

    @Column(name = "url")
    private String url;
    @Enumerated(EnumType.STRING)
    @Column(name = "method")
    private HttpMethod method;
    @ManyToOne
    @JsonIgnoreProperties({"forwardRequests"})
    private OperationFilter operationFilter;
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
        this.operationFilter = operationFilter;
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

    public OperationFilter getOperationFilter() {
        return operationFilter;
    }

    public void setOperationFilter(OperationFilter operationFilter) {
        this.operationFilter = operationFilter;
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
                ", operationFilter=" + operationFilter +
                ", authorizationHeaderName='" + authorizationHeaderName + '\'' +
                ", defaultAuthorizationHeader='" + defaultAuthorizationHeader + '\'' +
                '}';
    }
}