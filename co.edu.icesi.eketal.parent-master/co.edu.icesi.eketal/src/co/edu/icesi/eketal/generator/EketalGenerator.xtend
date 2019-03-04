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
import java.util.Set
import java.util.TreeSet
import java.util.ArrayList
import co.edu.icesi.eketal.jvmmodel.EketalJvmModelInferrer
import org.eclipse.xtext.xbase.impl.XStringLiteralImpl
import co.edu.icesi.eketal.eketal.Automaton
import org.eclipse.emf.common.util.EList
import co.edu.icesi.eketal.eketal.Decl
import co.edu.icesi.eketal.eketal.Model
import java.util.HashMap
import co.edu.icesi.eketal.eketal.TPrefix
import co.edu.icesi.eketal.eketal.Ltl

class EketalGenerator implements IGenerator{
	
	private var aspectClass="";
	
	override doGenerate(Resource resource, IFileSystemAccess fsa) {
		println("IGenerator line 35")
		
		val listModel = resource.allContents.filter(typeof(Model))
		val Set<String> importedLibraries = new TreeSet()
		if(listModel.hasNext){
			val modelo = listModel.next
			if(modelo.name!==null)
				importedLibraries+=modelo.name+".*"
			if(modelo.importSection!==null && !modelo.importSection.importDeclarations.empty){
				modelo.importSection.importDeclarations.forEach[
					if(it.importedNamespace!==null)
						importedLibraries+=it.importedNamespace
					else
						importedLibraries+=it.importedTypeName
				]				
			}
		}
		
		resource.allContents.filter(typeof(EventClass)).forEach[
			it.generateAspect(fsa, importedLibraries)
		]
	}
	
	def generateAspect(EventClass modelo, IFileSystemAccess fsa, Set<String> importedLibraries){
		var packageName = "co.edu.icesi.eketal.aspects"
		fsa.generateFile(prepareFileName("./"+packageName, modelo.name.toFirstUpper), IFileSystemAccess.DEFAULT_OUTPUT, modelo.generate(packageName, importedLibraries))
	}
	
	
	def prepareFileName(String packageName, String fileName) {
		return (packageName + "." + fileName).replace(".", File.separator) + ".aj"
	}
	
	def CharSequence generate(EventClass modelo, String packageName, Set<String> libraries){
		aspectClass = modelo.name
		var packageDefinition = '''package «packageName»;
		
		'''
		var automatons = modelo.declarations.filter(Automaton)
		
		var buchis = modelo.declarations.filter(Ltl)
		
		val eventsOfAutomaton =  automatons.toInvertedMap[a | 
			val Set<String> events = new TreeSet
			a.steps.forEach[s|s.transitions.forall[t|events.add(t.event.name)]]
			return events
		]
		
		var Set<String> importedLibraries = new TreeSet()
		importedLibraries+=libraries
		var pointcuts = new TreeSet<String>
		
		var after = new HashMap<String, String>()
		var before = new HashMap<String, String>()
		
		if(modelo.declarations.containsAutomaton)			
			importedLibraries+="co.edu.icesi.eketal.automaton.*"
		importedLibraries+="co.edu.icesi.eketal.groupsimpl.*"
		importedLibraries+="co.edu.icesi.eketal.handlercontrol.*"
		importedLibraries+="co.edu.icesi.eketal.reaction.*"
		importedLibraries+="co.edu.icesi.ketal.core.Automaton"
		importedLibraries+="co.edu.icesi.ketal.core.NamedEvent"
		importedLibraries+="co.edu.icesi.ketal.core.Event"
		importedLibraries+="java.util.Map"
		importedLibraries+="java.util.HashMap"
		importedLibraries+="org.apache.commons.logging.Log";
		importedLibraries+="org.apache.commons.logging.LogFactory";
		
		var aspect = '''
		public aspect «aspectClass.toFirstUpper»{
			
			final static Log logger = LogFactory.getLog(«aspectClass.toFirstUpper».class);
			«FOR event:modelo.declarations»
				«IF event instanceof EvDecl»
					//--------Evento: «event.name.toString»-------------
					pointcut «event.name.toFirstLower»():
						«createPointCut(event as EvDecl, pointcuts)»;
						
					//after() returning (Object o): «event.name.toFirstLower»() {
					//	System.out.println("[Aspectj] Returned normally with " + o);
					//}
					//after() throwing (Exception e): «event.name.toFirstLower»() {
					//	System.out.println("[Aspectj] Threw an exception: " + e);
					//}
					after(): «event.name.toFirstLower»(){
						«IF !automatons.isEmpty»
							«FOR automatonName:automatons»
								«IF eventsOfAutomaton.get(automatonName).contains(event.name)»
									Automaton «automatonName.name.toFirstLower» = «automatonName.name.toFirstUpper».getInstance();
									«EketalJvmModelInferrer.PREFIX_NAME_FOR_REACTION_CLASSES».verifyAfter(«automatonName.name.toFirstLower»);
								«ENDIF»			
								//System.out.println("[Aspectj] After: Recognized event in «automatonName.name»");
								logger.info("[Aspectj] After: Recognized event in «automatonName.name.toFirstUpper»");
							«ENDFOR»
						«ENDIF»
					}
					before(): «event.name.toFirstLower»(){
						«IF !automatons.isEmpty || !buchis.isEmpty»
							Event event = new NamedEvent("«event.name»");
							«EketalJvmModelInferrer.PREFIX_NAME_FOR_EVENT_CLASSES» distribuidor = «EketalJvmModelInferrer.PREFIX_NAME_FOR_EVENT_CLASSES».getInstance();
							event.setLocalization(distribuidor.getAsyncAddress());
							Map map = new HashMap<String, Object>();
							distribuidor.multicast(event, map);
							«FOR buchi: buchis»
								Automaton automaton«buchi.name.toFirstUpper» = co.edu.icesi.eketal.buchiautomaton.«buchi.name.toFirstUpper».getInstance();
								if(!automaton«buchi.name.toFirstUpper».evaluate(event)){
									«EketalJvmModelInferrer.PREFIX_NAME_FOR_REACTION_CLASSES».onViolation();
								}else{
									logger.info("[Aspectj] Event respects the property «buchi.name»");
								}
							«ENDFOR»
							«FOR automatonName:automatons»
								«IF eventsOfAutomaton.get(automatonName).contains(event.name)»
									Automaton «automatonName.name.toFirstLower» = «automatonName.name.toFirstUpper».getInstance();
									if(!«automatonName.name.toFirstLower».evaluate(event)){
										//System.out.println("[Aspectj] Before: Event not recognized by the automaton");
										logger.info("[Aspectj] Before: Event not recognized by the automaton: «automatonName.name.toFirstUpper»");
										//Debería parar
									}else{
										«EketalJvmModelInferrer.PREFIX_NAME_FOR_REACTION_CLASSES».verifyBefore(«automatonName.name.toFirstLower»);
										//System.out.println("[Aspectj] Before: Recognized event "+event+" in «automatonName.name»");
										logger.info("[Aspectj] Before: Recognized event "+event+" in «automatonName.name.toFirstUpper»");
									}
								«ENDIF»
							«ENDFOR»
						«ENDIF»
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
	
	def addImports(String name) {
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
	

	def createPointCut(EvDecl decl, TreeSet<String> pointcuts) {
		var ArrayList<String> eventsDefinition = new ArrayList
		for(event : decl.eventos){
			eventsDefinition+=processEventExpression(event as EventExpression, pointcuts)
		}
		val String pointCutString = eventsDefinition.toString.substring(1, eventsDefinition.toString.length-1)
		return pointCutString
	}
	
	/*
	 * El warnning es omitible, dado que no va a fallar bajo ninguna situación
	 */
	def processEventExpression(EventExpression event, TreeSet<String> pointcuts) {
			if(event.tipoEvento!==null){
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
						'''(«processEventExpression(andEvent.left as EventExpression, pointcuts)» && «processEventExpression(andEvent.right as EventExpression, pointcuts)»)'''
					}
					OrEvent: {
						//OrEvent
						var orEvent = event as OrEvent
						return 
						'''(«processEventExpression(orEvent.left as EventExpression, pointcuts)» || «processEventExpression(orEvent.right as EventExpression, pointcuts)»)'''
					}
					UnaryEvent:{
						var unaryEvent = event as UnaryEvent
						return
						'''!«processEventExpression(unaryEvent.expr as EventExpression, pointcuts)»'''
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
		}else if(attribute.hostgroup!==null){
			return '''if(«EketalJvmModelInferrer.PREFIX_NAME_FOR_GROUP_CLASSES».host("«attribute.hostgroup.name»"))'''
		}else if(attribute.ongroup!==null){
			return '''if(«EketalJvmModelInferrer.PREFIX_NAME_FOR_GROUP_CLASSES».on("«attribute.ongroup.name»"))'''
		}
	}
	
	def returnCall(Trigger trigger) {
		/*
		 * Toma todos los parmámetros del evento y los añade a una lista
		 */
		var parameters = newArrayList()
		for(p : trigger.params){			
			if(!p.qualifiedName.contains('$'))
				parameters+=p.simpleName
			else
				parameters+=p.qualifiedName.split("\\.").last.replace('$','.')//p.getQualifiedName('.')
		}
		
		var String typeReturn = null
		if(trigger.returndef===null){
			typeReturn=""
		}else if(trigger.returndef.astk===null){
			typeReturn=trigger.returndef.jvmRef.simpleName
		}else{
			typeReturn = "*"
		}
		
		/*
		 * La primera posición es el nombre del pointcut, la segunda es la definición del pointcut completo
		 * En el pointcut completo se toma todos los parámetros y agrupan separados por ','
		 */
		 var triggerType = ""
		 switch (trigger.triggerType){
			 case TPrefix.CALL:
			 	triggerType="call"
			 case TPrefix.EXECUTION:
			 	triggerType="execution"
		 }
		 
		var CharSequence[] returnCall = newArrayList('''point«trigger.esig.toString.replaceAll("\\.", "").toFirstUpper»()''',
			'''pointcut point«trigger.esig.toString.replaceAll("\\.", "").toFirstUpper»(): «triggerType»(«typeReturn» «trigger.esig»(«parameters.join(',')»))''')
		return returnCall
	}
	
}
