# Merge Yaml Profiles

---

MergeYamlProfiles is a Java program that merges multiple YAML configuration files into a single file.
Usage

    1. Clone the repository or download the source code.
    2. Place the YAML configuration files you want to merge in the src/main/resources directory.
    3. Build and run the program with Maven: 

`mvn clean install`

    - application.yml: the default configuration file
    - application-dev.yml: the configuration file for the "dev" profile
    - The output file will be in the `resources` folder, its default name is `merged-config.yml`

You can modify the names of these files by changing the values of the `APPLICATION_YML`, `APPLICATION_DEV_YML` 
and `MERGED_CONFIG_YML` constants in the `MergeYamlProfiles.java` file.


## Dependencies

This program uses the following libraries:

- `SnakeYAML`: for parsing and generating YAML files
- `Apache Commons IO`: for loading files from the filesystem
