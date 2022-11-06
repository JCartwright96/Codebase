package pokemon;

import java.util.*;

public class CoverageChecker {

    private final HashSet<PokemonType> allTypes;
    private final HashSet<PokemonType> addedTypes;
    private final HashSet<PokemonType> covered;
    private final HashSet<PokemonType> uncovered;

    public CoverageChecker() {
        this.allTypes = new HashSet<>();
        this.addedTypes = new HashSet<>();
        this.covered = new HashSet<>();
        this.uncovered = new HashSet<>();
        initialiseAllTypes();
    }

    public HashSet<PokemonType> getAllTypes() {
        return allTypes;
    }

    public HashSet<PokemonType> getAddedTypes() {
        return addedTypes;
    }

    public void addType(String name) {
        for (PokemonType type : allTypes) {
            if(type.getName().equalsIgnoreCase(name)) {
                addedTypes.add(type);
                covered.addAll(type.getStrengths());
            }
        }
    }

    public void addTypes(List<String> names) {
        names.forEach(this::addType);
    }

    public void removeType(String name) {
        for (PokemonType type : allTypes) {
            if(type.getName().equalsIgnoreCase(name)) {
                addedTypes.remove(type);
                removeCoverage(type);
            }
        }
    }

    private void removeCoverage(PokemonType removedType) {
        Set<PokemonType> removedCovers = removedType.getStrengths();
        Set<PokemonType> allCoversFromAddedTypes = new HashSet<>();
        for (PokemonType addedType : addedTypes) {
            allCoversFromAddedTypes.addAll(addedType.getStrengths());
        }
        for (PokemonType typeCovered : removedCovers) {
            if (!allCoversFromAddedTypes.contains(typeCovered)) {
                covered.remove(typeCovered);
            }
        }
    }

    private void calculateUncoveredTypes() {
        uncovered.clear();
         for (PokemonType type : allTypes) {
             if (!covered.contains(type)) {
                 uncovered.add(type);
             }
         }
    }

    public void printCoveredTypes() {
        System.out.println("--- Covered ---");
        System.out.println(formatStrengths(true));
    }

    public void printUncoveredTypes() {
        calculateUncoveredTypes();
        System.out.println("--- Uncovered ---");
        System.out.println(formatStrengths(false));
    }

    public void printCoverage() {
        printCoveredTypes();
        printUncoveredTypes();
    }

    private String formatStrengths(boolean coveredCheck) {
        StringJoiner joiner = new StringJoiner(", ");
        if(coveredCheck) {
            covered.forEach(c -> joiner.add(c.getName()));
        } else {
            uncovered.forEach(c -> joiner.add(c.getName()));
        }
        return joiner.toString();
    }

    private void initialiseAllTypes() {
        PokemonType normal = new PokemonType("Normal");
        PokemonType fire = new PokemonType("Fire");
        PokemonType water = new PokemonType("Water");
        PokemonType grass = new PokemonType("Grass");
        PokemonType ground = new PokemonType("Ground");
        PokemonType rock = new PokemonType("Rock");
        PokemonType bug = new PokemonType("Bug");
        PokemonType psychic = new PokemonType("Psychic");
        PokemonType ghost = new PokemonType("Ghost");
        PokemonType dark = new PokemonType("Dark");
        PokemonType fairy = new PokemonType("Fairy");
        PokemonType steel = new PokemonType("Steel");
        PokemonType poison = new PokemonType("Poison");
        PokemonType electric = new PokemonType("Electric");
        PokemonType flying = new PokemonType("Flying");
        PokemonType ice = new PokemonType("Ice");
        PokemonType fighting = new PokemonType("Fighting");
        PokemonType dragon = new PokemonType("Dragon");

        fire.addStrengths(Arrays.asList(grass, bug));
        water.addStrengths(Arrays.asList(fire, rock, ground));
        grass.addStrengths(Arrays.asList(water, rock, ground));
        ground.addStrengths(Arrays.asList(fire, electric, poison, rock, steel));
        rock.addStrengths(Arrays.asList(fire, ice, flying, bug));
        bug.addStrengths(Arrays.asList(grass, psychic, dark));
        psychic.addStrengths(Arrays.asList(fighting, poison));
        ghost.addStrengths(Arrays.asList(psychic, ghost));
        dark.addStrengths(Arrays.asList(psychic, ghost));
        fairy.addStrengths(Arrays.asList(dragon, fighting, dark));
        steel.addStrengths(Arrays.asList(ice, rock, fairy));
        poison.addStrengths(Arrays.asList(grass, fairy));
        electric.addStrengths(Arrays.asList(water, flying));
        flying.addStrengths(Arrays.asList(bug, grass, fighting));
        ice.addStrengths(Arrays.asList(grass, dragon, flying, ground));
        fighting.addStrengths(Arrays.asList(normal, ice, rock, dark, steel));
        dragon.addStrength(dragon);

        allTypes.addAll(Arrays.asList(fire, water, grass, ground, rock, bug, psychic, ghost, dark, fairy,
                steel, poison, electric, flying, ice, fighting, dragon));
    }

    public static void main(String[] args) {
        CoverageChecker swordTeam = new CoverageChecker();
        swordTeam.addTypes(Arrays.asList("fire", "water", "grass"));
        swordTeam.printCoverage();
    }
}
