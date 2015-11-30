package mq.popmenu.extend.dialogs;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import mq.popmenu.extend.util.SpringUtil;

public class SpringDialog extends Dialog{

	//private Button filebtn;
	private Button checkbox1;
	private Button checkbox2;
	private Text versiontest;
	private IJavaProject project;

	public SpringDialog(Shell parentShell, IJavaProject project) {
		super(parentShell);
		this.project = project;
	}

	 /**
	* 在这个方法里构建Dialog中的界面内容
	*/
	protected Control createDialogArea(Composite parent) {
		final GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 10;
		layout.marginHeight = 1;
		getShell().setText("SpringMVC 详细配置"); //设置Dialog的标头
		parent.setLayout(layout);
		parent.setSize(200, 200);
		parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		{
			Composite xmlComposite = createComposite(parent, 3);
			final Label label = new Label(xmlComposite, SWT.NONE);
			label.setText("包含: ");
			checkbox1 = new Button(xmlComposite, SWT.CHECK);
			checkbox1.setText("pom.xml");
			checkbox2 = new Button(xmlComposite, SWT.CHECK);
			checkbox2.setText("spring.xml");
			/*checkbox2.addSelectionListener(new SelectionListener(){
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					if(checkbox2.getSelection()) {  
						filebtn.setEnabled(true);
			        } else {  
			        	filebtn.setEnabled(false);
			        }  
				}});*/
		}
		/*{
			Composite fileComposite = createComposite(parent, 3);
			final Label filelabel = new Label(fileComposite, SWT.NONE);
			filelabel.setText("目录: ");
			
			filetext = new Text(fileComposite, SWT.BORDER|SWT.READ_ONLY);
			filebtn = new Button(fileComposite, SWT.NONE);
			filebtn.setText("search");
			filebtn.setEnabled(false);
			filebtn.addSelectionListener(new SelectionListener(){
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot(), true,"请选择文件夹");
					if (dialog.open() == ContainerSelectionDialog.OK) {
						Object[] result = dialog.getResult();
						if (result.length == 1) {
							filetext.setText(((IPath) result[0]).toOSString());
						}
					}
				}});
		}*/
		{
			Composite versionComposite = createComposite(parent, 2);
			final Label versionlabel = new Label(versionComposite, SWT.NONE);
			versionlabel.setText("版本: ");
			versiontest = new Text(versionComposite, SWT.BORDER);
			versiontest.setText("4.1.7.RELEASE");
		}
		return parent;
	}

	public Composite createComposite(Composite parent, int columnsLength){
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layoutComposite = new GridLayout();
		layoutComposite.numColumns = columnsLength;
		layoutComposite.marginHeight = 1;
		composite.setLayout(layoutComposite);
		composite.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,2,2));
		return composite;
	}
	/**
	* 重载这个方法可以改变窗口的默认式样
	* SWT.RESIZE：窗口可以拖动边框改变大小
	* SWT.MAX：　窗口可以最大化
	*/
	protected int getShellStyle() {
		return super.getShellStyle();
	}
	
	/**
	* Dialog点击按钮时执行的方法
	*/
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID){
			IFile iFile_pom = project.getProject().getFile("pom.xml");
			IFile iFile_web = project.getProject().getFile("src/main/webapp/WEB-INF/web.xml");
			String webFile = iFile_web.getLocation().toOSString();
			System.out.println("version : " + versiontest.getText());
			System.out.println("webFile : " + webFile);
			if(checkbox1.getSelection()) {  
				String pomFile = iFile_pom.getLocation().toOSString();
				System.out.println("pomFile : " + pomFile);
				SpringUtil.addSpringMVC_pom(pomFile, versiontest.getText());
	        }
			if(checkbox2.getSelection()) {  
				SpringUtil.addSpringMVC_web(webFile, true);
				String project_path = project.getProject().getLocation().toOSString();
				String localFilePath = project_path + "/src/main/resources/spring";
				System.out.println(localFilePath);
				SpringUtil.addSpringMVC_xml(localFilePath);
	        }else{
	        	SpringUtil.addSpringMVC_web(webFile, false);
	        }
		}
		super.buttonPressed(buttonId);
	}

}
