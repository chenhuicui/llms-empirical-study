package common.constants;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Testcase {

    private List<String> testcases = new ArrayList<>();
    private boolean isTrigger = false;
}
