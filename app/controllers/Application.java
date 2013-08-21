package controllers;

import java.text.DateFormat;
import java.util.Date;
import play.*;
import play.data.Form;
import play.data.validation.ValidationError;
import play.i18n.Lang;
import play.i18n.Messages;
import play.mvc.*;
import static play.mvc.Controller.ctx;
import static play.mvc.Controller.response;
import static play.mvc.Controller.session;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

import views.html.*;
import views.html.account.*;
import views.html.account.signup.*;

import auth.providers.LdapUsernamePasswordAuthProvider;
import auth.providers.LdapUsernamePasswordAuthProvider.LdapLogin;
import auth.providers.LocalUserAuthProviderHelper;
import auth.providers.LocalUsernamePasswordAuthProvider;
import auth.providers.LocalUsernamePasswordAuthProvider.MyLogin;
import auth.providers.LocalUsernamePasswordAuthProvider.MySignup;
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.providers.password.UsernamePasswordAuthProvider;
import com.feth.play.module.pa.user.AuthUser;


import models.sec.User;

public class Application extends Controller {

    public static final String FLASH_MESSAGE_KEY = "message";
    public static final String FLASH_ERROR_KEY = "error";
    public static final String USER_ROLE = "user";

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static User getLocalUser(final Http.Session session) {
        final AuthUser currentAuthUser = PlayAuthenticate.getUser(session);
        final User localUser = User.findByAuthUserIdentity(currentAuthUser);
        return localUser;
    }

    @Restrict(
            @Group(Application.USER_ROLE))
    public static Result restricted() {
        final User localUser = getLocalUser(session());
        return ok(restricted.render(localUser));
    }

    @Restrict(
            @Group(Application.USER_ROLE))
    public static Result profile() {
        final User localUser = getLocalUser(session());
        return ok(profile.render(localUser));
    }

    public static Result login() {
        return LocalUserAuthProviderHelper.login();
    }

    public static Result doLogin() {
        return LocalUserAuthProviderHelper.doLogin();
    }
    
    public static Result ldapLogin() {
        return ok(ldaplogin.render(LdapUsernamePasswordAuthProvider.getLoginForm()));
    }

    public static Result doLdapLogin() {
        com.feth.play.module.pa.controllers.Authenticate.noCache(response());
        final Form<LdapLogin> filledForm = LdapUsernamePasswordAuthProvider.getLoginForm().bindFromRequest();
        if (filledForm.hasErrors()) {
            // User did not fill everything properly
        	flash(Application.FLASH_ERROR_KEY, Messages.get("error.invalidData"));
            return badRequest(ldaplogin.render(filledForm));
        } else {
            // Everything was filled
            return LdapUsernamePasswordAuthProvider.handleLogin(ctx());
        }
    }

    public static Result signup() {
        return LocalUserAuthProviderHelper.signup();
    }

    public static Result doSignup() {
        return LocalUserAuthProviderHelper.doSignup();
    }
    
    public static Result jsRoutes() {
        return ok(
                Routes.javascriptRouter("jsRoutes",
                controllers.sec.routes.javascript.Signup.forgotPassword()))
                .as("text/javascript");
    }

    /**
     * 
     * @param errorMap
     * @param fieldDomain the prefix in messages file for translation of fieldNames. Without a dot at the end. Example: "playauthenticate.signup" 
     * @return
     */
    protected static String toErrorString(java.util.Map<String, java.util.List<ValidationError>> errorMap, String fieldPrefix) {
    	StringBuffer sb = new StringBuffer();
    	String key = null;
    	String msg = null;
    	for (java.util.Iterator<String> it = errorMap.keySet().iterator(); it.hasNext();) {
    		key = it.next();
    		if (sb.length() > 0) {
        		sb.append("\n");
    		}
    		sb.append(Messages.get(fieldPrefix + "." + key));
    		sb.append(": ");
    		for (java.util.Iterator<ValidationError> errIt = errorMap.get(key).iterator(); errIt.hasNext();) {
	    		sb.append(Messages.get(errIt.next().message()));
	    		sb.append("\n");
    		}
    	}
    	return sb.toString();
    }

    public static String formatTimestamp(final long t) {
    	java.util.Locale locale = Lang.preferred(ctx().request().acceptLanguages()).toLocale();
    	DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, locale);
    	DateFormat tf = DateFormat.getTimeInstance(DateFormat.MEDIUM, locale);
        return df.format(new Date(t)) + " " + tf.format(new Date(t));
    }
}
