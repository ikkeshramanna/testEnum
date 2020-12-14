package com.mycompany.myapp.domain.enumeration;

/**
 * The TestType enumeration.
 */
public enum TestType {
    AT_SUPPLEMENTARY_FEEDERS("At Supplementary Feeders"),
    OPPORTUNISTIC_AT_NEST("Opportinistic: at nest"),
    OPPORTUNISTIC_IN_FIELD("Opportunistic: in field"),
    NEST_SITE_EGG_CHECK("Nest site: egg check"),
    NEST_SITE_HATCH_CHECK("Nest site: hatch check");

    private final String value;


    TestType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
