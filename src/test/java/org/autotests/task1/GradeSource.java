package org.autotests.task1;

import java.util.Collections;
import java.util.List;

public class GradeSource {
    public static List<List<Integer>> listsOfGrades() {
        return List.of(List.of(5, 3, 2, 4), List.of(5), Collections.emptyList());
    }
}
