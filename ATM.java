import java.util.Scanner;

public class ATM {
    private BankAccount account;

    public ATM(BankAccount account) {
        this.account = account;
    }

    public void displayMenu() {
        System.out.println("Welcome to the ATM!");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Exit");
    }

    public void checkBalance() {
        System.out.println("Current balance: $" + account.getBalance());
    }

    public void deposit(double amount) {
        account.deposit(amount);
        System.out.println("Successfully deposited $" + amount);
        checkBalance();
    }

    public void withdraw(double amount) {
        if (account.withdraw(amount)) {
            System.out.println("Successfully withdrew $" + amount);
        }
        checkBalance();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            displayMenu();
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    deposit(depositAmount);
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    withdraw(withdrawAmount);
                    break;
                case 4:
                    running = false;
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000);  // Initial balance of $1000
        ATM atm = new ATM(account);
        atm.run();
    }
}
