/**
 * Created by Yakovenko on 19.05.2016.
 */
public class Selection {
    public String personX;
    public String personY;
    //public int size;


    public Selection(String personX, String personY) {
        super();
        this.personX = personX;
        this.personY = personY;

    }


    public int getLengthPersonX() {
        return personX.length();
    }

    public int getLengthPersonY() {
        return personY.length();
    }


}