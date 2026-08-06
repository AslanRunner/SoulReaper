# SoulReaper - OOP Course Project (Terminal-Based Java Game)

This repository contains SoulReaper, an interactive, terminal-based Role-Playing Game (RPG) developed in Java as a term project for the Object-Oriented Programming (OOP) course.

## Project Purpose and Overview
The primary objective of this project is to apply core Object-Oriented Programming concepts in a practical scenario. The entire application runs directly within the terminal, processing real-time user inputs through the command line and displaying text-based visual game states.

Inspired by supernatural anime lore, players take on the role of a guardian navigating through hostile zones to purify corrupted spirits and maintain the spiritual balance of the universe.

## Documentation and Architecture
For a deep dive into the game's architecture and design decisions, please review the documentation:
* UML Diagram: Visual representation of the system's class structures and relationships. Located at docs/UML_Diagram.png.
* Javadoc: Full technical documentation for all methods is available at docs/javadoc/index.html.

## Applied OOP Concepts and Technical Implementation
The system architecture leverages fundamental software engineering principles and Java features:

* Encapsulation: Critical game data (such as player attributes, financial budget, and game states) are protected using appropriate access modifiers (private, protected) and exposed safely via getter and setter methods.
* Inheritance: Redundant code is minimized by deriving specific entities from generic base classes to establish clear hierarchical structures (e.g., Shinigami, Hollow, and VastoLorde inheriting from BaseSoul and Enemy).
* Polymorphism: Method overriding and interfaces (Attack, Usable, Lootable, Saveable) are utilized to allow identical method triggers (like useItem()) to behave uniquely depending on the runtime context of the objects.
* Abstraction: Essential game loops, managers, and entities decouple definition from implementation using abstract classes (BaseSoul, Item) to ensure a modular design.
* Exception Handling: A multi-layered strategy using custom exceptions (InsufficientBudgetException, OverLoopException, etc.) ensures the game thread remains stable during illegal moves or boundary errors.
* File I/O: Storylines have been stored as .txt files for cleaner code. End-game statistics and summaries are automatically written to external text files.

## How to Play
1. Run the application to display the main interactive terminal menu.
2. Follow the command-line prompts and input numbers or characters to execute actions (Attack, Defend, Use Item, Visit Shop, etc.).
3. Objective: Defeat every enemy in the region, manage your budget strategically, and reach the final boss to win the game.

## Getting Started and Execution

Prerequisites
* Java Development Kit (JDK) 8 or higher
* Any standard Java IDE (e.g., IntelliJ IDEA, Eclipse) or a command-line terminal

Execution via Terminal
1. Clone the repository to your local machine:
   git clone https://github.com/AslanRunner/SoulReaper.git
2. Navigate into the source code directory:
   cd SoulReaper/src
3. Compile the Java source files:
   javac *.java
4. Run the compiled application:
   java GameEngine
