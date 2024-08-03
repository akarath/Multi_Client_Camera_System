import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        CameraSystem cameraSystem = new CameraSystem();
        ImageCaptureSystem imageCaptureSystem = new ImageCaptureSystem(cameraSystem);

        int numberOfClients = 7;
        int[] urgencies = {1, 2, 3, 4, 5, 6, 7}; // Fixed urgencies for each client

        // Create an array to store clients
        Client[] clients = new Client[numberOfClients];

        // Executor service to manage client threads
        ExecutorService executor = Executors.newFixedThreadPool(numberOfClients);

        // Loop to create clients
        for (int i = 0; i < numberOfClients; i++) {
            final int clientId = i + 1;
            final int urgency = urgencies[i]; // Get fixed urgency for this client
            clients[i] = new Client(imageCaptureSystem, urgency); // Initialize client
            System.out.println("Created Client " + clientId + " with urgency " + urgency);
        }

        // Loop to submit capture requests
        for (int i = 0; i < numberOfClients; i++) {
            final int clientId = i + 1;
            final Client client = clients[i];
            executor.submit(() -> {
                System.out.println("Client " + clientId + " is about to submit a request with urgency ");
                client.submitCaptureRequest(
                        image -> System.out.println("Client " + clientId + " Success: " + image + " with urgency " + client.getUrgency()),
                        error -> System.out.println("Client " + clientId + " Failure: " + error + " with urgency " + client.getUrgency())
                );
            });
        }

    }
}
