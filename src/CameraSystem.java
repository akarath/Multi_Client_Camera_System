import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CameraSystem {
    private final Lock lock;

    public CameraSystem() {
        this.lock = new ReentrantLock();
    }

    public boolean captureImage(CaptureRequest request) {
        new Thread(() -> {
            try {
                lock.lock();
                System.out.println("Capturing image for request ID: " + request.getRequestId());
                Thread.sleep(3000); // Simulate capture delay
                boolean success = Math.random() > 0.5; // 50% success rate
                if (success) {
                    System.out.println("Image captured successfully for request ID: " + request.getRequestId());
                } else {
                    System.out.println("Image capture failed for request ID: " + request.getRequestId());
                }
                // Signal completion (callback invocation will be done in ImageCaptureSystem)
                // Here we use a thread sleep to simulate a delay in the callback invocation
                try {
                    Thread.sleep(1000); // Simulate delay in handling callback
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread interrupted: " + e.getMessage());
            } finally {
                lock.unlock();
            }
        }).start();
        // Return a dummy value; actual success/failure will be handled by callbacks
        return false;
    }
}
