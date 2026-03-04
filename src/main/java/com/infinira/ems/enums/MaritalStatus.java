package com.infinira.ems.enums;

public enum MaritalStatus {
    MARRIED("M"),
    SINGLE("S");

   private final String status;

   MaritalStatus (String status) {
        this.status = status;
   }

   public static String getValue(String statusInput) {
        if(statusInput == null || statusInput.isBlank()) {
            throw new RuntimeException("MaritialStatus cannot be Null or Empty");
        }
        for(MaritalStatus maritalStatus : MaritalStatus.values()) {
            if(maritalStatus.status.equalsIgnoreCase(statusInput) || maritalStatus.name().equalsIgnoreCase(statusInput)) {
                return maritalStatus.name();
            }
        }
        throw new RuntimeException("Unknown gender :"+ statusInput);
    }
}