package br.com.munif.framework.vicente.domain;

import java.util.ArrayList;
import java.util.List;

public class BaseLink {
    private String rel;
    private String href;
    private List<String> verbs = new ArrayList<>();

    public BaseLink() {
    }

    public BaseLink(String rel, String href) {
        this.rel = rel;
        this.href = href;
    }

    public BaseLink(String rel, String href, List<String> verbs) {
        this.rel = rel;
        this.href = href;
        this.verbs = verbs;
    }

    public BaseLink(String rel, String href, String verb) {
        this.rel = rel;
        this.href = href;
        this.verbs.add(verb);
    }

    public List<String> getVerbs() {
        return verbs;
    }

    public void setVerbs(List<String> verbs) {
        this.verbs = verbs;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
