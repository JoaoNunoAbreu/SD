public class NotificationsThread implements Runnable {

    private Notifications notifications;

    public NotificationsThread(Notifications notifications) {
        this.notifications = notifications;
    }

    public void run(){
        notifications.notificarTodos();

        try { // ----------------------------- Para tirar prob -----------------------------
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
