# Progetto Sistemi Distribuiti 2022-2023 - API REST

Documentazione delle API REST di esempio. Si assume che i dati vengano scambiati in formato JSON.

## `/movies`



### GET

**Descrizione**: Il metodo restituisce la lista di tutti i film presenti nel database, con i loro dati e i relativi posti all'interno della sala.

**Parametri**: Non sono previsti parametri.

**Body richiesta**: Non è previsto un body.

**Risposta**: In caso di successo della chiamata, viene restituita la rappresentazione in JSON della programmazione settimanale, cioè un array JSON che comprende 7 array JSON, ognuno dei quali
ha all'interno un oggetto JSON per ogni film con i campi `title`, `date` (due stringhe) e `rooms` (un array di oggetti JSON). All'interno di rooms sono compresi tutte le
visioni dello stesso film in base all'orario. In particolare, esso è un oggetto JSON con i campi `title`, `date`, `time` (tre stringhe), `room`, `length`, `available` (tre interi)
e seatsGrid (una matrice di interi) che rappresenta la disposizione dei posti della sala (con il valore 0 se il posto è libero e il valore 1 se il posto è occupato).


**Codici di stato restituiti**:

* 200 OK
* 500 INTERNAL_SERVER_ERROR: C`è stato un errore di comunicazione tra il web server e il database


### `/{dayIndex}/{movieIndex}/{time}`

### GET

**Descrizione**: Il metodo restituisce un film in particolare, con i suoi dati e i relativi posti all`interno della sala.

**Parametri** ci devono essere `dayIndex`, `movieIndex` e `time` per l`identificazione di un film in particolare

**Body Richiesta**: Non è previsto un body.

**Risposta**: In caso di successo della chiamata, viene restituita la rappresentazione in JSON del film cercato, il quale ha al suo interno i campi `title`, `date`, `time` (tre stringhe), `room`, `length`, `available` (tre interi) e seatsGrid (una matrice di interi) che rappresenta la disposizione dei posti della sala (con il valore 0 se il posto è libero e il valore 1 se il posto è occupato).

**Codici di stato restituiti**:

* 200 OK
* 404 NOT_FOUND: il film cercato non è stato trovato
* 500 INTERNAL_SERVER_ERROR: C`è stato un errore di comunicazione tra il web server e il database


### `/{dayIndex}/{movieIndex}/{time}`

### POST

**Descrizione**:  Prenota posti per un film specifico.

**Parametri**: ci deve essere l'header `Content-Type: application/json` e ci devono essere `dayIndex`, `movieIndex` e `time` per l`identificazione di un film in particolare.

**Body richiesta**: rappresentazione in formato JSON della prenotazione che ha al suo interno il campo `seats` che è un array di oggetti che hanno al loro interno riga e colonna
del posto prenotato e `code`(una stringa).

**Risposta**: in caso di successo il body è vuoto e la risorsa creata (inizializzata con i dati recuperati dal film relativo) è stata aggiunta al database

**Codici di stato restituiti**:

* 201 Created
* 400 Bad Request: c`è un errore del client (JSON, campo mancante o altro).
* 500 INTERNAL_SERVER_ERROR: C`è stato un errore di comunicazione tra il web server e il database



### `/reservations/{code}`

### GET

**Descrizione**: restituisce la prenotazione relativa al codice inserito

**Parametri**: un parametro `code` che indica il codice della prenotazione da recuperare

**Body richiesta**: Non è previsto un body.

**Risposta**: In caso di successo, viene restituita la rappresentazione in JSON della prenotazione, che ha al suo interno il campo `seats` che è un array di oggetti che hanno al loro interno riga e colonna del posto prenotato, `code`, `time`, `title`, `date` (tutte stringhe), `dayIndex`, `movieIndex`, `room`

**Codici di stato restituiti**:

* 200 OK
* 404 Not Found: prenotazione non trovata.
* 500 INTERNAL_SERVER_ERROR: C`è stato un errore di comunicazione tra il web server e il database


### `/reservations/{code}`

### DELETE

**Descrizione**: elimina la prenotazione relativa al codice inserito

**Parametri**: un parametro `code` che indica il codice della prenotazione da eliminare

**Body richiesta**: Non è previsto un body.

**Risposta**: in caso di successo il body è vuoto e la risorsa è stata eliminata dal database

**Codici di stato restituiti**:

* 200 OK
* 404 Not Found: la prenotazione da eliminare non è stata trovata
* 500 INTERNAL_SERVER_ERROR: C`è stato un errore di comunicazione tra il web server e il database


### `/reservations/{code}`

### PUT

**Descrizione**: modifica la prenotazione relativa al codice inserito

**Parametri**: un parametro `code` che indica il codice della prenotazione da eliminare

**Body richiesta**: rappresentazione in formato JSON della prenotazione che ha al suo interno il campo `seats` che è un array di oggetti che hanno al loro interno riga e colonna
del posto prenotato e `code` (una stringa).

**Risposta**: in caso di successo il body è vuoto e la risorsa è stata modificata all`interno del database

**Codici di stato restituiti**:

* 200 OK
* 404 Not Found: la prenotazione da modificare non è stata trovata
* 500 INTERNAL_SERVER_ERROR: C`è stato un errore di comunicazione tra il web server e il database
