package auth.providers;


import static play.data.Form.form;

import java.util.ArrayList;
import java.util.List;

import play.Application;
import play.Configuration;
import play.Logger;
import play.data.Form;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.i18n.Messages;
import play.mvc.Call;
import play.mvc.Http.Context;

import com.feth.play.module.mail.Mailer.Mail.Body;
import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.exceptions.AuthException;
import com.feth.play.module.pa.providers.password.UsernamePasswordAuthProvider;
import com.feth.play.module.pa.providers.password.UsernamePasswordAuthUser;

import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.ResultCode;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchScope;

public class LdapUsernamePasswordAuthProvider
		extends
		UsernamePasswordAuthProvider<String, LdapUsernamePasswordAuthUser, LdapUsernamePasswordAuthUser, LdapUsernamePasswordAuthProvider.LdapLogin, LdapUsernamePasswordAuthProvider.LdapLogin> {

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

	public static class LdapLogin extends LdapIdentity
			implements
			com.feth.play.module.pa.providers.password.UsernamePasswordAuthProvider.UsernamePassword {

		@Required
		@MinLength(3)
		public String password;

		/**
		 * the interface requires a method <code>getEmail()</code> though it's not said that the username IS an email address
		 */
		@Override
		public String getEmail() {
			return getUsername();
		}
		
		public String getUsername() {
			return username;
		}

		@Override
		public String getPassword() {
			return password;
		}
	}

	public static final Form<LdapLogin> LOGIN_FORM = form(LdapLogin.class);

	public LdapUsernamePasswordAuthProvider(Application app) {
		super(app);
	}

	protected Form<LdapLogin> getSignupForm() {
		return null;
	}

	protected Form<LdapLogin> getLoginForm() {
		return LOGIN_FORM;
	}

	@Override
	protected com.feth.play.module.pa.providers.password.UsernamePasswordAuthProvider.SignupResult signupUser(final LdapUsernamePasswordAuthUser user) {
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

	@Override
	protected com.feth.play.module.pa.providers.password.UsernamePasswordAuthProvider.LoginResult loginUser(
			final LdapUsernamePasswordAuthUser authUser) {

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
			ldap = new LDAPConnection(ldapHost, ldapPort, dn, authUser.getPassword());
			// ** if login with password fails, an LDAPException is thrown with resultCode == ResultCode.INVALID_CREDENTIALS **
		}
		return userEntry;			
	}
	
	/**
	 * retrieves some user data from LDAP and puts it into the bean itself. 
	 * @param authUser the user that contains the username and will be filled with contactEmail, firstName, lastName, fullName
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
					authUser.contactEmail = ldapEmail;
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
			return super.authenticate(context, payload);
		} else {
			throw new AuthException("LDAP authentication does not support signup.");
		}
	}
	
	@Override
	protected Call userExists(final UsernamePasswordAuthUser authUser) {
		return controllers.sec.routes.Signup.exists();
	}

	@Override
	protected Call userUnverified(final UsernamePasswordAuthUser authUser) {
		return controllers.sec.routes.Signup.unverified();
	}

	@Override
	protected LdapUsernamePasswordAuthUser buildSignupAuthUser(
			final LdapLogin signup, final Context ctx) {
		return null;
	} 

	@Override
	protected LdapUsernamePasswordAuthUser buildLoginAuthUser(
			final LdapLogin login, final Context ctx) {
		return new LdapUsernamePasswordAuthUser(login.getPassword(), login.getUsername());
	}
	
	/**
	* This gets called when the user shall be logged in directly after signing up
	*
	* @param authUser
	* @param context
	* @return
	*/
	@Override
	protected LdapUsernamePasswordAuthUser transformAuthUser(final LdapUsernamePasswordAuthUser authUser, final Context context) {
		return new LdapUsernamePasswordAuthUser(null, authUser.getUsername());
	}

	@Override
	protected String getVerifyEmailMailingSubject(
			final LdapUsernamePasswordAuthUser user, final Context ctx) {
		return null;
	}

	@Override
	protected String onLoginUserNotFound(final Context context) {
		context.flash()
				.put(controllers.Application.FLASH_ERROR_KEY,
						Messages.get("playauthenticate.password.login.unknown_user_or_pw"));
		return super.onLoginUserNotFound(context);
	}


	@Override
	protected Body getVerifyEmailMailingBody(final String token,
			final LdapUsernamePasswordAuthUser user, final Context ctx) {

		return null;
	}

	@Override
	protected String generateVerificationRecord(
			final LdapUsernamePasswordAuthUser user) {
		return null;
	}

}
