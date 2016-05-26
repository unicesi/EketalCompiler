package co.edu.icesi.eketal.outputconfiguration

import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IFileSystemAccessExtension
import org.eclipse.xtext.generator.IFileSystemAccessExtension2
import org.eclipse.emf.common.util.URI
/**
 * @author Miguel Jiménez - Initial contribution and API
 */
class SingleOutputConfigurationFileSystemAccess implements IFileSystemAccess, IFileSystemAccessExtension, IFileSystemAccessExtension2 {

	protected IFileSystemAccess fsa
	protected String outputConfigurationName

	new(org.eclipse.xtext.generator.IFileSystemAccess fsa, String outputConfigurationName) {
		this.fsa = fsa
		this.outputConfigurationName = outputConfigurationName
	}

	override void generateFile(String fileName, CharSequence contents) {
		fsa.generateFile(fileName, outputConfigurationName, contents)
	}

	override void generateFile(String fileName, String outputConfiguration, CharSequence contents) {
		fsa.generateFile(fileName, outputConfigurationName, contents)
	}

	override void deleteFile(String fileName) {
		deleteFile(fileName, outputConfigurationName)
	}

	override void deleteFile(String fileName, String ignoredOutputConfigurationName) {
		(fsa as IFileSystemAccessExtension).deleteFile(fileName, outputConfigurationName)
	}

	override URI getURI(String fileName, String outputConfiguration) {
		return (fsa as IFileSystemAccessExtension2).getURI(fileName, outputConfigurationName)
	}

	override URI getURI(String fileName) {
		return (fsa as IFileSystemAccessExtension2).getURI(fileName, outputConfigurationName)
	}
}