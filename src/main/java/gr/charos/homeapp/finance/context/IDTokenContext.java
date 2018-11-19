package gr.charos.homeapp.finance.context;

public class IDTokenContext {

	  private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

	  public static void setUsername(String username) {
	    CONTEXT.set(username);
	  }

	  public static String getUsername() {
	    return CONTEXT.get();
	  }

	  public static void clear() {
	    CONTEXT.remove();
	  }
	}