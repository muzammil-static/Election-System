
package electionsystem;
import java.util.*;
public class Voter {
    String name;
    String cnic;
    LocalDate dob; // Use LocalDate for Date of Birth
    String city;
    boolean hasVoted;

    public Voter(String name, String cnic, LocalDate dob, String city) {
        this.name = name;
        this.cnic = cnic;
        this.dob = dob;
        this.city = city;
        this.hasVoted = false;
    }

    public boolean isEligible() {
        LocalDate today = LocalDate.now();
        return dob.plusYears(18).isBefore(today);
    }
}
