public class NotificationsThread implements Runnable {

    private Notifications notifications;

    public NotificationsThread(Notifications notifications) {
        this.notifications = notifications;
    }

    public void run(){
        notifications.notificarTodos();
    }
}
