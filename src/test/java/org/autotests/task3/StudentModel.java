package org.autotests.task3;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentModel {
    private Integer id;
    private String name;
    private int[] marks;
}
