# Override default Play's validation messages

# --- Constraints
constraint.required=Wymagane
constraint.min=Minimalna warto??: {0}
constraint.max=Maksymalna warto??: {0}
constraint.minLength=Minimalna d?ugo??: {0}
constraint.maxLength=Maksymalna d?ugo??: {0}
constraint.email=Email

# --- Formats
format.date=Data (''{0}'')
format.numeric=Numeryczny
format.real=Real

# --- Errors
error.invalid=Niepoprawna warto??
error.required=To pole jest wymagane
error.number=Wymagana warto?? numeryczna
error.real=Real number value expected
error.min=Musi by? wi?ksze lub równe ni? {0}
error.max=Musi by? mniejsze lub równe ni? {0}
error.minLength=Minimalna d?ugo?? {0}
error.maxLength=Maksymalna d?ugo?? {0}
error.email=Wymagany poprawny adres e-mail
error.pattern=Must satisfy {0}

### --- play-authenticate START

# play-authenticate: Initial translations

playauthenticate.accounts.link.success=Konto zosta? pod??czone
playauthenticate.accounts.merge.success=Konta zosta?y z??czone

playauthenticate.verify_email.error.already_validated=Twój adres zosta? ju? zweryfikowany.
playauthenticate.verify_email.error.set_email_first=Najpierw musisz poda? adres e-mail.
playauthenticate.verify_email.message.instructions_sent=Instrukcje dotycz?ce weryfikacji adresu zosta?y wys?ane na adres {0}.
playauthenticate.verify_email.success=Adres e-mail ({0}) zosta? poprawnie zweryfikowany.

playauthenticate.reset_password.message.instructions_sent=Instrukcje dotycz?ce przywracania has?a zosta?y wys?ane na adres {0}.
playauthenticate.reset_password.message.email_not_verified=Twoje konto nie zosta?o jeszcze zweryfikowane. Na wskazany adres zosta?y wys?ane instrukcje dotycz?ce weryfikacji. Dopiero po weryfikacji spróbuj przywróci? has?o w razie potrzeby.
playauthenticate.reset_password.message.no_password_account=Dla tego konta nie ustawiono jeszcze mo?liwo?ci logowania za pomoc? has?a.
playauthenticate.reset_password.message.success.auto_login=Twoje has?o zosta?o przywrócone.
playauthenticate.reset_password.message.success.manual_login=Twoje has?o zosta?o przywrócone. Zaloguj si? ponownie z u?yciem nowego has?a.

playauthenticate.change_password.error.passwords_not_same=Has?a nie s? takie same.
playauthenticate.change_password.success=Has?o zosta?o zmienione.

playauthenticate.password.signup.error.passwords_not_same=Has?a nie s? takie same.
playauthenticate.password.login.unknown_user_or_pw=Nieznany u?ytkownik lub z?e has?o.

playauthenticate.password.verify_signup.subject=PlayAuthenticate: Zako?cz rejestracj?
playauthenticate.password.verify_email.subject=PlayAuthenticate: Potwierd? adres e-mail
playauthenticate.password.reset_email.subject=PlayAuthenticate: Jak ustali? nowe has?o

# play-authenticate: Additional translations

playauthenticate.login.email.placeholder=Twój adres e-mail
playauthenticate.login.password.placeholder=Podaj has?o
playauthenticate.login.password.repeat=Powtórz has?o
playauthenticate.login.title=Logowanie
playauthenticate.login.password.placeholder=Has?o
playauthenticate.login.now=Zaloguj si?
playauthenticate.login.forgot.password=Nie pami?tasz has?a?
playauthenticate.login.oauth=lub zaloguj si? z innym dostawc?:

playauthenticate.signup.title=Rejestracja
playauthenticate.signup.name=Imi? i nazwisko
playauthenticate.signup.now=Zarejestruj si?
playauthenticate.signup.oauth=lub zarejestruj si? z innym dostawc?:

playauthenticate.verify.account.title=Wymagana weryfikacja adresu e-mail
playauthenticate.verify.account.before=Zanim ustawisz nowe has?o
playauthenticate.verify.account.first=musisz zweryfikowa? swój adres e-mail.

playauthenticate.change.password.title=Zmie? has?o
playauthenticate.change.password.cta=Zmie? moje has?o

playauthenticate.merge.accounts.title=Z??cz konta
playauthenticate.merge.accounts.question=Czy chcesz po??czy? aktualne konto ({0}) z kontem: {1}?
playauthenticate.merge.accounts.true=Tak, po??cz oba konta
playauthenticate.merge.accounts.false=Nie, opu?? bie??c? sesj? i zaloguj si? jako nowy u?ytkownik
playauthenticate.merge.accounts.ok=OK

playauthenticate.link.account.title=Do??cz konto
playauthenticate.link.account.question=Czy chcesz do??czy? konto ({0}) do swojego aktualnego konta u?ytkownika?
playauthenticate.link.account.true=Tak, do??cz to konto
playauthenticate.link.account.false=Nie, wyloguj mnie i utwórz nowe konto u?ytkownika
playauthenticate.link.account.ok=OK

# play-authenticate: Signup folder translations

playauthenticate.verify.email.title=Potwierd? adres e-mail
playauthenticate.verify.email.requirement=Musisz potwierdzi? swój adres e-mail, aby korzyta? z PlayAuthenticate
playauthenticate.verify.email.cta=Na wskazany adres zosta?a przes?ana informacja. Skorzystaj z do??czonego do niej linku aby aktywowa? konto.

playauthenticate.password.reset.title=Przywró? has?o
playauthenticate.password.reset.cta=Przywró? moje has?o

playauthenticate.password.forgot.title=Nie pami?tam has?a
playauthenticate.password.forgot.cta=Prze?lij instrukcj? dot. przywracania has?a

playauthenticate.oauth.access.denied.title=Dost?p OAuth zabroniony
playauthenticate.oauth.access.denied.explanation=Je?li chcesz u?ywa? PlayAuthenticate za pomoc? OAuth, musisz zaakceptowa? po??czenie.
playauthenticate.oauth.access.denied.alternative=Je?li wolisz tego nie robi? mo?esz równie?
playauthenticate.oauth.access.denied.alternative.cta=zarejestrowa? si? podaj?c nazw? u?ytkownika i has?o

playauthenticate.token.error.title=B??d tokena
playauthenticate.token.error.message=Podany token straci? wa?no?? lub nie istnieje.

playauthenticate.user.exists.title=U?ytkownik istnieje
playauthenticate.user.exists.message=Ten u?ytkownik ju? istnieje.

# play-authenticate: Navigation
playauthenticate.navigation.profile=Profil
playauthenticate.navigation.link_more=Do??cz wi?cej dostawców
playauthenticate.navigation.logout=Wyloguj si?
playauthenticate.navigation.login=Zaloguj si?
playauthenticate.navigation.home=Strona g?ówna
playauthenticate.navigation.restricted=Strona zastrze?ona
playauthenticate.navigation.signup=Zarejestruj si?

# play-authenticate: Handler
playauthenticate.handler.loginfirst=Musisz si? zalogowa?, aby uzyska? dost?p do strony ''{0}''

# play-authenticate: Profile
playauthenticate.profile.title=Profil u?ytkownika
playauthenticate.profile.mail=Nazywasz si? {0} a twój e-mail to {1}!
playauthenticate.profile.unverified=Niezweryfikowany - kliknij
playauthenticate.profile.verified=zweryfikowany
playauthenticate.profile.providers_many=Dostawcy pod??czeni do Twojego konta ({0}):
playauthenticate.profile.providers_one = Jedyny dostawca pod??czony do Twojego konta:
playauthenticate.profile.logged=Do obecnego zalogowania u?yto:
playauthenticate.profile.session=ID tego konta to {0} a jego sesja wyga?nie {1}
playauthenticate.profile.session_endless=ID tego konta to {0}, jego sesja nie wygasa automatycznie
playauthenticate.profile.password_change=Zmie? lub ustaw has?o dla tego konta

# play-authenticate - przyk?ad: Index page
playauthenticate.index.title=Witaj w Play Authenticate
playauthenticate.index.intro=Przyk?ad Play Authenticate
playauthenticate.index.intro_2=Oto szablon prostej aplikacji wykorzystuj?cej Play Authenticate.
playauthenticate.index.intro_3=Skorzystaj z powy?szej nawigacji aby przetestowa? dzia?anie autentykacji.
playauthenticate.index.heading=Nag?ówek
playauthenticate.index.details=Szczegó?y

# play-authenticate - przyk?ad: Restricted page
playauthenticate.restricted.secrets=Tajemnice, tajemnice, tajemnice... Wsz?dzie tajemnice!

### --- play-authenticate END