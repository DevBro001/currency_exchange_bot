package uz.pdp.lesson.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyUser {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String baseState;
    private String state;
    private String photoId;




}
