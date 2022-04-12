package model.entities.id;

public abstract class Id {
    protected String id;

    protected Id(String id) {
        this.id = id;
    }

    public String getValue() {
        return id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
