# Virtual World Simulator (Java)

---

### Project Overview

Virtual World Simulator is a turn-based graphical application written in **Java (Swing)**.  
The program simulates a 2D grid world inhabited by animals and plants.

The project was implemented as part of an Object-Oriented Programming course.

---

### Implemented Requirements

- Turn-based simulation  
- Full OOP architecture (inheritance, abstraction, polymorphism, encapsulation)  
- Graphical interface using Swing  
- All required animal and plant species  
- Human controlled with keyboard  
- Special ability system 
- Animal reproduction  
- Plant spreading  
- Adding organisms by clicking on the board

---

### Architecture

The project follows classic OOP hierarchy:

Organizm (abstract)  
├── Zwierze (abstract)  
│   ├── Wilk  
│   ├── Owca  
│   ├── Lis  
│   ├── Zolw  
│   ├── Antylopa  
│   └── Czlowiek  
│  
└── Roslina (abstract)  
├── Trawa  
├── Mlecz  
├── Guarana  
├── WilczeJagody  
└── BarszczSosnowskiego

---

### World Logic

- `Swiat` manages organisms and turn execution
- Each organism implements:
    - `akcja()` – behavior during turn
    - `kolizja()` – interaction logic
- Collision resolution depends on strength and species-specific rules

---

### Rules

#### Animals
- Move randomly (except Human)
- Fight based on strength
- Reproduce when meeting the same species

Some special behaviors:
-  Fox avoids stronger enemies
-  Turtle blocks weak attacks
-  Antelope moves 2 tiles and may escape
-  Human controlled by player

#### Plants
- Spread with probability
- Species-specific effects:
    - Guarana increases strength
    - Deadly nightshade kills the eater
    - Hogweed kills nearby animals

---

### GUI (Swing)

- Custom board rendering
- Next turn button
- Event log panel
- Mouse click -> add organism
- WASD -> move Human
- Key activation -> special ability

---

### Technologies

- Java SE
- Swing (JFrame, JPanel, event handling)
- Object-Oriented Programming principles


---

### How to Run

1. Open the project in IntelliJ IDEA or Eclipse
2. Run `Main.java`
3. Controls:
    - WASD -> move Human
    - Click on board -> add organism
    - GUI button -> next turn

---

### License

Project available under the MIT license.
