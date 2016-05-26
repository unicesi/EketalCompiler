package co.edu.icesi.eketal.outputconfiguration

import org.eclipse.xtext.generator.IOutputConfigurationProvider
import org.eclipse.xtext.generator.OutputConfiguration
import org.eclipse.xtext.generator.IFileSystemAccess
import java.util.Set

class EketalOutputConfigurationProvider implements IOutputConfigurationProvider {
	
	public static val EKETAL_OUTPUT = "eketal"
	public static val ASPECTJ_OUTPUT = "aspectj"
	public static val TARGET_SYSTEM_OUTPUT = "target"
	
	override Set<OutputConfiguration> getOutputConfigurations() {
		val defaultOutput = configure(IFileSystemAccess.DEFAULT_OUTPUT, "Output folder", "./src-gen")
		val eketalOutput = configure(EKETAL_OUTPUT, "Output folder for Eketal elements", "./eketal-gen")
		val aspectjOutput = configure(ASPECTJ_OUTPUT, "Output folder for Aspectj elements", "./aspectj-gen")
		val targetOutput = configure(TARGET_SYSTEM_OUTPUT, "Output folder for target-system components", "./src-gen-target")
		return newHashSet(defaultOutput, eketalOutput, aspectjOutput, targetOutput)
	}
	
	def configure(String name, String description, String outputDirectory) {
		val outputConf = new OutputConfiguration(name)
		outputConf.setDescription(description)
		outputConf.setOutputDirectory(outputDirectory)
		outputConf.setOverrideExistingResources(true)
		outputConf.setCreateOutputDirectory(true)
		outputConf.setCleanUpDerivedResources(true)
		outputConf.setSetDerivedProperty(true)
		return outputConf
	}
	
}