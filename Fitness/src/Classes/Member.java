package Classes;

public class Member extends Personne {
    private int age;
    private boolean paid;


    public Member(int id, String name, String prename, int age,  boolean paid) {
        super(id, name, prename);
        this.age = age;

        this.paid = paid;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {
        return "Member{" +
                "age=" + age +
                ", paid=" + paid +
                '}';
    }
}
