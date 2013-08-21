package auth.providers;

import play.Application;
import play.data.Form;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.i18n.Messages;
import play.mvc.Http.Response;
import play.mvc.Result;
import play.mvc.Http.Context;

import auth.providers.EmailPasswordAuthProvider;
import auth.providers.LocalUsernamePasswordAuthProvider;
import auth.providers.LocalUsernamePasswordAuthProvider.MySignup;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.exceptions.AuthException;
import com.feth.play.module.pa.providers.AuthProvider;
import com.feth.play.module.pa.providers.password.UsernamePasswordAuthProvider;
import com.feth.play.module.pa.providers.password.UsernamePasswordAuthProvider.UsernamePassword;
import com.feth.play.module.pa.providers.password.UsernamePasswordAuthUser;

import views.html.account.*;
import views.html.account.signup.*;

/**
 * This helper class can be used by controller.Application to do the signup and login of a local password based user, no matter if 
 * the plugin <code>EmailPasswordAuthProvider</code> or <code>LocalUsernamePasswordAuthProvider</code> is used. 
 * Simply insert the provider plugin you want into file <code>conf/play.plugins</code> like: 
 * <code>10040:auth.providers.LocalUsernamePasswordAuthProvider</code> or <code>10040:auth.providers.EmailPasswordAuthProvider</code>. 
 * You must not insert both plugins because they use the same provider key: "password".
 * <br/><br/>
 * The code in controller.Application can look like this:
 * <code>
 * public static Result login() {
 *      return LocalUserAuthProviderHelper.login();
 * }
 *
 * public static Result doLogin() {
 *      return LocalUserAuthProviderHelper.doLogin();
 * }
 *
 * public static Result signup() {
 *     return LocalUserAuthProviderHelper.signup();
 * }
 *
 * public static Result doSignup() {
 *     return LocalUserAuthProviderHelper.doSignup();
 * }
 * </code>
 * 
 * @author oma
 *
 */
public class LocalUserAuthProviderHelper {

	public static final String PROVIDER_KEY = "password"; // ** must be the same value as com.feth.play.module.pa.providers.password.UsernamePasswordAuthProvider.PROVIDER_KEY **
		
    public static Result login() {
    	AuthProvider p = getProviderInstance();
    	if (p != null) {
	        if (p instanceof LocalUsernamePasswordAuthProvider) {
	        	return controllers.Application.ok(login.render(LocalUsernamePasswordAuthProvider.LOGIN_FORM));
	        } else if (p instanceof EmailPasswordAuthProvider) {
	        	return controllers.Application.ok(emaillogin.render(EmailPasswordAuthProvider.LOGIN_FORM));
	        }
    	}
        return controllers.Application.badRequest();
    }

    public static Result doLogin() {
        com.feth.play.module.pa.controllers.Authenticate.noCache(controllers.Application.response());
        AuthProvider p = getProviderInstance();
        if (p != null) {
	        if (p instanceof LocalUsernamePasswordAuthProvider) {
		        final Form<LocalUsernamePasswordAuthProvider.MyLogin> filledForm = LocalUsernamePasswordAuthProvider.LOGIN_FORM
		                .bindFromRequest();
		        if (filledForm.hasErrors()) {
		            // User did not fill everything properly
		        	controllers.Application.flash(controllers.Application.FLASH_ERROR_KEY, Messages.get("error.invalidData"));
		            return controllers.Application.badRequest(login.render(filledForm));
		        } else {
		            // Everything was filled
		            return LocalUsernamePasswordAuthProvider.handleLogin(controllers.Application.ctx());
		        }
	        } else if (p instanceof EmailPasswordAuthProvider) {
		        final Form<EmailPasswordAuthProvider.MyLogin> filledForm = EmailPasswordAuthProvider.LOGIN_FORM
		                .bindFromRequest();
		        if (filledForm.hasErrors()) {
		            // User did not fill everything properly
		        	controllers.Application.flash(controllers.Application.FLASH_ERROR_KEY, Messages.get("error.invalidData"));
		            return controllers.Application.badRequest(emaillogin.render(filledForm));
		        } else {
		            // Everything was filled
		            return EmailPasswordAuthProvider.handleLogin(controllers.Application.ctx());
		        }
	        }
        }
        return controllers.Application.badRequest();
    }
    
    public static Result signup() {
    	AuthProvider p = getProviderInstance();
    	if (p != null) {
	        if (p instanceof LocalUsernamePasswordAuthProvider) {
	        	return controllers.Application.ok(signup.render(LocalUsernamePasswordAuthProvider.SIGNUP_FORM));
	        } else if (p instanceof EmailPasswordAuthProvider) {
	        	return controllers.Application.ok(emailsignup.render(EmailPasswordAuthProvider.SIGNUP_FORM));
	        }
    	}
        return controllers.Application.badRequest();
    }

    public static Result doSignup() {
        com.feth.play.module.pa.controllers.Authenticate.noCache(controllers.Application.response());
        AuthProvider p = getProviderInstance();
        if (p != null) {
	        if (p instanceof LocalUsernamePasswordAuthProvider) {
		        final Form<LocalUsernamePasswordAuthProvider.MySignup> filledForm = LocalUsernamePasswordAuthProvider.SIGNUP_FORM
		                .bindFromRequest();
		        if (filledForm.hasErrors()) {
		            // User did not fill everything properly
		        	controllers.Application.flash(controllers.Application.FLASH_ERROR_KEY, Messages.get("error.invalidData"));
		            return controllers.Application.badRequest(signup.render(filledForm));
		        } else {
		            // Everything was filled
		            return LocalUsernamePasswordAuthProvider.handleSignup(controllers.Application.ctx());
		        }
	        } else if (p instanceof EmailPasswordAuthProvider) {
		        final Form<EmailPasswordAuthProvider.MySignup> filledForm = EmailPasswordAuthProvider.SIGNUP_FORM
		                .bindFromRequest();
		        if (filledForm.hasErrors()) {
		            // User did not fill everything properly
		        	controllers.Application.flash(controllers.Application.FLASH_ERROR_KEY, Messages.get("error.invalidData"));
		            return controllers.Application.badRequest(emailsignup.render(filledForm));
		        } else {
		            // Everything was filled
		            return EmailPasswordAuthProvider.handleSignup(controllers.Application.ctx());
		        }
	        }
        }
        return controllers.Application.badRequest();
    }

	protected static UsernamePasswordAuthProvider<Object, UsernamePasswordAuthUser, UsernamePasswordAuthUser, UsernamePassword, UsernamePassword> getProviderInstance() {
		AuthProvider p = PlayAuthenticate.getProvider(PROVIDER_KEY);
		if (p instanceof UsernamePasswordAuthProvider) {
			return ((UsernamePasswordAuthProvider<Object, UsernamePasswordAuthUser, UsernamePasswordAuthUser, UsernamePassword, UsernamePassword>)p);
		} else {
			return null;
		}
	}


}
