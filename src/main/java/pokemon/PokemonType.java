package pokemon;

import java.util.*;

public class PokemonType {

    private final String name;
    private final Set<PokemonType> strengths;

    public PokemonType(String name) {
        this.strengths = new HashSet<>();
        this.name = name;
    }

    public void addStrength(PokemonType strength) {
        strengths.add(strength);
    }

    public void addStrengths(List<PokemonType> strengths) {
        this.strengths.addAll(strengths);
    }

    public void removeStrength(PokemonType strength) {
        strengths.remove(strength);
    }

    public void removeStrengths(List<PokemonType> strengths) {
        strengths.forEach(this.strengths::remove);
    }

    public String getName() {
        return name;
    }

    public Set<PokemonType> getStrengths() {
        return strengths;
    }
}
