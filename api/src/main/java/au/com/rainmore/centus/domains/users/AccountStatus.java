package au.com.rainmore.centus.domains.users;

public enum AccountStatus {
    ACTIVE,
    DISABLED,
    SUSPENDED;

    public Boolean isActive() {
        return ACTIVE == this;
    }

    public Boolean isDisabled() {
        return DISABLED == this;
    }

    public Boolean isSuspended() {
        return SUSPENDED == this;
    }
}
