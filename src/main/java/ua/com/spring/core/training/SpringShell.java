package ua.com.spring.core.training;

/*
import com.google.common.io.LineReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.shell.InputProvider;
import org.springframework.shell.Shell;
import org.springframework.shell.SpringShellAutoConfiguration;
import org.springframework.shell.jcommander.JCommanderParameterResolverAutoConfiguration;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.JLineShellAutoConfiguration;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.shell.standard.StandardAPIAutoConfiguration;
import org.springframework.shell.standard.commands.StandardCommandsAutoConfiguration;

import java.io.IOException;

@Configuration
@Import({
        SpringShellAutoConfiguration.class,
        JLineShellAutoConfiguration.class,

        JCommanderParameterResolverAutoConfiguration.class,
        StandardAPIAutoConfiguration.class,

        StandardCommandsAutoConfiguration.class,
})
public class SpringShell {

    public static void main(String[] args) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringShell.class);
        Shell shell = context.getBean(Shell.class);
        shell.run(context.getBean(InputProvider.class));
    }

    @Bean
    @Autowired
    public InputProvider inputProvider(LineReader lineReader, PromptProvider promptProvider) {
        return new InteractiveShellApplicationRunner.JLineInputProvider(lineReader, promptProvider);
    }
}*/


import org.springframework.context.annotation.*;
import org.springframework.shell.CommandLine;
import org.springframework.shell.SimpleShellCommandLineOptions;
import org.springframework.shell.core.ExitShellRequest;
import org.springframework.shell.core.JLineShellComponent;

import java.io.IOException;

@Configuration
@ImportResource(locations = {"classpath:application-context.xml"})
@ComponentScan(value = {"ua.com.spring.core.training", "org.springframework.shell.commands", "org.springframework.shell.converters", "org.springframework.shell.plugin.support"})
public class SpringShell {
    private static String[] args;

    @Bean("commandLine")
    public CommandLine getCommandLine() throws IOException {
        return SimpleShellCommandLineOptions.parseCommandLine(args);
    }

    @Bean("shell")
    public JLineShellComponent getShell() {
        return new JLineShellComponent();
    }

    public static void main(String[] args) {
        SpringShell.args = args;
        System.setProperty("jline.terminal", "none");

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringShell.class);
        ctx.registerShutdownHook();

        JLineShellComponent shell = ctx.getBean(JLineShellComponent.class);

        shell.start();
        shell.waitForComplete();
        ExitShellRequest exitShellRequest = shell.getExitShellRequest();
        if (exitShellRequest == null)
            exitShellRequest = ExitShellRequest.NORMAL_EXIT;
        System.exit(exitShellRequest.getExitCode());
    }

}
