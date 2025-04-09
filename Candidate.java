
package electionsystem;

public class Candidate {
    String name;
    String cnic;
    String party;
    String symbol;
    int votes;

    public Candidate(String name, String cnic, String party, String symbol) {
        this.name = name;
        this.cnic = cnic;
        this.party = party;
        this.symbol = symbol;
        this.votes = 0;
    }
}
