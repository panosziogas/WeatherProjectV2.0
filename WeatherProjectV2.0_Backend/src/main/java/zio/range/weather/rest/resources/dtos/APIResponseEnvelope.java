package zio.range.weather.rest.resources.dtos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Panos
 * @param <T>
 */
public class APIResponseEnvelope {

    private boolean success;
    protected Object payload;
    private final List<String> messages = new ArrayList<>(4);

    public APIResponseEnvelope() {
        this.payload = null;
    }

    public APIResponseEnvelope(Object payload) {
        this.payload = payload;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

        
}
