## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).


SCRIPT POUR IMPORTER UN CSV SUR INFLUXDB

import "csv"
import "experimental/http"

url = "https://raw.githubusercontent.com/ben1frt/SAE-302-appli/main/Java%20RecoveryData/usefulData.csv"
csvData = string(v: http.get(url: url).body)

csv.from(csv: csvData, mode: "raw")

LE FICHIER CSV DOIT RESSEMBLER A CELA 

days,pas0,pas1,pas2,pas3,pas4,pas5,pas6,pas7,pas8,pas9,pas10,pas11,pas12,pas13,pas14,pas15,pas16,pas17,pas18,pas19,pas20,pas21,pas22,pas23
2023-02-25T00:00:00+01:00,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1
2023-02-24T00:00:00+01:00,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1
2023-02-22T00:00:00+01:00,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1
2023-02-23T00:00:00+01:00,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1