import 'package:flutter/material.dart';
import 'package:key_vault/providers/providers.dart';
import 'package:key_vault/screens/screens.dart';
import 'package:key_vault/theme/app_theme.dart';
import 'package:provider/provider.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [ChangeNotifierProvider(create: (_) => BottomNavProvider())],
      child: MaterialApp(
        routes: {
          'login': (context) => const LoginScreen(),
          'main_bottom_nav_screen': (context) => const MainBottomNavScreen(),
        },
        initialRoute: 'main_bottom_nav_screen',
        debugShowCheckedModeBanner: false,
        theme: AppTheme.lighTheme,
      ),
    );
  }
}
