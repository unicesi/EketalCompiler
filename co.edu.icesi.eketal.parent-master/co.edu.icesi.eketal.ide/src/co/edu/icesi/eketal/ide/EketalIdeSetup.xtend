/*
 * generated by Xtext 2.12.0
 */
package co.edu.icesi.eketal.ide

import co.edu.icesi.eketal.EketalRuntimeModule
import co.edu.icesi.eketal.EketalStandaloneSetup
import com.google.inject.Guice
import org.eclipse.xtext.util.Modules2

/**
 * Initialization support for running Xtext languages as language servers.
 */
class EketalIdeSetup extends EketalStandaloneSetup {

	override createInjector() {
		Guice.createInjector(Modules2.mixin(new EketalRuntimeModule, new EketalIdeModule))
	}
	
}