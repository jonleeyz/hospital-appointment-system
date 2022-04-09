package model;

abstract class Id {
    protected String id;

    protected Id(String id) {
        this.id = id;
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
