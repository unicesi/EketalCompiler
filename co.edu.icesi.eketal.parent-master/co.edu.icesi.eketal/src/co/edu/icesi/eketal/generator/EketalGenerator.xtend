package co.edu.icesi.eketal.generator

import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator
import org.eclipse.emf.ecore.resource.Resource
import co.edu.icesi.eketal.eketal.EvDecl
import co.edu.icesi.eketal.eketal.EventClass
import co.edu.icesi.eketal.eketal.AndEvent
import co.edu.icesi.eketal.eketal.EventExpression
import co.edu.icesi.eketal.eketal.OrEvent
import co.edu.icesi.eketal.eketal.Trigger
import co.edu.icesi.eketal.eketal.KindAttribute
import java.io.File
import co.edu.icesi.eketal.eketal.UnaryEvent
import co.edu.icesi.eketal.eketal.JVarD
import java.util.Set
import java.util.TreeSet
import java.util.ArrayList
import co.edu.icesi.eketal.jvmmodel.EketalJvmModelInferrer
import org.eclipse.xtext.xbase.impl.XStringLiteralImpl
import co.edu.icesi.eketal.eketal.Automaton
import org.eclipse.emf.common.util.EList
import co.edu.icesi.eketal.eketal.Decl
//https://www.eclipse.org/forums/index.php/t/486215/

class EketalGenerator implements IGenerator{
		
	override doGenerate(Resource resource, IFileSystemAccess fsa) {
		println("IGenerator line 35")
		
		resource.allContents.filter(typeof(EventClass)).forEach[it.generateAspect(fsa)]
	}
	
	def generateAspect(EventClass modelo, IFileSystemAccess fsa){
		var packageName = "co.edu.icesi.eketal.aspects"
//		fsa.generateFile(prepareFileName("./"+packageName, modelo.name.toFirstUpper), EketalOutputConfigurationProvider::ASPECTJ_OUTPUT, modelo.generate(packageName))
		fsa.generateFile(prepareFileName("./"+packageName, modelo.name.toFirstUpper), IFileSystemAccess.DEFAULT_OUTPUT, modelo.generate(packageName))
	}
	
	
	def prepareFileName(String packageName, String fileName) {
		return (packageName + "." + fileName).replace(".", File.separator) + ".aj"
	}
	
	def CharSequence generate(EventClass modelo, String packageName){
		var packageDefinition = '''package «packageName»;
		
		'''
		var String automatonName = null
		var Set<String> importedLibraries = new TreeSet()
		var pointcuts = new ArrayList<String>
		
		if(modelo.declarations.containsAutomaton)			
			importedLibraries+="co.edu.icesi.eketal.automaton.*"
		importedLibraries+="co.edu.icesi.eketal.groupsimpl.*"
		importedLibraries+="co.edu.icesi.eketal.handlercontrol.*"
		importedLibraries+="co.edu.icesi.ketal.core.Automaton"
		importedLibraries+="co.edu.icesi.ketal.core.NamedEvent"
		importedLibraries+="co.edu.icesi.ketal.core.Event"
		importedLibraries+="java.util.Map"
		importedLibraries+="java.util.HashMap"
		//TODO línea 82, saber cómo se crea el evento
		var aspect = '''
		public aspect «modelo.name.toFirstUpper»{
			
			«FOR event:modelo.declarations»
				«IF event instanceof JVarD»
					//«importedLibraries+=agregarImports((event as JVarD).type.qualifiedName)»
					//--------Evento: «event.name.toString»-------------
					private «(event as JVarD).type.simpleName» «(event as JVarD).name.toFirstLower»;
				«ENDIF»
				«IF event instanceof co.edu.icesi.eketal.eketal.Automaton»
					//«automatonName=event.name»
				«ENDIF»
				«IF event instanceof EvDecl»
					//--------Evento: «event.name.toString»-------------
					pointcut «event.name.toFirstLower»():
						«createPointCut(event as EvDecl, pointcuts)»;
						
					after() returning (Object o): «event.name.toFirstLower»() {
						System.out.println("Returned normally with " + o);
					}
					after() throwing (Exception e): «event.name.toFirstLower»() {
						System.out.println("Threw an exception: " + e);
					}
					after(): «event.name.toFirstLower»(){
						System.out.println("Returned or threw an Exception");
					}
					before(): «event.name.toFirstLower»(){
						EventHandler distribuidor = «EketalJvmModelInferrer.handlerClassName».getInstance();
						Automaton automata = «automatonName.toFirstUpper».getInstance();
						Map map = new HashMap<String, Object>();
						map.put("Automata", automata);
						Event event = new NamedEvent("«event.name»");
						distribuidor.multicast(event, map);
						
						System.out.println("Returned or threw an Exception");
					}
				«ENDIF»
			«ENDFOR»
			
			«FOR pointcut:pointcuts»
				«pointcut»;
			«ENDFOR»
			
		}
		'''
		var imports = '''
		«FOR tipo:importedLibraries»
			import «tipo»;
		«ENDFOR»
		
		'''
		return packageDefinition+imports+aspect
	}
	
	def boolean containsAutomaton(EList<Decl> list){
		for(Decl decl : list){
			if(decl instanceof Automaton)
				return true
		}
		return false
	}
	
	def agregarImports(String name) {
		var importList = new ArrayList
		if(!name.contains('<')){
			importList.add(name)			
		}else{
			var String[] strings = name.split("<")
			for(string:strings){
				importList.add(string.replaceAll(">",""))
			}
		}
		return importList
	}
	
	def createPointCut(EvDecl decl, ArrayList<String> pointcuts) {
		var ArrayList<String> eventsDefinition = new ArrayList
		for(event : decl.eventos){
			eventsDefinition+=eventExpression(event as EventExpression, pointcuts)
		}
		val String pointCutString = eventsDefinition.toString.substring(1, eventsDefinition.toString.length-1)
		return pointCutString
	}
	
	/*
	 * El warnning es omitible, dado que no va a fallar bajo ninguna situación
	 */
	def eventExpression(EventExpression event, ArrayList<String> pointcuts) {
			if(event.tipoEvento!=null){
				var eventKind = event.tipoEvento
				switch(eventKind){
					Trigger:{
						var pointcutTemp = returnCall(eventKind as Trigger)
						pointcuts+=pointcutTemp.get(1).toString
						return pointcutTemp.get(0).toString
					}
					KindAttribute:{
						return returnAttribute(eventKind as KindAttribute)						
					}
				}
			}else{
				
				switch(event){
					AndEvent:{
						//AndEvent
						var andEvent = event as AndEvent
						return 
						'''(«eventExpression(andEvent.left as EventExpression, pointcuts)» && «eventExpression(andEvent.right as EventExpression, pointcuts)»)'''
					}
					OrEvent: {
						//OrEvent
						var orEvent = event as OrEvent
						return 
						'''(«eventExpression(orEvent.left as EventExpression, pointcuts)» || «eventExpression(orEvent.right as EventExpression, pointcuts)»)'''
					}
					UnaryEvent:{
						var unaryEvent = event as UnaryEvent
						return
						'''!«eventExpression(unaryEvent.expr as EventExpression, pointcuts)»'''
					}
				}
			}
	}
	
	def returnAttribute(KindAttribute attribute) {
		if(attribute.condition!=null){
			println(attribute.condition.eContents.size + "doGenerate línea 169")
			var body = ""
			if(attribute.condition.eContents.size==1){
				var XStringLiteralImpl valone = attribute.condition.eContents.get(0) as XStringLiteralImpl
				body = '''«valone.value»'''
			}else{
				if(attribute.condition.eContents.get(0) instanceof XStringLiteralImpl && attribute.condition.eContents.get(1) instanceof XStringLiteralImpl){
					var XStringLiteralImpl valone = attribute.condition.eContents.get(0) as XStringLiteralImpl
					var XStringLiteralImpl valtwo = attribute.condition.eContents.get(1) as XStringLiteralImpl
					body = '''"«valone.value»".equals("«valtwo.value»")'''
				}
			}
			return '''if(«body»)'''
		}else if(attribute.hostgroup!=null){
			return '''if(«EketalJvmModelInferrer.groupClassName».host("«attribute.hostgroup.name»"))'''
		}else if(attribute.ongroup!=null){
			return '''if(«EketalJvmModelInferrer.groupClassName».on("«attribute.ongroup.name»"))'''
//			return '''on()'''//TODO acá debe hacer otro procesamiento dado que este elemento no está
//			//soportado por aspectj
		}
	}
	
	def returnCall(Trigger trigger) {
		/*
		 * Toma todos los parmámetros del evento y los añade a una lista
		 */
		var parameters = newArrayList()
		for(p : trigger.params){
			parameters+=p.simpleName
		}
		/*
		 * La primera posición es el nombre del pointcut, la segunda es la definición del pointcut completo
		 * En el pointcut completo se toma todos los parámetros y agrupan separados por ','
		 */
		var CharSequence[] returnCall = newArrayList('''point«trigger.esig.toString.replaceAll("\\.", "").toFirstUpper»()''',
			'''pointcut point«trigger.esig.toString.replaceAll("\\.", "").toFirstUpper»(): call(* «trigger.esig»(«parameters.join(',')»))''')
		return returnCall
	}
	
}