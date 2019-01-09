package core;

public abstract class Command {
	private final CommandDescription description;

	/**
	 *
	 * @param context An object containing all important information
	 * @return If the help should be printed. False if not.
	 */
	public abstract boolean execute(Context context);

	public CommandDescription getDescription() {
		return description;
	}

	public Command() {
		description = getClass().getAnnotation(CommandDescription.class);
	}
}
