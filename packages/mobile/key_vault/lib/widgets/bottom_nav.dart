import 'package:flutter/material.dart';
import 'package:key_vault/providers/password_form_provider.dart';
import 'package:key_vault/providers/providers.dart';
import 'package:key_vault/theme/app_theme.dart';
import 'package:provider/provider.dart';
import 'package:flutter_barcode_scanner/flutter_barcode_scanner.dart';

class BottomNav extends StatelessWidget {
  const BottomNav({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final bottomNavProvider = Provider.of<BottomNavProvider>(context);
    final currentIndex = bottomNavProvider.selectedMenuOpt;
    void navToNewPassword(url) {
      Navigator.of(context).pushNamed('add_password_screen', arguments: url);
    }

    return BottomNavigationBar(
        backgroundColor: Colors.white,
        showSelectedLabels: false,
        currentIndex: currentIndex,
        showUnselectedLabels: false,
        selectedItemColor: AppTheme.primary,
        onTap: (value) async {
          if (value == 1) {
            //String barcodeScanRes = await FlutterBarcodeScanner.scanBarcode(
            //    '#3D8BEF', 'Cancel', false, ScanMode.QR);
            //if (barcodeScanRes == -1) return;
            String barcodeScanRes =
                "https://github.com/Lucas-Barrionuevo/KeyVault";
            navToNewPassword(barcodeScanRes);
          } else {
            bottomNavProvider.selectedMenuOpt = value;
          }
        },
        iconSize: 28,
        items: const [
          BottomNavigationBarItem(icon: Icon(Icons.home), label: 'Home'),
          BottomNavigationBarItem(icon: Icon(Icons.qr_code), label: 'QR'),
          BottomNavigationBarItem(icon: Icon(Icons.key), label: 'Passwords')
        ]);
  }
}
