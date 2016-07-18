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
import co.edu.icesi.eketal.outputconfiguration.EketalOutputConfigurationProvider
import java.io.File
import co.edu.icesi.eketal.eketal.UnaryEvent
import co.edu.icesi.eketal.eketal.JVarD
import java.util.Set
import java.util.TreeSet
import java.util.ArrayList
import co.edu.icesi.eketal.jvmmodel.EketalJvmModelInferrer
import org.eclipse.xtext.xbase.impl.XStringLiteralImpl
import org.eclipse.xtext.xbase.XStringLiteral
import org.eclipse.xtext.TypeRef
import co.edu.icesi.eketal.eketal.Automaton
import java.util.regex.Pattern
import org.eclipse.emf.common.util.EList
import co.edu.icesi.eketal.eketal.Decl

//https://www.eclipse.org/forums/index.php/t/486215/

class EketalGenerator implements IGenerator{
		
	override doGenerate(Resource resource, IFileSystemAccess fsa) {
		
		println("generator línea 30")
		
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
		var paquete = '''package «packageName»;
		
		'''
		var String automataName = null
		var Set<String> importaciones = new TreeSet()
		var pointcuts = new ArrayList<String>
		
		if(modelo.declarations.containsAutomaton)			
			importaciones+="co.edu.icesi.eketal.automaton.*"
		importaciones+="co.edu.icesi.eketal.groupsimpl.*"
		importaciones+="co.edu.icesi.eketal.handlercontrol.*"
		importaciones+="co.edu.icesi.ketal.core.Automaton"
		importaciones+="co.edu.icesi.ketal.core.NamedEvent"
		importaciones+="co.edu.icesi.ketal.core.Event"
		importaciones+="java.util.Map"
		importaciones+="java.util.HashMap"
		//TODO línea 82, saber cómo se crea el evento
		var aspect = '''
		public aspect «modelo.name.toFirstUpper»{
			
			«FOR event:modelo.declarations»
				«IF event instanceof JVarD»
					//«importaciones+=agregarImports((event as JVarD).type.qualifiedName)»
					//--------Evento: «event.name.toString»-------------
					private «(event as JVarD).type.simpleName» «(event as JVarD).name.toFirstLower»;
				«ENDIF»
				«IF event instanceof co.edu.icesi.eketal.eketal.Automaton»
					//«automataName=event.name»
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
						Automaton automata = «automataName.toFirstUpper».getInstance();
						Map map = new HashMap<String, Object>();
						map.put("Automata", automata);
						Event event = new NamedEvent("«event.name»");
						distribuidor.multicast(event, map);
						
						//distribuidor.multicast(null, null);
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
		«FOR tipo:importaciones»
			import «tipo»;
		«ENDFOR»
		
		'''
		return paquete+imports+aspect
	}
	
	def boolean containsAutomaton(EList<Decl> list){
		for(Decl decl : list){
			if(decl instanceof Automaton)
				return true
		}
		return false
	}
	
	def agregarImports(String name) {
		var lista = new ArrayList
		if(!name.contains('<')){
			lista.add(name)			
		}else{
			var String[] strings = name.split("<")
			for(string:strings){
				lista.add(string.replaceAll(">",""))
			}
		}
		return lista
	}
	
	def createPointCut(EvDecl decl, ArrayList<String> pointcuts) {
		var ArrayList<String> eventos = new ArrayList
		for(event : decl.eventos){
			eventos+=eventExpression(event as EventExpression, pointcuts)
		}
		val String valor = eventos.toString.substring(1, eventos.toString.length-1)
		return valor
	}
	
	//El warnning es omitible, dado que no va a fallar bajo ninguna situación
	def eventExpression(EventExpression event, ArrayList<String> pointcuts) {
			if(event.tipoEvento!=null){
				var tipoEvento = event.tipoEvento
				switch(tipoEvento){
					Trigger:{
						var pointcutTemp = returnCall(tipoEvento as Trigger)
//						println("---porintcut: "+pointcutTemp.get(0)+"---"+pointcutTemp.get(1))
						var patron = pointcutTemp.get(1).toString
						var p = Pattern.compile("(?=param).*?(?=,)");
						var m = p.matcher(patron);
						patron = m.replaceAll("Object");
						
						p = Pattern.compile("(?=param).*?(?=\\))");
						m = p.matcher(patron);
						patron = m.replaceAll("Object");
						
//						pointcuts+=pointcutTemp.get(1).toString.replaceAll("param \\w+ )","Object").replaceAll("param \\w+ ,","Object")
						pointcuts+=patron
						return pointcutTemp.get(0).toString
					}
					KindAttribute:{
						return returnAttribute(tipoEvento as KindAttribute)						
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
			return '''«EketalJvmModelInferrer.groupClassName».host(«attribute.hostgroup.name»)'''
		}else if(attribute.ongroup!=null){
			return '''if(«EketalJvmModelInferrer.groupClassName».on("«attribute.ongroup.name»"))'''
//			return '''on()'''//TODO acá debe hacer otro procesamiento dado que este elemento no está
//			//soportado por aspectj
		}
	}
	
	def returnCall(Trigger trigger) {
		//la primera posición es el nombre del pointcut, la segunda es la definición
		var CharSequence[] retorno = newArrayList('''point«trigger.esig.toString.replaceAll("\\.", "").toFirstUpper»()''', '''pointcut point«trigger.esig.toString.replaceAll("\\.", "").toFirstUpper»(): call(* «trigger.esig»(«trigger.params.join(',')»))''')
		return retorno
	}
	
}