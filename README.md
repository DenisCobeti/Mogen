### Mogen: mobility generator

## Vehicle simulation program capable of exporting traces in ns2/ns3 format

This software consists of a vehicle simulator that exports mobility traces written 
from real-world maps. It was designed to implement the first step in vehicular 
ad-hoc networks (VANET), creating  traces with various mobility models chosen 
by the user in real environment. These traces are exported in a format 
understandable by the network simulator ns2 or ns3 to simulate VANET networks.

- :car: Create personalized vehicles inside simulations.
- :earth_africa: Get real-world maps using OSM (Open Street Maps).
- :page_with_curl: Use mobility models such as random, flow and OD matrixes.
- :oncoming_automobile: Use various following models.
- :globe_with_meridians: Import OD matrixes.

## Requirements

- Sumo 1.2
- JDK 8
- Python 2 or 3

## Dependencies

- [OSM](https://www.openstreetmap.org)
- [JXMapViewer](https://github.com/msteiger/jxmapviewer2)
- [Sumo 1.2](https://www.eclipse.org/sumo/)
- [JDK 8](https://www.oracle.com/es/java/technologies/javase/javase-jdk8-downloads.html)
- [Python 2 or 3](https://www.python.org/)
- [Common Loggings 1.2](https://commons.apache.org/proper/commons-logging/)
