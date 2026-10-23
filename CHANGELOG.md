# Changelog

All notable changes to EndsAndBends are documented in this file.
The format is inspired by [Keep a Changelog](https://keepachangelog.com/) and adheres to [Semantic Versioning](https://semver.org/).

---

## 2.2.0 (2026-XX-XX)

### Enhancements / Évolutions

* **Java 21 Migration**: Migrated EndsAndBends runtime and build environment to **Java 21 (LTS)** with support for OpenJDK and Azul Zulu distributions.
* **Build System & Project Architecture**:
  * Migrated the build system from legacy Ant / NetBeans scripts to standard **Apache Maven** (`pom.xml`).
  * Reorganised codebase to Maven standard directory structure (`src/main/java`, `src/main/resources`, `src/distribution`).
  * Refactored Java package naming to comply with Ifremer standards (`fr.ifremer.sismer.endsandbends`).
  * Automated distribution packaging via `maven-assembly-plugin`, generating standalone archives for Windows (`EAB_2.2.0_win.zip`) and Linux/macOS (`EAB_2.2.0_linux.tar.gz`) containing executable JAR and platform launcher scripts.
* **FAIR Compliance & Open Science**:
  * Added machine-actionable metadata (`codemeta.json`).
  * Clarified GNU LGPL v3 open-source licensing across project metadata, documentation, and the application "About" dialog (`about.htm`).
  * Added Software Heritage (SWHID) and persistent identifier (DOI) readiness.
  * Overhauled repository documentation (`README.md`, `CHANGELOG.md`).
* **Validation & Documentation**:
  * Added reference input datasets and expected reduction outputs for test and validation (`Data/`, `Data/Results/`).
  * Updated complete User Manual to version 2.2.0 (`Docs/EndsAndBends_v2.2.0_User_Manual.docx`, `help/EndsAndBends_v2.2.0_User_Manual.pdf`).

### Bug Fixes / Corrections

* Resolved source file character encoding and string literal escaping issues (UTF-8).
* Fixed classpath and resource bundle loading for internationalised messages and UI icons.
* Cleaned repository from obsolete Ant artifacts and generated NetBeans build scripts.

---

## 2.1.0 (2014-04-04)

### Enhancements / Évolutions

* **CSR Metadata**: Updated Cruise Summary Report (CSR) schemas, web services, and export formats to comply with SeaDataNet ISO 19139 standards (Mantis 20237).
* **Licensing & Identity**: Updated application legal agreement, copyright notices, and SeaDataNet II / Geo-Seas logos and branding in `about.htm`.

---

## 2.06 (2014-03-21)

### Enhancements / Évolutions

* **CDI Metadata ISO 19139**: Upgraded Common Data Index (CDI) reading and writing to ISO 19139 XML metadata format; deprecated legacy ISO 19115 support (Mantis 19916).
* **Logging Framework**: Replaced legacy logging mechanisms with SLF4J / Log4j.
* **Documentation**: Consolidated user guidance into a single standalone PDF user manual (`GS_WP8_D8.1_EndsAndBends_User_Manual.pdf`).

---

## 2.05 (2013-04-09)

### Enhancements / Évolutions

* **Trajectory Segmentation**:
  * Reorganised segmentation tracking so that each segment explicitly identifies start and end point types (Mantis 15394).
  * Added segmentation activity logging to a dedicated log file (`endsAndBends.log`).
* **CSV Format**: Added capability to parse combined date and time from a single CSV column (FAE 15934).
* **GUI / Options**: Added units for the interruption definition threshold in the options panel (FAE 15972).

### Bug Fixes / Corrections

* Corrected Linux launch script naming.
* Corrected UI spelling and label inconsistencies.

---

## 2.03 (2012-02-01)

### Enhancements / Évolutions

* **UKOOA Navigation Format**:
  * Added support for reading and parsing UKOOA navigation files (P1/90 format).
  * Added decimal separator configuration in `ukooa.properties`.

### Bug Fixes / Corrections

* Fixed coordinate extraction for longitude and latitude in UKOOA files (FAE 11327, FAE 11328).
* Fixed GML geometry generation issues in `Jts2GmlDOM` (FAE 11329).

---

## 2.02 (2011-08-25)

### Enhancements / Évolutions

* **Distance Gap Splitting**: Added distance threshold ("gap in distance") parameter for splitting navigation trajectories into segments.
* **GUI**: Redesigned the "Options Constants" configuration panel (`OptionConstants`).

### Bug Fixes / Corrections

* Fixed cartographic display issue when viewing multiple segments under different projections (unified global projection on survey visualization).
* Fixed French language selection when starting with `-ln fr` / `-nl fr`.

---

## 2.01 (2011-03-25)

### Enhancements / Évolutions

* **CSV Import**: Added user selection of column delimiter for input CSV files.
* **Documentation**: Added English online help and user manual (`help/`), separating French documentation into `help_FR/`.

---

## 2.0.0 (2010-10-22)

### Enhancements / Évolutions

* **Major Redevelopment in Java**:
  * Complete redevelopment of EndsAndBends in Java for Ifremer / SISMER by Altran Brest.
  * Interactive graphical interface (Swing) featuring survey track visualisation, segment inspection, interactive highlighting, and cartographic projections.
* **Data Formats Support**:
  * Input formats: CSV (flexible formats, custom separators, date/time masks), NMEA frames, NetCDF NVI.
  * Output formats: Ifremer ASCII P50, CSV, GML, WKT (Well-Known Text / MultiLineString), and ESRI Shapefile using JTS (Java Topology Suite).
  * Reusable XML parameter models for inputs and outputs.
* **Reduction & QC Algorithms**:
  * Implementation of navigation data reduction algorithm based on Douglas-Peucker and tolerance criteria.
  * Configurable segmentation filters (time interval, distance gap, date ranges, start/end indicators).
* **Packaging & Multi-language**:
  * Multi-platform launcher scripts for Windows and Linux.
  * Multi-language GUI support (French and English).

---

## Earlier releases / Versions antérieures

Ends & Bends was originally developed as a desktop application (version 1.1) on 24/12/2002 by NLfB (*Niedersächsisches Landesamt für Bodenforschung*) and GGA (*Institut für Geowissenschaftliche Gemeinschaftsaufgaben*) - Hannover.
It was ported to Java by Ifremer / SISMER around 2003 for quality control and navigation reduction within the SeaDataNet project.
Detailed release logs prior to version 2.0.0 (October 2010) have not been preserved.
