# Laconic Logging

The aim of this project is to provide a logging framework that by design only outputs log messages when something goes
wrong during the execution of a program, and only outputs the log messages related to the error that occurred.

This project is inspired by the talk
[You are doing logging in .NET wrong. Let’s fix it](https://www.youtube.com/watch?v=Aih9AESZEmo) by
[Nick Chapsas](https://x.com/nickchapsas) given at the [Copenhagen Developers Festival](https://cphdevfest.com/fest) in
2023.

* [Overview](#overview)
* [Getting Started](#getting-started)
* [Logging Error Messages](#logging-error-messages)
* [Logging Progress](#logging-progress)
* [Projects Using Laconic Logging](#projects-using-laconic-logging)

## Overview

![Overview](Overview.png)

## Getting Started

You can use the Laconic Logging library in another Java project by adding the following dependency to the project's POM
file:

```xml
  <dependency>
    <groupId>net.filipvanlaenen</groupId>
    <artifactId>laconic</artifactId>
    <version>1.2.0</version>
  </dependency>
```

Since the project hasn't been deployed to a central Maven repository yet, you either have to add my private Maven
repository too, or download the code, and compile and install it locally. You can add my private Maven repository adding
the following to your POM file:

```xml
  <repositories>
    <repository>
      <id>fvl-mvn-repo</id>
      <name>fvl-mvn-repo</name>
      <url>https://storage.googleapis.com/fvl-mvn-repo/repo/</url>
    </repository>
  </repositories>
```

If you rather obtain a copy of the source code, compile it and install it locally, you can run the following commands to
do this:

```
git clone git@github.com:filipvanlaenen/laconic.git
cd laconic
mvn clean install
```

## Logging Error Messages

With Laconic Logging, you can log an error message as follows:

```java
  import net.filipvanlaenen.laconic.Laconic;

  Laconic.LOGGER.logError("Something went wrong."); 
```

This will print the following message to `System.err`:

```
2024-10-17T10:09:51.421+02:00 ‡ Something went wrong.
```

You can include messages that you've logged using a token like this:

```java
  import net.filipvanlaenen.laconic.Laconic;
  import net.filipvanlaenen.laconic.Token;

  Token token = Laconic.LOGGER.logMessage("Something happened.");
  Laconic.LOGGER.logMessage("Something else happened.", token);
  Laconic.LOGGER.logError("Something went wrong.", token);
```

This will print the following messages to `System.err`:

```
2024-10-17T10:09:51.432+02:00 ‡   Something happened.
2024-10-17T10:09:51.432+02:00 ‡ ⬐ Something else happened.
2024-10-17T10:09:51.432+02:00 ‡ Something went wrong.
```

Not everything is strictly linear though, and sometimes an error can be caused by more than one thing. You can include
different lines of messages as follows:

```java
  import net.filipvanlaenen.laconic.Laconic;
  import net.filipvanlaenen.laconic.Token;

  Token token1 = Laconic.LOGGER.logMessage("Something happened.");
  Laconic.LOGGER.logMessage("Something else happened.", token1);

  Token token2 = Laconic.LOGGER.logMessage("Started something else.");
  Laconic.LOGGER.logMessage("Continued something else.", token2);
  
  Laconic.LOGGER.logError("Something went wrong.", token1, token2);
```

This will print the following messages to `System.err`:

```
2024-10-17T10:09:51.434+02:00 ‡   Something happened.
2024-10-17T10:09:51.434+02:00 ‡ ⬐ Something else happened.
2024-10-17T10:09:51.434+02:00 ‡   Started something else.
2024-10-17T10:09:51.434+02:00 ‡ ⬐ Continued something else.
2024-10-17T10:09:51.434+02:00 ‡ Something went wrong.
```

On the other hand, the same line of messages can also cause more than one error:

```java
  import net.filipvanlaenen.laconic.Laconic;
  import net.filipvanlaenen.laconic.Token;

  Token token = Laconic.LOGGER.logMessage("Something happened.");
  Laconic.LOGGER.logMessage("Something else happened.", token);
 
  Laconic.LOGGER.logError("Something went wrong.", token);

  Laconic.LOGGER.logError("Something else went wrong.", token);
```

This will print the following messages to `System.err`:

```
2024-10-17T10:09:51.437+02:00 ‡   Something happened.
2024-10-17T10:09:51.437+02:00 ‡ ⬐ Something else happened.
2024-10-17T10:09:51.437+02:00 ‡ Something went wrong.

2024-10-17T10:09:51.437+02:00 ‡   Something happened.
2024-10-17T10:09:51.437+02:00 ‡ ⬐ Something else happened.
2024-10-17T10:09:51.437+02:00 ‡ Something else went wrong.
```

Finally, sometimes there's a branch, and log messages on one branch shouldn't be logged on the other branch:

```java
  import net.filipvanlaenen.laconic.Laconic;
  import net.filipvanlaenen.laconic.Token;

  Token token1 = Laconic.LOGGER.logMessage("Something happened.");
  Token token2 = Laconic.LOGGER.logMessage(token1, "Something else happened.");
 
  Laconic.LOGGER.logError("Something went wrong.", token1);

  Laconic.LOGGER.logError("Something else went wrong.", token2);
```

This will print the following messages to `System.err`:

```
2024-11-03T21:12:55.668+01:00 ‡ ⬐ Something happened.
2024-11-03T21:12:55.668+01:00 ‡ Something went wrong.

2024-11-03T21:12:55.668+01:00 ‡   Something happened.
2024-11-03T21:12:55.668+01:00 ‡ ⬐ Something else happened.
2024-11-03T21:12:55.668+01:00 ‡ Something else went wrong.
```

## Logging Progress

Sometimes you want to log progress, regardless of whether an error has occured or not. This can be done as follows:

```java
  import net.filipvanlaenen.laconic.Laconic;
  import net.filipvanlaenen.laconic.Token;

  Laconic.LOGGER.logProgress("Starting.");

  Laconic.LOGGER.logProgress("Doing some work.");

  Token token = Laconic.LOGGER.logMessage("Something happened.");
  Laconic.LOGGER.logMessage("Something else happened.", token);
  Laconic.LOGGER.logError("Something went wrong.", token);

  Laconic.LOGGER.logProgress("Done.");
```

This will print the following messages to `System.err`:

```
2024-10-17T10:09:51.439+02:00 Starting.
2024-10-17T10:09:51.439+02:00 Doing some work.

2024-10-17T10:09:51.439+02:00 ‡   Something happened.
2024-10-17T10:09:51.439+02:00 ‡ ⬐ Something else happened.
2024-10-17T10:09:51.439+02:00 ‡ Something went wrong.

2024-10-17T10:09:51.439+02:00 Done.
```

## Projects Using Laconic Logging

The following projects use Laconic Logging:

* [ASAPOP](https://github.com/filipvanlaenen/asapop)
* [IACAJ](https://github.com/filipvanlaenen/iacaj)
* [jCRK](https://github.com/filipvanlaenen/jcrk)
