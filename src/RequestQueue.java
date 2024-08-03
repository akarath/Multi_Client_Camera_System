import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class RequestQueue {
    private final PriorityBlockingQueue<CaptureRequest> queue;

    public RequestQueue() {
        this.queue = new PriorityBlockingQueue<>(11, Comparator.comparingInt(CaptureRequest::getUrgency).reversed());
    }

    public void enqueue(CaptureRequest request) {
        queue.put(request);
    }

    public CaptureRequest dequeue() throws InterruptedException {
        return queue.take();
    }
}
