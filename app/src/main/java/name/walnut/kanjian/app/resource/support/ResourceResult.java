package name.walnut.kanjian.app.resource.support;

/**
 * Created by walnut on 15/10/11.
 */
public class ResourceResult {
    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
