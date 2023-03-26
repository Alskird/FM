package App;

public class User {

    private String name;
    private int scl;

    public User(String name) {
        this.name = name;
        this.scl = 0;
    }

    public String getName() {
        return name;
    }

    public int getSCL() {
        return scl;
    }
}
