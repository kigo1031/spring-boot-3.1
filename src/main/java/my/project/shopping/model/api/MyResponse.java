package my.project.shopping.model.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyResponse implements Serializable {
    private int status;
    private String message;
    private boolean error;
    private List<Object> data;

    @Builder
    public MyResponse(int status, String message, boolean error, List<Object> data) {
        this.status = status;
        this.message = message;
        this.error = error;
        this.data = data;
    }
}
