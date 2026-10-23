# EndsAndBends Software (navigation data quality control and reduction tool for SeaDataNet)

**EndsAndBends** is an Ifremer software tool developed in the context of [SeaDataNet](https://www.seadatanet.org/) to check, correct and simplify marine navigation tracks.

![EndsAndBends logo](img/endsandbends-logo.jpg "EndsAndBends logo")

[![DOI](https://img.shields.io/badge/DOI-10.12770%2F5fea5584--e16a--4c22--927f--b11e25ff7b58-blue)](https://doi.org/10.12770/5fea5584-e16a-4c22-927f-b11e25ff7b58)
[![License: LGPL-3.0-only](https://img.shields.io/badge/License-LGPL--3.0--only-blue.svg)](https://spdx.org/licenses/LGPL-3.0-only.html)
[![Current stable version](https://img.shields.io/badge/stable-2.2.0-blue)](https://www.seadatanet.org/Software/EndsAndBends/)

## Persistent identifiers

- **DOI (Digital Object Identifier):** [10.12770/5fea5584-e16a-4c22-927f-b11e25ff7b58](https://doi.org/10.12770/5fea5584-e16a-4c22-927f-b11e25ff7b58), for persistent identification and citation of the software.
- **SWHID (SoftWare Hash IDentifier):** pending. A version-specific identifier will be added once the source code for EndsAndBends 2.2.0 has been archived and its identifier verified in [Software Heritage](https://archive.softwareheritage.org/).

## Latest stable release

**EndsAndBends 2.2.0** — latest version available for download from the SeaDataNet portal.

- **Official download:** [EndsAndBends 2.2.0 (SeaDataNet)](https://www.seadatanet.org/Software/EndsAndBends/Download-EndsAndBends-2.2.0).
- **Source code:** [Ifremer GitLab — EndsAndBends](https://gitlab.ifremer.fr/endsandbends/endsandbends).
- **French documentation:** [`help_FR/`](help_FR/) (HTML).
- **English installation manual:** [`EndsAndBends_v2.2.0_Installation_Manual.pdf`](help/EndsAndBends_v2.2.0_Installation_Manual.pdf).


## Overview

EndsAndBends is a **marine navigation data quality control and track reduction tool**. It helps data centres prepare and check navigation records while retaining a representative description of ship trajectories.

The software can:

- Read and analyse navigation files in different formats.
- Detect and correct errors in navigation tracks.
- Reduce the number of navigation points while preserving the required positional accuracy.
- Export corrected and simplified data in different formats.

### Input and test data

Sample and reference files are available in the [`Data/`](Data/) directory, including:

- `.nvi` — native navigation data.
- `.csv` — tabular data.
- `.xml` — metadata and configuration data.
- `Results/` — reference outputs for validation.

## Contributors

**Application manager**

- Cécile PERTUISOT — [Ifremer](https://www.ifremer.fr/).

**Development and maintenance**

- Ifremer, within the SeaDataNet framework. Individual developer credits can be added after confirmation.

## Software metadata

### Domain

Marine navigation data quality control, trajectory simplification and oceanographic data management.

### Support

For support or questions, contact [sdn-userdesk@seadatanet.org](mailto:sdn-userdesk@seadatanet.org).

### Languages

- English
- French

### Programming language

Java

### Project history

Historical documentation refers to NetBeans 6.9.1 and an Ant-based build (`build.xml`). The current README describes a Maven-based build workflow instead.

### Keywords

SeaDataNet, EndsAndBends, navigation, marine data, vessel tracks, trajectories, quality control, track simplification, data reduction, Java, Ifremer.

### Machine-readable metadata

Machine-actionable software metadata are provided in [`codemeta.json`](codemeta.json) in the repository; citation metadata are provided in [`CITATION.cff`](CITATION.cff).

Release history, including the 2.2.0 changes, is documented in [`CHANGELOG.md`](CHANGELOG.md).

## Requirements, supported environments and installation

### Requirements

- **Java 21 or later** for running the application; a JDK is recommended for development.
- **Maven 3.9 or later** for building from source.

Java distributions include [Eclipse Temurin](https://adoptium.net/), [Azul Zulu](https://www.azul.com/downloads/) and [Oracle JDK](https://www.oracle.com/java/technologies/downloads/).

### Supported environments and distribution packages

The existing installation instructions describe these packages:

| Environment | Distribution archive | Launch scripts |
| --- | --- | --- |
| Windows | `EAB_*_win.zip` | `EAB.bat`, `EAB_FR.bat`, `EAB_EN.bat` |
| Linux / macOS | `EAB_*_linux.tar.gz` | `EAB.sh`, `EAB_FR.sh`, `EAB_EN.sh` |

Compatibility with individual operating-system versions, including macOS, should be validated for each published release.

### Installation

**Windows**

1. Download the Windows archive from the [official EndsAndBends 2.2.0 download page](https://www.seadatanet.org/Software/EndsAndBends/Download-EndsAndBends-2.2.0).
2. Extract it, preserving the directory structure.
3. Run `EAB.bat`, `EAB_FR.bat` or `EAB_EN.bat`.

**Linux / macOS**

1. Download the appropriate archive from the [official EndsAndBends 2.2.0 download page](https://www.seadatanet.org/Software/EndsAndBends/Download-EndsAndBends-2.2.0), then extract it:

   ```bash
   tar -xzf EAB_*_linux.tar.gz
   ```

2. Make the launch scripts executable and run the preferred one:

   ```bash
   chmod +x *.sh
   ./EAB.sh
   # Or: ./EAB_FR.sh / ./EAB_EN.sh
   ```

### Manual launch

From the directory containing the application JAR:

```bash
java -jar EndsAndBends.jar          # Default language
java -jar EndsAndBends.jar -nl fr   # French
java -jar EndsAndBends.jar -nl en   # English
```

## Get started

EndsAndBends can be used to open a navigation file, inspect and correct its trajectory, reduce the number of track points and export the resulting data.

Refer to the [French online help](help_FR/) or the [English installation manual](help/EndsAndBends_v2.2.0_Installation_Manual.pdf) for usage and installation instructions.

## Development and build

### Clone the repository

```bash
git clone https://gitlab.ifremer.fr/endsandbends/endsandbends.git
cd endsandbends
```

### Build with Maven

```bash
mvn clean package    # Build and package
mvn clean compile    # Compile only
mvn clean install    # Install artifacts in the local Maven repository
```

The build instructions indicate the following outputs under `target/`:

- `EndsAndBends.jar` — executable JAR.
- `EAB_*_win.zip` — Windows distribution.
- `EAB_*_linux.tar.gz` — Linux/macOS distribution.

### Run in development

- **Main class:** `ifremer.sismer.endsandbends.EndsAndBends`
- **Arguments:** none (default), `-nl fr` (French), or `-nl en` (English).

### Project structure

```text
endsandbends/
├── src/
│   ├── main/
│   │   ├── java/             # Java source code
│   │   └── resources/        # Resources and translations
│   ├── distribution/
│   │   ├── windows/          # Windows launch scripts
│   │   └── linux/            # Unix launch scripts
│   └── assembly/             # Maven Assembly configuration
├── Data/                      # Test and reference files
├── help_FR/                   # French HTML documentation
└── pom.xml                    # Maven project configuration
```

### Preparing a new release

Before building a release:

1. Update the version in `pom.xml` (`<version>X.Y.Z</version>`).
2. Check that the application About/licence information is up to date (see the [`about.htm`](src/main/java/fr/ifremer/sismer/endsandbends/resources/about.htm) file).
3. Update the user or installation manual, where applicable.
4. Run `mvn clean package` and validate the Windows and Linux distribution archives.
5. Create the corresponding Git tag once the release is approved; archive the tagged source to obtain its SWHID.

## License

EndsAndBends is free software licensed under the [GNU Lesser General Public License v3.0 only (LGPL-3.0-only)](https://spdx.org/licenses/LGPL-3.0-only.html).
See the [`LICENSE.txt`](LICENSE.txt) file for the full license terms.

© 2006–2026 Ifremer / SeaDataNet.
