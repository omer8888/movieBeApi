package Startup.example.Startup.AI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageRequest {

    private String model;

    private String prompt;

    private Integer n;

    private String size;
}
