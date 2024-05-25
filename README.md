# Online City Tickets

## Run app - docker-compose

```bash
docker-compose up --build -d 
```

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
