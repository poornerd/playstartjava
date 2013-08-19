package auth.providers;



import auth.providers.EmailPasswordAuthProvider.MySignup;

import com.feth.play.module.pa.providers.password.UsernamePasswordAuthUser;
import com.feth.play.module.pa.user.NameIdentity;

public class EmailPasswordAuthUser extends UsernamePasswordAuthUser
		implements NameIdentity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String firstName;
	private final String lastName;

	public EmailPasswordAuthUser(final MySignup signup) {
		super(signup.password, signup.email);
		this.firstName = signup.firstName;
		this.lastName = signup.lastName;
	}

	/**
	 * Used for password reset only - do not use this to signup a user!
	 * @param password
	 */
	public EmailPasswordAuthUser(final String password) {
		super(password, null);
		lastName = null;
		firstName = null;
	}

	@Override
	public String getName() {
		StringBuffer sb = new StringBuffer();
		if (firstName != null) {
			sb.append(firstName);
			sb.append(" ");
		}
		if (lastName != null) {
			sb.append(lastName);
		}
		return sb.toString();
	}
}
