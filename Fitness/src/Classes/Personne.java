package Classes;

public class Personne {
    private int id;
    private String name;
    private String prename;

    public Personne(int id, String name, String prename) {
        this.id = id;
        this.name = name;
        this.prename = prename;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }
}
