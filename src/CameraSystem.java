import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CameraSystem {
    private final Lock lock;

    public CameraSystem() {
        this.lock = new ReentrantLock();
    }

    public boolean captureImage(CaptureRequest request) {
        lock.lock();
        try {
            System.out.println("Capturing image for request ID: " + request.getRequestId());
            Thread.sleep(3000); // Simulate capture delay

            boolean success = Math.random() > 0.5; // 50% success rate
            if (success) {
                System.out.println("Image captured successfully for request ID: " + request.getRequestId());
            } else {
                System.out.println("Image capture failed for request ID: " + request.getRequestId());
            }

            // Return the success status
            return success;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread interrupted: " + e.getMessage());
            return false; // Return false if interrupted
        } finally {
            lock.unlock();
        }
    }
}
