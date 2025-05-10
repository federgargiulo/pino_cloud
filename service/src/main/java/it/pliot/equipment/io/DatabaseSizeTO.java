package it.pliot.equipment.io;

public class DatabaseSizeTO {
    private String databaseName;
    private String size;

    public DatabaseSizeTO(String databaseName, String size) {
        this.databaseName = databaseName;
        this.size = size;
    }

    // Getter e Setter
    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
