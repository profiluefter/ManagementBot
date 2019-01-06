package core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandDescription {
	/**
	 * @return An array of names that trigger the command.
	 */
	String[] name();

	/**
	 * @return The key of the description in the strings file.
	 */
	String help() default "help.notAvailable";
}
