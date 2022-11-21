package hu.webuni.studentregister202210.dto;

import lombok.Data;

@Data
public class GoogleData {
private String sub;
private String name;
private String given_name;
private String family_name;
private String picture;
private String email;
private String locale;
}

