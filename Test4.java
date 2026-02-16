class Test4 {
  public static void main(String[] args) {
    TaskList list = new TaskList();
    list.printDueToday();

    list.completeTask(3);
    list.printDueToday();
  }
}
