package com.botscrew.cli.parser;

import com.botscrew.cli.annotation.ManagedEntity;
import com.botscrew.cli.exception.UnknownCommandException;
import com.botscrew.cli.option.BasicOption;
import com.botscrew.cli.command.Command;
import com.botscrew.cli.option.Option;
import com.botscrew.cli.option.MainArgument;
import com.botscrew.cli.option.MainOption;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.ArrayList;
import java.util.List;

public class DefaultParser implements OptionParser {
    private final List<String> entities = getManagedEntities();

    @Override
    public Command parse(String args) throws UnknownCommandException {
        return new Command(getMainArgument(args), extractData(args));
    }

    private MainArgument getMainArgument(String args) throws UnknownCommandException {
        MainArgument mainArgument = null;
        String[] arr = args.split(" ");
        for (MainOption m : MainOption.values()) {
            for (MainArgument requestType : m.getMainArguments()) {
                if (arr[0].equals(requestType.getOptionName())) {
                    for (String entity : entities) {
                        try {
                            if (arr[1].equals(entity)) {
                                mainArgument = requestType;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            throw new UnknownCommandException("You need to specify entity name and parameters for this option: " + arr[0]);
                        }
                    }
                }
            }
        }
        if (mainArgument == null) {
            throw new UnknownCommandException("Unknown command: " + args);
        }
        return mainArgument;
    }

    private List<Option> extractData(String args) {
        List<Option> optionList = new ArrayList<>();
        List<Integer> indexList = getIndexes(args);

        for (int i = 0; i < indexList.size(); i++) {
            BasicOption option = new BasicOption();

            int index = indexList.get(i) + 3;
            int nextIndex;
            option.setOptionName(getOptionName(args, indexList.get(i)));

            if (i + 1 != indexList.size()) {
                nextIndex = indexList.get(i + 1) - 1;
            } else {
                nextIndex = args.length();
            }
            String arguments = args.substring(index, nextIndex);
            option.setValues(arguments.split(" "));

            optionList.add(option);
        }

        return optionList;
    }

    private List<Integer> getIndexes(String args) {
        List<Integer> indexList = new ArrayList<>();
        for (int index = args.indexOf("-"); index >= 0; index = args.indexOf("-", index + 1)) {
            indexList.add(index);
        }
        indexList.remove(0);
        return indexList;
    }

    private String getOptionName(String args, int index) {
        String str = args.substring(index, args.length());
        StringBuilder builder = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (c != ' ') {
                builder.append(c);
            } else {
                break;
            }
        }
        return builder.toString();
    }

    private static List<String> getManagedEntities() {
        ClassPathScanningCandidateComponentProvider provider =
                new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(ManagedEntity.class));
        List<String> annotatedBeans = new ArrayList<>();

        for (BeanDefinition bd : provider.findCandidateComponents("com.botscrew.entity")) {
            String[] str = bd.getBeanClassName().split("[.]");
            int length = str.length;
            annotatedBeans.add(str[length - 1].toLowerCase());
        }

        return annotatedBeans;
    }

}
