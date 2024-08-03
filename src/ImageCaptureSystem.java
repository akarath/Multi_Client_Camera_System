import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ImageCaptureSystem {
    private final CameraSystem cameraSystem;
    private final BlockingQueue<CaptureRequest> requestQueue;
    private final Lock lock;
    private volatile boolean running;

    public ImageCaptureSystem(CameraSystem cameraSystem) {
        this.cameraSystem = cameraSystem;
        this.requestQueue = new LinkedBlockingQueue<>();
        this.lock = new ReentrantLock();
        this.running = true;
        new Thread(this::processRequests).start(); // Start processing requests
        System.out.println("ImageCaptureSystem started.");
    }

    public void submitCaptureRequest(int urgency, Callback<String> successCallback, Callback<String> failureCallback) {
        String requestId = generateRequestId();
        CaptureRequest request = new CaptureRequest(requestId, urgency, successCallback, failureCallback);
        requestQueue.add(request);
        System.out.println("Request submitted with ID: " + requestId + " and urgency: " + urgency);
    }

    private void processRequests() {
        while (running) {
            try {
                CaptureRequest request = requestQueue.take(); // Block until a request is available
                lock.lock();
                try {
                    System.out.println("Processing request with ID: " + request.getRequestId());
                    // Register callbacks with CameraSystem
                    boolean success = cameraSystem.captureImage(request);
                    // Invoke callbacks based on success/failure
                    if (success) {
                        request.getSuccessCallback().call("image_data");
                    } else {
                        request.getFailureCallback().call("capture_error");
                    }
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupt status
                System.err.println("Request processing interrupted: " + e.getMessage());
            }
        }
    }

    private String generateRequestId() {
        return String.valueOf(System.currentTimeMillis());
    }

    public void shutdown() {
        running = false;
        Thread.currentThread().interrupt(); // Interrupt the processing thread
        System.out.println("ImageCaptureSystem shutting down.");
    }
}
