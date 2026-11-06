# Board Module (Совет директоров)

A module for preparing and running board of directors meetings with voting
on tablets inside the company's secure network.

## Overview

The server side of the "Board of Directors" module of the Effective Office product.
It lets the secretary create a meeting, set up questions and voting rules,
connect participant tablets, run sequential voting, and get aggregated
results for the minutes. Individual participant answers are deleted
after the meeting is finished — data is stored only in the server memory.

**Key capabilities:**

- Meeting creation and preparation (title, date, participants)
- Agenda questions: "for/against/abstain" and "single choice"
- Quorum and decision rule configuration per question
- Connecting and assigning tablets to participants
- Voting with an aggregated result (without disclosing individual votes)
- Automatic deletion of working data after the meeting is finished

## Quick Start

```bash
# Check that the module is wired in
curl "http://localhost:8080/api/v1/board/ping"
```

### Response Example

```json
{
  "status": "ok"
}
```

## Architecture

The module follows the same layered architecture as the other features (see `feature/sport`):

```
board/
└── core/                                     # Main module
    ├── controller/
    │   └── BoardController.kt                # REST endpoints
    ├── service/
    │   └── BoardService.kt                   # Business logic, in-memory storage
    ├── domain/
    │   ├── DecisionCalculator.kt             # Pure decision/vote-counting logic
    │   └── model/
    │       ├── Meeting.kt                    # Meeting
    │       ├── Question.kt                   # Question and voting rules
    │       └── Vote.kt                       # Participant vote
    ├── dto/
    │   ├── MeetingDetailsDTO.kt              # Meeting DTOs
    │   └── BoardDtoMapper.kt                 # Model -> DTO conversion
    └── exception/
        ├── BoardExceptions.kt                # Module exceptions
        ├── BoardErrorCodes.kt                # Error codes
        └── BoardExceptionHandler.kt          # Global error handler
```

Difference from sport: no providers are needed — data lives in the server memory
and is deleted after the meeting is finished (a security requirement from the
project passport).

## Development

```bash
# Run the application
./gradlew :backend:app:bootRun

# Swagger UI
http://localhost:8080/swagger-ui.html
```

## Dependencies

- Spring Boot Starter Web
- SpringDoc OpenAPI
- Jakarta Validation API
