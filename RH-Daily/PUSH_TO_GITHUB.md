# Push RH DAILY V3 ke repository GitHub baru

## 1. Buat repository kosong

Di GitHub, buat repository baru, misalnya:

`rh-daily-v3`

Jangan centang README, .gitignore, atau license saat membuat repository.

## 2. Buka folder project

Di VS Code: **File → Open Folder → RH-Daily**.

Lalu buka terminal VS Code dan jalankan:

```bash
git init
git branch -M main
git add .
git commit -m "RH Daily V3 initial release"
git remote add origin https://github.com/USERNAME/rh-daily-v3.git
git push -u origin main
```

Ganti `USERNAME` dengan username GitHub kamu.

## 3. Cek build otomatis

Setelah push:

1. Buka repository GitHub.
2. Masuk ke tab **Actions**.
3. Workflow **Build RH Daily APK** akan berjalan.
4. Setelah selesai, buka run tersebut.
5. Di bagian **Artifacts**, download **RH-Daily-debug**.

## 4. Push perubahan berikutnya

```bash
git add .
git commit -m "Update RH Daily"
git push
```

Setiap push ke `main` akan build APK lagi.
