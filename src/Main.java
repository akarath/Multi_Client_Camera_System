import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        CameraSystem cameraSystem = new CameraSystem();
        ImageCaptureSystem imageCaptureSystem = new ImageCaptureSystem(cameraSystem);

        int numberOfClients = 7;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfClients);

        for (int i = 1; i <= numberOfClients; i++) {
            final int clientId = i;
            executor.submit(() -> {
                int urgency = (int) (Math.random() * 10) + 1; // Random urgency between 1 and 10
                System.out.println("Client " + clientId + " is about to submit a request with urgency " + urgency);
                imageCaptureSystem.submitCaptureRequest(urgency,
                        image -> System.out.println("Client " + clientId + " Success: " + image + " with urgency " + urgency),
                        error -> System.out.println("Client " + clientId + " Failure: " + error + " with urgency " + urgency)
                );
            });
        }
    }
}
