package executor.service.model;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;

public class Scenario {

    private String name;

    @NotEmpty(message = "site is required")
    private String site;

    @Valid
    @NotEmpty(message = "steps are required")
    private List<Step> steps;

    public Scenario() {
    }

    public Scenario(String name, String site, List<Step> steps) {
        this.name = name;
        this.site = site;
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Scenario)) return false;
        Scenario scenario = (Scenario) o;
        return name.equals(scenario.name) && site.equals(scenario.site) && steps.equals(scenario.steps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, site, steps);
    }

    @Override
    public String toString() {
        return "Scenario{" +
                "name='" + name + '\'' +
                ", site='" + site + '\'' +
                ", steps=" + steps +
                '}';
    }
}
