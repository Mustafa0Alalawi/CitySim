# CitySim

CitySim is a Scala-based simulation that models demographic dynamics within a city grid. It visualizes how individuals or families relocate based on neighborhood composition and personal satisfaction, inspired by Schelling's segregation model.

## Overview

The simulation represents a city as a two-dimensional grid:

- **Cells**: Each cell denotes a residential address.
- **Demographics**:
  - **Blue** and **Red**: Represent two distinct demographic groups.
  - **White**: Indicates vacant addresses.
- **Satisfaction Indicator**: Occupied cells with a circular mark signify residents dissatisfied with their current location.

Residents evaluate their satisfaction based on neighboring cells. Dissatisfied individuals seek vacant addresses that better match their preferences, leading to dynamic shifts in the city's demographic distribution over time.

## Features

- **Interactive Visualization**: Real-time display of the city grid and demographic movements.
- **Dynamic Simulation**: Residents relocate based on satisfaction thresholds.
- **Configurable Parameters**: Adjust grid size, demographic ratios, and satisfaction criteria.
- **Scala Implementation**: Leverages Scala's functional programming features for simulation logic.

## Installation

Ensure you have [Scala](https://www.scala-lang.org/download/) and [SBT](https://www.scala-sbt.org/download.html) installed.

1. Clone the repository:

   ```bash
   git clone https://github.com/Mustafa0Alalawi/CitySim.git
   cd CitySim

## Demo

![image](https://github.com/user-attachments/assets/1ac858b5-9b55-4370-8513-41049ccb9797)
