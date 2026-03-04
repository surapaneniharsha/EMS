package com.infinira.ems.enums;

public enum Gender {
    MALE("M"),
    FEMALE("F"),
    OTHERS("O");

    private final String genderCode;

    Gender(String genderCode) {
        this.genderCode = genderCode;
    }

    public static String getValue(String genderInput) {
        if (genderInput == null || genderInput.isBlank()) {
            throw new RuntimeException("Gender cannot be null or empty");
        }
        for (Gender gender : Gender.values()) {
            if (gender.genderCode.equalsIgnoreCase(genderInput) || gender.name().equalsIgnoreCase(genderInput)) {
                return gender.name();
            }
        }
        throw new RuntimeException("Unknown gender: " + genderInput);
    }

}