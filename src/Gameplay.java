public class Gameplay implements Runnable {
    private Controller controller;

    public Gameplay(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        while (!controller.isGameOver) {
            controller.updateGame();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        controller.handleGameOver();
    }
}
