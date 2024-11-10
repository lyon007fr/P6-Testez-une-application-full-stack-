<h3>BACK</h3>

Pour lancer les tests back, se positionner dans le dossier "back" du projet puis utiliser la commande "mvn test clean".

Le resultat du test se trouvera dans le répertoire : C:\Users\mbenziane\OneDrive\OneDrive - Vivendi Group\OCR\Dossier 5\Testez-une-application-full-stack-master\back\target\site\jacoco, fichier index.html

Les tests utilisent une base de donnée H2, déjà intégré à spring, donc aucun installation ou commande supplémentaite n'est nécessaire. Le script présent dans le dossier test_db permet au démarrage des tests
de peupler la bdd avec les informations nécessaire.
---
<h3>FRONT</h3>

Test front unitaire
Lancement des tests front : npm run test --coverage

Le rapport se trouvera dans le fichier index.html présent dans le dossier : front\coverage\jest\lcov-report

Test front end-to-end

Lancement des tests : npm run e2e. Lancer le fichier "Alltest.cy" puis se reporter au fichier index.html présent dans le dossier : front\coverage\lcov-report
