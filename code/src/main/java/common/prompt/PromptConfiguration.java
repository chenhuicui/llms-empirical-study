package common.prompt;


import android.util.Log;
import lombok.Getter;
import mo.must.common.constants.TestConstants;

/**
 * Provides a series of constant templates for generating prompt messages.
 * This class defines various template strings that are used in test implementations to generate specific prompt messages,
 * including, but not limited to, descriptions of app pages, input component prompts, resource ID prompts, etc.
 * Additionally, the class defines fully qualified class name constants for certain Android components such as EditText, for reference in tests.
 */
@Getter
public class PromptConfiguration {
    // Templates used to generate prompts
    public static final String APP_PAGE_DESCRIPTION_TEMPLATE = "This is an app named %s, and there are %d input components on its %s page, including %s. ";
    public static final String TEXT_INPUTS_GUIDELINE_TEMPLATE = "Please generate reasonable text input for the %s, and just return the text in the form of string(s) in an array, like [\"xxx\",\"xxx\"].";
    public static final String HINT_TEXT_TEMPLATE = "the hint text is [%s]. ";
    public static final String RESOURCE_ID_TEMPLATE = "the resource-id is %s. ";
    public static final String ADJACENT_LABEL_TEMPLATE = "The %s adjacent label of the %s %s is [%s]. ";
    public static final String COMPONENT_REFERENCE_TEMPLATE = "For the %s %s, ";
    public static final String PROMPT_TEXT_TEMPLATE = "the prompt text is [%s], ";
    public static final String CANDIDATE_OPTIONS_TEMPLATE = "and the candidate options are %s. ";
    public static final String INVALID_TEXT_INPUTS_GUIDELINE_TEMPLATE = "Please generate invalid text input for the %s, and just return the text in the form of string(s) in an array, like [\"xxx\",\"xxx\"].";
    public static final String GUIDING_GUIDELINE_TEMPLATE = "Explain why/how you choose these values step-by-step.";

    // Parsing exception related prompts
    public static final String PARSE_EXCEPTION_MSG = "Parse exception, test case array not found.";
    public static final String PARSE_RESULT_ERROR_MSG = "Unable to parse the returned result. Please provide me with a string array, like [\"xxx\",\"xxx\"] meet with the number of inputs, there are %d input are needed.";

    // Input component class names
    public static final String EDITTEXT_CLASS_NAME = "android.widget.EditText";
    public static final String AUTOCOMPLETE_TEXTVIEW_CLASS_NAME = "android.widget.AutoCompleteTextView";

    // Getters
    private boolean includeGlobal;
    private boolean includeComponent;
    private boolean includeRelated;
    private boolean includeRestrictive;
    private boolean includeGuiding;

    // Default constructor
    private PromptConfiguration(Builder builder) {
        this.includeGlobal = builder.includeGlobal;
        this.includeComponent = builder.includeComponent;
        this.includeRelated = builder.includeRelated;
        this.includeRestrictive = builder.includeRestrictive;
        this.includeGuiding = builder.includeGuiding;
    }

    public PromptConfiguration() {}

    /**
     * Builder class for constructing PromptConfiguration instances.
     */
    public static class Builder {
        private boolean includeGlobal;
        private boolean includeComponent;
        private boolean includeRelated;
        private boolean includeRestrictive;
        private boolean includeGuiding;

        public Builder withGlobal() {
            this.includeGlobal = true;
            Log.i(TestConstants.TAG, "Successfully set up the Global prompt");
            return this;
        }

        public Builder withComponent() {
            this.includeComponent = true;
            Log.i(TestConstants.TAG, "Successfully set up the Component prompt");
            return this;
        }

        public Builder withRelated() {
            this.includeRelated = true;
            Log.i(TestConstants.TAG, "Successfully set up the Related prompt");

            return this;
        }

        public Builder withRestrictive() {
            this.includeRestrictive = true;
            Log.i(TestConstants.TAG, "Successfully set up the Restrictive prompt");

            return this;
        }

        public Builder withGuiding() {
            this.includeGuiding = true;
            Log.i(TestConstants.TAG, "Successfully set up the Guiding prompt");

            return this;
        }

        /**
         * Builds and returns a PromptConfiguration instance based on the builder's setup.
         *
         * @return A PromptConfiguration instance
         */
        public PromptConfiguration build() {
            return new PromptConfiguration(this);
        }
    }
}
