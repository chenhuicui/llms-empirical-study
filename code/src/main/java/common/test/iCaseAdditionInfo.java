package common.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate additional information for test cases.
 * This annotation can be applied to test classes or test methods
 * to provide more details about the test case such as author, date,
 * description, and title. This helps in tracking test case metadata
 * and providing more context in reports or documentation.
 *
 * @Author cuichenhui
 * @Date 2023-09-29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface iCaseAdditionInfo {
    String author() default ""; // Author
    String date() default "";   // Date
    String description() default ""; // Description
    String title() default ""; // Title
}