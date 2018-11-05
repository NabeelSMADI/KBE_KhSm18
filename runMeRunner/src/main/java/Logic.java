
import de.htw.ai.kbe.runmerunner.RunMe;



/**
 *
 * @author Smadi
 */
public class Logic {

    @RunMe
    public boolean findMe1() {
        return true;
    }

    @RunMe
    public int findMe2() {
        return 1 + 1;
    }

    @RunMe
    public boolean findMe3() {
        return true;
    }

    @RunMe
    public boolean findMe4() {
        return true;
    }

    @RunMe
    public boolean findMeWithException(boolean check) {
        return check;
    }

    public boolean dontFindMe1() {
        return true;
    }

    public boolean dontFindMe2() {
        return true;
    }

}
