
package electionsystem;
import java.util.*;
public class VoteSystem {
    private LinkedList<Voter> voters = new LinkedList<>();
    private LinkedList<Candidate> candidates = new LinkedList<>();
    private Admin adminInstance = new Admin();
    private LocalDate votingStartTime;
    private int votingDurationSeconds;

    public void showMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Admin Menu");
        System.out.println("2. Vote");
        System.out.println("3. Exit");
    }

    private boolean isAlphabetic(String input) {
        return input != null && input.matches("[a-zA-Z ]+");
    }

    private boolean isValidCNIC(String cnic) {
        return cnic != null && cnic.matches("\\d{13}");
    }

    public void addVoter() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter voter's name: ");
        String name = scanner.nextLine();
        System.out.print("Enter voter's CNIC (13 digits, no dashes): ");
        String cnic = scanner.nextLine();
        System.out.print("Enter voter's date of birth (YYYY-MM-DD): ");
        LocalDate dob = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter voter's city: ");
        String city = scanner.nextLine();

        if (!isAlphabetic(name)) {
            System.out.println("Invalid name. It must contain only alphabetic characters.");
            return;
        }

        if (!isValidCNIC(cnic)) {
            System.out.println("Invalid CNIC. It must contain exactly 13 digits, without dashes.");
            return;
        }

        Voter voter = new Voter(name, cnic, dob, city);
        if (voter.isEligible()) {
            voters.add(voter);
            System.out.println("Voter added successfully.");
        } else {
            System.out.println("Voter is not eligible (must be 18 or older).");
        }
    }

    public void addCandidate() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter candidate's name: ");
        String name = scanner.nextLine();
        System.out.print("Enter candidate's CNIC (13 digits, no dashes): ");
        String cnic = scanner.nextLine();
        System.out.print("Enter candidate's party name: ");
        String party = scanner.nextLine();
        System.out.print("Enter candidate's symbol: ");
        String symbol = scanner.nextLine();

        if (!isAlphabetic(name) || !isAlphabetic(party) || !isAlphabetic(symbol)) {
            System.out.println("Invalid input. Name, party, and symbol must contain only alphabetic characters.");
            return;
        }

        if (!isValidCNIC(cnic)) {
            System.out.println("Invalid CNIC. It must contain exactly 13 digits, without dashes.");
            return;
        }

        candidates.add(new Candidate(name, cnic, party, symbol));
        System.out.println("Candidate added successfully.");
    }

    public void setVotingPeriod() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter voting duration in seconds (300 to 900 seconds): ");
        int duration = scanner.nextInt();

        if (duration < 300 || duration > 900) {
            System.out.println("Invalid duration. Please enter between 300 and 900 seconds.");
        } else {
            votingDurationSeconds = duration;
            votingStartTime = LocalDate.now();

            System.out.println("Voting period set successfully for " + votingDurationSeconds + " seconds.");

            // Start the countdown in a separate thread
            startCountdown();
        }
    }

    private void startCountdown() {
        new Thread(() -> {
            try {
                for (int i = votingDurationSeconds; i > 0; i--) {
                    System.out.print("\rRemaining time for voting: " + i + " seconds   ");
                    Thread.sleep(1000);
                }
                System.out.println("\nVoting period has ended.");
            } catch (InterruptedException e) {
                System.out.println("Countdown interrupted.");
            }
        }).start();
    }

    public void showReports() {
        System.out.println("Reports for each candidate:");
        for (Candidate candidate : candidates) {
            System.out.println("Candidate: " + candidate.name + " - Votes: " + candidate.votes);
        }
    }

    public void showResults() {
        if (votingStartTime == null) {
            System.out.println("Voting period has not started yet.");
            return;
        }

        System.out.println("\nElection Results:");
        int maxVotes = 0;
        Candidate winner = null;

        for (Candidate candidate : candidates) {
            System.out.println("Candidate: " + candidate.name + " - Votes: " + candidate.votes);
            if (candidate.votes > maxVotes) {
                maxVotes = candidate.votes;
                winner = candidate;
            }
        }

        if (winner != null) {
            System.out.println("Winner: " + winner.name + " with " + winner.votes + " votes.");
        } else {
            System.out.println("No winner determined.");
        }
    }

    public void vote() {
        // Check if voting period is set
        if (votingStartTime == null || votingDurationSeconds == 0) {
            System.out.println("As the admin didn't start the voting period, no one is able to cast a vote.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter voter's CNIC (13 digits, no dashes): ");
        String voterCnic = scanner.nextLine();

        Voter voter = voters.stream().filter(v -> v.cnic.equals(voterCnic)).findFirst().orElse(null);
        if (voter == null || voter.hasVoted) {
            System.out.println("Invalid or duplicate vote attempt.");
            return;
        }

        System.out.print("Enter the name of the candidate you want to vote for: ");
        String candidateName = scanner.nextLine();

        Candidate candidate = candidates.stream().filter(c -> c.name.equalsIgnoreCase(candidateName)).findFirst().orElse(null);
        if (candidate == null) {
            System.out.println("Invalid candidate. Vote not counted.");
            return;
        }

        // Mark the voter as having voted and increment candidate's vote count
        voter.hasVoted = true;
        candidate.votes++;
        System.out.println("Vote successfully cast for " + candidate.name + ".");
    }
}
