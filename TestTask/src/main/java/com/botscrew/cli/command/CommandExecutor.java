package com.botscrew.cli.command;

import com.botscrew.cli.annotation.OptionManager;
import com.botscrew.cli.exception.UnknownCommandException;
import com.botscrew.cli.option.MainArgument;
import com.botscrew.db.configuration.PersistenceConfig;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommandExecutor {
    private final static ApplicationContext context = new AnnotationConfigApplicationContext(PersistenceConfig.class);

    public void execute(Command command) throws Exception {
        List<Class> annotatedBeans = getAnnotatedBeans();

        findMethodAndInvoke(command, annotatedBeans);
    }

    private List<Class> getAnnotatedBeans() throws ClassNotFoundException {
        ClassPathScanningCandidateComponentProvider provider =
                new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(OptionManager.class));
        List<Class> annotatedBeans = new ArrayList<>();

        for (BeanDefinition bd : provider.findCandidateComponents("com.botscrew.db.service")) {
            annotatedBeans.add(Class.forName(bd.getBeanClassName()));
        }

        return annotatedBeans;
    }

    private void findMethodAndInvoke(Command command, List<Class> annotatedBeans) throws Exception {
        Class enumTape = command.getMainArgument().getClass();
        Method values = enumTape.getDeclaredMethod("values");
        Object result = values.invoke(enumTape);
        MainArgument[] mainArguments = (MainArgument[]) result;

        for (Class bean : annotatedBeans) {

            for (MainArgument argument : mainArguments) {
                if (argument.getOptionName().equals(command.getMainArgument().getOptionName())) {
                    StringBuilder methodToInvoke = new StringBuilder(argument.getOptionName());
                    methodToInvoke.deleteCharAt(0);
                    Object service = context.getBean(bean.getSimpleName());
                    Class serviceClass = service.getClass();
                    Method method = serviceClass.getDeclaredMethod(methodToInvoke.toString(), Command.class);
                    try {
                        method.invoke(service, command);
                    } catch (InvocationTargetException e) {
                        if (e.getCause() instanceof EmptyResultDataAccessException) {
                            System.out.println("You have no entities with such id");
                        } else {
                            System.out.println(e.getCause().getMessage());
                        }
                    }
                }
            }
        }
    }

}
