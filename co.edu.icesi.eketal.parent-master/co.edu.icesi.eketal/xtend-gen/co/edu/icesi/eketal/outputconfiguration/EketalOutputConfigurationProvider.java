package co.edu.icesi.eketal.outputconfiguration;

import java.util.Set;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

@SuppressWarnings("all")
public class EketalOutputConfigurationProvider implements IOutputConfigurationProvider {
  public final static String EKETAL_OUTPUT = "eketal";
  
  public final static String ASPECTJ_OUTPUT = "aspectj";
  
  public final static String TARGET_SYSTEM_OUTPUT = "target";
  
  @Override
  public Set<OutputConfiguration> getOutputConfigurations() {
    final OutputConfiguration defaultOutput = this.configure(IFileSystemAccess.DEFAULT_OUTPUT, "Output folder", "./src-gen");
    final OutputConfiguration eketalOutput = this.configure(EketalOutputConfigurationProvider.EKETAL_OUTPUT, "Output folder for Eketal elements", "./eketal-gen");
    final OutputConfiguration aspectjOutput = this.configure(EketalOutputConfigurationProvider.ASPECTJ_OUTPUT, "Output folder for Aspectj elements", "./aspectj-gen");
    final OutputConfiguration targetOutput = this.configure(EketalOutputConfigurationProvider.TARGET_SYSTEM_OUTPUT, "Output folder for target-system components", "./src-gen-target");
    return CollectionLiterals.<OutputConfiguration>newHashSet(defaultOutput, eketalOutput, aspectjOutput, targetOutput);
  }
  
  public OutputConfiguration configure(final String name, final String description, final String outputDirectory) {
    final OutputConfiguration outputConf = new OutputConfiguration(name);
    outputConf.setDescription(description);
    outputConf.setOutputDirectory(outputDirectory);
    outputConf.setOverrideExistingResources(true);
    outputConf.setCreateOutputDirectory(true);
    outputConf.setCleanUpDerivedResources(true);
    outputConf.setSetDerivedProperty(true);
    return outputConf;
  }
}
