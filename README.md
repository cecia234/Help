<h1> Help! App - 226MI - SISTEMI INTEGRATI E MOBILI 2020</h1>
<p>Tramite l'applicazione è possibile mandare richieste di aiuto direttamente alle forze dell'ordine (pulsante Emergenza) o ai propri contatti (precedentemente inseriti nella rubrica dell'applicazione - pulsante Informa Contatti).</p>
<h2> Funzionalità attualmente implementate </h2>
<ul>
  <li>Apertura chiamata alle forze dell'ordine e invio notifica di assistenza al <a href="https://github.com/cecia234/HelpClient">loro client</a> con posizione, tipo di richiesta e informazioni utente</li>
  <li>Possibilità di inviare messaggi di testo, whatsapp e chiamate a uno dei contatti presenti in rubrica </li>
  <li>Possibilità di far squillare il telefono nel caso non si riesca a parlare e quindi a farsi trovare</li>
  <li>Possibilità di mostrare a schermo le proprie informazioni personali, in caso di arrivo dei soccorsi con paziente incosciente</li>
  <li>Possibilità di specificare una lista di priorità per le modalità di contatto utenti (per ora si riflette solo nell'ordine dei pulsanti mostrati prima di effettuare l'azione)</li>
  <li>Localizzazione in Italiano e Inglese</li>
</ul>
<h2> Tecnologie utilizzate </h2>
<p>Un'architettura di tipo serverless è stata preferita all'architettura classica descritta nella documentazione di android studio. </p>
<p>Non sono quindi presenti un DB in locale e uno in remoto acceduto tramite web services in quanto Firebase consente il caching delle informazioni acquisite online e la conseguente persistenza dei dati anche in offline.</p>
<ul>
  <li>Android Studio per sviluppo generale</li>
  <li>Firebase Authentication</li>
  <li>Firebase Firestore per storage e sincronizzazione dati</li>
</ul>
<h2> Permessi Richiesti </h2>
<ul>
  <li>Invio Sms</li>
  <li>Accesso alla posizione</li>
</ul>
<h2> Possibili Migliorie </h2>
<ul>
  <li> Al momento non sono presenti controlli sui campi di input, non inseriti per mancanza di tempo. All'utente è quindi richiesto di riempire tutti i campi forniti in fase di registrazione o di inserimento nuovo contatto, e in caso di errore è mostrato solamente un messaggio generico tramite Toast con la notifica dell'errore.</li>
  <li> Telegram non supporta l'apertura di chat specifiche con messaggio predefinito tramite esecuzione di attività. Di conseguenza è necessaria la registrazione per l'uso delle API o l'utilizzo di un bot, non ancora implementati. Per ora il pulsante lancia un Toast con un semplice messaggio.</li>
  <li> L'interfaccia grafica è molto semplice e non particolarmente coerente nello schema colori </li>
  <li> Presenti troppe chiamate a Firebase per la lettura di dati o per l'aggiornamento dei record, che in uno scenario di utilizzo da parte di molti utenti porterebbe a far salire i costi del servizio.</li>
  <li> Gestione delle casistiche in cui l'utente non fornisce i permessi richiesti (chiamate,sms,posizione) </li>  
</ul>
<h2> Criticità riscontrate durante lo sviluppo </h2>
<ul>
  <li>Non è stato possibile implementare il cambio nome/icona dell'applicazione dall'interno dell'app. Il comportamento non è consistente tra dispositivi diversi, ad es. sul mio telefono non funzionava, su Nexus 4 emulato cambia l'icona ma solo nel task manager. Comportamento segnalato anche da altri utenti sul web. Al momento manca una feature porprietaria di android studio e l'unico modo di cambiare icona in maniera robusta è tramite aggiornamento app.</li>
  <li>Probabilmente a causa di un bug, non è stato possibile eseguire READ da una collezione radice chiamata "contacts" su Firebase. Di conseguenza i contatti sono stati memorizzati in una sottocollezione definita per ogni utente, che è risultata essere anche una soluzione più snella durante il recupero delle informazioni.</li>  
  <li>Non è possibile mandare messaggi Whatsapp in maniera automatica, è possibile solamente aprire la chat di un utente con un messaggio preimpostato e poi mandarlo a mano</li>
  <li>Non è possibile mandare messaggi a più utenti in maniera automatica, al massimo si può definire un testo e poi all'interno di whatsapp selezionare a chi inviarlo (di conseguenza rendendo inutile la gestione dei contatti in questa App)</li>
  <li>Non è possibile inviare messaggi Telegram in maniera semplice tramite Activity (spiegato sopra)</li>  
</ul>


