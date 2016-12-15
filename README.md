![Travis CI Status - idealista solrmeter](https://api.travis-ci.org/idealista-tech/solrmeter.svg?branch=master)

![Logo](logo.gif)

# What is SolrMeter?

It's an standalone java tool for stress tests with Solr (forked from the original (and not under development :p) [SolrMeter](https://github.com/tflobbe/solrmeter) project). Currently supports
traditional Master-Replica and SolrCloud (using ZooKeeper url) deployments and JSON as input document format.

- [Getting Started](#getting-started)
	- [Prerequisities](#prerequisities)
	- [Installing/Usage](#installing/usage)
- [Testing](#testing)
- [Built With](#built-with)
- [Changes](#changes)
- [Authors](#authors)
- [License](#license)
- [Acknowledgments](#acknowledgments)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisities

The project has been tested with [Apache Maven 3.3.3](https://maven.apache.org/) and JDK 1.8. Newer versions of Apache Maven should work but could also present issues.

### Installing/Usage

The following steps should be followed to compile, package and run SolrMeter:

1. Clone the repository ```$ git clone https://github.com/idealista-tech/solrmeter.git```
2. cd to the solr-parent folder ```$ cd solr-parent```
3. Run 'mvn package'
4. The generated jar file is under "solrmeter/target" directory. The jar is named 'solrmeter-{version}-jar-with-dependencies.jar'
5. Run it using ```$ java -jar solrmeter-{version}-jar-with-dependencies.jar```
6. Create files with information of queries, fields, updates and filter queries
7. Specify the URL of Solr (or the URL of [ZooKeeper](https://zookeeper.apache.org/) if you're using SolrCloud) for updates and queries
8. Run the executors with the "Start" button

## Testing

Execute ```$ mvn test``` under solr-parent folder to run the automated tests suite.

## Built With

![Maven](https://img.shields.io/badge/maven-3.3.3-green.svg)

## Changes

For the versions available, see the [tags on this repository](https://github.com/idealista-tech/solrmeter/tags).

You can see what change in each version in the [CHANGELOG.md](CHANGELOG.md) file.

## Authors

* **Idealista** - *Work with* - [idealista-tech](https://github.com/idealista-tech)

See also the list of [contributors](https://github.com/idealista-tech/solrmeter/contributors) who participated in this project.

## License

This project is licensed under the [Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0) license - see the [LICENSE.txt](LICENSE.txt) file for details.

## Acknowledgments

To the original developers of SolrMeter:

* [Tomás Fernández Löbbe](https://github.com/tflobbe)
* [Juan Grande](mailto:juan.grande@gmail.com)
* [Emmanuel Espina](mailto:emmanuel.espina@plugtree.com)
