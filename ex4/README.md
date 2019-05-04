Aplikacja obsługi kont bankowych
================================

Celem zadania jest stworzenie aplikacji do obsługi kont bankowych o następującej funkcjonalności

- obsługa kont typu Standard i Premium,
- nowe konto jest tworzone na podstawie podstawowych danych (imię, nazwisko, PESEL (stanowiący identyfikator klienta), deklarowany próg miesięcznych wpływów) - na bazie tej ostatniej informacji bank decyduje, czy konto będzie typu Standard czy Premium i powiadamia o tym klienta.
- autoryzacja dostępu do konta bankowego następuje w sytuacji każdorazowego podania poprawnego identyfikatora PESEL oraz klucza (hasła) który jest jednorazowo zwracany klientowi w momencie tworzenia konta (nie ma zatem pojęcia sesji z fazami logowania się i wylogowania z banku),
- użytkownik konta Premium może się starać o uzyskanie kredytu  w podanej przez siebie i  obsługiwanej przez bank walucie, żądanej kwocie i na zadany okres czasu. Bank  przedstawia całkowite koszty udzielenia pożyczki w wyrażone w walucie obcej oraz walucie rodzimej. Koszty powinny być skorelowane z aktualnym rynkowym kursem walut - o czym informuje Bank osobna usługa.
- użytkownik każdego typu konta może uzyskać informacje o jego aktualnym stanie (na potrzeby zadania ta funkcjonalność jest wystarczająca).
W aplikacji można więc wyróżnić trzy elementy: 1. usługa informująca banki o aktualnym kursie walut, 2. bank, 3. klient banku.

Realizacja
----------

Usługa informująca o aktualnym kurcie walut natychmiast po podłączeniu się jej klienta (czyli banku) przesyła kursy walut wszystkich wyspecyfikowanych przez Bank w walucie rodzimej, a później okresowo i niezależnie dla różnych walut informuje o zmianach ich kursów (symulując te zmiany). Różne banki mogą być zainteresowane różnymi walutami - usługa powinna to brać pod uwagę.  Komunikację pomiędzy bankiem a usługą należy zrealizować z wykorzystaniem gRPC i mechanizmu strumieniowania (stream), a nie pollingu. Kurs walut powinien się nieco wahać zmieniając dość często (np. co 5 sekund) by móc zaobserwować działanie usługi w czasie demonstracji zadania. Zbiór obsługiwanych walut jest zamknięty (enum).

Komunikację między klientem banku a bankiem należy zrealizować z wykorzystaniem ICE.

ICE
---

Realizując komunikację w ICE należy zaimplementować konta klientów jako osobne obiekty ICE tworzone przez odpowiednie factory (choć w przypadku tego zadania wielość obiektów nie znajduje uzasadnienia z inżynierskiego punktu widzenia) i rejestrowane w tablicy ASM z nazwą będącą wartością PESEL klienta i kategorią "standard" albo "premium" (para ta pozwala na uzyskanie referencji do obiektu konta). Klucz dostępowy powinien być przesyłany przez klienta jako kontekst wywołania operacji (dodatkowy, pozainterfejsowy argument wywołania „wyjmowany” z __current.ctx po stronie serwanta) by nie „psuć” elegancji interfejsu. Klient musi mieć możliwość korzystania ze swojego konta w dowolnym czasie, także po restarcie aplikacji (czyli przechowywanie w pamięci referencji nowoutworzonego obiektu nie może być jedynym sposobem uzyskania dostępu do konta).

Dodatkowe informacje
--------------------

Aplikacja kliencka powinna mieć postać tekstową i może być minimalistyczna, lecz musi pozwalać na przetestowanie funkcjonalności aplikacji szybko i na różny sposób (musi więc być przynajmniej w części interaktywna). W szczególności powinno być możliwe łatwe przełączanie się pomiędzy kontami użytkownika (bez konieczności restartu aplikacji klienckiej).

Interfejs IDL powinien być prosty, ale zaprojektowany w sposób dojrzały (odpowiednie typy proste, właściwe wykorzystanie typów złożonych), uwzględniając możliwość wystąpienia różnego rodzaju błędów. Tam gdzie to możliwe należy wykorzystać dziedziczenie interfejsów IDL.

Stan usługi bankowej nie musi być persystowany (nie musi przetrwać restartu).

ICE: Proszę pamiętać o operatorze * (proxy) przy zwracaniu referencji do obiektu (https://doc.zeroc.com/ice/3.7/the-slice-language/interfaces-operations-and-exceptions/proxies-for-ice-objects), np. interface Factory { Type* createAccount(...); }; Implementacja tej operacji powinna wyglądać mniej więcej tak: return TypePrxHelper.uncheckedCast(__current.adapter.add(new TypeI(), new Identity(pesel, accountType)));

Do realizacji zadania należy wykorzystać przynajmniej dwa różne języki programowania.

Działanie aplikacji może (nie musi) być demonstrowana na jednej maszynie. Wymagane jest uruchomienie co najmniej dwóch instancji banku. Kod źródłowy zadania powinien być demonstrowany w IDE. Aktywność poszczególnych elementów aplikacji należy odpowiednio logować (wystarczy na konsolę) by móc sprawnie ocenić poprawność jej działania.