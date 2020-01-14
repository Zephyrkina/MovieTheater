package ua.com.spring.core.training;

import org.springframework.context.annotation.*;
import org.springframework.shell.CommandLine;
import org.springframework.shell.SimpleShellCommandLineOptions;
import org.springframework.shell.core.ExitShellRequest;
import org.springframework.shell.core.JLineShellComponent;

import java.io.IOException;

@Configuration
@ImportResource(locations = {"classpath:application-context.xml"})
@ComponentScan(value = {"ua.com.spring.core.training.commands", "org.springframework.shell.commands", "org.springframework.shell.converters", "org.springframework.shell.plugin.support"})
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