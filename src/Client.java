public class Client {
    private final ImageCaptureSystem imageCaptureSystem;
    private final int urgency;

    public Client(ImageCaptureSystem imageCaptureSystem, int urgency) {
        this.imageCaptureSystem = imageCaptureSystem;
        this.urgency = urgency;
    }

    public int getUrgency() {
        return urgency;
    }

    public void submitCaptureRequest(Callback<String> successCallback, Callback<String> failureCallback) {
        imageCaptureSystem.submitCaptureRequest(urgency, successCallback, failureCallback);
    }
}
