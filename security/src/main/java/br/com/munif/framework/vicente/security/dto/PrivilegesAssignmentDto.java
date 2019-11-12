package br.com.munif.framework.vicente.security.dto;

public class PrivilegesAssignmentDto {
    public String groupCode;
    public String login;

    public PrivilegesAssignmentDto() {
    }

    public PrivilegesAssignmentDto(String groupCode, String login) {
        this.groupCode = groupCode;
        this.login = login;
    }
}
