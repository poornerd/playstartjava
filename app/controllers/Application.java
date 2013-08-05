package controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import auth.providers.MyUsernamePasswordAuthProvider;
import auth.providers.MyUsernamePasswordAuthProvider.MyLogin;
import auth.providers.MyUsernamePasswordAuthProvider.MySignup;
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
        return ok(login.render(MyUsernamePasswordAuthProvider.LOGIN_FORM));
    }

    public static Result doLogin() {
        com.feth.play.module.pa.controllers.Authenticate.noCache(response());
        final Form<MyLogin> filledForm = MyUsernamePasswordAuthProvider.LOGIN_FORM
                .bindFromRequest();
        if (filledForm.hasErrors()) {
            // User did not fill everything properly
        	flash(Application.FLASH_ERROR_KEY, toErrorString(filledForm.errors()));
            return badRequest(login.render(filledForm));
        } else {
            // Everything was filled
            return UsernamePasswordAuthProvider.handleLogin(ctx());
        }
    }

    public static Result signup() {
        return ok(signup.render(MyUsernamePasswordAuthProvider.SIGNUP_FORM));
    }

    public static Result jsRoutes() {
        return ok(
                Routes.javascriptRouter("jsRoutes",
                controllers.sec.routes.javascript.Signup.forgotPassword()))
                .as("text/javascript");
    }

    public static Result doSignup() {
        com.feth.play.module.pa.controllers.Authenticate.noCache(response());
        final Form<MySignup> filledForm = MyUsernamePasswordAuthProvider.SIGNUP_FORM
                .bindFromRequest();
        if (filledForm.hasErrors()) {
            // User did not fill everything properly
        	flash(Application.FLASH_ERROR_KEY, toErrorString(filledForm.errors()));
            return badRequest(signup.render(filledForm));
        } else {
            // Everything was filled
            // do something with your part of the form before handling the user
            // signup
            return UsernamePasswordAuthProvider.handleSignup(ctx());
        }
    }
    
    protected static String toErrorString(java.util.Map<String, java.util.List<ValidationError>> errorMap) {
    	StringBuffer sb = new StringBuffer();
    	String key = null;
    	String msg = null;
    	for (java.util.Iterator<String> it = errorMap.keySet().iterator(); it.hasNext();) {
    		key = it.next();
    		if (sb.length() > 0) {
        		sb.append("\n");
    		}
    		sb.append(key);
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
