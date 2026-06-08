Żeby odpalić kafkę + bazę danych: docker-compose up -d (terminał w głównym folderze)

Żeby odpalić frontend: npm run dev (terminał ustawić w folderze frontend)

Żeby odpalić backend: UczelniaApplication.java uruchomić za pomocą gui(w folderze backend)

Wejście frontendu jest od: http://localhost:5173/

Wyjście backendu jest od: http://localhost:8080/api/

application-local.properties utworzyć w backend/src/main/resources i podmienić na swoje klucze

spring.security.oauth2.client.registration.google.client-id=CLIENT_ID

spring.security.oauth2.client.registration.google.client-secret=SECRET_ID

stripe.api.key=ST_KEY

stripe.webhook.secret=W_KEY - to co wypisze po odpaleniu:

Komenda do włączania słuchania w stripe CLI: ./stripe listen --forward-to localhost:8080/api/webhooks/stripe

Żeby dostawać maile trzeba do application-local.properties
https://myaccount.google.com/apppasswords
dodać:
spring.mail.username=<adres email>
spring.mail.password=<hasło do maila>

Żeby otworzyć Kafka UI i popatrzeć se na eventy na topicach to trzeba docker-compose.yml odpalić i wchodzi się od: http://localhost:2137

Żeby działał webhook stripe to trzeba:
zainstalować stripe cli: npm i -g @stripe/cli
odpalić go przy użyciu: stripe listen --forward-to localhost:8080/api/webhooks/stripe tam wypluje nam klucz zaczynający sie od: whsec_ i go do application-local.properties i go do stripe.webhook.secret
