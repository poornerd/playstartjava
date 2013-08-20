package auth.providers;

import auth.providers.LocalUsernamePasswordAuthProvider.MySignup;

import com.feth.play.module.pa.providers.password.UsernamePasswordAuthUser;
import com.feth.play.module.pa.user.EmailIdentity;
import com.feth.play.module.pa.user.FirstLastNameIdentity;
import com.feth.play.module.pa.user.NameIdentity;

public class LocalUsernamePasswordAuthUser extends UsernamePasswordAuthUser implements NameIdentity, FirstLastNameIdentity 
{
	/**
	 * 
	 */
	static final long serialVersionUID = 1L;
	private final String firstName;
	private final String lastName;
	private final String username;

	public LocalUsernamePasswordAuthUser(final MySignup signup) {
		super(signup.password, signup.email);
		this.firstName = signup.firstName;
		this.lastName = signup.lastName;
		this.username = signup.username;
	}

	/**
	 * Used for password reset only - do not use this to signup a user!
	 * @param password
	 */
	public LocalUsernamePasswordAuthUser(final String password) {
		super(password, null);
		lastName = null;
		firstName = null;
		username = null;
	}
	
	public String getUsername() {
		return getName();
	}
	
	@Override
	public String getName() {
		return this.username;
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
