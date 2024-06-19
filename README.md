# Online City Tickets

## Run app - docker-compose

1. Install `mkcert`
2. Run `mkcert -install`
3. Run `mkcert localhost 127.0.0.1 ::1`
4. Copy `localhost.pem` and `localhost-key.pem` to `online-city-tickets-frontend/ssl`
5. Run the following command:
    ```bash
    docker-compose up --build -d 
    ```
6. Open `https://localhost` in your browser


## Backend

> [!NOTE]
> A local installation of Docker is required to run the backend.

```bash
cd online-city-tickets-backend
./gradlew bootRun
```

## Frontend

Initial setup:
```bash
cd online-city-tickets-frontend
nvm i                 # https://github.com/nvm-sh/nvm
npm i -g @angular/cli # (optional, used for code generation)
npm i
```

Further usage:
```bash
cd online-city-tickets-frontend
nvm use
npm run start  # Run developer server with hot-reload
npm run build  # Build the project for production use
npm run format # Automatically format the code
npm test      # Execute the unit tests
```
