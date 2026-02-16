class Test6 {
  public static void main(String[] args) {
    TaskList list = new TaskList();
    list.completeTask(2);
    list.completeTask(0);
    list.completeTask(1);
    System.out.println(list.getRewardPoints());
  }
}
