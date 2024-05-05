package uz.pdp.lesson.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Archive {
    private Long userId;
    private String currency;
    private Date date;
    private String rate;
}
