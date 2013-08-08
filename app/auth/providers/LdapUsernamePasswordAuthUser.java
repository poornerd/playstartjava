package auth.providers;

import com.feth.play.module.pa.providers.password.DefaultUsernamePasswordAuthUser;

public class LdapUsernamePasswordAuthUser extends
		DefaultUsernamePasswordAuthUser {

	/**
	 * must implement this field:
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The session timeout in seconds
	 * Defaults to two weeks
	 */
	final static long SESSION_TIMEOUT = 24 * 14 * 3600;
	private long expiration;

	public LdapUsernamePasswordAuthUser(String clearPassword, String username) {
		super(clearPassword, username);
		expiration = System.currentTimeMillis() + 1000 * SESSION_TIMEOUT;
	}

	public String getUsername() {
		return super.getEmail();
	}
	
	@Override
	public long expires() {
		return expiration;
	}
	
	public String firstName = null;
	public String lastName = null;
	public String fullName = null;
	public String contactEmail = null; // ** we must not name it "email" because that property is already used as username in superclass UsernamePasswordAuthUser **
}
