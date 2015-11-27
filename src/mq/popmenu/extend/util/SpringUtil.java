package mq.popmenu.extend.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.qxp.ctrl.maven.PomAction;
import org.qxp.ctrl.maven.po.Dependency;
import org.qxp.ctrl.maven.po.PomProperty;
import org.qxp.ctrl.util.IOUtil;
import org.qxp.ctrl.web.WebAction;
import org.qxp.ctrl.web.po.ContextParam;
import org.qxp.ctrl.web.po.InitParam;
import org.qxp.ctrl.web.po.Listener;
import org.qxp.ctrl.web.po.Servlet;

public class SpringUtil {

	public static final String RELATIVE_PATH = System.getProperty("user.dir"); 
	
	public static String getClassPath(Class<?> s){
		String path = s.getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.substring(1, path.length());
		return path;
	}
	
	public static void addSpringMVC_pom(String path, String mvcversion){
		Properties prop = new Properties();     
         try{
        	 PomProperty pomProperty = new PomProperty();
        	 pomProperty.setKey("spring.version");
        	 pomProperty.setValue(mvcversion);
        	 PomAction.addProperties(path, pomProperty);
        	 String class_path = getClassPath(SpringUtil.class);
        	 String pro_path = class_path + "src/mq/popmenu/extend/util/SpringMVC.properties";
        	 System.out.println(pro_path);
             InputStream in = new BufferedInputStream (new FileInputStream(pro_path));
             prop.load(in);
             String groupId = prop.getProperty("groupId");
             String artifactIds = prop.getProperty("artifactId");
             String version = prop.getProperty("version");
             String scope = prop.getProperty("scope");
             String[] artifactIdArray = artifactIds.split(",");
             Dependency[] dependencyArray = new Dependency[artifactIdArray.length];
             for(int i=0;i<artifactIdArray.length;i++){
            	 String artifactId = artifactIdArray[i];
            	 Dependency d = new Dependency();
            	 d.setArtifactId(artifactId);
            	 d.setGroupId(groupId);
            	 d.setScope(scope);
            	 d.setVersion(version);
            	 dependencyArray[i] = d;
             }
             in.close();
             PomAction.addDependencys(path, dependencyArray);
         }
         catch(Exception e){
        	 e.printStackTrace();
         }

	}
	
	public static void addSpringMVC_web(String path, boolean isConfig){
		Listener listener = new Listener();
		listener.setListenerClass("org.springframework.web.context.ContextLoaderListener");
		Servlet servlet = new Servlet();
		servlet.setServletName("springMVC");
		servlet.setServletClass("org.springframework.web.servlet.DispatcherServlet");
		servlet.setLoadOnStartup(1);
		servlet.setPattern("/");
		if(isConfig){
			InitParam initParam = new InitParam();
			initParam.setName("contextConfigLocation");
			initParam.setValue("classpath:spring/spring-mvc.xml");
			servlet.setInitParam(initParam);
			
			ContextParam contextParam = new ContextParam();
			contextParam.setName("contextConfigLocation");
			contextParam.setValue("classpath:spring/spring.xml");
			WebAction.addContextParam(path, contextParam);
		}
		WebAction.addListener(path, listener);
		WebAction.addServlet(path, servlet);
	}
	
	public static void addSpringMVC_xml(String localFilePath){
		 String class_path = getClassPath(SpringUtil.class);
		 String springmvc_name = "spring-mvc.xml";
		 String spring_name = "spring.xml";
		 String remoteFilePath1 = class_path + "resouces/spring/" + springmvc_name;
		 String remoteFilePath2 = class_path + "resouces/spring/" + spring_name;
		 IOUtil.downloadLocalFile(remoteFilePath1, localFilePath, localFilePath + "/" + springmvc_name);
		 IOUtil.downloadLocalFile(remoteFilePath2, localFilePath, localFilePath + "/" + spring_name);
	}
	
}
