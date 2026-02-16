import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A class that stores and manages a list of tasks.
 * 
 * NOTE: This is the original procedural-style implementation.
 * It does NOT follow good OOP design principles.
 */
public class TaskList {

  // Parallel arrays used to store task information
  private Array<Integer> types;         // 0, 1, or 2
  private Array<String> descriptions;   // Task descriptions
  private Array<Integer> deadlines;     // Deadline in days
  private Array<String> assignees;      // Assignee name (if any)
  private Array<Boolean> completed;     // Whether task is completed

  private int numTasks;                 // Total number of tasks
  private String errorMsg;              // Stores error message (bad design)

  /**
   * Constructs a TaskList by reading from a file.
   */
  public TaskList(String filename) {
    try {
      Scanner sc = new Scanner(new File(filename));
      loadTasks(sc);
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }

  /**
   * Constructs a TaskList by reading from standard input.
   */
  public TaskList() {
    Scanner sc = new Scanner(System.in);
    loadTasks(sc);
  }

  /**
   * Loads tasks from the given scanner.
   * Returns false if an invalid task type is encountered.
   */
  private boolean loadTasks(Scanner sc) {
    numTasks = Integer.parseInt(sc.nextLine());

    // Initialize all arrays
    types = new Array<>(numTasks);
    descriptions = new Array<>(numTasks);
    deadlines = new Array<>(numTasks);
    assignees = new Array<>(numTasks);
    completed = new Array<>(numTasks);

    // Read each task line
    for (int i = 0; i < numTasks; i++) {
      String line = sc.nextLine();
      if (!createTask(line, i)) {
        System.out.println(errorMsg); // Print stored error message
        return false;
      }
    }
    return true;
  }

  /**
   * Parses a line of input and stores task information
   * into the parallel arrays.
   *
   * Returns false if task type is invalid.
   */
  private boolean createTask(String line, int index) {
    String[] parts = line.split(",");
    int type = Integer.parseInt(parts[0]);

    // Validate task type
    if (type != 0 && type != 1 && type != 2) {
      errorMsg = "Invalid task type in input: " + type;
      return false;
    }

    types.set(index, type);
    descriptions.set(index, parts[1]);
    completed.set(index, false);

    // Tasks with deadlines
    if (type == 1 || type == 2) {
      deadlines.set(index, Integer.parseInt(parts[2]));
    }

    // Delegated tasks
    if (type == 2) {
      assignees.set(index, parts[3]);
    }

    return true;
  }

  /**
   * Prints only the descriptions of tasks.
   */
  public void printTaskDescriptions() {
    for (int i = 0; i < numTasks; i++) {
      System.out.println(i + " " + descriptions.get(i));
    }
  }

  /**
   * Prints full details of tasks.
   * Uses many if-else checks based on task type.
   */
  public void printTaskDetails() {
    for (int i = 0; i < numTasks; i++) {
      System.out.print(i + " ");
      System.out.print(completed.get(i) ? "[X] " : "[ ] ");
      System.out.print(descriptions.get(i));

      if (types.get(i) == 1 || types.get(i) == 2) {
        System.out.print(" | Due in " + deadlines.get(i) + " days");
      }

      if (types.get(i) == 2) {
        System.out.print(" | Assigned to " + assignees.get(i));
      }

      System.out.println();
    }
  }

  /**
   * Marks a task as completed.
   */
  public void completeTask(int index) {
    completed.set(index, true);
  }

  /**
   * Prints tasks that are due today (deadline == 0).
   */
  public void printDueToday() {
    for (int i = 0; i < numTasks; i++) {
      if (types.get(i) != 0 && deadlines.get(i) == 0) {
        System.out.println(i + " [ ] " + descriptions.get(i)
          + " | Due in 0 days");
      }
    }
  }

  /**
   * Prints reminders for incomplete tasks with deadlines.
   */
  public void remindAll() {
    for (int i = 0; i < numTasks; i++) {
      if (!completed.get(i) && types.get(i) != 0) {

        if (types.get(i) == 2) {
          System.out.println("Sending a reminder to complete \""
            + descriptions.get(i) + "\" to " + assignees.get(i));
        } else {
          System.out.println("The task \"" + descriptions.get(i)
            + "\" is due in " + deadlines.get(i) + " days");
        }
      }
    }
  }

  /**
   * Calculates total reward points.
   * Completing a task gives reward equal to its deadline value.
   */
  public int getRewardPoints() {
    int sum = 0;
    for (int i = 0; i < numTasks; i++) {
      if (completed.get(i) && types.get(i) != 0) {
        sum += deadlines.get(i);
      }
    }
    return sum;
  }
}
