# Technical Test

This project can be run using Docker by executing the following command in your terminal:

```bash
docker-compose up -d
```

After running the command, wait until all services are fully initialized. Once completed, the application will be available at `http://localhost:8080`.

Upon startup, all API data will be automatically seeded into the PostgreSQL database.

## API Usage

The project provides several endpoints grouped by resource:

---

### Crates

#### Get All Crates

Retrieves all crates, including the names of the keys and skins that may appear in them.

```
GET http://localhost:8080/crates
```

#### Search Crates

Allows filtering crates by various properties using query parameters:

```
GET http://localhost:8080/crates/search?id={ID}&name={NAME}&description={DESCRIPTION}&image={IMAGE}
```

---

### Skins

#### Get All Skins

Retrieves all skins, along with the list of crates where they can appear.

```
GET http://localhost:8080/skins
```

#### Search Skins

Allows filtering skins by various properties using query parameters:

```
GET http://localhost:8080/skins/search?id={ID}&name={NAME}&description={DESCRIPTION}&image={IMAGE}&team={TEAM}
```

---

### Agents

#### Get All Agents

Retrieves all agents.

```
GET http://localhost:8080/agents
```

#### Search Agents

Allows filtering agents by various properties using query parameters:

```
GET http://localhost:8080/agents/search?id={ID}&name={NAME}&description={DESCRIPTION}&image={IMAGE}&team={TEAM}
```

---

### Keys

These endpoints are protected by Basic Authentication. You can use the following credentials:

```
username: admin
password: password
```

or

```
username: manuel
password: password
```

#### Get All Keys

Retrieves all keys, along with the list of crates where they can appear.

```
GET http://localhost:8080/keys
```

#### Search Keys

Allows filtering keys by various properties using query parameters:

```
GET http://localhost:8080/keys/search?id={ID}&name={NAME}&description={DESCRIPTION}&image={IMAGE}
```

## Additional Notes

- To receive a XML response, send an Accept: application/xml header.
- I used Caffeine to manage cache