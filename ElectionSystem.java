
package electionsystem;
import java.util.*;
public class ElectionSystem {

    public static void main(String[] args) {
        VoteSystem voteSystem = new VoteSystem();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            voteSystem.showMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    Admin admin = new Admin();
                    if (admin.authenticate()) {
                        admin.adminMenu(voteSystem);
                    }
                    break;
                case 2:
                    voteSystem.vote();
                    break;
                case 3:
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }
}
