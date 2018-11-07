package cimeroEditor.XMLGeneration;

import com.bull.cimero.pluginEditor.generator.serviceMixGenerator.model.xmlModel.*;
import com.bull.cimero.pluginEditor.generator.serviceMixGenerator.model.global.*;
import com.bull.cimero.pluginEditor.generator.serviceMixGenerator.model.global.standardJBIClassImplementation.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

public class Container
{
  protected static String nl;
  public static synchronized Container create(String lineSeparator)
  {
    nl = lineSeparator;
    Container result = new Container();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + NL + "" + NL + "<!--" + NL + "############################################################" + NL + "    Generated by CimeroEditor" + NL + " \tBull S. A. S., Rue Jean Jaures, B.P.68, 78340, Les Clayes-sous-Bois" + NL + "############################################################" + NL + "-->" + NL + "" + NL + "" + NL + "<beans xmlns=\"http://xbean.org/schemas/spring/1.0\"";
  protected final String TEXT_2 = NL + "    xmlns:";
  protected final String TEXT_3 = "=\"";
  protected final String TEXT_4 = "\"" + NL + "\t";
  protected final String TEXT_5 = "xmlns:";
  protected final String TEXT_6 = "=\"";
  protected final String TEXT_7 = "\"" + NL + "\t";
  protected final String TEXT_8 = ">" + NL + "" + NL + "\t<import resource=\"classpath:jmx.xml\" />" + NL + "\t<import resource=\"classpath:activemq.xml\" />" + NL + "\t<import resource=\"classpath:jndi.xml\" />" + NL + "" + NL + "<!-- JBI container -->" + NL + "<";
  protected final String TEXT_9 = ":container id=\"jbi\" useMBeanServer=\"true\"" + NL + " createMBeanServer=\"true\" dumpStats=\"true\" statsInterval=\"10\"" + NL + " flowName=\"st\">" + NL + " <";
  protected final String TEXT_10 = ":activationSpecs>" + NL + "\t";
  protected final String TEXT_11 = NL + "    <!-- ####################### ";
  protected final String TEXT_12 = " ######################## -->" + NL + "    <";
  protected final String TEXT_13 = ":activationSpec componentName=\"";
  protected final String TEXT_14 = "\" service=\"";
  protected final String TEXT_15 = ":";
  protected final String TEXT_16 = "\"";
  protected final String TEXT_17 = NL + "\t  destinationService=\"";
  protected final String TEXT_18 = ":";
  protected final String TEXT_19 = "\"";
  protected final String TEXT_20 = ">" + NL + "  \t";
  protected final String TEXT_21 = "  <";
  protected final String TEXT_22 = ":component>";
  protected final String TEXT_23 = NL;
  protected final String TEXT_24 = "\t   <bean ";
  protected final String TEXT_25 = " xmlns=\"";
  protected final String TEXT_26 = "\"" + NL + "\t   \t\t";
  protected final String TEXT_27 = "class=\"";
  protected final String TEXT_28 = "\">" + NL;
  protected final String TEXT_29 = "           <property name=\"";
  protected final String TEXT_30 = "\"";
  protected final String TEXT_31 = "  value=\"";
  protected final String TEXT_32 = "\" />" + NL;
  protected final String TEXT_33 = ">" + NL + "\t\t\t\t\t<list>";
  protected final String TEXT_34 = NL + "\t\t\t\t\t\t<value>";
  protected final String TEXT_35 = "</value>";
  protected final String TEXT_36 = NL + "\t\t\t\t\t</list>" + NL + "\t\t\t </property>" + NL;
  protected final String TEXT_37 = "\t\t\t <property name=\"";
  protected final String TEXT_38 = "\">" + NL + "\t\t\t\t<map>" + NL + "\t\t\t\t\t<entry>";
  protected final String TEXT_39 = NL + "\t\t\t\t\t\t<key>" + NL + "\t\t\t\t\t\t\t<bean class=\"";
  protected final String TEXT_40 = "\">" + NL;
  protected final String TEXT_41 = "           \t\t\t\t\t\t<property name=\"";
  protected final String TEXT_42 = "\"";
  protected final String TEXT_43 = "  value=\"";
  protected final String TEXT_44 = "\" />" + NL;
  protected final String TEXT_45 = ">" + NL + "\t\t\t\t\t\t\t\t\t\t<list>";
  protected final String TEXT_46 = NL + "\t\t\t\t\t\t\t\t\t\t\t<value>";
  protected final String TEXT_47 = "</value>";
  protected final String TEXT_48 = NL + "\t\t\t\t\t\t\t\t\t\t</list>" + NL + "\t\t\t\t\t\t\t\t\t</property>";
  protected final String TEXT_49 = "\t\t\t\t\t\t\t</bean>" + NL + "\t\t\t\t\t\t</key>" + NL + "\t\t\t\t\t\t<bean class=\"";
  protected final String TEXT_50 = "\">" + NL;
  protected final String TEXT_51 = "           \t\t\t\t\t\t<property name=\"";
  protected final String TEXT_52 = "\"";
  protected final String TEXT_53 = "  value=\"";
  protected final String TEXT_54 = "\" />" + NL;
  protected final String TEXT_55 = ">" + NL + "\t\t\t\t\t\t\t\t\t\t<list>";
  protected final String TEXT_56 = NL + "\t\t\t\t\t\t\t\t\t\t\t<value>";
  protected final String TEXT_57 = "</value>";
  protected final String TEXT_58 = NL + "\t\t\t\t\t\t\t\t\t\t</list>" + NL + "\t\t\t\t\t\t\t\t\t</property>";
  protected final String TEXT_59 = "\t\t\t\t\t\t</bean>";
  protected final String TEXT_60 = NL + "\t\t\t\t\t</entry>" + NL + "\t\t\t\t</map>" + NL + "\t\t\t </property>" + NL;
  protected final String TEXT_61 = "\t\t\t <property name=\"";
  protected final String TEXT_62 = "\">" + NL;
  protected final String TEXT_63 = " ";
  protected final String TEXT_64 = "<bean class=\"";
  protected final String TEXT_65 = "\">" + NL;
  protected final String TEXT_66 = " ";
  protected final String TEXT_67 = "   <constructor-arg value=\"";
  protected final String TEXT_68 = "\" />" + NL;
  protected final String TEXT_69 = " ";
  protected final String TEXT_70 = "</bean>" + NL;
  protected final String TEXT_71 = "                 <bean ";
  protected final String TEXT_72 = " xmlns=\"";
  protected final String TEXT_73 = "\"" + NL + "\t\t\t\t   \t\t";
  protected final String TEXT_74 = "class=\"";
  protected final String TEXT_75 = "\">" + NL;
  protected final String TEXT_76 = "\t\t\t\t      <property name=\"";
  protected final String TEXT_77 = "\"";
  protected final String TEXT_78 = "  value=\"";
  protected final String TEXT_79 = "\" />";
  protected final String TEXT_80 = ">" + NL + "\t\t\t\t\t\t\t\t<list>";
  protected final String TEXT_81 = NL + "\t\t\t\t\t\t\t\t\t  <value>";
  protected final String TEXT_82 = "</value>";
  protected final String TEXT_83 = "</list>" + NL + "\t\t\t\t\t\t</property>";
  protected final String TEXT_84 = ">";
  protected final String TEXT_85 = NL;
  protected final String TEXT_86 = " ";
  protected final String TEXT_87 = "<bean class=\"";
  protected final String TEXT_88 = "\">" + NL;
  protected final String TEXT_89 = " ";
  protected final String TEXT_90 = "   <constructor-arg value=\"";
  protected final String TEXT_91 = "\" />" + NL;
  protected final String TEXT_92 = " ";
  protected final String TEXT_93 = "</bean>" + NL + "\t\t\t\t\t </property>";
  protected final String TEXT_94 = NL;
  protected final String TEXT_95 = "\t\t\t\t  </bean>" + NL;
  protected final String TEXT_96 = "\t\t\t</property>" + NL;
  protected final String TEXT_97 = "       </bean>";
  protected final String TEXT_98 = NL;
  protected final String TEXT_99 = "\t\t<";
  protected final String TEXT_100 = ":component>" + NL + "\t\t\t<";
  protected final String TEXT_101 = ":endpoints>" + NL;
  protected final String TEXT_102 = " ";
  protected final String TEXT_103 = " ";
  protected final String TEXT_104 = "\t\t\t\t<";
  protected final String TEXT_105 = ":endpoint" + NL + "\t\t\t\t\tdefaultMep=\"";
  protected final String TEXT_106 = "\"" + NL + "\t\t\t\t\tservice=\"";
  protected final String TEXT_107 = ":";
  protected final String TEXT_108 = "\"" + NL + "\t\t\t\t\tendpoint=\"";
  protected final String TEXT_109 = "\"" + NL + "\t\t\t\t\trole=\"";
  protected final String TEXT_110 = "\"" + NL + "\t\t\t\t\tlocationURI=\"";
  protected final String TEXT_111 = "\"" + NL + "\t\t\t\t\tsoap=\"";
  protected final String TEXT_112 = "true";
  protected final String TEXT_113 = "false";
  protected final String TEXT_114 = "\">" + NL + "\t\t\t\t</";
  protected final String TEXT_115 = ":endpoint>";
  protected final String TEXT_116 = " ";
  protected final String TEXT_117 = "\t\t\t\t<";
  protected final String TEXT_118 = ":wire-tap service=\"";
  protected final String TEXT_119 = ":";
  protected final String TEXT_120 = "\" endpoint=\"";
  protected final String TEXT_121 = "\">" + NL + "\t\t\t\t\t<";
  protected final String TEXT_122 = ":target>" + NL + "\t\t\t\t\t\t<";
  protected final String TEXT_123 = ":exchange-target service=\"";
  protected final String TEXT_124 = ":";
  protected final String TEXT_125 = "\"/>" + NL + "\t\t\t\t\t</";
  protected final String TEXT_126 = ":target>" + NL + "\t\t\t\t\t<";
  protected final String TEXT_127 = ":inListener>" + NL + "\t\t\t\t\t\t<";
  protected final String TEXT_128 = ":exchange-target service=\"";
  protected final String TEXT_129 = ":";
  protected final String TEXT_130 = "\"/>" + NL + "\t\t\t\t\t</";
  protected final String TEXT_131 = ":inListener>" + NL + "\t\t\t\t</";
  protected final String TEXT_132 = ":wire-tap>";
  protected final String TEXT_133 = " ";
  protected final String TEXT_134 = "\t\t\t\t<";
  protected final String TEXT_135 = ":pipeline service=\"";
  protected final String TEXT_136 = ":";
  protected final String TEXT_137 = "\" endpoint=\"";
  protected final String TEXT_138 = "\">" + NL + "\t\t\t\t\t<";
  protected final String TEXT_139 = ":transformer>" + NL + "\t\t\t\t\t\t<";
  protected final String TEXT_140 = ":exchange-target service=\"";
  protected final String TEXT_141 = ":";
  protected final String TEXT_142 = "\"/>" + NL + "\t\t\t\t\t</";
  protected final String TEXT_143 = ":transformer>" + NL + "\t\t\t\t\t<";
  protected final String TEXT_144 = ":target>" + NL + "\t\t\t\t\t\t<";
  protected final String TEXT_145 = ":exchange-target service=\"";
  protected final String TEXT_146 = ":";
  protected final String TEXT_147 = "\"/>" + NL + "\t\t\t\t\t</";
  protected final String TEXT_148 = ":target>" + NL + "\t\t\t\t</";
  protected final String TEXT_149 = ":pipeline>";
  protected final String TEXT_150 = " ";
  protected final String TEXT_151 = "\t\t\t\t<";
  protected final String TEXT_152 = ":xpath-splitter service=\"";
  protected final String TEXT_153 = ":";
  protected final String TEXT_154 = "\" endpoint=\"";
  protected final String TEXT_155 = "\"" + NL + "\t\t\t\t\txpath=\"";
  protected final String TEXT_156 = "\">" + NL + "\t\t\t\t\t<";
  protected final String TEXT_157 = ":target>" + NL + "\t\t\t\t\t\t<";
  protected final String TEXT_158 = ":exchange-target service=\"";
  protected final String TEXT_159 = ":";
  protected final String TEXT_160 = "\"/>" + NL + "\t\t\t\t\t</";
  protected final String TEXT_161 = ":target>" + NL + "\t\t\t\t</";
  protected final String TEXT_162 = ":xpath-splitter>";
  protected final String TEXT_163 = " ";
  protected final String TEXT_164 = "\t\t\t\t<";
  protected final String TEXT_165 = ":split-aggregator service=\"";
  protected final String TEXT_166 = ":";
  protected final String TEXT_167 = "\" endpoint=\"";
  protected final String TEXT_168 = "\">" + NL + "\t\t\t\t\t<";
  protected final String TEXT_169 = ":target>" + NL + "\t\t\t\t\t\t<";
  protected final String TEXT_170 = ":exchange-target service=\"";
  protected final String TEXT_171 = ":";
  protected final String TEXT_172 = "\"/>" + NL + "\t\t\t\t\t</";
  protected final String TEXT_173 = ":target>" + NL + "\t\t\t\t</";
  protected final String TEXT_174 = ":split-aggregator>";
  protected final String TEXT_175 = " ";
  protected final String TEXT_176 = "\t\t\t\t<";
  protected final String TEXT_177 = ":endpoint" + NL + "\t\t\t\t\tservice=\"";
  protected final String TEXT_178 = ":";
  protected final String TEXT_179 = "\"" + NL + "\t\t\t\t\tendpoint=\"";
  protected final String TEXT_180 = "\"" + NL;
  protected final String TEXT_181 = "\t\t\t\t\ttargetService=\"";
  protected final String TEXT_182 = ":";
  protected final String TEXT_183 = "\"" + NL + "\t\t\t\t\ttargetEndpoint=\"";
  protected final String TEXT_184 = "\"" + NL;
  protected final String TEXT_185 = "\t\t\t\t\trole=\"";
  protected final String TEXT_186 = "\"" + NL + "\t\t\t\t\tdefaultMep=\"";
  protected final String TEXT_187 = "\"" + NL + "\t\t\t\t\tdestinationStyle=\"";
  protected final String TEXT_188 = "\"" + NL + "\t\t\t\t\tjmsProviderDestinationName=\"";
  protected final String TEXT_189 = "\"" + NL + "\t\t\t\t\tjndiConnectionFactoryName=\"";
  protected final String TEXT_190 = "\">" + NL + "\t\t\t\t</";
  protected final String TEXT_191 = ":endpoint>";
  protected final String TEXT_192 = " ";
  protected final String TEXT_193 = "\t\t\t\t<";
  protected final String TEXT_194 = ":message-filter service=\"";
  protected final String TEXT_195 = ":";
  protected final String TEXT_196 = "\" endpoint=\"";
  protected final String TEXT_197 = "\">" + NL + "\t\t\t\t\t<";
  protected final String TEXT_198 = ":target>" + NL + "\t\t\t\t\t\t<";
  protected final String TEXT_199 = ":exchange-target service=\"";
  protected final String TEXT_200 = ":";
  protected final String TEXT_201 = "\"/>" + NL + "\t\t\t\t\t</";
  protected final String TEXT_202 = ":target>" + NL + "\t\t\t\t\t<";
  protected final String TEXT_203 = ":filter>" + NL + "\t\t\t\t\t\t<";
  protected final String TEXT_204 = ":xpath-predicate xpath=\"";
  protected final String TEXT_205 = "\"/>" + NL + "\t\t\t\t    </";
  protected final String TEXT_206 = ":filter>" + NL + "\t\t\t\t</";
  protected final String TEXT_207 = ":message-filter>" + NL;
  protected final String TEXT_208 = NL + "\t\t\t</";
  protected final String TEXT_209 = ":endpoints>" + NL + "\t\t</";
  protected final String TEXT_210 = ":component>";
  protected final String TEXT_211 = NL + "      </";
  protected final String TEXT_212 = ":component>" + NL + "    </";
  protected final String TEXT_213 = ":activationSpec>" + NL + "\t";
  protected final String TEXT_214 = NL + " </";
  protected final String TEXT_215 = ":activationSpecs>" + NL + "</";
  protected final String TEXT_216 = ":container>" + NL + "</beans>";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     ContainerClass container = (ContainerClass) argument;
    stringBuffer.append(TEXT_1);
    NameSpaceClass defaultNamespace = container.getNameSpaceType();
		String nmsName = defaultNamespace.getNameSpaceName();
		String nmsValue = defaultNamespace.getNameSpaceValue(); 
    stringBuffer.append(TEXT_2);
    stringBuffer.append(nmsName);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(nmsValue);
    stringBuffer.append(TEXT_4);
     for (int i = 0; i < container.getLstNameSpace().size(); i++) {
		NameSpaceClass nmsTmp = (NameSpaceClass) container.getLstNameSpace().get(i);
		String nmsTmpName = nmsTmp.getNameSpaceName();
		String nmsTmpValue = nmsTmp.getNameSpaceValue();
	
    stringBuffer.append(TEXT_5);
    stringBuffer.append(nmsTmpName);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(nmsTmpValue);
    stringBuffer.append(TEXT_7);
     } 
    stringBuffer.append(TEXT_8);
    stringBuffer.append(nmsName);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(nmsName);
    stringBuffer.append(TEXT_10);
    for (int i = 0; i < container.getLstActivationSpec().size(); i++) {
			ActivationSpecClass myActi = (ActivationSpecClass) container.getLstActivationSpec().get(i);
	
     String actiNmsName = myActi.getNameSpaceRef().getNameSpaceName();
  
    stringBuffer.append(TEXT_11);
    stringBuffer.append(myActi.getComponentName());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(actiNmsName);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(myActi.getComponentName());
    stringBuffer.append(TEXT_14);
    stringBuffer.append(myActi.getServiceNameSpace().getNameSpaceName());
    stringBuffer.append(TEXT_15);
    stringBuffer.append(myActi.getServiceName());
    stringBuffer.append(TEXT_16);
    
    if (myActi.getDestinationServiceName().compareTo("") != 0 ) {
	  //add the destination service name
	  
    stringBuffer.append(TEXT_17);
    stringBuffer.append(myActi.getDestinationServiceNameSpace().getNameSpaceName());
    stringBuffer.append(TEXT_18);
    stringBuffer.append(myActi.getDestinationServiceName());
    stringBuffer.append(TEXT_19);
    
    }
    ComponentClass monCompo = (ComponentClass) myActi.getComponent();
    
    stringBuffer.append(TEXT_20);
     String compoNmsName = monCompo.getNamespaceComponentRef().getNameSpaceName();
    
    stringBuffer.append(TEXT_21);
    stringBuffer.append(compoNmsName);
    stringBuffer.append(TEXT_22);
    
    //look of this is a bean or a standard compo
  	if (monCompo.isBeanClass()) {
  		//call the bean element
		LWBeanClass myBean = (LWBeanClass) monCompo.getMonBean();
		
    stringBuffer.append(TEXT_23);
    stringBuffer.append(TEXT_24);
    
	   	if (myBean.getXmlnsValue().compareTo("") != 0) {
	   		
    stringBuffer.append(TEXT_25);
    stringBuffer.append(myBean.getXmlnsValue());
    stringBuffer.append(TEXT_26);
    
	   }
	   
    stringBuffer.append(TEXT_27);
    stringBuffer.append(myBean.getClassName());
    stringBuffer.append(TEXT_28);
     			for (int cpt = 0; cpt < myBean.getlstProp().size(); cpt++) {
      			PropertyClass myProp = (PropertyClass) myBean.getlstProp().get(cpt);
				//if bean -> property -> value
				if (myProp.isValue()) {

    stringBuffer.append(TEXT_29);
    stringBuffer.append(myProp.getPropertyName());
    stringBuffer.append(TEXT_30);
    
					List lstValue = myProp.getLstValue();
					if (lstValue.size() == 1) {
						
    stringBuffer.append(TEXT_31);
    stringBuffer.append(lstValue.get(0));
    stringBuffer.append(TEXT_32);
    
					} else {
						
    stringBuffer.append(TEXT_33);
    
								for (int u = 0; u < lstValue.size(); u++) {
    stringBuffer.append(TEXT_34);
    stringBuffer.append(lstValue.get(u));
    stringBuffer.append(TEXT_35);
    
								}
							
    stringBuffer.append(TEXT_36);
    
					}
				} else {
					//test if it is a map
					if (myProp.isMap()) {

    stringBuffer.append(TEXT_37);
    stringBuffer.append(myProp.getPropertyName());
    stringBuffer.append(TEXT_38);
    					 Map myMap = myProp.getLWMap();
					Set keys = myMap.keySet();
					Iterator ik = keys.iterator();
					while (ik.hasNext()) {
						//we get all the map element.
						Object key = ik.next();
						LWBeanClass beanKey = (LWBeanClass) key;
						LWBeanClass beanValue = (LWBeanClass) myMap.get(key);

    stringBuffer.append(TEXT_39);
    stringBuffer.append(beanKey.getClassName());
    stringBuffer.append(TEXT_40);
     							for (int cptMap = 0; cptMap < beanKey.getlstProp().size(); cptMap++) {
      							PropertyClass myPropMap = (PropertyClass) beanKey.getlstProp().get(cptMap);
								if (myPropMap.isValue()) {

    stringBuffer.append(TEXT_41);
    stringBuffer.append(myPropMap.getPropertyName());
    stringBuffer.append(TEXT_42);
    
									List lstValue = myPropMap.getLstValue();
									if (lstValue.size() == 1) {
										
    stringBuffer.append(TEXT_43);
    stringBuffer.append(lstValue.get(0));
    stringBuffer.append(TEXT_44);
    
									} else {
										
    stringBuffer.append(TEXT_45);
    
										for (int u = 0; u < lstValue.size(); u++) {
    stringBuffer.append(TEXT_46);
    stringBuffer.append(lstValue.get(u));
    stringBuffer.append(TEXT_47);
    
										}
										
    stringBuffer.append(TEXT_48);
    									}
			 					}
							}

    stringBuffer.append(TEXT_49);
    stringBuffer.append(beanValue.getClassName());
    stringBuffer.append(TEXT_50);
     							for (int cptMap = 0; cptMap < beanValue.getlstProp().size(); cptMap++) {
      							PropertyClass myPropMap = (PropertyClass) beanValue.getlstProp().get(cptMap);
								if (myPropMap.isValue()) {

    stringBuffer.append(TEXT_51);
    stringBuffer.append(myPropMap.getPropertyName());
    stringBuffer.append(TEXT_52);
    
									List lstValue = myPropMap.getLstValue();
									if (lstValue.size() == 1) {
										
    stringBuffer.append(TEXT_53);
    stringBuffer.append(lstValue.get(0));
    stringBuffer.append(TEXT_54);
    
									} else {
										
    stringBuffer.append(TEXT_55);
    
										for (int u = 0; u < lstValue.size(); u++) {
    stringBuffer.append(TEXT_56);
    stringBuffer.append(lstValue.get(u));
    stringBuffer.append(TEXT_57);
    
										}
										
    stringBuffer.append(TEXT_58);
    									}
			 					}
							}

    stringBuffer.append(TEXT_59);
    					}
    stringBuffer.append(TEXT_60);
    
					} else {

					LWBeanClass myBeanTmp = (LWBeanClass) myProp.getMyBean();

    stringBuffer.append(TEXT_61);
    stringBuffer.append(myProp.getPropertyName());
    stringBuffer.append(TEXT_62);
    
					if (!myBeanTmp.isProperty()) {
						//if bean -> property -> bean -> constructor
						LWBeanClass myBeanConstructor = myBeanTmp;
						int valEsp = 17;

     for (int compt = 0; compt < valEsp; compt++) {
    stringBuffer.append(TEXT_63);
    }
    stringBuffer.append(TEXT_64);
    stringBuffer.append(myBeanConstructor.getClassName());
    stringBuffer.append(TEXT_65);
     for (int compt = 0; compt < valEsp; compt++) {
    stringBuffer.append(TEXT_66);
    }
    stringBuffer.append(TEXT_67);
    stringBuffer.append(myBeanConstructor.getMyConstructor().getValue());
    stringBuffer.append(TEXT_68);
     for (int compt = 0; compt < valEsp; compt++) {
    stringBuffer.append(TEXT_69);
    }
    stringBuffer.append(TEXT_70);
    
					} else {

    stringBuffer.append(TEXT_71);
    
				   	if (myBeanTmp.getXmlnsValue().compareTo("") != 0) {
				   		
    stringBuffer.append(TEXT_72);
    stringBuffer.append(myBeanTmp.getXmlnsValue());
    stringBuffer.append(TEXT_73);
    
				   }
				   
    stringBuffer.append(TEXT_74);
    stringBuffer.append(myBeanTmp.getClassName());
    stringBuffer.append(TEXT_75);
       					for (int j = 0; j < myBeanTmp.getlstProp().size(); j++) {
							PropertyClass myPropTmp = (PropertyClass) myBeanTmp.getlstProp().get(j);

    stringBuffer.append(TEXT_76);
    stringBuffer.append(myPropTmp.getPropertyName());
    stringBuffer.append(TEXT_77);
    
							//we test if this property is a value or a bean
							if (myPropTmp.isValue()) {
								//if bean -> property -> bean -> property -> value
								//value
								List lstValue = myPropTmp.getLstValue();
								if (lstValue.size() == 1) {
									
    stringBuffer.append(TEXT_78);
    stringBuffer.append(lstValue.get(0));
    stringBuffer.append(TEXT_79);
    
								} else {
									
    stringBuffer.append(TEXT_80);
    
											for (int u = 0; u < lstValue.size(); u++) {
    stringBuffer.append(TEXT_81);
    stringBuffer.append(lstValue.get(u));
    stringBuffer.append(TEXT_82);
    
											}
							  
    stringBuffer.append(TEXT_83);
    
								}
							} else {
								//constructor
								//if bean -> property -> bean -> property -> bean -> constructor
								LWBeanClass myBeanConstructor = myPropTmp.getMyBean();
								int valEsp = 26;
								
    stringBuffer.append(TEXT_84);
    stringBuffer.append(TEXT_85);
     for (int compt = 0; compt < valEsp; compt++) {
    stringBuffer.append(TEXT_86);
    }
    stringBuffer.append(TEXT_87);
    stringBuffer.append(myBeanConstructor.getClassName());
    stringBuffer.append(TEXT_88);
     for (int compt = 0; compt < valEsp; compt++) {
    stringBuffer.append(TEXT_89);
    }
    stringBuffer.append(TEXT_90);
    stringBuffer.append(myBeanConstructor.getMyConstructor().getValue());
    stringBuffer.append(TEXT_91);
     for (int compt = 0; compt < valEsp; compt++) {
    stringBuffer.append(TEXT_92);
    }
    stringBuffer.append(TEXT_93);
    
							}

    stringBuffer.append(TEXT_94);
    
						}

    stringBuffer.append(TEXT_95);
    
					}

    stringBuffer.append(TEXT_96);
    
				   } //fin si map sinon
				} //fin else
			} //fin for

    stringBuffer.append(TEXT_97);
    
  	} else {
  		//call the standard compo element
	   StandardComponentClass monStdCompo = (StandardComponentClass) monCompo.getMonCompoStandard();
  		
    stringBuffer.append(TEXT_98);
    stringBuffer.append(TEXT_99);
    stringBuffer.append(monStdCompo.getNamespaceReference().getNameSpaceName());
    stringBuffer.append(TEXT_100);
    stringBuffer.append(monStdCompo.getNamespaceReference().getNameSpaceName());
    stringBuffer.append(TEXT_101);
    				StandardJBIClass myJBI = monStdCompo.getMyJBICompo();

    stringBuffer.append(TEXT_102);
     if (myJBI instanceof StandardHttpClass) {
					StandardHttpClass myHttp = (StandardHttpClass) myJBI;

    stringBuffer.append(TEXT_103);
    stringBuffer.append(TEXT_104);
    stringBuffer.append(myHttp.getNamespaceRef().getNameSpaceName());
    stringBuffer.append(TEXT_105);
    stringBuffer.append(myHttp.getDefaultMepValue());
    stringBuffer.append(TEXT_106);
    stringBuffer.append(myHttp.getServiceNamespace().getNameSpaceName());
    stringBuffer.append(TEXT_107);
    stringBuffer.append(myHttp.getServiceName());
    stringBuffer.append(TEXT_108);
    stringBuffer.append(myHttp.getEndpointValue());
    stringBuffer.append(TEXT_109);
    stringBuffer.append(myHttp.getProviderRole());
    stringBuffer.append(TEXT_110);
    stringBuffer.append(myHttp.getLocationURI());
    stringBuffer.append(TEXT_111);
    
					if (myHttp.isSoap()) {
						
    stringBuffer.append(TEXT_112);
    
					} else {
						
    stringBuffer.append(TEXT_113);
    
					}
    stringBuffer.append(TEXT_114);
    stringBuffer.append(myHttp.getNamespaceRef().getNameSpaceName());
    stringBuffer.append(TEXT_115);
    
				} else if (myJBI instanceof StandardEIPWireTap) {
					StandardEIPWireTap myWT = (StandardEIPWireTap) myJBI;

    stringBuffer.append(TEXT_116);
    			String nmsWTName = myWT.getNamespaceRef().getNameSpaceName();

    stringBuffer.append(TEXT_117);
    stringBuffer.append(nmsWTName);
    stringBuffer.append(TEXT_118);
    stringBuffer.append(myWT.getServiceNamespace().getNameSpaceName());
    stringBuffer.append(TEXT_119);
    stringBuffer.append(myWT.getServiceName());
    stringBuffer.append(TEXT_120);
    stringBuffer.append(myWT.getEndpointValue());
    stringBuffer.append(TEXT_121);
    stringBuffer.append(nmsWTName);
    stringBuffer.append(TEXT_122);
    stringBuffer.append(nmsWTName);
    stringBuffer.append(TEXT_123);
    stringBuffer.append(myWT.getTargetNamespace().getNameSpaceName());
    stringBuffer.append(TEXT_124);
    stringBuffer.append(myWT.getTargetName());
    stringBuffer.append(TEXT_125);
    stringBuffer.append(nmsWTName);
    stringBuffer.append(TEXT_126);
    stringBuffer.append(nmsWTName);
    stringBuffer.append(TEXT_127);
    stringBuffer.append(nmsWTName);
    stringBuffer.append(TEXT_128);
    stringBuffer.append(myWT.getListenerNamespace().getNameSpaceName());
    stringBuffer.append(TEXT_129);
    stringBuffer.append(myWT.getListenerName());
    stringBuffer.append(TEXT_130);
    stringBuffer.append(nmsWTName);
    stringBuffer.append(TEXT_131);
    stringBuffer.append(nmsWTName);
    stringBuffer.append(TEXT_132);
    
				} else if (myJBI instanceof StandardEIPPipelineClass) {
					StandardEIPPipelineClass myPipeline = (StandardEIPPipelineClass) myJBI;

    stringBuffer.append(TEXT_133);
    			String nmsPipelineName = myPipeline.getNamespaceRef().getNameSpaceName();

    stringBuffer.append(TEXT_134);
    stringBuffer.append(nmsPipelineName);
    stringBuffer.append(TEXT_135);
    stringBuffer.append(myPipeline.getServiceNameSpace().getNameSpaceName());
    stringBuffer.append(TEXT_136);
    stringBuffer.append(myPipeline.getServiceName());
    stringBuffer.append(TEXT_137);
    stringBuffer.append(myPipeline.getEndpoint());
    stringBuffer.append(TEXT_138);
    stringBuffer.append(nmsPipelineName);
    stringBuffer.append(TEXT_139);
    stringBuffer.append(nmsPipelineName);
    stringBuffer.append(TEXT_140);
    stringBuffer.append(myPipeline.getTrsfNamespace().getNameSpaceName());
    stringBuffer.append(TEXT_141);
    stringBuffer.append(myPipeline.getTransformeurServiceName());
    stringBuffer.append(TEXT_142);
    stringBuffer.append(nmsPipelineName);
    stringBuffer.append(TEXT_143);
    stringBuffer.append(nmsPipelineName);
    stringBuffer.append(TEXT_144);
    stringBuffer.append(nmsPipelineName);
    stringBuffer.append(TEXT_145);
    stringBuffer.append(myPipeline.getTargetNamespace().getNameSpaceName());
    stringBuffer.append(TEXT_146);
    stringBuffer.append(myPipeline.getTargetServiceName());
    stringBuffer.append(TEXT_147);
    stringBuffer.append(nmsPipelineName);
    stringBuffer.append(TEXT_148);
    stringBuffer.append(nmsPipelineName);
    stringBuffer.append(TEXT_149);
    
				} else if (myJBI instanceof StandardEIPSplitter) {
					StandardEIPSplitter mySplitter = (StandardEIPSplitter) myJBI;

    stringBuffer.append(TEXT_150);
    			String nmsSplitterName = mySplitter.getNamespaceRef().getNameSpaceName();

    stringBuffer.append(TEXT_151);
    stringBuffer.append(nmsSplitterName);
    stringBuffer.append(TEXT_152);
    stringBuffer.append(mySplitter.getServiceNamespace().getNameSpaceName());
    stringBuffer.append(TEXT_153);
    stringBuffer.append(mySplitter.getServiceName());
    stringBuffer.append(TEXT_154);
    stringBuffer.append(mySplitter.getEndpointValue());
    stringBuffer.append(TEXT_155);
    stringBuffer.append(mySplitter.getXPathRule());
    stringBuffer.append(TEXT_156);
    stringBuffer.append(nmsSplitterName);
    stringBuffer.append(TEXT_157);
    stringBuffer.append(nmsSplitterName);
    stringBuffer.append(TEXT_158);
    stringBuffer.append(mySplitter.getTargetNamespace().getNameSpaceName());
    stringBuffer.append(TEXT_159);
    stringBuffer.append(mySplitter.getTargetName());
    stringBuffer.append(TEXT_160);
    stringBuffer.append(nmsSplitterName);
    stringBuffer.append(TEXT_161);
    stringBuffer.append(nmsSplitterName);
    stringBuffer.append(TEXT_162);
    
				} else if (myJBI instanceof StandardEIPAggregator) {
					StandardEIPAggregator myAggreg = (StandardEIPAggregator) myJBI;

    stringBuffer.append(TEXT_163);
    			String nmsAggregName = myAggreg.getNamespaceRef().getNameSpaceName();

    stringBuffer.append(TEXT_164);
    stringBuffer.append(nmsAggregName);
    stringBuffer.append(TEXT_165);
    stringBuffer.append(myAggreg.getServiceNamespace().getNameSpaceName());
    stringBuffer.append(TEXT_166);
    stringBuffer.append(myAggreg.getServiceName());
    stringBuffer.append(TEXT_167);
    stringBuffer.append(myAggreg.getEndpointValue());
    stringBuffer.append(TEXT_168);
    stringBuffer.append(nmsAggregName);
    stringBuffer.append(TEXT_169);
    stringBuffer.append(nmsAggregName);
    stringBuffer.append(TEXT_170);
    stringBuffer.append(myAggreg.getTargetNamespace().getNameSpaceName());
    stringBuffer.append(TEXT_171);
    stringBuffer.append(myAggreg.getTargetName());
    stringBuffer.append(TEXT_172);
    stringBuffer.append(nmsAggregName);
    stringBuffer.append(TEXT_173);
    stringBuffer.append(nmsAggregName);
    stringBuffer.append(TEXT_174);
    
				} else if (myJBI instanceof StandardJMSClass) {
					StandardJMSClass myJMS = (StandardJMSClass) myJBI;

    stringBuffer.append(TEXT_175);
    stringBuffer.append(TEXT_176);
    stringBuffer.append(myJMS.getNamespaceRef().getNameSpaceName());
    stringBuffer.append(TEXT_177);
    stringBuffer.append(myJMS.getServiceNamespace().getNameSpaceName());
    stringBuffer.append(TEXT_178);
    stringBuffer.append(myJMS.getServiceName());
    stringBuffer.append(TEXT_179);
    stringBuffer.append(myJMS.getEndpointValue());
    stringBuffer.append(TEXT_180);
    					if (myJMS.getTargetServiceName() != null) {

    stringBuffer.append(TEXT_181);
    stringBuffer.append(myJMS.getTargetServiceNms().getNameSpaceName());
    stringBuffer.append(TEXT_182);
    stringBuffer.append(myJMS.getTargetServiceName());
    stringBuffer.append(TEXT_183);
    stringBuffer.append(myJMS.getTargetServiceName());
    stringBuffer.append(TEXT_184);
    					}

    stringBuffer.append(TEXT_185);
    stringBuffer.append(myJMS.getRole());
    stringBuffer.append(TEXT_186);
    stringBuffer.append(myJMS.getDefaultMepValue());
    stringBuffer.append(TEXT_187);
    stringBuffer.append(myJMS.getDestinationStyle());
    stringBuffer.append(TEXT_188);
    stringBuffer.append(myJMS.getJMSProviderDestinationName());
    stringBuffer.append(TEXT_189);
    stringBuffer.append(myJMS.getJndiConnectionFactoryName());
    stringBuffer.append(TEXT_190);
    stringBuffer.append(myJMS.getNamespaceRef().getNameSpaceName());
    stringBuffer.append(TEXT_191);
    
				} else if (myJBI instanceof StandardEIPMessageFilter) {
					StandardEIPMessageFilter myMessageFilter = (StandardEIPMessageFilter) myJBI;

    stringBuffer.append(TEXT_192);
    			String nmsMessageFilterName = myMessageFilter.getNamespaceRef().getNameSpaceName();

    stringBuffer.append(TEXT_193);
    stringBuffer.append(nmsMessageFilterName);
    stringBuffer.append(TEXT_194);
    stringBuffer.append(myMessageFilter.getServiceNameSpace().getNameSpaceName());
    stringBuffer.append(TEXT_195);
    stringBuffer.append(myMessageFilter.getServiceName());
    stringBuffer.append(TEXT_196);
    stringBuffer.append(myMessageFilter.getEndpoint());
    stringBuffer.append(TEXT_197);
    stringBuffer.append(nmsMessageFilterName);
    stringBuffer.append(TEXT_198);
    stringBuffer.append(nmsMessageFilterName);
    stringBuffer.append(TEXT_199);
    stringBuffer.append(myMessageFilter.getTargetNamespace().getNameSpaceName());
    stringBuffer.append(TEXT_200);
    stringBuffer.append(myMessageFilter.getTargetName());
    stringBuffer.append(TEXT_201);
    stringBuffer.append(nmsMessageFilterName);
    stringBuffer.append(TEXT_202);
    stringBuffer.append(nmsMessageFilterName);
    stringBuffer.append(TEXT_203);
    stringBuffer.append(nmsMessageFilterName);
    stringBuffer.append(TEXT_204);
    stringBuffer.append(myMessageFilter.getXpathRule());
    stringBuffer.append(TEXT_205);
    stringBuffer.append(nmsMessageFilterName);
    stringBuffer.append(TEXT_206);
    stringBuffer.append(nmsMessageFilterName);
    stringBuffer.append(TEXT_207);
    
					} 
    stringBuffer.append(TEXT_208);
    stringBuffer.append(monStdCompo.getNamespaceReference().getNameSpaceName());
    stringBuffer.append(TEXT_209);
    stringBuffer.append(monStdCompo.getNamespaceReference().getNameSpaceName());
    stringBuffer.append(TEXT_210);
    
  	} 
    stringBuffer.append(TEXT_211);
    stringBuffer.append(compoNmsName);
    stringBuffer.append(TEXT_212);
    stringBuffer.append(actiNmsName);
    stringBuffer.append(TEXT_213);
     } 
    stringBuffer.append(TEXT_214);
    stringBuffer.append(nmsName);
    stringBuffer.append(TEXT_215);
    stringBuffer.append(nmsName);
    stringBuffer.append(TEXT_216);
    return stringBuffer.toString();
  }
}