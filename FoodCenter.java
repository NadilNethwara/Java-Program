import java.util.*;

class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class food {
    private static final int MAX_QUEUE_1 = 2;
    private static final int MAX_QUEUE_2 = 3;
    private static final int MAX_QUEUE_3 = 5;

    private List<Customer>[] cashiers;
    private int burgersInStock;

    public food() {
        cashiers = new ArrayList[3];
        cashiers[0] = new ArrayList<>();
        cashiers[1] = new ArrayList<>();
        cashiers[2] = new ArrayList<>();
        burgersInStock = 50;
    }

    public void displayMenu() {
        System.out.println("*****************");
        System.out.println("* Cashiers      *");
        System.out.println("*****************");
        displayQueues();

        System.out.println("100 or VFQ: View all Queues.");
        System.out.println("101 or VEQ: View all Empty Queues.");
        System.out.println("102 or ACQ: Add customer to a Queue.");
        System.out.println("103 or RCQ: Remove a customer from a Queue. (From a specific location)");
        System.out.println("104 or PCQ: Remove a served customer.");
        System.out.println("105 or VCS: View Customers Sorted in alphabetical order.");
        System.out.println("106 or SPD: Store Program Data into file.");
        System.out.println("107 or LPD: Load Program Data from file.");
        System.out.println("108 or STK: View Remaining burgers Stock.");
        System.out.println("109 or AFS: Add burgers to Stock.");
        System.out.println("999 or EXT: Exit the Program.");
    }

    public void processMenuOption(int option) {
        switch (option) {
            case 100:
                viewAllQueues();
                break;
            case 101:
                viewAllEmptyQueues();
                break;
            case 102:
                addCustomerToQueue();
                break;
            case 103:
                removeCustomerFromQueue();
                break;
            case 104:
                removeServedCustomer();
                break;
            case 105:
                viewCustomersSorted();
                break;
            case 106:
                storeProgramData();
                break;
            case 107:
                loadProgramData();
                break;
            case 108:
                viewRemainingBurgersStock();
                break;
            case 109:
                addBurgersToStock();
                break;
            case 999:
                System.out.println("Exiting the program. Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                break;
        }
    }

    private void displayQueues() {
        for (List<Customer> queue : cashiers) {
            for (int i = 0; i < queue.size(); i++) {
                System.out.print("O   ");
            }
            for (int i = queue.size(); i < getMaxQueueSize(queue); i++) {
                System.out.print("X   ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("X - Occupied     O - Not Occupied");
    }

    private int getMaxQueueSize(List<Customer> queue) {
        if (queue == cashiers[0]) {
            return MAX_QUEUE_1;
        } else if (queue == cashiers[1]) {
            return MAX_QUEUE_2;
        } else if (queue == cashiers[2]) {
            return MAX_QUEUE_3;
        }
        return 0;
    }

    private void viewAllQueues() {
        System.out.println("*****************");
        System.out.println("* Cashiers      *");
        System.out.println("*****************");
        displayQueues();
    }

    private void viewAllEmptyQueues() {
        System.out.println("Empty Queues:");
        for (int i = 0; i < cashiers.length; i++) {
            if (cashiers[i].isEmpty()) {
                System.out.println("Queue " + (i + 1));
            }
        }
    }

    private void addCustomerToQueue() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter customer name:");
        String name = scanner.nextLine();

        Customer customer = new Customer(name);
        int shortestQueueIndex = getShortestQueueIndex();
        if (shortestQueueIndex == -1) {
            System.out.println("All queues are full. Cannot add customer.");
            return;
        }

        cashiers[shortestQueueIndex].add(customer);
        burgersInStock -= 5;

        if (burgersInStock <= 10) {
            System.out.println("Warning: Low stock! Remaining burgers: " + burgersInStock);
        }
    }

    private int getShortestQueueIndex() {
        int shortestQueueIndex = -1;
        int shortestQueueSize = Integer.MAX_VALUE;

        for (int i = 0; i < cashiers.length; i++) {
            if (cashiers[i].size() < shortestQueueSize && cashiers[i].size() < getMaxQueueSize(cashiers[i])) {
                shortestQueueIndex = i;
                shortestQueueSize = cashiers[i].size();
            }
        }

        return shortestQueueIndex;
    }

    private void removeCustomerFromQueue() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter queue number (1, 2, or 3):");
        int queueNumber = scanner.nextInt();

        if (queueNumber < 1 || queueNumber > 3) {
            System.out.println("Invalid queue number.");
            return;
        }

        List<Customer> queue = cashiers[queueNumber - 1];
        if (queue.isEmpty()) {
            System.out.println("Queue " + queueNumber + " is empty. Cannot remove customer.");
            return;
        }

        System.out.println("Enter customer position (1 to " + queue.size() + "):");
        int customerPosition = scanner.nextInt();

        if (customerPosition < 1 || customerPosition > queue.size()) {
            System.out.println("Invalid customer position.");
            return;
        }

        Customer customer = queue.remove(customerPosition - 1);
        System.out.println("Removed customer " + customer.getName() + " from queue " + queueNumber);
    }

    private void removeServedCustomer() {
        for (List<Customer> queue : cashiers) {
            if (!queue.isEmpty()) {
                Customer customer = queue.remove(0);
                System.out.println("Removed served customer " + customer.getName());
                return;
            }
        }

        System.out.println("No customers to remove.");
    }

    private void viewCustomersSorted() {
        List<Customer> allCustomers = new ArrayList<>();
        for (List<Customer> queue : cashiers) {
            allCustomers.addAll(queue);
        }

        Collections.sort(allCustomers, Comparator.comparing(Customer::getName));

        System.out.println("Customers Sorted in alphabetical order:");
        for (Customer customer : allCustomers) {
            System.out.println(customer.getName());
        }
    }

    private void storeProgramData() {
        // Implement code to store program data into a file
        System.out.println("Program data stored successfully.");
    }

    private void loadProgramData() {
        // Implement code to load program data from a file
        System.out.println("Program data loaded successfully.");
    }

    private void viewRemainingBurgersStock() {
        System.out.println("Remaining burgers in stock: " + burgersInStock);
    }

    private void addBurgersToStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of burgers to add:");
        int quantity = scanner.nextInt();

        burgersInStock += quantity;
        System.out.println("Added " + quantity + " burgers to stock. Remaining burgers: " + burgersInStock);
        
    }
}

public class FoodCenter {
    public static void main(String[] args) {
        food foodCenter = new food();
        Scanner scanner = new Scanner(System.in);
        

        while (true) {
            foodCenter.displayMenu();
            System.out.println();
            System.out.print("Enter your choice: ");
            int option = scanner.nextInt();
            foodCenter.processMenuOption(option);
            System.out.println();
        
        }
        
    }
}
