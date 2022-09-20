# Resolving root with native image resources

This repository demonstrates an issue with the GraalVM Native Image `FileSystemProvider`.
This can be built with `./gradlew nativeCompile`.

The application ships the following `resource-config.json` metadata:

```json
{
  "resources": {
    "includes": [
      {
        "pattern": "first/.*"
      },
      {
        "pattern": "second/.*"
      }
    ]
  }
}
```

The application does the following:
* resolve the GraalVM Native Image `FileSystemProvider` with its official scheme `resource:///`
* lookup root directories for this filesystem and print them
* resolve a particular location given on the CLI and walks it

## Looking up the "first" directory

The "first" location is successfully resolved and files listed.
The "root" directory for the `resource:` filesystem is listed as `/`.
This all works as expected.

```
./build/native/nativeCompile/native-resources resource:/// first
Sep 20, 2022 6:00:29 PM org.example.Main main
INFO: scheme: resource:///, root: first
Sep 20, 2022 6:00:29 PM org.example.Main lambda$main$0
INFO: root directory: /
Sep 20, 2022 6:00:29 PM org.example.Main lambda$main$1
INFO: |- first
Sep 20, 2022 6:00:29 PM org.example.Main lambda$main$1
INFO: |- first/first.txt
```

## Looking up the root directory

The root location for this filesystem cannot be resolved nor walked.
This means we cannot list resources without knowing all first-level locations in the filesystem.

```
./build/native/nativeCompile/native-resources resource:/// /
Sep 20, 2022 6:02:12 PM org.example.Main main
INFO: scheme: resource:///, root: /
Sep 20, 2022 6:02:12 PM org.example.Main lambda$main$0
INFO: root directory: /
Sep 20, 2022 6:02:12 PM org.example.Main main
SEVERE: java.nio.file.NoSuchFileException: /
```