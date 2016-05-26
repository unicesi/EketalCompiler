package co.edu.icesi.eketal.outputconfiguration

import org.eclipse.xtext.xbase.compiler.JvmModelGenerator
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import com.google.inject.Inject
import co.edu.icesi.eketal.generator.EketalGenerator

class OutputConfigurationAwaredGenerator extends JvmModelGenerator{
	
	@Inject private EketalGenerator generator
	
	/*
	 * More information on using both IGenerator and IJvmModelInferrer:
	 * https://www.eclipse.org/forums/index.php/t/486215/
	 */
	override void doGenerate(Resource input, IFileSystemAccess fsa) {
		val _contents = input.getContents()
		for (obj : _contents) {
			val adapters = obj.eAdapters.filter(OutputConfigurationAdapter)
			for (adapter : adapters) {
				var outputConfiguration = adapter.getOutputConfigurationName()
				if (outputConfiguration == EketalOutputConfigurationProvider::EKETAL_OUTPUT) {
					val sfsa = new SingleOutputConfigurationFileSystemAccess(fsa, outputConfiguration)
					this.internalDoGenerate(obj, sfsa) // PascaniJvmModelInferrer
				} else if (outputConfiguration == EketalOutputConfigurationProvider::ASPECTJ_OUTPUT || IFileSystemAccess.DEFAULT_OUTPUT==outputConfiguration) {
					this.generator.doGenerate(input, fsa)
				}
			}
			if (adapters.isEmpty) {
				this.internalDoGenerate(obj, fsa)
			}
		}
	}
	
}