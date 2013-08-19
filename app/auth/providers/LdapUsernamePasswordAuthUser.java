package auth.providers;

import com.feth.play.module.pa.user.EmailIdentity;
import com.feth.play.module.pa.user.NameIdentity;
import com.feth.play.module.pa.user.SessionAuthUser;
import com.feth.play.module.pa.user.FirstLastNameIdentity;

public class LdapUsernamePasswordAuthUser extends SessionAuthUser implements FirstLastNameIdentity, EmailIdentity, NameIdentity {

	/**
	 * must implement this field:
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The session timeout in seconds
	 * Defaults to two weeks
	 */
	final static long SESSION_TIMEOUT = 24 * 14 * 3600;

	public LdapUsernamePasswordAuthUser(String clearPassword, String username) {
		super(LdapUsernamePasswordAuthProvider.getProvider().getKey(), username, System.currentTimeMillis() + 1000 * SESSION_TIMEOUT);
		this.clearPassword = clearPassword;
		this.username = username;
	}

	public String username = null;
	public String clearPassword = null;
	
	public String firstName = null;
	public String lastName = null;
	public String fullName = null;
	public String email = null; 

	public String getUsername() {
		return this.username;
	}
	
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

	@Override
	public String getEmail() {
		return email;
	}

}
