package br.com.munif.framework.vicente.core.vquery;

/**
 *
 * @author wmfsystem
 */
public enum JoinType {
    SIMPLE(" join "),
    LEFT(" left join "),
    RIGHT(" right join "),
    INNER(" inner join "),
    FULL_OUTER(" full outer join "),
    CROSS(" cross join");

    private String type;

    JoinType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
