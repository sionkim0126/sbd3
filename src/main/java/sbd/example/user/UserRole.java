package sbd.example.user;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADIME"),
    USER("ROLE_USER");

    private String value;

    UserRole(String value){
        this.value = value;
    }
}
