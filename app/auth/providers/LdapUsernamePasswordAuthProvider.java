package auth.providers;


import static play.data.Form.form;

import java.util.ArrayList;
import java.util.List;

import play.Application;
import play.Logger;
import play.data.Form;
import play.data.validation.Constraints.Required;
import play.i18n.Messages;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Result;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.exceptions.AuthException;
import com.feth.play.module.pa.providers.AuthProvider;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.ResultCode;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchScope;

import controllers.routes;

public class LdapUsernamePasswordAuthProvider extends AuthProvider {

	public static final String LDAP_PROVIDER_KEY = "ldap";
	
	private static final String SETTING_KEY_LDAP_HOST = "host";
	private static final String SETTING_KEY_LDAP_PORT = "port";
	private static final String SETTING_KEY_LDAP_LOGIN_USER = "login.user";
	private static final String SETTING_KEY_LDAP_LOGIN_PASSWORD = "login.password";
	private static final String SETTING_KEY_LDAP_BASE_DN = "basedn";
	private static final String SETTING_KEY_LDAP_USER_USERID_ATTRIBUTE = "attributekeys.userid";
	private static final String SETTING_KEY_LDAP_USER_EMAIL_ATTRIBUTE = "attributekeys.email";
	private static final String SETTING_KEY_LDAP_USER_FIRSTNAME_ATTRIBUTE = "attributekeys.firstname";
	private static final String SETTING_KEY_LDAP_USER_LASTNAME_ATTRIBUTE = "attributekeys.lastname";
	private static final String SETTING_KEY_LDAP_USER_FULLNAME_ATTRIBUTE = "attributekeys.fullname";
	

	private enum Case {
		LOGIN
	}
	
	protected enum LoginResult {
		USER_LOGGED_IN, NOT_FOUND, WRONG_PASSWORD
	}
	
	@Override
	protected List<String> neededSettingKeys() {
		final List<String> needed = new ArrayList<String>();
		needed.add(SETTING_KEY_LDAP_HOST);
		needed.add(SETTING_KEY_LDAP_PORT);
		needed.add(SETTING_KEY_LDAP_BASE_DN);
		needed.add(SETTING_KEY_LDAP_USER_USERID_ATTRIBUTE);
		needed.add(SETTING_KEY_LDAP_USER_EMAIL_ATTRIBUTE);
		//needed.add(SETTING_KEY_LDAP_USER_FIRSTNAME_ATTRIBUTE);
		//needed.add(SETTING_KEY_LDAP_USER_LASTNAME_ATTRIBUTE);
		//needed.add(SETTING_KEY_LDAP_USER_FULLNAME_ATTRIBUTE);
		return needed;
	}

	public static LdapUsernamePasswordAuthProvider getProvider() {
		return (LdapUsernamePasswordAuthProvider) PlayAuthenticate.getProvider(LDAP_PROVIDER_KEY);
	}
	
	@Override
	public String getKey() {
		return LDAP_PROVIDER_KEY;
	}
	
	public static class LdapIdentity {

		public LdapIdentity() {
		}

		public LdapIdentity(final String username) {
			this.username = username;
		}

		@Required
		public String username;

	}

	public static class LdapLogin {
		@Required
		public String username;
		
		@Required
		public String password;
		
	}

	public static Form<LdapLogin> getLoginForm() {
		return form(LdapLogin.class);
	}

	public LdapUsernamePasswordAuthProvider(Application app) {
		super(app);
	}

	protected Form<LdapLogin> getSignupForm() {
		return null;
	}
	
	private LDAPConnection getLDAPConnection() throws LDAPException {
		String ldapHost = getConfiguration().getString(SETTING_KEY_LDAP_HOST);
		int ldapPort = getConfiguration().getInt(SETTING_KEY_LDAP_PORT);
		String ldapLoginUser = getConfiguration().getString(SETTING_KEY_LDAP_LOGIN_USER);
		String ldapLoginPwd = getConfiguration().getString(SETTING_KEY_LDAP_LOGIN_PASSWORD);
		
		if(ldapLoginUser!=null && ldapLoginPwd!=null) {
			return new LDAPConnection(ldapHost, ldapPort, ldapLoginUser, ldapLoginPwd);
		}
		return new LDAPConnection(ldapHost, ldapPort);
	}

	protected LoginResult loginUser(final LdapUsernamePasswordAuthUser authUser) {
		try {
			SearchResultEntry userEntry = findLdapUserEntry(authUser, true);
			if (userEntry != null) {
				return LoginResult.USER_LOGGED_IN;
			} else {
				return LoginResult.NOT_FOUND;
			}
			
		} catch (LDAPException e) {
			if (e.getResultCode() == ResultCode.INVALID_CREDENTIALS) {
				return LoginResult.WRONG_PASSWORD;
			} else {
				Logger.error("Could not login to LDAP " + getConfiguration().getString(SETTING_KEY_LDAP_HOST) + ":" + getConfiguration().getInt(SETTING_KEY_LDAP_PORT) + " with user '" + authUser.getUsername() + "'.", e);
				return LoginResult.NOT_FOUND;
			}
		} 
	}
	
	protected SearchResultEntry findLdapUserEntry(final LdapUsernamePasswordAuthUser authUser, boolean withLogin) throws LDAPException {
		LDAPConnection ldap = null;		
		ldap = getLDAPConnection();
		
		String ldapBaseDn = getConfiguration().getString(SETTING_KEY_LDAP_BASE_DN);
		String ldapUserIdAttr = getConfiguration().getString(SETTING_KEY_LDAP_USER_USERID_ATTRIBUTE);
		SearchResult sr = ldap.search(ldapBaseDn, SearchScope.SUB, "(" + ldapUserIdAttr + "=" + authUser.getUsername() + ")");
		if (sr.getEntryCount() == 0) { // user is not in LDAP
			return null;
		}

		String dn = sr.getSearchEntries().get(0).getDN();
		SearchResultEntry userEntry = sr.getSearchEntries().get(0);

		if (withLogin) {
			String ldapHost = getConfiguration().getString(SETTING_KEY_LDAP_HOST);
			int ldapPort = getConfiguration().getInt(SETTING_KEY_LDAP_PORT);
			ldap = new LDAPConnection(ldapHost, ldapPort, dn, authUser.clearPassword);
			// ** if login with password fails, an LDAPException is thrown with resultCode == ResultCode.INVALID_CREDENTIALS **
		}
		return userEntry;			
	}
	
	/**
	 * retrieves some user data from LDAP and puts it into the bean itself. 
	 * @param authUser the user that contains the username and will be filled with email, firstName, lastName, fullName
	 */
	public void receiveUserDataFromLdap(LdapUsernamePasswordAuthUser authUser) {
		try {
			SearchResultEntry userEntry = findLdapUserEntry(authUser, false);
			if (userEntry != null) {
				String ldapEmail = userEntry.getAttributeValue(getConfiguration().getString(SETTING_KEY_LDAP_USER_EMAIL_ATTRIBUTE));
				String ldapFirstname = null;
				String ldapLastname = null;
				String ldapFullname = null;

				String ldapUserFullnameAttr = getConfiguration().getString(SETTING_KEY_LDAP_USER_FULLNAME_ATTRIBUTE);
				if(ldapUserFullnameAttr!=null) {
					ldapFullname = userEntry.getAttributeValue(ldapUserFullnameAttr);
					if(ldapFullname.indexOf(",")!=-1) {
						ldapFirstname = ldapFullname.split(",")[1].trim();
						ldapLastname = ldapFullname.split(",")[0].trim();
					}
				} else {
					String ldapUserFirstnameAttr = getConfiguration().getString(SETTING_KEY_LDAP_USER_FIRSTNAME_ATTRIBUTE);
					String ldapUserLastnameAttr = getConfiguration().getString(SETTING_KEY_LDAP_USER_LASTNAME_ATTRIBUTE);
					ldapFirstname = userEntry.getAttributeValue(ldapUserFirstnameAttr);
					ldapLastname = userEntry.getAttributeValue(ldapUserLastnameAttr);
				} 
				if (ldapEmail != null) {
					authUser.email = ldapEmail;
				}
				if (ldapFirstname != null) {
					authUser.firstName = ldapFirstname;
				}
				if (ldapLastname != null) {
					authUser.lastName = ldapLastname;
				}
				if (ldapFullname != null) {
					authUser.fullName = ldapFullname;
				}
			} 
		} catch (LDAPException e) {
			Logger.error("Could not login to LDAP " + getConfiguration().getString(SETTING_KEY_LDAP_HOST) + ":" + getConfiguration().getInt(SETTING_KEY_LDAP_PORT) + " with user '" + authUser.getUsername() + "'.", e);
		} 
	}

	@Override
	public Object authenticate(final Context context, final Object payload)
			throws AuthException {

		if (payload == Case.LOGIN) {
			final LdapLogin login = getLogin(context);
			final LdapUsernamePasswordAuthUser authUser = buildLoginAuthUser(login, context);
			final LoginResult r = loginUser(authUser);
			switch (r) {
			case USER_LOGGED_IN:
				// The user exists and the given password was correct
				// ** fill the data of the user **
				receiveUserDataFromLdap(authUser);
				return authUser;
			case WRONG_PASSWORD:
				// don't expose this - it might harm users privacy if anyone
				// knows they signed up for our service
			case NOT_FOUND:
				// forward to login page
				return onLoginUserNotFound(context);
			default:
				throw new AuthException("Something in login went wrong");
			}
		} else {
			return PlayAuthenticate.getResolver().login().url();
		}
	}
	
	public static Result handleLogin(final Context ctx) {
		return PlayAuthenticate.handleAuthentication(LDAP_PROVIDER_KEY, ctx, Case.LOGIN);
	}
	
	private LdapLogin getLogin(final Context ctx) {
		// TODO change to getSignupForm().bindFromRequest(request) after 2.1
		Http.Context.current.set(ctx);
		final Form<LdapLogin> filledForm = getLoginForm().bindFromRequest();
		return filledForm.get();
	}
	
	protected LdapUsernamePasswordAuthUser buildLoginAuthUser(final LdapLogin login, final Context ctx) {
		return new LdapUsernamePasswordAuthUser(login.password, login.username);
	}
	
	/**
	* This gets called when the user shall be logged in directly after signing up
	*
	* @param authUser
	* @param context
	* @return
	*/
	protected LdapUsernamePasswordAuthUser transformAuthUser(final LdapUsernamePasswordAuthUser authUser, final Context context) {
		return new LdapUsernamePasswordAuthUser(null, authUser.getUsername());
	}

	protected String onLoginUserNotFound(final Context context) {
		context.flash()
				.put(controllers.Application.FLASH_ERROR_KEY,
						Messages.get("playauthenticate.password.login.unknown_user_or_pw"));
		return PlayAuthenticate.getResolver().login().url();
	}

	/**
	 * only external providers are shown in the scala template forProviders
	 */
	@Override
	public boolean isExternal() {
		return true;
	}

	@Override
	public String getUrl() {
		return routes.Application.ldapLogin().url();
	}
}
