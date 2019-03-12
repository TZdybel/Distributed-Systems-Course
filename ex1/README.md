# Polecenie

Celem zadania jest napisanie aplikacji w języku Python, która pozwoli użytkownikom na przesyłanie\
(nadawanie i wyświetlanie) informacji bez wykorzystania serwera centralnego poprzez logiczną\
symulację architektury token ring.\
Każdy klient podczas uruchomienia otrzymuje siedem argumentów:

- tekstowy identyfikator użytkownika,
- adres IP i port na którym dany klient nasłuchuje,
- adres IP i port sąsiada, do którego przekazywane będą wiadomości,
- informacja o tym, czy dany użytkownik po uruchomieniu posiada token,
- wybrany protokół: tcp lub udp.

Wiadomości przekazywane są tylko w jedną stronę.\
W sieci znajduje się tylko jeden token i żadna aplikacja nie może nadawać dopóki nie otrzyma\
wolnego tokenu, pierwotnie token jest wolny. Wysłanie wiadomości polega na zajęciu tokenu\
i wpisaniu jej zawartości. Token traktujemy jako kopertę, nośnik wiadomości. Odbiorca po\
przeczytaniu wiadomości zwalnia token (flaga, wyczyszczenie zawartości...) i przekazuje go\
dalej. Dla celów symulacyjnych przyjmujemy, że token jest przetrzymywany przez każdego klienta\
przez około 1 sekundę (po otrzymaniu tokenu wywołujemy np. sleep(1), po tym czasie przesyłamy\
go dalej po ewentualnym dodaniu wiadomości). Dla uproszczenia zakładamy, że żaden klient nie\
będzie "złośliwy" i nie doprowadzi do sytuacji, w której w sieci znajdą się dwa tokeny -\
jednak za implementację mechanizmu, który to wyklucza, zostanie przyznany bonus punktowy.\
Program ma umożliwiać dodawanie nowych użytkowników w trakcie działania systemu oraz\ 
zapewniać dla nich pełną funkcjonalność, a także zabezpieczać przed sytuajcą, w której\
wiadomość krąży w nieskończoność w sieci (należy odpowiednio przemyśleć protokół komunikacyjny)\
Dodatkowo, każdy klient ma przesyłać multicastem informację o otrzymaniu tokenu\
(na dowolny adres grupowy, wspólny dla wszystkich klientów - może być wpisany "na sztywno"\
w kod). Odbiorcami grupy multicastowej są wyłącznie loggery - proste aplikacje zapisujące\
ID nadawcy i timestamp otrzymania tokenu, do pliku. Ilość loggerów może być dowolna\
(co najmniej 2). Logger należy zaimplementować w języku innym niż klientów. 