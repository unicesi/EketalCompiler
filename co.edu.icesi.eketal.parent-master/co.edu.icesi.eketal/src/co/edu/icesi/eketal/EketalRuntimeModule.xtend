/*
 * generated by Xtext 2.9.2
 */
package co.edu.icesi.eketal

import com.google.inject.Binder
import org.eclipse.xtext.generator.IOutputConfigurationProvider
import com.google.inject.Singleton
import org.eclipse.xtext.xbase.scoping.batch.ImplicitlyImportedFeatures
import co.edu.icesi.eketal.outputconfiguration.EketalOutputConfigurationProvider
import org.eclipse.xtext.generator.IGenerator
import co.edu.icesi.eketal.outputconfiguration.OutputConfigurationAwaredGenerator
import co.edu.icesi.eketal.scoping.EketalScopeProvider
import org.eclipse.xtext.scoping.IScopeProvider
import org.eclipse.xtext.linking.LinkingScopeProviderBinding

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
class EketalRuntimeModule extends AbstractEketalRuntimeModule {
	
	override void configureLinkingIScopeProvider(Binder binder) {
		binder
			.bind(IScopeProvider)
			.annotatedWith(LinkingScopeProviderBinding)
			.to(EketalScopeProvider);
	}
	
	override void configure(Binder binder) {
		super.configure(binder)
		binder
			.bind(IOutputConfigurationProvider)
			.to(EketalOutputConfigurationProvider)
			.in(Singleton)
//		binder
//			.bind(ImplicitlyImportedFeatures)
//			.to(PascaniImplicitlyImportedFeatures)
	}
	
	override Class<? extends IGenerator> bindIGenerator() {
		return OutputConfigurationAwaredGenerator
	}
	
	override Class<? extends IScopeProvider> bindIScopeProvider() {
		return EketalScopeProvider
	} 
	
}
