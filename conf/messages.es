# Override default Play's validation messages

# --- Constraints
constraint.required=Obligatorio
constraint.min=Valor m�nimo: {0}
constraint.max=Valor m�ximo: {0}
constraint.minLength=Longitud m�nima: {0}
constraint.maxLength=Longitud m�xima: {0}
constraint.email=Email

# --- Formats
format.date=Date (''{0}'')
format.numeric=Num�rico
format.real=Real

# --- Errors
error.invalid=Valor incorrecto
error.required=Este campo es obligatorio
error.number=Se esperaba un valor num�rico
error.real=Se esperaba un numero real
error.min=Debe ser mayor o igual que {0}
error.max=Debe ser menor o igual que {0}
error.minLength=La longitud m�nima es de {0}
error.maxLength=La longitud m�xima es de {0}
error.email=Se requiere un email v�lido
error.pattern=Debe satisfacer {0}

### --- play-authenticate START

# play-authenticate: Initial translations

playauthenticate.accounts.link.success=Cuenta enlazada correctamente
playauthenticate.accounts.merge.success=Cuentas unificadas correctamente

playauthenticate.verify_email.error.already_validated=Su email ya ha sido validado
playauthenticate.verify_email.error.set_email_first=Primero debe dar de alta un email.
playauthenticate.verify_email.message.instructions_sent=Las instrucciones para validar su cuenta han sido enviadas a {0}.
playauthenticate.verify_email.success=La direcci�n de email ({0}) ha sido verificada correctamente.

playauthenticate.reset_password.message.instructions_sent=Las instrucciones para restablecer su contrase�a han sido enviadas a {0}.
playauthenticate.reset_password.message.email_not_verified=Su cuenta a�n no ha sido validada. Se ha enviado un email incluyedo instrucciones para su validaci�n. Intente restablecer la contrase�a una vez lo haya recibido.
playauthenticate.reset_password.message.no_password_account=Su usuario todav�a no ha sido configurado para utilizar contrase�a.
playauthenticate.reset_password.message.success.auto_login=Su contrase�a ha sido restablecida.
playauthenticate.reset_password.message.success.manual_login=Su contrase�a ha sido restablecida. Intente volver a entrar utilizando su nueva contrase�a.

playauthenticate.change_password.error.passwords_not_same=Las contrase�as no coinciden.
playauthenticate.change_password.success=La contrase�a ha sido cambiada correctamente.

playauthenticate.password.signup.error.passwords_not_same=Las contrase�as no coinciden.
playauthenticate.password.login.unknown_user_or_pw=Usuario o contrase�a incorrectos.

playauthenticate.password.verify_signup.subject=PlayAuthenticate: Complete su registro
playauthenticate.password.verify_email.subject=PlayAuthenticate: Confirme su direcci�n de email
playauthenticate.password.reset_email.subject=PlayAuthenticate: C�mo restablecer su contrase�a

# play-authenticate: Additional translations

playauthenticate.login.email.placeholder=Su direcci�n de email
playauthenticate.login.password.placeholder=Elija una contrase�a
playauthenticate.login.password.repeat=Repita la contrase�a elegida
playauthenticate.login.title=Entrar
playauthenticate.login.password.placeholder=Contrase�a
playauthenticate.login.now=Entrar
playauthenticate.login.forgot.password=�Olvid� su contrase�a?
playauthenticate.login.oauth=entre usando su cuenta con alguno de los siguientes proveedores:

playauthenticate.signup.title=Registrarse
playauthenticate.signup.name=Su nombre
playauthenticate.signup.now=Reg�strese
playauthenticate.signup.oauth=reg�strese usando su cuenta con alguno de los siguientes proveedores:

playauthenticate.verify.account.title=Es necesario validar su email
playauthenticate.verify.account.before=Antes de configurar una contrase�a
playauthenticate.verify.account.first=valide su email

playauthenticate.change.password.title=Cambio de contrase�a
playauthenticate.change.password.cta=Cambiar mi contrase�a

playauthenticate.merge.accounts.title=Unir cuentas
playauthenticate.merge.accounts.question=�Desea unir su cuenta ({0}) con su otra cuenta: {1}?
playauthenticate.merge.accounts.true=S�, �une estas dos cuentas!
playauthenticate.merge.accounts.false=No, quiero abandonar esta sesi�n y entrar como otro usuario.
playauthenticate.merge.accounts.ok=OK

playauthenticate.link.account.title=Enlazar cuenta
playauthenticate.link.account.question=�Enlazar ({0}) con su usuario?
playauthenticate.link.account.true=S�, �enlaza esta cuenta!
playauthenticate.link.account.false=No, salir y crear un nuevo usuario con esta cuenta
playauthenticate.link.account.ok=OK

# play-authenticate: Signup folder translations

playauthenticate.verify.email.title=Verifique su email
playauthenticate.verify.email.requirement=Antes de usar PlayAuthenticate, debe validar su email.
playauthenticate.verify.email.cta=Se le ha enviado un email a la direcci�n registrada. Por favor, siga el link de este email para activar su cuenta.
playauthenticate.password.reset.title=Restablecer contrase�a
playauthenticate.password.reset.cta=Restablecer mi contrase�a

playauthenticate.password.forgot.title=Contrase�a olvidada
playauthenticate.password.forgot.cta=Enviar instrucciones para restablecer la contrase�a

playauthenticate.oauth.access.denied.title=Acceso denegado por OAuth
playauthenticate.oauth.access.denied.explanation=Si quiere usar PlayAuthenticate con OAuth, debe aceptar la conexi�n.
playauthenticate.oauth.access.denied.alternative=Si prefiere no hacerlo, puede tambi�n
playauthenticate.oauth.access.denied.alternative.cta=registrarse con un usuario y una contrase�a.

playauthenticate.token.error.title=Error de token
playauthenticate.token.error.message=El token ha caducado o no existe.

playauthenticate.user.exists.title=El usuario existe
playauthenticate.user.exists.message=Otro usario ya est� dado de alta con este identificador.

# play-authenticate: Navigation
playauthenticate.navigation.profile=Perfil
playauthenticate.navigation.link_more=Enlazar m�s proveedores
playauthenticate.navigation.logout=Salir
playauthenticate.navigation.login=Entrar
playauthenticate.navigation.home=Inicio
playauthenticate.navigation.restricted=P�gina restringida
playauthenticate.navigation.signup=D�rse de alta

# play-authenticate: Handler
playauthenticate.handler.loginfirst=Para ver ''{0}'', debe darse primero de alta.

# play-authenticate: Profile
playauthenticate.profile.title=Perfil de usuario
playauthenticate.profile.mail=Su nombre es {0} y su direcci�n de mail es {1}!
playauthenticate.profile.unverified=sin validar - haga click para validar
playauthenticate.profile.verified=validada
playauthenticate.profile.providers_many=Hay {0} proveedores enlazados con su cuenta:
playauthenticate.profile.providers_one = Hay un proveedor enlazado con su cuenta:
playauthenticate.profile.logged=Ha entrado con:
playauthenticate.profile.session=Su ID de usuario es {0}. Su sesi�n expirar� el {1}.
playauthenticate.profile.session_endless=Su ID de usuario es {0}. Su sesi�n no expirar� nunca porque no tiene caducidad.
playauthenticate.profile.password_change=Cambie/establezca una contrase�a para su cuenta

# play-authenticate - sample: Index page
playauthenticate.index.title=Bienvenido Play Authenticate
playauthenticate.index.intro=Aplicaci�n de ejemplo de Play Authenticate
playauthenticate.index.intro_2=Esto es una plantilla para una sencilla aplicaci�n con autentificaci�n y autorizaci�n
playauthenticate.index.intro_3=Mire la barra de navegaci�n superior para ver ejemplos sencillos incluyendo las caracter�sticas soportadas de autentificaci�n.
playauthenticate.index.heading=Cabecera
playauthenticate.index.details=Ver detalles

# play-authenticate - sample: Restricted page
playauthenticate.restricted.secrets=�Secretos y m�s secretos!

### --- play-authenticate END