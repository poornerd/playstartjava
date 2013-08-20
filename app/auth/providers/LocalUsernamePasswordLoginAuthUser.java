package auth.providers;

import com.feth.play.module.pa.providers.password.DefaultUsernamePasswordAuthUser;
import com.feth.play.module.pa.user.NameIdentity;

public class LocalUsernamePasswordLoginAuthUser extends DefaultUsernamePasswordAuthUser implements NameIdentity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The session timeout in seconds
	 * Defaults to two weeks
	 */
	final static long SESSION_TIMEOUT = 24 * 14 * 3600;
	private long expiration;
	private String username;

	/**
	 * For logging the user in automatically
	 * 
	 * @param email
	 */
	public LocalUsernamePasswordLoginAuthUser(final String username) {
		this(null, username);
		this.username = username;
	}

	public LocalUsernamePasswordLoginAuthUser(final String clearPassword,
			final String username) {
		super(clearPassword, username);
		this.username = username;
		expiration = System.currentTimeMillis() + 1000 * SESSION_TIMEOUT;
	}
	
	public LocalUsernamePasswordLoginAuthUser(final String providerKey, final String id, final long expiration) {
		super(null, id);
		this.username = id;
		this.expiration = expiration;
	}

	public String getName() {
		return username;
	}
	
	@Override
	public long expires() {
		return expiration;
	}

}
