public class Client {
    private final ImageCaptureSystem imageCaptureSystem;

    public Client(ImageCaptureSystem imageCaptureSystem) {
        this.imageCaptureSystem = imageCaptureSystem;
    }

    public void submitCaptureRequest(int urgency, Callback<String> successCallback, Callback<String> failureCallback) {
        imageCaptureSystem.submitCaptureRequest(urgency, successCallback, failureCallback);
    }
}
