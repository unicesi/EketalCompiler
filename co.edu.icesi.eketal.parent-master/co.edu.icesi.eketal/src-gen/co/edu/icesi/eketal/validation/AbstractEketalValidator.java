/*
 * generated by Xtext 2.12.0
 */
package co.edu.icesi.eketal.validation;

import java.util.ArrayList;
import java.util.List;
import jbase.validation.JbaseValidator;
import org.eclipse.emf.ecore.EPackage;

public abstract class AbstractEketalValidator extends JbaseValidator {
	
	@Override
	protected List<EPackage> getEPackages() {
		List<EPackage> result = new ArrayList<EPackage>(super.getEPackages());
		result.add(co.edu.icesi.eketal.eketal.EketalPackage.eINSTANCE);
		result.add(EPackage.Registry.INSTANCE.getEPackage("http://www.eclipse.org/xtext/xbase/Xbase"));
		result.add(EPackage.Registry.INSTANCE.getEPackage("http://www.eclipse.org/xtext/xbase/Xtype"));
		result.add(EPackage.Registry.INSTANCE.getEPackage("http://www.eclipse.org/xtext/common/JavaVMTypes"));
		result.add(EPackage.Registry.INSTANCE.getEPackage("http://www.Jbase.jbase"));
		result.add(EPackage.Registry.INSTANCE.getEPackage("http://www.eclipse.org/Xtext/Xbase/XAnnotations"));
		return result;
	}
	
}
