<h1 align="center">
    <b>
        <br>
        # Tugas Besar 1 IF3210 Pengembangan Aplikasi Platform Khusus
        <br>
    </b>
</h1>

<h2 align="center">
    <b>
        Android - BolaSepak
        <br>
        <br>
    </b>
</h2>

## Deskripsi Singkat
Aplikasi BolaSepak merupakan aplikasi android yang dapat menampilkan list hasil pertandingan sepak bola liga premier inggris. Aplikasi BolaSepak juga dapat melakukan pencarian pertandingan sepak bola liga premier inggris sebelumnya.

Beberapa dependencies tambahan yang dibutuhkan oleh aplikasi BolaSepak:
1. picasso - An image downloading and caching library for Android <br>
2. gson - A Java serialization/deserialization library to convert Java Objects into JSON and back <br>
3. okhttp - A type-safe HTTP client for Android and Java. It allows us to do multiple HTTP requests to be multiplexed over one socket connection<br>
4. SQLiteOpenHelper - A helper class to manage database creation if it's not exist, opening database if it's exist, and upgrading when necessary<br>

API:
1. theSportDB - Sport API<br>
2. OpenWeatherMap - Weather API<br>

## Screenshots

### Homescreen

![](screenshots/homescreen1.png)
<br>
<br>
![](screenshots/homescreen2.png)
<br>
<br>

### Search

![](screenshots/search1.png)
<br>
<br>
![](screenshots/search2.png)
<br>
<br>

### Match Detail

![](screenshots/matchDetail1.png)
<br>
<br>
![](screenshots/matchDetail2.png)
<br>
<br>
![](screenshots/matchDetail3.png)
<br>
<br>

### Team Detail

![](screenshots/teamDetail1.png)
<br>
<br>
![](screenshots/teamDetail2.png)
<br>
<br>
![](screenshots/teamDetail3.png)
<br>
<br>
![](screenshots/teamDetail4.png)
<br>
<br>


## Cara Kerja

### Step Counter
Dengan menggunakan sensor accelerometer yang tersedia pada android, step counter dapat direalisasikan ketika handphone bergerak melebihi kecepatan tertentu pada arah sumbu-z.

### Offline Data
Dengan menggunakan SQLiteOpenHelper kami mencatat response JSON dari API TheSportDB, dan ketika kehilangan koneksi kita mengambil simpanan dari 
database untuk informasi di homepage

<p align="center">
    <b>
        <br>
        <font size="6">
            About
        </font>
    </b>
</p>

<p align="center">
    <b>
        IF3210-Pengembangan Aplikasi Platform Khusus - 2020
        <br>
        Teknik Informatika 2017
        <br>
        <br>
        13517062 - Ardysatrio Fakhri Haroen	
        13517074 - Taufikurrahman Anwar	
        13517137 - Vincent Budianto
    </b>
</p>