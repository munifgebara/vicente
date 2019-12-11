package br.com.munif.framework.vicente.application.search;

/**
 * @author munif
 */
public class Association {

    private String attribute;
    private String sourceEntity;
    private String destinationEntity;
    private String multiplicity;

    public Association() {
    }

    public Association(String attribute, String sourceEntity, String destinationEntity, String multiplicity) {
        this.attribute = attribute;
        this.sourceEntity = sourceEntity;
        this.destinationEntity = destinationEntity;
        this.multiplicity = multiplicity;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getSourceEntity() {
        return sourceEntity;
    }

    public void setSourceEntity(String sourceEntity) {
        this.sourceEntity = sourceEntity;
    }

    public String getDestinationEntity() {
        return destinationEntity;
    }

    public void setDestinationEntity(String destinationEntity) {
        this.destinationEntity = destinationEntity;
    }

    public String getMultiplicity() {
        return multiplicity;
    }

    public void setMultiplicity(String multiplicity) {
        this.multiplicity = multiplicity;
    }

    @Override
    public String toString() {
        return "Association{" +
                "attribute='" + attribute + '\'' +
                ", sourceEntity='" + sourceEntity + '\'' +
                ", destinationEntity='" + destinationEntity + '\'' +
                ", multiplicity='" + multiplicity + '\'' +
                '}';
    }
}
