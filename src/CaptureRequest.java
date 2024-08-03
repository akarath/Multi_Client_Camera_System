public class CaptureRequest {
    private final String requestId;
    private final int urgency;
    private final Callback<String> successCallback;
    private final Callback<String> failureCallback;

    public CaptureRequest(String requestId, int urgency, Callback<String> successCallback, Callback<String> failureCallback) {
        this.requestId = requestId;
        this.urgency = urgency;
        this.successCallback = successCallback;
        this.failureCallback = failureCallback;
    }

    public String getRequestId() {
        return requestId;
    }

    public int getUrgency() {
        return urgency;
    }

    public Callback<String> getSuccessCallback() {
        return successCallback;
    }

    public Callback<String> getFailureCallback() {
        return failureCallback;
    }
}
