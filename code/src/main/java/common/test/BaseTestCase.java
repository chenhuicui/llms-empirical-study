package common.test;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.uiautomator.UiDevice;
import com.robotium.solo.Solo;
import lombok.Data;
import mo.must.common.enums.StrategyTypeEnum;
import mo.must.common.prompt.PromptConfiguration;

import java.util.Map;

/**
 * BaseTestCase is a class used for initializing information for test cases.
 * It includes attributes such as ActivityScenarioRule, UiDevice, Solo, PromptConfiguration, etc.,
 * which are essential for conducting UI automation tests.
 */
@Data
public class BaseTestCase {
    private ActivityScenarioRule<?> activityScenarioRule;
    private UiDevice uiDevice;
    private Solo solo;
    private PromptConfiguration promptConfiguration;
    private String modelType;
    private Map<String, Class<?>> classMap;
    private StrategyTypeEnum strategyTypeEnum;
    private String strategyTypeContent;
}
