package project.world.simulation;

import project.Constants;

import javax.swing.*;

/**
 * @author Kacper Pietkun
 */
public class InfoMessenger
{
    private JTextArea text_area;

    public InfoMessenger(JTextArea text_area)
    {
        this.text_area = text_area;
    }

    public void resetMessage()
    {
        text_area.setText("");
    }

    public void addCustomMessage(String content)
    {
        text_area.setText(text_area.getText() + content + "\n");
    }

    public void addNewAnimalBornInfo(String animal_class)
    {
        addCustomMessage("New " + animal_class + " is born");
    }

    public void addNewAnimalNotBornInfo(String animal_class)
    {
        addCustomMessage("There is not enough space for a new " + animal_class + "!");
    }

    public void addNewPlantSpawnedInfo(String plant_class)
    {
        addCustomMessage("New " + plant_class + " is spawned");
    }

    public void addNewPlantNotSpawnedInfo(String plant_class)
    {
        addCustomMessage(plant_class + " could not be spawned. There is not enough space!");
    }

    public void addAnimalKilledByAnimal(Organism attacker, Organism defender, int who_won)
    {
        String message = "Attacker: " + attacker.getClassId() + " (strength: " + String.valueOf(attacker.getStrength()) +
            ") | Defender: " + defender.getClassId() + " (strength: " + String.valueOf(defender.getStrength()) + ") | Winner: ";
        message += who_won == Constants.ATTACKER ? "attacker" : "defender";
        addCustomMessage(message);
    }

    public void addDefendedItselfFrom(Organism attacker, Organism defender)
    {
        String message = defender.getClassId() + " defended itself from being eaten by " + attacker.getClassId();
        addCustomMessage(message);
    }

    public void addAnimalRanAway(Organism attacker, Organism defender, Organism who_ran)
    {
        String message = who_ran.getClassId() + " runs away from ";
        message += who_ran == attacker ? defender.getClassId() : attacker.getClassId();
        addCustomMessage(message);
    }

    public void addStrengthBoost(Organism organism, Organism plant, int boost)
    {
        String message = organism.getClassId() + " eats " + plant.getClassId() + " | +" + String.valueOf(boost) + " strength!";
        addCustomMessage(message);
    }

    public void addPlantKillsAnimal(Organism organism, Organism plant)
    {
        String message = organism.getClassId() + " dies, after eating " + plant.getClassId();
        addCustomMessage(message);
    }

    public void addAnimalEatsPlant(Organism animal, Organism plant)
    {
        String message = animal.getClassId() + " eats " + plant.getClassId();
        addCustomMessage(message);
    }

    public void addAbilityActivated(boolean activated)
    {
        String message = activated == true ? "SUPER POWER IS ACTIVE FOR 5 ROUNDS!" : "SUPER POWERT CANNOT BE ACTIVATED YET!";
        addCustomMessage(message);
    }

    public void addHogweedKillsInZone(Organism animal)
    {
        String message = animal.getClassId() + " dies, because it stands to close to the hogweed";
        addCustomMessage(message);
    }
}
