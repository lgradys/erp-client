package warehouse.erpclient.utils.dto;

import java.util.List;

public class RequestResult<T> {

    private int status;
    private List<Error> error;
    private List<T> resource;

    public RequestResult() {
    }

    public RequestResult(int status, List<Error> error, List<T> resource) {
        this.status = status;
        this.error = error;
        this.resource = resource;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Error> getError() {
        return error;
    }

    public void setError(List<Error> error) {
        this.error = error;
    }

    public List<T> getResource() {
        return resource;
    }

    public void setResource(List<T> resource) {
        this.resource = resource;
    }
}
