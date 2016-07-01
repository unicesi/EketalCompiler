package co.edu.icesi.eketal.ui.wizard;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.xtext.ui.util.PluginProjectFactory;

public class EketalPluginCreator extends EketalProjectCreator {
	
	
	@Override
	protected PluginProjectFactory createProjectFactory() {
		PluginProjectFactory projectFactory = super.createProjectFactory();
		projectFactory.addImportedPackages(eketalRequiredPackages());
		projectFactory.addRequiredBundles(eketalRequiredBundles());
		projectFactory.addProjectNatures(aspectjNature());
		projectFactory.setWithPluginXml(false);
		return projectFactory;
	}
	
	
	private String aspectjNature() {
		return "org.eclipse.ajdt.ui.ajnature";
	}
	
	private String mavenNature() {
		return "org.eclipse.m2e.core.maven2Nature";
	}

	private List<String> eketalRequiredBundles() {
		ArrayList<String> array = new ArrayList<>();
		array.add("co.edu.icesi.eketal");
		array.add("co.edu.icesi.eketal.lib.osgi;bundle-version=\"1.0.0\"");
		array.add("org.aspectj.runtime");
		return array;
	}

	private List<String> eketalRequiredPackages() {
		ArrayList<String> array = new ArrayList<>();
		array.add("co.edu.icesi.ketal.distribution");
		array.add("co.edu.icesi.ketal.distribution.transports.jgroups");
		return array;
	}
	
}
