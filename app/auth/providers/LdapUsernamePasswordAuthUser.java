package auth.providers;

import com.feth.play.module.pa.user.FirstLastNameIdentity;

public class LdapUsernamePasswordAuthUser implements FirstLastNameIdentity {

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
		this.clearPassword = clearPassword;
		this.username = username;
		expiration = System.currentTimeMillis() + 1000 * SESSION_TIMEOUT;
	}

	public String getUsername() {
		return this.username;
	}
	
	public long expires() {
		return expiration;
	}
	
	public String username = null;
	public String clearPassword = null;
	
	public String firstName = null;
	public String lastName = null;
	public String fullName = null;
	public String contactEmail = null; // ** we must not name it "email" because that property is already used as username in superclass UsernamePasswordAuthUser **

	@Override
	public String getName() {
		return fullName;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}
}
