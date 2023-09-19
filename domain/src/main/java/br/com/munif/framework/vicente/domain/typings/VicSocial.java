package br.com.munif.framework.vicente.domain.typings;

import java.util.Objects;

public class VicSocial extends VicDomain {

    private String instagramId;
    private String linkedinId;
    private String facebookId;

    private String twitterId;


    public VicSocial(){

    }

    public VicSocial(String instagramId, String linkedinId, String facebookId, String twitterId) {
        this.instagramId = instagramId;
        this.linkedinId = linkedinId;
        this.facebookId = facebookId;
        this.twitterId = twitterId;
    }

    public String getInstagramId() {
        return instagramId;
    }

    public void setInstagramId(String instagramId) {
        this.instagramId = instagramId;
    }

    public String getLinkedinId() {
        return linkedinId;
    }

    public void setLinkedinId(String linkedinId) {
        this.linkedinId = linkedinId;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
    }

    @Override
    public String toString() {
        return "VicSocial{" +
                "instagramId='" + instagramId + '\'' +
                ", linkedinId='" + linkedinId + '\'' +
                ", facebookId='" + facebookId + '\'' +
                ", twitterId='" + twitterId + '\'' +
                '}';
    }
}