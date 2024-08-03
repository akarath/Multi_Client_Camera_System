import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class CallbackRegistry {
    private final Map<String, Consumer<String>> successCallbacks;
    private final Map<String, Consumer<String>> failureCallbacks;

    public CallbackRegistry() {
        this.successCallbacks = new ConcurrentHashMap<>();
        this.failureCallbacks = new ConcurrentHashMap<>();
    }

    public void registerSuccessCallback(String requestId, Callback<String> callback) {
        successCallbacks.put(requestId, callback::call);
    }

    public void registerFailureCallback(String requestId, Callback<String> callback) {
        failureCallbacks.put(requestId, callback::call);
    }

    public void invokeSuccessCallback(String requestId, String result) {
        Consumer<String> callback = successCallbacks.get(requestId);
        if (callback != null) {
            callback.accept(result);
        } else {
            System.err.println("No success callback registered for request ID: " + requestId);
        }
    }

    public void invokeFailureCallback(String requestId, String error) {
        Consumer<String> callback = failureCallbacks.get(requestId);
        if (callback != null) {
            callback.accept(error);
        } else {
            System.err.println("No failure callback registered for request ID: " + requestId);
        }
    }
}
