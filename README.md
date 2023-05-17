!!! Uwaga !!!

Po zainstalowaniu i pierwszym otworzeniu aplikacji należy włączyć ustawienia, następnie można wrócić do poprzedniego ekranu.

trzy kropki w prawym górnym rogu >> Ustawienia

!!!!!!!!!!!!!!


# Elevator-Simulator

Autor: Mikołaj Guz,                               Kurozwęki, 15.05.2023
========================================================================

Spis:
    * Opis
    * Wymagania
    * Instalacja
    * Jak obsługiwać interface

------------------------------------------------------------------------
Program zostal napisany i skompilowany w Android Studio 2022.2.1 Patch 1
------------------------------------------------------------------------

* Opis:
=========

Interface ElevatorSystem
    void pickup(int floor, int direction);
    void selectFloor(int elevatorId, int floor);
    void update(int elevatorId, int currentFloor, int destinationFloor);
    void step();
    ArrayList<Integer[]> status();

W odróżnieniu od przykładowego interfacu, mój dodatkowo ma metode selectFloor(...); oraz status zwraca stan windy reprezentowany przez pięć liczb całkowitych.

///////////////////////////////////////////////////////////////////////////////////////////

>>> void pickup(int floor, int direction);
Służy do zamawianie windy z poziomu na przyład korytarza. Podajemy numer piętra, z którego dzwonimy oraz kierunek, w którym chcemy jechać: 1 - w góre; -1 - w dół.
   
>>> void selectFloor(int elevatorId, int floor);
Służy do wybierania piętra gdy znajdujemy się już w windzie. Podajemu id windy, w której jesteśmy oraz piętro, na które chcemy się dostać.

>>> void update(int elevatorId, int currentFloor, int destinationFloor);
Służy do przeniesienie windy. Podajemy id windy, które chcemy przenieść, piętro na które ma być przeniesione, oraz piętro, do którego ma jechać po przeniesieniu.

>>> void step();
Wykonuje krok symulacji, zmienia piętro, otwiera, czeka na pasażerów lub zamyka drzwi.

>>> ArrayList<Integer[]> status();
Zwraca dane o wszystkich windach. Id windy, aktualne piętro, na którym się znajduje, najbliższy przystanek, do którego zmierza, ilość wszytkich przystanków jakie aktualnie ma zaplanowane, oraz informacje o drzwiach: 1 - otwarte, 0 - zamknięte.
Dodatkowe parametry dodano, aby łatwiej analizować zachowanie wind oraz, żeby móć wykorzystać je przy graficznej reprezentacji wind.

\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

Cała logika odpowiedzialna za sterwowanie windami napisana jest w osobnym module, dzięki czemu łatwo może być wykorzystywana przy innych projektach czy przenoszona między różnymi platformami.

Moduł komunikuje się z aplikacją poprzez dwa interface'y:
ElevatorSystem - dzięki, któremu aplikacja steruje systemem
SettingSystem - dzięki, któremu moduł wind może pobierać ustawienia aplikacji

Pierwszą, która wszystkim zarządza jest fabryka systemu windy, podajemy jej naszą implementacje ustawień, ona komuniku się z systemem wind poprzez interface InitializationByFactory, który finalnie zwróci do fabryki ElevatorSystem, a ona zwróci go do aplikacji.

Następnie ElevatorSystem komunikuje się z windami poprzez interface Elevator. Implementacja systemu wind po otrzymaniu zamówienia na winde, pyta się każdej z wind ile czasu zajmie jej dotarcie, następnie ze wzoru 3 * długość_całej_trasy + długość_trasy_do_zamówionego_piętra, wybiera najkrótszą z nich, jeśli winda jedzie w tym samym kierunku co zadeklarowała osoba zamawiająca przystanek jest dodawany od razu do kolejki, jeśli zamówienie zadeklarowano w przeciwnym, winda jest rezerwowana, i nie dostanie nowych przystanków, aż do zakończenia przejazdu w jednym kierunku.

//////////////////////////////////////////////////////////////////////////////////////////

Warte uwagi:
• Projekt wykorzystuje całą powierzchnie ekranu, również pod paskami systemowymi, które ustawia na przezroczyste.
• Logika aktywności przeniesiona jest do ViewModel, dzięki czemu dane nie muszą być każdorazowo ładowane po każdej zmianie konfiguracji ekranu (obrót ekranu, dzielony ekran).
• AnimatedVectorDrawable animowane ikony svg zapewniające płynne przejścia oraz żywy interface użytkownika.


* Wymagania:
===============

Android >= 5.0, Lolipop, API 21

Zalecany, najlepsze doznania wizualne:
Android >= 8.0, Oreo, API 27



* Instalacja:
===============

Aplikacja jest dostępna w Google Play pod adresem:
https://play.google.com/store/apps/details?id=pl.guzooo.elevatorsimulator

Można również ręcznie zainstalować plik apk z repozytorium github:
https://github.com/Guzooo/Elevator-Simulator/blob/main/app/release/app-release.apk


Po włączeniu aplikacji na górze ekranu pod paskiem akcji powinnien widnieć numer wersji v0.1.7.1



* Jak obsługiwać interface:
=============================

>>> void pickup(int floor, int direction);
        Po otwarciu aplikacji pod przyciskami "select floor" oraz "update" znajduje się lista, są na niej uporządkowane piętra w kolejności malejącym. Przy każdym piętrze są dwie strzałki, jedna skierowana w góre, następna w dół. 
        Strzałka w góre wywołuje <<< pickup(floor_przycisku, 1) >>>.
        Strzałka w dół wywołuje <<< pickup(floor_przycisku, -1) >>>.


>>> void selectFloor(int elevatorId, int floor);
        Po otwarciu aplikacji na głównym ekranie widać przycisk "select floor" a nad nim pole tekstowe. Należy wpisać dwie wartości liczbowe po ";" i wcisnąć przycisk "select floor".
        Aby poznać ID windy, należy wcisnąć zielony przycisk z ikoną "DEV", znajduje się w prawym górym rogu widoku, zaraz pod paskiem akcji.
        "307;5" <<< selectFloor(307, 5) >>>.
        "311;30" <<< selectFloor(311, 30) >>>.


>>> void update(int elevatorId, int currentFloor, int destinationFloor);
        Po otwarciu aplikacji na głównym ekranie widać przycisk "update" a nad nim pole tekstowe. Należy wpisać trzy wartości liczbowe po ";" i wcisnąć przycisk "update".
        Aby poznać ID windy, należy wcisnąć zielony przycisk z ikoną "DEV", znajduje się w prawym górym rogu widoku, zaraz pod paskiem akcji.
        "307;5;10" <<< update(307, 5, 10) >>>.
        "311;30;30" <<< update(311, 30, 30) >>>.

>>> void step();
        Po otwarciu aplikacji na pasku akcji, w prawym górnym rogu, obok trzech kropek rozwijanego menu, widać ikone trójkąta skierowanego w prawo i pionowej kreski ( >| ).
        Po wciśnięciu wykonmuje się <<< step() >>>.

>>> ArrayList<Integer[]> status();
        Po otwarciu aplikacji należy wcisnąć zielony przycisk z ikoną "DEV", znajduje się w prawym górym rogu widoku, zaraz pod paskiem akcji, który przełącza widok graficzny z widokiem deweloperskim.
        Pojedyncza tablica jest reprezentowana jako jeden wiersz
