# Perfect Gym — JavaFX Desktop Application

Desktop component of **Perfect Gym**, an academic gym-management project developed at ESPRIT.

**Companion web application:** [Perfect Gym Web](https://github.com/AhmedFekih/PerfectGym-Web).

## Main capabilities

The project covers gym discovery, membership offers, coaching sessions, sports events, products and user roles. The desktop source includes JavaFX interfaces and service classes; the original team overview is retained in [README.upstream.md](README.upstream.md).

## Technology stack

- Java 17 and JavaFX 20.
- Maven and the JavaFX Maven plugin.
- MySQL / MariaDB through JDBC.
- Stripe Java SDK and JavaMail dependencies.

## Repository guide

| Path | Purpose |
| --- | --- |
| `PIDEV/pom.xml` | Dependencies, Java version and JavaFX entry point |
| `PIDEV/src/main/java/tn/pidev/controllers/` | JavaFX controllers |
| `PIDEV/src/main/java/tn/pidev/utils/MyDatabase.java` | Database connection configuration |
| `PIDEV/src/main/resources/` | Interface resources |

## Local setup

Prerequisites: JDK 17, Maven and a local MySQL/MariaDB database.

```bash
cd PIDEV
mvn compile
mvn javafx:run
```

Before running, prepare the required database schema. Configure `DB_URL`, `DB_USER` and `DB_PASSWORD` as environment variables. Configure `SMTP_USER`, `SMTP_PASSWORD` and `STRIPE_API_KEY` only for the integrations you use. `.env.example` lists the settings; Java does not load this file automatically, so set them in your terminal or IDE run configuration.

The Maven plugin declares `tn.pidev.controllers.Home` as its entry point. These commands describe the repository configuration; they were not executed during this README update.

## Team and portfolio context

This is a team project presented in Ahmed Fekih's portfolio, not a claim of sole authorship. The original private repository retains its history; this public copy contains a cleaned source snapshot and preserved team documentation. The desktop and Symfony versions are separate repositories of the same Perfect Gym project.

## License

The earlier README mentions MIT, but no project-level LICENSE file was found in the downloaded revision. No new license is assigned by this documentation update.

## Public source snapshot

See [PROVENANCE.md](PROVENANCE.md) for the source revision and original contributors. Credentials were replaced with environment variables or explicit local placeholders before publication. The earlier private Git history is not included.
