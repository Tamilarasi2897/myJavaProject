package sx3Configuration.programming;
public class OSValidator {
	
	public enum Level {
	    WINDOWS,
	    MAC,
	    LINUX,
	    NONE
	  }

	private static String OS = System.getProperty("os.name").toLowerCase();
	
	public Level getOS() {
		System.out.println(OS);

		if (isWindows()) {
			System.out.println("This is Windows");
			return Level.WINDOWS;
		} else if (isMac()) {
			System.out.println("This is Mac");
			return Level.MAC;
		} else if (isUnix()) {
			System.out.println("This is Unix or Linux");
			return Level.LINUX;
		} else {
			System.out.println("Your OS is not support!!");
			return Level.NONE;
		}
	}

	public static void main(String[] args) {
		OSValidator osValidator = new OSValidator();
		System.out.println(osValidator.getOS());
		
	}

	public static boolean isWindows() {

		return (OS.indexOf("win") >= 0);

	}

	public static boolean isMac() {

		return (OS.indexOf("mac") >= 0);

	}

	public static boolean isUnix() {

		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );

	}

	public static boolean isSolaris() {

		return (OS.indexOf("sunos") >= 0);

	}

}