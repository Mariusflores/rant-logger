# rant-logger

A CLI tool for logging rants from anywhere in your terminal.
Built on top of [baby-redis](https://github.com/mariusflores/baby-redis)
as the data store.

## Status

✅ **In use.** Simple and functional.

## Features

- Log rants instantly from anywhere in the terminal
- List all rants with timestamps
- Find specific rants by key
- Delete individual rants
- Clear all rants
- Count total rants

## Usage

```bash
rant "Maven cached a failure again"     # Log a rant
rant list                               # Show all rants with timestamps
rant find rant:<id>                     # Find a specific rant
rant delete rant:<id>                   # Delete a rant
rant count                              # Show total number of rants
rant clear                              # Delete all rants
```

## Prerequisites

- Java 21+
- Maven
- [baby-redis-client](https://github.com/mariusflores/baby-redis-client) installed locally
- A running [baby-redis](https://github.com/mariusflores/baby-redis) server

## Building

```bash
git clone https://github.com/mariusflores/rant-logger.git
cd rant-logger
mvn clean package
```

## Running

```bash
java -jar target/rant-logger.jar <message or command>
```

## Related

- [baby-redis](https://github.com/mariusflores/baby-redis) — the server
- [baby-redis-client](https://github.com/mariusflores/baby-redis-client) — the client library
- [expense-tracker](https://github.com/mariusflores/expense-tracker) — another CLI tool built on baby-redis