import 'dart:io' show Platform;
import 'package:flutter/material.dart';
import 'package:key_vault/providers/providers.dart';
import 'package:key_vault/services/services.dart';
import 'package:key_vault/theme/app_theme.dart';
import 'package:key_vault/ui/input_decorations.dart';
import 'package:key_vault/utils/sizes.dart';
import 'package:key_vault/widgets/widgets.dart';
import 'package:provider/provider.dart';

class PasswordsScreen extends StatelessWidget {
  const PasswordsScreen({Key? key}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    Sizes(context);
    final appbarHeight = Platform.isAndroid
        ? Size.fromHeight(Sizes.scaleVertical * 12)
        : Size.fromHeight(Sizes.scaleVertical * 8);
    final toolbarHeight =
        Platform.isAndroid ? Sizes.scaleVertical * 12 : Sizes.scaleVertical * 8;
    return Scaffold(
      backgroundColor: Colors.white,
      floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          Navigator.of(context).pushNamed('add_password_screen');
        },
        backgroundColor: AppTheme.primary,
        child: const Icon(Icons.add),
      ),
      appBar: PreferredSize(
          preferredSize: appbarHeight,
          child: AppBar(
              title: const Text("Contraseñas"),
              backgroundColor: Colors.white,
              iconTheme: const IconThemeData(
                color: Colors.black, //change your color here
              ),
              centerTitle: true,
              elevation: 0,
              toolbarHeight: toolbarHeight,
              titleTextStyle: const TextStyle(
                  color: Colors.black,
                  fontSize: 25,
                  fontWeight: FontWeight.bold))),
      body: ChangeNotifierProvider(
        create: (_) => PasswordListProvider(),
        child: Builder(builder: (context) {
          final passwordsService =
              Provider.of<PasswordService>(context).passwords;
          final passwordsList = Provider.of<PasswordListProvider>(context);
          passwordsList.setPasswords(passwordsService);
          final pws = passwordsList.passwords;

          return Column(
            children: [
              const _SearchBar(),
              Expanded(
                child: SingleChildScrollView(
                  child: PasswordsList(
                    isEmpty: passwordsList.passwords.isEmpty,
                    passwords: pws,
                  ),
                ),
              ),
            ],
          );
        }),
      ),
    );
  }
}

class _SearchBar extends StatelessWidget {
  const _SearchBar({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.symmetric(horizontal: Sizes.scaleHorizontal * 5),
      child: TextFormField(
        onChanged: (value) {
          Provider.of<PasswordListProvider>(context, listen: false).search =
              value;
        },
        style: const TextStyle(
            color: Colors.black, fontWeight: FontWeight.bold, fontSize: 18),
        decoration: InputDecorations.searchDecoration(),
      ),
    );
  }
}
