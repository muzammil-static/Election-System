
package electionsystem;
import java.util.*;
public class Admin {
    private static final String USERNAME = "admin";
    private static final String PASSWORD_HASH = hashPassword("admin123");

    private static String hashPassword(String password) {
        return Integer.toString(password.hashCode());
    }

    public boolean authenticate() {
        Scanner scanner = new Scanner(System.in);
        final int maxAttempts = 3;
        int attempts = 0;

        while (attempts < maxAttempts) {
            System.out.print("Enter admin username: ");
            String enteredUsername = scanner.nextLine();

            System.out.print("Enter admin password: ");
            String enteredPassword = scanner.nextLine();

            if (USERNAME.equals(enteredUsername) && PASSWORD_HASH.equals(hashPassword(enteredPassword))) {
                System.out.println("Authentication successful.");
                return true;
            } else {
                System.out.println("Authentication failed. Please try again.");
                attempts++;
            }
        }
        System.out.println("Maximum attempts reached. Authentication failed.");
        return false;
    }

    public void adminMenu(VoteSystem voteSystem) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add Voter");
            System.out.println("2. Add Candidate");
            System.out.println("3. View Reports");
            System.out.println("4. View Results");
            System.out.println("5. Set Voting Period");
            System.out.println("6. Exit Admin Menu");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    voteSystem.addVoter();
                    break;
                case 2:
                    voteSystem.addCandidate();
                    break;
                case 3:
                    voteSystem.showReports();
                    break;
                case 4:
                    voteSystem.showResults();
                    break;
                case 5:
                    voteSystem.setVotingPeriod();
                    break;
                case 6:
                    System.out.println("Exiting Admin Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 6);
    }
}
