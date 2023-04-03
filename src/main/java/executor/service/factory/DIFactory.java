package executor.service.factory;

import executor.service.services.step.StepExecution;
import executor.service.services.step.StepExecutionClickCSS;
import executor.service.services.step.StepExecutionClickXpath;

import java.util.Arrays;
import java.util.List;

public class DIFactory implements AbstractFactory {

    private static DIFactory diFactory;

    private DIFactory(){
    }

    public static DIFactory init() {
        if (diFactory == null) {
            diFactory = new DIFactory();
        }
        return diFactory;
    }

    @Override
    public <T> T create(Class<T> clazz) {
        if(StepExecutionClickXpath.class.isAssignableFrom(clazz)) {
            return (T) (new StepExecutionClickXpath());
        }
        if(StepExecutionClickCSS.class.isAssignableFrom(clazz)) {
            return (T) (new StepExecutionClickCSS());
        }
        throw new RuntimeException("Class not found.");
    }

    @Override
    public <T> T create(TypeReference<T> typeReference) {
        TypeReference<List<StepExecution>> typeReferenceStepExecution =
                new TypeReference<List<StepExecution>>() {};
        if (typeReferenceStepExecution.getType().equals(typeReference.getType())) {
            return (T) Arrays.asList(
                    StepExecutionClickCSS.getInstance(),
                    StepExecutionClickXpath.getInstance());
//                    StepExecutionSleep.getInstance());
        }
        throw new RuntimeException("Implementation error.");
    }
}
