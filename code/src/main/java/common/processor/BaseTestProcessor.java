package common.processor;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import com.robotium.solo.Solo;
import mo.must.common.components.UIAutomatorInputComponent;
import mo.must.common.constants.TestConstants;
import mo.must.common.enums.StrategyTypeEnum;
import mo.must.common.llm.LLMRequester;
import mo.must.common.llm.ResponseProcessor;
import mo.must.common.patterns.strategy.IHierarchyParserStrategy;
import mo.must.common.patterns.strategy.factory.HierarchyParserStrategyFactory;
import mo.must.common.patterns.strategy.impl.UIAutomatorHierarchyParser;
import mo.must.common.prompt.PromptConfiguration;
import mo.must.common.prompt.PromptGenerator;
import mo.must.common.test.BaseTestCase;
import mo.must.common.test.TestAssert;
import mo.must.common.util.RobotiumUtils;
import mo.must.common.util.UIAutomatorUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * BaseTestProcessor is a utility class for simplifying and organizing Android UI tests.
 * It provides various methods for initializing the test environment, handling test case data, and filling test data into views.
 * This class combines multiple toolsets such as Robotium, UI Automator, and ChatGPTRequester to enhance the readability and maintainability of test code.
 */
@SuppressWarnings({"unchecked"})
public class BaseTestProcessor {

    private static final String TARGET_ACTIVITY_CLASS = "targetActivityClass";
    private static final String LAUNCHER_ACTIVITY_CLASS = "launcherActivityClass";

    public static BaseTestCase init(String modelName, ActivityScenarioRule<?> activityRule, PromptConfiguration promptConfiguration,
                                    Map<String, String> classNameMap) {
        return init(modelName, activityRule, promptConfiguration, classNameMap, StrategyTypeEnum.UI_AUTOMATOR, null);
    }

    public static BaseTestCase init(String modelName, ActivityScenarioRule<?> activityRule, PromptConfiguration promptConfiguration,
                                    Map<String, String> classNameMap, String strategyTypeContent) {
        return init(modelName, activityRule, promptConfiguration, classNameMap, StrategyTypeEnum.JSON_NODE, strategyTypeContent);
    }

    public static BaseTestCase init(String modelName,
                                    ActivityScenarioRule<?> activityScenarioRule,
                                    PromptConfiguration promptConfiguration,
                                    Map<String, String> classNameMap,
                                    StrategyTypeEnum strategyTypeEnum,
                                    String strategyTypeContent) {
        BaseTestCase baseTestCase = new BaseTestCase();
        Map<String, Class<?>> classMap = new HashMap<>(classNameMap.size());
        for (Map.Entry<String, String> entry : classNameMap.entrySet()) {
            classMap.put(entry.getKey(), initClass(entry.getValue()));
        }
        baseTestCase.setActivityScenarioRule(activityScenarioRule);
        baseTestCase.setClassMap(classMap);
        baseTestCase.setSolo(initSolo());
        RobotiumUtils.init(baseTestCase.getSolo());
        baseTestCase.setUiDevice(initUiDevice());
        UIAutomatorUtils.init(baseTestCase.getUiDevice());
        baseTestCase.setPromptConfiguration(promptConfiguration);
        baseTestCase.setModelType(TestConstants.modelMap.get(modelName));
        baseTestCase.setStrategyTypeEnum(strategyTypeEnum);
        baseTestCase.setStrategyTypeContent(strategyTypeContent);
        return baseTestCase;
    }

    public static Class<?> initClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static UiDevice initUiDevice() {
        return UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    private static Solo initSolo() {
        return new Solo(InstrumentationRegistry.getInstrumentation());
    }

    public static void fillTextIntoViewsByIndex(BaseTestCase baseTestCase) {
        String mActivity;
        if (!baseTestCase.getClassMap().get(TARGET_ACTIVITY_CLASS).equals(baseTestCase.getClassMap().get(LAUNCHER_ACTIVITY_CLASS))) {
            jumpToTargetActivity(baseTestCase.getClassMap().get(TARGET_ACTIVITY_CLASS));
            mActivity = TARGET_ACTIVITY_CLASS;
        } else {
            mActivity = LAUNCHER_ACTIVITY_CLASS;
        }
        RobotiumUtils.waitForActivity((Class<? extends Activity>) baseTestCase.getClassMap().get(mActivity), TestConstants.getMillisForSeconds(3));
        IHierarchyParserStrategy<?, ?> parserStrategy = createStrategy(baseTestCase.getStrategyTypeEnum(), baseTestCase.getStrategyTypeContent());
        PromptGenerator<UIAutomatorInputComponent> generator = new PromptGenerator<>((UIAutomatorHierarchyParser) parserStrategy, true);
        String[] response = getResponse(baseTestCase.getPromptConfiguration(), baseTestCase.getModelType(), generator);

        List<String> testCases = getTestCase(response);
        List<Integer> inputs = parserStrategy.getInputIdList();
        testCases = checkTestCaseForm(baseTestCase.getModelType(), testCases, inputs.size(), response[0]);
        fillTextIntoViewsByIndex(testCases, testCases.size());
    }

    private static void jumpToTargetActivity(Class<?> targetActivityClass) {
        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), targetActivityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        InstrumentationRegistry.getInstrumentation().startActivitySync(intent);
    }

    private static IHierarchyParserStrategy<?, ?> createStrategy(StrategyTypeEnum strategyTypeEnum, String strategyTypeContent) {
        return HierarchyParserStrategyFactory.createStrategy(strategyTypeEnum,
                strategyTypeEnum.name().equals(StrategyTypeEnum.JSON_NODE.name()) ? strategyTypeContent : null,
                PromptConfiguration.EDITTEXT_CLASS_NAME, PromptConfiguration.AUTOCOMPLETE_TEXTVIEW_CLASS_NAME);
    }

    private static String[] getResponse(PromptConfiguration promptConfiguration, String modelType, PromptGenerator<UIAutomatorInputComponent> generator) {
        String prompt = generator.generatePrompt(promptConfiguration);
        Log.d(TestConstants.TAG, "Generated Question: " + prompt);
        return LLMRequester.chat(null, prompt, modelType);
    }

    private static List<String> getTestCase(String[] response) {
        TestAssert.assertNotNull(response);
        return ResponseProcessor.extractTestCases(response[1]);
    }

    private static List<String> checkTestCaseForm(String modelType, List<String> testCases, int inputSize, String chatSessionId) {
        while (!Objects.equals(testCases.size(), inputSize) || (testCases.size() == 1 && PromptConfiguration.PARSE_EXCEPTION_MSG.equals(testCases.get(0)))) {
            Log.e(TestConstants.TAG, PromptConfiguration.PARSE_EXCEPTION_MSG);
            String errMsg = String.format(PromptConfiguration.PARSE_RESULT_ERROR_MSG, inputSize);
            String[] newResponse = LLMRequester.chat(chatSessionId, errMsg, modelType);
            testCases = ResponseProcessor.extractTestCases(newResponse[1]);
        }
        return testCases;
    }

    private static void fillTextIntoViews(List<String> testCases, List<Integer> ids) {
        for (int i = 0; i < ids.size(); i++) {
            int id = ids.get(i);
            String testCase = testCases.get(i);
            RobotiumUtils.fillTextInTextView(id, testCase);
            if (RobotiumUtils.waitForText(testCase)) {
                Log.d(TestConstants.TAG, "Component: " + id + " successfully filled.");
            }
        }
    }

    private static void fillTextIntoViewsByIndex(List<String> testCases, int length) {
        for (int index = 0; index < length; index++) {
            String testCase = testCases.get(index);
            RobotiumUtils.fillTextInTextViewByIndex(index, testCase);
            if (RobotiumUtils.waitForText(testCase)) {
                Log.d(TestConstants.TAG, "Component: " + index + " successfully filled.");
            }
        }
    }
}
