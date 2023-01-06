import 'package:flutter/material.dart';
import 'package:key_vault/theme/app_theme.dart';
import 'package:key_vault/ui/input_decorations.dart';
import 'package:key_vault/widgets/widgets.dart';

class LoginScreen extends StatelessWidget {
  const LoginScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: SafeArea(
      child: SingleChildScrollView(
        child: Expanded(
          child: Column(
            children: [
              const AuthLogo(),
              const AuthTitle(title: "¡Bienvenido a Key Vault!"),
              const AuthSubtitle(),
              const SizedBox(
                height: 40,
              ),
              const _LoginForm(),
              const SizedBox(
                height: 25,
              ),
              const _ForgotMyPasswordButton(),
              AuthTextAndButton(
                onTap: () {
                  Navigator.of(context).pushReplacementNamed('register');
                },
                text1: '¿No tienes una cuenta?',
                text2: 'Registrate',
              )
            ],
          ),
        ),
      ),
    ));
  }
}

class _ForgotMyPasswordButton extends StatelessWidget {
  const _ForgotMyPasswordButton({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return TextButton(
        onPressed: () {},
        child: const Text(
          "Olvidé mi contraseña",
          style: TextStyle(color: AppTheme.primary),
        ));
  }
}

class _LoginForm extends StatelessWidget {
  const _LoginForm();

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 20),
      child: Form(
          child: Column(
        children: [
          TextFormField(
            keyboardType: TextInputType.emailAddress,
            cursorColor: AppTheme.primary,
            decoration:
                InputDecorations.authInputDecoration(labelText: 'Email'),
          ),
          const SizedBox(
            height: 25,
          ),
          const AuthPasswordInput(),
          const SizedBox(
            height: 25,
          ),
          const SubmitButton(
            title: "Iniciar sesión",
          )
        ],
      )),
    );
  }
}
